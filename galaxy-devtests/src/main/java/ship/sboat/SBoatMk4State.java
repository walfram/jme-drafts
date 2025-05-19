package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import materials.ShowNormalsMaterial;
import mesh.Face;
import mesh.FlatShadedMesh;
import mesh.QuadFace;
import mesh.TriangleFace;
import misc.DebugPointMesh;
import org.slf4j.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class SBoatMk4State extends BaseAppState {
  
  private static final Logger logger = getLogger(SBoatMk4State.class);
  private final Node scene = new Node("scene");
  
  public SBoatMk4State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    Vector3f[] frontVertices = createSymmetricBeveledRect(10, 4, 4, 8, 10f);
    
    Vector3f[] backVertices = createSymmetricBeveledRect(10, 4, 4, 8, -10f);
    for (Vector3f v : backVertices) {
      v.multLocal(2, 2, 1);
    }
//    int[] indices = createOctagonFanIndices();
    
    Mesh debugPointMesh = new DebugPointMesh(frontVertices);
    
    Geometry debugPoints = new Geometry("debug-points", debugPointMesh);
    Material material = new Material(app.getAssetManager(), Materials.UNSHADED);
    material.setFloat("PointSize", 4f);
    debugPoints.setMaterial(material);
    scene.attachChild(debugPoints);
    
    logger.debug("estimates faces size = {}", 2 * 8 + 8);
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
          backVertices[idx + 1],
          frontVertices[idx + 1]
      );
      faces.add(face);
    }
    
    logger.debug("faces = {}", faces.size());
    
    Mesh mesh = new Mesh();
    mesh.setMode(Mesh.Mode.Triangles);
    
    Vector3f[] positions = faces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3()))
        .toArray(Vector3f[]::new);
    FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(positions);
    mesh.setBuffer(VertexBuffer.Type.Position, 3, positionBuffer);
    
    int[] indices = IntStream.range(0, positions.length).toArray();
    IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices);
    mesh.setBuffer(VertexBuffer.Type.Index, 3, indexBuffer);
    
    mesh.updateBound();
    mesh.updateCounts();
    
    Geometry geometry = new Geometry("octa-quad", new FlatShadedMesh(mesh));
    geometry.setMaterial(new ShowNormalsMaterial(app.getAssetManager()));
//    geometry.getMaterial().getAdditionalRenderState().setWireframe(true);
    scene.attachChild(geometry);
  }
  
  private int[] createOctagonFanIndices() {
    int[] indices = new int[8 * 3];
    int center = 8;
    for (int i = 0; i < 8; i++) {
      indices[i * 3] = center;
      indices[i * 3 + 1] = i;
      indices[i * 3 + 2] = (i + 1) % 8;
    }
    return indices;
  }
  
  private Vector3f[] createSymmetricBeveledRect(
      float topEdgeWidth,
      float topEdgeY,
      float sideEdgeHeight,
      float sideEdgeX,
      float z) {
    float halfTopWidth = topEdgeWidth / 2f;
    float halfSideHeight = sideEdgeHeight / 2f;
    
    float xLeft = -sideEdgeX;
    float xLeftInner = -halfTopWidth;
    float xRightInner = halfTopWidth;
    float xRight = sideEdgeX;
    
    float yBottom = -topEdgeY;
    float yBottomInner = -halfSideHeight;
    float yTopInner = halfSideHeight;
    float yTop = topEdgeY;
    
    return new Vector3f[]{
        // Bottom edge
        new Vector3f(xLeftInner, yBottom, z),     // v0
        new Vector3f(xRightInner, yBottom, z),    // v1
        
        // Right edge
        new Vector3f(xRight, yBottomInner, z),    // v2
        new Vector3f(xRight, yTopInner, z),       // v3
        
        // Top edge
        new Vector3f(xRightInner, yTop, z),       // v4
        new Vector3f(xLeftInner, yTop, z),        // v5
        
        // Left edge
        new Vector3f(xLeft, yTopInner, z),        // v6
        new Vector3f(xLeft, yBottomInner, z),     // v7
        
        // Center
        new Vector3f(0f, 0f, z)                   // v8
    };
  }
  
  @Override
  protected void cleanup(Application app) {
  
  }
  
  @Override
  protected void onEnable() {
  
  }
  
  @Override
  protected void onDisable() {
  
  }
}
