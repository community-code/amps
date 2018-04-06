package com.communitycode.amps.main;

import org.junit.Test;

import static com.communitycode.amps.main.Utils.convertCelsiusToFahrenheit;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void convertCelsiusToFahrenheit_sanity() throws Exception {
        assertEquals(32, convertCelsiusToFahrenheit(0), 0);
        assertEquals(212, convertCelsiusToFahrenheit(100), 0);
    }

}