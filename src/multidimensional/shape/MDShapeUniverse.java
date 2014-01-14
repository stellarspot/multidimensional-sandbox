/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.CMDList;
import multidimensional.datatype.CMDObservableList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.ICMDObservableList;
import multidimensional.datatype.IMDList;
import multidimensional.datatype.IMDProperties;
import multidimensional.datatype.IMDStack;
import multidimensional.datatype.MDStack;
import multidimensional.mathematics.ICMDVector;
import multidimensional.mathematics.IMDTransform;
import multidimensional.mathematics.IMDVector;

/**
 *
 * @author stellarspot
 */
public class MDShapeUniverse implements IMDShapeUniverse {

    int dimension;
    IMDShape root;
    ICMDObservableList<IMDCamera> cameras = new CMDObservableList<>();
    protected ICMDList<IMDCameraElem> cameraElems = new CMDList<IMDCameraElem>();

    public MDShapeUniverse(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    public IMDShape getShape() {
        return root;
    }

    public void setShape(IMDShape shape) {
        this.root = shape;
    }

    @Override
    public ICMDObservableList<IMDCamera> getCameras() {
        return cameras;
    }

    @Override
    public void evaluate() {
        //System.out.println("Evaluate");
        cameraElems.clear();
        //shapeElems.clear();

        IMDStack<ICMDList<IMDTransform>> transformsStack = new MDStack<ICMDList<IMDTransform>>();
        parse(root, transformsStack);

        IMDList<IMDCameraElem> elems = cameraElems.getIMDList();

        for (IMDCamera camera : cameras) {
            camera.draw(elems);
        }
    }

    protected void parse(IMDShape shape, IMDStack<ICMDList<IMDTransform>> transformsStack) {


        transformsStack.push(shape.getTransforms());
        for (IMDShapeElem elem : shape.getElems()) {
            cameraElems.addTail(new MDCameraElem(elem, transformsStack));
        }

        for (IMDShape s : shape.getShapes()) {
            parse(s, transformsStack);
        }

        transformsStack.pop();
    }

    static class MDCameraElem implements IMDCameraElem {

        IMDShapeElem shapeElem;
        IMDVector[] cameraVectors;
        IMDList<IMDCameraElem.Vertex> cameraVertices;
        IMDList<IMDCameraElem.Segment> cameraSegments;

        public MDCameraElem(IMDShapeElem shapeElem, IMDStack<ICMDList<IMDTransform>> transformsStack) {
            this.shapeElem = shapeElem;

            //IMDShapeElem.Vertex[] vertices = shapeElem.getVertices();
            ICMDVector[] vectors = shapeElem.getVectors();

            //cameraVertices = new IMDCameraElem.Vertex[vectors.length];
            cameraVectors = new IMDVector[vectors.length];

            for (int i = 0; i < vectors.length; i++) {
                cameraVectors[i] = parseVertex(vectors[i].getIMDVector(), transformsStack);
            }

            ICMDList<IMDCameraElem.Vertex> v = new CMDList<>();

            for (IMDShapeElem.Vertex vertex : shapeElem.getVertices()) {
                v.addTail(new CameraVertex(vertex));
            }

            ICMDList<IMDCameraElem.Segment> s = new CMDList<>();

            for (IMDShapeElem.Segment segment : shapeElem.getSegments()) {
                s.addTail(new CameraSegment(segment));
            }

            cameraVertices = v.getIMDList();
            cameraSegments = s.getIMDList();
        }

        protected IMDVector parseVertex(IMDVector vector, IMDStack<ICMDList<IMDTransform>> transformsStack) {

//            if (vertex == null || vertex.getCoordinats() == null) {
//                System.out.println("VERTEX: " + vertex);
//            }

//            if (vertex == null) {
//                return;
//            }

            IMDVector coordinats = vector;
            for (ICMDList<IMDTransform> transforms : transformsStack) {
                for (IMDTransform transform : transforms) {
                    coordinats = transform.transform(coordinats);
                }
            }

            return coordinats;
        }

        @Override
        public IMDVector[] getVectors() {
            return cameraVectors;
        }

        @Override
        public IMDList<IMDCameraElem.Vertex> getVertices() {
            return cameraVertices;
        }

        @Override
        public IMDList<IMDCameraElem.Segment> getSegments() {
            return cameraSegments;
        }

        @Override
        public IMDProperties getProperties() {
            return shapeElem.getProperties().getIMDProperties();
        }
        
        static class CameraVertex implements IMDCameraElem.Vertex {

            double radius;
            int coordinats;
            IMDProperties properties;

            public CameraVertex(IMDShapeElem.Vertex vertex) {
                this.radius = vertex.getRadius();
                this.coordinats = vertex.getCoordinats();
                this.properties = vertex.getProperties().getIMDProperties();
            }

            public double getRadius() {
                return radius;
            }

            public int getCoordinats() {
                return coordinats;
            }

            public IMDProperties getProperties() {
                return properties;
            }

        }

        static class CameraSegment implements IMDCameraElem.Segment {

            int vertex1;
            int vertex2;
            IMDProperties properties;

            public CameraSegment(IMDShapeElem.Segment segment) {
                vertex1 = segment.getVertex1();
                vertex2 = segment.getVertex2();
                this.properties = segment.getProperties().getIMDProperties();
            }

            @Override
            public int getVertex1() {
                return vertex1;
            }

            @Override
            public int getVertex2() {
                return vertex2;
            }

            public IMDProperties getProperties() {
                return properties;
            }
        }
    }
}
