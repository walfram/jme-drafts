package galaxy.ship.designer;

import com.simsilica.lemur.SequenceModel;
import com.simsilica.lemur.core.VersionedReference;
import java.util.List;

public class CompositeVersionedReference<T extends Number> extends VersionedReference<T> {

  private final List<SequenceModel<? extends Number>> models;
  
  public CompositeVersionedReference(List<SequenceModel<? extends Number>> models) {
    super();
    this.models = models;
  }

}
