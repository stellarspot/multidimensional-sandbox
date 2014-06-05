/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDProperties;
import multidimensional.mathematics.CMDVector;

/**
 *
 * @author stellarspot
 */
public class MDCrossElem extends MDShapeElem {

    int dim;
    double d;

    public MDCrossElem(int dim, double d) {
        this.dim = dim;
        this.d = d;

        int n = 0;
        init(2 * dim);

        for (int i = 0; i < dim; i++) {
            CMDVector coordinats1 = new CMDVector(dim);
            CMDVector coordinats2 = new CMDVector(dim);
            coordinats1.setElem(i, d);
            coordinats2.setElem(i, -d);

            vectors[n] = coordinats1;
            vectors[n + 1] = coordinats2;
            hulls.addTail(new ShapeVertex(n), new ShapeVertex(n + 1));
            hulls.addTail(new ShapeSegment(n));
            n += 2;
        }
    }

    static class ShapeSegment implements Hull {

        int index;
        ICMDProperties properties = new MDShapeProperties();

        public ShapeSegment(int index) {
            this.index = index;
        }

        @Override
        public HullType getType() {
            return HullType.SEGMENTS;
        }

        @Override
        public int[] getIndices() {
            return new int[]{index, index + 1};
        }

        @Override
        public ICMDProperties getProperties() {
            return properties;
        }
    }
}
