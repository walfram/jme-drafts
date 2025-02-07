package galaxy.tests;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickAppSetup;
import java.util.List;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoPlacementSingleTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    CargoPlacementSingleTest app = new CargoPlacementSingleTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(CargoPlacementSingleTest.class);

  @Override
  public void simpleInitApp() {
    new QuickAppSetup(4f, 32).applyTo(this);

    int cargo = 47;
    int groups = 3;

    int baseSize = cargo / groups;
    int extra = cargo % groups;

    logger.debug("cargo = {}, groups = {}, base size = {}, extra = {}", cargo, groups, baseSize, extra);

    Mesh containerMesh = new FlatShadedMesh(new Cylinder(3, 6, 3.75f, 7.75f, true));
    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    int from = -groups + 1; 
    int to = groups - 1;
    logger.debug("x from = {}, to = {}", from, to);

    List<Integer> split = new Splitted(cargo, groups).split();

    int group = 0;
    for (int x = from; x <= to; x += 2) {
      logger.debug("x = {}", x);
      int zFrom = baseSize / 2;

      int z = zFrom;
      while (z > (zFrom - split.get(group))) {
        // add container
        Geometry container = new Geometry("container-%s-%s".formatted(x, z), containerMesh);
        container.setMaterial(material);

        container.setLocalTranslation(new Cell2d(x, z, 4f).translation());
        rootNode.attachChild(container);

        z--;
      }
      
      group++;
    }

    new QuickChaseCamera(cam, inputManager).init(rootNode);

    long count = rootNode.getChildren().stream().filter(c -> c.getName() != null).filter(c -> c.getName().startsWith("container-")).count();
    logger.debug("containers in root node = {}", count);
  }
}
