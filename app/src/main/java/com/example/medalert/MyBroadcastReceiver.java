package com.example.medalert;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show();

        String email = intent.getStringExtra("email");
        Intent i = new Intent(context, Dashboard.class);
        i.putExtra("email",email);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notify")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("Alarm Reminders")
                .setContentText("Hey, Wake Up!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        try {
            notificationManagerCompat.notify(200, builder.build());
        } catch (SecurityException e) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

        }

        //Adding sound will add that later
//        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/"  + R.raw.wakeup_audio);
//
//        Ringtone r = RingtoneManager.getRingtone(context,sound);
//        r.play();
    }
}