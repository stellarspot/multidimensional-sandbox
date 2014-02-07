/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.ICMDProperties;
import multidimensional.mathematics.ICMDVector;

/**
 *
 * @author stellarspot
 */
public class MDShapeElem implements IMDShapeElem {

    protected ICMDVector[] vectors;
    protected ICMDList<Hull> hulls;
    protected ICMDProperties properties = new MDShapeProperties();

    protected void init(ICMDVector... vectors) {
        this.vectors = vectors;
        this.hulls = new CMDList<>();
    }

    protected void init(int vectorSize) {
        this.vectors = new ICMDVector[vectorSize];
        this.hulls = new CMDList<>();
    }

    @Override
    public ICMDVector[] getVectors() {
        return vectors;
    }

    @Override
    public ICMDList<IMDShapeElem.Hull> getHulls() {
        return hulls;
    }

    @Override
    public ICMDProperties getProperties() {
        return properties;
    }

    public static class ShapeHull implements Hull {

        HullType type;
        int[] indices;
        ICMDProperties properties = new MDShapeProperties();

        public ShapeHull(HullType type, int... indices) {
            this.type = type;
            this.indices = indices;
        }

        @Override
        public HullType getType() {
            return type;
        }

        @Override
        public int[] getIndices() {
            return indices;
        }

        @Override
        public ICMDProperties getProperties() {
            return properties;
        }
    }

    public static class ShapeVertex extends ShapeHull {

        public static final double DEFAULT_RADIUS = 5;

        public ShapeVertex(int coordinats) {
            this(DEFAULT_RADIUS, coordinats);
        }

        public ShapeVertex(double radius, int index) {
            super(HullType.VERTICES, index);
            getProperties().put(MDShapeProperties.Name.RADIUS, radius);
        }
    }

    public static class ShapeSegment extends ShapeHull {

        public ShapeSegment(int vertex1, int vertex2) {
            super(HullType.SEGMENTS, vertex1, vertex2);
        }
    }
}
