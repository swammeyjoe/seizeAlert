package com.siezeAlert.seizealert;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationPressed extends Activity {
	
    @TargetApi(16)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_start);  
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    			.setSmallIcon(R.drawable.ic_launcher)
    	        .setContentTitle("YOU'RE GONNA DIE!")
    	        .setContentText("Click this to live");
    	Intent resultIntent = new Intent(this, StartActivity.class);    	
    	
    	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	stackBuilder.addParentStack(StartActivity.class);
    	stackBuilder.addNextIntent(resultIntent);
    	PendingIntent resultPendingIntent =
    	        stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	        );
    	
    	mBuilder.setContentIntent(resultPendingIntent);
    	
    	NotificationManager mNotificationManager =
    			(NotificationManager) getSystemService("notification");
    	mNotificationManager.notify(123, mBuilder.build());
        
         
    }
	

}

