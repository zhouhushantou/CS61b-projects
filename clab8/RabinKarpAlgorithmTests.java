import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "helloffggdjk";
        String pattern = "ggd";
        assertEquals(7, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }
}
