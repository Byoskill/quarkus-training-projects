import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlanKTest {

    @Test
    public void test() {
        Assertions.assertTrue("".isBlank());
        Assertions.assertTrue("     ".isBlank());
        Assertions.assertTrue(" ".isBlank());
        Assertions.assertFalse("a ".isBlank());
    }
}
