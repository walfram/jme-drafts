package endless.terrain.heightmap;

import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Format;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerrainChunk {

  private static final Logger logger = LoggerFactory.getLogger(TerrainChunk.class);
  
  private final Heightmap heightmap;

  private final ReIndexedVertices reIndexedVertices = new ReIndexedVertices();
  private final int[] partitionBy;

  public TerrainChunk(Heightmap heightmap, int[] partitionBy) {
    this.heightmap = heightmap;
    this.partitionBy = partitionBy;
  }

  public Mesh mesh() {
    Mesh mesh = new Mesh();
    mesh.setMode(Mode.Triangles);

    // set buffers etc
    // heightmap to quads/triangles
    List<Triangle> triangles = heightmap.triangles();

    Vector3f[] vertices = triangles
        .stream()
        .flatMap(t -> Stream.of(t.get1(), t.get2(), t.get3()))
        .toArray(Vector3f[]::new);

    FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices);
    mesh.setBuffer(Type.Position, 3, vertexBuffer);

    Vector3f[] normals = triangles
        .stream()
        .flatMap(t -> Stream.of(t.getNormal(), t.getNormal(), t.getNormal()))
        .toArray(Vector3f[]::new);
    FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(normals);
    mesh.setBuffer(Type.Normal, 3, normalBuffer);

    int[] indices = IntStream.range(0, triangles.size() * 3).toArray();
    logger.debug("last index = {}", indices[indices.length - 1]);
    IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices);
    mesh.setBuffer(Type.Index, 3, indexBuffer);

    // lod levels -> index buffers
    // 0, 1, 2, 3
    VertexBuffer lod0Buffer = new VertexBuffer(Type.Index);
    lod0Buffer.setupData(Usage.Dynamic, 3, Format.UnsignedInt, indexBuffer);

    VertexBuffer lod1Buffer = new VertexBuffer(Type.Index);
    int[] lod1 = reIndexedVertices.apply(heightmap.quadsPerSide(), partitionBy[0])
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();
    logger.debug("lod1 = {}", lod1);
    lod1Buffer.setupData(Usage.Dynamic, 3, Format.UnsignedInt, BufferUtils.createIntBuffer(lod1));

    VertexBuffer lod2Buffer = new VertexBuffer(Type.Index);
    int[] lod2 = reIndexedVertices.apply(heightmap.quadsPerSide(), partitionBy[1])
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();
    logger.debug("lod2 = {}", lod2);
    lod2Buffer.setupData(Usage.Dynamic, 3, Format.UnsignedInt, BufferUtils.createIntBuffer(lod2));

    VertexBuffer lod3Buffer = new VertexBuffer(Type.Index);
    int[] lod3 = reIndexedVertices.apply(heightmap.quadsPerSide(), partitionBy[2])
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();
    logger.debug("lod3 = {}", lod3);
    lod3Buffer.setupData(Usage.Dynamic, 3, Format.UnsignedInt, BufferUtils.createIntBuffer(lod3));

    VertexBuffer[] lodLevels = new VertexBuffer[]{
        lod0Buffer,
        lod1Buffer,
        lod2Buffer,
        lod3Buffer
    };
    mesh.setLodLevels(lodLevels);

    mesh.updateBound();
    mesh.updateCounts();

    return mesh;
  }
}
