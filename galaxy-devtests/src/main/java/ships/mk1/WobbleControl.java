package ships.mk1;

import com.jme3.math.FastMath;
import jme3utilities.SimpleControl;

public class WobbleControl extends SimpleControl {
  
  private float hoverFrequency = 1.25f;
  private float hoverAmplitude = 0.0125f;
  private float time = 0f;
  
  @Override
  protected void controlUpdate(float updateInterval) {
    super.controlUpdate(updateInterval);
    
    float hoverOffsetY = FastMath.sin(time * hoverFrequency) * hoverAmplitude;
    getSpatial().move(0, hoverOffsetY, 0);
    
    time = (time + updateInterval) % FastMath.TWO_PI;
  }
}
