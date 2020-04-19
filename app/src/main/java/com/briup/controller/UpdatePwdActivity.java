package com.briup.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.briup.model.MyDao;
import com.briup.model.UpdatePwdDao;
import com.briup.myaccountapp.R;
import com.briup.util.ActivityCollector;


public class UpdatePwdActivity extends Activity {

	private EditText oldPwd;
	private EditText newPwd;
	private EditText renewPwd;
		
	private String nowUser=null;
	private String oldpwd=null;
	private String newPwdStr=null;
	private String renewPwdStr=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_update_pwd);
		
		oldPwd=(EditText) findViewById(R.id.oldpwd);
		newPwd=(EditText) findViewById(R.id.newpwd);
		renewPwd=(EditText) findViewById(R.id.renewpwd);
				
		nowUser=MyDao.getNowUser(this);
	}
	//Click to change password
	public void updatePwd(View view){
		oldpwd=oldPwd.getText().toString().trim();
		newPwdStr=newPwd.getText().toString().trim();
		renewPwdStr=renewPwd.getText().toString().trim();
		
		if(oldpwd.equals("") || newPwdStr.equals("") || renewPwdStr.equals("")){
			new AlertDialog.Builder(UpdatePwdActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.error_icon))
			.setTitle("Password change failed")
			.setMessage("Password can not empty")
			.setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					oldPwd.setText("");
					newPwd.setText("");
					renewPwd.setText("");
				}
			})
			.create().show();

		}else{
			if(UpdatePwdDao.isOldPwd(this, nowUser, oldpwd)){
				if(oldpwd.equals(newPwdStr)){
					new AlertDialog.Builder(UpdatePwdActivity.this)
					.setIcon(getResources().getDrawable(R.drawable.error_icon))
					.setTitle("Change password failed")
					.setMessage("The new password cannot be the same as the original password")
					.setPositiveButton("OK", null)
					.create().show();
					oldPwd.setText("");
					newPwd.setText("");
					renewPwd.setText("");
				}else{
					if(!newPwdStr.equals(renewPwdStr)){
						new AlertDialog.Builder(UpdatePwdActivity.this)
						.setIcon(getResources().getDrawable(R.drawable.error_icon))
						.setTitle("Change password failed")
						.setMessage("The new password entered twice is inconsistent")
						.setPositiveButton("OK", null)
						.create().show();
						oldPwd.setText("");
						newPwd.setText("");
						renewPwd.setText("");
					}else{
						UpdatePwdDao.updateUserPwd(this, nowUser, newPwdStr);
						new AlertDialog.Builder(UpdatePwdActivity.this)
						.setIcon(getResources().getDrawable(R.drawable.ok))
						.setTitle("Change password successful")
						.setMessage("Need to log in again")
						.setPositiveButton("Ok", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								ActivityCollector.finishAll();
								Intent intent=new Intent(UpdatePwdActivity.this,LoginActivity.class);
								startActivity(intent);
								finish();
							}
						})
						.create().show();
					}
				}
			}else{
				new AlertDialog.Builder(UpdatePwdActivity.this)
				.setIcon(getResources().getDrawable(R.drawable.error_icon))
				.setTitle("Password change failed")
				.setMessage("The original password entered is incorrect")
				.setPositiveButton("OK", null)
				.create().show();
				oldPwd.setText("");
				newPwd.setText("");
				renewPwd.setText("");
			}
		}

	}
	
	public void quit(View view){
		finish();
	}
	
}
