package com.ikshvaku.smsscheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ShowMessage extends DialogFragment {

	String to;
	String date;
	int time;
	String text;
	
	DateFormat dateFormat = SimpleDateFormat.getDateInstance();
	DateFormat timeFormat =  SimpleDateFormat.getDateTimeInstance();
	
	static ShowMessage getDialogFragment(String data){
		
		ShowMessage fragment = new ShowMessage();
		Bundle args = new Bundle();
		args.putString("data", data);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
				
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		String data = getArguments().getString("data");
		builder.setTitle("Scheduled SMS:");
		builder.setMessage(data);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
				// TODO Auto-generated method stub
				
			}
		});
		
		return builder.create();
	}

	
	

}
