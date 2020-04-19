package com.briup.controller;

import com.briup.fragment.BaobiaoFragment;
import com.briup.fragment.MingxiFragment;
import com.briup.fragment.QianbaoFragment;
import com.briup.fragment.TixingFragment;
import com.briup.myaccountapp.R;
import com.briup.util.ActivityCollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

    private FragmentManager manager;
    private MingxiFragment mingxiFragment;
    private QianbaoFragment qianbaoFragment;
    private BaobiaoFragment baobiaoFragment;
    private TixingFragment tixingFragment;

    private ImageView mingxiIv;
    private ImageView qianbaoIv;
    private ImageView baobiaoIv;
    private ImageView tixingIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_after_login);

        ActivityCollector.addActivity(this);
        manager = getFragmentManager();
        init();
        setTabSelection(1);
    }

    public void init() {
        mingxiIv = (ImageView) findViewById(R.id.mingxi_iv);
        qianbaoIv = (ImageView) findViewById(R.id.qianbao_iv);
        baobiaoIv = (ImageView) findViewById(R.id.baobiao_iv);
        tixingIv = (ImageView) findViewById(R.id.tixing_iv);

        mingxiIv.setOnClickListener(this);
        qianbaoIv.setOnClickListener(this);
        baobiaoIv.setOnClickListener(this);
        tixingIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.mingxi_iv:
                setTabSelection(1);
                break;
            case R.id.qianbao_iv:
                setTabSelection(2);
                break;
            case R.id.baobiao_iv:
                setTabSelection(3);
                break;
            case R.id.tixing_iv:
                setTabSelection(4);
                break;
            default:
                break;
        }
    }

    //switch fragment
    private void setTabSelection(int index) {
        FragmentTransaction transaction = manager.beginTransaction();
        removeFragment(transaction);
        clearImage();
        switch (index) {
            case 1:
                mingxiIv.setImageResource(R.drawable.mingxi2);
                mingxiFragment = new MingxiFragment();
                transaction.add(R.id.content, mingxiFragment);
                break;
            case 2:
                qianbaoIv.setImageResource(R.drawable.qianbao2);
                qianbaoFragment = new QianbaoFragment();
                transaction.add(R.id.content, qianbaoFragment);
                break;
            case 3:
                baobiaoIv.setImageResource(R.drawable.baobiao2);
                baobiaoFragment = new BaobiaoFragment();
                transaction.add(R.id.content, baobiaoFragment);
                break;
            case 4:
                tixingIv.setImageResource(R.drawable.tixing2);
                tixingFragment = new TixingFragment();
                transaction.add(R.id.content, tixingFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void removeFragment(FragmentTransaction transaction) {
        if (mingxiFragment != null) {
            transaction.remove(mingxiFragment);
        }
        if (qianbaoFragment != null) {
            transaction.remove(qianbaoFragment);
        }
        if (baobiaoFragment != null) {
            transaction.remove(baobiaoFragment);
        }
        if (tixingFragment != null) {
            transaction.remove(tixingFragment);
        }
    }

    private void clearImage() {
        mingxiIv.setImageResource(R.drawable.mingxi1);
        qianbaoIv.setImageResource(R.drawable.qianbao1);
        baobiaoIv.setImageResource(R.drawable.baobiao1);
        tixingIv.setImageResource(R.drawable.tixing1);
    }

    //Click the event processing of increase revenue and expenditure button
    public void addShouzhi(View view) {
        //A dialog box pops up when you click the Add Revenue and Expenditure button
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Please Choose");
        alertBuilder.setIcon(R.drawable.logo);
        alertBuilder.setNegativeButton("New Income", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, AddDialogActivity.class);
                startActivity(intent);
            }
        });

        alertBuilder.setPositiveButton("New Expenditure", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, DecDialogActivity.class);
                startActivity(intent);
            }
        });
        alertBuilder.create();
        alertBuilder.show();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(getResources().getDrawable(R.drawable.logo))
                .setTitle("Are you sure you want to quit?")
                .setPositiveButton("Sure", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ActivityCollector.finishAll();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }

}
