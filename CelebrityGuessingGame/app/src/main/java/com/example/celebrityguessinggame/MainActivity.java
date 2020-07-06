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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Document document;
    Elements celebDetails;
    String doc;
    String mainURL = "https://www.imdb.com/list/ls052283250/";

    public void checkGuess(View view) {
        Log.i("btn", "pressed");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

//        ImageDownloader task = new ImageDownloader();
//        Bitmap image;

        getCelebrityInfo();

//        try {
//            image = task.execute("https://thumbor.forbes.com/thumbor/https%3A%2F%2Fspecials-" +
//                    "images.forbesimg.com%2Fimageserve%2F5ed53e8fa40c3d0007ed25b3%2F874x416.jpg%3" +
//                    "Fbackground%3D000000%26cropX1%3D243%26cropX2%3D2037%26cropY1%3D87%26cropY2%3D942&quot;);").get();
//            imageView.setImageBitmap(image);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void getCelebrityInfo() {

        DownloadData download = new DownloadData();
        download.execute(mainURL);



    }

    public class DownloadData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {

            try {
                document = Jsoup.connect(urls[0]).get();   // connect to the website
                Log.i("DOCUMENT", document.toString());

                Elements celebs = document.select("div.lister-item mode-detail");
                for(Element el: celebs){
                    Log.i("CELEB", el.toString());
                }

                if(celebs.size() < 1) {
                    Log.i("TOO SMALL", celebs.size() + " ");
                }

                //Log.i("document", document.toString());
                //Element grid = document.select("div.grid").first();
                //Log.i("grid", grid.toString());
                //Elements section = document.select("section.celeb-list");
               // Elements imgs = document.select("img[src$=.jpg]");
                //Log.i("IMAGES", imgs.toString());
                //if(section != null)
                //Log.i("section", section.toString());
//                //Get the logo source of the website
//                Element img = document.select("img").first();
//                // Locate the src attribute
//                String imgSrc = img.absUrl("src");
//                // Download image from URL
//                InputStream input = new java.net.URL(imgSrc).openStream();
//                // Decode Bitmap
//                bitmap = BitmapFactory.decodeStream(input);
//
//                //Get the title of the website
//                title = document.title();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

//    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
//
//        @Override
//        protected Bitmap doInBackground(String... urls) {
//            try {
//                URL url = new URL(urls[0]);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream in = connection.getInputStream();
//                Bitmap myBitmap = BitmapFactory.decodeStream(in);
//                return myBitmap;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//    }


}