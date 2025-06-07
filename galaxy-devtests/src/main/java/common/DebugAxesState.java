package common;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import jme3utilities.debug.AxesVisualizer;

public class DebugAxesState extends BaseAppState {
  
  private final Node scene = new Node("debug-axes-scene");
  
  public DebugAxesState() {
  }
  
  @Override
  protected void initialize(Application app) {
    ((SimpleApplication) app).getRootNode().attachChild(scene);
    
    AxesVisualizer axesVisualizer = new AxesVisualizer(app.getAssetManager(), 128, 1);
    scene.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);
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
