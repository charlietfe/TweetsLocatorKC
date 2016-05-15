package net.carlosdominguez.tweetslocatorkc;

import android.app.Application;

import net.carlosdominguez.tweetslocatorkc.model.db.DBHelper;

/**
 * Created by Carlos Domínguez on 15/05/16.
 */
public class TweetsLocatorApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper.configure(getApplicationContext(), "TweetsLocatorDatabase.sqlite");
    }
}
