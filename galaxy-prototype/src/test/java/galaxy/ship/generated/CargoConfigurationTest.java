package galaxy.ship.generated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CargoConfigurationTest {
  
  private static final int batchesPerRow = 8;
  
  @Test
  void should_pick_1row_batch_placement() {
    BatchPlacement placement = placement(batchesPerRow);
    
    assertInstanceOf(SingleRowPlacement.class, placement);
    assertEquals(batchesPerRow, placement.arrange(batchesPerRow).size());
  }
  
  @Test
  void should_pick_double_row_placement() {
    int batches = 2 * batchesPerRow;
    
    BatchPlacement placement = placement(batches);
    
    assertInstanceOf(DoubleRowPlacement.class, placement);
    assertEquals(batchesPerRow, placement.arrange(batches).size());
  }
  
  @Test
  void should_pick_t_shape_batch_placement() {
    int batches = 3 * batchesPerRow;
    BatchPlacement placement = placement(batches);
    
    assertInstanceOf(TShapePlacement.class, placement);
    assertEquals(batchesPerRow, placement.arrange(batches).size());
  }
  
  private BatchPlacement placement(int batches) {
    int rows = batches / batchesPerRow;
    
    return switch(rows) {
      case 0, 1 -> new SingleRowPlacement(batches);
      case 2 -> new DoubleRowPlacement(batches);
      case 3 -> new TShapePlacement(batches); // TODO use YShapePlacement
      case 4 -> new CrossPlacement(batches);
      case 5 -> new StarPlacement(batches);
      default -> new HexPlacement(batches);
    };
  }
  
  @Test
  void should_pick_cross_shape_batch_placement() {
    int batches = 4 * batchesPerRow;
    BatchPlacement placement = placement(batches);
    
    assertInstanceOf(CrossPlacement.class, placement);
    assertEquals(batchesPerRow, placement.arrange(batches).size());
  }
  
  @Test
  void should_pick_5_star_batch_placement() {
    int batches = 5 * batchesPerRow;
    BatchPlacement placement = placement(batches);
    
    assertInstanceOf(StarPlacement.class, placement);
    assertEquals(batchesPerRow, placement.arrange(batches).size());
  }
  
  @Test
  void should_pick_hex_batch_placement() {
    int batches = 6 * batchesPerRow;
    BatchPlacement placement = placement(batches);
    
    assertInstanceOf(HexPlacement.class, placement);
    assertEquals(batchesPerRow, placement.arrange(batches).size());
  }
  
}
