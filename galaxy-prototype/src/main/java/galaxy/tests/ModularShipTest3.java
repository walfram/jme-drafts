package galaxy.tests;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.WireBox;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.MBox;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModularShipTest3 extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    ModularShipTest3 app = new ModularShipTest3();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }


  private static final float cellExtent = 4f;
  private static final float halfExtent = cellExtent * 0.5f;
  private static final float containerExtent = halfExtent * 0.5f;
  
  private Material material;
  
  @Override
  public void simpleInitApp() {
    new QuickAppSetup(4, 32).applyTo(this);

    material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

//    Geometry cellBound = new Geometry("cell", new WireBox(cellExtent, cellExtent, cellExtent));
//    cellBound.setMaterial(material);
//    rootNode.attachChild(cellBound);

    Geometry hullChunk = new Geometry("hull-chunk", new MBox(0.9f * halfExtent, 1.5f * cellExtent, cellExtent, 1, 4, 2));
    hullChunk.setMaterial(material);
    rootNode.attachChild(hullChunk);

    int[] containerMapping = {2, 3, 3, 2, 1};
    for (int ys = 0; ys < containerMapping.length; ys++) {
      int y = ys - 2;
      int containers = containerMapping[ys];
      
      for (int x = 0; x < containers; x++) {
        Geometry container = new Geometry("container", new WireBox(0.95f * containerExtent, 0.95f * containerExtent, 0.9f * cellExtent));
        container.setMaterial(material);
        container.setLocalTranslation(x * 2f * containerExtent, y * 2f * containerExtent, 0);
        container.move(halfExtent + containerExtent, 0, 0);
        rootNode.attachChild(container);
      }
    }
    
//    Geometry container = new Geometry("container", new WireBox(containerExtent, containerExtent, cellExtent));
//    container.setMaterial(material);
//    rootNode.attachChild(container);
//    container.move(halfExtent + containerExtent, 0, 0);

    new QuickChaseCamera(cam, inputManager).init(rootNode);
  }
}
