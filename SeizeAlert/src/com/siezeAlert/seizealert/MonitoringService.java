package com.siezeAlert.seizealert;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.IntentService;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.hardware.usb.*;

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
		System.out.println("Reached monitoring service");

		//My plan for this method.
		
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
		//create an intent that launches a notification
		Intent intent = new Intent(MonitoringService.this, NotificationPressed.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent); 
		
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


