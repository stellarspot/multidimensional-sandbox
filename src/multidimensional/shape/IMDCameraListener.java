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

    void screenPress(MDScreenEvent e);

    void screenRelease(MDScreenEvent e);

    void screenDrag(MDScreenEvent e);

    void keyPress(MDKeyEvent e);

    class MDScreenEvent {

        int id;
        double x;
        double y;

        public MDScreenEvent(int id, double x, double y) {
            this.x = x;
            this.y = y;
        }

        public int getId() {
            return id;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    class MDKeyEvent {

        char keyChar;

        public MDKeyEvent(char c) {
            this.keyChar = c;
        }

        public char getChar() {
            return keyChar;
        }
    }
}
