package galaxy.ship.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShipWeightTest {

  @Test
  void test_drone_weight() {
    ShipDesign drone = new ShipDesign(1, 0, 0, 0, 0);
    assertEquals(1, drone.weight());
  }
  
  @Test
  void test_fighter_weight() {
    ShipDesign fighter = new ShipDesign(1, 1, 1, 1, 0);
    assertEquals(3, fighter.weight());
  }
  
  @Test
  void test_freighter_weight() {
    ShipDesign freighter = new ShipDesign(8, 0, 0, 2, 10);
    assertEquals(20, freighter.weight());
   }
  
  @Test
  void test_gunship_weight() {
    ShipDesign cruiser = new ShipDesign(15, 1, 15, 15, 0);
    assertEquals(45, cruiser.weight());
  }
  
}
