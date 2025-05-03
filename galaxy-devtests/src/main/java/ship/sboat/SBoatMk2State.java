package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.simsilica.lemur.geom.DMesh;
import jme3utilities.MyMesh;
import jme3utilities.mesh.Octahedron;
import jme3utilities.mesh.Octasphere;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class SBoatMk2State extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  private Material material;
  
  public SBoatMk2State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    material = new ShowNormalsMaterial(app.getAssetManager());
    
    middle();
    front();
  }
  
  private void middle() {
    Mesh mesh = new Cylinder(2, 16, 10, 60);
    
    Geometry middle = new Geometry("middle", new FlatShadedMesh(mesh));
    middle.setMaterial(material);
    
    middle.scale(1, 0.5f, 1);
    
    scene.attachChild(middle);
  }
  
  private void front() {
    Mesh source = new Octasphere(2, 10f);
    Mesh deformed = new DMesh(source, (v, n) -> {
      if (v.z < 0)
        v.z = 0;
    });
    
    Geometry front = new Geometry("front", new FlatShadedMesh(deformed));
    front.setMaterial(material);
    front.scale(1, 0.5f, 1);
    
    front.move(0, 0, 30);
    
    scene.attachChild(front);
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
