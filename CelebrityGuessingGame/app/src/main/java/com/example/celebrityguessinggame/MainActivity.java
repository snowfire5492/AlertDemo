package com.example.celebrityguessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    public void checkGuess(View view) {
        Log.i("btn", "pressed");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        ImageDownloader task = new ImageDownloader();
        Bitmap image;

        getCelebrityInfo();

        try {
            image = task.execute("https://thumbor.forbes.com/thumbor/https%3A%2F%2Fspecials-" +
                    "images.forbesimg.com%2Fimageserve%2F5ed53e8fa40c3d0007ed25b3%2F874x416.jpg%3" +
                    "Fbackground%3D000000%26cropX1%3D243%26cropX2%3D2037%26cropY1%3D87%26cropY2%3D942&quot;);").get();
            imageView.setImageBitmap(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCelebrityInfo() {

    }



    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


}