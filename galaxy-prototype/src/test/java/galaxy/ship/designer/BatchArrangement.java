package galaxy.ship.designer;

import java.util.List;

public enum BatchArrangement {
  
  Cross, 
  TShape, 
  YShape;

  public List<List<CargoBatchPlacement>> arrange(int batches) {
    return switch (this) {
      case Cross -> CargoBatchPlacement.Cross.arrange(batches);
      case TShape -> CargoBatchPlacement.TShape.arrange(batches);
      case YShape -> CargoBatchPlacement.YShape.arrange(batches);
    };
  }
}
