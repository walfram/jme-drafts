package ships.mk3;

import com.jme3.bounding.BoundingBox;
import com.jme3.scene.Geometry;

public class ExtentZOf {
  private final Geometry source;

  public ExtentZOf(Geometry source) {
    this.source = source;
  }

  public float value() {
    return ((BoundingBox)source.getWorldBound()).getZExtent();
  }
}
