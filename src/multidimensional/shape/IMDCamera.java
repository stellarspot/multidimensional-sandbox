/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDList;
import multidimensional.mathematics.IMDTransform;

/**
 *
 * @author stellarspot
 */
public interface IMDCamera {

    ICMDList<IMDTransform> getTransforms();
    void draw(IMDList<IMDCameraElem> elems);
}
