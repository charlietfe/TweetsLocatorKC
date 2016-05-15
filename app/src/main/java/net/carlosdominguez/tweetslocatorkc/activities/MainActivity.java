package net.carlosdominguez.tweetslocatorkc.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.carlosdominguez.tweetslocatorkc.R;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
