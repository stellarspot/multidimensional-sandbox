/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.mathematics;

/**
 *
 * @author stellarspot
 */
public class MDAxesRotation implements IMDInvertibleTransform {

    int n;
    int m;
    double angle;
    double cos;
    double sin;

    public MDAxesRotation(int n, int m) {
        this(n, m, 0.0);
    }

    public MDAxesRotation(int n, int m, double angle) {
        this.n = n;
        this.m = m;
        this.angle = angle;

        cos = Math.cos(angle);
        sin = Math.sin(angle);
    }

    public void setAngle(double angle) {
        this.angle = angle;

        cos = Math.cos(angle);
        sin = Math.sin(angle);
    }

    public void addAngle(double deltaAngle) {
        setAngle(angle + deltaAngle);
    }

    public IMDVector transform(IMDBaseVector vector) {
        return transform(cos, sin, n, m, vector);
    }

    @Override
    public IMDVector inverse(IMDBaseVector vector) {
        return transform(cos, -sin, n, m, vector);
    }

    private static IMDVector transform(double cos, double sin, int n, int m, IMDBaseVector vector) {
        double[] elems = vector.getElemsCopy();

        double x1 = elems[n];
        double y1 = elems[m];

        double x2 = x1 * cos + y1 * sin;
        double y2 = -x1 * sin + y1 * cos;

        elems[n] = x2;
        elems[m] = y2;

        return new MDVector(elems);

    }

    public static MDAxesRotation[] getRotations(int dim) {
        return getRotations(dim, 0);
    }

    public static MDAxesRotation[] getRotations(int dim, double angle) {

        MDAxesRotation[] rotations = new MDAxesRotation[dim * (dim - 1) / 2];

        int i = 0;

        for (int n = 0; n < dim; n++) {
            for (int m = 0; m < n; m++) {
                rotations[i] = new MDAxesRotation(n, m, angle);
                i++;
            }
        }

        return rotations;
    }
}
