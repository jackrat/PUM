package com.jackrat.a09_sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

public class FirstSimplePreferencesActivity extends BaseSimplePreferencesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!settingsPrv.contains(PREFERENCE_STRING_NAME))
        {
            SharedPreferences.Editor prefEditor = settingsPrv.edit();
            prefEditor.putBoolean("Boolean_Pref", false);
            prefEditor.putFloat("Float_Pref", java.lang.Float.NEGATIVE_INFINITY);
            prefEditor.putInt("Int_Pref", java.lang.Integer.MIN_VALUE);
            prefEditor.putString(PREFERENCE_STRING_NAME, this.getLocalClassName());
            prefEditor.apply();
        }
    }

    @Override
    protected Class<?> GetTargetClass() {
        return SecondSimplePreferencesActivity.class;
    }
}