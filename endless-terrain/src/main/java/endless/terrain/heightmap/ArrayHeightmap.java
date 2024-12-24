package endless.terrain.heightmap;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class ArrayHeightmap implements Heightmap {

  private final float[] heights;
  private final int size;

  public ArrayHeightmap(float[] heights, int size) {
    this.heights = heights;
    this.size = size;
  }

  @Override
  public List<Triangle> triangles() {
    List<Triangle> triangles = new ArrayList<>(size * size * 2);
    
    for (int x = 0; x < (size - 1); x++) {
      for (int z = 0; z < (size - 1); z++) {
        Triangle a = new Triangle(
            new Vector3f(x, heights[x * size + z], z),
            new Vector3f(x + 1, heights[(x + 1) * size + z], z),
            new Vector3f(x + 1, heights[(x + 1) * size + z + 1], z + 1)
        );
        
        Triangle b = new Triangle(
            new Vector3f(x, heights[x * size + z], z),
            new Vector3f(x + 1, heights[(x + 1) * size + z + 1], z + 1),
            new Vector3f(x, heights[x * size + z + 1], z + 1)
        );
        
        triangles.add(a);
        triangles.add(b);
      }
    }
    
    return triangles;
  }
}
