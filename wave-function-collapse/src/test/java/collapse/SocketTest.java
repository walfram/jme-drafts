package collapse;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cells.Side;
import com.jme3.math.Vector3f;
import java.util.Collection;
import java.util.Set;
import misc.InvertedVectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socket.Socket;
import socket.Vector3fSocket;

public class SocketTest {

  private static final Logger logger = LoggerFactory.getLogger(SocketTest.class);

  @Test
  void should_match_other_socket() {
    Collection<Vector3f> vectors = Set.of(
        Vector3f.UNIT_X,
        Vector3f.UNIT_X.mult(0.5f),
        Vector3f.UNIT_X.mult(1.5f)
    );
    
    Socket a = new Vector3fSocket(Side.X_POS, vectors);
    logger.debug("socket a = {}", a);
    
    Socket b = new Vector3fSocket(Side.X_NEG, new InvertedVectors(vectors).value());
    logger.debug("socket b = {}", b);
    
    assertTrue(a.matches(b));
    assertTrue(b.matches(a));
  }
  
}
