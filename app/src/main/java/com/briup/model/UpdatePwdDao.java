package com.briup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UpdatePwdDao {

	//Determine whether the original password entered by the user is correct
	public static boolean isOldPwd(Context context,String name,String pwd){
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
	
	//Change user's password
	public static void updateUserPwd(Context context,String nowUser,String newPwd){
		MySQLiteOpenHelper openHelper=MyDao.getOpenHelper(context);
		SQLiteDatabase db=MyDao.getWritableDB(openHelper);
		
		ContentValues values=new ContentValues();
		values.put("pwd", newPwd);
		db.update("account_users", values, "name=?", new String[]{nowUser});
	}
	
}
