/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samples;

import javax.swing.SwingUtilities;
import multidimensional.java2d.camera.MDFrameJava2D;
import multidimensional.mathematics.IMDFunction;
import multidimensional.physics.wave.MDWave1Universe;

/**
 *
 * @author stellarspot
 */
public class Wave1Sample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                MDWave1Universe universe = new MDWave1Universe();

                final int n = 3;
                final double A = 0.9;
                universe.init(new IMDFunction() {
                    @Override
                    public double getValue(double x) {
                        return A * Math.sin(2 * Math.PI * n * x);
                        //return Math.sin(A * 2 * Math.PI * n * x);
                    }
                });

//                int steps = 3;
//                for (int i = 0; i < steps; i++) {
//                    universe.evaluate();
//
//                }

                MDFrameJava2D frameJava2D = new MDFrameJava2D(universe);
                frameJava2D.setVisible(true);
                frameJava2D.animate();

            }
        });
    }
}