package com.siezeAlert.seizealert;


import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends Activity {

	final int CONTACT_MAX = 3;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences data = getSharedPreferences("seizeAlert_pref", MODE_PRIVATE);
        boolean FirstTime = data.getBoolean("FirstTime", true);
        Editor editor = data.edit();
        
        if (FirstTime) {
        	
        	System.out.printf("INSIDE MAIN "+ FirstTime + "\n");
        	
            editor.putBoolean("FirstTime", false);
            editor.commit();
            
        	Intent intent = new Intent(this, SetupIntro.class);
            startActivityForResult(intent, 0);
            editor.remove("FirstTime");
        	editor.commit();
        } else {
        	
        	editor.remove("FirstTime");
        	editor.commit();
        	//just launch the regular UI with options menus and stuff
        }
        
        //check if an async task is currently running, if not fire one off to monitor the data
        
        setContentView(R.layout.activity_start);
        
        TextView contactNames = (TextView) findViewById(R.id.contactNames);
        for (int i = 0; i < CONTACT_MAX; i++) {
        	Set <String> c = data.getStringSet("Contact"+i, null);//new HashSet<String>());
        	if (c != null) {
        		Object [] contact = c.toArray();
                System.out.printf(""+contact[0]+" "+contact[1]+"\n");
                contactNames.setText(contactNames.getText()+"\n"+contact[0]);
        	}
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    }
}
