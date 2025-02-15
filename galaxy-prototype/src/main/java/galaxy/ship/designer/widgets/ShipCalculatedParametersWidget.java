package galaxy.ship.designer.widgets;

import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedHolder;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.ElementId;
import galaxy.domain.ship.ShipDesign;
import galaxy.ship.designer.model.DoubleSequenceImpl;
import jme3utilities.SimpleControl;

public class ShipCalculatedParametersWidget extends Container {

  public ShipCalculatedParametersWidget(VersionedHolder<ShipDesign> holder) {
    super(new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.Even));

    Label header = addChild(new Label("calculated parameters", new ElementId("title")));
    header.setMaxWidth(256f);

    addChild(new SpacerWidget());

    // speed
    addChild(new Label("speed"));
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));

    addChild(new SpacerWidget());

    // speed with full cargo
    addChild(new Label("speed, fully loaded"));
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));

    addChild(new SpacerWidget());

    // attack
    addChild(new Label("attack"));
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));

    addChild(new SpacerWidget());

    // bombing
    addChild(new Label("bombing"));
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));

    addChild(new SpacerWidget());

    // defence
    addChild(new Label("defence"));
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));

    addChild(new SpacerWidget());

    // mass
    VersionedReference<ShipDesign> refWeight = holder.createReference();
    addChild(new Label("weight"));
    SpinnerWidget<Double> weight = addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    weight.model().setObject(holder.getObject().weight());

    weight.addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (refWeight.update()) {
          weight.model().setObject(refWeight.get().weight());
        }
      }
    });

    addChild(new SpacerWidget());

    // cargo capacity
    addChild(new Label("cargo capacity"));
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));

    addChild(new SpacerWidget());
  }

}
