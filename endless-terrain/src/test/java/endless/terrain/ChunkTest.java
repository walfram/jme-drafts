package endless.terrain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cells.Cell;
import cells.Cell2d;
import cells.CellOffset2d;
import org.junit.jupiter.api.Test;

public class ChunkTest {

  private static final float extent = 128f;
  
  @Test
  void should_calculate_resolution_based_on_cell_distance() {
    assertEquals(2, new CellOffset2d(2, 0).distance());
    
    assertEquals(2.828427f, new CellOffset2d(2, 2).distance());
    assertEquals(2.828427f, new CellOffset2d(2, -2).distance());
    assertEquals(2.828427f, new CellOffset2d(-2, 2).distance());
    assertEquals(2.828427f, new CellOffset2d(-2, -2).distance());
  }
  
  @Test
  void should_create_cell_domain_for_current_cell() {
    
  }
  
  @Test
  void should_create_mesh_with_requested_resolution() {
    
  }
  
}
