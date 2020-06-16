package com.example.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer alarmAudio;
    CountDownTimer eggTimer;
    SeekBar timeControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeControl = findViewById(R.id.seekBar);
        alarmAudio = MediaPlayer.create(this, R.raw.alarm);

        setTimer(30);
        timeControl.setMax(600);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            timeControl.setMin(1);
        }
        timeControl.setProgress(30);
        timeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int i, boolean b) {
                Log.i("value", String.valueOf(i));

                setTimer(i);
                // update timer view in minutes and seconds
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setTimer(final int seconds) {
        eggTimer = new CountDownTimer(seconds * 1000, 1000) {

            int temp = seconds;
            @Override
            public void onTick(long l) {
                // !!!! update the timer view - 1 second

                 Log.i("eggTimer", String.valueOf(temp--))   ;
            }

            @Override
            public void onFinish() {
                alarmAudio.start();
            }
        };
    }



    public void start(View view) {
        eggTimer.start();
        timeControl.setEnabled(false);
        Button btn = (Button) view;
        btn.setText("Stop");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
       // btn.setOnContextClickListener(stop));
    }


    public void stop() {

        eggTimer.cancel();

        Button btn = (Button) findViewById(R.id.button);
        btn.setText("Play");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(view);
            }
        });

        timeControl.setProgress(30);
        timeControl.setEnabled(true);

        setTimer(30);
    }
}