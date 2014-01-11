/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.wave;

import java.util.Arrays;
import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDListListener;
import multidimensional.mathematics.CMDVector;
import multidimensional.mathematics.IMDFunction;
import multidimensional.shape.IMDCamera;
import multidimensional.shape.IMDCameraListener;
import multidimensional.shape.IMDShapeElem;
import multidimensional.shape.MDShape;
import multidimensional.shape.MDShapeElem;
import multidimensional.shape.MDShapeUniverse;

/**
 *
 * @author stellarspot
 */
public class MDWave1Universe extends MDShapeUniverse {

    //IMDFunction initWave;
    int N = 500;
    //int N = 1000;
    double dx;
    double[] waves;
    double[] wavesPlus;
    double[] wavesMinus;
    double dt;
    double m;
    double k;
    double length;
    ICMDList<DragPoint> dragPoints = new CMDList<>();
    double maxDragY;

    public MDWave1Universe(double mass, double k, double length) {
        super(2);
        this.m = mass;
        this.k = k;
        this.length = length;

        dx = length / N;
        dt = 3 * dx;
        wavesPlus = new double[N];
        waves = new double[N]; // test
        wavesMinus = new double[N]; // test

        maxDragY = 2 * dx;

        System.out.println("cameras: " + getCameras().getSize());

        getCameras().addListeners(new CameraListListener());
    }

    public void init(IMDFunction initWave) {

        for (int i = 0; i < N; i++) {
            wavesPlus[i] = initWave.getValue(i * dx);
        }
        waves = Arrays.copyOf(wavesPlus, N);
    }

    @Override
    public void evaluate() {
//        System.out.println("----------------");
//        System.out.println(format("waves plus ", wavesPlus));
//        System.out.println(format("waves      ", waves));
//        System.out.println(format("waves minus", wavesMinus));
        evaluateWave();
        setShape(getRootShape());
        super.evaluate();
    }

    private String format(String msg, double[] array) {
        String res = msg + ": ";

        for (int i = 0; i < array.length; i++) {
            String value = String.format("%1.2f", array[i]);
            res += value + " ";
        }
        return res;
    }

    private void evaluateWave() {
//        double[] temp = wavesMinus;
//        wavesMinus = waves;
//        waves = wavesPlus;
//        wavesPlus = temp;

        wavesMinus = Arrays.copyOf(waves, N);
        waves = Arrays.copyOf(wavesPlus, N);

        for (int i = 1; i < N - 1; i++) {
            evaluateWave(i);
        }

        for (DragPoint dragPoint : dragPoints) {

            int n = dragPoint.n;
            if (Math.abs(dragPoint.y - wavesPlus[n]) < 0.1) {
                dragPoint.connected = true;
            }

            if (dragPoint.connected) {
                wavesPlus[n] = dragPoint.y;
            }

            dragPoint.evaluate();
            //dragPoint.allowDrag = true;
        }
    }

    private void evaluateWave(int i) {
        double w = 2 * waves[i] - wavesMinus[i];
        double x = waves[i];
        double xMinus = waves[i - 1];
        double xPlus = waves[i + 1];

        double xx = (xPlus - 2 * x + xMinus) / (dx * dx);

        //xx = (i == 0 || i == N) ? 0 : xx;

        w += (k / m) * xx * (dt * dt);
        //w = (k / m) * xx;


        //wavesPlus[i] = xx;
        wavesPlus[i] = w;

    }

    int getIndex(double x) {
        return (int) (x / dx);
    }

//    public IMDFunction getInitWave() {
//        return initWave;
//    }
//
//    public void setInitWave(IMDFunction initWave) {
//        this.initWave = initWave;
//    }
    MDShape getRootShape() {

        MDShape shape = new MDShape();

//        final int scale = 20;

//        shape.getTransforms().addTail(new IMDTransform() {
//            @Override
//            public IMDVector transform(IMDBaseVector v) {
//                return v.mul(scale);
//            }
//        });


        ICMDList<IMDShapeElem> shapeElems = shape.getElems();
        shapeElems.addTail(new WaveShapeElem());
        for (DragPoint dragPoint : dragPoints) {
            shapeElems.addTail(dragPoint.getShape());
        }


        return shape;
    }

