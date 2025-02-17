package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.core.VersionedHolder;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.ElementId;
import galaxy.ship.designer.widgets.PlanetInfoWidget;
import galaxy.ship.designer.widgets.ShipBaseParametersWidget;
import galaxy.ship.designer.widgets.ShipCalculatedParametersWidget;
import galaxy.domain.ship.ShipDesign;
import galaxy.ship.designer.widgets.SpacerWidget;
import java.util.Map;

public class ShipDesignUiState extends BaseAppState {

  private final Node gui = new Node("ship-design-ui-node");

  private final VersionedHolder<ShipDesign> holder = new VersionedHolder<>(ShipDesign.minimal());
  
  public ShipDesignUiState(Node guiNode) {
    guiNode.attachChild(gui);
  }

  @Override
  protected void initialize(Application application) {
    Container main = new Container();

    Container top = main.addChild(new Container());
    
    top.addChild(new ShipBaseParametersWidget(holder));
    top.addChild(new SpacerWidget(), 1);
    top.addChild(new ShipCalculatedParametersWidget(holder), 2);

    main.addChild(new PlanetInfoWidget());

    gui.attachChild(main);
    main.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
    
    // TODO as widget
    Container designs = new Container();
    Label title = designs.addChild(new Label("saved ship designs", new ElementId("title")));
    title.setMaxWidth(320f);

    for (Map.Entry<String, ShipDesign> entry : getState(ShipDesignIOState.class).designs().entrySet()) {
      Button button = designs.addChild(new Button(entry.getKey()));
      button.addClickCommands(b -> holder.setObject(entry.getValue()));
    }
    
    gui.attachChild(designs);
    designs.setLocalTranslation(application.getCamera().getWidth() - designs.getPreferredSize().x - 10, application.getCamera().getHeight() - 10, 0);
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

  public VersionedReference<ShipDesign> shipDesignReference() {
    return holder.createReference();
  }
}
