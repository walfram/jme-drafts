package galaxy.ship.designer;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.ElementId;

public class PlanetInfoWidget extends Container {

  public PlanetInfoWidget() {
    Label header = addChild(new Label("planet info", new ElementId("title")));
    
    addChild(new Label("to be added later"));
  }
  
}
