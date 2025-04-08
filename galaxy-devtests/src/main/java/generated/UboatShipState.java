package generated;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import jme3utilities.mesh.Octasphere;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class UboatShipState extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public UboatShipState(Node rootNode) {
    // rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    
    Mesh mesh = new FlatShadedMesh(new Octasphere(2, 1));
    
    Geometry geometry = new Geometry("hull", mesh);
    geometry.setMaterial(new ShowNormalsMaterial(app.getAssetManager()));
    
    scene.attachChild(geometry);
    
    geometry.scale(2, 2, 8);
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
