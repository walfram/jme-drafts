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
  private final int lines;

  public DebugGrid(AssetManager assetManager, float cellExtent) {
    this(assetManager, cellExtent, 8);
  }
  
  public DebugGrid(AssetManager assetManager, float cellExtent, int lines) {
    this.assetManager = assetManager;
    this.cellExtent = cellExtent;
    this.lines = lines;
  }

  public void attachTo(Node node) {
    Geometry debugGrid = new Geometry("debug-grid", new Grid(lines, lines, 2f * cellExtent));
    debugGrid.setMaterial(new Material(assetManager, Materials.UNSHADED));
    debugGrid.getMaterial().setColor("Color", ColorRGBA.Blue);
    node.attachChild(debugGrid);
    debugGrid.center();
  }
  
}
