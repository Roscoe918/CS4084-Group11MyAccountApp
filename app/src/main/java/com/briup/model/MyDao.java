package com.briup.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class MyDao {

	private static MySQLiteOpenHelper openHelper;
	private static SQLiteDatabase db;
    private static SharedPreferences sharedPreferences;
	
	public static MySQLiteOpenHelper getOpenHelper(Context context){
		openHelper=new MySQLiteOpenHelper(context, "accountInfo", null, 1);
		return openHelper;
	}
	
	public static SQLiteDatabase getReadableDB(MySQLiteOpenHelper openHelper){
		db=openHelper.getReadableDatabase();
		return db;
	}
	
	public static SQLiteDatabase getWritableDB(MySQLiteOpenHelper openHelper){
		db=openHelper.getWritableDatabase();
		return db;
	}
		
	//Get the currently logged in user
	public static String getNowUser(Activity activity){
	    sharedPreferences=activity.getSharedPreferences("loginInfo", activity.MODE_PRIVATE);
	    String userName=sharedPreferences.getString("nowUser", null);
	    return userName;
	}
			
	//Get current date
	public static String getCalendarStrs(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=df.format(new Date());
		return dateStr;
	}

	//get first day in this week
	public static String queryWeekBegin(){
		String firstDay="";
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		firstDay=df.format(calendar.getTime());
 		return firstDay;
	}
	



	

}
