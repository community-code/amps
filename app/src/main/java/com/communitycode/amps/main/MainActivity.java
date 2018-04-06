package com.communitycode.amps.main;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.BatteryManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.communitycode.amps.main.settings.SettingsActivity;

import java.text.NumberFormat;
import java.util.ArrayList;

import static com.communitycode.amps.main.Utils.convertCelsiusToFahrenheit;
import static com.communitycode.amps.main.Utils.flattenViewGroup;

public class MainActivity extends AppCompatActivity implements BatteryInfoInterface {
    private BatteryPresenter mBatteryPresenter;

    private void findAndSetText(int id, String text) {
        if (text == null) {
            findAndSetText(id, R.string.blank_value);
        }
        else {
            ((TextView) findViewById(id)).setText(text);
        }
    }

    private void findAndSetText(int id, int resourceId) {
        ((TextView) findViewById(id)).setText(getResources().getString(resourceId));
    }

    public void changeAccentColor(int colorResId) {
        int color = getResources().getColor(colorResId);
        final String ACCENT = getResources().getString(R.string.accent_tag);

        // update text views
        ArrayList<View> views = flattenViewGroup((ViewGroup) findViewById(R.id.root_main_activity));
        for (int i = 0 ; i < views.size(); i ++) {
            View view = views.get(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                Object tag = textView.getTag();
                if (tag != null && tag.equals(ACCENT)) {
                    textView.setTextColor(color);
                }
            }
        }

        // update throbber
        ProgressBar throbber = findViewById(R.id.indeterminateBar);
        throbber.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public void resetCurrentHistory(View view) {
        mBatteryPresenter.resetCurrentHistory();
    }

    public void goToCurrentInformation(View view) {
        BatteryInfoAlertDialog fragment = new BatteryInfoAlertDialog();
        getSupportFragmentManager()
                .beginTransaction()
                .add(fragment, "dialog")
                .commit();
    }
    
    public void showAmpInfoButton(boolean visible) {
        TextView textView = findViewById(R.id.amp_info);
        int i = visible ? View.VISIBLE : View.INVISIBLE;
        textView.setVisibility(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        findAndSetText(R.id.build_id_value, Build.ID);
        findAndSetText(R.id.android_version_value, Build.VERSION.RELEASE);
        findAndSetText(R.id.model_value, Build.MODEL);

        mBatteryPresenter = new BatteryPresenter(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_debug) {
            Intent intent = new Intent(this, DebugActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBatteryPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBatteryPresenter.stop();
    }

    @Override
    //    value in milliamps
    public void setMaxAmps(Integer value) {
        if (value == null) {
            findAndSetText(R.id.max_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.max_value, getString(R.string.mA, value));
        }
    }

    @Override
    //    value in milliamps
    public void setMinAmps(Integer value) {
        if (value == null) {
            findAndSetText(R.id.min_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.min_value, getString(R.string.mA, value));
        }
    }

    @Override
    //    value in milliamps
    public void setCurrentAmps(Integer value) {
        if (value == null) {
            findAndSetText(R.id.amps_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.amps_value, getString(R.string.mA, value));
        }
    }

    @Override
    // value in volts
    public void setVoltage(Double value) {
        if (value == null) {
            findAndSetText(R.id.voltage_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.voltage_value, getString(R.string.V, value));
        }
    }

    @Override
    //  value in Celsius
    public void setTemperature(Double value) {
        if (value == null) {
            findAndSetText(R.id.temperature_value, R.string.blank_value);
        }
        else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            boolean isCelsius = sharedPref.getBoolean("use_celsius", true);

            if (isCelsius) {
                findAndSetText(R.id.temperature_value, getString(R.string.degrees_c, value));
            }
            else {
                value = convertCelsiusToFahrenheit(value);
                findAndSetText(R.id.temperature_value, getString(R.string.degrees_f, value));
            }
        }
    }

    @Override
    //  status one of BatteryManager.BATTERY_STATUS_*
    public void setChargingStatus(int status) {
        int statusLbl;

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusLbl = R.string.battery_status_charging;
                changeAccentColor(R.color.chargingAccent);
                break;

            case BatteryManager.BATTERY_STATUS_FULL:
                statusLbl = R.string.battery_status_full;
                changeAccentColor(R.color.fullAccent);
                break;

            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                statusLbl = -1;
                break;

            case BatteryManager.BATTERY_STATUS_DISCHARGING:
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
            default:
                statusLbl = R.string.battery_status_discharging;
                changeAccentColor(R.color.dischargingAccent);
                break;
        }

        if (statusLbl != -1) {
            findAndSetText(R.id.charging_status_value, statusLbl);
        }
        else {
            findAndSetText(R.id.charging_status_value, R.string.blank_value);
        }

    }

    @Override
    //    plugged one of BatteryManager.BATTERY_PLUGGED_*
    public void setPluggedInStatus(int plugged) {
        int pluggedLbl;

        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                pluggedLbl = R.string.battery_plugged_wireless;
                break;

            case BatteryManager.BATTERY_PLUGGED_USB:
                pluggedLbl = R.string.battery_plugged_usb;
                break;

            case BatteryManager.BATTERY_PLUGGED_AC:
                pluggedLbl = R.string.battery_plugged_ac;
                break;

            default:
                pluggedLbl = R.string.battery_plugged_none;
                break;
        }

        findAndSetText(R.id.plugged_in_value, pluggedLbl);
    }

    @Override
    //    health one of BatteryManager.BATTERY_HEALTH_*
    public void setBatteryHealth(int health) {
        int healthLbl = -1;

        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                healthLbl = R.string.battery_health_cold;
                break;

            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthLbl = R.string.battery_health_dead;
                break;

            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthLbl = R.string.battery_health_good;
                break;

            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthLbl = R.string.battery_health_over_voltage;
                break;

            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthLbl = R.string.battery_health_overheat;
                break;

            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthLbl = R.string.battery_health_unspecified_failure;
                break;

            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
            default:
                break;
        }

        if (healthLbl == -1) {
            findAndSetText(R.id.health_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.health_value, healthLbl);
        }
    }

    @Override
    public void setBatteryPercent(Double value) {
        if (value == null) {
            findAndSetText(R.id.battery_level_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.battery_level_value, NumberFormat.getPercentInstance().format(value));
        }
    }

    @Override
    public void setBatteryTechnology(String value) {
        if (value == null) {
            findAndSetText(R.id.technology_value, R.string.blank_value);
        }
        else {
            findAndSetText(R.id.technology_value, value);
        }
    }
}
