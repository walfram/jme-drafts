package endless.terrain;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;

public class PostProcessingState extends BaseAppState {

  @Override
  protected void initialize(Application app) {
    FilterPostProcessor filterPostProcessor = new FilterPostProcessor(app.getAssetManager());
    BloomFilter bloomFilter = new BloomFilter(BloomFilter.GlowMode.Objects);
    filterPostProcessor.addFilter(bloomFilter);
    app.getViewPort().addProcessor(filterPostProcessor);
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
