package com.example.medalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class NotificationHelper {
    private static final String CHANNEL_ID = "medalert_channel";
    private static final String CHANNEL_NAME = "MedAlert Channel";
    private static final int NOTIFICATION_ID = 1;
    private static final String WORKER_TAG = "medalert_worker";

    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("MedAlert Notifications");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void scheduleNotification(String time) {
        // Parse the time string to extract hour and minute values
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1].substring(0, 2));

        // Create a Data object to pass the time value to the worker
        Data inputData = new Data.Builder()
                .putString("time", time)
                .build();

        // Create a unique tag for each notification worker
        String workerTag = WORKER_TAG + "_" + time;

        // Create a OneTimeWorkRequest for the notification worker
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInputData(inputData)
                .addTag(workerTag)
                .setInitialDelay(calculateDelay(hour, minute), TimeUnit.MILLISECONDS)
                .build();

        // Schedule the notification worker
        WorkManager.getInstance(context).enqueue(workRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private long calculateDelay(int targetHour, int targetMinute) {
        long currentTime = System.currentTimeMillis();
        int currentHour = (int) (currentTime / (60 * 60 * 1000)) % 24;
        int currentMinute = (int) (currentTime / (60 * 1000)) % 60;

        int delayHour = targetHour - currentHour;
        int delayMinute = targetMinute - currentMinute;

        if (delayHour < 0 || (delayHour == 0 && delayMinute <= 0)) {
            delayHour += 24;
        }

        return (delayHour * 60 * 60 * 1000) + (delayMinute * 60 * 1000);
    }
}
