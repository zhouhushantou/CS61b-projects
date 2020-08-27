import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    public OffByN offByOne = new OffByN(2);

    @Test
    public void testOff()
    {
        assertTrue(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('a', 'd'));
    }
}