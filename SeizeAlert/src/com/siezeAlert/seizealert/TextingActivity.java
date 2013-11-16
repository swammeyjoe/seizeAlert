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
		super.onCreate(savedInstanceState);
		String name, number;
		SmsManager texting = SmsManager.getDefault();
		
		SharedPreferences data = getSharedPreferences("seizeAlert_pref", MODE_PRIVATE);
    	Set <String> c = data.getStringSet("Contact0", null);//new HashSet<String>());
    	if(c == null) {
        	System.out.printf("C IS NULL\n");
    		return;
    	}

		Object [] contact = c.toArray();

		number = (String) contact[0];
		System.out.printf(number+"\n");
		texting.sendTextMessage(number, null, "Automated testing app texting. Sorry.", null, null);
	}
}
