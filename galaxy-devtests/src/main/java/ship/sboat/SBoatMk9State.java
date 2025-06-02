package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;

import java.util.ArrayList;
import java.util.List;

public class SBoatMk9State extends BaseAppState {
  
  private static final float cellExtent = 4f;
  private final Node scene = new Node("scene");
  
  public SBoatMk9State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
    
    Mesh mesh = new CustomCylinder(cellExtent, 3f * cellExtent, 2f * 3f * cellExtent, 8, true, false, 0.2f);
    Geometry geometry = new Geometry("hull", mesh);
    geometry.setMaterial(material);
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
