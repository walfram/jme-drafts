package cells;

import com.jme3.math.Vector3f;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum Side {

  X_POS, X_NEG, Z_POS, Z_NEG;

  public List<Vector3f> filter(Vector3f[] vectorArray, float extent) {
    return switch (this) {
      case X_POS -> Arrays.stream(vectorArray).filter(v -> v.x == extent).toList();
      case X_NEG -> Arrays.stream(vectorArray).filter(v -> v.x == -extent).toList();
      case Z_POS -> Arrays.stream(vectorArray).filter(v -> v.z == extent).toList();
      case Z_NEG -> Arrays.stream(vectorArray).filter(v -> v.z == -extent).toList();
    };
  }

  public boolean filter(Vector3f vector, float extent) {
    return switch(this) {
      case X_POS -> vector.x == extent;
      case X_NEG -> vector.x == -extent;
      case Z_POS -> vector.z == extent;
      case Z_NEG -> vector.z == -extent;
    };
  }

  public Side opposite() {
    return switch (this) {
      case X_POS -> X_NEG;
      case X_NEG -> X_POS;
      case Z_POS -> Z_NEG;
      case Z_NEG -> Z_POS;
    };
  }

  public Collection<Vector3f> invert(Collection<Vector3f> source) {
    return switch(this) {
      case X_POS, X_NEG -> source.stream().map(v -> v.mult(-1, 0, 0)).toList();
      case Z_POS, Z_NEG -> source.stream().map(v -> v.mult(0, 0, -1)).toList();
    };
  }
}
