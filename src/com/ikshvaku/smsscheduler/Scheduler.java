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
import android.widget.Toast;

public class Scheduler extends Activity {

	private static final String workCompleted = "Your SMS has been scheduled";
	public static final int SCHEDULER_CONSTANT = 2;
	
	AlarmManager newManager;
	EditText addressee;
	TextView dateView;
	TextView timeView;
	EditText smsText;
	Button scheduleSMSButton;
	Button scheduleTime;
	Button scheduleDate;
	Calendar cal;
	Calendar targetCal;
	Intent backIntent; 
	
	SimpleDateFormat sfgDate = new SimpleDateFormat("dd.MM.yyyy");
	SimpleDateFormat sfgTime = new SimpleDateFormat(" HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scheduler);
		
		backIntent = getIntent();
		addressee = (EditText)findViewById(R.id.addressee);
		dateView = (TextView)findViewById(R.id.date);
		timeView = (TextView)findViewById(R.id.time);
		smsText = (EditText)findViewById(R.id.smsText);
		scheduleDate = (Button)findViewById(R.id.scheduleDate);
		scheduleTime = (Button)findViewById(R.id.scheduleTime);
		scheduleSMSButton = (Button)findViewById(R.id.scheduleTextButton);
		
		
		targetCal = Calendar.getInstance();
		targetCal.set(Calendar.SECOND, 00);
		
		
		dateView.setText(checkDate(targetCal.get(Calendar.DAY_OF_MONTH)) + "/" + 
				checkDate((targetCal.get(Calendar.MONTH )+ 1)) + "/" + checkDate(targetCal.get(Calendar.YEAR)));
		
		timeView.setText(checkDate(targetCal.get(Calendar.HOUR_OF_DAY)) + ":" + 
				checkDate(targetCal.get(Calendar.MINUTE)) + ":" + checkDate(targetCal.get(Calendar.SECOND)));
		
		
		
		scheduleDate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				cal = Calendar.getInstance();
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
				
				cal = Calendar.getInstance();
				int hours = cal.get(Calendar.HOUR_OF_DAY);
				int minutes = cal.get(Calendar.MINUTE);
				
				TimePickerDialog tpd = new TimePickerDialog(Scheduler.this, t, hours, minutes, 
						DateFormat.is24HourFormat(getApplicationContext()));
				tpd.show();
				// TODO Auto-generated method stub
				
			}
			
		});
		
		scheduleSMSButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				

				long difference = targetCal.getTimeInMillis();
				
				String smsData = smsText.getText().toString();
				String smsAddressee = addressee.getText().toString(); 
				int requestCode = (int)System.currentTimeMillis();
				
				new SchedulingInProgress(difference, smsData, smsAddressee, requestCode).schedule();
				
				
				Toast toast = Toast.makeText(Scheduler.this, workCompleted, Toast.LENGTH_LONG);
				toast.show();
				
				backIntent.putExtra("smsData", smsData);
				backIntent.putExtra("smsAddressee", smsAddressee);
				backIntent.putExtra("dateTime", difference);
				backIntent.putExtra("requestCode", requestCode);
				setResult(SCHEDULER_CONSTANT, backIntent);
				finish();
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
			
			dateView.setText(sfgDate.format(time));
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
			
			timeView.setText(sfgTime.format(time));
		}
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scheduler, menu);
		return true;
	}
	
	public String checkDate(int i)
	{
		String date;
		if(i<10)
			date = "0" + i;
		else
			date = ""+i;
		return date;
	}
	
	private class SchedulingInProgress{
		
		long timeToTrigger;
		String data;
		String addressee;
		int code;
		
		public SchedulingInProgress(long difference, String smsData,
				String smsAddressee, int requestCode) {
			timeToTrigger = difference;
			data = smsData;
			addressee = smsAddressee;
			code = requestCode;
			// TODO Auto-generated constructor stub
		}

		private void schedule(){
			Intent fireSendSMSClass = new Intent(getApplicationContext(), SendSMS.class);
			fireSendSMSClass.putExtra("smsData", data);
			fireSendSMSClass.putExtra("smsAddressee", addressee);
			
	
			PendingIntent pdi = PendingIntent.getActivity(getApplicationContext(), code, fireSendSMSClass, 
					PendingIntent.FLAG_ONE_SHOT);
			
			newManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			newManager.set(AlarmManager.RTC_WAKEUP, timeToTrigger, pdi);
		}
		
	}
	

}
