package generated;

import cells.Cell2d;
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
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import galaxy.ship.generated.BatchPlacement;
import materials.ShowNormalsMaterial;
import materials.WireframeMaterial;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tests.ScaleUpTo;

import static com.jme3.math.FastMath.DEG_TO_RAD;
import static com.jme3.math.FastMath.HALF_PI;
import static com.jme3.math.Vector3f.UNIT_Z;

public class ShipModelState extends BaseAppState {
  
  private static final Logger logger = LoggerFactory.getLogger(ShipModelState.class);
  
  private static final float cellExtent = 4f;
  
  private final Node scene = new Node("scene");
  
  public ShipModelState(Node rootNode) {
//    rootNode.attachChild(scene);
  }
  
  static final int containersPerBatch = 5;
  
  @Override
  protected void initialize(Application app) {
    Material material = new ShowNormalsMaterial(app.getAssetManager());
    
    int containers = 99;
    int batches = containers / containersPerBatch;
    
    logger.debug("containers = {}, batches = {}", containers, batches);
    
    int batchesPerRow = 8;
    float theta = 360f / batchesPerRow;
    logger.debug("theta = {}", theta);
    
    int rows = batches / batchesPerRow;
    int rowRemainder = batches % batchesPerRow;
    logger.debug("rows = {}, row remainder = {}", rows, rowRemainder);
    
    Mesh batchMesh = new WireBox(cellExtent, cellExtent, cellExtent);
    
    for (int z = 0; z < rows; z++) {
      for (int i = 0; i < batchesPerRow; i++) {
        Geometry batch = new Geometry("batch", batchMesh);
        batch.setMaterial(material);
        
        Vector3f offset = new Vector3f(1.5f * 2f * cellExtent, 0, 0);
        batch.setLocalTranslation(offset);
        
        Node node = new Node("batch");
        node.attachChild(batch);
        
        Vector3f t = new Cell2d(0, z, cellExtent).translation();
        node.setLocalTranslation(t);
        
        Quaternion rotation = new Quaternion().fromAngleNormalAxis(DEG_TO_RAD * theta * i, UNIT_Z);
        node.setLocalRotation(rotation);
        
        scene.attachChild(node);
      }
    }
    
    Mesh mesh = new FlatShadedMesh(new Cylinder(4, 6, 1, 1, true));
    
    Geometry hull = new Geometry("hull", mesh);
    hull.setMaterial(new WireframeMaterial(material));
    
    // x = 1, y = 3, z = 7
    // new ScaleUpTo(1f * 2f * cellExtent, 3f * 2f * cellExtent, 7f * 2f * cellExtent, hull).scale();
    
    int extraRow = rowRemainder > 0 ? 1 : 0;
    logger.debug("extraRow = {}", extraRow);
    new ScaleUpTo(1.5f * cellExtent, 1.5f * cellExtent, (rows + extraRow) * cellExtent, hull).scale();
    scene.attachChild(hull);
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
