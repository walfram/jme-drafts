package editor;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import org.slf4j.Logger;

import static java.lang.Math.cbrt;
import static java.lang.Math.pow;
import static org.slf4j.LoggerFactory.getLogger;

public class ShipState extends BaseAppState {
  private static final Logger logger = getLogger(ShipState.class);
  
  private static final double PHI = 1.61803398875;
  
  @Override
  protected void initialize(Application application) {
  
  }
  
  @Override
  protected void cleanup(Application application) {
  
  }
  
  @Override
  protected void onEnable() {
  
  }
  
  @Override
  protected void onDisable() {
  
  }
  
  public void updateContainerCount(int containerCount) {
    double volume = 0.5 * containerCount; // * pow(cellExtent, 3);
    logger.debug("container count = {}, volume = {}", containerCount, volume);
    
    double width = cbrt(volume / pow(PHI, 3));
    double height = PHI * width;
    double depth = pow(PHI, 2) * width;
    
    float xExtent = (float) width;
    float yExtent = (float) height;
    float zExtent = (float) depth;
    logger.debug("container extent = x={} y={} z={}", xExtent, yExtent, zExtent);
    
    getState(ContainerState.class).updateContainers(xExtent, yExtent, zExtent);
    getState(HullState.class).updateContainers(xExtent, yExtent, zExtent);
  }
}
