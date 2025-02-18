package galaxy.ship.designer;

import cells.Cell2d;
import com.jme3.math.Vector3f;

public interface CargoBatchPlacement {

  Vector3f translation(int z, float cellExtent);
  
  enum Cross implements CargoBatchPlacement {
    CENTER, Y_POS, Y_NEG, X_POS, X_NEG;

    public Vector3f translation(int z, float cellExtent) {
      final Vector3f t = new Cell2d(0, z, cellExtent).translation();
      return switch (this) {
        case CENTER -> t;
        case Y_POS -> t.add(0, 2f * cellExtent, 0);
        case Y_NEG -> t.add(0, -2f * cellExtent, 0);
        case X_POS -> t.add(2f * cellExtent, 0, 0);
        case X_NEG -> t.add(-2f * cellExtent, 0, 0);
      };
    }
  }

  enum TShape implements CargoBatchPlacement {
    CENTER, Y_NEG, X_POS, X_NEG;

    @Override
    public Vector3f translation(int z, float cellExtent) {
      final Vector3f t = new Cell2d(0, z, cellExtent).translation();
      return switch (this) {
        case CENTER -> t;
        case Y_NEG -> t.add(0, -2f * cellExtent, 0);
        case X_POS -> t.add(2f * cellExtent, 0, 0);
        case X_NEG -> t.add(-2f * cellExtent, 0, 0);
      };
    }
  }
  
}
