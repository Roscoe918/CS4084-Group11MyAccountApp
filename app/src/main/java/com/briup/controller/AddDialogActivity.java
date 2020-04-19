package com.briup.controller;

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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddDialogActivity extends Activity {

    private Spinner typespinner;
    private String[] typeLabel =
            {"wage", "living expenses", "transfer", "part-time", "borrow", "bonus", "repayment", "reimbursement", "cash", "other"};

    private int[] typeImages =
            {R.drawable.shourugongzi, R.drawable.shourushenghuofei,
                    R.drawable.hongbao, R.drawable.shouruwaikuai,
                    R.drawable.shourujieru, R.drawable.shourujiangjin,
                    R.drawable.shouruhuankuan, R.drawable.shourubaoxiao,
                    R.drawable.shouruxianjin, R.drawable.qita};

    private Spinner qianbaoSpinner;
    private String[] qianbaos = {"Cash","Debit Card","Credit Card","PayPal"};
    private int[] qianbaoImages = {R.drawable.qianbaoxianjin, R.drawable.qianbaochuxuka,
            R.drawable.qianbaoxinyongka, R.drawable.qianbaozhifubao};

    private EditText moneyEt;
    private TextView dateTv;
    private EditText beizhuEt;
    private String type;
    private String qianbao;

    private int Cyear;
    private int Cmonth;
    private int Cday;
    private String Cmonthstr;
    private String Cdaystr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_dialog);

        init();
    }

    //Initialize
    public void init() {
        moneyEt = (EditText) findViewById(R.id.addmoney);
        beizhuEt = (EditText) findViewById(R.id.addbeizhu);
        dateTv = (TextView) findViewById(R.id.adddate);

        moneyEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String text = s.toString();
                if (text.contains(".")) {
                    int index = text.indexOf(".");
                    if (index + 3 < text.length()) {
                        text = text.substring(0, index + 3);
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
        String nowdate = MyDao.getCalendarStrs();
        String[] dates = nowdate.split("[-]");
        Cyear = Integer.parseInt(dates[0]);
        Cmonth = Integer.parseInt(dates[1]);
        Cday = Integer.parseInt(dates[2]);

        dateTv.setText(nowdate);
        typespinner = (Spinner) findViewById(R.id.typespinner);
        qianbaoSpinner = (Spinner) findViewById(R.id.qianbaospinner);

        //Select a custom drop-down menu for income type
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_checked_text, typeLabel) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = View.inflate(getContext(), R.layout.spinner_item_layout, null);
                ImageView image = (ImageView) view.findViewById(R.id.spinner_item_image);
                TextView label = (TextView) view.findViewById(R.id.spinner_item_label);
                label.setText(typeLabel[position]);
                image.setImageResource(typeImages[position]);

                return view;
            }
        };
        typespinner.setAdapter(adapter);
        typespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(AddDialogActivity.this, "You have chosen " + typeLabel[position],
                        Toast.LENGTH_SHORT).show();
                type = typeLabel[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        //Choose a custom drop-down menu for the wallet type
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.spinner_checked_text, qianbaos) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                // TODO Auto-generated method stub

                View view = View.inflate(getContext(), R.layout.spinner_qianbao_layout, null);
                ImageView image = (ImageView) view.findViewById(R.id.spinner_qianbao_image);
                TextView label = (TextView) view.findViewById(R.id.spinner_qianbao_label);
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
                Toast.makeText(AddDialogActivity.this, "You have Chosen " + qianbaos[position],
                        Toast.LENGTH_SHORT).show();
                qianbao = qianbaos[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    //Event processing when the OK button is clicked
    public void addshouru(View view) {
        if ("".equals(moneyEt.getText().toString())) {
            new AlertDialog.Builder(AddDialogActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.error_icon))
                    .setTitle("Accounting Failed")
                    .setMessage("Please Enter Account！")
                    .setPositiveButton("Ok", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            moneyEt.setText("");
                            beizhuEt.setText("");
                        }
                    })
                    .create().show();
        } else {
            String nowUser = MyDao.getNowUser(this);
            float money = Float.parseFloat(moneyEt.getText().toString().trim());
            String date = dateTv.getText().toString().trim();
            String beizhu = beizhuEt.getText().toString().trim();
            //Insert data into the database
            InsertShouzhiDao.insertShouruRecord(AddDialogActivity.this,
                    nowUser, money, date, type, qianbao, beizhu);
            ;

            new AlertDialog.Builder(AddDialogActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.ok))
                    .setTitle("Accounting Successful")
                    .setPositiveButton("OK", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(AddDialogActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .create().show();
        }

    }

    //Event processing when the cancel button is clicked
    public void quit(View view) {
        finish();
    }

    //Event processing of the text box after clicking the date
    public void selectDate(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int day) {
                month += 1;
                // TODO Auto-generated method stub
                if (month < 10) {
                    Cmonthstr = "0" + month;
                } else {
                    Cmonthstr = Integer.toString(month);
                }
                if (day < 10) {
                    Cdaystr = "0" + day;
                } else {
                    Cdaystr = Integer.toString(day);
                }
                dateTv.setText(year + "-" + Cmonthstr + "-" + Cdaystr);
            }
        }, Cyear, Cmonth, Cday);
        dialog.show();
    }

}
