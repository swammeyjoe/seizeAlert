package com.siezeAlert.seizealert;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
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
    	// Creates an explicit intent for an Activity in your app
    	// **********
    	//Intent resultIntent = new Intent(this, ResultActivity.class);
    	//Intent resultIntent = new Intent(this, ResultActivity.class);    	
    	
    	
    	// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	//TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    	// Adds the back stack for the Intent (but not the Intent itself)
    	//stackBuilder.addParentStack(StartActivity.class);
    	// Adds the Intent that starts the Activity to the top of the stack
    	/*PendingIntent resultPendingIntent =
    	        stackBuilder.getPendingIntent(
    	            0,
    	            PendingIntent.FLAG_UPDATE_CURRENT
    	        );
    	mBuilder.setContentIntent(resultPendingIntent);*/
    	
    	NotificationManager mNotificationManager =
    			(NotificationManager) getSystemService("notification");
    	mNotificationManager.notify(123, mBuilder.build());
        
         
    }
	

}

