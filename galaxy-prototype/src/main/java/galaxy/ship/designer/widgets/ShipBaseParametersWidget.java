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
import galaxy.ship.designer.model.DoubleSequenceImpl;
import galaxy.ship.designer.model.IntegerSequenceImpl;
import galaxy.domain.ship.ShipDesign;
import jme3utilities.SimpleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipBaseParametersWidget extends Container {

  private static final Logger logger = LoggerFactory.getLogger(ShipBaseParametersWidget.class);
  
  private final VersionedReferenceList references = VersionedReferenceList.create();

  private final VersionedReference<Integer> refGuns;
  private final VersionedReference<Double> refCaliber;

  public ShipBaseParametersWidget(VersionedHolder<ShipDesign> holder) {
    super(new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.Even));

    Label header = addChild(new Label("ship base parameters", new ElementId("title")));
    header.setMaxWidth(256f);

    // drives
    SpinnerWidget<Double> drives = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(1)));
    references.addReference(drives.model());

    // guns
    SpinnerWidget<Integer> guns = addChild(new SpinnerWidget<>(new IntegerSequenceImpl(0)));
    references.addReference(guns.model());
    refGuns = guns.model().createReference();

    // caliber
    SpinnerWidget<Double> caliber = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    references.addReference(caliber.model());
    refCaliber = caliber.model().createReference();

    // shields
    SpinnerWidget<Double> shields = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    references.addReference(shields.model());

    // cargo
    SpinnerWidget<Double> cargo = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    references.addReference(cargo.model());

    addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (refGuns.update()) {
          if (caliber.model().getObject() == 0) {
            caliber.model().setObject(1.0);
          }
        }

        if (refCaliber.update()) {
          if (guns.model().getObject() == 0) {
            guns.model().setObject(1);
          }
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
}
