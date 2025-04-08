package tests;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import debug.QuickChaseCamera;
import debug.QuickAppSetup;
import jme3utilities.mesh.Octasphere;
import jme3utilities.mesh.Prism;
import mesh.FlatShadedMesh;

public class ShipShapeTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    ShipShapeTest app = new ShipShapeTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  @Override
  public void simpleInitApp() {
    new QuickAppSetup().applyTo(this);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    // material.getAdditionalRenderState().setWireframe(true);

    mk5(material);

    new QuickChaseCamera(cam, inputManager).attachTo(rootNode);
  }
  
  private void mk5(Material material) {
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 8, 5, 20, true)));
    hull.setMaterial(material);
    rootNode.attachChild(hull);

    Geometry module = new Geometry("module", new Octasphere(1, 5f));
    module.setMaterial(material);
    rootNode.attachChild(module);
    module.move(0, 0, 15f);
  }
  
  private void mk4(Material material) {
    Geometry geometry = new Geometry("", new Prism(3, 20, 5, true));
    geometry.setMaterial(material);
    rootNode.attachChild(geometry);
  }

  private void mk3(Material material) {
    Geometry geometry = new Geometry("", new FlatShadedMesh(new Cylinder(2, 3, 5, 15, 30, true, false)));
    geometry.setMaterial(material);
    rootNode.attachChild(geometry);
  }

  private void mk2(Material material) {
    Geometry body = new Geometry("body", new FlatShadedMesh(new Cylinder(3, 3, 5, 40, true)));
    body.setMaterial(material);
    rootNode.attachChild(body);

    Geometry geometry = new Geometry("", new FlatShadedMesh(new Cylinder(4, 6, 10, 5, true)));
    geometry.setMaterial(material);
    geometry.scale(0.75f, 1.25f, 1f);
    rootNode.attachChild(geometry);
  }

  private void mk1(Material material) {
    Geometry torus = new Geometry("", new FlatShadedMesh(new Torus(8, 4, 5, 20)));
    torus.setMaterial(material);
    rootNode.attachChild(torus);

    Geometry cylinder = new Geometry("", new FlatShadedMesh(new Cylinder(4, 8, 10, 60, true)));
    cylinder.setMaterial(material);
    rootNode.attachChild(cylinder);
  }

}
