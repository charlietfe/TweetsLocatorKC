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

    TwitterService service;
    AsyncTwitter twitter;
    TwitterListenerHelper twitterListenerHelper;
    Geocoder geoCoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twitterListenerHelper = new TwitterListenerHelper();
        service = new TwitterService();
        twitter = service.getAsyncTwitter();
        twitter.addListener(twitterListenerHelper.getListener());
        geoCoder = new Geocoder(this, Locale.getDefault());
    }


    private void searchForLocation(String locationName) {
        if (Geocoder.isPresent()) {
            try {
                List<Address> addressList = geoCoder.getFromLocationName(locationName, 1);
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);
                    if (address.hasLatitude() && address.hasLongitude()) {
                        double selectedLat = address.getLatitude();
                        double selectedLng = address.getLongitude();
                        GeoLocation geo = new GeoLocation(selectedLat, selectedLng);
                        Query query = new Query();
                        query.geoCode(geo, 5, "km");
                        twitter.search(query);
                    }
                }
            }
            catch (IOException e) {
                Toast.makeText(this, "Opps! Error searching for a location name", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Opps! Geocoder is not available", Toast.LENGTH_SHORT).show();
        }
    }
}
