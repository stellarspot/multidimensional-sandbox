/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samples;

import javax.swing.SwingUtilities;
import multidimensional.java2d.camera.MDFrameJava2D;
import multidimensional.shape.MDCrossElem;
import multidimensional.shape.MDShape;
import multidimensional.shape.MDShapeUniverse;

/**
 *
 * @author stellarspot
 */
public class CrossSample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int dimension = 4;
                MDCrossElem crossElem = new MDCrossElem(dimension, 300);
                MDShape crossShape = new MDShape();
                crossShape.getElems().addTail(crossElem);

                MDShapeUniverse universe = new MDShapeUniverse(dimension);
                universe.setShape(crossShape);

                MDFrameJava2D frameJava2D = new MDFrameJava2D(universe);
                frameJava2D.setVisible(true);
                frameJava2D.addCameraRotationTransforms();
                frameJava2D.animate();

            }
        });
    }
}
