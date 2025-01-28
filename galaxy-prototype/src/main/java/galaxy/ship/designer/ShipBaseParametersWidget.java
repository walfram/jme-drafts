package galaxy.ship.designer;

import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.style.ElementId;
import galaxy.ship.designer.widgets.SpinnerWidget;

public class ShipBaseParametersWidget extends Container {

  public ShipBaseParametersWidget() {
    super(new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.Even));
    
    Label header = addChild(new Label("ship base parameters", new ElementId("title")));
    header.setMaxWidth(256f);
    
    // drives
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(1)));
    
    // guns
    addChild(new SpinnerWidget<>(new IntegerSequenceImpl(0)));
    
    // caliber
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // shields
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
    
    // cargo
    addChild(new SpinnerWidget<>(new DoubleSequenceImpl(0)));
  }
  
}
