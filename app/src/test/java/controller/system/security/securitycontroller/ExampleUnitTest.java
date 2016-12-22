package controller.system.security.securitycontroller;

import org.junit.Test;

import utils.SMSParser;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void serialization_isCorrect() throws Exception {
        String payload = "Hello World 123";
        String serialString = SMSParser.serialize(payload);
        assertEquals(payload,SMSParser.deSerialize(serialString));
    }
}