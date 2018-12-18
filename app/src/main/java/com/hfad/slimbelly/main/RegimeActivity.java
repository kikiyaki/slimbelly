package com.hfad.slimbelly.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;

import com.hfad.slimbelly.DB;
import com.hfad.slimbelly.MainActivity;
import com.hfad.slimbelly.PojoRegim;
import com.hfad.slimbelly.R;
import com.hfad.slimbelly.RegimAdapter;
import com.hfad.slimbelly.TrainingActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

public class RegimeActivity extends Activity {

    private RecyclerView regimRecyclerView;
    private RegimAdapter regimAdapter;
    Collection<PojoRegim> pojoRegim;
    Calendar dateAndTime=Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regime);

        regimRecyclerView = findViewById(R.id.regim_recycler_view);
        regimRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        regimAdapter = new RegimAdapter(RegimeActivity.this);
        regimRecyclerView.setAdapter(regimAdapter);


        //получаем номер дня по счету и время подъема из базы данных
        DB dbHelper = new DB(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("DATA", new String[]{"MAX(NUMBER)"}, null,
                null, null, null, null);

        cursor.moveToFirst();
        int day = cursor.getInt(0);

        Cursor cursor1 = db.query("REGIME", new String[]{"HOUR", "MINUTE"},
                "NUMBER=1", null, null, null, null);

        cursor1.moveToFirst();
        int wakeHour = cursor1.getInt(0)-3;
        int wakeMinute = cursor1.getInt(1);

        dateAndTime.set(Calendar.HOUR_OF_DAY, wakeHour);
        dateAndTime.set(Calendar.MINUTE, wakeMinute);

        //День с максимальным количеством вакуума
        Collection<PojoRegim> maxRegim = Arrays.asList(
                new PojoRegim("Подъем", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME), R.drawable.podyom1),
                new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+5*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                new PojoRegim("Зарядка", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+15*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zaryadka1),
                new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+2*60*60*1000+30*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                new PojoRegim("Завтрак", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+3*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zavtrak1),
                new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+5*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                new PojoRegim("Обед", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+6*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.obed1),
                new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+8*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+10*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                new PojoRegim("Ужин", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+11*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.ugin1),
                new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+15*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                new PojoRegim("Отбой", DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis()+16*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.otboy1)
        );

        pojoRegim = maxRegim;

        switch (day) {
            case 1:
                pojoRegim = Arrays.asList(
                        new PojoRegim("Подъем", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME), R.drawable.podyom1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Зарядка", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zaryadka1),
                        new PojoRegim("Завтрак", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+3*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zavtrak1),
                        new PojoRegim("Обед", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+6*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.obed1),
                        new PojoRegim("Ужин", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+11*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.ugin1),
                        new PojoRegim("Отбой", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+16*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.otboy1)
                );
                break;
            case 2:
                pojoRegim = Arrays.asList(
                        new PojoRegim("Подъем", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME), R.drawable.podyom1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Зарядка", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zaryadka1),
                        new PojoRegim("Завтрак", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+3*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zavtrak1),
                        new PojoRegim("Обед", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+6*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.obed1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Ужин", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+11*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.ugin1),
                        new PojoRegim("Отбой", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+16*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.otboy1)
                );
                break;
            case 3:
                pojoRegim = Arrays.asList(
                        new PojoRegim("Подъем", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME), R.drawable.podyom1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Зарядка", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zaryadka1),
                        new PojoRegim("Завтрак", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+3*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zavtrak1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Обед", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+6*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.obed1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Ужин", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+11*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.ugin1),
                        new PojoRegim("Отбой", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+16*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.otboy1)
                );
                break;
            case 4:
                pojoRegim = Arrays.asList(
                        new PojoRegim("Подъем", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME), R.drawable.podyom1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Зарядка", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zaryadka1),
                        new PojoRegim("Завтрак", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+3*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zavtrak1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Обед", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+6*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.obed1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Ужин", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+11*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.ugin1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+15*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Отбой", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+16*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.otboy1)
                );
                break;
            case 5:
                pojoRegim = Arrays.asList(
                        new PojoRegim("Подъем", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME), R.drawable.podyom1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Зарядка", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+15*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zaryadka1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+50*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Завтрак", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+3*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.zavtrak1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+5*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Обед", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+6*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.obed1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+10*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Ужин", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+11*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.ugin1),
                        new PojoRegim("Вакуум", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+15*60*60*1000+40*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.vacuum1),
                        new PojoRegim("Отбой", DateUtils.formatDateTime(this,
                                dateAndTime.getTimeInMillis()+16*60*60*1000, DateUtils.FORMAT_SHOW_TIME), R.drawable.otboy1)
                );
                break;
            case 6:
                pojoRegim = maxRegim;
                break;
            case 7:
                pojoRegim = maxRegim;
                break;
            case 8:
                pojoRegim = maxRegim;
                break;
            case 9:
                pojoRegim = maxRegim;
                break;
            case 10:
                pojoRegim = maxRegim;
                break;
            case 11:
                pojoRegim = maxRegim;
                break;
            case 12:
                pojoRegim = maxRegim;
                break;
            case 13:
                pojoRegim = maxRegim;
                break;
            case 14:
                pojoRegim = maxRegim;
                break;
        }

        regimAdapter.setItems(pojoRegim);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegimeActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
