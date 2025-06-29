package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import jme3utilities.mesh.Icosphere;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class SBoatMk7State extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public SBoatMk7State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Icosphere(1, 10f)));
    hull.setMaterial(material);
    scene.attachChild(hull);
    
    Geometry tube = new Geometry("tube", new FlatShadedMesh(new Cylinder(2, 8, 12, 18, false, true)));
    tube.setMaterial(material);
    scene.attachChild(tube);
    
    Geometry front = new Geometry("front", new FlatShadedMesh(new Cylinder(2, 8, 12, 24, 6, false, false)));
    front.setMaterial(material);
    scene.attachChild(front);
    front.move(0, 0, 6);
    
    Geometry back = new Geometry("back", new FlatShadedMesh(new Cylinder(2, 8, 24, 12, 6, false, false)));
    back.setMaterial(material);
    scene.attachChild(back);
    back.move(0, 0, -6);
    
    Geometry outer = new Geometry("outer", new FlatShadedMesh(new Cylinder(2, 8, 24, 6, false)));
    outer.setMaterial(material);
    scene.attachChild(outer);
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
