package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private Button buttonLap;
    private ToggleButton buttonStartStop;
    private Button buttonReset;
    private Chronometer chronometer;
    private TextView textViewLap1;
    private TextView textViewLap2;
    private TextView textViewLap3;
    private long lapHolder;
    private String lapDifferences;
    private String laps;
    private int lapNumber;
    private long stopTime;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        wireWidgets();
        setListeners();
        lapNumber = 1;
        stopTime = 0;
        buttonLap.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


    private void wireWidgets() {
        buttonLap = findViewById(R.id.button_lap_main);
        buttonReset = findViewById(R.id.button_reset_main);
        buttonStartStop = findViewById(R.id.togglebutton_startstop_main);
        chronometer = findViewById(R.id.textview_clockdisplay_main);
        textViewLap1 = findViewById(R.id.textview_lap1_main);
        textViewLap2 = findViewById(R.id.textview_lap2_main);
        textViewLap3 = findViewById(R.id.textview_lap3_main);

    }

    private void setListeners() {
        buttonStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    if (stopTime != 0){
                        chronometer.setBase(chronometer.getBase() + (SystemClock.elapsedRealtime() - stopTime));
                    }
                    chronometer.start();
                    buttonLap.setEnabled(true);
                }
                else {
                    chronometer.stop();
                    buttonLap.setEnabled(false);
                    stopTime = SystemClock.elapsedRealtime();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopTime = chronometer.getBase();
            }
        });

        buttonLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laps = chronometer.getText().toString();
                if (textViewLap1.getVisibility() == view.GONE){
                    textViewLap1.setVisibility(View.VISIBLE);
                    textViewLap2.setVisibility(View.VISIBLE);
                    textViewLap3.setVisibility(View.VISIBLE);

                    textViewLap1.setText("" + lapNumber);
                    textViewLap2.setText("" + laps);
                    textViewLap3.setText("");
                    lapNumber++;
                    lapHolder = SystemClock.elapsedRealtime();
                }
                else if (textViewLap3.getText().toString().equals("")){
                    textViewLap1.setText(textViewLap1 + "" + lapNumber);
                    textViewLap2.setText(textViewLap2 + "" + laps);
                    textViewLap3.setText(textViewLap3 + "" + SystemClock.elapsedRealtime());
                    lapNumber++;
                    lapHolder = SystemClock.elapsedRealtime();
                }
                else{
                    textViewLap1.setText(textViewLap1.getText() + "\n" + lapNumber);
                    textViewLap2.setText(textViewLap2.getText() + "\n" + laps);
                    textViewLap3.setText(textViewLap3.getText() + "\n" + (SystemClock.elapsedRealtime() - lapHolder));
                    lapNumber++;
                    lapHolder = SystemClock.elapsedRealtime();

                }
            }
        });

    }

}
