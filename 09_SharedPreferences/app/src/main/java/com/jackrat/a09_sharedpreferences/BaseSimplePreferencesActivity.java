package com.jackrat.a09_sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public abstract class BaseSimplePreferencesActivity extends AppCompatActivity {


    public static final String PREFERENCE_FILENAME = "AppPrefs";
    public static final String PREFERENCE_STRING_NAME = "StringPrefActivity";

    private TextView tvTitle;
    private EditText etPrefName;
    private EditText etPrefValue;
    private TextView tvActivityPrefs;
    private TextView tvAppPrefs;
    private Button goButton;
    private Button btnAddActivityPref;
    private Button btnAddSharedPref;
    private Button btnClearAct;
    private Button btnClearShared;
    private Button btnClearActPrefByName;
    private Button btnClearSharedPrefByName;

    protected SharedPreferences settingsApp;
    protected SharedPreferences settingsPrv;


    @Override
    protected void onStart() {
        super.onStart();
        refreshUI(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        tvTitle = findViewById(R.id.tvTitle);
        etPrefName = findViewById(R.id.etPrefName);
        etPrefValue = findViewById(R.id.etPrefValue);
        tvActivityPrefs = findViewById(R.id.tvActivityPrefs);
        tvAppPrefs = findViewById(R.id.tvAppPrefs);
        goButton = findViewById(R.id.btnGo);
        btnAddActivityPref = findViewById(R.id.btnAddActivityPref);
        btnAddSharedPref = (Button) findViewById(R.id.btnAddSharedPref);
        btnClearAct = (Button) findViewById(R.id.btnClearAct);
        btnClearShared = (Button) findViewById(R.id.btnClearShared);
        btnClearActPrefByName = (Button) findViewById(R.id.btnClearActPrefByName);
        btnClearSharedPrefByName = (Button) findViewById(R.id.btnClearSharedPrefByName);


        // Przejście do innej aktywności.
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(BaseSimplePreferencesActivity.this, GetTargetClass());
                startActivity(intent);
            }
        });


        // Rozdzielenie bieżących preferencji.
        settingsApp = BaseSimplePreferencesActivity.this.getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
        settingsPrv = getPreferences(MODE_PRIVATE);


        // Dodaj/zmień preferencję prywatną aktywności
        btnAddActivityPref.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Pobranie ustawień z UI.
                String strPrefName = etPrefName.getText().toString();
                String strPrefValue = etPrefValue.getText().toString();
                addModify(settingsPrv, strPrefName, strPrefValue, BaseSimplePreferencesActivity.this);
            }
        });


        // dodaj/zmień preferencję wspólną
        btnAddSharedPref.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strPrefName = etPrefName.getText().toString();
                String strPrefValue = etPrefValue.getText().toString();
                addModify(settingsApp, strPrefName, strPrefValue, BaseSimplePreferencesActivity.this);
            }
        });


        // Usuń wszystkie preferencje prywatne
        btnClearAct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeAll(settingsPrv, "prywatne", BaseSimplePreferencesActivity.this);
            }
        });


        // Usuń wszystkie preferencje wspólne
        btnClearShared.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeAll(settingsApp, "wspólne", BaseSimplePreferencesActivity.this);
            }
        });


        // usunięcie preferencji prywatnej na podstawie nazwy.
        btnClearActPrefByName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Pobranie nazwy preferencji z UI
                String strPrefName = etPrefName.getText().toString();
                removeByName(settingsPrv, strPrefName, BaseSimplePreferencesActivity.this);
            }
        });


        // usunięcie preferencji wspólnej na podstawie nazwy.
        btnClearSharedPrefByName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Pobranie nazwy preferencji z UI
                String strPrefName = etPrefName.getText().toString();
                removeByName(settingsApp, strPrefName, BaseSimplePreferencesActivity.this);
            }
        });


        // Rejestrujemy odbiorcę zdarzeń, by wiedzieć kiedy właściwości zostaną zmodyfikowane.
        settingsPrv.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (prefs == BaseSimplePreferencesActivity.this.settingsApp)
                    onSharedPreferenceAppChanged(prefs, key);
                else if (prefs == BaseSimplePreferencesActivity.this.settingsPrv)
                    onSharedPreferencePrvChanged(prefs, key);
            }
        });
    }


    static void addModify(SharedPreferences prefs, String name, String value, BaseSimplePreferencesActivity instance) {
        // Otworzenie edytora, dodanie/modyfikacja preferencji i zapis zmian.
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        editor.apply();
        // Aktualizacja UI
        refreshUI(instance);
    }

    static void removeAll(SharedPreferences prefs, String sJakie, BaseSimplePreferencesActivity instance) {
        //otworzenie edytora i  usunięcie wszystkich właściwości wspólnych
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        // Aktualizacja interfejsu użytkownika.
        refreshUI(instance);
        // Wyświetlenie użytkownikowi informacji
        Toast.makeText(instance, "Usunięto wszystkie właściwości " + sJakie, Toast.LENGTH_SHORT).show();
    }

    static void removeByName(SharedPreferences prefs, String name, BaseSimplePreferencesActivity instance) {
        // Otworzenie edytora, usunięcie preferencji jeśli istnieje.
        if (prefs.contains(name)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(name);
            editor.apply();
        }
        // Aktualizacja UI
        refreshUI(instance);
    }

    protected static void refreshUI(BaseSimplePreferencesActivity t) {
        // Wyświetlenie nazwy aktywności.
        t.tvTitle.setText(t.getLocalClassName());
        // Wyświetlenie wspólnych właściwości.
        t.tvAppPrefs.setText(t.settingsApp.getAll().toString());
        // Ustawienie prywatnych właściwości aktywności.
        t.tvActivityPrefs.setText(t.settingsPrv.getAll().toString());
    }

    protected abstract Class<?> GetTargetClass();

    public void onSharedPreferenceAppChanged(SharedPreferences sharedPreferences, String key) {
        // Informujemy użytkownika o tym co się zmieniło.
        Toast.makeText(BaseSimplePreferencesActivity.this, "Zmieniono preferencję: " + key, Toast.LENGTH_SHORT).show();
    }

    public void onSharedPreferencePrvChanged(SharedPreferences sharedPreferences, String key) {
        // Informujemy użytkownika o tym co się zmieniło.
        Toast.makeText(BaseSimplePreferencesActivity.this, "Zmieniono preferencję: " + key, Toast.LENGTH_SHORT).show();
    }


    void a() {

        SharedPreferences prefs = getSharedPreferences("ApplicationPreferences", 0);
        //...
        Map<String, ?> map = prefs.getAll();
        SharedPreferences.Editor editor = prefs.edit();
        for (String key : map.keySet()) {
           editor.r
        }
        editor.apply();


    }

}
