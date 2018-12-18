package com.hfad.slimbelly.intro;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hfad.slimbelly.DB;
import com.hfad.slimbelly.R;

import java.util.Calendar;


public class SecondActivity extends Activity {


    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) { try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        currentDateTime=(TextView)findViewById(R.id.time);



    } catch (Exception e) {e.printStackTrace();}
    }

    public void setTime(View v) {
        new TimePickerDialog(SecondActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    public void onBtn2 (View view) {


        DB dbHelper = new DB(SecondActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NUMBER", 1);
        values.put("HOUR", dateAndTime.get(Calendar.HOUR_OF_DAY)+3);
        values.put("MINUTE", dateAndTime.get(Calendar.MINUTE));
        db.insert("REGIME", null, values);
        values.clear();

        values.put("NUMBER", 2);
        values.put("HOUR", dateAndTime.get(Calendar.HOUR_OF_DAY)+6);
        values.put("MINUTE", dateAndTime.get(Calendar.MINUTE));
        db.insert("REGIME", null, values);
        values.clear();

        values.put("NUMBER", 3);
        values.put("HOUR", dateAndTime.get(Calendar.HOUR_OF_DAY)+11);
        values.put("MINUTE", dateAndTime.get(Calendar.MINUTE));
        db.insert("REGIME", null, values);
        values.clear();

        db.close();

        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        startActivity(intent);

    }

}
