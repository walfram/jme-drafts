package galaxy.tests;

import static com.jme3.math.FastMath.cos;
import static com.jme3.math.FastMath.sin;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import com.simsilica.lemur.geom.MBox;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickSetup;
import java.util.ArrayDeque;
import java.util.Deque;
import jme3utilities.mesh.Icosphere;
import mesh.FlatShadedMesh;

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

    mk2();

    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }

  private void mk2() {
    Mesh source = new MBox(2, 2, 8, 0, 0, 0);
    
    Deformation deformationPos = (v, n) -> {
      if (v.x == 2) {
        v.y *= 0.5f;
        v.z *= 0.5f;
      }
    };
    
    Geometry cargoXPos = new Geometry("cargo-x-pos", new FlatShadedMesh(new DMesh(source.clone(), deformationPos)));
    cargoXPos.setMaterial(material);
    cargoXPos.move(6, 0, 0);
    rootNode.attachChild(cargoXPos);
    
    Deformation deformationNeg = (v, n) -> {
      if (v.x == -2) {
        v.y *= 0.5f;
        v.z *= 0.5f;
      }
    };
    
    Geometry cargoXNeg = new Geometry("cargo-x-neg", new FlatShadedMesh(new DMesh(source.clone(), deformationNeg)));
    cargoXNeg.setMaterial(material);
    cargoXNeg.move(-6, 0, 0);
    rootNode.attachChild(cargoXNeg);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 8, 4, 24, true)));
    hull.setMaterial(material);
    rootNode.attachChild(hull);
    
    int engineCount = 8;
    float delta = FastMath.TWO_PI / engineCount;
    
    Mesh engineMesh = new Cylinder(2, 6, 1f, 4f, true);
    
    float radius = 4f; // same as hull
    for (int i = 0; i < engineCount; i++) {
      Geometry engine = new Geometry("engine-%s".formatted(i), engineMesh);
      engine.setMaterial(material);
      
      float dx = radius * cos(i * delta);
      float dy = radius * sin(i * delta);
      
      engine.setLocalTranslation(new Vector3f(0, 0, -12).addLocal(dx, dy, 0));
      
      rootNode.attachChild(engine);
    }
  }

  private void mk1() {
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
