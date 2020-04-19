package com.briup.controller;

import com.briup.myaccountapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

public class WelcomeActivity extends Activity {

    private Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.arg1 == 10) {
                Intent intent = new Intent(WelcomeActivity.this, IndexActivity.class);
                startActivity(intent);
                finish();
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        Log.d("data", "This is my homepage！");

        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.arg1 = 10;
                mainHandler.sendMessage(message);
            }

            ;
        }.start();
    }

}
