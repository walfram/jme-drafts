package socket;

import cells.Side;
import com.jme3.math.Vector3f;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Vector3fSocket implements Socket {

  private final Side side;
  private final Collection<Vector3f> vectors;

  public Vector3fSocket(Side side, Collection<Vector3f> vectors) {
    this.side = side;
    this.vectors = vectors;
  }

  @Override
  public String toString() {
    return "Vector3fSocket(%s, %s)".formatted(side, vectors);
  }

  @Override
  public boolean matches(Socket other) {
    Vector3fSocket that = (Vector3fSocket) other;

    if (side != that.side.opposite()) {
      return false;
    }

    Set<Vector3f> _this = new HashSet<>(vectors);
    Set<Vector3f> _that = new HashSet<>(side.invert(that.vectors));

    _this.retainAll(that.vectors);
    _that.retainAll(vectors);

    return _this.isEmpty() && _that.isEmpty();
  }
}
