/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.IMDList;
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
        //IMDVector getCoordinats();
        int getCoordinats();
    }

    public static interface Segment {

        int getVertex1();
        int getVertex2();
    }
}
