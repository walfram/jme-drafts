package galaxy.ship.generated;

import cells.Cell2d;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static com.jme3.math.FastMath.DEG_TO_RAD;
import static com.jme3.math.Vector3f.UNIT_Z;

public class YShapePlacement implements BatchPlacement {
  
  public enum YShapeBatchPosition implements BatchPosition {
    BOTTOM, X_POS, X_NEG;
    
    @Override
    public Vector3f translation(int z, float cellExtent) {
      final Vector3f t = new Cell2d(0, z, cellExtent).translation().add(0, -2f * cellExtent, 0);
      return switch (this) {
        case BOTTOM -> new Quaternion().fromAngleAxis(0, UNIT_Z).mult(t);
        case X_POS -> new Quaternion().fromAngleAxis(120f * DEG_TO_RAD, UNIT_Z).mult(t);
        case X_NEG -> new Quaternion().fromAngleAxis(240f * DEG_TO_RAD, UNIT_Z).mult(t);
      };
    }
  }
  
  @Override
  public List<List<BatchPosition>> arrange(int batches) {
    int remainder = batches % 3;
    
    int rows = batches / 3;
    
    List<List<BatchPosition>> arranged = new ArrayList<>(rows + 1);
    for (int row = 0; row < rows; row++) {
      arranged.add(List.of(YShapeBatchPosition.values()));
    }
    
    if (remainder == 1) {
      arranged.add(List.of(YShapeBatchPosition.BOTTOM));
    } else if (remainder == 2) {
      arranged.add(List.of(YShapeBatchPosition.X_POS, YShapeBatchPosition.X_NEG));
    }
    
    return arranged;
  }
}
