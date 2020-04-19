package com.briup.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginDao {

	//Determine whether the user exists when logging in
	public static boolean isUserExist(Context context,String name,String pwd){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getReadableDB(openHelper);
		
		Cursor cursor=db.query("account_users", null, "name=? and pwd=?",
				   new String[]{name,pwd}, null, null, null);
		if(cursor.getCount()==0){
			return false;
		}else{
			return true;
		}
	}
}
