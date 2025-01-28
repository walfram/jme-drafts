package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.component.SpringGridLayout;

public class ShipDesignUiState extends BaseAppState {

  private final Node gui = new Node("ship-design-ui-node");

  public ShipDesignUiState(Node guiNode) {
    guiNode.attachChild(gui);
  }

  @Override
  protected void initialize(Application application) {
    Container main = new Container();

    Container top = main.addChild(new Container());
    top.addChild(new ShipBaseParametersWidget());
    top.addChild(new ShipCalculatedParametersWidget(), 1);

    main.addChild(new PlanetInfoWidget());

    gui.attachChild(main);
    main.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
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
