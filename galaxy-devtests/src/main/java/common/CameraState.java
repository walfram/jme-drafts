package common;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class CameraState extends BaseAppState {
  private static final Logger logger = getLogger(CameraState.class);
  
  private final Vector3f location = new Vector3f();
  private final Vector3f viewTarget = new Vector3f();
  private final Vector3f up = new Vector3f(Vector3f.UNIT_Y);
  
  @Override
  protected void initialize(Application app) {
    FlyByCamera flyCam = ((SimpleApplication) app).getFlyByCamera();
    
    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(50);
    flyCam.setZoomSpeed(0);
    
    app.getCamera().setLocation(this.location);
    app.getCamera().lookAt(this.viewTarget, this.up);
//    app.getCamera().setRotation(new Quaternion(-0.030913286f, 0.9689057f, -0.18284884f, -0.16380624f));
    
    logger.debug("initialized camera");
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
  
  public void updateLocation(Vector3f location) {
    this.location.set(location);
  }
  
  public void lookAt(Vector3f target, Vector3f up) {
    this.viewTarget.set(target);
    this.up.set(up);
  }
}
