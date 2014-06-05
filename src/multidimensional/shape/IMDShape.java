/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;
import multidimensional.mathematics.IMDTransform;

/**
 *
 * @author stellarspot
 */
public interface IMDShape {

    ICMDList<IMDShape> getShapes();
    ICMDList<IMDShapeElem> getElems();

    ICMDList<IMDTransform> getTransforms();

}
