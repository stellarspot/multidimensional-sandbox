/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.wave;

import java.util.Arrays;
import multidimensional.datatype.IMDListListener;
import multidimensional.mathematics.CMDVector;
import multidimensional.mathematics.IMDFunction;
import multidimensional.shape.IMDCamera;
import multidimensional.shape.IMDCameraListener;
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
    double dx;
    double[] waves;
    double[] wavesPlus;
    double[] wavesMinus;
    double dt;
    double m;
    double k;
    double length;

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
        setShape(getWaveShape());
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

//    double getWave(int i) {
//        //return (i <= 0 || i >= N) ? 0 : waves[i];
//        return waves[i];
//    }
//    public IMDFunction getInitWave() {
//        return initWave;
//    }
//
//    public void setInitWave(IMDFunction initWave) {
//        this.initWave = initWave;
//    }
    MDShape getWaveShape() {

        MDShape shape = new MDShape();

//        final int scale = 20;

//        shape.getTransforms().addTail(new IMDTransform() {
//            @Override
//            public IMDVector transform(IMDBaseVector v) {
//                return v.mul(scale);
//            }
//        });


        shape.getElems().addTail(new WaveShapeElem());

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

    class CameraListener implements IMDCameraListener {

        @Override
        public void screenPress(ScreenEvent e) {
            double x = e.getX();
            double y = e.getY();

            System.out.println("camera press x: " + x + ", y: " + y);
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
