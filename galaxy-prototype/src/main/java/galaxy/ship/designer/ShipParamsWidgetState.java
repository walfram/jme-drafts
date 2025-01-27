package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.SequenceModel;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.ElementId;
import galaxy.ship.designer.widgets.SpinnerWidget;
import galaxy.ship.model.ShipDesign;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipParamsWidgetState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(ShipParamsWidgetState.class);

  private final Node gui = new Node("ship-parameters-widget-node");

  // private final ShipDesign design = new ShipDesign(1, 0, 0, 0, 0);
  
  private final SequenceModel<Double> modelDrives = new DoubleSequenceImpl(1.0);
  private final SequenceModel<Integer> modelGuns = new IntegerSequenceImpl(0);
  private final SequenceModel<Double> modelCaliber = new DoubleSequenceImpl(0.0);
  private final SequenceModel<Double> modelShields = new DoubleSequenceImpl(0.0);
  private final SequenceModel<Double> modelCargo = new DoubleSequenceImpl(0.0);
  
  private final VersionedReference<? extends Number> reference = new CompositeVersionedReference<>(
      List.of(modelDrives, modelGuns, modelCaliber, modelShields, modelCargo)
  );
  
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

    Container drives = new SpinnerWidget<>(modelDrives);
    container.addChild(drives);

    Container guns = new SpinnerWidget<>(modelGuns);
    container.addChild(guns);

    Container caliber = new SpinnerWidget<>(modelCaliber);
    container.addChild(caliber);

    Container shields = new SpinnerWidget<>(modelShields);
    container.addChild(shields);

    Container cargo = new SpinnerWidget<>(modelCargo);
    container.addChild(cargo);

    container.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
  }

  @Override
  public void update(float tpf) {
    if (reference.update()) {
      logger.debug("design params changed = {}", reference.get());
    }
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
