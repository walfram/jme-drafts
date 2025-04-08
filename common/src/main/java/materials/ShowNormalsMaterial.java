package materials;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

public class ShowNormalsMaterial extends Material {
  public ShowNormalsMaterial(AssetManager assetManager) {
    super(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
  }
}
