package com.ikshvaku.smsscheduler;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.widget.TextView;

public class SendSMS extends Activity {

	TextView newId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_sms);
		
		newId = (TextView)findViewById(R.id.newId);
		
		Intent intent = getIntent();
		String number = intent.getStringExtra("smsAddressee");
		String text = intent.getStringExtra("smsData");
		long difference = intent.getLongExtra("difference", 0);
		SimpleDateFormat sfg = new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss");
		newId.setText(number + "\n" + text + "\n" + sfg.format(difference));
		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(number, null, text, null, null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_sms, menu);
		return true;
	}

}
