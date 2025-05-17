package ship.sboat;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import jme3utilities.mesh.RoundedRectangle;
import materials.ShowNormalsMaterial;
import mesh.FlatShadedMesh;
import misc.DebugPointMesh;

public class SBoatMk4State extends BaseAppState {
  
  private final Node scene = new Node("scene");
  
  public SBoatMk4State(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
//    roundedRectTest();
    
//    float[] vertices = {
//        -4.5f, -7.5f, 0.0f,   // v0
//        4.5f, -7.5f, 0.0f,   // v1
//        7.5f, -4.5f, 0.0f,   // v2
//        7.5f, 4.5f, 0.0f,   // v3
//        4.5f, 7.5f, 0.0f,   // v4
//        -4.5f, 7.5f, 0.0f,   // v5
//        -7.5f, 4.5f, 0.0f,   // v6
//        -7.5f, -4.5f, 0.0f,   // v7
//        0.0f, 0.0f, 0.0f    // v8 (center)
//    };
//    int[] indices = {
//        8, 0, 1,
//        8, 1, 2,
//        8, 2, 3,
//        8, 3, 4,
//        8, 4, 5,
//        8, 5, 6,
//        8, 6, 7,
//        8, 7, 0
//    };
    float[] vertices = createSymmetricBeveledRect(10, 4, 4, 8);
    int[] indices = createOctagonFanIndices();
    
    Mesh debugPointMesh = new DebugPointMesh(vertices);
    
    Geometry debugPoints = new Geometry("debug-points", debugPointMesh);
    Material material = new Material(app.getAssetManager(), Materials.UNSHADED);
    material.setFloat("PointSize", 4f);
    debugPoints.setMaterial(material);
    scene.attachChild(debugPoints);
    
    Mesh mesh = new Mesh();
    mesh.setMode(Mesh.Mode.Triangles);
    
    mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
    mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indices));
    
//    Vector3f[] normals = IntStream
//        .range(0, indices.length / 3)
//        .mapToObj(idx -> new Vector3f(0, 0, 1))
//        .toArray(Vector3f[]::new);
//    mesh.setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(normals));
    
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
  
  private float[] createSymmetricBeveledRect(
      float topEdgeWidth,
      float topEdgeY,
      float sideEdgeHeight,
      float sideEdgeX
  ) {
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
    
    return new float[] {
        // Bottom edge
        xLeftInner, yBottom, 0f,     // v0
        xRightInner, yBottom, 0f,    // v1
        
        // Right edge
        xRight, yBottomInner, 0f,    // v2
        xRight, yTopInner, 0f,       // v3
        
        // Top edge
        xRightInner, yTop, 0f,       // v4
        xLeftInner, yTop, 0f,        // v5
        
        // Left edge
        xLeft, yTopInner, 0f,        // v6
        xLeft, yBottomInner, 0f,     // v7
        
        // Center
        0f, 0f, 0f                   // v8
    };
  }
  
  private void roundedRectTest() {
    Mesh mesh = new RoundedRectangle();
    
    Geometry geometry = new Geometry("rounded-rect", mesh);
    Material material = new ShowNormalsMaterial(getApplication().getAssetManager());
    material.getAdditionalRenderState().setWireframe(true);
    geometry.setMaterial(material);
    scene.attachChild(geometry);
    
    geometry.scale(10);
    geometry.center();
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
