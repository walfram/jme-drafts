package galaxy.ship.designer;

import static java.lang.Math.ceil;

import cells.Cell2d;
import com.google.common.collect.Iterators;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import galaxy.domain.ship.ShipDesign;
import galaxy.ship.designer.CargoBatchPlacement.YShape;
import java.util.Iterator;
import java.util.List;

import galaxy.ship.generated.BatchPlacement;
import galaxy.ship.generated.BatchPosition;
import galaxy.ship.generated.YShapePlacement;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratedShipState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(GeneratedShipState.class);
  
  private static final int batchesSingleRowThreshold = 6;
  
  private static final float cellExt = 4f;
  
  private Material material;
  private Mesh cargoBatchMesh;
  
  @Override
  protected void initialize(Application app) {
    material = new Material(app.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
    
    cargoBatchMesh = new WireBox(cellExt * 0.95f, cellExt * 0.95f, cellExt * 0.95f);
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

  public Node generate(ShipDesign shipDesign) {
    Node ship = new Node("generated-ship");

//    Spatial hull = hull(shipDesign);
//    ship.attachChild(hull);

    Spatial cargo = cargo(shipDesign);
    ship.attachChild(cargo);

    Spatial drives = drives(shipDesign);
    ship.attachChild(drives);

    Spatial bridge = bridge(shipDesign);
    ship.attachChild(bridge);

    Spatial shields = shields(shipDesign);
    ship.attachChild(shields);
    
    return ship;
  }

  private Spatial hull(ShipDesign shipDesign) {
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 3, 4f, 32f, true)));

    hull.setMaterial(material);
    
    return hull;
  }

  private Spatial shields(ShipDesign shipDesign) {
    return new Node("shields");
  }

  private Spatial bridge(ShipDesign shipDesign) {
    return new Node("bridge");
  }

  private Spatial drives(ShipDesign shipDesign) {
    return new Node("drives");
  }

  private Spatial cargo(ShipDesign shipDesign) {
    int containers = (int) ceil(shipDesign.cargo().volume());
    int batches = (int) ceil(containers * 0.2f);
    logger.debug("cargo volume = {}, containers = {}, batches (x5) = {}", shipDesign.cargo().volume(), containers, batches);

    Node node = new Node("cargo");
    
    if (batches == 0) {
      return node;
    }
    
    if (batches <= batchesSingleRowThreshold) {
      for (int z = 0; z < batches; z++) {
        Geometry batch = new Geometry("batch-%s".formatted(z), cargoBatchMesh);
        batch.setMaterial(material);
        batch.setLocalTranslation(new Cell2d(0, z, cellExt).translation());
        node.attachChild(batch);
      }
      
      return node;
    }
    
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(batches);
    logger.debug("arranged = {}", arranged);
    
    for (int z = 0; z < arranged.size(); z++) {
      for (BatchPosition batchPosition: arranged.get(z)) {
        Geometry batch = new Geometry("batch-%s-%s".formatted(z, batchPosition.toString()), cargoBatchMesh);
        batch.setMaterial(material);
        batch.setLocalTranslation(batchPosition.translation(z, cellExt));
        node.attachChild(batch);
      }
    }
    
    return node;
  }
}
