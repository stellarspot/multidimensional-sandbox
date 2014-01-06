/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.field.relativity;

import multidimensional.mathematics.CMDVector;

/**
 *
 * @author stellarspot
 */
public class MDFourPotential extends CMDVector {

    public MDFourPotential() {
        this(0, 0, 0, 0);
    }

    public MDFourPotential(double t, double x, double y, double z) {
        super(t, x, y, z);
    }
    //double potential;
}
