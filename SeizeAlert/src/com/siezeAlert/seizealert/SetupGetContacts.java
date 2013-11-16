package com.siezeAlert.seizealert;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

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
//	      if (resultCode == Activity.RESULT_OK) {
//	        Uri contactData = data.getData();
//	        Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);
//	        if (cursor.moveToFirst()) { 
//	          addContactToPreferences(cursor);
//	        }
//	      }
	    	if (resultCode == Activity.RESULT_OK)
	    	{
	    	  Uri contactData = data.getData();
	    	  ContentResolver cr = getContentResolver();
	    	  Cursor curContact = managedQuery(contactData, null, null, null, null);

	    	  if ((curContact != null) && (curContact.moveToFirst()))
	    	  {
	    	    String id = curContact.getString(curContact
	    	        .getColumnIndexOrThrow(BaseColumns._ID));
	    	    
	            String nameFromCursor = curContact.getString(curContact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


	    	    // check there's a phone number at all
	    	    if (Integer.parseInt(curContact.getString(curContact
	    	        .getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
	    	    {

	    	      // get the phone number
	    	      Cursor curNumbers = cr.query(
	    	          ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
	    	          ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
	    	          new String[] { id }, null);

	    	      if ((curNumbers != null) && curNumbers.moveToFirst())
	    	      {
	    	        String strNumber = 
	    	          curNumbers.getString(
	    	           curNumbers.getColumnIndexOrThrow(
	    	             ContactsContract.CommonDataKinds.Phone.NUMBER));
	    	        System.out.printf("NUMBER: "+strNumber +"\n");
	    	        addContactToPreferences(nameFromCursor, strNumber);

	    	        curNumbers.close();
	    	      }
	    	    }
	    	    curContact.close();
	    	  }
	    	}
	      break;
	  }
	}
	
	private void addContactToPreferences(String name, String number) {
		
        
        SharedPreferences data = getSharedPreferences("seizeAlert_pref", MODE_PRIVATE);
        Editor editor = data.edit();
        
//        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//        String number = "12345";// cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        System.out.printf(name+" "+number+"\n");        
        
        TextView emergencyContacts = (TextView) findViewById(R.id.emergency_contacts_names);
        emergencyContacts.setText(emergencyContacts.getText() + name + "\n");
        
        Set contact = new HashSet();
        contact.add(name);
        contact.add(number);
        
        editor.putStringSet("Contact0", contact);
        editor.commit();
        System.out.printf("DATA: " + data.toString());
        contactCount++;
	}

}
