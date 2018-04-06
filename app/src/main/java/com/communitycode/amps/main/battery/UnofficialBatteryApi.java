package com.communitycode.amps.main.battery;


import android.os.Build;

import java.util.Locale;

public class UnofficialBatteryApi {
    public static final String BUILD_MODEL = Build.MODEL.toLowerCase(Locale.ENGLISH);

    public static Integer getCurrent() {
        for (UnofficialBatteryMethod unofficialBatteryMethod : methods) {
            if (unofficialBatteryMethod.isApplicable(BUILD_MODEL)) {
                Integer val = unofficialBatteryMethod.read();
                if (val != null) {
                    return val;
                }
            }
        }
        return null;
    }

    public static final UnofficialBatteryMethod[] methods = {
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_max", 1.0F, null, null, new String[]{"gt-i9300", "gt-i9300T", "gt-i9305", "gt-i9305N", "gt-i9305T", "shv-e210k", "shv-e210l", "shv-e210s", "sgh-t999", "sgh-t999l", "sgh-t999v", "sgh-i747", "sgh-i747m", "sgh-n064", "sc-06d", "sgh-n035", "sc-03e", "SCH-j021", "scl21", "sch-r530", "sch-i535", "sch-S960l", "gt-i9308", "sch-i939", "sch-s968c"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_now", 1.0F, null, null, new String[]{"nexus 7", "one", "lg-d851"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/da9052-bat/current_avg", 1.0F, null, null, new String[]{"sl930"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_now", 1.0F, null, null, new String[]{"sgh-i337", "gt-i9505", "gt-i9500", "sch-i545", "find 5", "sgh-m919", "sgh-i537"}),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/mt6329-battery/FG_Battery_CurrentConsumption", 1.0F, null, null, new String[]{"cynus"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/BatteryAverageCurrent", 1.0F, null, null, new String[]{"zp900", "jy-g3", "zp800", "zp800h", "zp810", "w100", "zte v987"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_avg", 1.0F, null, null, new String[]{"gt-p31", "gt-p51"}),
            new UnofficialBatteryMethod(2, "/sys/class/power_supply/battery/batt_attr_text", 1.0F, "I_MBAT", "I_MBAT", new String[]{"htc one x"}),
            new UnofficialBatteryMethod(2, "/sys/class/power_supply/battery/smem_text", 1.0F, "eval_current", "batt_current", new String[]{"wildfire s"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_now", 1.0F, null, null, new String[]{"triumph", "ls670", "gt-i9300", "sm-n9005", "gt-n7100", "sgh-i317"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/batt_current", 1.0F, null, null, new String[]{"desire hd", "desire z", "inspire", "pg41200"}),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_now", 0.1F, null, null, new String[]{"LG-D850", "LG-D851", "LG-D852", "LG-D855", "LG-D856", "LG-D858", "LG-D859"}),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/ds2784-battery/getcurrent", 0.001F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/i2c-adapter/i2c-0/0-0036/power_supply/ds2746-battery/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/i2c-adapter/i2c-0/0-0036/power_supply/battery/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/tegra-i2c.4/i2c-4/4-0040/current1_input", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/bq27425_battery/charge_now", 0.1F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/bq27541-bat/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(3, "/sys/class/power_supply/battery/smem_text", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/batt_current", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(2, "/sys/class/power_supply/battery/batt_attr_text", 1.0F, "batt_discharge_current", "batt_current", new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/batt_chg_current", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/charger_current", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/max17042-0/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/bq27520/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/cpcap_battery/power_supply/usb/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/EcControl/BatCurrent", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/batt_current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/batt_current_adc", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/max170xx_battery/current_now", 0.001F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/ab8500_fg/current_now", 0.001F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/android-battery/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/ds2784-fuelgauge/current_now", 0.001F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/Battery/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/msm-charger/power_supply/battery_gauge/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/battery/power_supply/battery/BatteryAverageCurrent", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/mt6320-battery/power_supply/battery/BatteryAverageCurrent", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/devices/platform/msm-battery/power_supply/battery/chg_current_adc", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/bq27x41/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/bq27541_battery/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/cw2015_battery/current_now", 0.001F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/dollar_cove_battery/current_now", 0.001F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/bms/current_now", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_avg", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/BatteryAverageCurrent", 1.0F, null, null, new String[0]),
            new UnofficialBatteryMethod(1, "/sys/class/power_supply/battery/current_max", 1.0F, null, null, new String[0])
    };


}
