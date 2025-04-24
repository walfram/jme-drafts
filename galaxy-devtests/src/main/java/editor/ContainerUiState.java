package editor;

import com.google.common.base.Function;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;
import jme3utilities.SimpleControl;

import static java.lang.Integer.max;

public class ContainerUiState extends BaseAppState {
  
  private final Node scene = new Node("container-ui-scene");
  
  public ContainerUiState(Node guiNode) {
    guiNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application application) {
    Container panel = new Container();
    
    scene.attachChild(panel);
    panel.setLocalTranslation(10, application.getCamera().getHeight() - 10, 0);
    
    Label title = panel.addChild(new Label("containers", new ElementId("title")));
    title.setMaxWidth(320);
    
    Function<Character, Character> inTransform = (c) -> Character.isDigit(c) ? c : null;
    Function<String, String> outTransform = (s) -> s;
    
    DocumentModelFilter model = new DocumentModelFilter(inTransform, outTransform);
    VersionedReference<DocumentModel> containerCountRef = model.createReference();
    
    Container container = panel.addChild(new Container());
    
    container.addChild(new TextField(model));
    container.addChild(new Button("-10"), 1).addClickCommands(new ChangeValueCommand(-10, model));
    container.addChild(new Button("+10"), 2).addClickCommands(new ChangeValueCommand(10, model));
    container.addChild(new Button("-100"), 3).addClickCommands(new ChangeValueCommand(-100, model));
    container.addChild(new Button("+100"), 4).addClickCommands(new ChangeValueCommand(100, model));
    
    container.addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (containerCountRef.update()) {
//          getState(ContainerState.class).updateContainerCount(
//              Integer.parseInt(containerCountRef.get().getText())
//          );
//          getState(HullState.class).updateContainerCount(
//              Integer.parseInt(containerCountRef.get().getText())
//          );
          getState(ShipState.class).updateContainerCount(
              Integer.parseInt(containerCountRef.get().getText())
          );
        }
      }
    });
    
    model.setText("111");
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
  
  private static final class ChangeValueCommand implements Command<Button> {
    private final int delta;
    private final DocumentModelFilter model;
    
    public ChangeValueCommand(int delta, DocumentModelFilter model) {
      this.delta = delta;
      this.model = model;
    }
    
    @Override
    public void execute(Button source) {
      int value = Integer.parseInt(model.getText());
      model.setText(String.valueOf(max(1, value + delta)));
    }
  }
  
  
}
