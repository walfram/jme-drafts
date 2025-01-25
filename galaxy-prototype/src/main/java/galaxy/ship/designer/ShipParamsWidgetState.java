package galaxy.ship.designer;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.DefaultRangedValueModel;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.SequenceModel;
import com.simsilica.lemur.SequenceModels;
import com.simsilica.lemur.Spinner;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.ValueEditors;
import com.simsilica.lemur.ValueRenderers;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.value.DefaultValueRenderer;
import jme3utilities.SimpleControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipParamsWidgetState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(ShipParamsWidgetState.class);
  
  private final Node gui = new Node("ship-parameters-widget-node");
  
  public ShipParamsWidgetState(Node guiNode) {
    guiNode.attachChild(gui);
  }

  @Override
  protected void initialize(Application application) {
    Container container = new Container();
    gui.attachChild(container);

    Label header = container.addChild(new Label("Base parameters", new ElementId("header")));
    header.setMaxWidth(320f);
    container.addChild(new Button("test"));
    
    SequenceModel<Double> model = SequenceModels.rangedSequence(new DefaultRangedValueModel(0, Double.MAX_VALUE, 0), 0.01, 0.01);
    DefaultValueRenderer<Double> renderer = ValueRenderers.formattedRenderer("%.02f", "error");
    Spinner<Double> spinner = container.addChild(new Spinner<>(model, renderer));
    spinner.setValueEditor(ValueEditors.doubleEditor("%.02f"));

    VersionedReference<Double> reference = model.createReference();
    
    container.addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (reference.update()) {
          logger.debug("value = {}", reference.get());
        }
      }
    });

    Container form = container.addChild(new Container(new SpringGridLayout(Axis.Y, Axis.X, FillMode.Even, FillMode.Even)));

    form.setBackground(new QuadBackgroundComponent(ColorRGBA.Brown));
    
    form.addChild(new Label("Engines"));
    form.addChild(new TextField(""), 1);
    
    form.addChild(new Label("Guns"));
    form.addChild(new TextField(""), 1);
    
    form.addChild(new Label("Caliber"));
    form.addChild(new TextField(""), 1);
    
    form.addChild(new Label("Shields"));
    form.addChild(new TextField(""), 1);
    
    form.addChild(new Label("Cargo"));
    form.addChild(new TextField(""), 1);

    container.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
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
