package com.briup.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.briup.bean.Record;
import com.briup.model.DeleteShouzhiDao;
import com.briup.model.MyDao;
import com.briup.model.SelectShouzhiDao;
import com.briup.myaccountapp.R;
import com.briup.util.MyListViewAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class YearMingxiActivity extends Activity {
    
	private ListView yearList;
	private List<Record> list;
	private TextView yShouru;
	private TextView yZhichu;
    private String userName;
    
	private String dateStr;
	private String shouzhiStr;
	private String qianbaoStr;
	private String typeStr;
	private float money;
	private String beizhuStr;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_year_mingxi);
		
		yearList=(ListView) findViewById(R.id.year_record);
		list=new ArrayList<Record>();
		yShouru=(TextView) findViewById(R.id.y_shouru);
		yZhichu=(TextView) findViewById(R.id.y_zhichu);
		
	    userName=MyDao.getNowUser(this);
		float[] yearShouzhi=SelectShouzhiDao.queryYear(this, userName);
		yShouru.setText("+"+yearShouzhi[0]);
		yZhichu.setText("-"+yearShouzhi[1]);
	    		
		init();
	}
	
	private void init(){
		
		list=SelectShouzhiDao.queryYearMingxi(this, userName);
		
		MyListViewAdapter adapter=new MyListViewAdapter(this, list);
		yearList.setAdapter(adapter);
		yearList.setDividerHeight(0);
		yearList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				
				dateStr=list.get(position).getDate();
				shouzhiStr=list.get(position).getShouzhi();
				qianbaoStr=list.get(position).getQianbao();
				typeStr=list.get(position).getType();
				money=list.get(position).getMoney();
				beizhuStr=list.get(position).getBeizhu();
				

		    	AlertDialog.Builder alertBuilder=new AlertDialog.Builder(YearMingxiActivity.this);
		    	alertBuilder.setTitle("Please choose what you want to do");
		    	alertBuilder.setNegativeButton("Edit Detail", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(YearMingxiActivity.this, UpdateRecordActivity.class);
						intent.putExtra("date", list.get(position).getDate());
						intent.putExtra("shouzhi", list.get(position).getShouzhi());
						intent.putExtra("qianbao", list.get(position).getQianbao());
						intent.putExtra("type", list.get(position).getType());
						intent.putExtra("money", list.get(position).getMoney());
						intent.putExtra("beizhu", list.get(position).getBeizhu());
						intent.putExtra("className", "YearMingxiActivity");
						startActivity(intent);
						finish();
					}
				});
		   
		    	alertBuilder.setPositiveButton("Delete Detail", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				    	AlertDialog.Builder alertDialog=new AlertDialog.Builder(YearMingxiActivity.this);
				    	alertDialog.setTitle("Are you sure you want to delete this detail？");
				    	alertDialog.setPositiveButton("Sure", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								DeleteShouzhiDao.deleteRecord(YearMingxiActivity.this,
										userName,dateStr,shouzhiStr,qianbaoStr,typeStr,money,beizhuStr);
								DeleteShouzhiDao.updateDAmountMoney(YearMingxiActivity.this,
										userName, shouzhiStr, qianbaoStr, money);
								//刷新当前Activity
								Intent intent=new Intent(YearMingxiActivity.this, YearMingxiActivity.class);
								startActivity(intent);
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
				});
		    	alertBuilder.create();
		    	alertBuilder.show();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}


