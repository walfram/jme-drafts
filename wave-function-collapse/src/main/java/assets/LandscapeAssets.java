package assets;

import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LandscapeAssets {

  private static final Logger logger = LoggerFactory.getLogger(LandscapeAssets.class);
  
  public List<String> paths() {
    Reflections reflections = new Reflections("landscape-assets", Scanners.Resources);
    Set<String> resources = reflections.getResources(".*\\.obj");

    logger.debug("found obj resources = {}", resources.size());

    List<String> filtered = resources.stream()
        .sorted()
        .filter(path -> path.contains("ground_"))
        .filter(path -> !path.contains("river"))
        .toList();

    logger.debug("filtered assets = {}", filtered.size());
    
    return filtered;
  }
}
