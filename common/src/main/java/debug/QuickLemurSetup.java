package debug;

import com.jme3.app.Application;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

public class QuickLemurSetup {

  public void applyTo(Application app) {
    GuiGlobals.initialize(app);
    BaseStyles.loadGlassStyle();
    GuiGlobals.getInstance().getStyles().setDefaultStyle(BaseStyles.GLASS);
  }
}
