package com.hsmerseburg.mariia.popularmovies1.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.hsmerseburg.mariia.popularmovies1.R;

/**
 * Created by 2mdenyse on 03.05.2017.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences_menu);
    }
}
