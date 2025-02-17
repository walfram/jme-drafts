package galaxy.ship.designer.widgets;

import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedHolder;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.core.VersionedReferenceList;
import com.simsilica.lemur.style.ElementId;
import galaxy.domain.ship.ShipDesign;
import galaxy.ship.designer.model.DoubleSequenceImpl;
import galaxy.ship.designer.model.IntegerSequenceImpl;
import jme3utilities.SimpleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipBaseParametersWidget extends Container {

  private static final Logger logger = LoggerFactory.getLogger(ShipBaseParametersWidget.class);

  private final VersionedReferenceList references = VersionedReferenceList.create();

  private final VersionedReference<Integer> refGuns;
  private final VersionedReference<Double> refCaliber;
  private final SpinnerWidget<Double> drives;
  private final SpinnerWidget<Integer> guns;
  private final SpinnerWidget<Double> caliber;
  private final SpinnerWidget<Double> shields;
  private final SpinnerWidget<Double> cargo;

  public ShipBaseParametersWidget(VersionedHolder<ShipDesign> holder) {
    super(new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.Even));

    Label header = addChild(new Label("ship base parameters", new ElementId("title")));
    header.setMaxWidth(256f);

    addChild(new SpacerWidget());

    // drives
    addChild(new Label("drives"));
    drives = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(1)));
    references.addReference(drives.model());

    addChild(new SpacerWidget());

    // guns
    addChild(new Label("guns"));
    guns = addChild(new SpinnerWidget<>(new IntegerSequenceImpl(0)));
    references.addReference(guns.model());
    refGuns = guns.model().createReference();

    addChild(new SpacerWidget());

    // caliber
    addChild(new Label("caliber"));
    caliber = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    references.addReference(caliber.model());
    refCaliber = caliber.model().createReference();

    addChild(new SpacerWidget());

    // shields
    addChild(new Label("shields"));
    shields = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    references.addReference(shields.model());

    addChild(new SpacerWidget());

    // cargo
    addChild(new Label("cargo"));
    cargo = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    references.addReference(cargo.model());

    addChild(new SpacerWidget());
    
    addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (refGuns.update()) {
          if (guns.model().getObject() == 0) {
            caliber.model().setObject(0.0);
            refCaliber.update(); // prevent infinite refresh
          } else if (caliber.model().getObject() == 0) {
            caliber.model().setObject(1.0);
          }
        }

        if (refCaliber.update()) {
          if (caliber.model().getObject() == 0.0) {
            guns.model().setObject(0);
            refGuns.update(); // prevent infinite refresh
          } else if (guns.model().getObject() == 0) {
            guns.model().setObject(1);
          }
        }
        
        if (refGuns.update() || refCaliber.update()) {
          logger.debug("ref guns = {}, ref caliber = {}, guns = {}, caliber = {}", refGuns.get(), refCaliber.get(), guns.model().getObject(), caliber.model().getObject());
        } 

        if (references.update()) {
          try {
            ShipDesign shipDesign = new ShipDesign(
                drives.model().getObject(),
                guns.model().getObject(),
                caliber.model().getObject(),
                shields.model().getObject(),
                cargo.model().getObject()
            );

            holder.setObject(shipDesign);
            logger.debug("ship design = {}", shipDesign);
          } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
          }
        }
      }
    });
  }

  public void useShipDesign(ShipDesign design) {
    drives.model().setObject(design.drives().size());
    guns.model().setObject(design.weapons().guns());
    caliber.model().setObject(design.weapons().caliber());
    shields.model().setObject(design.shields().power());
    cargo.model().setObject(design.cargo().volume());
  }
}
