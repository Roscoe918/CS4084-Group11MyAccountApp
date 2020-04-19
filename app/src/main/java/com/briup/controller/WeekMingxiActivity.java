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
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WeekMingxiActivity extends Activity {
   
	private ListView weekList;
	private List<Record> list;
	private TextView wShouru;
	private TextView wZhichu;
	
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
		setContentView(R.layout.activity_week_mingxi);
		
		weekList=(ListView) findViewById(R.id.week_record);
		/*list= new ArrayList<Record>();*/
		wShouru=(TextView) findViewById(R.id.w_shouru);
		wZhichu=(TextView) findViewById(R.id.w_zhichu);
	    userName=MyDao.getNowUser(this);
	    		
		float[] weekShouzhi=SelectShouzhiDao.queryWeek(this, userName);
		wShouru.setText("+"+weekShouzhi[0]);
		wZhichu.setText("-"+weekShouzhi[1]);
		
		init();
	}
	
	//Check this week's revenue and expenditure
	private void init(){

        list=SelectShouzhiDao.queryWeekMingxi(this, userName);

		MyListViewAdapter adapter=new MyListViewAdapter(this, list);
		weekList.setAdapter(adapter);
		weekList.setDividerHeight(0);
		weekList.setOnItemClickListener(new OnItemClickListener() {

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
				

		    	AlertDialog.Builder alertBuilder=new AlertDialog.Builder(WeekMingxiActivity.this);
		    	alertBuilder.setTitle("Please choose what you want to do");
		    	alertBuilder.setNegativeButton("Edit details", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(WeekMingxiActivity.this, UpdateRecordActivity.class);
						intent.putExtra("date", list.get(position).getDate());
						intent.putExtra("shouzhi", list.get(position).getShouzhi());
						intent.putExtra("qianbao", list.get(position).getQianbao());
						intent.putExtra("type", list.get(position).getType());
						intent.putExtra("money", list.get(position).getMoney());
						intent.putExtra("beizhu", list.get(position).getBeizhu());
						intent.putExtra("className", "WeekMingxiActivity");
						startActivity(intent);
						finish();
					}
				});
		   
		    	alertBuilder.setPositiveButton("Delete details", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				    	AlertDialog.Builder alertDialog=new AlertDialog.Builder(WeekMingxiActivity.this);
				    	alertDialog.setTitle("Are you sure you want to delete this detail？");
				    	alertDialog.setPositiveButton("Sure", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								DeleteShouzhiDao.deleteRecord(WeekMingxiActivity.this,
										userName,dateStr,shouzhiStr,qianbaoStr,typeStr,money,beizhuStr);
								DeleteShouzhiDao.updateDAmountMoney(WeekMingxiActivity.this,
										userName, shouzhiStr, qianbaoStr, money);
								Intent intent=new Intent(WeekMingxiActivity.this, WeekMingxiActivity.class);
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
