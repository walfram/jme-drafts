package endless.terrain;

import cells.Cell;
import cells.Cell2d;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import endless.terrain.heightmap.CellHeightmap;
import endless.terrain.heightmap.TerrainChunk;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import misc.Difference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerrainState extends BaseAppState {

  private static final Logger logger = LoggerFactory.getLogger(TerrainState.class);

  private final Node scene = new Node("terrain-scene");
  private Cell origin;

  private final Map<Cell, Geometry> cache = new HashMap<>(256);
  private final Map<Cell, Geometry> currentCells = new HashMap<>(256);

  private Material material;

  private float cellExtent;

  public TerrainState(Node rootNode) {
    rootNode.attachChild(scene);
  }

  @Override
  protected void initialize(Application app) {
    cellExtent = getState(ConfigState.class).cellExtent();

    // origin = new Cell2d(app.getCamera().getLocation(), cellExtent);

    material = new Material(app.getAssetManager(), Materials.LIGHTING);
    material.setBoolean("UseMaterialColors", true);
    material.setColor("Diffuse", ColorRGBA.LightGray);
    material.setColor("Ambient", ColorRGBA.Gray);
//    material.getAdditionalRenderState().setWireframe(true);

//    Set<Cell> neighbours = origin.neighboursAll();
//    for (Cell cell : neighbours) {
//      Geometry geometry = cellToGeometry(cell);
//      cache.put(cell, geometry);
//      currentCells.put(cell, geometry);
//      scene.attachChild(geometry);
//      logger.debug("attached chunk = {}", geometry.getName());
//    }
  }

  @Override
  public void update(float tpf) {
    Cell current = new Cell2d(getApplication().getCamera().getLocation(), cellExtent);

    if (current.equals(origin)) {
      return;
    }

    origin = current;

    Set<Cell> newCells = origin.neighboursAll();
    logger.debug("new cells = {}", newCells);

    Set<Cell> removeCells = new Difference<>(currentCells.keySet(), newCells).asSet();
    logger.debug("remove cells = {}", removeCells);
    for (Cell removeCell : removeCells) {
      scene.detachChildNamed(removeCell.toString());
    }
    currentCells.keySet().removeAll(removeCells);

    Set<Cell> addCells = new Difference<>(newCells, currentCells.keySet()).asSet();
    for (Cell cell : addCells) {
      Geometry geometry = cache.computeIfAbsent(cell, this::cellToGeometry);
      currentCells.put(cell, geometry);
      scene.attachChild(geometry);
      logger.debug("attached chunk = {}", geometry.getName());
    }

  }

  private Geometry cellToGeometry(Cell cell) {
    Mesh mesh = new TerrainChunk(
        new CellHeightmap(
            cell,
            (x, z) -> getState(NoiseState.class).calculateHeight(x, z),
            getState(ConfigState.class).terrainHeightmapResolution()
        ),
        getState(ConfigState.class).terrainChunkPartitions()
    ).mesh();
    
    Geometry geometry = new Geometry(cell.toString(), mesh);
    geometry.setLocalTranslation(cell.translation());
    geometry.setMaterial(material);

    ChunkLodControl lodControl = new ChunkLodControl(2f * cellExtent);
    geometry.addControl(lodControl);
    logger.debug("geometry {}, initial lod = {}", geometry.getName(), geometry.getLodLevel());

    return geometry;
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
