package net.carlosdominguez.tweetslocatorkc.activities;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.carlosdominguez.tweetslocatorkc.R;
import net.carlosdominguez.tweetslocatorkc.services.external.TwitterListenerHelper;
import net.carlosdominguez.tweetslocatorkc.services.external.TwitterService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import twitter4j.AsyncTwitter;
import twitter4j.GeoLocation;
import twitter4j.Query;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
