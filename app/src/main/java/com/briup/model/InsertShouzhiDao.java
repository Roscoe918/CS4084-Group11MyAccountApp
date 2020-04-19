package com.briup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class InsertShouzhiDao {

	//Insert income
	public static void insertShouruRecord(Context context,String nowUser,
			float money,String date,String type,String qianbao,String beizhu){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		ContentValues values=new ContentValues();
		values.put("shouzhi", "Income");
		values.put("money", money);
		values.put("type", type);
		values.put("qianbao", qianbao);
		values.put("date", date);
		values.put("beizhu", beizhu);
		db.insert(nowUser+"_record", null, values);
		
		ContentValues values2=new ContentValues();
		float newValue=0.0f;
		float oldValue=0.0f;
		Cursor cursor=db.query("account_users", null, "name=?", new String[]{nowUser}, null, null, null);
		if(cursor.moveToFirst()){
			if("Cash".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("xianjin"));
				newValue=oldValue+money;
				values2.put("xianjin", newValue);
			}else if("Debit Card".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("chuxuka"));
				newValue=oldValue+money;
				values2.put("chuxuka", newValue);
			}else if("Credit Card".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("xinyongka"));
				newValue=oldValue+money;
				values2.put("xinyongka", newValue);
			}else if("PayPal".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("zhifubao"));
				newValue=oldValue+money;
				values2.put("zhifubao", newValue);
			}
		}
		db.update("account_users", values2, "name=?", new String[]{nowUser});
	}
	
	//insert expenditure
	public static void insertZhichuRecord(Context context,String nowUser,
			float money,String date,String type,String qianbao,String beizhu){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		ContentValues values=new ContentValues();
		values.put("shouzhi", "Expenditure");
		values.put("money", money);
		values.put("type", type);
		values.put("qianbao", qianbao);
		values.put("date", date);
		values.put("beizhu", beizhu);
		
		ContentValues values2=new ContentValues();
		float newValue=0.0f;
		float oldValue=0.0f;
		float nowValue=money;
		Cursor cursor=db.query("account_users", null, "name=?", new String[]{nowUser}, null, null, null);
		if(cursor.moveToFirst()){
			if("Cash".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("xianjin"));
				newValue=oldValue-nowValue;
				values2.put("xianjin", newValue);
			}else if("Debit Card".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("chuxuka"));
				newValue=oldValue-nowValue;
				values2.put("chuxuka", newValue);
			}else if("Credit Card".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("xinyongka"));
				newValue=oldValue-nowValue;
				values2.put("xinyongka", newValue);
			}else if("PayPal".equals(qianbao)){
				oldValue=cursor.getFloat(cursor.getColumnIndex("zhifubao"));
				newValue=oldValue-nowValue;
				values2.put("zhifubao", newValue);
			}
		}
		
		//judge balance
/*		if(newValue>=0.0d){
		    db.insert(nowUser+"_record", null, values);
			db.update("account_users", values2, "name=?", new String[]{nowUser});
			return true;
		}else{
	        return false;
		}*/
		
	    db.insert(nowUser+"_record", null, values);
		db.update("account_users", values2, "name=?", new String[]{nowUser});
	}
	
}
