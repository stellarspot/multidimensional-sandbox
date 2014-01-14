/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.datatype;

/**
 *
 * @author stellarspot
 */
public interface ICMDProperties extends IMDBaseProperties {

    void put(IMDName name, Object value);
    IMDProperties getIMDProperties();
}
