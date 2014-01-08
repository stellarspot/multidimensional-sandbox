/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.java2d.camera;

import java.awt.Component;
import multidimensional.shape.IMDCamera;

/**
 *
 * @author stellarspot
 */
public interface IMDSwingCamera extends IMDCamera{

    void setCenterX(int center);
    void setCenterY(int center);
    Component getComponent();
}
