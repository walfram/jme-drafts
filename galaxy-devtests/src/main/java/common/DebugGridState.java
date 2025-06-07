package common;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import debug.DebugGrid;

public class DebugGridState extends BaseAppState {
  
  private final Node scene = new Node("debug-grid-scene");
  private final float cellExtent;
  private final boolean additionalGrids;
  
  public DebugGridState(float cellExtent, boolean additionalGrids) {
    this.cellExtent = cellExtent;
    this.additionalGrids = additionalGrids;
  }
  
  @Override
  protected void initialize(Application app) {
    ((SimpleApplication) app).getRootNode().attachChild(scene);
    
    new DebugGrid(app.getAssetManager(), cellExtent, 32).attachTo(scene);
    
    if (additionalGrids) {
      Geometry top = new DebugGrid(app.getAssetManager(), cellExtent, 8).attachTo(scene);
      top.move(0, 2f * cellExtent, 0);
      top.getMaterial().setColor("Color", ColorRGBA.Red);
      
      Geometry bottom = new DebugGrid(app.getAssetManager(), cellExtent, 8).attachTo(scene);
      bottom.move(0, -2f * cellExtent, 0);
      bottom.getMaterial().setColor("Color", ColorRGBA.Green);
    }
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
