/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;

/**
 *
 * @author stellarspot
 */
public interface IMDShapeUniverse {

    int getDimension();
    IMDShape getShape();
    void setShape(IMDShape shape);

    ICMDList<IMDCamera> getCameras();

    void evaluate();
}
