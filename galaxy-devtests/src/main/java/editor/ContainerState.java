package editor;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import com.simsilica.lemur.geom.MBox;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;

import static java.lang.Math.signum;
import static org.slf4j.LoggerFactory.getLogger;

public class ContainerState extends BaseAppState {
  
  private static final Logger logger = getLogger(ContainerState.class);
  
  private final Node scene = new Node("scene");
  private final float cellExtent;
  
  public ContainerState(Node rootNode, float cellExtent) {
    rootNode.attachChild(scene);
    this.cellExtent = cellExtent;
  }
  
  @Override
  protected void initialize(Application application) {
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
  
  public void updateContainers(float xExtent, float yExtent, float zExtent) {
    scene.detachAllChildren();
    
    Material material = new ShowNormalsMaterial(getApplication().getAssetManager());
    
    Mesh source = new Cylinder(2, 8, xExtent, 2f * zExtent, true);
//    Mesh mesh = new MBox(xExtent, yExtent, zExtent, 2, 2, 2);
    
    Geometry left = new Geometry("left-container", new FlatShadedMesh(new DMesh(source, new ContainerDeformation(1f))));
    left.setMaterial(material);
    left.move(-2f * xExtent, 0, 0);
    scene.attachChild(left);
    
    Geometry right = new Geometry("right-container", new FlatShadedMesh(new DMesh(source, new ContainerDeformation(-1))));
    right.setMaterial(material);
    right.move(2f * xExtent, 0, 0);
    scene.attachChild(right);
  }
  
  private static final class ContainerDeformation implements Deformation {
    
    private final float delta;
    
    public ContainerDeformation(float delta) {
      this.delta = delta;
    }
    
    @Override
    public void deform(Vector3f vert, Vector3f normal) {
      if (signum(delta) == signum(vert.x)) {
        vert.x = 0;
      }
    }
  }
  
}
