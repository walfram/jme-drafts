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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.slf4j.LoggerFactory.getLogger;

public class SBoatMk3State extends BaseAppState {
  
  private static final Logger logger = getLogger(SBoatMk3State.class);
  private final Node scene = new Node("scene");
  private Material material;
  
  public SBoatMk3State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    material = new ShowNormalsMaterial(app.getAssetManager());
//    material.getAdditionalRenderState().setWireframe(true);
    
    hull();
    bridge();
    engines();
  }
  
  private void engines() {
    
  }
  
  private void bridge() {
    Mesh mesh = new Cylinder(2, 8, 5, 10, 40, true, false);
    MyMesh.rotate(mesh, new Quaternion().fromAngleNormalAxis(FastMath.PI / 8f, Vector3f.UNIT_Z));
    
    Geometry geometry = new Geometry("bridge", new FlatShadedMesh(mesh));
    geometry.setMaterial(material);
    geometry.scale(2f, 1f, 1f);
    geometry.move(0, 0, 40f);
    scene.attachChild(geometry);
    logger.debug("bridge bound = {}", geometry.getWorldBound());
  }
  
  private void hull() {
    Mesh mesh = new Cylinder(2, 8, 10, 40, true);
    MyMesh.rotate(mesh, new Quaternion().fromAngleNormalAxis(FastMath.PI / 8f, Vector3f.UNIT_Z));
    
    Geometry geometry = new Geometry("hull", new FlatShadedMesh(mesh));
    geometry.setMaterial(material);
    geometry.scale(2f, 1f, 1f);
    scene.attachChild(geometry);
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
