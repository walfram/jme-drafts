package tests;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.geom.MBox;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModularShipTest3 extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    ModularShipTest3 app = new ModularShipTest3();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(ModularShipTest3.class);

  private static final float cellExtent = 4f;
  private static final float halfExtent = cellExtent * 0.5f;
  private static final float containerExtent = halfExtent * 0.5f;
  
  private Material material;
  
  @Override
  public void simpleInitApp() {
    new QuickAppSetup(4, 32).applyTo(this);

    material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    Node hullChunk0 = hullChunk();
    rootNode.attachChild(hullChunk0);

    Node hullChunk1 = hullChunk();
    rootNode.attachChild(hullChunk1);
    hullChunk1.move(0, 0, -2f * cellExtent);
    
    Node hullChunk2 = hullChunk();
    rootNode.attachChild(hullChunk2);
    hullChunk2.move(0, 0, 2f * cellExtent);
    
    new QuickChaseCamera(cam, inputManager).attachTo(rootNode);
  }

  private Node hullChunk() {
    Node chunk = new Node("hull-chunk");
    
    Geometry hullChunk = new Geometry("hull-chunk", new MBox(0.9f * halfExtent, 1.5f * cellExtent, 0.95f * cellExtent, 1, 4, 2));
    hullChunk.setMaterial(material);
    chunk.attachChild(hullChunk);

    int[] containerMapping = {2, 3, 3, 2, 1};
    for (int ys = 0; ys < containerMapping.length; ys++) {
      int y = ys - 2;
      int containers = containerMapping[ys];
      
      for (int x = 0; x < containers; x++) {
        Geometry containerXPos = new Geometry("container", new WireBox(0.95f * containerExtent, 0.95f * containerExtent, 0.9f * cellExtent));
        containerXPos.setMaterial(material);
        containerXPos.setLocalTranslation(x * 2f * containerExtent, y * 2f * containerExtent, 0);
        chunk.attachChild(containerXPos);
        containerXPos.move(halfExtent + containerExtent, 0, 0);

        Geometry containerXNeg = new Geometry("container", new WireBox(0.95f * containerExtent, 0.95f * containerExtent, 0.9f * cellExtent));
        containerXNeg.setMaterial(material);
        containerXNeg.setLocalTranslation(-x * 2f * containerExtent, y * 2f * containerExtent, 0);
        chunk.attachChild(containerXNeg);
        containerXNeg.move(-halfExtent - containerExtent, 0, 0);
      }
    }
    
    return chunk;
  }
}
