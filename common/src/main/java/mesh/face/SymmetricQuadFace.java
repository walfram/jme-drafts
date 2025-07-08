package mesh.face;

import com.jme3.math.Triangle;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

import java.util.List;
import java.util.stream.Stream;

public record SymmetricQuadFace(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f v3) implements Face {

  @Override
  public List<Triangle> triangles() {
    long negative = Stream.of(v0, v1, v2, v3).filter(v -> v.x < 0).count();

    if (negative > 2) {
      return List.of(
          new Triangle(v3, v0, v1),
          new Triangle(v3, v1, v2)
      );
//      new QuadFace(v3, v0, v1, v2).triangles();
    }

    return new QuadFace(v0, v1, v2, v3).triangles();
  }

  @Override
  public List<Vector3f> points() {
    return List.of(v0, v1, v2, v3);
  }

  @Override
  public List<Vector3f> normals() {
    return triangles().stream().flatMap(t -> Stream.of(t.getNormal(), t.getNormal(), t.getNormal())).toList();
  }

  @Override
  public int triangleCount() {
    return 2;
  }

  @Override
  public List<Vector2f> texCoords() {
    return List.of();
  }
}
