package com.briup.model;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QueryBaobiaoDao {

	//Query the percentage of each item in Income
	public static float[] queryShouruBili(Activity activity){
		
		float[] shouruBili=new float[10];
		float[] shouruxiang=new float[10];
		
		float[] zongshouzhi=SelectShouzhiDao.queryAmount(activity);
		float zongshouru=zongshouzhi[0];
		
		shouruxiang[0]=queryShouruItem(activity, "Income", "wage");
		shouruxiang[1]=queryShouruItem(activity, "Income", "living expenses");
		shouruxiang[2]=queryShouruItem(activity, "Income", "transfer");
		shouruxiang[3]=queryShouruItem(activity, "Income", "parttime");
		shouruxiang[4]=queryShouruItem(activity, "Income", "borrow");
		shouruxiang[5]=queryShouruItem(activity, "Income", "bonus");
		shouruxiang[6]=queryShouruItem(activity, "Income", "repayment");
		shouruxiang[7]=queryShouruItem(activity, "Income", "reimbursement");
		shouruxiang[8]=queryShouruItem(activity, "Income", "cash");
		shouruxiang[9]=queryShouruItem(activity, "Income", "other");
		
		if(zongshouru!=0){
			shouruBili[0]=shouruxiang[0]/zongshouru;
			shouruBili[1]=shouruxiang[1]/zongshouru;
			shouruBili[2]=shouruxiang[2]/zongshouru;
			shouruBili[3]=shouruxiang[3]/zongshouru;
			shouruBili[4]=shouruxiang[4]/zongshouru;
			shouruBili[5]=shouruxiang[5]/zongshouru;
			shouruBili[6]=shouruxiang[6]/zongshouru;
			shouruBili[7]=shouruxiang[7]/zongshouru;
			shouruBili[8]=shouruxiang[8]/zongshouru;
			shouruBili[9]=shouruxiang[9]/zongshouru;
		}
		
		return shouruBili;	   
	}
	
	//Query the percentage of Expenditure items
	public static float[] queryZhichuBili(Activity activity){
		
		float[] zhichuBili=new float[10];
		float[] zhichuxiang=new float[10];
		
		float[] zongshouzhi=SelectShouzhiDao.queryAmount(activity);
		float zongzhichu=zongshouzhi[1];
		
		zhichuxiang[0]=queryShouruItem(activity, "Expenditure", "rent");
		zhichuxiang[1]=queryShouruItem(activity, "Expenditure", "food");
		zhichuxiang[2]=queryShouruItem(activity, "Expenditure", "transportation");
		zhichuxiang[3]=queryShouruItem(activity, "Expenditure", "clothing");
		zhichuxiang[4]=queryShouruItem(activity, "Expenditure", "Daily necessities");
		zhichuxiang[5]=queryShouruItem(activity, "Expenditure", "Phone bill");
		zhichuxiang[6]=queryShouruItem(activity, "Expenditure", "transfer");
		zhichuxiang[7]=queryShouruItem(activity, "Expenditure", "skin care");
		zhichuxiang[8]=queryShouruItem(activity, "Expenditure", "refund");
		zhichuxiang[9]=queryShouruItem(activity, "Expenditure", "other");
		
		if(zongzhichu!=0){
			zhichuBili[0]=zhichuxiang[0]/zongzhichu;
			zhichuBili[1]=zhichuxiang[1]/zongzhichu;
			zhichuBili[2]=zhichuxiang[2]/zongzhichu;
			zhichuBili[3]=zhichuxiang[3]/zongzhichu;
			zhichuBili[4]=zhichuxiang[4]/zongzhichu;
			zhichuBili[5]=zhichuxiang[5]/zongzhichu;
			zhichuBili[6]=zhichuxiang[6]/zongzhichu;
			zhichuBili[7]=zhichuxiang[7]/zongzhichu;
			zhichuBili[8]=zhichuxiang[8]/zongzhichu;
			zhichuBili[9]=zhichuxiang[9]/zongzhichu;
		}
		
		return zhichuBili;	   
	}
	
	//Query the amount of all input items
	public static float[] queryShouruItemMoney(Activity activity){
		
		float[] shouruxiang=new float[10];
		
		shouruxiang[0]=queryShouruItem(activity, "Income", "wage");
		shouruxiang[1]=queryShouruItem(activity, "Income", "living expenses");
		shouruxiang[2]=queryShouruItem(activity, "Income", "transfer");
		shouruxiang[3]=queryShouruItem(activity, "Income", "part-time");
		shouruxiang[4]=queryShouruItem(activity, "Income", "borrow");
		shouruxiang[5]=queryShouruItem(activity, "Income", "bonus");
		shouruxiang[6]=queryShouruItem(activity, "Income", "repayment");
		shouruxiang[7]=queryShouruItem(activity, "Income", "reimbursement");
		shouruxiang[8]=queryShouruItem(activity, "Income", "cash");
		shouruxiang[9]=queryShouruItem(activity, "Income", "other");
		return shouruxiang;
	}
	
	//Query the amount of all Expenditure items
	public static float[] queryZhichuItemMoney(Activity activity){
		
		float[] zhichuxiang=new float[10];
		
		zhichuxiang[0]=queryShouruItem(activity, "Expenditure", "rent");
		zhichuxiang[1]=queryShouruItem(activity, "Expenditure", "food");
		zhichuxiang[2]=queryShouruItem(activity, "Expenditure", "transportation");
		zhichuxiang[3]=queryShouruItem(activity, "Expenditure", "clothing");
		zhichuxiang[4]=queryShouruItem(activity, "Expenditure", "Daily necessities");
		zhichuxiang[5]=queryShouruItem(activity, "Expenditure", "Phone bill");
		zhichuxiang[6]=queryShouruItem(activity, "Expenditure", "transfer");
		zhichuxiang[7]=queryShouruItem(activity, "Expenditure", "skin care");
		zhichuxiang[8]=queryShouruItem(activity, "Expenditure", "refund");
		zhichuxiang[9]=queryShouruItem(activity, "Expenditure", "other");
		return zhichuxiang;
	}
		
	//Query detailed items
	public static float queryShouruItem(Activity activity,String shouzhi,String type){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(activity);
		SQLiteDatabase db=openHelper.getReadableDatabase();
		String nowUser=MyDao.getNowUser(activity);
		float result=0;
		float money=0;
		Cursor cursor=db.query(nowUser+"_record", new String[]{"money"},
				"shouzhi=? and type=?", new String[]{shouzhi,type}, null, null, null);
		while(cursor.moveToNext()){
			money=cursor.getFloat(cursor.getColumnIndex("money"));
			result+=money;
		}
		return result;
	}
}
