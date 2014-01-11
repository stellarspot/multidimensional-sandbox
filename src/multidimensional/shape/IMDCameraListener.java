/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

/**
 *
 * @author stellarspot
 */
public interface IMDCameraListener {

    void screenPress(ScreenEvent e);
    void screenRelease(ScreenEvent e);
    void screenDrag(ScreenEvent e);

    class ScreenEvent {

        double x;
        double y;

        public ScreenEvent(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}
