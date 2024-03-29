package com.jackrat.a10_c_hardware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class BatteryActivity extends AppCompatActivity {
    private boolean receiverRegistered = false;
    private static final String DEBUG_TAG = "BatteryActivity";

    Button start;
    Button stop;
    TextView status;
    ImageView icon;

    private static final Map<Integer, String> healthValueMap =
            new HashMap<Integer, String>() {
                {
                    put(BatteryManager.BATTERY_HEALTH_DEAD, "Martwa");
                    put(BatteryManager.BATTERY_HEALTH_GOOD, "Dobry");
                    put(BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE, "Zbyt duże napięcie");
                    put(BatteryManager.BATTERY_HEALTH_OVERHEAT, "Przegrzana");
                    put(BatteryManager.BATTERY_HEALTH_UNKNOWN, "Nieznany");
                    put(BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE,
                            "Błąd, stan nieznany");
                    put(-1, "Nie określony");
                }
            };
    private static final Map<Integer, String> statusValueMap =
            new HashMap<Integer, String>() {
                {
                    put(BatteryManager.BATTERY_STATUS_CHARGING, "Ładowanie");
                    put(BatteryManager.BATTERY_STATUS_DISCHARGING, "Rozładowywanie");
                    put(BatteryManager.BATTERY_STATUS_FULL, "Naładowana");
                    put(BatteryManager.BATTERY_STATUS_NOT_CHARGING, "Brak ładowania");
                    put(BatteryManager.BATTERY_STATUS_UNKNOWN, "Znieznany");
                    put(-1, "Nie określony");
                }
            };
    private static final Map<Integer, String> pluggedValueMap =
            new HashMap<Integer, String>() {
                {
                    put(BatteryManager.BATTERY_PLUGGED_AC, "sieć elektryczna");
                    put(BatteryManager.BATTERY_PLUGGED_USB, "USB");
                    put(-1, "Nie określony");
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bateria");
        setContentView(R.layout.activity_battery);
        status = (TextView) findViewById(R.id.status);
        icon = (ImageView) findViewById(R.id.icon);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    registerReceiver(batteryRcv, new IntentFilter(
                            Intent.ACTION_BATTERY_CHANGED));
                    Toast.makeText(BatteryActivity.this,
                            "Monitorowanie stanu baterii rozpoczęte", Toast.LENGTH_SHORT).show();
                    receiverRegistered = true;
                } catch (Exception e) {
                    Log.e(DEBUG_TAG, "Błąd rejestracji odbiorcy zdarzeń");
                }
                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (receiverRegistered) {
                    try {
                        unregisterReceiver(batteryRcv);
                    } catch (Exception e) {
                        Log
                                .e(
                                        DEBUG_TAG,
                                        "Błąd wyrejestrowywania odbiorcy zdarzeń. Czy naprawdę został zarejestrowany?",
                                        e);
                    }
                    receiverRegistered = false;
                }
                Toast.makeText(BatteryActivity.this,
                        "Zakończono monitorowanie stanu baterii", Toast.LENGTH_SHORT).show();
                stop.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {
        if (receiverRegistered) {
            try {
                unregisterReceiver(batteryRcv);
            } catch (Exception e) {
                Log
                        .e(
                                DEBUG_TAG,
                                "Błąd wyrejestrowywania odbiorcy zdarzeń. Czy naprawdę został zarejestrowany",
                                e);
            }
            receiverRegistered = false;
        }
        super.onPause();
    }

    private final BroadcastReceiver batteryRcv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                int level =
                        intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int maxValue =
                        intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryStatus =
                        intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int batteryHealth =
                        intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                int batteryPlugged =
                        intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                String batteryTech =
                        intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                int batteryIcon =
                        intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, -1);
                float batteryVoltage =
                        (float) intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,
                                -1) / 1000;
                boolean battery =
                        intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT,
                                false);
                float batteryTemp =
                        (float) intent.getIntExtra(
                                BatteryManager.EXTRA_TEMPERATURE, -1) / 10;
                /*
                 * Poniższy kod służy do określania kluczy i typów
                 * Bundle extras = intent.getExtras(); Set<String> keys =
                 * extras.keySet(); Iterator<String> allKeys =
                 * keys.iterator();
                 * while (allKeys.hasNext()) { String key = allKeys.next();
                 * Log.v("Bateria", key); }
                 */
                int chargedPct = (level * 100) / maxValue;
                String batteryInfo =
                        "Informacje o stanie baterii:\nStan ogólny = "
                                + healthValueMap.get(batteryHealth) + "\n"
                                + "Status = " + statusValueMap.get(batteryStatus)
                                + "\n" + "Naładowana w "
                                + chargedPct + "%\n" + "Podłączono do sieci = "
                                + pluggedValueMap.get(batteryPlugged) + "\n"
                                + "Typ = " + batteryTech + "\n" + "Napięcie = "
                                + batteryVoltage
                                + " volts\n" + "Temperatura = " + batteryTemp
                                + "°C\n" + "Bateria zamontowana = " + battery + "\n";

                status.setText(batteryInfo);
                icon.setImageResource(batteryIcon);
                Toast.makeText(BatteryActivity.this, "Stan baterii uległ zmianie",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "Błąd odczytu informacji o stanie baterii: ", e);
            }
        }
    };
}