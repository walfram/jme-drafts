package galaxy.ship.designer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import galaxy.ship.designer.CargoBatchPlacement.YShape;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CargoBatchPlacementTest {

  @Test
  void should_place_1_batch_below() {
    List<List<CargoBatchPlacement>> placements = distribute(1);

    assertEquals(1, placements.size());
    assertEquals(List.of(YShape.BOTTOM), placements.get(0));
  }

  private List<List<CargoBatchPlacement>> distribute(int batches) {
    int remainder = batches % 3;
    
    int rows = batches / 3;
    
    List<List<CargoBatchPlacement>> placements = new ArrayList<>(rows + 1);
    for (int row = 0; row < rows; row++) {
      placements.add(List.of( YShape.values() ));
    }
    
    if (remainder == 1) {
      placements.add(List.of(YShape.BOTTOM));
    } else if (remainder == 2) {
      placements.add(List.of(YShape.X_POS, YShape.X_NEG));
    }
    
    return placements;
  }

  @Test
  void should_place_2_batches_top_and_bottom() {
    List<List<CargoBatchPlacement>> placements = distribute(2);

    assertEquals(1, placements.size());
    assertEquals(List.of(YShape.X_POS, YShape.X_NEG), placements.get(0));
  }

  @Test
  void should_place_3_batches_y_shape() {
    List<List<CargoBatchPlacement>> placements = distribute(3);

    assertEquals(1, placements.size());
    assertEquals(List.of(YShape.BOTTOM, YShape.X_POS, YShape.X_NEG), placements.get(0));
  }

  @Test
  void should_place_4_batches_y_shape_and_1_bottom() {
    List<List<CargoBatchPlacement>> placements = distribute(4);

    assertEquals(2, placements.size());
    assertEquals(List.of(YShape.BOTTOM, YShape.X_POS, YShape.X_NEG), placements.get(0));

    assertEquals(List.of(YShape.BOTTOM), placements.get(1));
  }
  
  @Test
  void should_place_5_batches_y_shape_and_2_sides() {
    List<List<CargoBatchPlacement>> placements = distribute(5);

    assertEquals(2, placements.size());
    assertEquals(List.of(YShape.BOTTOM, YShape.X_POS, YShape.X_NEG), placements.get(0));

    assertEquals(List.of(YShape.X_POS, YShape.X_NEG), placements.get(1));
  }

}
