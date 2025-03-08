package galaxy.ship.generated;

import java.util.List;

public class TShapePlacement implements BatchPlacement {
  public TShapePlacement(int batches) {
  }
  
  @Override
  public List<List<BatchPosition>> arrange(int batches) {
    int remainder = batches % 3;
    int rows = batches / 3;
    
    return List.of();
  }
}
