package com.hfad.slimbelly.intro;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.hfad.slimbelly.DB;
import com.hfad.slimbelly.MainActivity;
import com.hfad.slimbelly.R;
import com.hfad.slimbelly.TrainingActivity;

import java.util.Calendar;

public class FourthActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        Calendar calendar = Calendar.getInstance();

        DB dbHelper = new DB(FourthActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("REGIME", new String[]{"HOUR", "MINUTE"},
                "NUMBER>0", null, null, null, null);


        if (cursor.moveToFirst()) {
            int hour = cursor.getInt(0);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            int minute = cursor.getInt(1);
            calendar.set(Calendar.MINUTE, minute);

            calendar.add(Calendar.HOUR_OF_DAY, -3);
            calendar.add(Calendar.MINUTE, 10);

            TextView textView = (TextView) findViewById(R.id.zaryadka);
            textView.setText(DateUtils.formatDateTime(this, calendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }



    }

    public void onBtn4(View view) {
        Intent intent = new Intent(FourthActivity.this, FifthActivity.class);
        startActivity(intent);
    }

}
