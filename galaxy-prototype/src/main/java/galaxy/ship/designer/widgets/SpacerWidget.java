package galaxy.ship.designer.widgets;

import com.simsilica.lemur.Panel;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;

public class SpacerWidget extends Panel {

  public SpacerWidget() {
    super(10f, 2f, new ElementId("spacer"), BaseStyles.GLASS);
  }
  
}
