package com.ikshvaku.smsscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button schedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		schedule = (Button)findViewById(R.id.schedule);
		
		schedule.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent fireScheduleActivity = new Intent(MainActivity.this, Scheduler.class);
				startActivity(fireScheduleActivity);
				// TODO Auto-generated method stub	
			}	
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