    class WaveShapeElem extends MDShapeElem {

        public WaveShapeElem() {

            init(N + 2);

            //double scale = 200;

            for (int i = 0; i < N; i++) {
                vectors[i] = new CMDVector(i * dx, wavesPlus[i]);
                vertices.addTail(new MDShapeElem.ShapeVertex(2.0, i));
            }

            double r = 5;
            vectors[N] = new CMDVector(0, wavesPlus[0]);
            vertices.addTail(new MDShapeElem.ShapeVertex(r, N));
            vectors[N + 1] = new CMDVector(length, wavesPlus[N - 1]);
            vertices.addTail(new MDShapeElem.ShapeVertex(r, N + 1));


//            double scale = 200;
//
//            for (int i = 0; i < N; i++) {
//                vectors[i] = new CMDVector(2 * scale * (i * dx - 0.5), scale * wavesPlus[i]);
//                vertices.addTail(new MDShapeElem.ShapeVertex(2.0, i));
//            }
//
//            double r = 5;
//            vectors[N] = new CMDVector(2 * scale * (0 - 0.5), scale * wavesPlus[0]);
//            vertices.addTail(new MDShapeElem.ShapeVertex(r, N));
//            vectors[N + 1] = new CMDVector(2 * scale * ((N - 1) * dx - 0.5), scale * wavesPlus[N - 1]);
//            vertices.addTail(new MDShapeElem.ShapeVertex(r, N + 1));

        }
    }

    class DragPoint {

        double x;
        double y;
        double targetY;
        int n;
        boolean connected;
        boolean allowDrag;

        public DragPoint(double x, double y, double dx) {
            this.n = (int) (x / dx);
            this.x = x;
            this.y = y;
            this.targetY = y;
        }

        void evaluate() {
            if (targetY != y) {
                double delta = targetY - y;
                double minDelta = Math.min(Math.abs(delta), maxDragY);
                y += (0 <= delta) ? minDelta : -minDelta;
            }
        }

        IMDShapeElem getShape() {
            return new DragPointShapeElem();

        }

        class DragPointShapeElem extends MDShapeElem {

            public DragPointShapeElem() {
                init(new CMDVector(x, y));
                getVertices().addTail(new ShapeVertex(10, 0));
            }
        }
    }

    class CameraListener implements IMDCameraListener {

        @Override
        public void screenPress(ScreenEvent e) {
            double x = e.getX();
            double y = e.getY();
            //lastY = y;
            int n = getIndex(x);

            boolean found = false;
            for (DragPoint dragPoint : dragPoints) {
                if (dragPoint.n == n) {
                    //dragPoint.x = x;
                    dragPoint.y = y;
                    dragPoint.targetY = y;
                    found = true;
                    break;
                }
            }

            if (!found) {
                dragPoints.clear();
                dragPoints.addTail(new DragPoint(x, y, dx));
            }

            System.out.println("camera press x: " + x + ", y: " + y);
        }

        @Override
        public void screenDrag(ScreenEvent e) {
            double x = e.getX();
            double y = e.getY();
            int n = getIndex(x);

            for (DragPoint dragPoint : dragPoints) {
//                if (dragPoint.n == n) {
//                }

//                double delta = y - lastY;
//                double minDelta = Math.min(Math.abs(delta), maxDragY);
//                dragPoint.y += (0 <= delta) ? minDelta : -minDelta;

                dragPoint.targetY = y;
                break;
            }

            //lastY = y;



            //System.out.println("camera drag x: " + x + ", y: " + y);
        }

        @Override
        public void screenRelease(ScreenEvent e) {
            dragPoints.clear();
        }
    }

    class CameraListListener implements IMDListListener<IMDCamera> {

        IMDCameraListener cameraListener = new CameraListener();

        @Override
        public void add(IMDCamera camera) {
            System.out.println("camera added");
            camera.addListener(cameraListener);
        }

        @Override
        public void remove(IMDCamera camera) {
        }
    }
}
