/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.datatype;

/**
 *
 * @author Alexander
 */
public interface IMDBaseProperties {

    IMDList<IMDName> getNames();
    Object get(ICMDProperties.IMDName name);

    interface IMDName {
    }
}
