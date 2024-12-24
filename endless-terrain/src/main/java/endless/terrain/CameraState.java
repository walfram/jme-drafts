package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.input.InputState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CameraState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(CameraState.class);

  private Spatial target;

  @Override
  protected void initialize(Application app) {
    app.getCamera().setLocation(new Vector3f(82.721375f, 78.605225f, 228.47423f));
    app.getCamera().setRotation(new Quaternion(-0.026715863f, 0.972733f, -0.1786568f, -0.14545964f));
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

  @Override
  public void update(float tpf) {
    if (target == null) {
      return;
    }

    Vector3f targetForward = target.getWorldRotation().mult(Vector3f.UNIT_Z);
    Vector3f offset = targetForward.mult(32f).negate();

    Vector3f targetUp = target.getWorldRotation().mult(Vector3f.UNIT_Y);
    offset.addLocal(targetUp.mult(5));

    getApplication().getCamera().setLocation(target.getWorldTranslation().add(offset));
    getApplication().getCamera().lookAtDirection(targetForward, targetUp);
  }

  public void updateCursorVisibility(InputState state) {
    logger.debug("updateCursorVisibility state = {}", state);
    getApplication().getInputManager().setCursorVisible(state == InputState.Off);
  }

  public void attachTo(Spatial target) {
    this.target = target;
    logger.debug("attaching camera to {}", target);
  }
}
