package com.ikshvaku.smsscheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int MAIN_ACTIVITY_CONSTANT = 1; 
	DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
	DateFormat dateFormat = SimpleDateFormat.getDateInstance();
	DateFormat timeFormat =  SimpleDateFormat.getTimeInstance();

	Button schedule;
	ListView list;
	private final Map<String, String> displayDataMap = new HashMap<String, String>();

	private  ArrayAdapter<String> adapter;

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

		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {

				String key = adapter.getItem(position);
				String data = displayDataMap.get(key);
				long time = Long.valueOf(data.split("aqlpzaml")[0]);
				String addressee = data.split("aqlpzaml")[1];
				String text = data.split("aqlpzaml")[2];

				String formattedTime = timeFormat.format(time);
				String formattedDate = dateFormat.format(time);

				String dialogData = "To: " +  addressee + "\n" + "Date: " + formattedDate + "\n" + "Time: " + 
									formattedTime + "\n\n" + "SMS: " + "\n" + text;

				ShowMessage fragment = ShowMessage.getDialogFragment(dialogData);
				fragment.show(getFragmentManager(), "dialog");

			}

		});

		list.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				
				final String key = adapter.getItem(position);
				String data = displayDataMap.get(key);
				
				final String addressee = data.split("aqlpzaml")[1];
				long time = Long.valueOf(data.split("aqlpzaml")[0]);
				final int finalID = Integer.valueOf(data.split("aqlpzaml")[3]);
				final String dateTime = sdf.format(time);
				
				Builder dialog = new Builder(view.getContext());
				dialog.setTitle("! Delete Message" );
				dialog.setMessage("Delete this message ? ");
				dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						displayDataMap.remove(key);
						adapter.remove(addressee + " " + dateTime);
						adapter.notifyDataSetChanged();

						Intent cancelIntent = new Intent(getApplicationContext(), SendSMS.class);
						PendingIntent pdi = PendingIntent.getActivity(getApplicationContext(), finalID, cancelIntent, 
								PendingIntent.FLAG_ONE_SHOT);

						AlarmManager cancelManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
						cancelManager.cancel(pdi);

						Toast toast = Toast.makeText(getApplicationContext(), "SMS cancelled", Toast.LENGTH_LONG);
						toast.show();
					}
				});
				
				dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dialog.create().show();
				

				// TODO Auto-generated method stub
				return false;
			}

		});
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
			int uniqueID = data.getIntExtra("requestCode", 0);
			String dateTime = sdf.format(time);
			String finalData = addressee + " " + dateTime;
			adapter.add(finalData);
			displayDataMap.put(finalData, time + "aqlpzaml" + addressee + "aqlpzaml" + smsText + "aqlpzaml" + uniqueID);
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