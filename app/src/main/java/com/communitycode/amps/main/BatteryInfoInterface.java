package com.communitycode.amps.main;

public interface BatteryInfoInterface {
    // value in milliamps
    void setMaxAmps(Integer value);

    // value in milliamps
    void setMinAmps(Integer value);

    // value in milliamps
    void setCurrentAmps(Integer value);

    // value in volts
    void setVoltage(Double value);

    //  value in Celsius
    void setTemperature(Double value);

    //  status one of BatteryManager.BATTERY_STATUS_*
    void setChargingStatus(int status);

    //  plugged one of BatteryManager.BATTERY_PLUGGED_*
    void setPluggedInStatus(int plugged);

    // health one of BatteryManager.BATTERY_HEALTH_*
    void setBatteryHealth(int health);

    void setBatteryPercent(Double value);

    void setBatteryTechnology(String value);

    void showAmpInfoButton(boolean visible);
}
