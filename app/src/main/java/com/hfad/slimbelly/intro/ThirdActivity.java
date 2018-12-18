package com.hfad.slimbelly.intro;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.hfad.slimbelly.DB;
import com.hfad.slimbelly.MyReceiver;
import com.hfad.slimbelly.MyReceiver2;
import com.hfad.slimbelly.R;

import java.util.Calendar;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Calendar calendar = Calendar.getInstance();

            DB dbHelper = new DB(ThirdActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursor = db.query("REGIME", new String[]{"HOUR", "MINUTE"},
                    "NUMBER>0", null, null, null, null);

            int hour;
            int minute;

            if (cursor.moveToFirst()) {
                hour = cursor.getInt(0);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                minute = cursor.getInt(1);
                calendar.set(Calendar.MINUTE, minute);

                TextView textView = (TextView) findViewById(R.id.zavtrak);
                textView.setText(DateUtils.formatDateTime(this, calendar.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_TIME));
            }
        if (cursor.moveToNext()) {
            hour = cursor.getInt(0);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            minute = cursor.getInt(1);
            calendar.set(Calendar.MINUTE, minute);

            TextView textView = (TextView) findViewById(R.id.obed);
            textView.setText(DateUtils.formatDateTime(this, calendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }
        if (cursor.moveToNext()) {
            hour = cursor.getInt(0);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            minute = cursor.getInt(1);
            calendar.set(Calendar.MINUTE, minute);

            TextView textView = (TextView) findViewById(R.id.ugin);
            textView.setText(DateUtils.formatDateTime(this, calendar.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));
        }

    }

    public void onBtn3 (View view) {
        Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
        startActivity(intent);
    }
}
