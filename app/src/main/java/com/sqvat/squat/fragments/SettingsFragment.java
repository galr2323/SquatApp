package com.sqvat.squat.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.sqvat.squat.R;

/**
 * Created by GAL on 1/11/2015.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
