package galaxy.ship.designer;

import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.style.ElementId;
import galaxy.ship.designer.widgets.SpinnerWidget;

public class ShipCalculatedParametersWidget extends Container {

  public ShipCalculatedParametersWidget() {
    super(new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.Even));
    
    Label header = addChild(new Label("calculated parameters", new ElementId("title")));
    header.setMaxWidth(256f);
    
    // speed
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // speed with full cargo
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // attack
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // bombing
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // defence
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // mass
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // cargo capacity
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
  }
  
}
