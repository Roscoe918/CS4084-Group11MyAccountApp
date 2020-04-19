package com.briup.fragment;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.briup.controller.UpdateInfoActivity;
import com.briup.model.MyDao;
import com.briup.myaccountapp.R;

public class TixingFragment extends Fragment implements OnClickListener{

	private View view;
	private ImageButton touxiangBtn;
	private RelativeLayout xinyongkatixing;
	private RelativeLayout fangzutixing;
	private RelativeLayout jizhangtixing;
	private RelativeLayout huankuantixing;
	
	private TextView xinyongkashijian;
	private TextView fangzushijian;
	private TextView jizhangshijian;
	private TextView huankuanshijian;
	
	private AlarmManager alarmManager;
	public Calendar calendar;
	private int year;
	private int month;
	private int day;
	
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private String nowUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.tixingfragment, container,false);
		
		alarmManager=((AlarmManager)getActivity().getSystemService("alarm"));
		calendar=Calendar.getInstance();
		year=calendar.YEAR;
		month=calendar.MONTH;
		day=calendar.DATE;
		
		nowUser=MyDao.getNowUser(getActivity());
		sharedPreferences=getActivity().getSharedPreferences(nowUser+"_tixing", Context.MODE_APPEND);
		editor=sharedPreferences.edit();
		
        touxiangBtn=(ImageButton) view.findViewById(R.id.tixingtouxiang);
        
		xinyongkashijian=(TextView) view.findViewById(R.id.xinyongkashezhi);
		fangzushijian=(TextView) view.findViewById(R.id.fangzushezhi);
		jizhangshijian=(TextView) view.findViewById(R.id.jizhangshezhi);
		huankuanshijian=(TextView) view.findViewById(R.id.naozhongshezhi);
		
		xinyongkatixing=(RelativeLayout) view.findViewById(R.id.tixing_xinyongka);
		fangzutixing=(RelativeLayout) view.findViewById(R.id.tixing_fangzu);
		jizhangtixing=(RelativeLayout) view.findViewById(R.id.tixing_jizhang);
		huankuantixing=(RelativeLayout) view.findViewById(R.id.tixing_huankuan);
		
		touxiangBtn.setOnClickListener(this);
		xinyongkatixing.setOnClickListener(this);
		fangzutixing.setOnClickListener(this);
		jizhangtixing.setOnClickListener(this);
		huankuantixing.setOnClickListener(this);
	
		xinyongkashijian.setText(sharedPreferences.getString("xinyongkatixing", "Setting"));
		fangzushijian.setText(sharedPreferences.getString("fangzutixing", "Setting"));
		jizhangshijian.setText(sharedPreferences.getString("jizhangtixing", "Setting"));
		huankuanshijian.setText(sharedPreferences.getString("huankuantixing", "Setting"));
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tixingtouxiang:
			Intent intent=new Intent(getActivity(), UpdateInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.tixing_xinyongka:
			showDateDialog("Credit card expires",1);
			break;
		case R.id.tixing_fangzu:
			showDateDialog("Rent expires",2);
			break;
		case R.id.tixing_jizhang:
			showDateDialog("Accounting",3);
			break;
		case R.id.tixing_huankuan:
			showDateDialog("Repayment", 4);
			break;
		default:
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void showDateDialog(final String type,final int n){
		DatePickerDialog dialog=new DatePickerDialog(view.getContext(), null, year, month, day);
		final DatePicker picker=dialog.getDatePicker();
		picker.setMinDate(calendar.getTimeInMillis());
		dialog.setTitle("Please Set "+type+"Reminder Time");
		dialog.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.YEAR, picker.getYear());
				calendar.set(Calendar.MONTH,picker.getMonth());
				calendar.set(Calendar.DATE, picker.getDayOfMonth());
				Toast.makeText(view.getContext(), type+"Reminder Time is"+DateFormat.format("yyyy-MM-dd", calendar), 0).show();
			    Intent intent=new Intent("WAKEUP");
			    if(n==1){
			    	intent.putExtra("type", "Your credit card has expired！");
			    	intent.putExtra("id", 1);
			    	xinyongkashijian.setText(DateFormat.format("yyyy-MM-dd", calendar));
			    	editor.putString("xinyongkatixing", DateFormat.format("yyyy-MM-dd", calendar).toString());
			    }else if(n==2){
			    	intent.putExtra("type", "Your rent has expired！");
			    	intent.putExtra("id", 2);
			    	fangzushijian.setText(DateFormat.format("yyyy-MM-dd", calendar));
			    	editor.putString("fangzutixing", DateFormat.format("yyyy-MM-dd", calendar).toString());
			    }else if(n==3){
			    	intent.putExtra("type", "Your billing time is up！");
			    	intent.putExtra("id", 3);
			    	jizhangshijian.setText(DateFormat.format("yyyy-MM-dd", calendar));
			    	editor.putString("jizhangtixing", DateFormat.format("yyyy-MM-dd", calendar).toString());
			    }else if(n==4){
			    	 intent.putExtra("type", "Your repayment time is up!");
			    	 intent.putExtra("id", 4);
			    	 huankuanshijian.setText(DateFormat.format("yyyy-MM-dd", calendar));
				     editor.putString("huankuantixing", DateFormat.format("yyyy-MM-dd", calendar).toString());
			    }
			    editor.commit();
				PendingIntent pIntent=PendingIntent.getBroadcast(view.getContext(), n, intent, 0);
				alarmManager.set(0, calendar.getTimeInMillis(), pIntent);
			}
		});
		dialog.show();
	}	
}
