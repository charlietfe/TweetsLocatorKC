package net.carlosdominguez.tweetslocatorkc.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.WeakReference;

/**
 * Created by Carlos on 13/05/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance;
    private static WeakReference<Context> sContext;
    private static String sDatabaseName;


    private static void configure(Context context, String databaseName) {
        sContext = new WeakReference<>(context);
        sDatabaseName = databaseName;
    }

    private DBHelper(Context context, String databaseName) {
        super(context, databaseName, null, DBConstants.DB_VERSION);
    }

    public static DBHelper getInstance() {
        if (sContext == null || sDatabaseName == null) {
            throw new IllegalAccessError();
        }

        if (sInstance == null) {
            sInstance = new DBHelper(sContext.get().getApplicationContext(), sDatabaseName);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sql: DBConstants.DB_SCRIPTS) {
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
