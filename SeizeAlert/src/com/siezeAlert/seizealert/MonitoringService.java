package com.siezeAlert.seizealert;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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


import android.hardware.usb.*;

@SuppressLint("NewApi")
public class MonitoringService extends IntentService {
	public final int TIME_LIMIT = 30;
	public final int THRESHOLD = 55;
	
	private UsbManager mUsbManager;
	private UsbAccessory mUsbAccessory;
	ParcelFileDescriptor mFileDescriptor;
	FileInputStream mInputStream;
	
	
	public MonitoringService() {
		super("Monitoring service");
		// Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent incoming) {
		//String dataString = incoming.getDataString();
		//System.out.println("This is the string we're getting from the intent: " + dataString);
 
		System.out.println("Reached monitoring service");
		long vibrate[] = {0, 100, 0, 100,0, 100, 0, 100,0, 100, 0, 100,0, 100, 0, 100};
		//My plan for this method.
		NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    			.setSmallIcon(R.drawable.ic_launcher)
    	        .setContentTitle("YOU'RE GONNA DIE!")
    	        .setAutoCancel(true)
    	        .setVibrate(vibrate)
    	        .setContentText("Click this to live");
    	Intent resultIntent = new Intent(this, SeizureConfirmation.class);    	
    	
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
		//create an intent that launches the FalsePositive check activity
		System.out.println("I MADE IT");
		
		
		int overCounter = 0;
		int underCounter = 0;
		while (true) {
			int value = readFromBoard();
			if (value > THRESHOLD) {
				overCounter++;
			}
			if (overCounter > TIME_LIMIT){
				break;
			}
		}
//		Intent intent = new Intent(MonitoringService.this, NotificationPressed.class);
		
		
	}
	
	private void openAccessory(UsbAccessory accessory) {
		System.out.println("trying to open accessory...");
		mFileDescriptor = mUsbManager.openAccessory(accessory);
		if (mFileDescriptor != null) {
			FileDescriptor fd = mFileDescriptor.getFileDescriptor();
			mInputStream = new FileInputStream(fd);
		}
	}
	
	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(action)) {
				System.out.println("Attached");
				synchronized (this) {
					UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
					if (intent.getBooleanExtra(
							UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						openAccessory(accessory);
					} else {
					}
				}
			} else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
				System.out.println("Not attached");
				UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
			}
		}
	};
	
	//TESTING PURPOSES ONLY!!!!!!
	private int readFromBoard() {
		//System.out.println("TEST TEST TEST TEST TEST TEST TEST");
		int retVal = -1;
		byte[] buffer = new byte[1024];
		int err = 0;
		try{
			err = mInputStream.read(buffer);
		} catch(Exception e){}
		
		return buffer[0];
		
	}
}


