package net.carlosdominguez.tweetslocatorkc.utils.map;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import twitter4j.Status;

/**
 * Created by Carlos Domínguez on 15/05/16.
 */
public class MapHelper {

    private static final int ZOOM_LEVEL = 8;

    public static void centerMap(final GoogleMap map, double latitude, double longitude) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(ZOOM_LEVEL).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    public static void addGeoTweetsToMap(List<Status> tweets, final GoogleMap googleMap, final Context context) {
        if (tweets == null || googleMap == null) {
            return;
        }

        for (final Status tweet : tweets) {

            if (tweet.getGeoLocation() == null) continue;

            final LatLng position = new LatLng(tweet.getGeoLocation().getLatitude(), tweet.getGeoLocation().getLongitude());
            final String profileImageUrl = tweet.getUser().getProfileImageURL();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    BitmapDescriptor bitmapDescriptor = null;

                    try {
                        if (profileImageUrl!= null) bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(Picasso.with(context).load(profileImageUrl).get());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final MarkerOptions marker = new MarkerOptions().position(position).title(tweet.getText()).icon(bitmapDescriptor);

                    (new Handler(Looper.getMainLooper())).post(new Runnable() {
                        @Override
                        public void run() {
                            googleMap.addMarker(marker);
                        }
                    });
                }
            }).start();
        }
    }

}
