package com.briup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.briup.bean.Message;
import com.briup.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Consumption sharing
 */
public class MessageDao {

    //search consumption sharing List
    public static List<Message> queryMessageList(Context context) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("message", new String[]{/*"money", */ "name", "content", "time"},
                null, null, null, null, "time desc");

        List<Message> list = new ArrayList<Message>();
        while (cursor.moveToNext()) {
//            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String dateStr = cursor.getString(cursor.getColumnIndex("time"));
            Message message = new Message();
            message.setDate(dateStr);
//            message.setMoney(money);
            message.setContent(content);
            message.setName(name);
            list.add(message);
        }
        return list;
    }

    //insert message
    public static void insertMessage(Context context, String content, String name) {
        MySQLiteOpenHelper openHelper = MyDao.getOpenHelper(context);
        SQLiteDatabase db = MyDao.getWritableDB(openHelper);
        ContentValues values = new ContentValues();
        values.put("time", DateUtils.convertToString(System.currentTimeMillis()));
        values.put("content", content);
        values.put("name", name);
        db.insert("message", null, values);
    }

}
