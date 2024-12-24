package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CameraState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(CameraState.class);

  private ChaseCamera chaseCamera;

  @Override
  protected void initialize(Application app) {
    app.getCamera().setLocation(new Vector3f(82.721375f, 78.605225f, 228.47423f));
    app.getCamera().setRotation(new Quaternion(-0.026715863f, 0.972733f, -0.1786568f, -0.14545964f));

    chaseCamera = new ChaseCamera(app.getCamera(), app.getInputManager());
    
    chaseCamera.setDefaultHorizontalRotation(-FastMath.HALF_PI);
    chaseCamera.setDefaultDistance(48f);
    chaseCamera.setMinDistance(16f);
    chaseCamera.setMaxDistance(128f);
    chaseCamera.setInvertVerticalAxis(true);
    chaseCamera.setDefaultVerticalRotation(15f * FastMath.DEG_TO_RAD);
  }

  @Override
  protected void cleanup(Application app) {
  }

  @Override
  protected void onEnable() {
  }

  @Override
  protected void onDisable() {
  }

  public void follow(Spatial target) {
    logger.debug("setting camera to follow = {}", target);

    BoundingBox targetBound = (BoundingBox) target.getWorldBound().clone();

    Vector3f targetDirection = target.getWorldRotation().mult(Vector3f.UNIT_Z);
    logger.debug("target direction = {}", targetDirection);

    Vector3f offset = targetDirection.negate().mult(targetBound.getZExtent() * 8f);
    offset.addLocal(0, targetBound.getYExtent() * 2f, 0);
    logger.debug("camera offset = {}", offset);

    Vector3f cameraLocation = target.getWorldTranslation().add(offset);
    logger.debug("camera location {}", cameraLocation);

    getApplication().getCamera().setLocation(cameraLocation);
    getApplication().getCamera().lookAtDirection(targetDirection, Vector3f.UNIT_Y);
  }

  public void chase(Spatial target) {
    logger.debug("setting camera to chase {}", target);
    target.addControl(chaseCamera);
    
    Vector3f upVector = target.getWorldRotation().mult(Vector3f.UNIT_Y);
    logger.debug("target up vector = {}", upVector);
    chaseCamera.setUpVector(upVector);
    
    // TODO distance and angle from target bound
  }
}
