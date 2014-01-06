/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.field.relativity;

import multidimensional.shape.MDShapeUniverse;

/**
 *
 * @author stellarspot
 */
public class MDRelativityUniverse extends MDShapeUniverse {

    int N = 20;
    int delta = 15;
    MDRelativityLattice lattice; // = new MDRelativityLattice(N, N, N, N);

    public MDRelativityUniverse(MDParticle... particles) {
        super(3);
        lattice = new MDRelativityLattice(N, delta, particles);
    }

    @Override
    public void evaluate() {
        lattice.evaluate();
        setShape(lattice.getShape());
        // update
        super.evaluate();
    }
}
