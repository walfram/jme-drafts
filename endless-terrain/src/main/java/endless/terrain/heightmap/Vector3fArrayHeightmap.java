package endless.terrain.heightmap;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class Vector3fArrayHeightmap implements Heightmap {

  private final Vector3f[] points;
  private final int resolution;

  public Vector3fArrayHeightmap(Vector3f[] points, int resolution) {
    this.points = points;
    this.resolution = resolution;
  }

  @Override
  public List<Triangle> triangles() {
    List<Triangle> triangles = new ArrayList<>(resolution * resolution * 2);

    for (int x = 0; x < (resolution - 1); x++) {
      for (int z = 0; z < (resolution - 1); z++) {
        triangles.add(
            new Triangle(
                points[x * resolution + z],
                points[(x + 1) * resolution + z],
                points[(x + 1) * resolution + z + 1]
            )
        );

        triangles.add(
            new Triangle(
                points[x * resolution + z],
                points[(x + 1) * resolution + z + 1],
                points[x * resolution + z + 1]
            )
        );
      }
    }

    return triangles;
  }
}
