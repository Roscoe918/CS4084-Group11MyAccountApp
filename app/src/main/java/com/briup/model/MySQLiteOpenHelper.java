package com.briup.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Create a table to store all user information
 *
 * @author Administrator
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String sql = "create table account_users(_id integer primary key autoincrement,name text,pwd text,xianjin real,chuxuka real,xinyongka real,zhifubao real)";
    public static final String sq2 = "create table message(id integer primary key autoincrement,name text,content text,time text)";

    public MySQLiteOpenHelper(Context context, String name,
                              CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(sql);
        db.execSQL(sq2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }


}
