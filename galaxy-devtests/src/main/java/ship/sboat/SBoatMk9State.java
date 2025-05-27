package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class SBoatMk9State extends BaseAppState {
  
  private static final float cellExtent = 4f;
  private final Node scene = new Node("scene");
  
  public SBoatMk9State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    Mesh source = new Cylinder(2, 6, 3f * cellExtent, 2f * cellExtent, true);
    
    Deformation deformation = (v, n) -> {
      if (v.y > 0) {
        v.y *= 2f;
      }
    };
    
    Geometry block = new Geometry("block", new FlatShadedMesh(new DMesh(source, deformation)));
    block.setMaterial(material);
    
    scene.attachChild(block);
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
