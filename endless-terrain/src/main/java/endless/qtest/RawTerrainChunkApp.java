package endless.qtest;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.system.AppSettings;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;
import misc.DebugPointMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawTerrainChunkApp extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    RawTerrainChunkApp app = new RawTerrainChunkApp();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(RawTerrainChunkApp.class);

  @Override
  public void simpleInitApp() {
    AxesVisualizer axesVisualizer = new AxesVisualizer(assetManager, 128, 1);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(100);
    cam.setLocation(new Vector3f(120.76262f, 339.73312f, 318.61066f));
    cam.setRotation(new Quaternion(-0.06442851f, 0.8863848f, -0.43965718f, -0.12989435f));
    MyCamera.setNearFar(cam, cam.getFrustumNear(), 5000);

    Cell cell = new Cell2d(0, 0, 128f);
    logger.debug("using cell = {}", cell);

    int baseQuads = 4;
    int pointsPerSide = baseQuads + 1;

    float quadSize = (2f * cell.extent()) / baseQuads;
    logger.debug("cell extent = {}, size = {}, quad size = {}", cell.extent(), 2f * cell.extent(), quadSize);

    int triangleCount = baseQuads * baseQuads * 2;
    logger.debug("estimated triangle count = {}", triangleCount);

    List<Triangle> triangles = new ArrayList<>(triangleCount);

    for (int z = 0; z < baseQuads; z++) {
    
      float dz = z * quadSize - cell.extent();
      logger.debug("z = {}, z*quadSize = {}, dz = {}", z, z * quadSize, dz);

      for (int x = 0; x < baseQuads; x++) {
        float dx = x * quadSize - cell.extent();

        Vector3f a = new Vector3f(dx, 0, dz);
        Vector3f b = new Vector3f(dx + quadSize, 0, dz + quadSize);
        Vector3f c = new Vector3f(dx + quadSize, 0, dz);

        triangles.add(new Triangle(a, b, c));

        Vector3f d = new Vector3f(dx, 0, dz + quadSize);

        triangles.add(new Triangle(a, d, b));
      }
    }

    logger.debug("actual triangle count = {}", triangles.size());

    long pointCount = triangles.stream().flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3())).count();
    logger.debug("total point count in all triangles = {}", pointCount);

    List<Vector3f> distinctPoints = triangles.stream().flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3())).distinct().toList();
    logger.debug("distinct points = {}", distinctPoints.size());

    Geometry distinctDebugPoints = new Geometry("distinct-debug-points", new DebugPointMesh(distinctPoints));
    distinctDebugPoints.setMaterial(new Material(assetManager, Materials.UNSHADED));
    distinctDebugPoints.getMaterial().setColor("Color", ColorRGBA.Cyan);
    distinctDebugPoints.getMaterial().setFloat("PointSize", 8f);
    rootNode.attachChild(distinctDebugPoints);

    Mesh mesh = new Mesh();
    mesh.setMode(Mode.Triangles);

    Vector3f[] vertices = triangles.stream().flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3())).toArray(Vector3f[]::new);
    mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));

    Vector3f[] normals = triangles.stream().flatMap(t -> Stream.of(t.getNormal(), t.getNormal(), t.getNormal())).toArray(Vector3f[]::new);
    mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));

//    int[] indices = IntStream.range(0, triangleCount * 3).toArray();
//    int[] indices = {0, 1, 2, 18, 19, 20, 0, 95, 20};
//    int[] indices = {0, 91, 20, 3, 76, 95};
    int[] indices = {0, 31, 8, 3, 28, 35, 12, 43, 20, 15, 40, 47, 48, 79, 56, 51, 76, 83, 60, 91, 68, 63, 88, 95};
    mesh.setBuffer(Type.Index, 3, BufferUtils.createIntBuffer(indices));

    ColorRGBA[] colors = triangles.stream()
        .flatMap(t -> Stream.of(ColorRGBA.randomColor(), ColorRGBA.randomColor(), ColorRGBA.randomColor())).toArray(ColorRGBA[]::new);
    mesh.setBuffer(Type.Color, 3, BufferUtils.createFloatBuffer(colors));
    
    mesh.updateBound();
    mesh.updateCounts();

    Geometry geometry = new Geometry("geometry", mesh);
//    geometry.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md"));
    geometry.setMaterial(new Material(assetManager, Materials.UNSHADED));
    geometry.getMaterial().setBoolean("VertexColor", true);
//    geometry.getMaterial().getAdditionalRenderState().setWireframe(true);
    rootNode.attachChild(geometry);

    geometry.move(0, 1, 0);
  }
}
