package com.briup.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.briup.model.LoginDao;
import com.briup.myaccountapp.R;
import com.briup.util.ActivityCollector;


public class LoginActivity extends Activity {

    private EditText usernameEdit;
    private EditText passwdEdit;
    private CheckBox checkBox;

    private SharedPreferences sharedPreferences;
    private Editor editor;

    private String usernameStr;
    private String passwdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);
        init();
    }

    @SuppressLint("WrongConstant")
    public void init() {
        usernameEdit = (EditText) findViewById(R.id.login_user_edit);
        passwdEdit = (EditText) findViewById(R.id.login_passwd_edit);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        sharedPreferences = getSharedPreferences("loginInfo", MODE_APPEND);
        editor = sharedPreferences.edit();

        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    editor.putBoolean("remember", true);
                } else {
                    editor.putBoolean("remember", false);
                }
                editor.commit();
            }
        });

        //Determine whether the password was remembered before initialization
        if (sharedPreferences.getBoolean("remember", true)) {
            usernameEdit.setText(sharedPreferences.getString("username", null));
            passwdEdit.setText(sharedPreferences.getString("password", null));
            checkBox.setChecked(true);
        }
    }

    //Back button event handling
    public void loginBack(View view) {
        finish();
    }

    //Event processing of login button
    public void loginUser(View view) {
        usernameStr = usernameEdit.getText().toString().trim();
        passwdStr = passwdEdit.getText().toString().trim();

        //Determine whether the input box is empty
        if (("".equals(usernameStr) || usernameStr == null) ||
                ("".equals(passwdStr) || passwdStr == null)) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.error_icon))
                    .setTitle("Login Failed")
                    .setMessage("Username or password cannot be empty")
                    .setPositiveButton("OK", null)
                    .create().show();
            usernameEdit.setText("");
            passwdEdit.setText("");
        } else {
            if (isUserExist(usernameStr, passwdStr)) {
                //If the user name and password are correct, record the user name of the current user in the file
                editor.putString("nowUser", usernameStr);
                editor.commit();
                //Determine if Remember Password is checked
                if (checkBox.isChecked()) {
                    editor.putString("username", usernameStr);
                    editor.putString("password", passwdStr);
                    editor.putBoolean("remember", true);
                    editor.commit();
                } else {
                    //Clear the username and password stored in the remember password file
                    editor.putBoolean("remember", false);
                    editor.remove("username");
                    editor.remove("password");
                    editor.commit();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(LoginActivity.this)
                        .setIcon(getResources().getDrawable(R.drawable.error_icon))
                        .setTitle("Login Failed")
                        .setMessage("Incorrect username or password")
                        .setPositiveButton("OK", null)
                        .create().show();
                usernameEdit.setText("");
                passwdEdit.setText("");
            }
        }
    }

    //Determine whether the user already exists in the database
    public boolean isUserExist(String name, String pwd) {
        return LoginDao.isUserExist(this, name, pwd);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }
}
