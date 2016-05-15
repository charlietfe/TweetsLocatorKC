package net.carlosdominguez.tweetslocatorkc.model.db;

import com.google.android.maps.GeoPoint;

import net.carlosdominguez.tweetslocatorkc.utils.map.GeoHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Status;


/**
 * Created by Carlos on 13/05/16.
 */
public class Tweet {
    private long id;
    private String username;
    private String text;
    private String profileImageURL;
    private Date publicationDate;
    private double lat;
    private double lng;


    public Tweet() {
    }

    public Tweet(String username, String text) {
        this.username = username;
        this.text = text;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
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


    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }


    public static List<Tweet> tweetsFromStatuses(List<Status> statuses, double lat, double lng) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (Status status: statuses) {
            tweets.add(tweetFromStatus(status, lat, lng));
        }
        return tweets;
    }

    public static Tweet tweetFromStatus(Status status, double lat, double lng) {
        Tweet tweet = new Tweet();
        tweet.setText(status.getText());
        tweet.setUsername(status.getUser().getName());
        if (status.getGeoLocation() != null) {
            tweet.setLat(status.getGeoLocation().getLatitude());
            tweet.setLng(status.getGeoLocation().getLongitude());
        }
        else {
            // TODO: We should remove this fallback in order to get only
            // tweets with geo.
            
            GeoHelper.GeoLocation p = GeoHelper.generateRandomPoint(lat,lng, 10);
            tweet.setLat(p.getLat());
            tweet.setLng(p.getLng());
        }
        tweet.setProfileImageURL(status.getUser().getProfileImageURL());
        tweet.setPublicationDate(status.getCreatedAt());
        return tweet;
    }





}
