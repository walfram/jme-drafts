package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.ElementId;
import galaxy.ship.designer.widgets.SpinnerWidget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipParamsWidgetState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(ShipParamsWidgetState.class);

  private final Node gui = new Node("ship-parameters-widget-node");

  public ShipParamsWidgetState(Node guiNode) {
    guiNode.attachChild(gui);
  }

  @Override
  protected void initialize(Application application) {
    Container container = new Container();
    gui.attachChild(container);

    Label header = container.addChild(new Label("Ship parameters", new ElementId("header")));
    header.setMaxWidth(320f);
    container.addChild(new Button("test"));

    Container drives = new SpinnerWidget();
    container.addChild(drives);

    Container guns = new SpinnerWidget();
    container.addChild(guns);

    Container caliber = new SpinnerWidget();
    container.addChild(caliber);

    Container shields = new SpinnerWidget();
    container.addChild(shields);

    Container cargo = new SpinnerWidget();
    container.addChild(cargo);

    container.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
  }

  @Override
  protected void cleanup(Application application) {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void onDisable() {

  }
}
