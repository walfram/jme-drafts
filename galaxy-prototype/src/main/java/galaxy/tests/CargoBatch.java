package galaxy.tests;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Cylinder;

public class CargoBatch extends Node {

  private static final float extent = 4f;

  private static final Mesh box = new WireBox(1.95f * extent, 2f * extent, 1.95f * extent);
  private static final Mesh cylinder = new Cylinder(2, 6, 0.95f * extent, 1.95f * extent, true);

  private static final int[] range = new int[]{-1, 1};

  public CargoBatch(AssetManager assetManager) {
    super("cargo-batch");

    Geometry bound = new Geometry("bound", box);
    bound.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md"));
    bound.getMaterial().getAdditionalRenderState().setWireframe(true);
    attachChild(bound);

    Material material = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");

    for (int x : range) {
      for (int y : range) {
        for (int z : range) {
          Geometry geometry = new Geometry("container-%s-%s".formatted(x, z), cylinder);
          geometry.setMaterial(material);
          geometry.setLocalTranslation(x * extent, y * extent, z * extent);
          attachChild(geometry);
        }
      }
    }
  }

}
