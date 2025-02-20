package cells;

import static java.lang.Math.round;

import com.jme3.math.Vector3f;
import java.util.Set;

public record Cell3d(int x, int y, int z, float extent) implements Cell {

  public Cell3d(Vector3f origin, float extent) {
    this(
        round(origin.x / (2f * extent)),
        round(origin.y / (2f * extent)),
        round(origin.z / (2f * extent)),
        extent
    );
  }

  @Override
  public Vector3f translation() {
    return new Vector3f(x, y, z).multLocal(2f * extent);
  }

  @Override
  public Set<Cell> slice(int slices) {
    if (slices % 2 != 0) {
      throw new IllegalArgumentException("Slices param expected to be odd, given = %s".formatted(slices));
    }

    float f = 0.5f * (2f * extent / (slices + 1));
    return new Cell3d(translation(), f).neighboursAll();
  }

  @Override
  public Set<Cell> neighboursAll() {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public Set<Cell> neighboursAdjacent() {
    throw new UnsupportedOperationException("not yet implemented");
  }

  @Override
  public <T extends CellOffset> Cell relative(T offset) {
    throw new UnsupportedOperationException("not yet implemented");
  }
}
