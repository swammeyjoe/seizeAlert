package com.siezeAlert.seizealert;


import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
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
        } else {
        	
        	editor.remove("FirstTime");
        	editor.commit();
        	//just launch the regular UI with options menus and stuff
        }
        
        //check if an async task is currently running, if not fire one off to monitor the data
        setContentView(R.layout.activity_start);

        if (!isMonitorRunning()){
        	Intent serviceIntent = new Intent(StartActivity.this, MonitoringService.class);
        	serviceIntent.setData(null);
        	StartActivity.this.startService(serviceIntent);
        }
        
        
        
//        Set <String> c = data.getStringSet("Contact1", null);
//        Object [] contact = c.toArray();
//        
//        TextView contactNames = (TextView) findViewById(R.id.contactNames);
//        contactNames.setText("AAAAAAAAAAAAA");
//        for (int i = 0; i < CONTACT_MAX; i++) {
//        	Set <String> c = data.getStringSet("Contact"+i, null);//new HashSet<String>());
//        	if (c != null) {
//        		Object [] contact = c.toArray();
//                System.out.printf(""+contact[0]+" "+contact[1]+"\n");
//                
//                
//                contactNames.setText(""+contact[0]);
//        	}
//            return;
//        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start, menu);
        return true;
    }
    
    private boolean isMonitorRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MonitoringService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
