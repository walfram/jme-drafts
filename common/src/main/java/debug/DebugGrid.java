package debug;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;

public class DebugGrid {

  private final AssetManager assetManager;
  private final float cellExtent;

  public DebugGrid(AssetManager assetManager, float cellExtent) {
    this.assetManager = assetManager;
    this.cellExtent = cellExtent;
  }

  public void attachTo(Node node) {
    Geometry debugGrid = new Geometry("debug-grid", new Grid(8, 8, 2f * cellExtent));
    debugGrid.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debugGrid.getMaterial().setColor("Color", ColorRGBA.Blue);
    node.attachChild(debugGrid);
    debugGrid.center();
  }
  
}
