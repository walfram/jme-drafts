package galaxy.tests;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.MBox;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import galaxy.domain.ship.ShipDesign;
import java.util.Objects;
import jme3utilities.MyMesh;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModularShipTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    ModularShipTest app = new ModularShipTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(ModularShipTest.class);

  private static final float cellExtent = 4f;

  @Override
  public void simpleInitApp() {
    new QuickAppSetup(cellExtent, 32).applyTo(this);

    ShipDesign design = new ShipDesign(80, 2, 2, 30, 100);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    // material.getAdditionalRenderState().setWireframe(true);

    float hullCells = 7;
    
    float hullExtent = hullCells * cellExtent;
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 6, 4, 2f * hullExtent, true)));
    hull.setMaterial(material);
    hull.scale(1, 2, 1);
    rootNode.attachChild(hull);

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(new Cylinder(2, 6, 2, cellExtent, 2f * cellExtent, true, false)));
    bridge.setMaterial(material);
    bridge.move(0, 0, hullExtent + cellExtent);
    bridge.scale(1, 2f, 1);
    rootNode.attachChild(bridge);

    Node engines = enginesMk1(material);
    engines.move(0, 0, -hullExtent - cellExtent);
    rootNode.attachChild(engines);

     cargoBatches();

    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }

  private void cargoBatches() {
    int[] xs = {-3, -2, -1, 1, 2, 3};
    int[] ys = {0};

    for (int y : ys) {
      for (int x : xs) {
        for (int z = -3; z <= 3; z++) {
          Node cargoBatch = new CargoBatch(assetManager);
          rootNode.attachChild(cargoBatch);
          new ScaleUpTo(cellExtent, cellExtent, cellExtent, cargoBatch).scale();
          cargoBatch.setLocalTranslation(new Cell2d(x, z, cellExtent).translation());
          cargoBatch.move(0, y * 2f * cellExtent, 0);
        }
      }
    }

    long cargoBatchCount = rootNode.getChildren().stream().filter(s -> Objects.equals("cargo-batch", s.getName())).count();
    logger.debug("cargo batch count = {}, container count = {}", cargoBatchCount, cargoBatchCount * 4);
  }

  private Node enginesMk1(Material material) {
    Node engines = new Node("engines");

    Quaternion r = new Quaternion().fromAngleNormalAxis(FastMath.DEG_TO_RAD * 30f, Vector3f.UNIT_Z);

    Geometry base = new Geometry("base", new MBox(1, 2f * cellExtent, cellExtent, 1, 2, 2));
    base.setMaterial(material);
    engines.attachChild(base);

    // Geometry middle = new Geometry("engine-main", new MBox(8, 1, 4, 2, 1, 1));
    Geometry middle = new Geometry("engine-main", new FlatShadedMesh(new Cylinder(2, 6, 16, 8, true)));
    MyMesh.rotate(middle.getMesh(), r);
    middle.setMaterial(material);
    middle.scale(1, 0.2f, 1);
    engines.attachChild(middle);

    Geometry bottom = new Geometry("engine-bottom", new FlatShadedMesh(new Cylinder(2, 6, 8, 8, true)));
    MyMesh.rotate(bottom.getMesh(), r);
    bottom.setMaterial(material);
    bottom.scale(1, 0.2f, 1);
    engines.attachChild(bottom);
    bottom.move(0, -6, 0);

    Geometry top = new Geometry("engine-top", new FlatShadedMesh(new Cylinder(2, 6, 8, 8, true)));
    MyMesh.rotate(top.getMesh(), r);
    top.setMaterial(material);
    top.scale(1, 0.2f, 1);
    engines.attachChild(top);
    top.move(0, 6, 0);

    //engines.move(0, 0, -36);
    // rootNode.attachChild(engines);
    return engines;
  }
}
