package com.briup.controller;

import java.util.Calendar;

import com.briup.model.InsertShouzhiDao;
import com.briup.model.MyDao;
import com.briup.myaccountapp.R;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class DecDialogActivity extends Activity {

	private Spinner spinner;
	private String[] types={"rent","food","transportation","clothing","Daily necessities","Phone bill","transfer","skin care","refund","other"};
	private int[] typeImages=
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
	private String decType;
	private String decQianbao;
	private EditText decbeizhu;
		
	private Calendar calendar=Calendar.getInstance();
	private int Cyear;
	private int Cmonth;
	private int Cday;
	private String Cmonthstr;
	private String Cdaystr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dec_dialog);
		
		init();

	}

	//Initialize operation
	public void init(){
		spinner=(Spinner) findViewById(R.id.dectypespinner);
		qianbaoSpinner=(Spinner) findViewById(R.id.decqianbaospinner);
		
		Cyear=calendar.get(Calendar.YEAR);
		calendar.add(Calendar.MONTH, 1);
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
		
		dateTv=(TextView) findViewById(R.id.decdate);
		dateTv.setText(Cyear+"-"+Cmonthstr+"-"+Cdaystr);
		
		moneyEt=(EditText) findViewById(R.id.decmoney);
		decbeizhu=(EditText) findViewById(R.id.decbeizhu);
				
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

		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
				R.layout.spinner_checked_text,types){
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				// TODO Auto-generated method stub
				View view=View.inflate(getContext(), R.layout.spinner_item_layout, null);
				ImageView image=(ImageView) view.findViewById(R.id.spinner_item_image);
				TextView label=(TextView) view.findViewById(R.id.spinner_item_label);
				label.setText(types[position]);
				image.setImageResource(typeImages[position]);
				
				return view;
			}
		};
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(DecDialogActivity.this, "You have Chosen"+types[position],
						Toast.LENGTH_SHORT).show();
			    decType=types[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,
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
				Toast.makeText(DecDialogActivity.this, "You have Chosen "+qianbaos[position],
						Toast.LENGTH_SHORT).show();
				decQianbao=qianbaos[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
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
    
    //Click the OK button for event handling
    public void decMoney(View view){
    	//If no amount is entered, a pop-up box prompts
    	if("".equals(moneyEt.getText().toString())){
			new AlertDialog.Builder(DecDialogActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.error_icon))
			.setTitle("Accounting Failed")
			.setMessage("Please enter account")
			.setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					moneyEt.setText("");
					decbeizhu.setText("");
				}
			})
			.create().show();
    	}else{
    		String nowUser=MyDao.getNowUser(this);
    		float money= Float.parseFloat(moneyEt.getText().toString().trim());
			String date=dateTv.getText().toString().trim();
			String beizhu=decbeizhu.getText().toString().trim();
			
			//Make new expenditures after judging the balance of the wallet
/*    		boolean result=MyUtils.insertZhichuRecord(this,
    				nowUser, money, date, decType, decQianbao, beizhu);*/
			
			InsertShouzhiDao.insertZhichuRecord(this,
    				nowUser, money, date, decType, decQianbao, beizhu);
/*    		if(result==true){*/
    			new AlertDialog.Builder(DecDialogActivity.this)
    			.setIcon(getResources().getDrawable(R.drawable.ok))
    			.setTitle("Accounting Successful")
    			.setPositiveButton("OK", new OnClickListener() {
    				
    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub
    					Intent intent=new Intent(DecDialogActivity.this, MainActivity.class);
    					startActivity(intent);
    					finish();
    				}
    			})
    			.create().show();
 /*   		}else{
    			new AlertDialog.Builder(DecDialogActivity.this)
    			.setIcon(getResources().getDrawable(R.drawable.error_icon))
    			.setTitle("Accounting Failed ")
    			.setMessage("Insufficient balance!")
    			.setPositiveButton("OK", new OnClickListener() {

    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub

    				}
    			})
    			.create().show();
    		}*/
    	}
    }
    
    //Click the cancel button for event handling
	public void quit(View view){
		finish();
	}
		
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
