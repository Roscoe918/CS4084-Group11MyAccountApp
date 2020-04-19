package com.briup.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.briup.model.RegisterDao;
import com.briup.myaccountapp.R;
import com.briup.util.StringUtils;

public class RegisterActivity extends Activity {

    private EditText userEditText;
    private EditText passwdEditText;
    private EditText repasswdEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {

        userEditText = (EditText) findViewById(R.id.register_user_edit);
        passwdEditText = (EditText) findViewById(R.id.register_passwd_edit);
        repasswdEditText = (EditText) findViewById(R.id.reRegister_passwd_edit);

    }

    public void registerBack(View view) {
        finish();
    }

    public void registerUser(View view) {
        String userStr = userEditText.getText().toString().trim();
        String passwdStr = passwdEditText.getText().toString().trim();
        String repasswdStr = repasswdEditText.getText().toString().trim();

        if (("".equals(userStr) || userStr == null) ||
                ("".equals(passwdStr) || passwdStr == null)
                || ("".equals(repasswdStr) || repasswdStr == null)) {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.error_icon))
                    .setTitle("registration failed")
                    .setMessage("Username or password cannot be empty")
                    .setPositiveButton("OK", null)
                    .create().show();
        } else if (StringUtils.isStartWithNumber(userStr)) {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.error_icon))
                    .setTitle("Tips")
                    .setMessage("Username cannot start with a number")
                    .setPositiveButton("OK", null)
                    .create().show();
        } else if (!passwdStr.equals(repasswdStr)) {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.error_icon))
                    .setTitle("registration failed")
                    .setMessage("Passwords entered twice are inconsistent")
                    .setPositiveButton("OK", null)
                    .create().show();
        } else {
            //Determine if the user name already exists
            if (RegisterDao.isUserExist(this, userStr)) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setIcon(getResources().getDrawable(R.drawable.error_icon))
                        .setTitle("registration failed")
                        .setMessage("The user name already exists")
                        .setPositiveButton("OK", null)
                        .create().show();
            } else {
                //Insert user into database table
                RegisterDao.addUser(this, userStr, passwdStr);
                //Create user's own income and expenditure statement
                RegisterDao.createUserTable(this, userStr);
                new AlertDialog.Builder(RegisterActivity.this)
                        .setIcon(getResources().getDrawable(R.drawable.ok))
                        .setTitle("Register Successful")
                        .setMessage("GO TO Login")
                        .setPositiveButton("OK", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create().show();
            }
        }
        userEditText.setText("");
        passwdEditText.setText("");
        repasswdEditText.setText("");
    }

}
