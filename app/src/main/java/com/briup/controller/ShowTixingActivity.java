package com.briup.controller;

import com.briup.myaccountapp.R;
import com.briup.util.ActivityCollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class ShowTixingActivity extends Activity {

	private TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_tixing);	
		Intent intent=getIntent();
		String type=intent.getStringExtra("type");
		AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
    	alertDialog.setTitle("Account Reminder");
    	alertDialog.setMessage(type);
    	alertDialog.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
		    	finish();
			}
		});
    	alertDialog.create();
    	alertDialog.show();
	}

	
}
