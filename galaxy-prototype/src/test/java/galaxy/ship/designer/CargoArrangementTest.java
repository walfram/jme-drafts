package galaxy.ship.designer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import galaxy.ship.designer.CargoBatchPlacement.TShape;
import galaxy.ship.designer.CargoBatchPlacement.YShape;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CargoArrangementTest {

  @Test
  void should_not_place_anything_if_no_batches() {
    List<List<CargoBatchPlacement>> arranged = arrange(0);

    assertTrue(arranged.isEmpty());
  }

  @Test
  void should_place_1_batch_below() {
    List<List<CargoBatchPlacement>> arranged = arrange(1);

    assertEquals(1, arranged.size());
    assertEquals(List.of(YShape.BOTTOM), arranged.getFirst());
  }

  private List<List<CargoBatchPlacement>> arrange(int batches) {
    int remainder = batches % 3;

    int rows = batches / 3;

    List<List<CargoBatchPlacement>> arranged = new ArrayList<>(rows + 1);
    for (int row = 0; row < rows; row++) {
      arranged.add(List.of(YShape.values()));
    }

    if (remainder == 1) {
      arranged.add(List.of(YShape.BOTTOM));
    } else if (remainder == 2) {
      arranged.add(List.of(YShape.X_POS, YShape.X_NEG));
    }

    return arranged;
  }

  @Test
  void should_place_2_batches_top_and_bottom() {
    List<List<CargoBatchPlacement>> arranged = arrange(2);

    assertEquals(1, arranged.size());
    assertEquals(List.of(YShape.X_POS, YShape.X_NEG), arranged.getFirst());
  }

  @Test
  void should_place_3_batches_y_shape() {
    List<List<CargoBatchPlacement>> arranged = arrange(3);

    assertEquals(1, arranged.size());
    assertEquals(List.of(YShape.BOTTOM, YShape.X_POS, YShape.X_NEG), arranged.getFirst());
  }

  @Test
  void should_place_4_batches_y_shape_and_1_bottom() {
    List<List<CargoBatchPlacement>> arranged = arrange(4);

    assertEquals(2, arranged.size());
    assertEquals(List.of(YShape.BOTTOM, YShape.X_POS, YShape.X_NEG), arranged.getFirst());

    assertEquals(List.of(YShape.BOTTOM), arranged.get(1));
  }

  @Test
  void should_place_5_batches_y_shape_and_2_sides() {
    List<List<CargoBatchPlacement>> arranged = arrange(5);

    assertEquals(2, arranged.size());
    assertEquals(List.of(YShape.BOTTOM, YShape.X_POS, YShape.X_NEG), arranged.getFirst());

    assertEquals(List.of(YShape.X_POS, YShape.X_NEG), arranged.get(1));
  }

  @Test
  void should_arrange_as_y_shape() {
    List<List<CargoBatchPlacement>> arranged = BatchArrangement.YShape.arrange(42);
    assertEquals(14, arranged.size());
  }
  
  @Test
  void quick_check() {
    CargoBatchPlacement placement = TShape.CENTER;
  }
  
}
