/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDList;
import multidimensional.mathematics.IMDInvertibleTransform;

/**
 *
 * @author stellarspot
 */
public interface IMDCamera {

    ICMDList<IMDInvertibleTransform> getTransforms();
    void draw(IMDList<IMDCameraElem> elems);

    void addListener(IMDCameraListener listener);
}
