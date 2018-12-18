package com.hfad.slimbelly;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;


public class VacuumIntentService extends IntentService {


    public VacuumIntentService() {
        super("VacuumIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Вакуум");
        builder.setContentText("Зайдите в приложение и совершите тренировку вакуума");
        builder.setSmallIcon(R.drawable.skladka);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        Intent notifyIntent = new Intent(this, TrainingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(123456, notificationCompat);
    }
}