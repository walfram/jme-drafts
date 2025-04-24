package editor;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.simsilica.lemur.geom.MBox;
import materials.ShowNormalsMaterial;
import org.slf4j.Logger;

import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static org.slf4j.LoggerFactory.getLogger;

public class ContainerState extends BaseAppState {
  
  private static final Logger logger = getLogger(ContainerState.class);
  private static final double PHI = 1.61803398875;
  
  private final Node scene = new Node("scene");
  private final float cellExtent;
  
  private int containerCount;
  
  public ContainerState(Node rootNode, float cellExtent) {
    rootNode.attachChild(scene);
    this.cellExtent = cellExtent;
  }
  
  @Override
  protected void initialize(Application application) {
  }
  
  private void updateContainers() {
    scene.detachAllChildren();
    
    Material material = new ShowNormalsMaterial(getApplication().getAssetManager());
    
    double volume = 0.5 * containerCount; // * pow(cellExtent, 3);
    logger.debug("container count = {}, volume = {}", containerCount, volume);
    
    double width = cbrt(volume / pow(PHI, 3));
    double height = PHI * width;
    double depth = pow(PHI, 2) * width;
    
    float xExtent = (float) width;
    float yExtent = (float) height;
    float zExtent = (float) depth;
    logger.debug("container extent = x={} y={} z={}", xExtent, yExtent, zExtent);
    
    Mesh mesh = new MBox(xExtent, yExtent, zExtent, 2, 2, 2);
    
    Geometry left = new Geometry("left-container", mesh);
    left.setMaterial(material);
    left.move(-cellExtent - xExtent, 0, 0);
    scene.attachChild(left);
    
    Geometry right = new Geometry("right-container", mesh);
    right.setMaterial(material);
    right.move(cellExtent + xExtent, 0, 0);
    scene.attachChild(right);
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
  
  public void updateContainerCount(int containerCount) {
    this.containerCount = containerCount;
    updateContainers();
  }
}
