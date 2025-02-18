package galaxy.ship.designer;

import static com.jme3.scene.plugins.fbx.RotationOrder.values;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.util.res.Resources;
import galaxy.domain.ship.ShipDesign;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShipDesignIOState extends BaseAppState {

  private final ObjectMapper mapper = new ObjectMapper();

  private final Map<String, ShipDesign> designs = new LinkedHashMap<>();

  @Override
  protected void initialize(Application app) {
    try (InputStream is = Resources.getResourceAsStream("designs/ai-0.json")) {

      JsonNode root = mapper.readTree(is);

      for (JsonNode child : root) {
        String name = child.get("name").asText();
        JsonNode designNode = child.get("design");

        ShipDesign design = new ShipDesign(
            designNode.get("engines").doubleValue(),
            designNode.get("guns").intValue(),
            designNode.get("caliber").doubleValue(),
            designNode.get("shields").doubleValue(),
            designNode.get("cargo").doubleValue()
        );
        
        designs.put(name, design);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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

  public Map<String, ShipDesign> designs() {
    return designs;
  }
}
