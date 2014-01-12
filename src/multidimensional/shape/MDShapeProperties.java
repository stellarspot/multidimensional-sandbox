/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.IMDProperties;

/**
 *
 * @author stellarspot
 */
public class MDShapeProperties implements IMDProperties {

    IMDColor color = MDColor.BLACK;

    @Override
    public Object get(IMDName name) {

        if (name instanceof Name) {
            switch ((Name) name) {
                case COLOR:
                    return color;
            }
        }

        return null;
    }

    @Override
    public void put(IMDName name, Object value) {
        if (name instanceof Name) {
            switch ((Name) name) {
                case COLOR:
                    this.color = (IMDColor) value;
            }
        }
    }

    public enum Name implements IMDName {

        COLOR,
    }
}
