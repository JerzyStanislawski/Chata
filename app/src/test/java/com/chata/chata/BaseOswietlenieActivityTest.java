package com.chata.chata;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BaseOswietlenieActivityTest {
    @Test
    public void parseStatus_isCorrect() {
        class TestOswietlenieActivity extends BaseOswietlenieActivity {

            @Override
            protected Map<Integer, Light> getSwitchList() {
                return new HashMap<>();
            }

            @Override
            protected String getHost() {
                return "";
            }
        }

        final String input="23=0\n24=1\n25=0";

        TestOswietlenieActivity oswietlenieActivity = new TestOswietlenieActivity();
        Map<Integer, Boolean> map = oswietlenieActivity.ParseStatus(input);

        assertEquals(map.get(23),false);
        assertEquals(map.get(24),true);
        assertEquals(map.get(25),false);
    }
}
