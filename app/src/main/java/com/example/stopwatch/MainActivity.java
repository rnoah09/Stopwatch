package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private Button buttonLap;
    private ScrollView lapList;
    private ToggleButton buttonStartStop;
    private Boolean startStop;
    private Button buttonReset;
    private Chronometer chronometer;
    private TextView textViewLap1;
    private TextView textViewLap2;
    private TextView textViewLap3;
    private long lapHolder;
    private String laps;
    private int lapNumber;
    private long stopTime;
    private String lapDouble;
    private String textView1PlaceHolder;
    private String textView2PlaceHolder;
    private String textView3PlaceHolder;
    private long chronometerPlaceHolder;
    private long chronometerStopTimePlaceHolder;
    private int lapNumberPlaceHolder;
    private long lapHolderPlaceHolder;
    private boolean firstTime;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String START_STOP_KEY = "StartStopKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        wireWidgets();
        setListeners();
        lapNumber = 1;
        stopTime = SystemClock.elapsedRealtime();
        buttonLap.setEnabled(false);
        textViewLap1.setText("");
        if (savedInstanceState != null){
            startStop = savedInstanceState.getBoolean("START_STOP_KEY");
            buttonStartStop.setChecked(startStop);
            textView1PlaceHolder = savedInstanceState.getString("textView1PlaceHolder");
            textView2PlaceHolder = savedInstanceState.getString("textView2PlaceHolder");
            textView3PlaceHolder = savedInstanceState.getString("textView3PlaceHolder");
            chronometerPlaceHolder = savedInstanceState.getLong("chronometerPlaceHolder");
            lapNumber = savedInstanceState.getInt("lapNumberPlaceHolder");
            lapHolder = savedInstanceState.getLong("lapHolderPlaceHolder");
            chronometerStopTimePlaceHolder = savedInstanceState.getLong("chronometerStopTimePlaceHolder");
            chronometer.setBase(chronometerPlaceHolder + (SystemClock.elapsedRealtime() - chronometerStopTimePlaceHolder));
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if(lapList.getVisibility() == View.GONE){
                lapList.setVisibility(View.VISIBLE);
                buttonLap.setVisibility(View.VISIBLE);
                textViewLap1.setVisibility(View.VISIBLE);
                textViewLap2.setVisibility(View.VISIBLE);
                textViewLap3.setVisibility(View.VISIBLE);
            }
            textViewLap1.setText(textView1PlaceHolder);
            textViewLap2.setText(textView2PlaceHolder);
            textViewLap3.setText(textView3PlaceHolder);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("textView1PlaceHolder", textView1PlaceHolder);
        savedInstanceState.putString("textView2PlaceHolder", textView2PlaceHolder);
        savedInstanceState.putString("textView3PlaceHolder", textView3PlaceHolder);
        savedInstanceState.putLong("chronometerPlaceHolder", chronometerPlaceHolder);
        if (buttonStartStop.isChecked() == true){
            chronometerStopTimePlaceHolder = SystemClock.elapsedRealtime();
        }
        savedInstanceState.putLong("chronometerStopTimePlaceHolder", chronometerStopTimePlaceHolder);
        lapNumberPlaceHolder = lapNumber;
        savedInstanceState.putInt("lapNumberPlaceHolder", lapNumberPlaceHolder);
        lapHolderPlaceHolder = lapHolder;
        savedInstanceState.putLong("lapHolderPlaceHolder", lapHolderPlaceHolder);
        savedInstanceState.putBoolean(START_STOP_KEY, startStop);

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
        lapList = findViewById(R.id.scrollView_laps_main);

    }

    private void setListeners() {
        buttonStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    if (stopTime != 0){
                        chronometer.setBase(chronometer.getBase() + (SystemClock.elapsedRealtime() - stopTime));
                        chronometerPlaceHolder = chronometer.getBase();
                    }
                    if (firstTime == true){
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    }
                    chronometer.start();
                    firstTime = false;
                    buttonLap.setEnabled(true);
                    startStop = true;
                }
                else {
                    chronometer.stop();
                    buttonLap.setEnabled(false);
                    stopTime = SystemClock.elapsedRealtime();
                    chronometerStopTimePlaceHolder = stopTime;
                    chronometerPlaceHolder = chronometer.getBase();
                    startStop = false;
                    firstTime = false;
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopTime = chronometer.getBase();
                buttonStartStop.setChecked(false);
                textViewLap1.setText("");
                textViewLap2.setText("");
                textViewLap3.setText("");
                lapNumber = 1;
                textView1PlaceHolder = "";
                textView2PlaceHolder = "";
                textView3PlaceHolder = "";
                chronometerStopTimePlaceHolder = stopTime;
                lapHolder = SystemClock.elapsedRealtime();
                lapHolderPlaceHolder = lapHolder;

            }
        });

        buttonLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laps = chronometer.getText().toString();
                if (textViewLap1.getText().toString().equals("")){

                    textViewLap1.setText("" + lapNumber);
                    textViewLap2.setText("" + laps);
                    textViewLap3.setText("");
                    lapNumber++;
                    lapHolder = SystemClock.elapsedRealtime();

                    textView1PlaceHolder = textViewLap1.getText().toString();
                    textView2PlaceHolder = textViewLap2.getText().toString();
                    textView3PlaceHolder = textViewLap3.getText().toString();

                }
                else if (textViewLap3.getText().toString().equals("")){
                    textViewLap1.setText(textViewLap1.getText() + "\n" + lapNumber);
                    textViewLap2.setText(textViewLap2.getText() + "\n" + laps);
                    lapDouble = String.format("%.2f", (SystemClock.elapsedRealtime() - lapHolder)/1000.0);
                    textViewLap3.setText(textViewLap3.getText() + "\n" + lapDouble + "s");
                    lapNumber++;
                    lapHolder = SystemClock.elapsedRealtime();

                    textView1PlaceHolder = textViewLap1.getText().toString();
                    textView2PlaceHolder = textViewLap2.getText().toString();
                    textView3PlaceHolder = textViewLap3.getText().toString();

                }
                else{
                    textViewLap1.setText(textViewLap1.getText() + "\n" + lapNumber);
                    textViewLap2.setText(textViewLap2.getText() + "\n" + laps);
                    lapDouble = String.format("%.2f", (SystemClock.elapsedRealtime() - lapHolder)/1000.0);
                    textViewLap3.setText(textViewLap3.getText() + "\n" + lapDouble + "s");
                    lapNumber++;
                    lapHolder = SystemClock.elapsedRealtime();

                    textView1PlaceHolder = textViewLap1.getText().toString();
                    textView2PlaceHolder = textViewLap2.getText().toString();
                    textView3PlaceHolder = textViewLap3.getText().toString();

                }
            }
        });

    }

}
