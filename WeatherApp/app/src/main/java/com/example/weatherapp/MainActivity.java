package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    DownloadTask task;
    TextView userInput;
    TextView outputView;
    String mainURL = "";
    String URLFirstPart = "https://api.openweathermap.org/data/2.5/weather?q=";
    String APIKey = "&appid=cc47c15ec15d5198af96bf6c5a4cfe4d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //task = new DownloadTask();
        userInput = findViewById(R.id.userInputView);
        outputView = findViewById(R.id.outputView);

    }

    public void checkWeather(View view) {
       // Log.i("CHECKING", "CHECKING");
        String input = userInput.getText().toString();

        mainURL = URLFirstPart + input.toLowerCase() + APIKey;

        Log.i("URL", mainURL);
        Log.i("USERINPUT", input);
        task = new DownloadTask();
        task.execute(mainURL);
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream input = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String output = "";

            try {
                JSONObject jsonObj = new JSONObject(s);
                String weatherInfo = jsonObj.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);

                //Log.i("ARRAY", arr.toString());
                //for(int i = 0 ; i < arr.length(); ++i) {

                JSONObject jsonPart = arr.getJSONObject(0);

                output += jsonPart.getString("main") + ": ";
                output += jsonPart.getString("description");

                outputView.setText(output);
//                    Log.i("Main", jsonPart.getString("main"));
//                    Log.i("descript", jsonPart.getString("description"));
                //}

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



}