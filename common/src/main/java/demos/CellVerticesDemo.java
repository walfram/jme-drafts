package demos;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import java.util.List;
import jme3utilities.debug.AxesVisualizer;
import misc.DebugPointMesh;

public class CellVerticesDemo extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    CellVerticesDemo app = new CellVerticesDemo();
    app.setSettings(settings);
    app.setShowSettings(false);

    app.start();
  }
  
  private static final float extent = 128f;

  @Override
  public void simpleInitApp() {
    Cell cell = new Cell2d(0, 0, extent);

    CellVertices cellVertices = new CellVertices(cell);
    List<Vector3f> vertices = cellVertices.asList();

    Geometry geometry = new Geometry("cell-points", new DebugPointMesh(vertices));
    geometry.setMaterial(new Material(assetManager, Materials.UNSHADED));
    geometry.getMaterial().setColor("Color", ColorRGBA.Yellow);
    geometry.getMaterial().setFloat("PointSize", 5f);
    
    rootNode.attachChild(geometry);
    
    flyCam.setDragToRotate(true);
    flyCam.setMoveSpeed(50);

    AxesVisualizer axesVisualizer = new AxesVisualizer(assetManager, 256, 1);
    rootNode.addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    cam.setLocation(new Vector3f(175.41922f, 202.48157f, 244.87985f));
    cam.setRotation(new Quaternion(-0.10297445f, 0.8955582f, -0.33196798f, -0.2777931f));
  }
}
