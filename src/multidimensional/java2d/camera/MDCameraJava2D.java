/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.java2d.camera;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDList;
import multidimensional.datatype.IMDProperties;
import multidimensional.mathematics.IMDInvertibleTransform;
import multidimensional.mathematics.IMDTransform;
import multidimensional.mathematics.IMDVector;
import multidimensional.mathematics.MDVector;
import multidimensional.shape.IMDCameraElem;
import multidimensional.shape.IMDCameraListener;
import multidimensional.shape.IMDCameraListener.ScreenEvent;
import multidimensional.shape.IMDColor;
import multidimensional.shape.MDShapeProperties;

/**
 *
 * @author stellarspot
 */
public class MDCameraJava2D implements IMDSwingCamera {

    IMDList<IMDCameraElem> elems;
    ICMDList<IMDInvertibleTransform> transforms = new CMDList<>();
    ICMDList<IMDCameraListener> listeners = new CMDList<>();
    private CameraCanvas canvas = new CameraCanvas();
    private volatile boolean isPainted = false;
    private int centerX;
    private int centerY;

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    @Override
    public void addListener(IMDCameraListener listener) {
        listeners.addTail(listener);
    }

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
    public ICMDList<IMDInvertibleTransform> getTransforms() {
        return transforms;
    }

    @Override
    public Component getComponent() {
        return canvas;
    }

    class CameraCanvas extends JComponent {

        public CameraCanvas() {

            MouseAdapter mouseAdapter = new CameraMouseListener();

            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
        }

        @Override
        public void paint(Graphics g) {

            isPainted = true;

            try {
                if (elems == null) {
                    return;
                }

                Graphics2D g2 = (Graphics2D) g;

                ////g2.translate(MDFrameJava2D.WIDTH / 2, MDFrameJava2D.HEIGHT / 2);
                g2.translate(centerX, centerY);
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

            for (IMDCameraElem.Segment segment : elem.getSegments()) {
                drawSegment(g, segment, vectors);
            }

            for (IMDCameraElem.Vertex vertex : elem.getVertices()) {
                drawVertex(g, vertex, vectors, elem.getProperties());
            }
        }

        void drawVertex(Graphics2D g, IMDCameraElem.Vertex vertex,
                IMDVector[] vectors, IMDProperties properties) {

            int r = (int) vertex.getRadius();
            int r2 = r * 2;
            IMDVector v = vectors[vertex.getCoordinats()];

            int x = getCoordinats(0, v);
            int y = getCoordinats(1, v);

            Color color = getColor(properties, vertex.getProperties());
            boolean fill = getFill(properties, vertex.getProperties());

            g.setColor(color);
            if (fill) {
                g.fillOval(x - r, y - r, r2, r2);
            } else {
                g.drawOval(x - r, y - r, r2, r2);
            }

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

    boolean getFill(IMDProperties baseProperties, IMDProperties properties) {
        Boolean fill = (Boolean) getProperty(MDShapeProperties.Name.FILL,
                baseProperties, properties);
        return fill == null ? true : fill;
    }

    Color getColor(IMDProperties baseProperties, IMDProperties properties) {
        return getColor((IMDColor) getProperty(MDShapeProperties.Name.COLOR,
                baseProperties, properties));
    }

    Color getColor(IMDColor color) {
        return color == null ? Color.BLACK : new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    Object getProperty(IMDProperties.IMDName name,
            IMDProperties baseProperties, IMDProperties properties) {
        Object property = properties.get(name);

        return property == null ? baseProperties.get(name) : property;
    }

    int getCoordinats(int index, IMDVector v) {

        for (IMDTransform transform : transforms) {
            v = transform.transform(v);
        }

        return index < v.getDim() ? (int) v.getElem(index) : 0;
    }

    class CameraMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            ScreenEvent event = getEvent(e);

            for (IMDCameraListener listener : listeners) {
                listener.screenPress(event);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            ScreenEvent event = getEvent(e);

            for (IMDCameraListener listener : listeners) {
                listener.screenRelease(event);
            }
        }

//        @Override
//        public void mouseMoved(MouseEvent e) {
//            System.out.println("Mouse moved!");
//        }
        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("Mouse Dragged!");
            ScreenEvent event = getEvent(e);

            for (IMDCameraListener listener : listeners) {
                listener.screenDrag(event);
            }
        }

        ScreenEvent getEvent(MouseEvent e) {
            double x = e.getX() - centerX;
            double y = e.getY() - centerY;

            IMDVector v = new MDVector(x, -y);
            //System.out.println("transforms: " + transforms.getSize());
            for (IMDInvertibleTransform t : transforms) {
                //System.out.println("v: " + v);
                v = t.inverse(v);
            }

            return new ScreenEvent(v.getElem(0), v.getElem(1));
        }
    }
}
