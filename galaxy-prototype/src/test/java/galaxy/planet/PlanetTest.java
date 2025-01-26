package galaxy.planet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PlanetTest {

  @Test
  void test_home_world() {
    Planet hw = Planet.homeWorld();

    assertEquals(1000, hw.size().value());
    assertEquals(10, hw.resources().value());
    assertEquals(1000, hw.population().value());
    assertEquals(1000, hw.industry().value());
    
    assertEquals(1000, hw.effort().value());
  }

  @Test
  void test_daughter_world() {
    Planet dw = Planet.daughterWorld();

    assertEquals(500, dw.size().value());
    assertEquals(10, dw.resources().value());
    assertEquals(500, dw.population().value());
    assertEquals(500, dw.industry().value());
    
    assertEquals(500, dw.effort().value());
  }

  @Test
  void test_effort() {
    Planet planet = new Planet(new Size(1000), new Resources(1f), new Population(1000, 1000), new Industry(500, 1000));
    
    assertEquals(625, planet.effort().value());
  }
  
}
