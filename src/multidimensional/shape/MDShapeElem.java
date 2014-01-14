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
    protected ICMDList<Vertex> vertices;
    protected ICMDList<Segment> segments;
    protected ICMDProperties properties = new MDShapeProperties();

    protected void init(ICMDVector... vectors) {
        this.vectors = vectors;
        this.vertices = new CMDList<>();
        this.segments = new CMDList<>();
    }

    protected void init(int vectorSize) {
        this.vectors = new ICMDVector[vectorSize];
        this.vertices = new CMDList<>();
        this.segments = new CMDList<>();
    }

    @Override
    public ICMDVector[] getVectors() {
        return vectors;
    }

    @Override
    public ICMDList<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public ICMDList<IMDShapeElem.Segment> getSegments() {
        return segments;
    }

    @Override
    public ICMDProperties getProperties() {
        return properties;
    }

    public static class ShapeVertex implements Vertex {

        public static final double DEFAULT_RADIUS = 5;
        double radius;
        int coordinats;
        ICMDProperties properties = new MDShapeProperties();

        public ShapeVertex(int coordinats) {
            this(DEFAULT_RADIUS, coordinats);
        }

        public ShapeVertex(double radius, int coordinats) {
            this.radius = radius;
            this.coordinats = coordinats;
        }

        @Override
        public double getRadius() {
            return radius;
        }

        @Override
        public int getCoordinats() {
            return coordinats;
        }

        @Override
        public ICMDProperties getProperties() {
            return properties;
        }
    }

    public static class ShapeSegment implements Segment {

        int vertex1;
        int vertex2;
        ICMDProperties properties = new MDShapeProperties();

        public ShapeSegment(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public int getVertex1() {
            return vertex1;
        }

        @Override
        public int getVertex2() {
            return vertex2;
        }

        @Override
        public ICMDProperties getProperties() {
            return properties;
        }
    }
}
