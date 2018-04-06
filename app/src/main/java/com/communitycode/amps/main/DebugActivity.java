package com.communitycode.amps.main;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.communitycode.amps.main.battery.reader.OneLineReader;

import java.io.File;

public class DebugActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private TextView debugInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        debugInfo = findViewById(R.id.debug_info);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        else {
            showDebugInfo();
        }
    }

    private void showDebugInfo() {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        debugInfo.setText("");

        BatteryManager mBatteryManager = (BatteryManager) this.getSystemService(Context.BATTERY_SERVICE);
        if (mBatteryManager != null) {
            int val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            debugInfo.append("BATTERY_PROPERTY_CURRENT_NOW = " + val + "\n");
            val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
            debugInfo.append("BATTERY_PROPERTY_CURRENT_AVERAGE = " + val + "\n");
            val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            debugInfo.append("BATTERY_PROPERTY_CAPACITY = " + val  + "\n");
            val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            debugInfo.append("BATTERY_PROPERTY_CHARGE_COUNTER = " + val  + "\n");
            val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
            debugInfo.append("BATTERY_PROPERTY_ENERGY_COUNTER = " + val  + "\n");
        }

        for (String file : SystemBatteryFiles.power_files) {
            debugInfo.append(file + ":\n");
            debugInfo.append(OneLineReader.getValue(new File(file)) + "\n\n");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDebugInfo();

                } else {

                    debugInfo.setText("No permissions granted");
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
