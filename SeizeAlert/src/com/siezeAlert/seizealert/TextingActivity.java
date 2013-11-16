package com.siezeAlert.seizealert;

import java.util.Set;

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
    	Set <String> c = data.getStringSet("Contact", null);//new HashSet<String>());
		Object [] contact = c.toArray();
		number = (String) contact[1];			
		texting.sendTextMessage(number, null, "Automated testing app texting. Sorry.", null, null);
	}
}
