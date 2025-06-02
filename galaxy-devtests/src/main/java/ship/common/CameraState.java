package ship.common;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResult;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import java.util.Optional;

public class CameraState extends BaseAppState {
  @Override
  protected void initialize(Application app) {
    FlyByCamera flyCam = ((SimpleApplication) app).getFlyByCamera();
    
    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(50);
    flyCam.setZoomSpeed(0);
    
    app.getCamera().setLocation(new Vector3f(33.137505f, 35.385365f, 89.41703f));
    app.getCamera().setRotation(new Quaternion(-0.030913286f, 0.9689057f, -0.18284884f, -0.16380624f));
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
  
  public void centerOn(Vector3f centerOn) {
//    Optional<CollisionResult> collision = getState(CollisionState.class).cameraCollision();
//
//    if (collision.isEmpty())
//      return;
//
//    Vector3f offset = centerOn.subtract(collision.get().getContactPoint());
//
//    Vector3f updated = getApplication().getCamera().getLocation().add(offset);
//    getApplication().getCamera().setLocation(updated);
  }
}
