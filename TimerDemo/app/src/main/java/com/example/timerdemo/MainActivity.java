package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    int seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {
                Log.i("remaining", String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Log.i("finished", "All Done!");
            }
        }.start();





//        final Handler handler = new Handler();
//
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//
//                Log.i("time", "time is " + seconds++ + " seconds");
//
//                handler.postDelayed(this, 1000);
//            }
//        };
//
//        handler.post(run);


    }
}