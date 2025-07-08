package mesh;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.mesh.IndexBuffer;
import com.jme3.util.BufferUtils;
import mesh.face.Face;
import mesh.face.TriangleFace;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FlatShadedMesh extends Mesh {

  public FlatShadedMesh(Mesh source) {
    this(trianglesOf(source));
  }

  public FlatShadedMesh(List<Face> faces) {
    setMode(Mode.Triangles);

    setBuffer(Type.Position, 3,
        floatBuffer(
            faces.stream().flatMap(f -> f.points().stream()).toArray(Vector3f[]::new)
        )
    );

    setBuffer(Type.Normal, 3,
        floatBuffer(
            faces.stream().flatMap(f -> f.normals().stream()).toArray(Vector3f[]::new)
        )
    );

    int triangleCount = faces.stream().mapToInt(Face::triangleCount).sum();
    setBuffer(Type.Index, 3, intBuffer(IntStream.range(0, triangleCount * 3).toArray()));

    setBuffer(Type.TexCoord, 2,
      floatBuffer(
          faces.stream().flatMap(f -> f.texCoords().stream()).toArray(Vector2f[]::new)
      )
    );

    updateBound();
    updateCounts();
  }

//  @Deprecated
//  public FlatShadedMesh(List<Triangle> triangles) {
//    setMode(Mode.Triangles);
//    setBuffer(Type.Position, 3,
//        floatBuffer(
//            triangles.stream().flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3())).toArray(Vector3f[]::new)
//        )
//    );
//    setBuffer(Type.Normal, 3,
//        floatBuffer(
//            triangles.stream().flatMap(t -> Stream.of(t.getNormal(), t.getNormal(), t.getNormal())).toArray(Vector3f[]::new)
//        )
//    );
//    setBuffer(Type.Index, 3,
//        intBuffer(
//            IntStream.range(0, triangles.size() * 3).toArray()
//        )
//    );
//    updateBound();
//    updateCounts();
//  }

  private static List<Face> trianglesOf(Mesh source) {
    IndexBuffer indexBuffer = source.getIndicesAsList();
    Vector3f[] vertices = BufferUtils.getVector3Array(source.getFloatBuffer(Type.Position));

    int triangleCount = indexBuffer.size() / 3;
    List<Face> triangles = new ArrayList<>(triangleCount);

    for (int idx = 0; idx < triangleCount; idx++) {
      int tidx0 = idx * 3;
      int tidx1 = idx * 3 + 1;
      int tidx2 = idx * 3 + 2;
      triangles.add(
          new TriangleFace(
              vertices[indexBuffer.get(tidx0)],
              vertices[indexBuffer.get(tidx1)],
              vertices[indexBuffer.get(tidx2)]
          )
      );
    }

    return triangles;
  }

  private static IntBuffer intBuffer(int[] source) {
    return BufferUtils.createIntBuffer(source);
  }

  private static FloatBuffer floatBuffer(Vector3f[] source) {
    return BufferUtils.createFloatBuffer(source);
  }

  private static FloatBuffer floatBuffer(Vector2f[] source) {
    return BufferUtils.createFloatBuffer(source);
  }

}
