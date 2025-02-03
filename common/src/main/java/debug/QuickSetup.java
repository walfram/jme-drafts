package debug;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import jme3utilities.debug.AxesVisualizer;

public class QuickSetup {

  private final float gridCellExtent;
  private final int gridLines;

  public QuickSetup() {
    this(32f, 16);
  }

  public QuickSetup(float gridCellExtent, int gridLines) {
    this.gridCellExtent = gridCellExtent;
    this.gridLines = gridLines;
  }

  public void applyTo(SimpleApplication app) {
    app.getFlyByCamera().setDragToRotate(true);
    app.getFlyByCamera().setMoveSpeed(50);
    app.getFlyByCamera().setZoomSpeed(0);

    AxesVisualizer axesVisualizer = new AxesVisualizer(app.getAssetManager(), 256, 1);
    app.getRootNode().addControl(axesVisualizer);
    axesVisualizer.setEnabled(true);

    new DebugGrid(app.getAssetManager(), gridCellExtent, gridLines).attachTo(app.getRootNode());

    app.getCamera().setLocation(new Vector3f(33.416954f, 22.45838f, 58.60264f));
    app.getCamera().setRotation(new Quaternion(-0.041742355f, 0.95605385f, -0.16932712f, -0.23568396f));
  }
}
