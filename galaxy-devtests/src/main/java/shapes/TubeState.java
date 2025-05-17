package shapes;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import materials.ShowNormalsMaterial;
import mesh.QuadFace;
import mesh.FlatShadedMesh;
import misc.DebugPointMesh;
import org.slf4j.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

public class TubeState extends BaseAppState {
  
  private static final Logger logger = getLogger(TubeState.class);
  
  private final Node scene = new Node("scene");
  
  public TubeState(Node rootNode) {
    rootNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application app) {
    float height = 2.5f;
    float ri = 5f;
    float ro = 10f;
    int radialSamples = 8;
    
    float angleDelta = FastMath.TWO_PI / radialSamples;
    logger.debug("radial sample = {}, angle delta = {} (degrees = {})", radialSamples, angleDelta, FastMath.RAD_TO_DEG * angleDelta);
    
    Quaternion rotation = new Quaternion();
    List<Vector3f> positions = new ArrayList<>(radialSamples * 4 * 2);
    logger.debug("estimated positions size = {}", radialSamples * 4 * 2);
    
    List<QuadFace> quadFaces = new ArrayList<>(radialSamples * 4);
    logger.debug("estimated faces size = {}", radialSamples * 4);
    
    for (int i = 0; i < radialSamples; i++) {
      rotation.fromAngleNormalAxis(i * angleDelta, Vector3f.UNIT_Z);
      Vector3f v0 = rotation.mult(new Vector3f(ri, 0, height));
      Vector3f v0Neg = rotation.mult(new Vector3f(ri, 0, -height));
      Vector3f v1 = rotation.mult(new Vector3f(ro, 0, height));
      Vector3f v1Neg = rotation.mult(new Vector3f(ro, 0, -height));
      
      positions.add(v0);
      positions.add(v0Neg);
      positions.add(v1);
      positions.add(v1Neg);
      
      rotation.fromAngleNormalAxis((i + 1) * angleDelta, Vector3f.UNIT_Z);
      Vector3f v2 = rotation.mult(new Vector3f(ro, 0, height));
      Vector3f v2Neg = rotation.mult(new Vector3f(ro, 0, -height));
      Vector3f v3 = rotation.mult(new Vector3f(ri, 0, height));
      Vector3f v3Neg = rotation.mult(new Vector3f(ri, 0, -height));
      
      positions.add(v2);
      positions.add(v2Neg);
      positions.add(v3);
      positions.add(v3Neg);
      
      QuadFace front = new QuadFace(v0, v1, v2, v3);
      QuadFace back = new QuadFace(v0Neg, v3Neg, v2Neg, v1Neg);
      QuadFace outer = new QuadFace(v1, v1Neg, v2Neg, v2);
      QuadFace inner = new QuadFace(v0Neg, v0, v3, v3Neg);
      
      quadFaces.add(front);
      quadFaces.add(back);
      quadFaces.add(outer);
      quadFaces.add(inner);
    }
    
    logger.debug("actual positions size = {}", positions.size());
    logger.debug("actual faces size = {}", quadFaces.size());
    
    Mesh debug = new DebugPointMesh(positions);
    Geometry geometry = new Geometry("debug", debug);
    geometry.setMaterial(new Material(app.getAssetManager(), Materials.UNSHADED));
    geometry.getMaterial().setColor("Color", ColorRGBA.Yellow);
    geometry.getMaterial().setFloat("PointSize", 4f);
    scene.attachChild(geometry);
    
    Mesh mesh = new Mesh();
    mesh.setMode(Mesh.Mode.Triangles);
    
    Vector3f[] vertices = quadFaces
        .stream()
        .flatMap(f -> f.triangles().stream())
        .flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3()))
        .toArray(Vector3f[]::new);
    FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(vertices);
    mesh.setBuffer(VertexBuffer.Type.Position, 3, positionBuffer);
    
    int[] indices = IntStream.range(0, vertices.length).toArray();
    IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices);
    mesh.setBuffer(VertexBuffer.Type.Index, 3, indexBuffer);
    
    mesh.updateCounts();
    mesh.updateBound();
    
    Geometry tube = new Geometry("tube", new FlatShadedMesh(mesh));
    tube.setMaterial(new ShowNormalsMaterial(app.getAssetManager()));
    scene.attachChild(tube);
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
