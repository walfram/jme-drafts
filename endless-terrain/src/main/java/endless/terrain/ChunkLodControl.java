package endless.terrain;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import jme3utilities.SimpleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChunkLodControl extends SimpleControl {

  private static final Logger logger = LoggerFactory.getLogger(ChunkLodControl.class);
  
  private final float threshold;
  private int currentLod = 0;

  public ChunkLodControl(float threshold) {
    this.threshold = threshold;
  }

  @Override
  protected void controlRender(RenderManager renderManager, ViewPort viewPort) {
    super.controlRender(renderManager, viewPort);

    Vector3f location = viewPort.getCamera().getLocation();
//    BoundingVolume bound = spatial.getWorldBound();
//    float distance = bound.distanceTo(location);
    
    float distance = spatial.getWorldTranslation().distance(location);
    
    int calculatedLod = Math.min(3, (int) (distance / threshold));
    
    if (calculatedLod != currentLod) {
      spatial.setLodLevel(calculatedLod);
      logger.debug("setting lod level from {} to {} on spatial {}", currentLod, calculatedLod, spatial.getName());
      currentLod = calculatedLod;
    }
  }

  @Override
  protected void controlUpdate(float updateInterval) {
    super.controlUpdate(updateInterval);
  }
}
