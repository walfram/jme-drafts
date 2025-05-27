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
import jme3utilities.MyMesh;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class SBoatMk8State extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public SBoatMk8State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    Mesh mesh = new Cylinder(2, 4, 5, 25, 15, true, false);
    
    MyMesh.rotate(mesh, new Quaternion().fromAngleNormalAxis(FastMath.QUARTER_PI, Vector3f.UNIT_Z));
    
    Geometry chunk = new Geometry("chunk", new FlatShadedMesh(mesh));
    chunk.setMaterial(material);
    
    chunk.scale(1, 0.25f, 1);
    
    scene.attachChild(chunk);
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
