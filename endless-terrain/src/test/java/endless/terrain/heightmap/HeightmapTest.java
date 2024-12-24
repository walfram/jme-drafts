package endless.terrain.heightmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cells.Cell;
import cells.Cell2d;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import endless.terrain.WrappedNoise;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class HeightmapTest {

  @Test
  void should_create_heightmap_from_cell() {
    Cell cell = new Cell2d(0, 0, 32f);
    Heightmap heightmap = new CellHeightmap(cell, (x, z) -> 0f, 3);

    List<Triangle> triangles = heightmap.triangles();

    assertEquals(2 * 2 * 2, triangles.size());

    Set<Vector3f> points = triangles.stream().flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3())).collect(Collectors.toSet());

    assertEquals(9, points.size());
    
    assertTrue(points.contains(new Vector3f(-32, 0, -32)));
    assertTrue(points.contains(new Vector3f(-32, 0, 0)));
    assertTrue(points.contains(new Vector3f(-32, 0, 32)));

    assertTrue(points.contains(new Vector3f(0, 0, -32)));
    assertTrue(points.contains(new Vector3f(0, 0, 0)));
    assertTrue(points.contains(new Vector3f(0, 0, 32)));

    assertTrue(points.contains(new Vector3f(32, 0, -32)));
    assertTrue(points.contains(new Vector3f(32, 0, 0)));
    assertTrue(points.contains(new Vector3f(32, 0, 32)));
  }

  @Test
  void should_create_heightmap_from_height_array() {
    float[] heights = new float[]{
        0, 0, 1, 1,
        0, 0, 1, 1,
        0, 0, 0, 1,
        0, 0, 0, 0
    };

    // 'actual' resolution = 3
    // length = 4

    Heightmap heightmap = new ArrayHeightmap(heights, 4);
    List<Triangle> triangles = heightmap.triangles();

    assertEquals(3 * 3 * 2, triangles.size());
  }

  @Test
  void should_allow_create_heightmap_from_raw_vectors() {
    int resolution = 5;

    Vector3f[] points = new Vector3f[resolution * resolution];

    for (int x = 0; x < resolution; x++) {
      for (int z = 0; z < resolution; z++) {
        points[x * resolution + z] = new Vector3f(x, 0, z);
      }
    }

    // create heightmap
    Heightmap heightmap = new Vector3fArrayHeightmap(points, resolution);

    // use heightmap
    List<Triangle> triangles = heightmap.triangles();

    assertEquals(4 * 4 * 2, triangles.size());
  }

}
