package com.briup.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QueryQianbaoDao {

	//Query user's wallet
	public static float[] queryQianbao(Context context,String nowUser){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getReadableDB(openHelper);
		
		float[] result=new float[5];
		Cursor cursor=db.query("account_users", null,
				"name=?", new String[]{nowUser}, null, null, null);
		if(cursor!=null){
			/*String[] columnsName=cursor.getColumnNames();
			for(String name:columnsName){
				System.out.println(name);
			}*/
			//getColumnIndex(String columnName) 
	        //Returns the zero-based index for the given column name, 
			//or -1 if the column doesn't exist.

		    if(cursor.moveToFirst()){
				result[0]=cursor.getFloat(cursor.getColumnIndex("xianjin"));
				result[1]=cursor.getFloat(cursor.getColumnIndex("chuxuka"));
				result[2]=cursor.getFloat(cursor.getColumnIndex("xinyongka"));
				result[3]=cursor.getFloat(cursor.getColumnIndex("zhifubao"));
				result[4]=result[0]+result[1]+result[2]+result[3];
		    }
		 }
		    return result;
	}
}
