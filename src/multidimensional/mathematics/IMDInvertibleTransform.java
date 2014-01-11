/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.mathematics;

/**
 *
 * @author Alexander
 */
public interface IMDInvertibleTransform extends IMDTransform {

    IMDVector inverse(IMDBaseVector vector);
}
