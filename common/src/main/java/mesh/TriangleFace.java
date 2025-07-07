package mesh;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;

import java.util.List;

public record TriangleFace(
    Vector3f v0,
    Vector3f v1,
    Vector3f v2
) implements Face {
  
  @Override
  public List<Triangle> triangles() {
    return List.of(new Triangle(v0, v1, v2));
  }

  @Override
  public List<Vector3f> points() {
    return List.of(v0, v1, v2);
  }
}
