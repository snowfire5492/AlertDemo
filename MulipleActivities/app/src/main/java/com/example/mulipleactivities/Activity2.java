package com.example.mulipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    public void goToNext(View view) {
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//        startActivity(intent);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Intent intent = getIntent();
        int age = intent.getIntExtra("age", 0);
        Toast.makeText(this, Integer.toString(age), Toast.LENGTH_SHORT).show();
    }
}