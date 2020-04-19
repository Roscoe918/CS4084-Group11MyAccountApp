package com.briup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RegisterDao {

    //Check if the account has been registered in the database
    public static boolean isUserExist(Context context, String name) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = MyDao.getReadableDB(openHelper);

        Cursor cursor = db.query("account_users", null, "name=?", new String[]{name}, null, null, null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    //Add users to database tables
    public static void addUser(Context context, String name, String pwd) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = MyDao.getWritableDB(openHelper);

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("pwd", pwd);
        values.put("xianjin", 0.00f);
        values.put("chuxuka", 0.00f);
        values.put("xinyongka", 0.00f);
        values.put("zhifubao", 0.00f);
        db.insert("account_users", null, values);
    }

    //Create user's own income and expenditure information table
    public static void createUserTable(Context context, String name) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = MyDao.getWritableDB(openHelper);

        String sql = "create table " + name + "_record(_id integer primary key autoincrement,"
                + "shouzhi text,money real,type text,qianbao text,date text,beizhu text)";
        db.execSQL(sql);
    }

}
