package materials;

import com.jme3.material.Material;

public class WireframeMaterial extends Material {

  public WireframeMaterial(Material source) {
    super(source.getMaterialDef());
    getAdditionalRenderState().setWireframe(true);
  }
}
