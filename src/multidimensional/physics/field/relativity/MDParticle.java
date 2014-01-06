/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.field.relativity;

import multidimensional.mathematics.IMDVector;

/**
 *
 * @author stellarspot
 */
public class MDParticle {

    double radius;
    double charge;
    IMDVector coordinats;

    public MDParticle(double charge, IMDVector coordinats) {
        this(charge, 30, coordinats);
    }
    public MDParticle(double charge, double radius, IMDVector coordinats) {
        this.charge = charge;
        this.coordinats = coordinats;
        this.radius = radius;
    }

    public double getCharge() {
        return charge;
    }

    public double getRadius() {
        return radius;
    }

    public IMDVector getCoordinats() {
        return coordinats;
    }
}
