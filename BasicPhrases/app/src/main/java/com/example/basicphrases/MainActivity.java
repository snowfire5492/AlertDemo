package com.example.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void speak(View view) {

        Button pressedButton = (Button) view;

        Log.i("pressed", pressedButton.getTag().toString());
        
        mediaPlayer = MediaPlayer.create(this,
                getResources().getIdentifier(pressedButton.getTag().toString(), "raw", getPackageName()));
        mediaPlayer.start();

    }
}