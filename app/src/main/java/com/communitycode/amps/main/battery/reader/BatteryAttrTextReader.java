/*
 *  Copyright (c) 2010-2013 Ran Manor
 *
 *  This file is part of CurrentWidget.
 *
 * 	CurrentWidget is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CurrentWidget is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CurrentWidget.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Modified 23 March 2018
 */


package com.communitycode.amps.main.battery.reader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.util.Log;

public class BatteryAttrTextReader {

    public static Integer getValue(File f, String dischargeField, String chargeField) {

        String text;
        Integer value = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {

            // @@@ debug
            //StringReader fr = new StringReader("vref: 1248\r\nbatt_id: 3\r\nbatt_vol: 4068\r\nbatt_current: 0\r\nbatt_discharge_current: 123\r\nbatt_temperature: 329\r\nbatt_temp_protection:normal\r\nPd_M:0\r\nI_MBAT:-313\r\npercent_last(RP): 94\r\npercent_update: 71\r\nlevel: 71\r\nfirst_level: 100\r\nfull_level:100\r\ncapacity:1580\r\ncharging_source: USB\r\ncharging_enabled: Slow\r\n");
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            String line = br.readLine();

            final String chargeFieldHead = chargeField + ": ";
            final String dischargeFieldHead = dischargeField + ": ";


            while (line != null)
            {
                if (line.contains(chargeField))
                {
                    text = line.substring(line.indexOf(chargeFieldHead) + chargeFieldHead.length());
                    try {
                        value = Integer.parseInt(text);
                        if (value != 0)
                            break;
                    }
                    catch (NumberFormatException nfe) {
                        Log.e("Amps", nfe.getMessage(), nfe);
                    }
                }

                //  "batt_discharge_current:"
                if (line.contains(dischargeField))
                {
                    text = line.substring(line.indexOf(dischargeFieldHead) + dischargeFieldHead.length());
                    try {
                        value = (-1)*Math.abs(Integer.parseInt(text));
                    }
                    catch (NumberFormatException nfe) {
                        Log.e("Amps", nfe.getMessage(), nfe);
                    }
                    break;
                }

                line = br.readLine();
            }
        }
        catch (Exception ex) {
            Log.e("Amps", ex.getMessage(), ex);
        }
        finally
        {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            }
            catch (Exception ex)
            {
                Log.e("Amps", ex.getMessage(), ex);
            }
        }

        return value;
    }

}
