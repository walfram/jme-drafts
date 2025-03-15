package tests;

import com.jme3.bounding.BoundingBox;
import com.jme3.scene.Spatial;

public class ScaleUpTo {

  private final float xExtent;
  private final float yExtent;
  private final float zExtent;
  private final Spatial target;

  public ScaleUpTo(float xExtent, float yExtent, float zExtent, Spatial target) {
    this.xExtent = xExtent;
    this.yExtent = yExtent;
    this.zExtent = zExtent;
    this.target = target;
  }

  public void scale() {
    BoundingBox bound = (BoundingBox) target.getWorldBound();
    target.scale(
        xExtent / bound.getXExtent(),
        yExtent / bound.getYExtent(),
        zExtent / bound.getZExtent()
    );
  }
}
