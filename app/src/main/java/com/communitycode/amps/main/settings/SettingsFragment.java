package com.communitycode.amps.main.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.communitycode.amps.main.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}