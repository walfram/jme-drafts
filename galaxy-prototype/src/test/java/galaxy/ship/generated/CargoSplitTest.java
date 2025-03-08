package galaxy.ship.generated;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CargoSplitTest {
  
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 5, 8})
  void should_split_into_n_rows(int rows) {
    SplittedCargo splittedCargo = new SplittedCargo(rows * 10, rows);
    assertEquals(rows, splittedCargo.batches());
  }
  
}
