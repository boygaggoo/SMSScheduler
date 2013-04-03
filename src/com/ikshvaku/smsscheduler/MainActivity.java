package com.ikshvaku.smsscheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final int MAIN_ACTIVITY_CONSTANT = 1; 
	DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
	
	Button schedule;
	ListView list;
	
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView welcome = (TextView)findViewById(R.id.welcome_one);
		try{
		list = (ListView)findViewById(R.id.list);}
		catch(Exception e)
		{
			welcome.setText("beep beep. error");
		}
		schedule = (Button)findViewById(R.id.schedule);
		
		schedule.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent fireScheduleActivity = new Intent(MainActivity.this, Scheduler.class);
				startActivityForResult(fireScheduleActivity, MAIN_ACTIVITY_CONSTANT);
				// TODO Auto-generated method stub	
			}	
		});
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item);
		list.setAdapter(adapter);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == Scheduler.SCHEDULER_CONSTANT)
		{
			String addressee = data.getStringExtra("smsAddressee");
			String smsText = data.getStringExtra("smsData");
			long time = data.getLongExtra("dateTime", 0);
			String dateTime = sdf.format(time);
			String finalData = addressee + " " + dateTime;
			adapter.add(finalData);
			adapter.notifyDataSetChanged();
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
