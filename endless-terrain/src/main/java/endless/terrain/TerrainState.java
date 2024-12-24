package endless.terrain;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import noise.FastNoiseLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerrainState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(TerrainState.class);
  
  private final Node scene = new Node("terrain-scene");
  private Cell origin;
  
  private final Map<Cell, Geometry> cache = new HashMap<>(256);
  private final Map<Cell, Geometry> currentCells = new HashMap(256);
  
  private Material material;
  
  private final FastNoiseLite noise = new FastNoiseLite(42);
  private float cellExtent;

  {
    noise.SetFrequency(0.001f);
  }

  public TerrainState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application app) {
    cellExtent = getState(ConfigState.class).cellExtent();

    origin = new Cell2d(app.getCamera().getLocation(), cellExtent);

    Set<Cell> neighbours = origin.neighboursAll();
    
    material = new Material(app.getAssetManager(), Materials.UNSHADED);
    material.setColor("Color", ColorRGBA.Orange);
    material.setFloat("PointSize", 4f);
    
    for (Cell cell : neighbours) {
      Geometry geometry = createGeometry(cell);
      cache.put(cell, geometry);
      currentCells.put(cell, geometry);
      scene.attachChild(geometry);
    }
  }

  @Override
  public void update(float tpf) {
    Cell current = new Cell2d(getApplication().getCamera().getLocation(), cellExtent);
    
    if (current.equals(origin))
      return;
    
    origin = current;

    Set<Cell> newCells = origin.neighboursAll();
    logger.debug("new cells = {}", newCells);

    Set<Cell> removeCells = new Difference<>(currentCells.keySet(), newCells).asSet();
    logger.debug("remove cells = {}", removeCells);
    for (Cell removeCell: removeCells) {
      scene.detachChildNamed(removeCell.toString());
    }
    currentCells.keySet().removeAll(removeCells);
    
    Set<Cell> addCells = new Difference<>(newCells, currentCells.keySet()).asSet();
    for (Cell cell: addCells) {
      Geometry geometry = cache.computeIfAbsent(cell, this::createGeometry);
      currentCells.put(cell, geometry);
      scene.attachChild(geometry);
    }
    
  }

  private Geometry createGeometry(Cell cell) {
    List<Vector3f> points = createPoints(cell);
    Mesh mesh = new PointsMesh(points).create();
    
    Geometry geometry = new Geometry(cell.toString(), mesh);
    geometry.setLocalTranslation(cell.translation());
    geometry.setMaterial(material);
    return geometry;
  }

  private List<Vector3f> createPoints(Cell cell) {
    int resolution = 33;
    
    List<Vector3f> points = new ArrayList<>(resolution * resolution);

    float cellExtent = getState(ConfigState.class).cellExtent();
    float cellWidth = 2f * cellExtent;

    float delta = cellWidth / (resolution - 1);
    logger.debug("delta = {}", delta);

    Vector3f center = cell.translation();
    
    for (int x = 0; x < resolution; x++) {
      for (int z = 0; z < resolution; z++) {
        float dx = x * delta - cellExtent;
        float dz = z * delta - cellExtent;
        
        float e = calculateHeight(center.x + dx, center.z + dz);
        
        points.add(
            new Vector3f(dx, e, dz)
        );
      }
    }
    
    logger.debug("points size = {}", points.size());
    
    return points;
  }

  private float calculateHeight(float x, float z) {
    float e = noise.GetNoise(x, z);
    
    if (e < 0)
      e = 0f;
    
    return e * 64f;
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
