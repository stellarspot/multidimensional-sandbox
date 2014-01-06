/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samples;

import javax.swing.SwingUtilities;
import multidimensional.java2d.camera.MDFrameJava2D;
import multidimensional.mathematics.MDVector;
import multidimensional.physics.field.relativity.MDParticle;
import multidimensional.physics.field.relativity.MDRelativityUniverse;

/**
 *
 * @author stellarspot
 */
public class LatticeTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int dimension = 3;
                MDParticle particle1 = new MDParticle(5, 10, new MDVector(0, 20, 50, 0));
                MDParticle particle2 = new MDParticle(10, 15, new MDVector(0, 200, 250, 0));

//                int N = 100;
//                MDRelativityLattice lattice = new MDRelativityLattice(N, N, N, N, particle);

                MDRelativityUniverse universe = new MDRelativityUniverse(particle1, particle2);


//                MDCrossElem crossElem = new MDCrossElem(dimension, 300);
//                MDShape crossShape = new MDShape();
//                crossShape.getElems().addTail(crossElem);
//
//                MDShapeUniverse universe = new MDShapeUniverse(dimension);
//                universe.setShape(crossShape);

                MDFrameJava2D frameJava2D = new MDFrameJava2D(universe);
                frameJava2D.setVisible(true);
                frameJava2D.addCameraRotationTransforms();
                frameJava2D.animate();

            }
        });
    }
}
