package galaxy.ship.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import galaxy.domain.ship.ShipDesign;
import org.junit.jupiter.api.Test;

class ShipDesignTest {

  @Test
  void cargo_0_or_more_then_1() {
    assertThrows(IllegalArgumentException.class, () -> new ShipDesign(1, 1, 1, 1, 0.5));

    assertDoesNotThrow(() -> new ShipDesign(1, 0, 0, 0, 0));
    assertDoesNotThrow(() -> new ShipDesign(0, 0, 0, 0, 1));
  }

  @Test
  void shields_0_or_more_then_1() {
    assertThrows(IllegalArgumentException.class, () -> new ShipDesign(0, 0, 0, 0.5, 0));

    assertDoesNotThrow(() -> new ShipDesign(1, 0, 0, 0, 0));
    assertDoesNotThrow(() -> new ShipDesign(1, 0, 0, 1, 0));
  }

  @Test
  void weapons_both_0_or_more_then_1() {
    assertThrows(IllegalArgumentException.class, () -> new ShipDesign(1, 1, 0, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> new ShipDesign(1, 0, 1, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> new ShipDesign(1, 1, 0.5, 0, 0));

    assertDoesNotThrow(() -> new ShipDesign(1, 0, 0, 0, 0));
    assertDoesNotThrow(() -> new ShipDesign(1, 1, 1, 0, 0));
  }

  @Test
  void engines_0_or_more_then_1() {
    assertThrows(IllegalArgumentException.class, () -> new ShipDesign(0.5, 0, 0, 0, 0));
    
    assertDoesNotThrow(() -> new ShipDesign(0, 1, 1, 0, 0));
    assertDoesNotThrow(() -> new ShipDesign(1, 0, 0, 0, 0));
  }

}
