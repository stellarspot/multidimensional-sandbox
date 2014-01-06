/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multidimensional.java2d.camera;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import multidimensional.mathematics.IMDTransform;
import multidimensional.mathematics.MDAxesRotation;
import multidimensional.shape.IMDCamera;
import multidimensional.shape.IMDShapeUniverse;

/**
 *
 * @author stellarspot
 */
public class MDFrameJava2D extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    private static final int DELAY = 25;
    //private static final int DELAY = 100;
    private IMDSwingCamera camera;
    private IMDShapeUniverse universe;
    private volatile boolean paused = false;
    private static final double DELTA_ANGLE = 0.5 * 2 * Math.PI / 360;
    private static final double PLUS_ANGLE = DELTA_ANGLE / 15;
    private double deltaAngle = DELTA_ANGLE;

    private MDAxesRotation[] rotations = new MDAxesRotation[0];

    public MDFrameJava2D(IMDShapeUniverse universe) {
        this(new MDCameraJava2D(), universe);
    }

    public MDFrameJava2D(IMDSwingCamera camera, IMDShapeUniverse universe) {
        setTitle("MD Universe");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.camera = camera;
        this.universe = universe;
        universe.getCameras().addTail(camera);
        universe.evaluate();

        getContentPane().add(camera.getComponent());

        camera.getComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println("process key: '" + e.getKeyChar() + "'" + e.getKeyCode());

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_P:
                        paused = !paused;
                        break;
                    case KeyEvent.VK_EQUALS:
                        deltaAngle += PLUS_ANGLE;
                        //System.out.println("delta angle: " + deltaAngle);
                        break;
                    case KeyEvent.VK_MINUS:
                        deltaAngle -= PLUS_ANGLE;
                        //System.out.println("delta angle: " + deltaAngle);
                        break;
                }
            }
        });

        //setVisible(true);
        //animate();
    }

    public void addCameraTransforms(IMDTransform... transforms){
        for(IMDCamera camera: universe.getCameras()){
            camera.getTransforms().addTail(transforms);
        }
    }

    public void addCameraRotationTransforms(){
        rotations = MDAxesRotation.getRotations(universe.getDimension());
        addCameraTransforms(rotations);
    }

    public void animate() {


        paused = false;
        camera.getComponent().requestFocusInWindow();


        //final MDAxesRotation[] rotations = MDAxesRotation.getRotations(universe.getDimension());
        //System.out.println("rotations: " + rotations.length);
        //universe.getShape().getTransforms().addTail(rotations);

//        for(IMDCamera camera: universe.getCameras()){
//            camera.getTransforms().addTail(rotations);
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                double angle = 0;

                while (true) {
                    if (!paused) {
                        angle += deltaAngle;
                        //System.out.println("angle: " + angle);
                        for (int i = 0; i < rotations.length; i++) {
                            rotations[i].setAngle(angle);
                        }

                        universe.evaluate();
                    }
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException ex) {
                    }

                }
            }
        }).start();
    }
}
