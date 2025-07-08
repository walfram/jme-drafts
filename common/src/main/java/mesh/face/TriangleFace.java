package mesh.face;

import com.jme3.math.Triangle;
import com.jme3.math.Vector2f;
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

  @Override
  public List<Vector3f> normals() {
    Vector3f n0 = new Triangle(v0, v1, v2).getNormal();
    return List.of(n0, n0, n0);
  }

  @Override
  public int triangleCount() {
    return 1;
  }

  @Override
  public List<Vector2f> texCoords() {
    return List.of(
        new Vector2f(0, 1), new Vector2f(1, 1), new Vector2f(1, 0)
    );
  }
}
