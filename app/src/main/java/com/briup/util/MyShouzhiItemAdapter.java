package com.briup.util;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.briup.bean.ShouzhiItemDetails;
import com.briup.fragment.BaobiaoFragment;
import com.briup.myaccountapp.R;

public class MyShouzhiItemAdapter extends RecyclerView.Adapter<ViewHolder>{

	private Context context;
	private List<ShouzhiItemDetails> list;
	private int highLight=-1;
	
	public MyShouzhiItemAdapter(Context context,List<ShouzhiItemDetails> list) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		// TODO Auto-generated method stub
		if(holder instanceof ShouzhiItemViewHolder){
			if(position==highLight){
				((ShouzhiItemViewHolder)holder).getShouzhiItem()
				.setBackgroundColor(Color.rgb(196, 233, 245));
			}else{
				((ShouzhiItemViewHolder)holder).getShouzhiItem()
				.setBackgroundColor(Color.rgb(255, 255, 255));
			}
			((ShouzhiItemViewHolder)holder).getColorBlock().setColor(list.get(position).getItemColor());
			((ShouzhiItemViewHolder)holder).getItemImage().setImageResource(list.get(position).getItemImage());
			((ShouzhiItemViewHolder)holder).getItemNameTv().setText(list.get(position).getType());
			((ShouzhiItemViewHolder)holder).getItemBaifenbiTv().setText(list.get(position).getBaifenbi());
			((ShouzhiItemViewHolder)holder).getItemMoneyTv().setText(Float.toString(list.get(position).getMoney()));
			((ShouzhiItemViewHolder)holder).getShouzhiItem().setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BaobiaoFragment.pieChart.highlightValue(position, 0);
					HighlightItem(position);
				}
			});
		}
	}


	public void HighlightItem(int position){
		int temp = highLight;
		highLight = position;
		if (temp!=-1) {
			notifyItemChanged(temp);
		}
		if(position!=-1){
			notifyItemChanged(position);
		}
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.myshouzhiitem, parent,false);
		return new ShouzhiItemViewHolder(view);
	}
	

}
