/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;
import multidimensional.datatype.ICMDProperties;
import multidimensional.mathematics.ICMDVector;

/**
 *
 * @author stellarspot
 */
public interface IMDShapeElem {

    ICMDVector[] getVectors();
    ICMDList<Hull> getHulls();
    ICMDProperties getProperties();


    enum HullType{
        VERTICES,
        SEGMENTS,
    }

    interface Hull {

        HullType getType();
        int[] getIndices();
        ICMDProperties getProperties();
    }
}
