/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.shape;

import multidimensional.datatype.CMDList;
import multidimensional.datatype.ICMDList;
import multidimensional.datatype.ICMDProperties;
import multidimensional.datatype.IMDBaseProperties;
import multidimensional.datatype.IMDList;
import multidimensional.datatype.IMDProperties;

/**
 *
 * @author stellarspot
 */
public class MDShapeProperties implements ICMDProperties {

    IMDColor color = null;
    Boolean fill;
    ICMDList<IMDName> names = new CMDList<>();

    @Override
    public IMDList<IMDName> getNames() {
        return names.getIMDList();
    }

    @Override
    public Object get(IMDName name) {

        if (name instanceof Name) {
            switch ((Name) name) {
                case COLOR:
                    return color;
                case FILL:
                    return fill;
            }
        }

        return null;
    }

    @Override
    public void put(IMDName name, Object value) {
        if (name instanceof Name) {
            switch ((Name) name) {
                case COLOR:
                    if (color == null) {
                        names.addTail(Name.COLOR);
                    }
                    color = (IMDColor) value;
                    break;
                case FILL:
                    if (fill == null) {
                        names.addTail(Name.FILL);
                    }
                    fill = (Boolean) value;
                    break;

            }
        }
    }

    public enum Name implements IMDName {

        COLOR,
        FILL,
    }

    @Override
    public IMDProperties getIMDProperties() {
        return new CameraProperties(this);
    }

    static class CameraProperties implements IMDProperties {

        IMDList<IMDName> names;
        Object[] values;

        public CameraProperties(IMDBaseProperties properties) {
            this.names = properties.getNames();
            values = new Object[this.names.getSize()];
            int i = 0;
            for (IMDName name : names) {
                values[i] = properties.get(name);
                i++;
            }
        }

        @Override
        public IMDList<IMDName> getNames() {
            return names;
        }

        @Override
        public Object get(IMDName name) {
            int i = 0;
            for (IMDName n : names) {
                if (n == name) {
                    return values[i];
                }
                i++;
            }
            return null;
        }
    }
}
