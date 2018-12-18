package com.hfad.slimbelly.intro;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hfad.slimbelly.AndroidDatabaseManager;
import com.hfad.slimbelly.DB;
import com.hfad.slimbelly.MainActivity;
import com.hfad.slimbelly.MyReceiver;
import com.hfad.slimbelly.R;
import com.hfad.slimbelly.TrainingActivity;
import com.hfad.slimbelly.main.SvodkaActivity;

import java.util.Calendar;

public class FirstActivity extends Activity {

    java.util.Calendar calendar;
    int day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        day = Integer.valueOf(getIntent().getStringExtra("NUMBER"));
    }


    public void onBtn (View view) {

        DB dbHelper = new DB(FirstActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.e("testtest", "create db");

        double weight;

         EditText editText = (EditText) findViewById(R.id.weight);
         if (!editText.getText().toString().equals("")) {
           weight = Double.valueOf(editText.getText().toString());
             String sql = "UPDATE DATA SET WEIGHT="+String.valueOf(weight)+" WHERE NUMBER="+String.valueOf(day);
             db.execSQL(sql);
         } else {

         }

        Intent intent = new Intent(FirstActivity.this, SvodkaActivity.class);
        startActivity(intent);
    }
}
