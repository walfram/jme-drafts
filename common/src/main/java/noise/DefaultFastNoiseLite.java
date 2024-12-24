package noise;

public class DefaultFastNoiseLite extends FastNoiseLite {

  public DefaultFastNoiseLite() {
    super(42);
    SetFrequency(0.001f);
    SetNoiseType(NoiseType.Value);
    SetFractalType(FractalType.PingPong);
  }
}
