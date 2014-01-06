/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.mathematics;

/**
 *
 * @author stellarspot
 */
public interface IMDVector extends IMDBaseVector{

    IMDVector add(IMDVector v);
    IMDVector sub(IMDVector v);
    IMDVector mul(double a);

    ICMDVector getCVector();

}
