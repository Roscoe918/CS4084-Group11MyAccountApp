package com.briup.controller;

import java.util.Calendar;

import com.briup.myaccountapp.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

public class TixingBroadReceiver extends BroadcastReceiver {

    private NotificationManager manager;
    private Context context;
    private String type;
    private int id;
    private Calendar calendar;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        this.context = context;
        calendar = Calendar.getInstance();

        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        type = intent.getStringExtra("type");
        id = intent.getIntExtra("id", 1);

        sendNotification();
    }

    @SuppressLint("NewApi")
    private void sendNotification() {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.tixingtongzhi);
        builder.setContentText(type);
        builder.setContentTitle("Account Reminder");
        builder.setTicker(context.getResources().getString(R.string.app_name) + "Reminder");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        builder.setLargeIcon(bitmap);
        builder.setWhen(calendar.getTimeInMillis());

        Intent intent = new Intent(context, ShowTixingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", type);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setContentInfo("Reminder");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setProgress(100, 30, true);
        Notification notification = builder.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(id, notification);
    }

}
