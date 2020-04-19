package com.briup.controller;


import com.briup.model.MyDao;
import com.briup.myaccountapp.R;
import com.briup.util.ActivityCollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class UpdateInfoActivity extends Activity {

    private TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_info);
        ActivityCollector.addActivity(this);
        String nowUser = MyDao.getNowUser(this);
        usernameTV = (TextView) findViewById(R.id.username);
        usernameTV.setText(nowUser);
    }

    //Back button
    public void updateBack(View view) {
        finish();
    }



    //Click to change password
    public void updatePwd(View view) {
        Intent intent = new Intent(UpdateInfoActivity.this, UpdatePwdActivity.class);
        startActivity(intent);
    }

    //Click the logout button
    public void quit(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Are you sure you want to quit?");
        alertDialog.setMessage("You need to log in again after logging out");
        alertDialog.setPositiveButton("Ok", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                ActivityCollector.finishAll();
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        alertDialog.create();
        alertDialog.show();
    }


}
