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
import galaxy.ship.designer.model.DoubleSequenceImpl;
import galaxy.ship.designer.model.IntegerSequenceImpl;
import galaxy.ship.designer.widgets.SpinnerWidget;
import galaxy.domain.ship.ShipDesign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ShipParamsWidgetState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(ShipParamsWidgetState.class);

  private final Node gui = new Node("ship-parameters-widget-node");

  private final VersionedHolder<ShipDesign> holder = new VersionedHolder<>(ShipDesign.minimal());

  private final SequenceModel<Double> modelDrives = new DoubleSequenceImpl(holder.getObject().drives().size());
  private final SequenceModel<Integer> modelGuns = new IntegerSequenceImpl(holder.getObject().weapons().guns());
  private final SequenceModel<Double> modelCaliber = new DoubleSequenceImpl(holder.getObject().weapons().caliber());
  private final SequenceModel<Double> modelShields = new DoubleSequenceImpl(holder.getObject().shields().power());
  private final SequenceModel<Double> modelCargo = new DoubleSequenceImpl(holder.getObject().cargo().volume());

  private final VersionedReference<Integer> refGuns = modelGuns.createReference();
  private final VersionedReference<Double> refCaliber = modelCaliber.createReference();
  
  VersionedReferenceList reference = VersionedReferenceList.create(modelDrives, modelGuns, modelCaliber, modelShields, modelCargo);

  public ShipParamsWidgetState(Node guiNode) {
    guiNode.attachChild(gui);
  }

  @Override
  protected void initialize(Application application) {
    Container container = new Container();
    gui.attachChild(container);

    Label header = container.addChild(new Label("Ship parameters", new ElementId("title")));
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
    if (refGuns.update()) {
      if (modelCaliber.getObject() == 0) {
        modelCaliber.setObject(1.0);
      }
    }
    
    if (refCaliber.update()) {
      if (modelGuns.getObject() == 0) {
        modelGuns.setObject(1);
      }
    }
    
    if (reference.update()) {
      try {
        ShipDesign shipDesign = new ShipDesign(modelDrives.getObject(), modelGuns.getObject(), modelCaliber.getObject(), modelShields.getObject(),
            modelCargo.getObject());
        holder.setObject(shipDesign);
        logger.debug("ship design = {}", shipDesign);
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
