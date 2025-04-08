package tests;

import static com.jme3.math.FastMath.ZERO_TOLERANCE;
import static com.jme3.math.FastMath.abs;
import static com.jme3.math.FastMath.cos;
import static com.jme3.math.FastMath.sign;
import static com.jme3.math.FastMath.sin;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import com.simsilica.lemur.geom.MBox;
import com.simsilica.lemur.style.BaseStyles;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickAppSetup;
import java.util.ArrayDeque;
import java.util.Deque;
import jme3utilities.mesh.Icosphere;
import jme3utilities.mesh.Octahedron;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModularProceduralShipTest extends SimpleApplication {

  private static final Logger logger = LoggerFactory.getLogger(ModularProceduralShipTest.class);

  private final Node scene = new Node("scene");

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
    new QuickAppSetup().applyTo(this);

    GuiGlobals.initialize(this);
    BaseStyles.loadGlassStyle();
    GuiGlobals.getInstance().getStyles().setDefaultStyle(BaseStyles.GLASS);

    initGui();

    material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    // material.getAdditionalRenderState().setWireframe(true);

    rootNode.attachChild(scene);

    new QuickChaseCamera(cam, inputManager).attachTo(scene);
  }

  private void initGui() {
    Container container = new Container();
    container.addChild(new Label("ships")).setMaxWidth(320);
    
    container.addChild(new Button("mk1")).addClickCommands(b -> {
      clear();
      mk1();
    });
    container.addChild(new Button("mk2")).addClickCommands(b -> {
      clear();
      mk2();
    });
    container.addChild(new Button("mk3")).addClickCommands(b -> {
      clear();
      mk3();
    });
    container.addChild(new Button("mk4")).addClickCommands(b -> {
      clear();
      mk4();
    });
    container.addChild(new Button("mk5")).addClickCommands(b -> {
      clear();
      mk5();
    });
    
    container.addChild(new Button("mk6")).addClickCommands(b -> {
      clear();
      mk6();
    });
    
    container.addChild(new Button("mk7")).addClickCommands(b -> {
      clear();
      mk7();
    });
    
    container.addChild(new Button("mk8")).addClickCommands(b -> {
      clear();
      mk8();
    });
    
    guiNode.attachChild(container);
    container.setLocalTranslation(10, cam.getHeight() - 10, 0);
  }

  private void mk8() {
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 6, 1f, 1f, true)));
    hull.setMaterial(material);
    hull.scale(16f, 4f, 64f);
    scene.attachChild(hull);
  }

  private void mk7() {
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Torus(6, 3, 5, 10)));
    hull.setMaterial(material);
    scene.attachChild(hull);
    
    for (int i = 0; i < 3; i++) {
      Geometry geometry = new Geometry("arm", new Box(3, 1, 32));
      geometry.setMaterial(material);
      geometry.move(10, 0, 10);
      
      Node arm = new Node("arm");
      arm.attachChild(geometry);
      
      scene.attachChild(arm);
      
      arm.rotate(0, 0, FastMath.DEG_TO_RAD * 120 * i);
    }
    
  }

  private void mk6() {
    Mesh mesh = new DMesh(new Octahedron(10, true), (v, n) -> {
      if (v.z == -10) {
        v.z = -1f;
      }  
    });
    
    Geometry ship = new Geometry("ship", mesh);
    ship.setMaterial(material);
    scene.attachChild(ship);
    
    ship.scale(4, 2, 16);
  }

  private void clear() {
    scene.detachAllChildren();
  }

  private void mk5() {
    float extent = 32f;

    Mesh source = new Cylinder(4, 6, 4, 16, 2f * extent, true, false);

    Mesh mesh = new DMesh(source, (v, n) -> {
      if (abs(v.y) > ZERO_TOLERANCE) {
        v.y = 8f * sign(v.y);
      }

      if (abs(v.y) < ZERO_TOLERANCE) {
        float factor = FastMath.unInterpolateLinear(v.z, 2f * extent, -extent);
        v.x = extent * factor * sign(v.x);
      }
    });

    Geometry hull = new Geometry("hull", new FlatShadedMesh(source));
    hull.setMaterial(material);
    scene.attachChild(hull);
    new ScaleUpTo(extent, 8, extent, hull).scale();

    Geometry cargo = new Geometry("cargo", new FlatShadedMesh(new Cylinder(2, 6, extent, 2f * extent, true)));
    cargo.setMaterial(material);
    scene.attachChild(cargo);
    new ScaleUpTo(extent, 8f, extent, cargo).scale();
    cargo.setLocalTranslation(new Cell2d(0, -1, extent).translation());
  }

  private void mk4() {
    float cellExtent = 32f;

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(new Cylinder(4, 6, 5, 10, 24, true, false)));
    bridge.setMaterial(material);
    scene.attachChild(bridge);
    bridge.setLocalTranslation(new Cell2d(0, 1, cellExtent).translation());
    bridge.scale(2, 1, 1);
    bridge.move(0, 0, -20);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(4, 6, 10, 2f * cellExtent, true)));
    hull.setMaterial(material);
    scene.attachChild(hull);
    hull.setLocalTranslation(new Cell2d(0, 0, cellExtent).translation());
    hull.scale(2, 1, 1);

    Geometry left = new Geometry("left", new FlatShadedMesh(new Cylinder(4, 6, 5, cellExtent, true)));
    left.setMaterial(material);
    scene.attachChild(left);
    left.move(24, 0, 0);

    Geometry right = new Geometry("right", new FlatShadedMesh(new Cylinder(4, 6, 5, cellExtent, true)));
    right.setMaterial(material);
    scene.attachChild(right);
    right.move(-24, 0, 0);
  }

  // truncated pyramid
  private void mk3() {
    float xExtent = 8f;
    float yExtent = 8f;
    float zExtent = 24f;

    Mesh source = new MBox(xExtent, yExtent, zExtent, 4, 4, 4);

    Deformation deformation = (v, n) -> {
      float factor = FastMath.unInterpolateLinear(v.z, 2f * zExtent, -zExtent);
      logger.debug("z = {}, factor = {}", v.z, factor);

      v.x *= factor;
      v.y *= factor;
    };

    Mesh mesh = new DMesh(source, deformation);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(mesh));
    hull.setMaterial(material);

    scene.attachChild(hull);
  }

  // combination of deformed shapes and 3d primitives
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
    scene.attachChild(cargoXPos);

    Deformation deformationNeg = (v, n) -> {
      if (v.x == -2) {
        v.y *= 0.5f;
        v.z *= 0.5f;
      }
    };

    Geometry cargoXNeg = new Geometry("cargo-x-neg", new FlatShadedMesh(new DMesh(source.clone(), deformationNeg)));
    cargoXNeg.setMaterial(material);
    cargoXNeg.move(-6, 0, 0);
    scene.attachChild(cargoXNeg);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 8, 4, 24, true)));
    hull.setMaterial(material);
    scene.attachChild(hull);

    int engineCount = 8;
    float delta = FastMath.TWO_PI / engineCount;

    Mesh engineMesh = new Cylinder(2, 6, 1f, 4f, true);

    float radius = 3f;
    for (int i = 0; i < engineCount; i++) {
      Geometry engine = new Geometry("engine-%s".formatted(i), engineMesh);
      engine.setMaterial(material);

      float dx = radius * cos(i * delta);
      float dy = radius * sin(i * delta);

      engine.setLocalTranslation(new Vector3f(0, 0, -14).addLocal(dx, dy, 0));

      scene.attachChild(engine);
    }

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(new Icosphere(1, 5)));
    bridge.setMaterial(material);
    bridge.move(0, 0, 15);
    scene.attachChild(bridge);
  }

  // simple shapes in a row
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

    float dz = 0;
    for (Spatial spatial : modules) {
      spatial.move(0, 0, dz);
      scene.attachChild(spatial);

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
