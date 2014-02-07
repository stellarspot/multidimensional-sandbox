/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.IMDList;
import multidimensional.datatype.IMDProperties;
import multidimensional.mathematics.IMDVector;

/**
 *
 * @author stellarspot
 */
public interface IMDCameraElem {

    IMDVector[] getVectors();
    IMDList<Hull> getHulls();
    IMDProperties getProperties();

    public static interface Hull {

        IMDShapeElem.HullType getType();
        int[] getIndices();
        IMDProperties getProperties();
    }
}
