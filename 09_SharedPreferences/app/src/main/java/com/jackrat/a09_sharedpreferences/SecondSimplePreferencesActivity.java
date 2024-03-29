package com.jackrat.a09_sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

public class SecondSimplePreferencesActivity extends BaseSimplePreferencesActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!settingsPrv.contains(PREFERENCE_STRING_NAME)) {
            SharedPreferences.Editor prefEditor = settingsPrv.edit();
            prefEditor.putString(PREFERENCE_STRING_NAME, this.getLocalClassName());
            prefEditor.putLong("SomeLong", java.lang.Long.MIN_VALUE);
            prefEditor.apply();
        }
    }

    @Override
    protected Class<?> GetTargetClass() {
        return FirstSimplePreferencesActivity.class;
    }
}
