/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.datatype;

/**
 *
 * @author stellarspot
 */
public interface IMDProperties {

    Object get(IMDName name);

    void put(IMDName name, Object value);

    interface IMDName {
    }
}
