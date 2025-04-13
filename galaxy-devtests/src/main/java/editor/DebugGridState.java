package editor;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import debug.DebugGrid;

public class DebugGridState extends BaseAppState {
  
  private final Node scene = new Node("debug-grid-scene");
  private final float cellExtent;
  
  public DebugGridState(Node rootNode, float cellExtent) {
    rootNode.attachChild(scene);
    this.cellExtent = cellExtent;
  }
  
  @Override
  protected void initialize(Application app) {
    new DebugGrid(app.getAssetManager(), cellExtent, 32).attachTo(scene);
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
