package com.briup.util;

import com.briup.myaccountapp.R;
import com.briup.util.ColorBlock;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShouzhiItemViewHolder extends ViewHolder{
	
	private LinearLayout shouzhiItem;
	private ColorBlock colorBlock;
	private ImageView itemImage;
	private TextView itemNameTv;
	private TextView itemBaifenbiTv;
	private TextView itemMoneyTv;
	
	public ShouzhiItemViewHolder(View itemView) {
		super(itemView);
		// TODO Auto-generated constructor stub
		shouzhiItem=(LinearLayout) itemView.findViewById(R.id.myshouzhiitem);
		colorBlock=(ColorBlock) itemView.findViewById(R.id.itemColorView);
		itemImage=(ImageView) itemView.findViewById(R.id.itemImage);
		itemNameTv=(TextView) itemView.findViewById(R.id.itemName);
		itemBaifenbiTv=(TextView) itemView.findViewById(R.id.itembaifenbi);
		itemMoneyTv=(TextView) itemView.findViewById(R.id.itemMoney);
	}

	
	public ImageView getItemImage() {
		return itemImage;
	}


	public void setItemImage(ImageView itemImage) {
		this.itemImage = itemImage;
	}


	public LinearLayout getShouzhiItem() {
		return shouzhiItem;
	}


	public void setShouzhiItem(LinearLayout shouzhiItem) {
		this.shouzhiItem = shouzhiItem;
	}


	public ColorBlock getColorBlock() {
		return colorBlock;
	}

	public void setColorBlock(ColorBlock colorBlock) {
		this.colorBlock = colorBlock;
	}

	public TextView getItemNameTv() {
		return itemNameTv;
	}

	public void setItemNameTv(TextView itemNameTv) {
		this.itemNameTv = itemNameTv;
	}
	
	public TextView getItemBaifenbiTv() {
		return itemBaifenbiTv;
	}


	public void setItemBaifenbiTv(TextView itemBaifenbiTv) {
		this.itemBaifenbiTv = itemBaifenbiTv;
	}


	public TextView getItemMoneyTv() {
		return itemMoneyTv;
	}

	public void setItemMoneyTv(TextView itemMoneyTv) {
		this.itemMoneyTv = itemMoneyTv;
	}
	
	
}
