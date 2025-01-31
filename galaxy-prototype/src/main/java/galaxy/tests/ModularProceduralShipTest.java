package galaxy.tests;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickSetup;
import java.util.ArrayDeque;
import java.util.Deque;
import jme3utilities.mesh.Icosphere;

public class ModularProceduralShipTest extends SimpleApplication {

  private Material material;

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    ModularProceduralShipTest app = new ModularProceduralShipTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  @Override
  public void simpleInitApp() {
    new QuickSetup().applyTo(this);

    material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    Deque<Spatial> modules = new ArrayDeque<>();

    modules.push(engines());

    modules.push(connector());
    modules.push(hull());
    modules.push(connector());
    modules.push(hull());
    modules.push(connector());
    modules.push(hull());

    modules.push(connector());
    modules.push(bridge());

    float cellExtent = 8f;
    
    float dz = 0;
    for (Spatial spatial: modules) {
      spatial.move(0, 0, dz);
      rootNode.attachChild(spatial);
      
      BoundingBox bound = (BoundingBox) spatial.getWorldBound();
      dz -= bound.getZExtent() * 2f;
    }

    
    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }

  private Spatial bridge() {
    Geometry geometry = new Geometry("bridge", new Icosphere(1, 5));
    geometry.setMaterial(material);
    return geometry;
  }

  private Spatial hull() {
    Geometry geometry = new Geometry("hull", new Cylinder(2, 6, 8, 5, true));
    geometry.setMaterial(material);
    geometry.move(0, -3, 0);
    return geometry;
  }

  private Spatial connector() {
    // new Cylinder(2, 8, 5, 5, true)
    Geometry geometry = new Geometry("connector", new Box(2.5f, 2.5f, 2.5f));
    geometry.setMaterial(material);
    return geometry;
  }

  private Spatial engines() {
    Geometry geometry = new Geometry("engines", new Cylinder(2, 8, 5, 10, 5, true, false));
    geometry.setMaterial(material);
    return geometry;
  }
}
