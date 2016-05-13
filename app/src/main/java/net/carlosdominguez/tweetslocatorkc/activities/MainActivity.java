package net.carlosdominguez.tweetslocatorkc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.carlosdominguez.tweetslocatorkc.R;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
