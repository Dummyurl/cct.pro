package com.tregix.cryptocurrencytracker.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.ScheduledJobService;
import com.tregix.cryptocurrencytracker.activities.MainActivity;
import com.tregix.cryptocurrencytracker.activities.NavigationDrawerActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getUserWatchlist;

/**
 * Created by Gohar Ali on 2/22/2018.
 */

public class Utils {

    public  static String getFormatedAmount(double amount){
        String num =  NumberFormat.getNumberInstance(Locale.US).format(amount);
        if(amount < 0.099){
            return String.format(Locale.US,"%.6f", amount);
        }
       return num;
    }

    public  static String getFormatedAmount(float amount){
        String num =  NumberFormat.getNumberInstance(Locale.US).format(amount);
        if(amount < 0.099){
            return String.format(Locale.US,"%.6f", amount);
        }
        return num;
    }

    public static String truncateNumber(double floatNumber) {
        long million = 1000000L;
        long billion = 1000000000L;
        long trillion = 1000000000000L;
        long number = Math.round(floatNumber);
        if ((number >= million) && (number < billion)) {
            float fraction = calculateFraction(number, million);
            return Float.toString(fraction) + "M";
        } else if ((number >= billion) && (number < trillion)) {
            float fraction = calculateFraction(number, billion);
            return Float.toString(fraction) + "B";
        }
        return Long.toString(number);
    }

    private static float calculateFraction(long number, long divisor) {
        long truncate = (number * 10L + (divisor / 2L)) / divisor;
        float fraction = (float) truncate * 0.10F;
        return fraction;
    }

    public static void showError(Context context){
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            alertDialog.setTitle("Error");
            alertDialog.setMessage(context.getString(R.string.no_internet));
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton(BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

            alertDialog.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void showError(Context context,String msg){
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            alertDialog.setTitle("Error");
            alertDialog.setMessage(msg);
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton(BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

            alertDialog.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setAlarm(Context context) {
        AlarmManager alarmMgr =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduledJobService.class);
        PendingIntent pi = PendingIntent.getBroadcast( context, 0, intent, 0);
          if(alarmMgr != null) {
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                AlarmManager.INTERVAL_HOUR * 2
                , pi);
      }
    }

    public static void sendNotification(Context context,String title,String message){
        Intent intent = new Intent(context, NavigationDrawerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = context.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Random random = new Random();
        int notificationId = random.nextInt();
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }

    public static boolean isFavorite(Context context,String coin){
        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);
        ArrayList<String> list =  pref.loadTabs();

        return list.contains(coin);
    }

    public static void updateFavorite(Context context,String coin){
        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);
        ArrayList<String> list =  pref.loadTabs();

        if(list.contains(coin)){
            list.remove(coin);
        }else{
            list.add(coin);
        }

        pref.storeFavorites(list);
        RetrofitUtil.createProviderAPI(CryptoApi.CCT_BASE_URL).
                getUserWatchlist(SharedPreferenceUtil.getInstance(context).getUserObj().getData().getId()).enqueue(getUserWatchlist(null));
    }

    public static int getUserId(Context context){
        if(SharedPreferenceUtil.getInstance(context).getUserObj() != null
                && SharedPreferenceUtil.getInstance(context).getUserObj().getData() != null) {
            return SharedPreferenceUtil.getInstance(context).getUserObj().getData().getId();
        }else{
            return -1;
        }
    }

    public static String longToDate(String val, String pattern, int multiplier) {
        if (val != null && !val.isEmpty()) {
            Date date = new Date(Long.parseLong(val) * multiplier);
            SimpleDateFormat df2 = new SimpleDateFormat(pattern);
            return df2.format(date);
        } else {
            return "";
        }
    }
}
