package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import noise.FastNoiseLite;
import noise.FastNoiseLite.FractalType;
import noise.FastNoiseLite.NoiseType;

public class NoiseState extends BaseAppState {

  private final FastNoiseLite noise = new FastNoiseLite(42);
  {
    noise.SetFrequency(0.001f);
    noise.SetNoiseType(NoiseType.Value);
    noise.SetFractalType(FractalType.PingPong);
  }

  public float calculateHeight(float x, float z) {
    float e = noise.GetNoise(x, z);

    if (e < 0) {
      e = 0f;
    }

    return e * 256f;
  }
  
  @Override
  protected void initialize(Application app) {
    
  }

  @Override
  protected void cleanup(Application app) {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void onDisable() {

  }
}
