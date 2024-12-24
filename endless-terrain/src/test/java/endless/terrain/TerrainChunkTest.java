package endless.terrain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cells.Cell;
import cells.Cell2d;
import com.jme3.scene.Mesh;
import endless.terrain.heightmap.CellHeightmap;
import endless.terrain.heightmap.Heightmap;
import endless.terrain.heightmap.TerrainChunk;
import endless.terrain.heightmap.WrappedNoise;
import noise.FastNoiseLite;
import org.junit.jupiter.api.Test;

public class TerrainChunkTest {

  // cell -> heightmap -> mesh
  @Test
  void should_mesh_terrain_chunk_from_cell() {
    Cell origin = new Cell2d(0, 0, 256f);

    final FastNoiseLite noise = new FastNoiseLite();

    WrappedNoise wrappedNoise = noise::GetNoise;
    Heightmap heightmap = new CellHeightmap(origin, wrappedNoise, 17);

    Mesh mesh = new TerrainChunk(heightmap, new int[]{2, 4, 16}).mesh();

    assertEquals(16 * 16 * 2, mesh.getTriangleCount());

    assertEquals(16 * 16 * 2, mesh.getTriangleCount(0));
    assertEquals(8 * 8 * 2, mesh.getTriangleCount(1));
    assertEquals(4 * 4 * 2, mesh.getTriangleCount(2));
    assertEquals(2, mesh.getTriangleCount(3));
  }

}
