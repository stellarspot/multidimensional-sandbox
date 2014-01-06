/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.wave;

import java.util.Arrays;
import multidimensional.mathematics.CMDVector;
import multidimensional.mathematics.IMDFunction;
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
    double m = 1000;
    double k = 10;

    public MDWave1Universe() {
        super(2);
        dx = 1.0 / N;
        dt = 3 * dx;
        wavesPlus = new double[N];
        waves = new double[N]; // test
        wavesMinus = new double[N]; // test
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
        wavesMinus = Arrays.copyOf(waves, N);
        waves = Arrays.copyOf(wavesPlus, N);

        for (int i = 0; i < N; i++) {
            evaluateWave(i);
        }

    }

    private void evaluateWave(int i) {
        double w = 2 * waves[i] - wavesMinus[i];
        double x = getWave(i);
        double xMinus = getWave(i - 1);
        double xPlus = getWave(i + 1);

        double xx = (xPlus - 2 * x + xMinus) / (dx * dx);

        xx = (i == 0 || i == N) ? 0 : xx;

        w += (k / m) * xx * (dt * dt);
        //w = (k / m) * xx;


        //wavesPlus[i] = xx;
        wavesPlus[i] = w;

    }

    double getWave(int i) {
        return (i <= 0 || i >= N) ? 0 : waves[i];
    }
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

            init(N);

            double scale = 200;

            for (int i = 0; i < N; i++) {
                vectors[i] = new CMDVector(2 * scale * (i * dx - 0.5), scale * wavesPlus[i]);
                vertices.addTail(new MDShapeElem.ShapeVertex(2.0, i));
            }

        }
    }
}
