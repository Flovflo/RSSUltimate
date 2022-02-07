package com.example.rssultimate;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.rssultimate.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        binding.fab.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {
                Downloader downloader= new Downloader();
                downloader.start();

                Snackbar.make(view, "ca a refresh BROOOO", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    class Downloader extends Thread{
        @Override
        public void run() {
            String url = "https://www.lemonde.fr/rss/une.xml";
            ArrayList<RSSItem> news = new ArrayList<>();
            try {
                InputStream stream = new URL(url).openConnection().getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                int eventType = parser.getEventType();
                boolean done = false;
                RSSItem item = null;
                while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                    String name = null;
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase("item")) {
                                item = new RSSItem();
                            } else if (item != null) {
                                if (name.equalsIgnoreCase("link")) {
                                    item.link = parser.nextText();
                                } else if (name.equalsIgnoreCase("description")) {
                                    item.description = parser.nextText().trim();
                                } else if (name.equalsIgnoreCase("pubDate")) {
                                    item.pubDate = parser.nextText();
                                } else if (name.equalsIgnoreCase("title")) {
                                    item.title = parser.nextText().trim();
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            name = parser.getName();
                            if (name.equalsIgnoreCase("item") && item != null) {
                                news.add(item);
                            } else if (name.equalsIgnoreCase("channel")) {
                                done = true;
                            }
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
            runOnUiThread(() ->{
            ArrayAdapter<RSSItem> itemsAdapter =
                    new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, news);
            ListView listView = findViewById(R.id.list_news);
            listView.setAdapter(itemsAdapter);

        });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent( this, Settings.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_about) {
            //lancer about activity
            Intent intent = new Intent( this, AboutActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}