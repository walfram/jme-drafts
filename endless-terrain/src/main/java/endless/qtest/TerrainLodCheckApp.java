package endless.qtest;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.mesh.IndexBuffer;
import com.jme3.system.AppSettings;
import com.jme3.util.BufferUtils;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import endless.terrain.heightmap.CellHeightmap;
import endless.terrain.heightmap.Heightmap;
import endless.terrain.heightmap.TerrainChunk;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;
import misc.DebugPointMesh;
import noise.FastNoiseLite;
import noise.FastNoiseLite.FractalType;
import noise.FastNoiseLite.NoiseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerrainLodCheckApp extends SimpleApplication {

  private Geometry geometry;

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    TerrainLodCheckApp app = new TerrainLodCheckApp();
    app.setSettings(settings);
    app.setShowSettings(false);

    app.start();
  }

  private final FastNoiseLite noise = new FastNoiseLite(42);

  {
    noise.SetFrequency(0.001f);
    noise.SetNoiseType(NoiseType.Value);
    noise.SetFractalType(FractalType.PingPong);
  }

  private static final Logger logger = LoggerFactory.getLogger(TerrainLodCheckApp.class);
  
  @Override
  public void simpleInitApp() {
    AxesVisualizer axesVisualizer = new AxesVisualizer(assetManager, 512, 1);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(256);
    flyCam.setZoomSpeed(0);

    cam.setLocation(new Vector3f(171.98265f, 1724.5903f, 1466.3348f));
    cam.setRotation(new Quaternion(-0.018309824f, 0.88563335f, -0.4626988f, -0.035045967f));

    MyCamera.setNearFar(cam, cam.getFrustumNear(), 32768f);

//    Geometry debugGrid = new Geometry("debug-grid", new Grid(16, 16, 64));
//    debugGrid.center();
//    debugGrid.setMaterial(new Material(assetManager, Materials.UNSHADED));
//    debugGrid.getMaterial().setColor("Color", ColorRGBA.Blue);
//    rootNode.attachChild(debugGrid);

    Cell cell = new Cell2d(0, 0, 1024f);

    Heightmap heightmap = new CellHeightmap(cell, this::calculateHeight, 5);
    TerrainChunk terrainChunk = new TerrainChunk(heightmap, new int[]{2, 4, 4});

    Mesh mesh = terrainChunk.mesh();
    logger.debug("mesh triangles = {}, vertices = {}", mesh.getTriangleCount(), mesh.getVertexCount());

    geometry = new Geometry("chunk", mesh);
    geometry.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md"));
    geometry.getMaterial().getAdditionalRenderState().setWireframe(true);
    rootNode.attachChild(geometry);
    
    logger.debug("geometry lods = {}", mesh.getNumLodLevels());
    
    geometry.setLodLevel(0);

    GuiGlobals.initialize(this);
    
    Container container = new Container();
    container.addChild(new Button("lod 0")).addClickCommands(b -> setLodLevel(0));
    container.addChild(new Button("lod 1")).addClickCommands(b -> setLodLevel(1));
    container.addChild(new Button("lod 2")).addClickCommands(b -> setLodLevel(2));
    container.addChild(new Button("lod 3")).addClickCommands(b -> setLodLevel(3));
    
    guiNode.attachChild(container);
    container.setLocalTranslation(10, cam.getHeight() - 10, 0);
  }
  
  private void setLodLevel(int level) {
    Mesh mesh = geometry.getMesh();
    FloatBuffer positionBuffer = mesh.getFloatBuffer(Type.Position);
    Vector3f[] vertices = BufferUtils.getVector3Array(positionBuffer);

    VertexBuffer lodLevel = mesh.getLodLevel(level);
    IndexBuffer indexBuffer = IndexBuffer.wrapIndexBuffer(lodLevel.getData());

    int[] indices = BufferUtils.getIntArray((IntBuffer) indexBuffer.getBuffer());
    logger.debug("lod level = {}, indices = {}", level, indices);

    List<Vector3f> points = Arrays.stream(indices).mapToObj(idx -> vertices[idx]).toList();
    logger.debug("points = {}", points);

    Geometry g = new Geometry("debug-point-mesh", new DebugPointMesh(points));
    g.setMaterial(new Material(assetManager, Materials.UNSHADED));
    g.getMaterial().setColor("Color", ColorRGBA.Yellow);
    g.getMaterial().setFloat("PointSize", 16f);
    
    rootNode.detachChildNamed(g.getName());
    rootNode.attachChild(g);
  }

  private float calculateHeight(float x, float z) {
    float e = noise.GetNoise(x, z);

    if (e < 0) {
      e = 0f;
    }

    return e * 265f;
  }
}
