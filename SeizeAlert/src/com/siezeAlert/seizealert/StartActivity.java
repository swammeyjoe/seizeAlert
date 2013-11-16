package com.siezeAlert.seizealert;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;

public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        SharedPreferences data = getPreferences(MODE_PRIVATE);
        boolean FirstTime = data.getBoolean("FirstTime", false);
        if (FirstTime) {
        	//TODO(Freddy): here is where we need to ask them to enter their name and at least 
        	//one contact number
        } else {
        	//just launch the regular UI with options menus and stuff
        }
        
        //check if an async task is currently running, if not fire one off to monitor the data
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    }
}
