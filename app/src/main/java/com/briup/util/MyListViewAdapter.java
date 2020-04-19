package com.briup.util;

import java.util.List;

import com.briup.bean.Record;
import com.briup.myaccountapp.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter{

	private List<Record> list;
	private Context context;
	
	public MyListViewAdapter(Context context,List<Record> list) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=View.inflate(context, R.layout.myrecordlist, null);
			viewHolder.dateTv=(TextView) convertView.findViewById(R.id.list_date);
			viewHolder.shouzhiTv=(TextView) convertView.findViewById(R.id.list_shouzhi);
			viewHolder.qianbaoTv=(TextView) convertView.findViewById(R.id.list_qianbao);
			viewHolder.typeTv=(TextView) convertView.findViewById(R.id.list_type);
			viewHolder.moneyTv=(TextView) convertView.findViewById(R.id.list_monty);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		Record record=list.get(position);
		viewHolder.dateTv.setText(record.getDate());
		viewHolder.typeTv.setText(record.getType());
		viewHolder.qianbaoTv.setText(record.getQianbao());
		viewHolder.shouzhiTv.setText(record.getShouzhi());
		if("Income".equals(record.getShouzhi())){
			viewHolder.moneyTv.setText("+"+record.getMoney());
			viewHolder.moneyTv.setTextColor(Color.rgb(139, 195, 74));
		}else if("Expenditure".equals(record.getShouzhi())){
			viewHolder.moneyTv.setText("-"+record.getMoney());
			viewHolder.moneyTv.setTextColor(Color.rgb(23, 182, 237));
		}
		return convertView;
	}

}
