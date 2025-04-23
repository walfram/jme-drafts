package editor;

import com.google.common.base.Function;
import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.TextField;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.text.DocumentModel;
import com.simsilica.lemur.text.DocumentModelFilter;
import jme3utilities.SimpleControl;

public class ContainerUiState extends BaseAppState {
  
  private final Node scene = new Node("container-ui-scene");
  
  public ContainerUiState(Node guiNode) {
    guiNode.attachChild(scene);
  }
  
  @Override
  protected void initialize(Application application) {
    Container container = new Container();
    
    Function<Character, Character> inTransform = (c) -> Character.isDigit(c) ? c : null;
    Function<String, String> outTransform = (s) -> s;
    
    DocumentModelFilter model = new DocumentModelFilter(inTransform, outTransform);
    VersionedReference<DocumentModel> containerCountRef = model.createReference();
    
    container.addChild(new TextField(model));
    container.addChild(new Button("-10"), 1);
    container.addChild(new Button("+10"), 2);
    
    container.addControl(new SimpleControl() {
      @Override
      protected void controlUpdate(float updateInterval) {
        if (containerCountRef.update()) {
          getState(ContainerState.class).updateContainerCount(
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
}
