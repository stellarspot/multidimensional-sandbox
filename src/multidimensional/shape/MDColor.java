/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

/**
 *
 * @author stellarspot
 */
public enum MDColor implements IMDColor {

    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    BLACK(0, 0, 0);
    private int red;
    private int green;
    private int blue;

    private MDColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public int getRed() {
        return red;
    }

    @Override
    public int getGreen() {
        return green;
    }

    @Override
    public int getBlue() {
        return blue;
    }
}
