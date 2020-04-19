package com.briup.controller;

import java.util.Calendar;

import com.briup.model.MyDao;
import com.briup.model.UpdateShouzhiDao;
import com.briup.myaccountapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateRecordActivity extends Activity {

	private Spinner typespinner;
	private String[] shourutypeLabel=
		{"wage","living expenses","transfer","part-time","borrow","bonus","repayment","reimbursement","cash","other"};
	private String[] zhichutypeLabel=
		{"rent","food","transportation","clothing","Daily necessities","Phone bill","transfer","skin care","refund","other"};
	private int[] shourutypeImages=
		   {R.drawable.shourugongzi,R.drawable.shourushenghuofei,
			R.drawable.hongbao,R.drawable.shouruwaikuai,
			R.drawable.shourujieru,R.drawable.shourujiangjin,
			R.drawable.shouruhuankuan,R.drawable.shourubaoxiao,
			R.drawable.shouruxianjin,R.drawable.qita};
	private int[] zhichutypeImages=
		   {R.drawable.zhichufangzu,R.drawable.zhichucanyin,
			R.drawable.zhichujiaotong,R.drawable.zhichuyifu,
			R.drawable.zhichushenghuo,R.drawable.zhichuhuafei,
			R.drawable.hongbao,R.drawable.zhichuhufu,
			R.drawable.zhichuhuanqian,R.drawable.qita};
	
	private Spinner qianbaoSpinner;
	private String[] qianbaos={"Cash","Debit Card","Credit Card","PayPal"};
	private int[] qianbaoImages={R.drawable.qianbaoxianjin,R.drawable.qianbaochuxuka,
			R.drawable.qianbaoxinyongka,R.drawable.qianbaozhifubao};
	
	private EditText moneyEt;
	private TextView dateTv;
	private EditText beizhuEt;
	private String type;
	private String qianbao;
	
	private String dateStr;
	private String shouzhiStr;
	private String qianbaoStr;
	private String typeStr;
	private float money;
	private String beizhuStr;
		
	private Calendar calendar=Calendar.getInstance();
	private int Cyear;
	private int Cmonth;
	private int Cday;
	private String Cmonthstr;
	private String Cdaystr;
	
	private RadioGroup shouzhiGroup;
	private RadioButton shouruRadio;
	private RadioButton zhichuRadio;
	
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> adapter2;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_update_record);
				
		init();
	}

	public void initWidget(){
		moneyEt=(EditText) findViewById(R.id.updatemoney);
		beizhuEt=(EditText) findViewById(R.id.updatebeizhu);
		dateTv=(TextView) findViewById(R.id.updatedate);
		typespinner=(Spinner) findViewById(R.id.updatetypespinner);
		qianbaoSpinner=(Spinner) findViewById(R.id.updateqianbaospinner);
		shouzhiGroup=(RadioGroup) findViewById(R.id.shouzhiGroup);
		shouruRadio=(RadioButton) findViewById(R.id.danxuan_shouru);
		zhichuRadio=(RadioButton) findViewById(R.id.danxuan_zhichu);
		
moneyEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String text=s.toString();
				if(text.contains(".")){
					int index=text.indexOf(".");
					if(index+3<text.length()){
						text=text.substring(0, index+3);
						moneyEt.setText(text);
						moneyEt.setSelection(text.length());
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void initWidgetInfo(){
		Intent intent=getIntent();
		dateStr=intent.getStringExtra("date");
		shouzhiStr=intent.getStringExtra("shouzhi");
		qianbaoStr=intent.getStringExtra("qianbao");
		typeStr=intent.getStringExtra("type");
		money=intent.getFloatExtra("money", 0.0f);
		beizhuStr=intent.getStringExtra("beizhu");
					
		moneyEt.setText(Double.toString(money));
		dateTv.setText(dateStr);
		beizhuEt.setText(beizhuStr);
		if("Income".equals(shouzhiStr)){
			shouruRadio.setChecked(true);
			zhichuRadio.setChecked(false);
		}else if("Expenditure".equals(shouzhiStr)){
			zhichuRadio.setChecked(true);
			shouruRadio.setChecked(false);
		}
		
		//Initialize the wallet
		if("Cash".equals(qianbaoStr)){
			qianbaoSpinner.setSelection(0);
		}else if("Debit Card".equals(qianbaoStr)){
			qianbaoSpinner.setSelection(1);
		}else if("Credit Card".equals(qianbaoStr)){
			qianbaoSpinner.setSelection(2);
		}else if("PayPal".equals(qianbaoStr)){
			qianbaoSpinner.setSelection(3);
		}
		//Initial consumption type
		if("wage".equals(typeStr)){
			typespinner.setSelection(0);
		}else if("living expenses".equals(typeStr)){
			typespinner.setSelection(1);
		}else if("Income".equals(shouzhiStr) && "transfer".equals(typeStr)){
			typespinner.setSelection(2);
		}else if("part-time".equals(typeStr)){
			typespinner.setSelection(3);
		}else if("borrow".equals(typeStr)){
			typespinner.setSelection(4);
		}else if("bonus".equals(typeStr)){
			typespinner.setSelection(5);
		}else if("repayment".equals(typeStr)){
			typespinner.setSelection(6);
		}else if("reimbursement".equals(typeStr)){
			typespinner.setSelection(7);
		}else if("cash".equals(typeStr)){
			typespinner.setSelection(8);
		}else if("other".equals(typeStr)){
			typespinner.setSelection(9);
		}
		
		if("rent".equals(typeStr)){
			typespinner.setSelection(0);
		}else if("food".equals(typeStr)){
			typespinner.setSelection(1);
		}else if("transportation".equals(typeStr)){
			typespinner.setSelection(2);
		}else if("clothing".equals(typeStr)){
			typespinner.setSelection(3);
		}else if("Daily necessities".equals(typeStr)){
			typespinner.setSelection(4);
		}else if("Phone bill".equals(typeStr)){
			typespinner.setSelection(5);
		}else if("Expenditure".equals(shouzhiStr) &&  "transfer".equals(typeStr)){
			typespinner.setSelection(6);
		}else if("skin care".equals(typeStr)){
			typespinner.setSelection(7);
		}else if("refund".equals(typeStr)){
			typespinner.setSelection(8);
		}else if("other".equals(shouzhiStr)){
			typespinner.setSelection(9);
		}
	}
	
	//Initialize
		public void init(){

			initWidget();
			
			calendar.add(Calendar.MONTH, 1);
			Cyear=calendar.get(Calendar.YEAR);
			Cmonth=calendar.get(Calendar.MONTH);
			Cday=calendar.get(Calendar.DATE);
			
			if(Cmonth<10){
				Cmonthstr="0"+Cmonth;
			}else{
				Cmonthstr=Integer.toString(Cmonth);
			}
			if(Cday<10){
				Cdaystr="0"+Cday;
			}else{
				Cdaystr=Integer.toString(Cday);
			}
			
			shouzhiGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					switch (checkedId) {
					case R.id.danxuan_shouru:

						adapter=new ArrayAdapter<String>(UpdateRecordActivity.this,
								R.layout.spinner_checked_text,shourutypeLabel){
							@Override
							public View getDropDownView(int position, View convertView,
									ViewGroup parent) {
								// TODO Auto-generated method stub
								View view=View.inflate(getContext(), R.layout.spinner_item_layout, null);
								ImageView image=(ImageView) view.findViewById(R.id.spinner_item_image);
								TextView label=(TextView) view.findViewById(R.id.spinner_item_label);
								image.setImageResource(shourutypeImages[position]);
								label.setText(shourutypeLabel[position]);
								return view;
							}
						};
						typespinner.setAdapter(adapter);
						typespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent, View view,
									int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(UpdateRecordActivity.this, "You have chosen "+shourutypeLabel[position],
										Toast.LENGTH_SHORT).show();
								type=shourutypeLabel[position];
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								// TODO Auto-generated method stub
								
							}
						});
						typespinner.refreshDrawableState();
						break;
					case R.id.danxuan_zhichu:
						//Select a custom drop-down menu for income type
						adapter=new ArrayAdapter<String>(UpdateRecordActivity.this,
								R.layout.spinner_checked_text,zhichutypeLabel){
							@Override
							public View getDropDownView(int position, View convertView,
									ViewGroup parent) {
								// TODO Auto-generated method stub
								View view=View.inflate(getContext(), R.layout.spinner_item_layout, null);
								ImageView image=(ImageView) view.findViewById(R.id.spinner_item_image);
								TextView label=(TextView) view.findViewById(R.id.spinner_item_label);
								image.setImageResource(zhichutypeImages[position]);
								label.setText(zhichutypeLabel[position]);
								return view;
							}
						};
						typespinner.setAdapter(adapter);
						typespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent, View view,
									int position, long id) {
								// TODO Auto-generated method stub
								Toast.makeText(UpdateRecordActivity.this, "You have chosen "+zhichutypeLabel[position],
										Toast.LENGTH_SHORT).show();
								type=zhichutypeLabel[position];
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								// TODO Auto-generated method stub

							}
						});
						typespinner.refreshDrawableState();
						break;
					default:
						break;
					}
				}
			});
						
			//Choose a custom drop-down menu for the wallet type
			adapter2=new ArrayAdapter<String>(this,
					R.layout.spinner_checked_text,qianbaos){
				@Override
				public View getDropDownView(int position, View convertView,
						ViewGroup parent) {
					// TODO Auto-generated method stub
					
					View view=View.inflate(getContext(), R.layout.spinner_qianbao_layout, null);
					ImageView image=(ImageView) view.findViewById(R.id.spinner_qianbao_image);
					TextView label=(TextView) view.findViewById(R.id.spinner_qianbao_label);
					label.setText(qianbaos[position]);
					image.setImageResource(qianbaoImages[position]);
					return view;
				}
			};
			qianbaoSpinner.setAdapter(adapter2);
			qianbaoSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(UpdateRecordActivity.this, "You have chosen"+qianbaos[position],
							Toast.LENGTH_SHORT).show();
					qianbao=qianbaos[position];
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});

			initWidgetInfo();;
			
		}
	
		//Event processing when the text box after the date is clicked
	    public void selectDate(View view){
			DatePickerDialog dialog=new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month,
							int day) {
				// TODO Auto-generated method stub
				month=month+1;
				if(month<10){
					Cmonthstr="0"+month;
				}else{
					Cmonthstr=Integer.toString(month);
				}
				if(day<10){
					Cdaystr="0"+day;
				}else{
					Cdaystr=Integer.toString(day);
				}
				dateTv.setText(year+"-"+Cmonthstr+"-"+Cdaystr);
				}			
			}, Cyear, Cmonth, Cday);
				dialog.show();
		}
	   
	//Click the OK button to modify the details
	public void updateRecord(View view){
		
		if("".equals(moneyEt.getText().toString())){
			new AlertDialog.Builder(UpdateRecordActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.error_icon))
			.setTitle("Change Detail Failed")
			.setMessage("Please enter the account！")
			.setPositiveButton("ok", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					moneyEt.setText("");
					beizhuEt.setText("");
				}
			})
			.create().show();
		}else{
			String nowUser=MyDao.getNowUser(this);
			float newmoney= Float.parseFloat(moneyEt.getText().toString().trim());
			String newdate=dateTv.getText().toString().trim();
			String newbeizhu=beizhuEt.getText().toString().trim();
			//修改数据库表

			if(shouruRadio.isChecked()){
				UpdateShouzhiDao.updateUAmountMoney(UpdateRecordActivity.this, nowUser,
						shouzhiStr, qianbaoStr, money, "Income", qianbao, newmoney);
				UpdateShouzhiDao.updateRecord(UpdateRecordActivity.this,
						nowUser, newmoney, "Income", newdate, type, qianbao,newbeizhu,
						money,shouzhiStr,dateStr,typeStr,qianbaoStr,beizhuStr);

			}else if(zhichuRadio.isChecked()){
				UpdateShouzhiDao.updateUAmountMoney(UpdateRecordActivity.this, nowUser,
						shouzhiStr, qianbaoStr, money, "Expenditure", qianbao, newmoney);
				UpdateShouzhiDao.updateRecord(UpdateRecordActivity.this,
							nowUser, newmoney, "Expenditure", newdate, type, qianbao,newbeizhu,
							money,shouzhiStr,dateStr,typeStr,qianbaoStr,beizhuStr);
			}
		}
		new AlertDialog.Builder(UpdateRecordActivity.this)
		.setIcon(getResources().getDrawable(R.drawable.ok))
		.setTitle("Change Detail Successful")
		.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String className=getIntent().getStringExtra("className");
				Intent intent=null;
				if("TodayMingxiActivity".equals(className)){
					intent=new Intent(UpdateRecordActivity.this, TodayMingxiActivity.class);
				}else if("WeekMingxiActivity".equals(className)){
					intent=new Intent(UpdateRecordActivity.this, WeekMingxiActivity.class);
				}else if("MonthMingxiActivity".equals(className)){
					intent=new Intent(UpdateRecordActivity.this, MonthMingxiActivity.class);
				}else if("YearMingxiActivity".equals(className)){
					intent=new Intent(UpdateRecordActivity.this, YearMingxiActivity.class);
				}
				startActivity(intent);
				finish();
			}
		})
		.create().show();
		
	}
	
	//Click the Cancel button to cancel the modification of the details
	public void quit(View view){
		finish();
	}
}
