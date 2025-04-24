package editor;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import materials.ShowNormalsMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.slf4j.LoggerFactory.getLogger;

public class HullState extends BaseAppState {
  
  private static final Logger logger = getLogger(HullState.class);
  
  private final Node scene = new Node("scene");
  private final float cellExtent;
  
  public HullState(Node rootNode, float cellExtent) {
    this.cellExtent = cellExtent;
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application application) {
  
  }
  
  @Override
  protected void cleanup(Application application) {
  
  }
  
  @Override
  protected void onEnable() {
  
  }
  
  @Override
  protected void onDisable() {
  
  }
  
  public void updateContainers(float xExtent, float yExtent, float zExtent) {
    scene.detachAllChildren();
    
    Mesh mesh = new Cylinder(2, 6, cellExtent, 1.5f * 2f * zExtent, true);
    Geometry geometry = new Geometry("hull", mesh);
    geometry.setMaterial(new ShowNormalsMaterial(getApplication().getAssetManager()));
    
    scene.attachChild(geometry);
  }
}
