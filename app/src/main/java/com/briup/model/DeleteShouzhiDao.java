package com.briup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DeleteShouzhiDao {

	//delete detail
	public static void deleteRecord(Context context,String userName,String dateStr,
			String shouzhiStr,String qianbaoStr,String typeStr,float money,String beizhuStr){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		String sql="delete from "+userName+"_record"
				   +" where date='"+dateStr+"' and shouzhi='"+shouzhiStr
				   +"' and qianbao='"+qianbaoStr+"' and type='"+typeStr
				   +"' and money="+money+" and beizhu='"+beizhuStr+"'";
		db.execSQL(sql);
	}
	
	//Modify the total amount when deleting details
	public static void updateDAmountMoney(Context context,String userName,String shouzhiStr,
			String qianbaoStr,float money){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		Cursor cursor=db.query("account_users", new String[]{"xianjin","chuxuka","xinyongka","zhifubao"},
				"name=?", new String[]{userName}, null, null, null);
		float xianjin=0;
		float chuxuka=0;
		float xinyongka=0;
		float zhifubao=0;
		
		while(cursor.moveToNext()){
			xianjin=cursor.getFloat(cursor.getColumnIndex("xianjin"));
			chuxuka=cursor.getFloat(cursor.getColumnIndex("chuxuka"));
			xinyongka=cursor.getFloat(cursor.getColumnIndex("xinyongka"));
			zhifubao=cursor.getFloat(cursor.getColumnIndex("zhifubao"));
	    }
		
		ContentValues values=new ContentValues();
		if("Income".equals(shouzhiStr) && "Cash".equals(qianbaoStr)){
			values.put("xianjin", xianjin-money);
		}else if("Expenditure".equals(shouzhiStr) && "Cash".equals(qianbaoStr)){
			values.put("xianjin", xianjin+money);
		}else if("Income".equals(shouzhiStr) && "Debit Card".equals(qianbaoStr)){
			values.put("chuxuka", chuxuka-money);
		}else if("Expenditure".equals(shouzhiStr) && "Debit Card".equals(qianbaoStr)){
			values.put("chuxuka", chuxuka+money);
		}else if("Income".equals(shouzhiStr) && "Credit Card".equals(qianbaoStr)){
			values.put("xinyongka", xinyongka-money);
		}else if("Expenditure".equals(shouzhiStr) && "Credit Card".equals(qianbaoStr)){
			values.put("xinyongka", xinyongka+money);
		}else if("Income".equals(shouzhiStr) && "PayPal".equals(qianbaoStr)){
			values.put("zhifubao", zhifubao-money);
		}else if("Expenditure".equals(shouzhiStr) && "PayPal".equals(qianbaoStr)){
			values.put("zhifubao", zhifubao+money);
		}
		db.update("account_users", values, "name=?", new String[]{userName});
	}
	
}
