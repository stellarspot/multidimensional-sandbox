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

    //Vertex[] getVertices();
    IMDVector[] getVectors();
    IMDList<Vertex> getVertices();
    IMDList<Segment> getSegments();

    public static interface Vertex {

        double getRadius();
        int getCoordinats();

        IMDProperties getProperties();
    }

    public static interface Segment {

        int getVertex1();
        int getVertex2();

        IMDProperties getProperties();
    }
}
