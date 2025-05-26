package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import jme3utilities.MyMesh;
import jme3utilities.mesh.Dodecahedron;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

public class SBoatMk6State extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public SBoatMk6State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    Mesh hullMesh = new BeveledBox(4f, 3f, 15f, 1f);
    Geometry geometry = new Geometry("hull", new FlatShadedMesh(hullMesh));
    geometry.setMaterial(material);
    scene.attachChild(geometry);
   
    Deformation bottomDeform = (v, n) -> {
      if (v.y < 0) {
        v.z *= 0.3f;
      }
    };
    
    Mesh bottomMesh = new DMesh(new Box(1.5f, 1.5f, 13f), bottomDeform);
    Geometry bottom = new Geometry("bottom", new FlatShadedMesh(bottomMesh));
    bottom.setMaterial(material);
    bottom.move(0, -4.6f, 0f);
    scene.attachChild(bottom);
    
    Deformation rightDeform = (v, n) -> {
      if (v.x > 0 && v.z > 0) {
        v.z = -3f;
      }
    };
    
    Mesh rightMesh = new DMesh(new Box(1.5f, 1.5f, 7f), rightDeform);
    Geometry right = new Geometry("right", new FlatShadedMesh(rightMesh));
    right.setMaterial(material);
    right.move(4f + 1.5f, 0, 7f);
    scene.attachChild(right);
    
    Mesh leftMesh = rightMesh.clone();
    MyMesh.rotate(leftMesh, new Quaternion().fromAngleNormalAxis(FastMath.PI, Vector3f.UNIT_Z));
    Geometry left = new Geometry("left", new FlatShadedMesh(leftMesh));
    left.setMaterial(material);
    left.move(-4f -1.5f, 0, 7f);
    scene.attachChild(left);
    
    Mesh enginesMesh = new Cylinder(2, 8, 4f, 8f, 8f, true, false);
    Geometry engines = new Geometry("engines", new FlatShadedMesh(enginesMesh));
    engines.setMaterial(material);
    engines.rotate(0, 0, FastMath.QUARTER_PI * 0.5f);
    engines.move(0, 0, -15f - 4f);
    scene.attachChild(engines);
    
    app.getCamera().setLocation(new Vector3f(43.542095f, 19.069902f, 28.281128f));
    app.getCamera().setRotation(new Quaternion(-0.08171964f, 0.86680204f, -0.15133776f, -0.4680524f));
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
