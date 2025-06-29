package tests;

import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import debug.QuickAppSettings;
import debug.QuickAppSetup;
import debug.QuickChaseCamera;
import jme3utilities.mesh.*;
import mesh.FlatShadedMesh;

public class MeshShapes2Test extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();
    
    MeshShapes2Test app = new MeshShapes2Test();
    app.setSettings(settings);
    app.setShowSettings(false);
    
    app.start();
  }
  
  private static final float cellExtent = 32f;
  
  @Override
  public void simpleInitApp() {
    new QuickAppSetup().applyTo(this);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
    
    Geometry capsule = new Geometry("capsule-mesh", new FlatShadedMesh(new CapsuleMesh(1, 5, 20)));
    capsule.setMaterial(material);
    rootNode.attachChild(capsule);
    capsule.setLocalTranslation(new Cell2d(-1, -1, cellExtent).translation());
    
    Geometry disc = new Geometry("disc-mesh", new FlatShadedMesh(new DiscMesh(5, 25)));
    disc.setMaterial(material);
    rootNode.attachChild(disc);
    disc.setLocalTranslation(new Cell2d(0, -1, cellExtent).translation());

    Geometry rect = new Geometry("rectangle-mesh", new RectangleMesh());
    rect.setMaterial(material);
    rect.scale(10);
    rootNode.attachChild(rect);
    rect.setLocalTranslation(new Cell2d(1, -1, cellExtent).translation());
    
    Geometry rectOutline = new Geometry("RectangleOutlineMesh", new RectangleOutlineMesh());
    rectOutline.setMaterial(material);
    rectOutline.scale(10);
    rootNode.attachChild(rectOutline);
    rectOutline.setLocalTranslation(new Cell2d(-1, 0, cellExtent).translation());

    Geometry rounded = new Geometry("RoundedRectangle", new RoundedRectangle());
    rounded.setMaterial(material);
    rounded.scale(10);
    rootNode.attachChild(rounded);
    
    new QuickChaseCamera(cam, inputManager).attachTo(rootNode);
  }
}
