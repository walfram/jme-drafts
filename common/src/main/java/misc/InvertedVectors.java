package misc;

import com.jme3.math.Vector3f;
import java.util.Collection;

public class InvertedVectors {

  private final Collection<Vector3f> source;

  public InvertedVectors(Collection<Vector3f> source) {
    this.source = source;
  }

  public Collection<Vector3f> value() {
    return source.stream().map(Vector3f::negate).toList();
  }
}
