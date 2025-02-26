package galaxy.ship.designer;

import static com.jme3.math.FastMath.DEG_TO_RAD;
import static com.jme3.math.Vector3f.UNIT_Z;

import cells.Cell2d;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.List;

public interface CargoBatchPlacement {

  Vector3f translation(int z, float cellExtent);
  
  static List<List<CargoBatchPlacement>> arrange(int batches);
  
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

  enum YShape implements CargoBatchPlacement {
    BOTTOM, X_POS, X_NEG;

    @Override
    public Vector3f translation(int z, float cellExtent) {
      final Vector3f t = new Cell2d(0, z, cellExtent).translation();
      return switch (this) {
        case BOTTOM -> new Quaternion().fromAngleAxis(0, UNIT_Z).mult(t);
        case X_POS -> new Quaternion().fromAngleAxis(120f * DEG_TO_RAD, UNIT_Z).mult(t);
        case X_NEG -> new Quaternion().fromAngleAxis(240f * DEG_TO_RAD, UNIT_Z).mult(t);
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
