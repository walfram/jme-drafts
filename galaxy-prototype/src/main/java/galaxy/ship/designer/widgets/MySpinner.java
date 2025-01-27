package galaxy.ship.designer.widgets;

import com.simsilica.lemur.Container;
import com.simsilica.lemur.SequenceModel;
import galaxy.ship.model.ShipComponent;

public class MySpinner<T extends ShipComponent> extends Container {

  public MySpinner(T shipComponent) {
    
  }

  public SequenceModel<T> model() {
    return null;
  }
}
