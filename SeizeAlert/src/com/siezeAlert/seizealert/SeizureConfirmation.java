package com.siezeAlert.seizealert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SeizureConfirmation extends Activity {

	int currentCountDown = 30;
	TextView countDownText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seizure_confirmation);
		countDownText = (TextView) findViewById(R.id.count_down);
		countDownText.setText(currentCountDown+" sec");
		countDown();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seizure_confirmation, menu);
		return true;
	}
	
	public void falseAlarm(View view) {
		finish();
	}
	
	private void countDown() {
		if(currentCountDown-1 < 0) {
			Intent intent = new Intent(this, TextingActivity.class);
			startActivity(intent);
			return;
		}

		Handler handler = new Handler(); handler.postDelayed(new Runnable() {
			public void run() {
				currentCountDown--;
				countDownText.setText(currentCountDown+" sec");
				countDown();
				}
			}, 1000);
	}

}
