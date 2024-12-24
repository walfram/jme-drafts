package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.simsilica.lemur.input.Axis;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.input.Button;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;

public class PlayerControlsState extends BaseAppState {

  private static final FunctionId FUNC_SPEED_UP = new FunctionId("speed-up");
  private static final FunctionId FUNC_SPEED_DOWN = new FunctionId("speed-down");
  private static final FunctionId FUNC_SPEED_ZERO = new FunctionId("speed-zero");
  private static final FunctionId FUNC_MOUSE_DRAG_VERTICAL = new FunctionId("mouse-drag-vertical");
  private static final FunctionId FUNC_MOUSE_DRAG_HORIZONTAL = new FunctionId("mouse-drag-horizontal");
  private static final FunctionId FUNC_MOUSE_DRAG_TOGGLE = new FunctionId("mouse-drag-toggle");
  private static final FunctionId FUNC_ALTITUDE_UP = new FunctionId("altitude-up");
  private static final FunctionId FUNC_ALTITUDE_DOWN = new FunctionId("altitude-down");
  
  @Override
  protected void initialize(Application app) {
    InputMapper inputMapper = GuiGlobals.getInstance().getInputMapper();
    
    inputMapper.map(FUNC_SPEED_UP, KeyInput.KEY_W);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).speedUp(value, tpf), FUNC_SPEED_UP);
    
    inputMapper.map(FUNC_SPEED_DOWN, KeyInput.KEY_S);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).speedDown(value, tpf), FUNC_SPEED_DOWN);
    
    inputMapper.map(FUNC_SPEED_ZERO, KeyInput.KEY_0);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).speedZero(value, tpf), FUNC_SPEED_ZERO);
    
    inputMapper.map(FUNC_MOUSE_DRAG_HORIZONTAL, Axis.MOUSE_X, Button.MOUSE_BUTTON2);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).yaw(value, tpf), FUNC_MOUSE_DRAG_HORIZONTAL);
    inputMapper.map(FUNC_MOUSE_DRAG_VERTICAL, Axis.MOUSE_Y, Button.MOUSE_BUTTON2);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).pitch(value, tpf), FUNC_MOUSE_DRAG_VERTICAL);
    
    inputMapper.map(FUNC_MOUSE_DRAG_TOGGLE, Button.MOUSE_BUTTON2);
    inputMapper.addStateListener((func, state, tpf) -> getState(CameraState.class).updateCursorVisibility(state), FUNC_MOUSE_DRAG_TOGGLE);
    
    inputMapper.map(FUNC_ALTITUDE_UP, KeyInput.KEY_Q);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).altitudeUp(value, tpf), FUNC_ALTITUDE_UP);
    
    inputMapper.map(FUNC_ALTITUDE_DOWN, KeyInput.KEY_Z);
    inputMapper.addAnalogListener((func, value, tpf) -> getState(PlayerState.class).altitudeDown(value, tpf), FUNC_ALTITUDE_DOWN);
  }

  @Override
  protected void cleanup(Application app) {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void onDisable() {

  }
}
