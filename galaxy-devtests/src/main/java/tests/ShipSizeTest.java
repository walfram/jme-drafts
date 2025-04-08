package tests;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.DefaultRangedValueModel;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.RangedValueModel;
import com.simsilica.lemur.Slider;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.ElementId;
import debug.QuickAppSettings;
import debug.QuickChaseCamera;
import debug.QuickAppSetup;
import debug.QuickLemurSetup;
import jme3utilities.SimpleControl;
import jme3utilities.mesh.Octasphere;
import mesh.FlatShadedMesh;

public class ShipSizeTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new QuickAppSettings().settings();

    ShipSizeTest app = new ShipSizeTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  @Override
  public void simpleInitApp() {
    new QuickAppSetup().applyTo(this);
    new QuickLemurSetup().applyTo(this);
    
    Container container = new Container();
    Label header = container.addChild(new Label("ship size test", new ElementId("title")));
    header.setMaxWidth(320);

    RangedValueModel model = new DefaultRangedValueModel(1, 256, 1);
    container.addChild(new Slider(model));

    Label value = container.addChild(new Label("%.02f".formatted(model.getValue())));
    value.addControl(new SimpleControl() {
      private final VersionedReference<Double> ref = model.createReference();
      @Override
      protected void controlUpdate(float updateInterval) {
        if (ref.update()) {
          value.setText("%.02f".formatted(ref.get()));
        }
      }
    });
    
    guiNode.attachChild(container);
    container.setLocalTranslation(10, cam.getHeight() - 10, 0);

    Geometry ship = new Geometry("ship", new FlatShadedMesh(new Octasphere(1, 1)));
    ship.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md"));
    rootNode.attachChild(ship);
    
    ship.addControl(new SimpleControl() {
      private final VersionedReference<Double> ref = model.createReference();
      @Override
      protected void controlUpdate(float updateInterval) {
        if (ref.update()) {
          getSpatial().setLocalScale(ref.get().floatValue());
        }
      }
    });

    new QuickChaseCamera(cam, inputManager).attachTo(rootNode);
  }
}
