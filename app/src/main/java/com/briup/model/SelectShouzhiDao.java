package com.briup.model;

import java.util.ArrayList;
import java.util.List;

import com.briup.bean.Record;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SelectShouzhiDao {

    //Check total revenue and expenditure
    public static float[] queryAmount(Activity activity) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(activity);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        String userName = MyDao.getNowUser(activity);
        float[] result = new float[2];
        Cursor cursor = db.query(userName + "_record", new String[]{"shouzhi", "money", "date"}, null, null, null, null, null);
        float shouru = 0.0f;
        float zhichu = 0.0f;
        while (cursor.moveToNext()) {
            String shouzhiStr = cursor.getString(cursor.getColumnIndex("shouzhi"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));
            if (shouzhiStr.equals("Income")) {
                shouru += money;
            } else if (shouzhiStr.equals("Expenditure")) {
                zhichu += money;
            }
        }
        result[0] = shouru;
        result[1] = zhichu;
        return result;
    }

    //Check total revenue and expenditure
    public static float[] queryToday(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String dateStr = MyDao.getCalendarStrs();

        float[] result = new float[2];
        Cursor cursor = db.query(userName + "_record", new String[]{"shouzhi", "money"},
                "date=?", new String[]{dateStr}, null, null, null);
        float shouru = 0.0f;
        float zhichu = 0.0f;
        while (cursor.moveToNext()) {
            String shouzhiStr = cursor.getString(cursor.getColumnIndex("shouzhi"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));
            if (shouzhiStr.equals("Income")) {
                shouru += money;
            } else if (shouzhiStr.equals("Expenditure")) {
                zhichu += money;
            }
        }
        result[0] = shouru;
        result[1] = zhichu;

        return result;
    }

    //Check the total revenue and expenditure this week
    public static float[] queryWeek(Context context, String userName) {

        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        float[] result = new float[2];
        String weekBegin = MyDao.queryWeekBegin();
        Log.d("data", "First Day In This Week---------" + weekBegin);

        String dateStr = MyDao.getCalendarStrs();


        Cursor cursor = db.query(userName + "_record", new String[]{"shouzhi", "money", "date"},
                "date between ? and ?", new String[]{weekBegin, dateStr}, null, null, null);
        float shouru = 0.0f;
        float zhichu = 0.0f;
        while (cursor.moveToNext()) {
            String shouzhiStr = cursor.getString(cursor.getColumnIndex("shouzhi"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));
            if (shouzhiStr.equals("Income")) {
                shouru += money;
            } else if (shouzhiStr.equals("Expenditure")) {
                zhichu += money;
            }
        }
        result[0] = shouru;
        result[1] = zhichu;

        return result;
    }

    //Check the total revenue and expenditure of this month
    public static float[] queryMonth(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String dateStr = MyDao.getCalendarStrs();
        String[] dateStrs = dateStr.split("[-]");
        String Cyear = dateStrs[0];
        String Cmonthstr = dateStrs[1];

        float[] result = new float[2];
        Cursor cursor = db.query(userName + "_record", new String[]{"shouzhi", "money"},
                "date like ?", new String[]{Cyear + "-" + Cmonthstr + "-%"}, null, null, null);
        float shouru = 0.0f;
        float zhichu = 0.0f;
        while (cursor.moveToNext()) {
            String shouzhiStr = cursor.getString(cursor.getColumnIndex("shouzhi"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));
            if (shouzhiStr.equals("Income")) {
                shouru += money;
            } else if (shouzhiStr.equals("Expenditure")) {
                zhichu += money;
            }
        }
        result[0] = shouru;
        result[1] = zhichu;
        return result;
    }

    //Check the total revenue and expenditure of this year
    public static float[] queryYear(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String dateStr = MyDao.getCalendarStrs();
        String[] dateStrs = dateStr.split("[-]");
        String Cyear = dateStrs[0];

        float[] result = new float[2];
        Cursor cursor = db.query(userName + "_record", new String[]{"shouzhi", "money"},
                "date like ?", new String[]{Cyear + "-" + "%-%"}, null, null, null);
        float shouru = 0.0f;
        float zhichu = 0.0f;
        while (cursor.moveToNext()) {
            String shouzhiStr = cursor.getString(cursor.getColumnIndex("shouzhi"));
            double money = cursor.getDouble(cursor.getColumnIndex("money"));
            if (shouzhiStr.equals("Income")) {
                shouru += money;
            } else if (shouzhiStr.equals("Expenditure")) {
                zhichu += money;
            }
        }
        result[0] = shouru;
        result[1] = zhichu;
        return result;
    }

    //Check today's details
    public static List<Record> queryTodayMingxi(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String dateStr = MyDao.getCalendarStrs();

        Cursor cursor = db.query(userName + "_record", new String[]{"date", "type", "qianbao", "shouzhi", "money", "beizhu"},
                "date=?", new String[]{dateStr}, null, null, null);
        List<Record> list = queryMingxi(cursor);
        return list;
    }

    //Check this week's details
    public static List<Record> queryWeekMingxi(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String weekBegin = MyDao.queryWeekBegin();

        String dateStr = MyDao.getCalendarStrs();

        Cursor cursor = db.query(userName + "_record", new String[]{"date", "shouzhi", "qianbao", "type", "money", "beizhu"},
                "date between ? and ?", new String[]{weekBegin, dateStr}, null, null, "date desc");
        List<Record> list = queryMingxi(cursor);
        return list;
    }

    //Check this month's details
    public static List<Record> queryMonthMingxi(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String dateStr = MyDao.getCalendarStrs();
        String[] dateStrs = dateStr.split("[-]");
        String Cyear = dateStrs[0];
        String Cmonthstr = dateStrs[1];

        Cursor cursor = db.query(userName + "_record", new String[]{"date", "shouzhi", "qianbao", "type", "money", "beizhu"},
                "date like ?", new String[]{Cyear + "-" + Cmonthstr + "-%"}, null, null, "date desc");
        List<Record> list = queryMingxi(cursor);
        return list;
    }

    //Check the details of this year
    public static List<Record> queryYearMingxi(Context context, String userName) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String dateStr = MyDao.getCalendarStrs();
        String[] dateStrs = dateStr.split("[-]");
        String Cyear = dateStrs[0];

        Cursor cursor = db.query(userName + "_record", new String[]{"date", "shouzhi", "qianbao", "type", "money", "beizhu"},
                "date like ?", new String[]{Cyear + "-%-%"}, null, null, "date desc");
        List<Record> list = queryMingxi(cursor);
        return list;
    }

    //Return detailed results
    public static List<Record> queryMingxi(Cursor cursor) {
        List<Record> list = new ArrayList<Record>();
        while (cursor.moveToNext()) {
            String dateStr = cursor.getString(cursor.getColumnIndex("date"));
            String shouzhiStr = cursor.getString(cursor.getColumnIndex("shouzhi"));
            String qianbaoStr = cursor.getString(cursor.getColumnIndex("qianbao"));
            String typeStr = cursor.getString(cursor.getColumnIndex("type"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String beizhuStr = cursor.getString(cursor.getColumnIndex("beizhu"));
            Record record = new Record();
            record.setDate(dateStr);
            record.setShouzhi(shouzhiStr);
            record.setQianbao(qianbaoStr);
            record.setType(typeStr);
            record.setMoney(money);
            record.setBeizhu(beizhuStr);
            list.add(record);
        }
        return list;
    }


}
