package com.communitycode.amps.main.settings;

import com.communitycode.amps.main.BatteryInfoInterface;
import com.communitycode.amps.main.CurrentTracker;
import com.communitycode.amps.main.battery.BatteryMethodInterface;
import com.communitycode.amps.main.battery.OfficialBatteryMethod;
import com.communitycode.amps.main.battery.UnofficialBatteryMethod;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class BatteryMethodPicklerTest {
    @Test
    public void official() throws Exception {
        OfficialBatteryMethod officialBatteryMethod = new OfficialBatteryMethod(null);
        String json = BatteryMethodPickler.toJson(officialBatteryMethod);
        BatteryMethodInterface method = BatteryMethodPickler.fromJson(json, null);
        assertTrue(OfficialBatteryMethod.class.isInstance(method));
    }

    @Test
    public void unofficial_null() throws Exception {
        UnofficialBatteryMethod unofficialBatteryMethod = new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_max", 1.0F, null, null, new String[]{});
        String json = BatteryMethodPickler.toJson(unofficialBatteryMethod);
        UnofficialBatteryMethod method = (UnofficialBatteryMethod) BatteryMethodPickler.fromJson(json, null);
        assertEquals(method.chargeField, unofficialBatteryMethod.chargeField);
        assertEquals(method.dischargeField, unofficialBatteryMethod.dischargeField);
        assertEquals(method.filePath, unofficialBatteryMethod.filePath);
        assertEquals(method.reader, unofficialBatteryMethod.reader);
        assertEquals(method.scale, unofficialBatteryMethod.scale, 0.01);
    }

    @Test
    public void unofficial_full() throws Exception {
        UnofficialBatteryMethod unofficialBatteryMethod = new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_max", 1.0F, "asdf", "fdsa", new String[]{});
        String json = BatteryMethodPickler.toJson(unofficialBatteryMethod);
        UnofficialBatteryMethod method = (UnofficialBatteryMethod) BatteryMethodPickler.fromJson(json, null);
        assertEquals(method.chargeField, unofficialBatteryMethod.chargeField);
        assertEquals(method.dischargeField, unofficialBatteryMethod.dischargeField);
        assertEquals(method.filePath, unofficialBatteryMethod.filePath);
        assertEquals(method.reader, unofficialBatteryMethod.reader);
        assertEquals(method.scale, unofficialBatteryMethod.scale, 0.01);
    }
}
