package galaxy.ship.generated;

import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class DoubleRowPlacement implements BatchPlacement {
  
  private enum Position implements BatchPosition {
    X_POS, X_NEG;
    
    @Override
    public Vector3f translation(int z, float cellExtent) {
      return switch (this) {
        case X_POS -> new Vector3f(2f * cellExtent, 0, 2f * z * cellExtent);
        case X_NEG -> new Vector3f(-2f * cellExtent, 0, 2f * z * cellExtent);
      };
    }
  }
  
  public DoubleRowPlacement(int batches) {
  }
  
  @Override
  public List<List<BatchPosition>> arrange(int batches) {
    int remainder = batches % 2;
    int rows = batches / 2;
    
    List<List<BatchPosition>> arranged = new ArrayList<>(rows + 1);
    
    for (int row = 0; row < rows; row++) {
      arranged.add(List.of(Position.X_POS, Position.X_NEG));
    }
    
    if (remainder == 1) {
      arranged.add(List.of(Position.X_POS));
    }
    
    return arranged;
  }
}
