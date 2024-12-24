package endless.qtest;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import endless.terrain.heightmap.CellHeightmap;
import endless.terrain.heightmap.Heightmap;
import endless.terrain.heightmap.TerrainChunk;
import jme3utilities.MyCamera;
import jme3utilities.debug.AxesVisualizer;
import noise.FastNoiseLite;
import noise.FastNoiseLite.FractalType;
import noise.FastNoiseLite.NoiseType;

public class TerrainChunkApp extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    TerrainChunkApp app = new TerrainChunkApp();
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

  @Override
  public void simpleInitApp() {
    AxesVisualizer axesVisualizer = new AxesVisualizer(assetManager, 512, 1);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(512f);
    flyCam.setZoomSpeed(0);

    cam.setLocation(new Vector3f(301.48444f, 366.0108f, 859.8443f));
    cam.setRotation(new Quaternion(-0.023147399f, 0.97679496f, -0.15583344f, -0.14509204f));
    MyCamera.setNearFar(cam, cam.getFrustumNear(), 32768f);

    Cell cell = new Cell2d(0, 0, 2048f);

    Heightmap heightmap = new CellHeightmap(cell, this::heightAt, 129);

    TerrainChunk chunk = new TerrainChunk(heightmap, new int[]{2, 8, 16});

    Geometry chunkGeometry = new Geometry("chunk", chunk.mesh());
    chunkGeometry.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md"));
    rootNode.attachChild(chunkGeometry);

    GuiGlobals.initialize(this);
    Container container = new Container();
    container.addChild(new Button("lod 0")).addClickCommands(b -> chunkGeometry.setLodLevel(0));
    container.addChild(new Button("lod 1")).addClickCommands(b -> chunkGeometry.setLodLevel(1));
    container.addChild(new Button("lod 2")).addClickCommands(b -> chunkGeometry.setLodLevel(2));
    container.addChild(new Button("lod 3")).addClickCommands(b -> chunkGeometry.setLodLevel(3));
    container.addChild(new Button("wireframe")).addClickCommands(b -> {
      boolean isWireframe = !chunkGeometry.getMaterial().getAdditionalRenderState().isWireframe();
      chunkGeometry.getMaterial().getAdditionalRenderState().setWireframe(isWireframe);
    });

    guiNode.attachChild(container);
    container.setLocalTranslation(10, cam.getHeight() - 10, 0);
  }

  private float heightAt(float x, float z) {
    float e = noise.GetNoise(x, z);

    if (e < 0) {
      e = 0f;
    }

    return e * 265f;
  }
}
