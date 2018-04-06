/*
 *  Copyright (c) 2010-2011 Ran Manor
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
import java.io.FileReader;

import android.util.Log;

public class SMemTextReader {

    public static Integer getValue() {

        boolean success = false;
        String text = null;
        BufferedReader br = null;
        FileReader fr = null;

        try {

            // @@@ debug StringReader fr = new StringReader("batt_id: 1\r\nbatt_vol: 3840\r\nbatt_vol_last: 0\r\nbatt_temp: 1072\r\nbatt_current: 1\r\nbatt_current_last: 0\r\nbatt_discharge_current: 112\r\nVREF_2: 0\r\nVREF: 1243\r\nADC4096_VREF: 4073\r\nRtemp: 70\r\nTemp: 324\r\nTemp_last: 0\r\npd_M: 20\r\nMBAT_pd: 3860\r\nI_MBAT: -114\r\npd_temp: 0\r\npercent_last: 57\r\npercent_update: 58\r\ndis_percent: 64\r\nvbus: 0\r\nusbid: 1\r\ncharging_source: 0\r\nMBAT_IN: 1\r\nfull_bat: 1300000\r\neval_current: 115\r\neval_current_last: 0\r\ncharging_enabled: 0\r\ntimeout: 30\r\nfullcharge: 0\r\nlevel: 58\r\ndelta: 1\r\nchg_time: 0\r\nlevel_change: 0\r\nsleep_timer_count: 11\r\nOT_led_on: 0\r\noverloading_charge: 0\r\na2m_cable_type: 0\r\nover_vchg: 0\r\n");
            fr = new FileReader("/sys/class/power_supply/battery/smem_text");
            br = new BufferedReader(fr);

            String line = br.readLine();

            while (line != null) {
                if (line.contains("I_MBAT")) {
                    text = line.substring(line.indexOf("I_MBAT: ") + 8);
                    success = true;
                    break;
                }
                line = br.readLine();
            }
        } catch (Exception ex) {
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


        Integer value = null;

        if (success) {

            try {
                value = Integer.parseInt(text);
            } catch (NumberFormatException nfe) {
                Log.e("Amps", nfe.getMessage(), nfe);
                value = null;
            }
        }

        return value;
    }

}