package galaxy.tests;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import debug.QuickChaseCamera;
import debug.QuickSetup;
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
    new QuickSetup().applyTo(this);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    // material.getAdditionalRenderState().setWireframe(true);
    
    Geometry body = new Geometry("body", new FlatShadedMesh(new Cylinder(3, 3, 5, 40, true)));
    body.setMaterial(material);
    rootNode.attachChild(body);

    Geometry geometry = new Geometry("", new FlatShadedMesh(new Cylinder(4, 6, 10, 5, true)));
    geometry.setMaterial(material);
    geometry.scale(0.75f, 1.25f, 1f);
    rootNode.attachChild(geometry);
    
    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }


//  Geometry torus = new Geometry("", new FlatShadedMesh(new Torus(8, 4, 5, 20)));
//    torus.setMaterial(material);
//    rootNode.attachChild(torus);
//
//  Geometry cylinder = new Geometry("", new FlatShadedMesh(new Cylinder(4, 8, 10, 60, true)));
//    cylinder.setMaterial(material);
//    rootNode.attachChild(cylinder);
}
