package ship.deromed;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Torus;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class DeformedTorusState extends BaseAppState {
  
  private static final Logger logger = getLogger(DeformedTorusState.class);
  private final Node scene = new Node("scene");
  private final float cellExtent;
  
  public DeformedTorusState(Node rootNode, float cellExtent) {
    rootNode.attachChild(scene);
    this.cellExtent = cellExtent;
  }
  
  @Override
  protected void initialize(Application application) {
    Material material = new ShowNormalsMaterial(application.getAssetManager());
    
    Mesh source = new Torus(8, 6, 6, 10);
    
    Set<Float> lengths = new HashSet<>();
    
    Deformation deform = (v, n) -> {
      if (Math.abs(v.z) > 0.001f)
        return;
      
      logger.debug("V = {}, length = {}", v, v.length());
      
      float r = 8f;
      
      if (v.length() > r)
        r = 12f;
      
      float theta = FastMath.atan2(v.y, v.x);
      v.x = r * FastMath.cos(theta);
      v.y = r * FastMath.sin(theta);
    };
    
    Mesh deformed = new DMesh(source, deform);
    
    logger.debug("lengths = {}", lengths);
    
    Geometry geometry = new Geometry("ring", new FlatShadedMesh(deformed));
    geometry.setMaterial(material);
    scene.attachChild(geometry);
  }
  
  @Override
  protected void cleanup(Application application) {
  
  }
  
  @Override
  protected void onEnable() {
  
  }
  
  @Override
  protected void onDisable() {
  
  }
}
