package net.carlosdominguez.tweetslocatorkc.model.db;

/**
 * Created by Carlos on 13/05/16.
 */
public class DBConstants {
    public static final int DB_VERSION = 2;

    public static String TWEETS_TABLE = "tweets";
    public static String KEY_COLUMN_ID = "_id";
    public static String KEY_COLUMN_USERNAME = "username";
    public static String KEY_COLUMN_TEXT = "text";
    public static String KEY_COLUMN_PUBLICATION_DATE = "publication_date";
    public static String KEY_COLUMN_LAT = "latitude";
    public static String KEY_COLUMN_LNG = "longitude";
    public static String KEY_COLUMN_PROFILE_IMAGE_URL = "image_url";


    public static String SQL_CREATE_TWEETS_TABLE = "create table " + TWEETS_TABLE
            + "("
                + KEY_COLUMN_ID + " integer primary key autoincrement, "
                + KEY_COLUMN_USERNAME + " text not null, "
                + KEY_COLUMN_TEXT + " text not null, "
                + KEY_COLUMN_PUBLICATION_DATE + " int not null, "
                + KEY_COLUMN_LAT + " double, "
                + KEY_COLUMN_LNG + " double, "
                + KEY_COLUMN_PROFILE_IMAGE_URL + " text"
            + ");";

    public static String DB_SCRIPTS[] = {
            SQL_CREATE_TWEETS_TABLE
    };

    public static String TWEETS_ALL_COLUMNS[] = {
            KEY_COLUMN_ID,
            KEY_COLUMN_USERNAME,
            KEY_COLUMN_TEXT,
            KEY_COLUMN_PUBLICATION_DATE,
            KEY_COLUMN_LAT,
            KEY_COLUMN_LNG,
            KEY_COLUMN_PROFILE_IMAGE_URL
    };

}
