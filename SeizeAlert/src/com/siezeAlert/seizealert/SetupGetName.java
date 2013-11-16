package com.siezeAlert.seizealert;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SetupGetName extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_get_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setup_get_name, menu);
		return true;
	}
	
	public void nextSetupContacts(View view) {
		setName();
		
		
		Intent intent = new Intent(this, SetupGetContacts.class);
		startActivity(intent);
	}
	
	private void setName() {
		SharedPreferences data = getSharedPreferences("seizeAlert_pref", MODE_PRIVATE);
        Editor editor = data.edit();
        
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        
        editor.putString("Name", name);
        editor.commit();
	}

}
