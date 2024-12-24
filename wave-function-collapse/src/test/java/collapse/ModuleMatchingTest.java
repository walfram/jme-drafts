package collapse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import assets.LandscapeAssets;
import cells.Side;
import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wfc.Module;

public class ModuleMatchingTest {

  private static final Logger logger = LoggerFactory.getLogger(ModuleMatchingTest.class);

  private final AssetManager assetManager = new DesktopAssetManager(true);

  @Test
  void test_module() {
    Spatial spatial = assetManager.loadModel("landscape-assets/ground_pathCross.obj");

    Quaternion rotation = new Quaternion().fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);
    Module module = new Module(spatial, rotation);

    assertTrue(module.matches(module, Side.X_POS));
    assertTrue(module.matches(module, Side.X_NEG));
    assertTrue(module.matches(module, Side.Z_POS));
    assertTrue(module.matches(module, Side.Z_NEG));
  }

  @Test
  void load_modules() {
    List<Spatial> spatials = new LandscapeAssets()
        .paths()
        .stream()
        .peek(path -> logger.debug("path = {}", path))
        .map(assetManager::loadModel)
        .toList();
    
    logger.debug("loaded spatials = {}", spatials.size());

    List<Module> modules = new ArrayList<>(spatials.size() * 4);

    for (Spatial spatial : spatials) {
      for (int rotationIdx = 0; rotationIdx < 4; rotationIdx++) {
        Quaternion rotation = new Quaternion().fromAngleNormalAxis(FastMath.HALF_PI * rotationIdx, Vector3f.UNIT_Y);
        modules.add(new Module(spatial, rotation));
      }
    }

    assertEquals(spatials.size() * 4, modules.size());
  }

}
