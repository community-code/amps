package com.communitycode.amps.main;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Utils {
    public static ArrayList<View> flattenViewGroup(ViewGroup root) {
        ArrayList<View> views = new ArrayList<>();
        if (root == null) {
            return views;
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            final View child = root.getChildAt(i);
            views.add(child);
            if (child instanceof ViewGroup) {
                views.addAll(flattenViewGroup((ViewGroup) child));
            }
        }
        return views;
    }

    public static double convertCelsiusToFahrenheit(double value) {
        return value*1.8 + 32;
    }
}
