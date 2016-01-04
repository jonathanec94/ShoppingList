package com.example.nikolai.shoppinglist;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Jonathan on 21-12-2015.
 */
public class PushMsg implements Runnable {
    Context context;
    public PushMsg(Context context)
    {
     this.context = context;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(100);
            Intent intent = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Notification noti = new Notification.Builder(context)
                    .setTicker(context.getString(R.string.app_name))
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(context.getString(R.string.push_msg))
                    .setSmallIcon(R.drawable.shopping_cart)
                    .setContentIntent(pendingIntent).getNotification();
            noti.flags = Notification.FLAG_AUTO_CANCEL;
            Activity activity = (Activity) context;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(activity.NOTIFICATION_SERVICE);
           notificationManager.notify(0, noti);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
