package galaxy.tests;

import static com.jme3.math.FastMath.HALF_PI;
import static com.jme3.math.FastMath.PI;
import static com.jme3.math.Vector3f.UNIT_X;

import cells.Cell2d;
import cells.Cell3d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import com.simsilica.lemur.geom.MBox;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import jme3utilities.MyMesh;
import jme3utilities.math.noise.Generator;
import mesh.FlatShadedMesh;

public class PlaceholderShipTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    PlaceholderShipTest app = new PlaceholderShipTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private final Generator random = new Generator(42);

  @Override
  public void simpleInitApp() {
    new QuickAppSetup().applyTo(this);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    // material.getAdditionalRenderState().setWireframe(true);

    Node randomCubes = randomCubes(material);
    rootNode.attachChild(randomCubes);
    randomCubes.move(new Cell2d(-1, -1, 32f).translation());

    Node modularShip = modularShip(material);
    rootNode.attachChild(modularShip);
    modularShip.move(new Cell2d(0, -2, 32f).translation());

    Node simpleShip = simpleShip(material);
    rootNode.attachChild(simpleShip);
    simpleShip.move(new Cell2d(-1, 0, 32f).translation());
    
    Node advancedShip = advancedShip(material);
    rootNode.attachChild(advancedShip);
    advancedShip.move(new Cell2d(1, 0, 32f).translation());
    
    Node pyramidShip = pyramidShip(material);
    rootNode.attachChild(pyramidShip);
    
    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }

  private Node pyramidShip(Material material) {
    Node ship = new Node("pyramid-ship");

    Mesh source = new Cylinder(2, 4, 0.25f, 1f, 1f, true, false);
    MyMesh.rotate(source, new Quaternion().fromAngleNormalAxis(HALF_PI, UNIT_X.negate()));
    
    Deformation deform = (v, n) -> {
      if (v.y < 0) {
        v.y = 0;
      }
      
      if (v.z > 0) {
        v.z *= 2f;
      }
    };
    
    Geometry hullTop = new Geometry("hull-top", new FlatShadedMesh(new DMesh(source, deform)));
    hullTop.setMaterial(material);
    
    ship.attachChild(hullTop);
    
    ship.scale(10);

    return ship;
  }

  private Node advancedShip(Material material) {
    Node ship = new Node("advanced-ship");

    Geometry hullFront = new Geometry("hull-front", new FlatShadedMesh(new Cylinder(2, 6, 0.125f, 1f, 1f, true, false)));
    hullFront.setMaterial(material);
    hullFront.scale(8f, 4f, 24f);
    hullFront.move(0, 0, 12f);
    
    ship.attachChild(hullFront);

    Geometry hullBack = new Geometry("hull-back", new FlatShadedMesh(new Cylinder(2, 6, 0.25f, 1f, 1f, true, false)));
    MyMesh.rotate(hullBack.getMesh(), new Quaternion().fromAngleNormalAxis(PI, UNIT_X.negate()));
    hullBack.setMaterial(material);
    hullBack.scale(8f, 4f, 4f);
    
    hullBack.move(0, 0, -2f);
    
    ship.attachChild(hullBack);
    
    return ship;
  }

  private Node simpleShip(Material material) {
    Node ship = new Node("simple-ship");

    Geometry hullFront = new Geometry("hull-front", new FlatShadedMesh(new Dome(new Vector3f(), 2, 4, 1, false)));
    MyMesh.rotate(hullFront.getMesh(), new Quaternion().fromAngleNormalAxis(HALF_PI, UNIT_X));
    hullFront.setMaterial(material);
    hullFront.scale(8f, 4f, 24f);

    ship.attachChild(hullFront);
    
    Geometry hullBack = new Geometry("hull-back", new FlatShadedMesh(new Dome(new Vector3f(), 2, 4, 1, false)));
    MyMesh.rotate(hullBack.getMesh(), new Quaternion().fromAngleNormalAxis(HALF_PI, UNIT_X.negate()));
    hullBack.setMaterial(material);
    hullBack.scale(8f, 4f, 4f);
    
    ship.attachChild(hullBack);

    return ship;
  }

  private Node modularShip(Material material) {
    Node ship = new Node("modular-ship");

    float cellExt = 4f;
    
    Mesh cargoBatchMesh = new WireBox(1, 1, 1);
    Mesh cylinder = new FlatShadedMesh(new Cylinder(2, 6, 0.5f, 1f, true));
    
    int[] xCargoExt = {-1, 0, 1};
    int[] yCargoExt = {-1, 0};
    int[] zCargoExt = {5, 4, 3, 2, 1};
    
    for (int x: xCargoExt) {
      for (int y: yCargoExt) {
        for (int z: zCargoExt) {
          Geometry g = new Geometry("cargo-batch", cargoBatchMesh);
          
          g.setMaterial(material);
          g.setLocalTranslation(new Cell3d(x, y, z, cellExt).translation());
          g.scale(cellExt * 0.95f);
          
          ship.attachChild(g);
        }
      }
    }

    Geometry sideNeg = new Geometry("side-neg", cylinder);
    sideNeg.setMaterial(material);
    ship.attachChild(sideNeg);
    sideNeg.scale(2f * cellExt, 2f * cellExt, 7f * 2f * cellExt);
    sideNeg.move(new Cell3d(-2, 0, 3, cellExt).translation());

    Geometry sidePos = new Geometry("side-pos", cylinder);
    sidePos.setMaterial(material);
    ship.attachChild(sidePos);
    sidePos.scale(2f * cellExt, 2f * cellExt, 7f * 2f * cellExt);
    sidePos.move(new Cell3d(2, 0, 3, cellExt).translation());
    
    return ship;
  }

  private Node randomCubes(Material material) {
    Node ship = new Node("cube-ship-placeholder");

    float minExtent = 4f;
    float maxExtent = 8f;

    Mesh mesh = new MBox(1, 1, 1, 2, 2, 2);

    for (int i = 0; i < 8; i++) {
      Geometry geometry = new Geometry("box-%s".formatted(i), mesh);
      geometry.setMaterial(material);

      float xs = random.nextFloat(minExtent, maxExtent);
      float ys = random.nextFloat(minExtent, maxExtent);
      float zs = random.nextFloat(minExtent, maxExtent);

      geometry.scale(xs, ys, zs);
      ship.attachChild(geometry);

      Vector3f t = random.nextVector3f().mult(minExtent + maxExtent);
      geometry.setLocalTranslation(t);
    }

    return ship;
  }
}
