package com.waldm.MastermindAndroid;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {
    public static final String KEY_PREF_CODE_LENGTH = "pref_codeLength_key";
    public static final String KEY_PREF_NUMBER_OF_COLOURS = "pref_numberOfColours_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        setTitle(R.string.settings);
    }
}
