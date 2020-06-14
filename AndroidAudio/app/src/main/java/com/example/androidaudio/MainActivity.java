package com.example.androidaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = MediaPlayer.create(this, R.raw.alien_sound);
    }

    public void playMedia(View view) {

        mPlayer.start();
    }

    public void pauseMedia(View view) {

        mPlayer.pause();
    }
}