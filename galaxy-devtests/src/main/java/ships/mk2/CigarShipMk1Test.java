package ships.mk2;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Cylinder;
import common.ChaseCameraState;
import common.DebugAxesState;
import common.DebugGridState;
import common.LemurState;
import materials.ShowNormalsMaterial;
import math.ellipse.Ellipse;
import math.ellipse.EllipseXY;
import math.ellipse.EllipseXZ;
import math.ellipse.EllipseYZ;
import mesh.*;
import mesh.face.Face;
import mesh.face.SymmetricQuadFace;
import mesh.face.TriangleFace;
import misc.DebugPointMesh;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class CigarShipMk1Test extends SimpleApplication {
  
  private static final Logger logger = getLogger(CigarShipMk1Test.class);
  
  private static final float cellExtent = 4f;
  
  public static void main(String[] args) {
    CigarShipMk1Test app = new CigarShipMk1Test();
    app.start();
  }
  
  public CigarShipMk1Test() {
    super(
        new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
        new ConstantVerifierState(),
        
        new DebugGridState(cellExtent, false),
        new DebugAxesState(),
        new ChaseCameraState(),
        new LemurState()
    );
  }
  
  @Override
  public void simpleInitApp() {
    float zExtent = cellExtent * 16;
    float xExtent = cellExtent * 8f;
    float yExtent = cellExtent * 4f;

    int numberOfPoints = 6;
    int numberOfFrames = 12;

    Material material = new ShowNormalsMaterial(assetManager);

    Geometry hull = new Geometry("hull", new Ellipse3d(xExtent, yExtent, zExtent, numberOfFrames, numberOfPoints));
    hull.setMaterial(material);
    rootNode.attachChild(hull);

    Geometry engine = new Geometry("engine", new FlatShadedMesh(new Cylinder(2, numberOfPoints, 1.5f * cellExtent, 3f * cellExtent, 4f * cellExtent, true, false)));
    engine.setMaterial(material);
    engine.move(0, 0, -7.5f * 2f * cellExtent - 2f * cellExtent);
    rootNode.attachChild(engine);
  }

}
