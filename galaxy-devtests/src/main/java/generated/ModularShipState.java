package generated;

import cells.Cell;
import cells.Cell3d;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.geom.MBox;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import tests.ScaleUpTo;

public class ModularShipState extends BaseAppState {
  
  private static final float cellExtent = 8f;
  
  private final Node scene = new Node("scene");
  private Material material;
  
  public ModularShipState(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    material = new ShowNormalsMaterial(app.getAssetManager());
    
    Mesh cylinder = new FlatShadedMesh(new Cylinder(2, 6, 1, 1, true));
    Geometry geometry = new Geometry("cylinder", cylinder);
    geometry.setMaterial(material);
    scene.attachChild(geometry);
    
    geometry.scale(cellExtent, cellExtent, 2f * cellExtent);
  }
  
  private void simpleBoxes() {
    Mesh box = new MBox(1, 1, 1, 2, 2, 2);
    
    for (int z = -2; z <= 2; z++) {
      Cell cell = new Cell3d(0, 0, z, cellExtent);
      
      Geometry geometry = new Geometry("box-%s".formatted(z), box);
      geometry.scale(8f * 0.99f);
      
      geometry.setMaterial(material);
      geometry.setLocalTranslation(cell.translation());
      
      scene.attachChild(geometry);
    }
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
