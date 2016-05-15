package net.carlosdominguez.tweetslocatorkc.model.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import net.carlosdominguez.tweetslocatorkc.model.db.DBConstants;
import net.carlosdominguez.tweetslocatorkc.model.db.DBHelper;
import net.carlosdominguez.tweetslocatorkc.model.db.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Carlos Domínguez on 13/05/16.
 */
public class TweetDAO {

    private DBHelper db;

    private static final long INVALID_ID_DELETE_ALL_RECORDS = 0;

    public TweetDAO() {
        db = DBHelper.getInstance();
    }


    public void insertTweets(List<Tweet>tweets) {
        for (Tweet tweet: tweets) {
            insert(tweet);
        }
    }

    public long insert(Tweet tweet) {
        if (tweet == null) {
            throw new IllegalArgumentException("Passing NULL tweet, imbecile");
        }

        long id = db.getWritableDatabase().insert(DBConstants.TWEETS_TABLE, null, this.getContentValues(tweet));
        tweet.setId(id);

        return id;
    }

    public int update(Tweet tweet) {
        if (tweet == null) {
            throw new IllegalArgumentException("Passing NULL tweet, imbecile");
        }

        int numberOfRowsUpdated = db.getWritableDatabase().update(DBConstants.TWEETS_TABLE, this.getContentValues(tweet), DBConstants.KEY_COLUMN_ID + "=?", new String[]{"" + tweet.getId()});

        db.close();
        return numberOfRowsUpdated;
    }

    public void delete(long id) {
        if (id == INVALID_ID_DELETE_ALL_RECORDS) {
            db.getWritableDatabase().delete(DBConstants.TWEETS_TABLE,  null, null);
        } else {
            db.getWritableDatabase().delete(DBConstants.TWEETS_TABLE, DBConstants.KEY_COLUMN_ID + "=?", new String[]{"" + id});
        }
        db.close();
    }

    public void deleteAll() {
        delete(INVALID_ID_DELETE_ALL_RECORDS);
    }

    public  ContentValues getContentValues(Tweet tweet) {
        ContentValues content = new ContentValues();
        content.put(DBConstants.KEY_COLUMN_TEXT, tweet.getText());
        content.put(DBConstants.KEY_COLUMN_USERNAME, tweet.getUsername());
        content.put(DBConstants.KEY_COLUMN_LAT, tweet.getLat());
        content.put(DBConstants.KEY_COLUMN_LNG, tweet.getLng());
        content.put(DBConstants.KEY_COLUMN_PROFILE_IMAGE_URL, tweet.getProfileImageURL());
        content.put(DBConstants.KEY_COLUMN_PUBLICATION_DATE, dateToLong(tweet.getPublicationDate()));

        return content;
    }


    // convenience method

    public static Tweet TweetFromCursor(Cursor c) {
        assert c != null;

        String username = c.getString(c.getColumnIndex(DBConstants.KEY_COLUMN_USERNAME));
        String text = c.getString(c.getColumnIndex(DBConstants.KEY_COLUMN_TEXT));

        long id = c.getLong(c.getColumnIndex(DBConstants.KEY_COLUMN_ID));

        Tweet tweet = new Tweet(username, text);
        tweet.setId(id);
        tweet.setLng(c.getDouble(c.getColumnIndex(DBConstants.KEY_COLUMN_LNG)));
        tweet.setLat(c.getDouble(c.getColumnIndex(DBConstants.KEY_COLUMN_LAT)));
        tweet.setProfileImageURL(c.getString(c.getColumnIndex(DBConstants.KEY_COLUMN_PROFILE_IMAGE_URL)));
        tweet.setPublicationDate(longToDate(c.getLong(c.getColumnIndex(DBConstants.KEY_COLUMN_PUBLICATION_DATE))));

        return tweet;
    }


    /**
     * Returns all Tweets in DB inside a Cursor
     * @return cursor with all records
     */
    public Cursor queryCursor() {
        Cursor c = db.getReadableDatabase().query(DBConstants.TWEETS_TABLE, DBConstants.TWEETS_ALL_COLUMNS, null, null, null, null, null);
     //   db.close();
        return c;
    }

    public List<Tweet> query() {
        List<Tweet> Tweets = new ArrayList<>();

        Cursor cursor = queryCursor();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Tweet Tweet = TweetFromCursor(cursor);
                Tweets.add(Tweet);
            } while (cursor.moveToNext());
        }
        return Tweets;
    }


    /**
     * Returns a Tweet object from its id
     * @param id - the Tweet id in db
     * @return Tweet object if found, null otherwise
     */
    public Tweet query(long id) {
        Tweet Tweet = null;

        String where = DBConstants.KEY_COLUMN_ID + "=" + id;
        Cursor c = db.getReadableDatabase().query(DBConstants.TWEETS_TABLE, DBConstants.TWEETS_ALL_COLUMNS, where, null, null, null, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                Tweet = TweetFromCursor(c);
            }
        }
        c.close();
        return Tweet;
    }


    public static long dateToLong (Date date) {
        return date.getTime();
    }


    public static Date longToDate (long longDate) {
        return new Date(longDate);
    }



}
