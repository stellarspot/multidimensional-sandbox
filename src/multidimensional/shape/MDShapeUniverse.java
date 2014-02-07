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
        cameraElems.clear();
        
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
        IMDList<IMDCameraElem.Hull> cameraHulls;

        public MDCameraElem(IMDShapeElem shapeElem, IMDStack<ICMDList<IMDTransform>> transformsStack) {
            this.shapeElem = shapeElem;

            ICMDVector[] vectors = shapeElem.getVectors();

            cameraVectors = new IMDVector[vectors.length];

            for (int i = 0; i < vectors.length; i++) {
                cameraVectors[i] = parseVertex(vectors[i].getIMDVector(), transformsStack);
            }

            ICMDList<IMDCameraElem.Hull> h = new CMDList<>();

            for (IMDShapeElem.Hull hull : shapeElem.getHulls()) {
                h.addTail(new CameraVertex(hull));
            }

            cameraHulls = h.getIMDList();
        }

        protected IMDVector parseVertex(IMDVector vector, IMDStack<ICMDList<IMDTransform>> transformsStack) {

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
        public IMDProperties getProperties() {
            return shapeElem.getProperties().getIMDProperties();
        }

        @Override
        public IMDList<Hull> getHulls() {
            return cameraHulls;
        }

        static class CameraVertex implements IMDCameraElem.Hull {

            IMDShapeElem.HullType type;
            int[] indices;
            IMDProperties properties;

            public CameraVertex(IMDShapeElem.Hull hull) {
                this.type = hull.getType();
                this.indices = hull.getIndices(); // Copy Elements!!
                this.properties = hull.getProperties().getIMDProperties();
            }

            public IMDProperties getProperties() {
                return properties;
            }

            @Override
            public IMDShapeElem.HullType getType() {
                return type;
            }

            @Override
            public int[] getIndices() {
                return indices;
            }
        }
    }
}
