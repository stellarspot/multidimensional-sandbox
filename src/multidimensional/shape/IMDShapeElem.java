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

    ICMDList<Vertex> getVertices();
    ICMDList<Segment> getSegments();

    ICMDProperties getProperties();

    interface Vertex {

        double getRadius();
        int getCoordinats();

        ICMDProperties getProperties();
    }

    interface Segment {

        int getVertex1();
        int getVertex2();
        
        ICMDProperties getProperties();
    }
}
