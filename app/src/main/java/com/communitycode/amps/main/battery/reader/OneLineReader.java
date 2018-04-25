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


import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class OneLineReader {


    public static Integer getValue(File _f) {

        String text = null;

        FileInputStream fs = null;
        InputStreamReader sr = null;
        BufferedReader br = null;
        try {
            fs = new FileInputStream(_f);
            sr = new InputStreamReader(fs);
            br = new BufferedReader(sr);

            text = br.readLine();

        } catch (Exception ex) {
            // Expected to fail frequently due to permissions
            Log.d("Amps", ex.getMessage(), ex);
        }
        finally
        {
            try {
                if (fs != null) {
                    fs.close();
                }
                if (sr != null) {
                    sr.close();
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

        try {
            if (text != null) {
                value = Integer.parseInt(text);
            }
        } catch (NumberFormatException nfe) {
            // Expected to fail frequently due to unknown format of text
            Log.d("Amps", nfe.getMessage(), nfe);
        }

        return value;
    }
}