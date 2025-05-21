package ship.sboat;

import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import mesh.Face;
import mesh.QuadFace;
import mesh.TriangleFace;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OctaCone extends Mesh {
  public record Base(float topEdgeWidth,
                     float topEdgeY,
                     float sideEdgeHeight,
                     float sideEdgeX) {
  }
  
  public OctaCone(Base front, Base back, float extent) {
    Vector3f[] frontVertices = createSymmetricBeveledRect(front, extent);
    Vector3f[] backVertices = createSymmetricBeveledRect(back, -extent);
    
    List<Face> faces = new ArrayList<>(2 * 8 + 8);
    int centerIdx = 8;
    
    for (int idx = 0; idx < 8; idx++) {
      Face face = new TriangleFace(
          frontVertices[centerIdx],
          frontVertices[idx],
          frontVertices[(idx + 1) % 8]
      );
      faces.add(face);
    }
    
    for (int idx = 0; idx < 8; idx++) {
      Face face = new TriangleFace(
          backVertices[centerIdx],
          backVertices[(idx + 1) % 8],
          backVertices[idx]
      );
      faces.add(face);
    }
    
    for (int idx = 0; idx < 8; idx++) {
      Face face = new QuadFace(
          frontVertices[idx],
          backVertices[idx],
          backVertices[(idx + 1) % 8],
          frontVertices[(idx + 1) % 8]
      );
      faces.add(face);
    }
    
    setMode(Mesh.Mode.Triangles);
    
    Vector3f[] positions = faces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3()))
        .toArray(Vector3f[]::new);
    FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(positions);
    setBuffer(VertexBuffer.Type.Position, 3, positionBuffer);
    
    int[] indices = IntStream.range(0, positions.length).toArray();
    IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices);
    setBuffer(VertexBuffer.Type.Index, 3, indexBuffer);
    
    updateBound();
    updateCounts();
  }
  
  private Vector3f[] createSymmetricBeveledRect(Base base, float z) {
    float halfTopWidth = base.topEdgeWidth / 2f;
    float halfSideHeight = base.sideEdgeHeight / 2f;
    
    float xLeft = -base.sideEdgeX;
    float xLeftInner = -halfTopWidth;
    
    float yBottom = -base.topEdgeY;
    float yBottomInner = -halfSideHeight;
    
    return new Vector3f[]{
        // Bottom edge
        new Vector3f(xLeftInner, yBottom, z),     // v0
        new Vector3f(halfTopWidth, yBottom, z),    // v1
        
        // Right edge
        new Vector3f(base.sideEdgeX, yBottomInner, z),    // v2
        new Vector3f(base.sideEdgeX, halfSideHeight, z),       // v3
        
        // Top edge
        new Vector3f(halfTopWidth, base.topEdgeY, z),       // v4
        new Vector3f(xLeftInner, base.topEdgeY, z),        // v5
        
        // Left edge
        new Vector3f(xLeft, halfSideHeight, z),        // v6
        new Vector3f(xLeft, yBottomInner, z),     // v7
        
        // Center
        new Vector3f(0f, 0f, z)                   // v8
    };
  }
  
}
