package collapse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import assets.LandscapeAssets;
import cells.Side;
import com.jme3.asset.AssetManager;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import jme3utilities.MyMesh;
import jme3utilities.math.VectorSet;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetProcessingTest {

  private static final Logger logger = LoggerFactory.getLogger(AssetProcessingTest.class);
  
  private final AssetManager assetManager = new DesktopAssetManager(true);
  
  private final LandscapeAssets assets = new LandscapeAssets();
  
  @Test
  void filter_vertices() {
    for (String path : assets.paths()) {
      Spatial spatial = assetManager.loadModel(path);
      logger.debug("loaded spatial {}", path);

      VectorSet vectorSet = MyMesh.listVertexLocations(spatial, null);
      Vector3f[] vectorArray = vectorSet.toVectorArray();
      
      Map<Side, List<Vector3f>> sockets = new EnumMap<>(Side.class);
      
      for (Side side : Side.values()) {
        List<Vector3f> filtered = side.filter(vectorArray, 0.5f);
        sockets.put(side, filtered);
        logger.debug("\tside = {}, socket = {}", side, filtered);
      }
      
      assertEquals(4, sockets.size());
      
      assertFalse(sockets.get(Side.X_POS).isEmpty());
      assertFalse(sockets.get(Side.X_NEG).isEmpty());
      
      assertFalse(sockets.get(Side.Z_POS).isEmpty());
      assertFalse(sockets.get(Side.Z_NEG).isEmpty());
    }
  }
  
}
