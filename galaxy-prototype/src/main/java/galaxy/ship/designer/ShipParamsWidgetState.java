package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.SequenceModel;
import com.simsilica.lemur.core.VersionedHolder;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.core.VersionedReferenceList;
import com.simsilica.lemur.style.ElementId;
import galaxy.ship.designer.widgets.MySpinner;
import galaxy.ship.designer.widgets.SpinnerWidget;
import galaxy.ship.model.Drives;
import galaxy.ship.model.ShipDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipParamsWidgetState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(ShipParamsWidgetState.class);

  private final Node gui = new Node("ship-parameters-widget-node");

  private final VersionedHolder<ShipDesign> holder = new VersionedHolder<>(new ShipDesign(1, 0, 0, 0, 0));

  private final SequenceModel<Double> modelDrives = new DoubleSequenceImpl(holder.getObject().drives().size());
  private final SequenceModel<Integer> modelGuns = new IntegerSequenceImpl(holder.getObject().weapons().guns());
  private final SequenceModel<Double> modelCaliber = new DoubleSequenceImpl(holder.getObject().weapons().caliber());
  private final SequenceModel<Double> modelShields = new DoubleSequenceImpl(holder.getObject().shields().power());
  private final SequenceModel<Double> modelCargo = new DoubleSequenceImpl(holder.getObject().cargo().volume());

  VersionedReferenceList reference = VersionedReferenceList.create(modelDrives, modelGuns, modelCaliber, modelShields, modelCargo);

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

    SequenceModel<Drives> model = container.addChild(new MySpinner<>(new Drives(1))).model();
    VersionedReference<Drives> ref = model.createReference();

    container.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
  }

  @Override
  public void update(float tpf) {
    if (reference.update()) {
      logger.debug("design params changed = {}", reference);
      
      try {
        ShipDesign shipDesign = new ShipDesign(modelDrives.getObject(), modelGuns.getObject(), modelCaliber.getObject(), modelShields.getObject(),
            modelCargo.getObject());
        holder.setObject(shipDesign);
      } catch (IllegalArgumentException e) {
        logger.error(e.getMessage(), e);
      }
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
