package editor;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.simsilica.lemur.geom.MBox;
import materials.ShowNormalsMaterial;

public class ContainerState extends BaseAppState {
  
  private final Node scene = new Node("scene");
  private final float cellExtent;
  
  private int containerCount;
  
  public ContainerState(Node rootNode, float cellExtent) {
    rootNode.attachChild(scene);
    this.cellExtent = cellExtent;
  }
  
  @Override
  protected void initialize(Application application) {
    updateContainers();
  }
  
  private void updateContainers() {
    scene.detachAllChildren();
    
    Mesh mesh = new MBox(cellExtent, cellExtent, cellExtent, 2, 2, 2);
    Material material = new ShowNormalsMaterial(getApplication().getAssetManager());
    
    ContainerVolume volume = new ContainerVolume(containerCount);
    Dimensions dimensions = volume.dimensions();
    
    for (int ox: dimensions.x()) {
      for (int oy: dimensions.y()) {
        for (int oz: dimensions.z()) {
          Geometry geometry = new Geometry("container.%s.%s.%s".formatted(ox, oy, oz), mesh);
          geometry.setMaterial(material);
          
          Vector3f translation = new Vector3f(ox, oy, oz).multLocal(2f * cellExtent);
          geometry.setLocalTranslation(translation);
          
          geometry.scale(0.95f);
          scene.attachChild(geometry);
        }
      }
    }
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
  }
}
