package tests;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.DMesh;
import com.simsilica.lemur.geom.Deformation;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import jme3utilities.mesh.Octasphere;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModularShipTest4 extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    ModularShipTest4 app = new ModularShipTest4();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(ModularShipTest4.class);

  private static final float cellExtent = 4f;
  private static final float halfExtent = cellExtent * 0.5f;

  private Material material;

  @Override
  public void simpleInitApp() {
    new QuickAppSetup(4, 32).applyTo(this);

    material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    // drone();
    // tube();

    Mesh source = new Cylinder(6, 8, cellExtent, 5f * 2f * cellExtent, true);

    Deformation xPosCut = (v, n) -> {
      if (v.x >= 0) {
        v.x = 0;
      }
    };

    Deformation xNegCut = (v, n) -> {
      if (v.x <= 0) {
        v.x = 0;
      }
    };

    Geometry hullXNeg = new Geometry("hull-x-neg", new FlatShadedMesh(new DMesh(source, xPosCut)));
    hullXNeg.setMaterial(material);
    rootNode.attachChild(hullXNeg);
    hullXNeg.move(new Cell2d(-1, 3, cellExtent).translation().add(cellExtent, 0, 0));
    hullXNeg.scale(2, 1, 1);

    logger.debug("hull x neg bound = {}", hullXNeg.getWorldBound());

    Geometry hullXPos = new Geometry("hull-x-pos", new FlatShadedMesh(new DMesh(source, xNegCut)));
    hullXPos.setMaterial(material);
    rootNode.attachChild(hullXPos);
    hullXPos.move(new Cell2d(1, 3, cellExtent).translation().add(-cellExtent, 0, 0));
    hullXPos.scale(2, 1, 1);

    logger.debug("hull x pos bound = {}", hullXPos.getWorldBound());

    int[] zs = {5, 4, 3, 2, 1};
    for (int z : zs) {
      Geometry container = new Geometry("container", new Octasphere(1, cellExtent));
      container.setMaterial(material);
      container.setLocalTranslation(new Cell2d(0, z, cellExtent).translation());
      rootNode.attachChild(container);
    }

    Geometry hullBase = new Geometry("hull-base", new FlatShadedMesh(new Cylinder(2, 8, 3f * cellExtent, 2f * cellExtent, true)));
    hullBase.setMaterial(material);
    rootNode.attachChild(hullBase);
    hullBase.move(new Cell2d(0, 0, cellExtent).translation());
    hullBase.scale(1, 0.5f, 1);

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(new Cylinder(2, 8, 0.5f * cellExtent, cellExtent, cellExtent, true, false)));
    bridge.setMaterial(material);
    bridge.rotate(-FastMath.HALF_PI, 0, 0);
    bridge.move(new Cell2d(0, 0, cellExtent).translation().add(0, 1.5f * cellExtent, 0));
    rootNode.attachChild(bridge);

    Geometry engineCenter = new Geometry("engine-center", new FlatShadedMesh(new Cylinder(2, 8, cellExtent, 0.25f * cellExtent, true)));
    engineCenter.setMaterial(material);
    engineCenter.setLocalTranslation(new Cell2d(0, -1, cellExtent).translation().add(0, 0, (0.75f + 0.125f) * cellExtent));
    rootNode.attachChild(engineCenter);

    Geometry engineXNeg = new Geometry("engine-x-neg", new FlatShadedMesh(new Cylinder(2, 8, 0.5f * cellExtent, 0.25f * cellExtent, true)));
    engineXNeg.setMaterial(material);
    engineXNeg.setLocalTranslation(new Cell2d(-1, -1, cellExtent).translation().add(0, 0, (0.75f + 0.125f) * cellExtent));
    rootNode.attachChild(engineXNeg);

    Geometry engineXPos = new Geometry("engine-x-pos", new FlatShadedMesh(new Cylinder(2, 8, 0.5f * cellExtent, 0.25f * cellExtent, true)));
    engineXPos.setMaterial(material);
    engineXPos.setLocalTranslation(new Cell2d(1, -1, cellExtent).translation().add(0, 0, (0.75f + 0.125f) * cellExtent));
    rootNode.attachChild(engineXPos);

    new QuickChaseCamera(cam, inputManager).attachTo(rootNode);
  }

  private void tube() {
    Node tube = new Node("tube");
    rootNode.attachChild(tube);

    Geometry top = new Geometry("top", new FlatShadedMesh(new Torus(6, 2, 0.5f * cellExtent, cellExtent)));
    top.setMaterial(material);
    tube.attachChild(top);
    top.move(0, 0, cellExtent);

    Geometry inner = new Geometry("inner", new FlatShadedMesh(new Cylinder(2, 6, 0.5f * cellExtent, 2f * cellExtent, false, true)));
    inner.setMaterial(material);
    tube.attachChild(inner);

    Geometry outer = new Geometry("outer", new FlatShadedMesh(new Cylinder(2, 6, 1.5f * cellExtent, 2f * cellExtent, false)));
    outer.setMaterial(material);
    tube.attachChild(outer);

    tube.scale(1, 1, 2);
  }

  private void drone() {
    Node drone = new Node("drone");
    rootNode.attachChild(drone);

    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 8, cellExtent, 2f * cellExtent, true)));
    hull.setMaterial(material);
    drone.attachChild(hull);

    Geometry engine = new Geometry("engine", new FlatShadedMesh(new Cylinder(2, 8, 0.25f * cellExtent, cellExtent, cellExtent, true, false)));
    engine.setMaterial(material);
    drone.attachChild(engine);
    engine.move(new Cell2d(0, -1, cellExtent).translation().add(0, 0, cellExtent * 0.5f));
  }
}
