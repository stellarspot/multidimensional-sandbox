/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.ICMDList;
import multidimensional.datatype.IMDProperties;
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
        int getCoordinats();

        IMDProperties getProperties();
    }

    interface Segment {

        int getVertex1();
        int getVertex2();
        
        IMDProperties getProperties();
    }
}
