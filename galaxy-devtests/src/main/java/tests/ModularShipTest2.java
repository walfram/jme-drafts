package tests;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import jme3utilities.mesh.Octasphere;
import mesh.FlatShadedMesh;

public class ModularShipTest2 extends SimpleApplication {

  private Material material;

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    ModularShipTest2 app = new ModularShipTest2();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final float cellExtent = 4f;
  private static final float halfExtent = cellExtent * 0.5f;
  
  @Override
  public void simpleInitApp() {
    new QuickAppSetup(4, 32).applyTo(this);

    material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    int hullExtent = 5;

    Node body = new Node("body");
    rootNode.attachChild(body);
    
    Geometry hull = new Geometry("hull", new FlatShadedMesh(new Cylinder(2, 6, cellExtent, hullExtent * 2f * cellExtent, true)));
    hull.setMaterial(material);
    body.attachChild(hull);

    Geometry bridge = new Geometry("bridge", new FlatShadedMesh(new Cylinder(2, 6, 0.25f * cellExtent, cellExtent, 2f * cellExtent, true, false)));
    bridge.setMaterial(material);
    body.attachChild(bridge);
    bridge.move(0, 0, (hullExtent + 1) * cellExtent);

    body.scale(1, 2, 1);
    
    containers(hullExtent);

    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }

  private void containers(int hullExtent) {
    Mesh containerMesh = new Octasphere(1, halfExtent);

    Node containers = new Node("containers");
    rootNode.attachChild(containers);
    
    for (int z = -(hullExtent / 2); z <= (hullExtent / 2); z++) {
      Cell2d cell = new Cell2d(0, z, cellExtent);
      
      Geometry geometry = new Geometry("container-batch", new WireBox(cellExtent, cellExtent, cellExtent));
      geometry.setMaterial(material);
      containers.attachChild(geometry);
      geometry.setLocalTranslation(cell.translation());

      int[] m = {-1, 1};
      for (int dx : m) {
        for (int dy : m) {
          for (int dz : m) {
            Geometry g = new Geometry("container", containerMesh);
            g.setMaterial(material);
            g.setLocalTranslation(cell.translation().add(halfExtent * dx, halfExtent * dy, halfExtent * dz));
            containers.attachChild(g);
          }
        }
      }

      Geometry c = new Geometry("container", containerMesh);
      c.setLocalTranslation(cell.translation());
      c.setMaterial(material);
      containers.attachChild(c);
    }
    
    containers.move(2f * cellExtent, 0, 0);
  }
}
