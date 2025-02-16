package galaxy.ship.designer.widgets;

import com.jme3.input.event.MouseButtonEvent;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Button.ButtonAction;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.SequenceModel;
import com.simsilica.lemur.ValueEditors;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.event.CursorEventControl;
import com.simsilica.lemur.event.CursorMotionEvent;
import com.simsilica.lemur.event.DefaultCursorListener;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.value.TextFieldValueEditor;
import jme3utilities.SimpleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpinnerWidget<T extends Number> extends Container {

  private static final Logger logger = LoggerFactory.getLogger(SpinnerWidget.class);
  
  private final SequenceModel<T> model;
  private final VersionedReference<T> reference;
  private final Label label;
  
  private TextFieldValueEditor<Double> editor;

  public SpinnerWidget(SequenceModel<T> model) {
    super(new SpringGridLayout(Axis.Y, Axis.X, FillMode.ForcedEven, FillMode.First));

    this.reference = model.createReference();
    this.model = model;
    
    label = addChild(new Label("%.02f".formatted(model.getObject().doubleValue())));
    
    Button decrease = addChild(new Button("-"), 1);
    decrease.setMaxWidth(32f);
    decrease.setTextHAlignment(HAlignment.Center);
    decrease.addClickCommands(b -> model.setObject(model.getPreviousObject()));

    Button increase = addChild(new Button("+"), 2);
    increase.setMaxWidth(32f);
    increase.setTextHAlignment(HAlignment.Center);
    increase.addClickCommands(b -> model.setObject(model.getNextObject()));
    
    addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (reference.update()) {
          label.setText("%.02f".formatted(model.getObject().doubleValue()));
          // holder.updateObject(model.getObject());
        }
      }
    });

    CursorEventControl.addListenersToSpatial(this, new DefaultCursorListener() {
      @Override
      public void cursorMoved(CursorMotionEvent event, Spatial target, Spatial capture) {
        event.setConsumed();
        
        if (event.getScrollDelta() != 0) {
          if (event.getScrollDelta() > 0) {
            model.setObject(model.getNextObject());
          } else if (event.getScrollDelta() < 0) {
            model.setObject(model.getPreviousObject());
          }
        }
      }
    });
    
    addMouseListener(new DefaultMouseListener() {
      @Override
      protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
        getLayout().removeChild(label);

        editor = ValueEditors.doubleEditor("%.02f");
        editor.startEditing((Double) model.getObject());
        
        Panel panel = editor.getEditor();
        
        getLayout().addChild(panel, 0, 0);
        GuiGlobals.getInstance().requestFocus(panel);
      }
    });
  }

  @Override
  public void updateLogicalState(float tpf) {
    super.updateLogicalState(tpf);
    
    if (editor != null) {
      if (!editor.updateState(tpf)) {
        model.setObject((T) editor.getObject());
        
        getLayout().removeChild(editor.getEditor());
        getLayout().addChild(label, 0, 0);
        GuiGlobals.getInstance().requestFocus(label);
        
        editor = null;
      }
    }
  }

  public SequenceModel<T> model() {
    return model;
  }
}
