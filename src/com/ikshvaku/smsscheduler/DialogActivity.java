package com.ikshvaku.smsscheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DialogActivity extends Activity {

	TextView to;
	TextView date;
	TextView time;
	TextView text;
	DateFormat dateFormat = SimpleDateFormat.getDateInstance();
	DateFormat timeFormat =  SimpleDateFormat.getDateTimeInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);
		
		to = (TextView)findViewById(R.id.to);
		date = (TextView)findViewById(R.id.date);
		time = (TextView)findViewById(R.id.time);
		text = (TextView)findViewById(R.id.text);
		
		Intent intent = getIntent();
		String smsTo = intent.getStringExtra("to");
		long smsTime = Long.valueOf(intent.getStringExtra("time"));
		String smsText = intent.getStringExtra("text");
		String formattedTime = timeFormat.format(smsTime);
		String formattedDate = timeFormat.format(smsTime);
		
		to.setText("To: " + smsTo);
		time.setText("Time: " + formattedTime);
		date.setText("Date: " + formattedDate);
		text.setText("SMS: " + smsText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dialog, menu);
		return true;
	}

}
