package galaxy.ship.generated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import galaxy.ship.generated.YShapePlacement.YShapeBatchPosition;
import org.junit.jupiter.api.Test;

class BatchPlacementTest {

  @Test
  void should_not_place_anything_if_no_batches() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(0);

    assertTrue(arranged.isEmpty());
  }

  @Test
  void should_place_1_batch_below() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(1);

    assertEquals(1, arranged.size());
    assertEquals(List.of(YShapeBatchPosition.BOTTOM), arranged.getFirst());
  }

  @Test
  void should_place_2_batches_top_and_bottom() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(2);

    assertEquals(1, arranged.size());
    assertEquals(List.of(YShapeBatchPosition.X_POS, YShapeBatchPosition.X_NEG), arranged.getFirst());
  }

  @Test
  void should_place_3_batches_y_shape() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(3);

    assertEquals(1, arranged.size());
    assertEquals(
        List.of(YShapeBatchPosition.BOTTOM, YShapeBatchPosition.X_POS, YShapeBatchPosition.X_NEG),
        arranged.getFirst()
    );
  }

  @Test
  void should_place_4_batches_y_shape_and_1_bottom() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(4);

    assertEquals(2, arranged.size());
    assertEquals(
        List.of(YShapeBatchPosition.BOTTOM, YShapeBatchPosition.X_POS, YShapeBatchPosition.X_NEG),
        arranged.getFirst()
    );

    assertEquals(List.of(YShapeBatchPosition.BOTTOM), arranged.get(1));
  }

  @Test
  void should_place_5_batches_y_shape_and_2_sides() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(5);

    assertEquals(2, arranged.size());
    assertEquals(
        List.of(YShapeBatchPosition.BOTTOM, YShapeBatchPosition.X_POS, YShapeBatchPosition.X_NEG),
        arranged.getFirst()
    );

    assertEquals(
        List.of(YShapeBatchPosition.X_POS, YShapeBatchPosition.X_NEG),
        arranged.get(1)
    );
  }

  @Test
  void test_batch_placement() {
    BatchPlacement placement = new YShapePlacement();
    List<List<BatchPosition>> arranged = placement.arrange(40);
    
    assertEquals(14, arranged.size());
    assertEquals(1, arranged.getLast().size());
  }

}
