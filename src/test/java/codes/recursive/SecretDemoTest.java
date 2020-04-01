package codes.recursive;

import com.fnproject.fn.testing.*;
import org.junit.*;

import static org.junit.Assert.*;

public class SecretDemoTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnDecodedSecret() {
        /*
        testing.givenEvent().enqueue();
        testing.thenRun(SecretDemo.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals("hunter2", result.getBodyAsString());
        */
    }

}