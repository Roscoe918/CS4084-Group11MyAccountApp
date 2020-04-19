package com.briup.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ColorBlock extends View{

	private Paint paint;
	private Canvas canvas;
	
	public ColorBlock(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}
	
	public ColorBlock(Context context,AttributeSet attributeSet) {
		// TODO Auto-generated constructor stub
		this(context, attributeSet, 0);
	}
	
	public ColorBlock(Context context,AttributeSet attributeSet,int defStyleAttr) {
		super(context, attributeSet, defStyleAttr);
		// TODO Auto-generated constructor stub
		paint=new Paint();
		paint.setColor(Color.YELLOW);
		paint.setStyle(Paint.Style.FILL);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		this.canvas=canvas;
		
		//canvas.drawRoundRect(5, 5, 5, 5, 10, 10, paint);
		canvas.drawCircle(getMeasuredWidth()/5, getMeasuredHeight()/5, getMeasuredHeight()/5, paint);
	}
	
	public void setColor(int color){
		paint.setColor(color);
		postInvalidate();
	}
	
}
