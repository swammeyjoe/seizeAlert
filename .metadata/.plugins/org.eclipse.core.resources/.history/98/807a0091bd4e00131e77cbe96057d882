package com.siezeAlert.seizealert;

import android.telephony.*;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;

@SuppressLint("UnlocalizedSms")
public class TextingActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		String name, number;
		SmsManager texting = SmsManager.getDefault();
		SharedPreferences data = getPreferences(MODE_PRIVATE);
		name = data.getString("Name", "");
		if (data.contains("Number1")){
			number = data.getString("Number1", "");			
		} else { 
			number = "1111111111";
		}
		
		texting.sendTextMessage("9562890158", null, "HELP", null, null);
	}
}
