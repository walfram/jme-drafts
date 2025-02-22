package galaxy.ship.designer;

import static com.jme3.math.FastMath.PI;
import static com.jme3.math.FastMath.cos;
import static com.jme3.math.FastMath.sin;
import static com.jme3.math.FastMath.sqrt;
import static galaxy.ship.designer.CargoBatchPlacement.TShape.X_NEG;
import static java.lang.Math.ceil;
import static java.lang.Math.min;
import static jme3utilities.math.MyMath.max;

import cells.Cell2d;
import com.google.common.collect.Iterators;
import com.google.common.primitives.Floats;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import galaxy.domain.ship.ShipDesign;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jme3utilities.mesh.Icosphere;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @deprecated this is "god" class, must be refactored
 */
@Deprecated
public class GeneratedShip {

  private static final Logger logger = LoggerFactory.getLogger(GeneratedShip.class);

  private static final float cellExtent = 4f;
  private static final float halfExtent = cellExtent * 0.5f;
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
    // count containers, rounded up
    int containers = (int) ceil(design.cargo().volume());

    // containers to batches, 5 containers per batch
    int batches = (int) ceil(containers * 0.2f);

    logger.debug("containers = {}, batches = {}", containers, batches);

    Node cargo = new Node("cargo");
    root.attachChild(cargo);

    Iterator<CargoBatchPlacement> itr = Iterators.cycle(CargoBatchPlacement.TShape.values());

    Mesh containerMesh = new FlatShadedMesh(new Cylinder(2, 6, 0.75f * halfExtent, containerBatchExtent, true));

    Vector3f[] containerOffsets = {
        new Vector3f(-halfExtent, -halfExtent, 0), new Vector3f(-halfExtent, halfExtent, 0),
        new Vector3f(halfExtent, -halfExtent, 0), new Vector3f(halfExtent, halfExtent, 0),
        new Vector3f(0, 0, 0)
    };
    
    int z = 1;
    int container = 0;
    while (batches > 0) {
      Node batch = new Node("batch");
      cargo.attachChild(batch);

      CargoBatchPlacement cbp = itr.next();
      Vector3f translation = cbp.translation(z, cellExtent);
      batch.setLocalTranslation(translation);

      Geometry batchBound = new Geometry("batch-%s".formatted(z), new WireBox(containerBatchExtent, containerBatchExtent, containerBatchExtent));
      batchBound.setMaterial(material);
      batch.attachChild(batchBound);

      for (Vector3f offset : containerOffsets) {
        Geometry g = new Geometry("container-%s".formatted(container), containerMesh);
        g.setLocalTranslation(offset);
        g.setMaterial(material);
        batch.attachChild(g);
        
        if (container++ > containers)
          break;
      }

      if (cbp == X_NEG) {
        z++;
      }

      batches--;
    }
  }

  private void attachDrives(Node root) {
    Node engine = new Node("engine");
    root.attachChild(engine);
    engine.move(new Cell2d(0, -1, cellExtent).translation());

    int driveCount = (int) ceil(design.drives().size());
    logger.debug("drives = {}", driveCount);

    List<Vector3f> points = new ArrayList<>(driveCount);
    points.add(new Vector3f(0, 0, 0));

    float r = 0.75f;
    int placed = 1;
    int ring = 1;

    while (placed < driveCount) {
      int ringSize = 6 * ring;
      int remaining = driveCount - placed;
      float ringRadius = ring * 2 * r;
      for (int i = 0; i < ringSize && placed < driveCount; i++) {
        float theta = (2f * PI * i) / min(remaining, ringSize);
        float x = ringRadius * cos(theta);
        float y = ringRadius * sin(theta);
        points.add(new Vector3f(x, y, 0));
        placed++;
      }
      ring++;
    }

    Node drives = new Node("drives");
    engine.attachChild(drives);
    drives.move(0, 0, -cellExtent - 1f); // -1 == half drive mesh height

    Mesh driveMesh = new FlatShadedMesh(new Cylinder(2, 6, 0.25f, 0.65f, 2f, true, false));
    for (Vector3f v : points) {
      Geometry drive = new Geometry("drive", driveMesh);
      drive.setMaterial(material);
      drive.setLocalTranslation(v);
      drives.attachChild(drive);
    }

    BoundingBox bound = (BoundingBox) drives.getWorldBound();
    float baseRadius = max(bound.getXExtent(), bound.getYExtent(), 0.5f * cellExtent);

    Geometry base = new Geometry("engine-base", new FlatShadedMesh(new Cylinder(2, 8, cellExtent, baseRadius, 2f * cellExtent, true, false)));
    base.setMaterial(material);
    engine.attachChild(base);
  }
}
