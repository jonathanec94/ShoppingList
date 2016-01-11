package com.example.nikolai.shoppinglist.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.nikolai.shoppinglist.MainActivity;
import com.example.nikolai.shoppinglist.R;

public class PushMsg extends Service {
    public PushMsg() {
    }

    private Handler mPeriodicEventHandler;
    private static final String TAG = "Service MSG";
    Context context = this;
    private final int PERIODIC_EVENT_TIMEOUT = 5000;

    @Override
    public void onCreate() {
        super.onCreate();

        mPeriodicEventHandler = new Handler();
        mPeriodicEventHandler.postDelayed(doPeriodicTask, PERIODIC_EVENT_TIMEOUT);
    }

    private Runnable doPeriodicTask = new Runnable()
    {
        public void run()
        {
            Log.i(TAG, "test test test");
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
            //your action here
            mPeriodicEventHandler.postDelayed(doPeriodicTask, PERIODIC_EVENT_TIMEOUT);
        }
    };

    @Override
    public void onDestroy() {

        mPeriodicEventHandler.removeCallbacks(doPeriodicTask);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
