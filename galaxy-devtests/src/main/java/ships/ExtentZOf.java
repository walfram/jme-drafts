package ships;

import com.jme3.bounding.BoundingBox;
import com.jme3.scene.Geometry;

import java.util.List;

public class ExtentZOf {
  private final Geometry[] geometries;

  public ExtentZOf(Geometry... geometries) {
    this.geometries = geometries;
  }

  public float value() {
    return value(0f);
  }

  public float value(float offset) {
    float extent = 0f;

    List<Geometry> sources = List.of(geometries);

    extent += extentOf(sources.getFirst());

    for (Geometry geometry : sources.subList(1, sources.size()) ) {
      BoundingBox bb = (BoundingBox) geometry.getModelBound();
      extent += 2f * bb.getZExtent();
    }

    extent += offset;

    return extent;
  }

  private float extentOf(Geometry geometry) {
    BoundingBox bb = (BoundingBox) geometry.getModelBound();
    return bb.getZExtent();
  }
}
