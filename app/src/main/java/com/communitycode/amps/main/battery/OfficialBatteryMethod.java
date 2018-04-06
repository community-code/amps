package com.communitycode.amps.main.battery;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;

public class OfficialBatteryMethod implements BatteryMethodInterface {
    private final transient Context mCtx;

    public OfficialBatteryMethod(Context context) {
        mCtx = context;
    }
    public Integer read() {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }

        BatteryManager mBatteryManager = (BatteryManager) mCtx.getSystemService(Context.BATTERY_SERVICE);
        if (mBatteryManager == null) {
            return null;
        }

        int current = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        if (current != Integer.MIN_VALUE) {
            return current / 1000;
        }

        return null;
    }
}
