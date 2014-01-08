/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.physics.field.relativity;

import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.mathematics.CMDVector;
import multidimensional.mathematics.ICMDVector;
import multidimensional.mathematics.IMDBaseVector;
import multidimensional.mathematics.IMDTransform;
import multidimensional.mathematics.IMDVector;
import multidimensional.mathematics.MDVector;
import multidimensional.shape.IMDShapeElem;
import multidimensional.shape.MDShape;
import multidimensional.shape.MDShapeElem;

/**
 *
 * @author stellarspot
 */
public class MDRelativityLattice {

    int Nt, Nx, Ny, Nz;
    double dt, dx, dy, dz;
    MDRelativityLatticeCell[][][] latticeNext, latticeCurrent, latticePrevous;
    ICMDList<MDLatticeParticle> particles = new CMDList<>();
    int nT = 0;

    //public MDRelativityLattice(int Nt, int Nx, int Ny, int Nz, MDParticle... particles) {
    public MDRelativityLattice(int N, double delta, MDParticle... particles) {

        this.Nt = N;
        this.Nx = N;
        this.Ny = N;
        this.Nz = N;
        this.dx = delta;
        this.dy = delta;
        this.dz = delta;
        //cells = new MDRelativityLatticeCell[Nt][twice(Nx)][twice(Ny)][twice(Nz)];
        latticeCurrent = new MDRelativityLatticeCell[twice(Nx)][twice(Ny)][twice(Nz)];

        for (MDParticle particle : particles) {
            this.particles.addHead(new MDLatticeParticle(particle));
        }
        init();
    }

    void init() {

        for (int i = -Nx; i <= Nx; i++) {
            for (int j = -Nx; j <= Ny; j++) {
                int k = 0;
                MDRelativityLatticeCell cell = new MDRelativityLatticeCell();
                latticeCurrent[indexToLattice(i, Nx)][indexToLattice(j, Ny)][indexToLattice(k, Nz)] = cell;
                //System.out.println("--- " + particles.getSize());
                for (MDLatticeParticle p : particles) {
                    double d = distance(i, j, k, p.nX, p.nY, p.nZ);
                    //System.out.println("distance: " + d);

                    //MDFourPotential potential = new MDFourPotential(d, 0, 0, 0);
                    //cell.fourPotential =
                    //System.out.println("set distance: " + d);
                    double potential = cell.fourPotential.getElem(0);
                    //System.out.println("potential: " + potential);
                    if (d != 0) {
                        double A = 1e3;
                        double charge = p.particle.getCharge();
                        potential += A * charge / d;
                        cell.fourPotential.setElem(0, potential);
                    }


                }
            }
        }
    }

    static int twice(int N) {
        return 2 * N + 1;
    }

    static int getIndex(double x, double delta) {
        return (int) (x / delta);
    }

    static int indexToLattice(int n, int N) {
        return n + N;
    }

    static double getX(int n, double delta) {
        return n * delta;
    }

    double distance(int n1, int m1, int l1, int n2, int m2, int l2) {
        return distance(n2 - n1, m2 - m1, l2 - l1);
    }

    double sqr(double x) {
        return x * x;
    }

    double distance(int n, int m, int l) {
        return Math.sqrt(sqr(dx * n) + sqr(dy * m) + sqr(dz * l));
    }

    void evaluate() {
    }

    MDShape getShape() {

        MDShape shape = new MDShape();

        shape.getTransforms().addTail(new IMDTransform() {
            @Override
            public IMDVector transform(IMDBaseVector v) {
                return new MDVector(v.getElem(1), v.getElem(2), v.getElem(3), v.getElem(0));
            }
        });


        for (MDLatticeParticle particle : particles) {
            //System.out.println("particle: " + particle);
            shape.getElems().addTail(new ParticleShapeElem(particle));

        }

        shape.getElems().addTail(new LatticeShapeElem());

        return shape;
    }

    class ParticleShapeElem extends MDShapeElem {

        MDRelativityLattice.MDLatticeParticle particle;

        public ParticleShapeElem(MDRelativityLattice.MDLatticeParticle particle) {

            this.particle = particle;
            init(particle.getCoordinats());
            //vectors[0] = particle.getCoordinats();
            vertices.addTail(new MDShapeElem.ShapeVertex(particle.getRadius(), 0));
        }
    }

    class LatticeShapeElem extends MDShapeElem {

        public LatticeShapeElem() {
            int size = 2 * twice(Nx) * twice(Ny);
            //System.out.println("size: " + size + ", nx: " + Nx + ", ny: " + Ny);

            vectors = new ICMDVector[size];
            segments = new CMDList<>();
            vertices = new CMDList<>();

            int n = 0;

            for (int i = -Nx; i <= Nx; i++) {
                for (int j = -Ny; j <= Ny; j++) {
                    //for (int k = -Nz; j <= Nz; k++) {
                    //System.out.println("i: " + i + ", j: " + j);
                    int k = 0;
                    double x = getX(i, dx);
                    double y = getX(j, dy);
                    double z = getX(k, dz);

                    MDRelativityLatticeCell cell = latticeCurrent[indexToLattice(i, Nx)][indexToLattice(j, Ny)][indexToLattice(k, Nz)];

                    double potential = cell.fourPotential.getElem(0);

                    //System.out.println("potential: " + potential);

                    //System.out.println("x: " + x + ", y: " + y);

                    ICMDVector begin = new CMDVector(0, x, y, z);
                    ICMDVector end = new CMDVector(0, x, y, z + potential);

//                    vertices[n] = new ShapeVertex(1, begin);
//                    vertices[n + 1] = new ShapeVertex(2, end);
                    vectors[n] = begin;
                    vectors[n + 1] = end;

                    vertices.addTail(new ShapeVertex(1.0, n), new ShapeVertex(2.0, n + 1));

                    segments.addTail(new ShapeSegment(n, n + 1));

                    n += 2;
                }
            }

        }
    }

    class MDLatticeParticle {

        MDParticle particle;
        int nX;
        int nY;
        int nZ;

        public MDLatticeParticle(MDParticle particle) {
            this.particle = particle;
            IMDVector coordinats = particle.getCoordinats();
            double x = coordinats.getElem(1);
            double y = coordinats.getElem(2);
            double z = coordinats.getElem(3);

            nX = getIndex(x, dx);
            nY = getIndex(y, dy);
            nZ = getIndex(z, dz);

            System.out.println("particle: x: " + x + ", y: " + y);

            System.out.println("particle nx: " + nX + ", ny: " + nY);
        }

        double getRadius() {
            return particle.getRadius();
        }

        ICMDVector getCoordinats() {

            double x = getX(nX, dx);
            double y = getX(nY, dy);
            double z = getX(nZ, dz);

            return new CMDVector(0, x, y, z);
        }

        @Override
        public String toString() {
            return "particle radius: " + getRadius() + ", coordinats: " + getCoordinats();
        }
    }

    public static class MDRelativityLatticeCell {

        //MDFourPotential fourPotential = new MDFourPotential();
        ICMDVector fourPotential = new CMDVector(0, 0, 0, 0);
    }
}
