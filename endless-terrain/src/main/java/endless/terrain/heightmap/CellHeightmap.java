package endless.terrain.heightmap;

import cells.Cell;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class CellHeightmap implements Heightmap {

  private final Cell cell;
  private final WrappedNoise wrappedNoise;
  private final int resolution;
  private final float delta;
  private final Vector3f center;

  public CellHeightmap(Cell cell, WrappedNoise wrappedNoise, int resolution) {
    this.cell = cell;
    this.wrappedNoise = wrappedNoise;
    this.resolution = resolution;

    this.delta = (cell.extent() * 2f) / (resolution - 1);
    this.center = cell.translation();
  }

  @Override
  public List<Triangle> triangles() {
    List<Triangle> triangles = new ArrayList<>(resolution * resolution * 2);

    for (int z = 0; z < (resolution - 1); z++) {
      for (int x = 0; x < (resolution - 1); x++) {
        triangles.add(
            new Triangle(
                new Vector3f(pointA(x, z)),
                new Vector3f(pointC(x, z)),
                new Vector3f(pointB(x, z))
            )
        );
        triangles.add(
            new Triangle(
                new Vector3f(pointA(x, z)),
                new Vector3f(pointD(x, z)),
                new Vector3f(pointC(x, z))
            )
        );
      }
    }

    return triangles;
  }

  @Override
  public int quadsPerSide() {
    return resolution - 1;
  }

  private Vector3f pointD(int x, int z) {
    float dx = x * delta - cell.extent();
    float dz = (z + 1) * delta - cell.extent();
    float e = wrappedNoise.noiseAt(center.x + dx, center.z + dz);
    return new Vector3f(dx, e, dz);
  }

  private Vector3f pointC(int x, int z) {
    float dx = (x + 1) * delta - cell.extent();
    float dz = (z + 1) * delta - cell.extent();
    float e = wrappedNoise.noiseAt(center.x + dx, center.z + dz);
    return new Vector3f(dx, e, dz);
  }

  private Vector3f pointB(int x, int z) {
    float dx = (x + 1) * delta - cell.extent();
    float dz = z * delta - cell.extent();
    float e = wrappedNoise.noiseAt(center.x + dx, center.z + dz);
    return new Vector3f(dx, e, dz);
  }

  private Vector3f pointA(int x, int z) {
    float dx = x * delta - cell.extent();
    float dz = z * delta - cell.extent();
    float e = wrappedNoise.noiseAt(center.x + dx, center.z + dz);
    return new Vector3f(dx, e, dz);
  }
}
