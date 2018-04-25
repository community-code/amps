package com.communitycode.amps.main.battery;


import com.communitycode.amps.main.battery.reader.BatteryAttrTextReader;
import com.communitycode.amps.main.battery.reader.OneLineReader;
import com.communitycode.amps.main.battery.reader.SMemTextReader;

import java.io.File;

public class UnofficialBatteryMethod implements BatteryMethodInterface {
    public String dischargeField;
    public String chargeField;
    public String filePath;
    public float scale;
    public int reader;
    public transient String[] modelFilter;

    public UnofficialBatteryMethod(int reader, String filePath, float scale, String dischargeField, String chargeField, String[] modelFilter) {
        this.filePath = filePath;
        this.scale = scale;
        this.reader = reader;
        this.dischargeField = dischargeField;
        this.chargeField = chargeField;
        this.modelFilter = modelFilter;
    }

    public boolean checkModelFilter(String model) {
        if (modelFilter.length == 0) {
            return true;
        }

        for (String val : modelFilter) {
            if (model.contains(val)) {
                return true;
            }
        }
        return false;
    }

    public boolean isApplicable(String model) {
        if (checkModelFilter(model)) {
            File f = new File(filePath);
            return f.exists();
        }
        return false;
    }

    public Integer read() {
        File f = new File(filePath);
        Integer val = null;

        switch (reader) {
            case 1:
                val = OneLineReader.getValue(f);
                break;
            case 2:
                val = BatteryAttrTextReader.getValue(f, this.dischargeField, this.chargeField);
                break;
            case 3:
                val = SMemTextReader.getValue();
                break;
        }

        if (val == null) {
            return null;
        }
        else {
            return Math.round(val * scale);
        }
    }

    public boolean equalsIgnoreTransient(UnofficialBatteryMethod b) {
        return filePath.equals(b.filePath)
                && reader == b.reader
                && scale == b.scale
                && chargeField.equals(b.chargeField)
                && dischargeField.equals(b.dischargeField);
    }
}
