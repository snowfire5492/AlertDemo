package schenck.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("schenck.sharedpreferences", Context.MODE_PRIVATE);

       // sharedPreferences.edit().putString("name", "Eric Schenck").apply();

//        String userName = sharedPreferences.getString("name", "defaultValue");

//        Log.i("User Name : " , userName);

        ArrayList<String> friends = new ArrayList<>();

        friends.add("Fido");
        friends.add("Jen");
        friends.add("Eric");


        try {
            sharedPreferences.edit().putString( "friends2", ObjectSerializer.serialize(friends)).apply();
            Log.i("friends2", ObjectSerializer.serialize(friends));
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayList<String> newFriends = new ArrayList<>();

        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends2", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("newFriends", newFriends.toString());

    }
}