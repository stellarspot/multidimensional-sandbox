/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samples;

import javax.swing.SwingUtilities;
import multidimensional.java2d.camera.MDFrameJava2D;
import multidimensional.shape.MDGridElem;
import multidimensional.shape.MDShape;
import multidimensional.shape.MDShapeUniverse;

/**
 *
 * @author stellarspot
 */
public class MDCubeSample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int dimension = 4;
                MDGridElem cubeElem = new MDGridElem(dimension, 300, 1);
                MDShape crossShape = new MDShape();
                crossShape.getElems().addTail(cubeElem);

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
