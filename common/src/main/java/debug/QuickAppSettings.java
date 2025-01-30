package debug;

import com.jme3.system.AppSettings;

public class QuickAppSettings {

  public AppSettings settings() {
    AppSettings settings = new AppSettings(true);
    settings.setResolution(1600, 800);
    return settings;
  }
}
