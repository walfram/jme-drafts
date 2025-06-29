package tests;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.SimpleApplication;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.event.DefaultMouseListener;
import com.simsilica.lemur.event.MouseEventControl;
import debug.QuickAppSetup;
import jme3utilities.MyMesh;
import jme3utilities.mesh.*;
import mesh.FlatShadedMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeshShapesTest extends SimpleApplication {

  public static void main(String[] args) {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);

    MeshShapesTest app = new MeshShapesTest();
    app.setSettings(settings);
    app.setShowSettings(false);
    app.start();
  }

  private static final Logger logger = LoggerFactory.getLogger(MeshShapesTest.class);

  @Override
  public void simpleInitApp() {
    new QuickAppSetup().applyTo(this);
    GuiGlobals.initialize(this);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    List<Mesh> meshes = List.of(
        new Cone(7, 10, 10, true),
        new Dodecahedron(10, Mode.Triangles),
        new Icosahedron(10, true),
        new Icosphere(1, 10),
        new Octahedron(10, true),
        new Octasphere(1, 10),
        new Prism(7, 10, 10, true),
        new Tetrahedron(10, true),
        new Cylinder(5, 7, 10, 10, true),
        new Torus(5, 7, 5, 10)
    );

    Cell origin = new Cell2d(0, 0, 32);

    Set<Cell> cells = Stream.of(
            origin.neighboursAll(),
            new Cell2d(0, -1, 32).neighboursAll()
        )
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
    
    logger.debug("cells = {}", cells.size());

    Iterator<Cell> iterator = cells.iterator();

    for (Mesh mesh : meshes) {
      boolean hasNormals = MyMesh.hasAnyNormals(mesh);
      logger.debug("mesh {}, normals = {}", mesh.getClass().getSimpleName(), hasNormals);

      Geometry geometry = new Geometry(mesh.getClass().getSimpleName(), new FlatShadedMesh(mesh));
      geometry.setMaterial(material);

      Cell c = iterator.next();
      geometry.setLocalTranslation(c.translation());

      rootNode.attachChild(geometry);

      MouseEventControl.addListenersToSpatial(geometry, new DefaultMouseListener() {
        @Override
        protected void click(MouseButtonEvent event, Spatial target, Spatial capture) {
          logger.debug("click on {}", target);
        }
      });
    }

    //    Geometry cone = new Geometry("cone", new Cone(7, 10, 10, true));
    //    cone.setMaterial(material);
    //    rootNode.attachChild(cone);

    //    Geometry cylinder = new Geometry("hull", new Cylinder(5, 7, 1, 1, true));
    //    cylinder.setMaterial(material);
    //    rootNode.attachChild(cylinder);
  }
}
