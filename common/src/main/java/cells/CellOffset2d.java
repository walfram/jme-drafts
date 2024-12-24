package cells;

import com.jme3.math.FastMath;

public record CellOffset2d(int dx, int dz) implements CellOffset {

  @Override
  public float distance() {
    return FastMath.sqrt(dx * dx + dz * dz);
  }
}
