package tests;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickAppSetup;
import java.util.Objects;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CargoPlacementBatchTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    CargoPlacementBatchTest app = new CargoPlacementBatchTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(CargoPlacementBatchTest.class);
  private static final float extent = 4f;

  @Override
  public void simpleInitApp() {
    new QuickAppSetup(2f * extent, 64).applyTo(this);

    // rootNode.attachChild(new CargoBatch(assetManager));

    int[] xRange = IntStream.range(-1, 2).toArray();
    logger.debug("x range = {}", xRange);
    
    int[] zRange = IntStream.range(-4, 4).toArray();
    logger.debug("z range = {}", zRange);
    
    int[] yRange = IntStream.range(-1, 1).toArray();
    logger.debug("y range = {}", yRange);

    for (int y : yRange) {
      for (int x : xRange) {
        for (int z : zRange) {
          Node cargoBatch = new CargoBatch(assetManager);
          
          cargoBatch.setLocalTranslation(new Cell2d(x, z, 2f * extent).translation());
          rootNode.attachChild(cargoBatch);
          
          cargoBatch.move(0, y * 4f * extent, 0);
        }
      }
    }

    long cargoBatches = rootNode.getChildren().stream().filter(s -> Objects.equals("cargo-batch", s.getName())).count();
    logger.debug("cargo batches = {}, total containers = {}", cargoBatches, cargoBatches * 8);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    
    Geometry engines = new Geometry("engines", new Cylinder(2, 6, 8f * extent, 4f * extent, true));
    engines.setMaterial(material);
    rootNode.attachChild(engines);
    engines.move(0, 0, -4f * extent * 4);
    engines.scale(1, 0.5f, 1);

    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }
}
