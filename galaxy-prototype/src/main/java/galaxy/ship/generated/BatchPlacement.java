package galaxy.ship.generated;

import java.util.List;

public interface BatchPlacement {
    // TODO remove batches argument in favour of constructor argument
    List<List<BatchPosition>> arrange(int batches);
}
