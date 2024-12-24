package cells;

import com.jme3.math.Vector3f;
import java.util.HashSet;
import java.util.Set;

public record Cell2d(int x, int z, float extent) implements Cell {

  public Cell2d(Vector3f origin, float extent) {
    this(
        Math.round(origin.x / (2f * extent)),
        Math.round(origin.z / (2f * extent)),
        extent
    );
  }

  @Override
  public Vector3f translation() {
    return new Vector3f(x, 0, z).multLocal(2f * extent);
  }

  @Override
  public Set<Cell> slice(int slices) {
    if (slices % 2 != 0) {
      throw new IllegalArgumentException("Slices param expected to be odd, given = %s".formatted(slices));
    }

    float f = 0.5f * (2f * extent / (slices + 1));
    return new Cell2d(translation(), f).neighboursAll();
  }

  @Override
  public Set<Cell> neighboursAll() {
    Set<Cell> nbrs = new HashSet<>();

    for (int dx = x - 1; dx <= x + 1; dx++) {
      for (int dz = z - 1; dz <= z + 1; dz++) {
        nbrs.add(new Cell2d(dx, dz, extent));
      }
    }

    return nbrs;
  }

  @Override
  public Set<Cell> neighboursAdjacent() {
    Set<Cell> cells = new HashSet<>();
    
    cells.add(new Cell2d(x - 1, z, extent));
    cells.add(new Cell2d(x + 1, z, extent));
    cells.add(new Cell2d(x, z - 1, extent));
    cells.add(new Cell2d(x, z + 1, extent));
    
    return cells;
  }

  @Override
  public Cell relative(CellOffset offset) {
    if (offset instanceof CellOffset2d ofs) {
      return new Cell2d(x + ofs.dx(), z + ofs.dz(), extent);
    } 
    
    throw new IllegalArgumentException("expected CellOffset2d, got %s".formatted(offset.getClass()));
  }

}
