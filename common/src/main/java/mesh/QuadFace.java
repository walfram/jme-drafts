package mesh;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;

import java.util.List;

public record QuadFace(
    Vector3f v0,
    Vector3f v1,
    Vector3f v2,
    Vector3f v3
) {
  public List<Triangle> triangles() {
    return List.of(
        new Triangle(v0, v1, v2),
        new Triangle(v0, v2, v3)
    );
  }
}
