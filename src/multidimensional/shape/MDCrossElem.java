/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDProperties;
import multidimensional.mathematics.CMDVector;
import multidimensional.mathematics.ICMDVector;

/**
 *
 * @author stellarspot
 */
//public class MDCrossElem implements IMDShapeElem {
public class MDCrossElem extends MDShapeElem {

    int dim;
    double d;
    //ICMDVector[] vertices;
//    Vertex[] vertices;
//    ICMDList<Segment> segments = new CMDList<>();

    public MDCrossElem(int dim, double d) {
        this.dim = dim;
        this.d = d;

        int n = 0;
        //vertices = new Vertex[2 * dim];
        vectors = new ICMDVector[2 * dim];
        vertices = new CMDList<>();
        segments = new CMDList<>();


        for (int i = 0; i < dim; i++) {
            CMDVector coordinats1 = new CMDVector(dim);
            CMDVector coordinats2 = new CMDVector(dim);
            coordinats1.setElem(i, d);
            coordinats2.setElem(i, -d);

//            vertices[n] = new ShapeVertex(coordinats1);
//            vertices[n + 1] = new ShapeVertex(coordinats2);
            vectors[n] = coordinats1;
            vectors[n + 1] = coordinats2;
            vertices.addTail(new ShapeVertex(n), new ShapeVertex(n + 1));

            segments.addTail(new ShapeSegment(n));
            n += 2;
        }
    }

    static class ShapeSegment implements Segment {

        int vertex;
        IMDProperties properties = new MDShapeProperties();

        public ShapeSegment(int vertex) {
            this.vertex = vertex;
        }

        @Override
        public int getVertex1() {
            return vertex;
        }

        @Override
        public int getVertex2() {
            return vertex + 1;
        }

        @Override
        public IMDProperties getProperties() {
            return properties;
        }
    }
}
