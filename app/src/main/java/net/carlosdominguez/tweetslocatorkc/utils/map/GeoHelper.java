package net.carlosdominguez.tweetslocatorkc.utils.map;

import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 * Created by Carlos Domínguez on 15/05/16.
 */
public class GeoHelper {

    public static class GeoLocation {
        private double lat;
        private double lng;

        public GeoLocation(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }



    }


    public static GeoLocation generateRandomPoint(double lat, double lng, double radius) {
        double x0 = lng;
        double y0 = lat;
        // Convert Radius from meters to degrees.
        double rd = (radius*1000)/111300;

        double u = Math.random();
        double v = Math.random();

        double w = rd * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        double xp = x/Math.cos(lat);

        // Resulting point.
        return new GeoLocation(y+y0,xp + x0);
    }
}
