package galaxy.ship.designer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import galaxy.domain.ship.ShipDesign;
import org.junit.jupiter.api.Test;

public class GeneratedShipTest {

  @Test
  void should_create_drone_ship() {
    ShipDesign design = new ShipDesign(1, 0, 0, 0, 0);
    
    Material material = mock(Material.class);
    
    Node ship = new GeneratedShip(design, material).node();
    
    assertEquals(2, ship.getChildren().size());
    
    Spatial hull = ship.getChild("hull");
    assertNotNull(hull);
    
    Spatial engine = ship.getChild("engine");
    assertNotNull(engine);
  }
  
}
