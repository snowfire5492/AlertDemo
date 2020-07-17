package com.example.listofplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {






    public void addANewPlace(View view) {
        Intent intent = new Intent(getApplicationContext(), AddAPlaceMapActivity.class);

        intent.putExtra("Latitude", 32.3);
        intent.putExtra("Longitude", -12.33);

        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        ArrayList<String> places = new ArrayList<>();

        places.add("Add a new place...");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, places);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Toast.makeText(MainActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), AddAPlaceMapActivity.class);
                intent.putExtra("placeNumber", i );
                startActivity(intent);

            }
        });

    }
}