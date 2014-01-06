/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;
import multidimensional.mathematics.ICMDVector;

/**
 *
 * @author stellarspot
 */
public interface IMDShapeElem {

    ICMDVector[] getVectors();

    ICMDList<Vertex> getVertices();
    ICMDList<Segment> getSegments();

    interface Vertex {

        double getRadius();
        //ICMDVector getCoordinats();
        int getCoordinats();
    }

    interface Segment {

        int getVertex1();
        int getVertex2();
    }
}
