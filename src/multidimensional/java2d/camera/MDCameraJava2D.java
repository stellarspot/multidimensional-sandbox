/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.java2d.camera;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDList;
import multidimensional.mathematics.IMDTransform;
import multidimensional.mathematics.IMDVector;
import multidimensional.shape.IMDCameraElem;

/**
 *
 * @author stellarspot
 */
public class MDCameraJava2D implements IMDSwingCamera {

    IMDList<IMDCameraElem> elems;
    ICMDList<IMDTransform> transforms = new CMDList<>();
    private CameraCanvas canvas = new CameraCanvas();
    private volatile boolean isPainted = false;

    @Override
    public void draw(final IMDList<IMDCameraElem> elems) {

        if (!isPainted) {
            isPainted = true;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MDCameraJava2D.this.elems = elems;
                    canvas.repaint();
                }
            });
        }
    }

    @Override
    public ICMDList<IMDTransform> getTransforms() {
        return transforms;
    }

    @Override
    public Component getComponent() {
        return canvas;
    }

    class CameraCanvas extends JComponent {

        @Override
        public void paint(Graphics g) {

            isPainted = true;

            try {
                if (elems == null) {
                    return;
                }

                Graphics2D g2 = (Graphics2D) g;

                g2.translate(MDFrameJava2D.WIDTH / 2, MDFrameJava2D.HEIGHT / 2);
                g2.scale(1, -1);

                for (IMDCameraElem elem : elems) {
                    drawElem(g2, elem);
                }

            } finally {
                isPainted = false;
            }
        }

        void drawElem(Graphics2D g, IMDCameraElem elem) {

            IMDVector[] vectors = elem.getVectors();

            for (IMDCameraElem.Vertex vertex : elem.getVertices()) {
                drawVertex(g, vertex, vectors);
            }

            for (IMDCameraElem.Segment segment : elem.getSegments()) {
                drawSegment(g, segment, vectors);
            }
        }

        void drawVertex(Graphics2D g, IMDCameraElem.Vertex vertex, IMDVector[] vectors) {

            int r = (int) vertex.getRadius();
            int r2 = r * 2;
            IMDVector v = vectors[vertex.getCoordinats()];

            int x = getCoordinats(0, v);
            int y = getCoordinats(1, v);

            //g.drawOval(x - r, y - r, r2, r2);
            g.fillOval(x - r, y - r, r2, r2);

        }

        void drawSegment(Graphics2D g, IMDCameraElem.Segment segment, IMDVector[] vectors) {

            IMDVector v1 = vectors[segment.getVertex1()];
            IMDVector v2 = vectors[segment.getVertex2()];

            //System.out.println("segment: " + v1 + ", " + v2);

            int x1 = getCoordinats(0, v1);
            int y1 = getCoordinats(1, v1);
            int x2 = getCoordinats(0, v2);
            int y2 = getCoordinats(1, v2);

            //System.out.printf("x1: %d, y1: %d, x2: %d, y2: %d\n", x1, y1, x2, y2);

            g.drawLine(x1, y1, x2, y2);
        }
    }

    int getCoordinats(int index, IMDVector v) {

        for (IMDTransform transform : transforms) {
            v = transform.transform(v);
        }

        return index < v.getDim() ? (int) v.getElem(index) : 0;
    }
}
