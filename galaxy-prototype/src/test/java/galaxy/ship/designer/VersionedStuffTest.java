package galaxy.ship.designer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.simsilica.lemur.SequenceModel;
import com.simsilica.lemur.core.VersionedHolder;
import com.simsilica.lemur.core.VersionedReference;
import galaxy.ship.designer.model.DoubleSequenceImpl;
import galaxy.domain.ship.Drives;
import org.junit.jupiter.api.Test;

public class VersionedStuffTest {

  @Test
  void test_versioned_holder() {
    VersionedHolder<Drives> holder = new VersionedHolder<>(new Drives(1));

    VersionedReference<Drives> reference = holder.createReference();
    assertFalse(reference.update());

    boolean updated = holder.updateObject(new Drives(2));
    assertTrue(updated);
    
    assertTrue(reference.update());
  }
  
  @Test
  void test_sequence_model() {
    SequenceModel<Double> model = new DoubleSequenceImpl(0);
    
    VersionedReference<Double> reference = model.createReference();
    assertFalse(reference.update());

    Double nextObject = model.getNextObject();
    assertEquals(1.0, nextObject);
    
    model.setObject(nextObject);
    assertTrue(reference.update());
  }
  
}
