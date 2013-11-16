package com.siezeAlert.seizealert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;

public class SetupGetContacts extends Activity {

	final int CONTACT_CASE = 0;
	int contactCount= 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_get_contacts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_get_contacts, menu);
		return true;
	}
	
	public void goHome(View view) {
		Intent intent = new Intent(this, StartActivity.class);
		startActivityForResult(intent, 0);
	}
	
	public void getContacts(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, CONTACT_CASE);
	}
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	  super.onActivityResult(reqCode, resultCode, data);

	  switch (reqCode) {
	    case (CONTACT_CASE) :
	      if (resultCode == Activity.RESULT_OK) {
	        Uri contactData = data.getData();
	        Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);
	        if (cursor.moveToFirst()) { 
	          addContactToPreferences(cursor);
	        }
	      }
	      break;
	  }
	}
	
	private void addContactToPreferences(Cursor cursor) {
		
        
        SharedPreferences data = getSharedPreferences("seizeAlert_pref", MODE_PRIVATE);
        Editor editor = data.edit();
        
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        String number = "12345";// cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        System.out.printf(name+" "+number+"\n");        
        
        Set contact = new HashSet();
        contact.add(name);
        contact.add(number);
        
        editor.putStringSet("Contact"+contactCount, contact);
        editor.commit();
        System.out.printf("DATA: " + data.toString());
        contactCount++;
	}

}
