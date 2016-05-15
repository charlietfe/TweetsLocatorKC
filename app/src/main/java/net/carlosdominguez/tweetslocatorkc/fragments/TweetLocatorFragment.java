package net.carlosdominguez.tweetslocatorkc.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import net.carlosdominguez.tweetslocatorkc.R;
import net.carlosdominguez.tweetslocatorkc.model.db.Tweet;
import net.carlosdominguez.tweetslocatorkc.services.external.TwitterService;
import net.carlosdominguez.tweetslocatorkc.utils.map.MapHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;


/**
 * Created by Carlos Domínguez on 14/05/16.
 */
public class TweetLocatorFragment extends SupportMapFragment {

    private GoogleApiClient googleApiClient;
    private GoogleMap map;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tweet_locator_fragment_menu, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new SearchTweetsAsyncTask(query, map).execute();
                searchView.setIconified(true);
                searchView.clearFocus();
                searchMenuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    public class SearchTweetsAsyncTask extends AsyncTask<Void, Void, List<Status>> {
        private final String TAG =  SearchTweetsAsyncTask.class.getSimpleName();

        private String locationName;
        private TwitterService service;
        private Twitter twitter;
        private Geocoder geoCoder;
        double selectedLat;
        double selectedLng;

        private GoogleMap map;


        public SearchTweetsAsyncTask(String locationName, GoogleMap map) {
            this.locationName = locationName;
            service = new TwitterService();
            twitter = service.getTwitter();
            geoCoder = new Geocoder(getContext(),Locale.getDefault());
            this.map = map;
            map.clear();
        }

        @Override
        protected List<twitter4j.Status> doInBackground(Void... params) {
            if (Geocoder.isPresent()) {
                try {
                    List<Address> addressList = geoCoder.getFromLocationName(locationName, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        if (address.hasLatitude() && address.hasLongitude()) {

                            selectedLat = address.getLatitude();
                            selectedLng = address.getLongitude();

                            Query query = new Query();
                            GeoLocation location = new GeoLocation(selectedLat, selectedLng);
                            query.geoCode(location, 100, Query.KILOMETERS.name());
                            query.setCount(20);

                            // TODO: Maybe It could be a good idea to iterate to get
                            // only tweets with geo

                            return twitter.search(query).getTweets();
                        }
                    }
                }
                catch (IOException e) {
                    Log.e(TAG, "Opps! Error searching for a location name");
                }
                catch (TwitterException e) {
                    Log.e(TAG, "Opps! Error on twitter query");
                }
            }
            else {
                Log.e(TAG, "Opps! Geocoder is not available");
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<twitter4j.Status> statuses) {
            super.onPostExecute(statuses);
            if (statuses == null) {
                Toast.makeText(getContext(), "Could not find any location", Toast.LENGTH_SHORT).show();
            }
            else {
                List<Tweet> tweets = Tweet.tweetsFromStatuses(statuses, selectedLat, selectedLng);
                MapHelper.centerMap(map, selectedLat, selectedLng);
                MapHelper.addGeoTweetsToMap(tweets, map, getContext());
            }
        }
    }
}
