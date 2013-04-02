package com.ikshvaku.smsscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;

public class SendSMS extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_sms);
		
		Intent intent = getIntent();
		String number = intent.getStringExtra("smsAddressee");
		String text = intent.getStringExtra("smsData");
		
		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(number, null, text, null, null);
		
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_sms, menu);
		return true;
	}

}
