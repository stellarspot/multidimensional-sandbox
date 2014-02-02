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
import multidimensional.shape.MDColor;
import multidimensional.shape.MDShape;
import multidimensional.shape.MDShapeElem;
import multidimensional.shape.MDShapeProperties;
import multidimensional.shape.MDShapeUniverse;

/**
 *
 * @author stellarspot
 */
public class MDWave1Universe extends MDShapeUniverse {

    IMDFunction initWave;
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

        //System.out.println("cameras: " + getCameras().getSize());

        getCameras().addListeners(new CameraListListener());

        initWave = new IMDFunction() {
            @Override
            public double getValue(double x) {
                return 0;
            }
        };
    }

    public void setInitFunction(IMDFunction initWave) {
        this.initWave = initWave;
        reset();
    }

    public void reset() {
        for (int i = 0; i < N; i++) {
            wavesPlus[i] = initWave.getValue(i * dx);
        }
        waves = Arrays.copyOf(wavesPlus, N);
    }

    @Override
    public void evaluate() {
        evaluateAll();
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

    private void evaluateAll() {
//        double[] temp = wavesMinus;
//        wavesMinus = waves;
//        waves = wavesPlus;
//        wavesPlus = temp;

        wavesMinus = Arrays.copyOf(waves, N);
        waves = Arrays.copyOf(wavesPlus, N);
        for (int i = 1; i < N - 1; i++) {
            wavesPlus[i] = 0;
        }

        for (int i = 1; i < N - 1; i++) {
            evaluateWave(i);
        }

        for (DragPoint dragPoint : dragPoints) {
            System.out.println("Use Dag Point");
            dragPoint.evaluate();
        }
    }

    private void evaluateWave(int i) {
        double w = 2 * waves[i] - wavesMinus[i];
        double x = waves[i];
        double xMinus = waves[i - 1];
        double xPlus = waves[i + 1];

        double xx = (xPlus - 2 * x + xMinus) / (dx * dx);

        w += (k / m) * xx * (dt * dt);
        wavesPlus[i] = w;
    }

    int getIndex(double x) {
        return (int) (x / dx);
    }

    MDShape getRootShape() {

        MDShape shape = new MDShape();

        ICMDList<IMDShapeElem> shapeElems = shape.getElems();
        shapeElems.addTail(new WaveShapeElem());
        shapeElems.addTail(new PinsShapeElem());

        for (DragPoint dragPoint : dragPoints) {
            shapeElems.addTail(dragPoint.getShape());
        }

        return shape;
    }

    class PinsShapeElem extends MDShapeElem {

        public PinsShapeElem() {
            init(2);
            double r = 5;
            properties.put(MDShapeProperties.Name.COLOR, MDColor.DARK_GREEN);
            vectors[0] = new CMDVector(0, wavesPlus[0]);
            IMDShapeElem.Vertex vertex = new MDShapeElem.ShapeVertex(r, 0);
            //vertex.getProperties().put(MDShapeProperties.Name.COLOR, MDColor.DARK_GREEN);
            vertices.addTail(vertex);
            vectors[1] = new CMDVector(length, wavesPlus[N - 1]);
            vertex = new MDShapeElem.ShapeVertex(r, 1);
            //vertex.getProperties().put(MDShapeProperties.Name.COLOR, MDColor.DARK_GREEN);
            vertices.addTail(vertex);

        }
    }

    class WaveShapeElem extends MDShapeElem {

        public WaveShapeElem() {

            init(N);

            properties.put(MDShapeProperties.Name.COLOR, MDColor.BLUE);

            //vectors[0] = new CMDVector(0, wavesPlus[0]);
            for (int i = 0; i < N - 1; i++) {
                vectors[i] = new CMDVector(i * dx, wavesPlus[i]);
                //vertices.addTail(new MDShapeElem.ShapeVertex(2.0, i));
                segments.addTail(new ShapeSegment(i, i + 1));
            }
            vectors[N - 1] = new CMDVector(length, wavesPlus[N - 1]);
        }
    }

    class DragPoint {

        double dragK = 300 * k;
        int id;
        double x;
        double y;
        int index;
        boolean allowDrag;

        public DragPoint(int id, double x, double y) {
            this.index = (int) (x / dx);
            this.x = x;
            this.y = y;
        }

        void evaluate() {
            double dx = y - wavesPlus[index];
            double w = (dragK / m) * dx * (dt * dt);

            wavesPlus[index] += w;
        }

        IMDShapeElem getShape() {
            return new DragPointShapeElem();
        }

        class DragPointShapeElem extends MDShapeElem {

            public DragPointShapeElem() {
                init(new CMDVector(x, waves[index]), new CMDVector(x, y));

                Vertex waveVertex = new ShapeVertex(10, 0);
                waveVertex.getProperties().put(MDShapeProperties.Name.COLOR, MDColor.RED);
                waveVertex.getProperties().put(MDShapeProperties.Name.FILL, Boolean.FALSE);

                Vertex dragVertex = new ShapeVertex(5, 1);
                Segment segment = new ShapeSegment(0, 1);
                segment.getProperties().put(MDShapeProperties.Name.COLOR, MDColor.RED);

                getVertices().addTail(waveVertex);
                getVertices().addTail(dragVertex);
                getSegments().addTail(segment);
            }
        }
    }

    class CameraListener implements IMDCameraListener {

        @Override
        public void screenPress(MDScreenEvent e) {
            double x = e.getX();
            double y = e.getY();
            int n = getIndex(x);

            if (0 < n && n < N - 1) {
                int id = e.getId();
                dragPoints.addTail(new DragPoint(id, x, y));
            }
        }

        @Override
        public void screenDrag(MDScreenEvent e) {
            int id = e.getId();

            for (DragPoint dragPoint : dragPoints) {

                if (dragPoint.id == id) {
                    dragPoint.y = e.getY();
                    break;
                }
            }
            //System.out.println("camera drag x: " + x + ", y: " + y);
        }

        @Override
        public void screenRelease(MDScreenEvent e) {
            System.out.println("Remove drag points: " + dragPoints.getSize());
            dragPoints.clear();
        }

        @Override
        public void keyPress(MDKeyEvent e) {
            char c = Character.toLowerCase(e.getChar());
            switch (c) {
                case 'r':
                    reset();
                    break;
            }
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
