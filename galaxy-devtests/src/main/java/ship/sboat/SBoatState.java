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
import jme3utilities.MyMesh;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SBoatState extends BaseAppState {
  
  private static final Logger logger = getLogger(SBoatState.class);
  private final Node scene = new Node("scene");
  private Material material;
  
  public SBoatState(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    material = new ShowNormalsMaterial(app.getAssetManager());
    
    middle();
    front();
    back();
    
    scene.scale(1, 0.5f, 1);
  }
  
  private void back() {
    Mesh mesh = new Cylinder(2, 8, 10, 2, 20, true, false);
    
    Geometry back = new Geometry("back", new FlatShadedMesh(mesh));
    back.setMaterial(material);
    
    scene.attachChild(back);
    
    back.move(0, 0, -10f - 10f);
  }
  
  private void front() {
    Mesh mesh = new Dome(new Vector3f(), 4, 8, 10, false);
    MyMesh.rotate(mesh, new Quaternion().fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
    
    Geometry front = new Geometry("cone", new FlatShadedMesh(mesh));
    front.setMaterial(material);
//    logger.debug("front bound = {}", front.getWorldBound());
//    front.rotate(FastMath.HALF_PI, 0, 0);
    logger.debug("front bound = {}", front.getWorldBound());
    scene.attachChild(front);
    
    front.move(0, 0, 10f);
  }
  
  private void middle() {
    Mesh mesh = new Cylinder(2, 8, 10, 20);
    
    Geometry middle = new Geometry("middle", new FlatShadedMesh(mesh));
    middle.setMaterial(material);
    scene.attachChild(middle);
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
