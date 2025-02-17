package galaxy.ship.designer;

import static com.jme3.math.FastMath.sqrt;
import static galaxy.ship.designer.CargoBatchPlacement.X_NEG;
import static java.lang.Math.ceil;

import cells.Cell2d;
import com.google.common.collect.Iterators;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import galaxy.domain.ship.ShipDesign;
import java.util.Iterator;
import jme3utilities.mesh.Icosphere;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratedShip {

  private static final Logger logger = LoggerFactory.getLogger(GeneratedShip.class);

  private static final float cellExtent = 4f;
  private static final float containerBatchExtent = 0.95f * cellExtent;

  private final ShipDesign design;
  private final Material material;

  public GeneratedShip(ShipDesign design, Material material) {
    this.design = design;
    this.material = material;
  }

  public Node node() {
    Node root = new Node("ship-root");

    attachHull(root);

    if (design.drives().size() > 0) {
      attachDrives(root);
    }

    if (design.cargo().volume() > 0) {
      attachCargo(root);
    }

    if (design.weapons().guns() > 0) {
      attachWeapons(root);
    }

    if (design.shields().power() > 0) {
      attachShields(root);
    }

    return root;
  }

  private void attachShields(Node root) {

  }

  private void attachWeapons(Node root) {

  }

  private void attachHull(Node root) {
    // Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 8, cellExtent, 2f * cellExtent, true)));
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Icosphere(1, sqrt(2 * cellExtent * cellExtent))));
    hull.setMaterial(material);
    root.attachChild(hull);
    // hull.move(new Cell2d(0, 1, cellExtent).translation());
  }

  private void attachCargo(Node root) {
    // count containers
    int containers = (int) ceil(design.cargo().volume());
    // create container batches
    int batches = (int) ceil(containers * 0.2f);

    logger.debug("containers = {}, batches = {}", containers, batches);

    Node cargo = new Node("cargo");
    root.attachChild(cargo);

    Iterator<CargoBatchPlacement> itr = Iterators.cycle(CargoBatchPlacement.values());

    int z = 1;
    while (batches > 0) {
      Geometry batch = new Geometry("batch-%s".formatted(z), new WireBox(containerBatchExtent, containerBatchExtent, containerBatchExtent));
      batch.setMaterial(material);

      CargoBatchPlacement cbp = itr.next();
      Vector3f translation = cbp.translation(z, cellExtent);
      batch.setLocalTranslation(translation);
      cargo.attachChild(batch);

      if (cbp == X_NEG) {
        z++;
      }

      batches--;
    }

    //    for (int z = 1; z < 1 + batches; z++) {
    //      Geometry batch = new Geometry("batch-%s".formatted(z), new WireBox(containerBatchExtent, containerBatchExtent, containerBatchExtent));
    //      batch.setMaterial(material);
    //      batch.setLocalTranslation(new Cell2d(0, z, cellExtent).translation());
    //      cargo.attachChild(batch);
    //    }
  }

  private void attachDrives(Node root) {
    Node engine = new Node("engine");
    root.attachChild(engine);

    //    Geometry hullBase = new Geometry("hull-base", new FlatShadedMesh(new Cylinder(2, 8, 3f * cellExtent, 2f * cellExtent, true)));
    //    hullBase.setMaterial(material);
    //    engine.attachChild(hullBase);
    //    hullBase.move(new Cell2d(0, 0, cellExtent).translation());
    //    hullBase.scale(1, 0.5f, 1);

    //    Geometry engineCenter = new Geometry("engine-center", new FlatShadedMesh(new Cylinder(2, 8, cellExtent, 0.25f * cellExtent, true)));
    //    engineCenter.setMaterial(material);
    //    engineCenter.setLocalTranslation(new Cell2d(0, -1, cellExtent).translation().add(0, 0, (0.75f + 0.125f) * cellExtent));
    //    engine.attachChild(engineCenter);

    Geometry engineCenter = new Geometry("engine-center", new FlatShadedMesh(new Cylinder(2, 8, 0.5f * cellExtent, cellExtent, 2f * cellExtent, true, false)));
    engineCenter.setMaterial(material);
    engine.attachChild(engineCenter);

    engine.move(new Cell2d(0, -1, cellExtent).translation());
  }
}
