package com.ikshvaku.smsscheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class Scheduler extends Activity {

	AlarmManager newManager;
	EditText addressee;
	TextView date;
	TextView time;
	EditText smsText;
	Button scheduleTextButton;
	Button scheduleTime;
	Button scheduleDate;
	Calendar cal;
	Calendar targetCal;
	
	SimpleDateFormat sfg = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scheduler);
		
		addressee = (EditText)findViewById(R.id.addressee);
		date = (TextView)findViewById(R.id.date);
		time = (TextView)findViewById(R.id.time);
		smsText = (EditText)findViewById(R.id.smsText);
		scheduleDate = (Button)findViewById(R.id.scheduleDate);
		scheduleTime = (Button)findViewById(R.id.scheduleTime);
		scheduleTextButton = (Button)findViewById(R.id.scheduleTextButton);
		
		
		cal = Calendar.getInstance();
		targetCal = Calendar.getInstance();
		targetCal.set(Calendar.SECOND, 00);
//		day = cal.get(Calendar.DAY_OF_MONTH);
//		month = cal.get(Calendar.MONTH) + 1;
//		year = cal.get(Calendar.YEAR);
//		hours = cal.get(Calendar.HOUR_OF_DAY);
//		minutes = cal.get(Calendar.MINUTE);
//		date.setText(day + "/" + month + "/" + year);
//		time.setText(hours + ":" + minutes);
		
		scheduleDate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				
				DatePickerDialog dpd = new DatePickerDialog(Scheduler.this, d, year, month, day);
				dpd.show();
				// TODO Auto-generated method stub
				
			}
			
		});
		
		scheduleTime.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				int hours = cal.get(Calendar.HOUR_OF_DAY);
				int minutes = cal.get(Calendar.MINUTE);
				
				TimePickerDialog tpd = new TimePickerDialog(Scheduler.this, t, hours, minutes, 
						DateFormat.is24HourFormat(getApplicationContext()));
				tpd.show();
				// TODO Auto-generated method stub
				
			}
			
		});
		
		scheduleTextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				cal = Calendar.getInstance();
				long difference = targetCal.getTimeInMillis();
				
				String smsData = smsText.getText().toString();
				String smsAddressee = addressee.getText().toString(); 
				
//				smsText.setText("Target Time: " + alarmInstance.getTimeInMillis() 
//						+ "Current Time: " + cal.getTimeInMillis() + "System Time: " + System.currentTimeMillis()
//						+ "final time: " + difference);
//				smsText.setText("Current Time: " + cal.getTimeInMillis());
//				smsText.setText("System Time: " + System.currentTimeMillis());
//				smsText.setText("final time: " + difference );
				Intent fireSendSMSClass = new Intent(Scheduler.this, SendSMS.class);
				fireSendSMSClass.putExtra("smsData", smsData);
				fireSendSMSClass.putExtra("smsAddressee", smsAddressee);
				fireSendSMSClass.putExtra("difference", difference);
				
				PendingIntent pdi = PendingIntent.getActivity(Scheduler.this, 0, fireSendSMSClass, 
						PendingIntent.FLAG_ONE_SHOT);
				
				smsText.setText(""+sfg.format(difference));
				newManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				newManager.set(AlarmManager.RTC_WAKEUP, difference, pdi);
				
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int thisyear, int monthOfYear,
				int dayOfMonth) {
			
			targetCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			targetCal.set(Calendar.MONTH, monthOfYear);
			targetCal.set(Calendar.YEAR, thisyear);
			
			long time = targetCal.getTimeInMillis();
			
			date.setText(sfg.format(time));
			// TODO Auto-generated method stub
			
		}
	};
	
	private TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
			// TODO Auto-generated method stub
			targetCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
			targetCal.set(Calendar.MINUTE, minuteOfDay);

			long time = targetCal.getTimeInMillis();
			
			date.setText(sfg.format(time));
		}
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scheduler, menu);
		return true;
	}

}
