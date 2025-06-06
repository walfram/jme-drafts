package debug;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class QuickChaseCamera {

  private final Camera camera;
  private final InputManager inputManager;
  
  private ChaseCamera chaseCamera;

  public QuickChaseCamera(Camera camera, InputManager inputManager) {
    this.camera = camera;
    this.inputManager = inputManager;
  }

  public void attachTo(Node target) {
    chaseCamera = new ChaseCamera(camera, target, inputManager);
    chaseCamera.setDragToRotate(true);
    
    chaseCamera.setInvertVerticalAxis(true);
    chaseCamera.setUpVector(Vector3f.UNIT_Y);
    
    chaseCamera.setDefaultDistance(64);
    chaseCamera.setMaxDistance(256);
    
    chaseCamera.setZoomSensitivity(8);
    
    chaseCamera.setDefaultHorizontalRotation(FastMath.QUARTER_PI);
    
    chaseCamera.setMinVerticalRotation(-FastMath.QUARTER_PI);
  }
}
