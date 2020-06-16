package com.example.timestablesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.R.layout;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView timesTableList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timesTableList = findViewById(R.id.listView);
        SeekBar operandSelector = findViewById(R.id.seekBar);

        List<Integer> results = new ArrayList<>();
        for (int i = 1; i <= 20; ++i) {
            results.add(i);
        }
        changeList(results);


        operandSelector.setMax(13);
        operandSelector.setMin(1);

        operandSelector.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Log.i("SeekBar value", Integer.toString(i));


                List<Integer> results = new ArrayList<>();
                for (int j = 1; j <= 20; ++j) {
                    results.add(j*i);
                    //Log.i("timesTables" , Integer.toString(i*j));
                }
                changeList(results);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeList(List<Integer> input){
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, layout.simple_list_item_1, input);

        timesTableList.setAdapter(arrayAdapter);


    }
}