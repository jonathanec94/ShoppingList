package com.example.nikolai.shoppinglist.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.nikolai.shoppinglist.MainActivity;
import com.example.nikolai.shoppinglist.R;
import com.example.nikolai.shoppinglist.domain.Facade;
import com.example.nikolai.shoppinglist.entity.ShoppingList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PushMsg extends Service {
    public PushMsg() {
    }

    private Handler mPeriodicEventHandler;
    private static final String TAG = "Service MSG";
    Context context = this;
    private final int PERIODIC_EVENT_TIMEOUT = 30000;

    String currentDateTimeString;
    SimpleDateFormat dateFormat;

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
            Log.i(TAG, "Service");
            ArrayList<ShoppingList> shoppingLists = Facade.getInstance().getShoppingLists();

             currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
             dateFormat = new SimpleDateFormat("dd/MM/yyyy");


            Calendar cal = Calendar.getInstance();
            try{
                cal.setTime(dateFormat.parse(currentDateTimeString.substring(0,10)));
            }
            catch (ParseException e) {

            }

            for(ShoppingList sl  :shoppingLists )
            {
                Log.i(TAG, "shopping list : "+sl.getName());
                Log.i(TAG, "shopping list : "+sl.getDato());
                Log.i(TAG, "date: "+dateFormat.format(cal.getTime()));
             if(dateFormat.format(cal.getTime()).equals(sl.getDato()))
                {
                 Log.i(TAG, "date.equals(sl.getDato())");
                 if(!Facade.getInstance().getNotification(dateFormat.format(cal.getTime())))
                 {
                     Log.i(TAG, "!Facade.getInstance().getNotification(date)");
                   Facade.getInstance().addNotification(dateFormat.format(cal.getTime()));
                     notification();
                 }
             }
            }
            //your action here
            mPeriodicEventHandler.postDelayed(doPeriodicTask, PERIODIC_EVENT_TIMEOUT);
        }
    };

    public void notification()
    {
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification noti = new Notification.Builder(context)
                .setTicker(context.getString(R.string.app_name))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.push_msg))
                .setSmallIcon(R.drawable.shopping_cart)
                .setContentIntent(pendingIntent).getNotification();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);

    }

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
