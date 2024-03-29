package com.jackrat.a10_c_hardware;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SensorsActivity extends AppCompatActivity {

    TextView tvStatus;
    RadioGroup radioGroup;
    Button btnStart;
    Button btnStop;

    private static final String DEBUG_TAG = "SensorsActivity";
    private SensorManager sensorManager = null;
    private SensorEventListener sensorEventListener = null;

    private static final Map<Integer, Integer> buttonSensorMap = new HashMap<Integer, Integer>() {
        {
            put(R.id.sensor_accel, Sensor.TYPE_ACCELEROMETER);
            put(R.id.sensor_light, Sensor.TYPE_LIGHT);
            put(R.id.sensor_mag, Sensor.TYPE_MAGNETIC_FIELD);
            put(R.id.sensor_orient, Sensor.TYPE_ORIENTATION);
            put(R.id.sensor_prox, Sensor.TYPE_PROXIMITY);
            put(R.id.sensor_temp, Sensor.TYPE_TEMPERATURE);
            put(R.id.sensor_pressure, Sensor.TYPE_PRESSURE);
            put(R.id.sensor_gyro, Sensor.TYPE_GYROSCOPE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        tvStatus = findViewById(R.id.status);
        radioGroup = findViewById(R.id.sensor_group);
        btnStart = findViewById(R.id.start_sensor);
        btnStop = findViewById(R.id.stop_sensor);

        setTitle("Sensory");
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v(DEBUG_TAG, "onCheckedChanged");
                handleStartSensor(tvStatus, btnStart, btnStop, checkedId);
            }

        });

        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.v(DEBUG_TAG, "onClickListener");
                int checkedId = radioGroup.getCheckedRadioButtonId();
                handleStartSensor(tvStatus, btnStart, btnStop, checkedId);
            }

        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sensorManager.unregisterListener(sensorEventListener);
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.GONE);
            }

        });

    }


    public void onPause() {
        if (sensorManager != null && sensorEventListener != null) {
            sensorManager.unregisterListener(sensorEventListener);
            sensorEventListener = null;
        }
        super.onPause();
    }





    private void handleStartSensor(TextView status, Button start, Button stop, int checkedId) {
        if (btnStop.getVisibility() != View.GONE) btnStop.callOnClick();
        Sensor defaultSensor = sensorManager.getDefaultSensor(buttonSensorMap.get(checkedId));
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                StringBuilder sb = new StringBuilder("Nowe wartości z czujnika  ");
                sb.append(event.sensor.getName());
                sb.append(" ");
                for (float value : event.values) {
                    sb.append("[");
                    sb.append(value);
                    sb.append("]");
                }
                sb.append(" odczytane z dokładnością ");
                sb.append(event.accuracy);
                sb.append(" o godzinie ");
                sb.append(event.timestamp);
                sb.append(" (znacznik czasu) ");
                sb.append(".");
                status.setText(sb);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                String sensorName = sensor.getName();
                status.setText("Czujnik: " + sensorName + " zmiana dokładności: " + accuracy);
            }
        };

        boolean isAvailable = sensorManager.registerListener(sensorEventListener, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (!isAvailable) {
            RadioButton checked = findViewById(checkedId);
            status.setText("Wybrany czujnik (" + checked.getText() + ") nie jest dostępny.");
        } else{

            stop.setVisibility(View.VISIBLE);
            start.setVisibility(View.GONE);
        }
    }


}