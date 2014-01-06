/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.mathematics.IMDTransform;

/**
 *
 * @author stellarspot
 */
public class MDShape implements IMDShape {

    protected ICMDList<IMDShape> shapes = new CMDList<>();
    protected ICMDList<IMDShapeElem> elems = new CMDList<>();
//    protected ICMDList<IMDShapeVertex> vertices = new CMDList<IMDShapeVertex>();
//    protected ICMDList<IMDShapeSegment> segments = new CMDList<IMDShapeSegment>();
    protected ICMDList<IMDTransform> transforms = new CMDList<>();

    @Override
    public ICMDList<IMDShape> getShapes() {
        return shapes;
    }

    @Override
    public ICMDList<IMDShapeElem> getElems() {
        return elems;
    }

//    @Override
//    public ICMDList<IMDShapeVertex> getVertices() {
//        return vertices;
//    }
//
//    @Override
//    public ICMDList<IMDShapeSegment> getSegments() {
//        return segments;
//    }

    public ICMDList<IMDTransform> getTransforms() {
        return transforms;
    }
}
