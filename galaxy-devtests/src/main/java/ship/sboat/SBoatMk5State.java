package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class SBoatMk5State extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public SBoatMk5State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    Mesh frontMesh = new FlatShadedMesh(new Cylinder(2, 8, 5, 10, 10, true, false));
    Geometry front = new Geometry("front", frontMesh);
    front.setMaterial(material);
    scene.attachChild(front);
    front.move(0, 0, 25);
    
    Mesh middleMesh = new FlatShadedMesh(new Cylinder(2, 8, 10, 40));
    Geometry middle = new Geometry("middle", middleMesh);
    middle.setMaterial(material);
    scene.attachChild(middle);
    
    Mesh backMesh = new FlatShadedMesh(new Cylinder(2, 8, 10, 5, 2.5f, true, false));
    Geometry back = new Geometry("back", backMesh);
    back.setMaterial(material);
    scene.attachChild(back);
    back.move(0, 0, -21.25f);
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
