package com.briup.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.briup.controller.UpdateInfoActivity;
import com.briup.model.MyDao;
import com.briup.model.QueryQianbaoDao;
import com.briup.myaccountapp.R;


public class QianbaoFragment extends Fragment{

	private View view;
	private ImageButton touxiangBtn;
	
	private TextView zonghe;
	private TextView xianjin;
	private TextView chuxuka;
	private TextView xinyongka;
	private TextView zhifubao;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.qianbaofragment, container, false);

		init();
		
		selectMoney();
		
		return view;
	}

	public void init(){
		touxiangBtn=(ImageButton) view.findViewById(R.id.qianbaotouxiang);
		
		zonghe=(TextView) view.findViewById(R.id.zonghe_money);
		xianjin=(TextView) view.findViewById(R.id.xianjin_money);
		chuxuka=(TextView) view.findViewById(R.id.chuxuka_money);
		xinyongka=(TextView) view.findViewById(R.id.xinyongka_money);
		zhifubao=(TextView) view.findViewById(R.id.zhifubao_money);
				
		touxiangBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), UpdateInfoActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void selectMoney(){
		String nowUser=MyDao.getNowUser(getActivity());
		float[] qianbaos=QueryQianbaoDao.queryQianbao(view.getContext(), nowUser);

		xianjin.setText(Double.toString(qianbaos[0]));
	    chuxuka.setText(Double.toString(qianbaos[1]));
	    xinyongka.setText(Double.toString(qianbaos[2]));
	    zhifubao.setText(Double.toString(qianbaos[3]));
	    zonghe.setText(Double.toString(qianbaos[4]));		
	}
}
