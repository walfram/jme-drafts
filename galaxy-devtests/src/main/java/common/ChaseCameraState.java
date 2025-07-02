package common;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import debug.QuickChaseCamera;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ChaseCameraState extends BaseAppState {
  
  private static final Logger logger = getLogger(ChaseCameraState.class);
  
  private QuickChaseCamera quickChaseCamera;
  
  @Override
  protected void initialize(Application app) {
    quickChaseCamera = new QuickChaseCamera(app.getCamera(), app.getInputManager());
    
    Node rootNode = ((SimpleApplication) app).getRootNode();
    logger.debug("rootNode: {}", rootNode);
    
    quickChaseCamera.attachTo(rootNode);
  }
  
  public void chase(Node target) {
    quickChaseCamera.attachTo(target);
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
}
