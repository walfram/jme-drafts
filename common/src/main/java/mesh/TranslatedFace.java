package mesh;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;

import java.util.List;

public class TranslatedFace implements Face {
  private final Face source;
  private final Vector3f translation;
  
  public TranslatedFace(Face source, Vector3f translation) {
    this.source = source;
    this.translation = translation;
  }
  
  @Override
  public List<Triangle> triangles() {
    return source
        .triangles()
        .stream()
        .map(t -> new Triangle(t.get1().add(translation), t.get2().add(translation), t.get3().add(translation)))
        .toList();
  }
}
