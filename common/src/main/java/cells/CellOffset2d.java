package cells;

import static com.jme3.math.FastMath.sqrt;

public record CellOffset2d(int dx, int dz) implements CellOffset {

  @Override
  public float distance() {
    return sqrt(dx * dx + dz * dz);
  }
}
