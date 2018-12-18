package com.hfad.slimbelly;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.slimbelly.intro.FirstActivity;
import com.hfad.slimbelly.intro.IntroActivity;
import com.hfad.slimbelly.intro.SecondActivity;
import com.hfad.slimbelly.main.RegimeActivity;
import com.hfad.slimbelly.main.SpravkaActivity;
import com.hfad.slimbelly.main.SvodkaActivity;
import com.hfad.slimbelly.PojoRegim;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;


public class MainActivity extends Activity implements View.OnClickListener {

    Collection<PojoRegim> pojoRegim;

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqLiteOpenHelper = new DB(this);
        db = sqLiteOpenHelper.getWritableDatabase();

        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boolean notice = extras.getBoolean("NOTICE");
            if (notice) {
                openMarketDialog();
            }
        }

        //    Intent intent3 = new Intent(MainActivity.this, AndroidDatabaseManager.class);
        //   startActivity(intent3);

        Cursor cursor = db.query("REGIME", new String[]{"NUMBER", "HOUR", "MINUTE"},
                null, null,
                null, null, null);

        if (cursor.moveToLast()) {
            int i = cursor.getInt(0);
            if (i == 0) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            } else {

                CardView card1 = (CardView) findViewById(R.id.cardSvodka);
                CardView card2 = (CardView) findViewById(R.id.cardRegim);
                CardView card3 = (CardView) findViewById(R.id.cardSpravka);
                card1.setOnClickListener(this);
                card2.setOnClickListener(this);
                card3.setOnClickListener(this);

                Calendar currentTime = Calendar.getInstance();

                Cursor cursor1 = db.query("DATA", new String[]{"NUMBER", "WEIGHT", "DAY",
                        "MONTH", "YEAR"},
                        null, null,
                        null, null, "NUMBER ASC");

                //Выставление дня и создание записи для нового дня
                cursor1.moveToFirst();
                Calendar firstDay = Calendar.getInstance();
                firstDay.set(Calendar.DAY_OF_MONTH, cursor1.getInt(2));
                firstDay.set(Calendar.MONTH, cursor1.getInt(3));
                firstDay.set(Calendar.YEAR, cursor1.getInt(4));

                int deltaDay = (int) currentTime.getTimeInMillis()/24/60/60/1000 -
                        (int) firstDay.getTimeInMillis()/24/60/60/1000;

                cursor1.moveToLast();
                int day = cursor1.getInt(0);

                while (day < deltaDay+1){
                    day++;

                    ContentValues values1 = new ContentValues();
                    values1.put("WEIGHT", 0);
                    values1.put("DAY", currentTime.get(Calendar.DATE));
                    values1.put("MONTH", currentTime.get(Calendar.MONTH));
                    values1.put("YEAR", currentTime.get(Calendar.YEAR));

                    db.insert("DATA", null, values1);
                }

                //Если текущее время меньше времени подъема и если это не 1й день, то ставится следующий день
                if (currentTime.get(Calendar.HOUR_OF_DAY) < cursor.getInt(1) - 11 && day != 1) {
                    day++;
                }

                cursor1 = db.query("DATA", new String[]{"NUMBER", "WEIGHT", "DAY",
                                "MONTH", "YEAR"},
                        null, null,
                        null, null, "NUMBER ASC");
                cursor1.moveToLast();
                double weight = cursor1.getDouble(1);
                double preWeight = weight;
                Log.e("testtest", String.valueOf(day));

                if (day > 1) {
                    cursor1.moveToPrevious();
                    preWeight = cursor1.getDouble(1);

                    if (weight == 0) {
                        weight = preWeight;

                        if (day>2) {
                            cursor1.moveToPrevious();
                            preWeight = cursor1.getDouble(1);
                        }
                    }
                }

                Calendar dateAndTime = Calendar.getInstance();

                cursor.moveToFirst();
                cursor.moveToNext();
                int wakeHour = cursor.getInt(1) - 3;
                int wakeMinute = cursor.getInt(2);

                dateAndTime.set(Calendar.HOUR_OF_DAY, wakeHour);
                dateAndTime.set(Calendar.MINUTE, wakeMinute);

                //День с максимальным количеством вакуума
                Collection<PojoRegim> maxRegim = Arrays.asList(
                        new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 1000), R.drawable.vacuum1),
                        new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 2 * 60 * 60 * 1000 + 30 * 60 * 1000), R.drawable.vacuum1),
                        new PojoRegim("Завтрак", String.valueOf(dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000), R.drawable.zavtrak1),
                        new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                        new PojoRegim("Обед", String.valueOf(dateAndTime.getTimeInMillis() + 6 * 60 * 60 * 1000), R.drawable.obed1),
                        new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 8 * 60 * 60 * 1000), R.drawable.vacuum1),
                        new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 10 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                        new PojoRegim("Ужин", String.valueOf(dateAndTime.getTimeInMillis() + 11 * 60 * 60 * 1000), R.drawable.ugin1),
                        new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 15 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1)
                );

                pojoRegim = maxRegim;

                switch (day) {
                    case 1:
                        pojoRegim = Arrays.asList(
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Завтрак", String.valueOf(dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000), R.drawable.zavtrak1),
                                new PojoRegim("Обед", String.valueOf(dateAndTime.getTimeInMillis() + 6 * 60 * 60 * 1000), R.drawable.obed1),
                                new PojoRegim("Ужин", String.valueOf(dateAndTime.getTimeInMillis() + 11 * 60 * 60 * 1000), R.drawable.ugin1)
                        );
                        break;
                    case 2:
                        pojoRegim = Arrays.asList(
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Завтрак", String.valueOf(dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000), R.drawable.zavtrak1),
                                new PojoRegim("Обед", String.valueOf(dateAndTime.getTimeInMillis() + 6 * 60 * 60 * 1000), R.drawable.obed1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 10 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Ужин", String.valueOf(dateAndTime.getTimeInMillis() + 11 * 60 * 60 * 1000), R.drawable.ugin1)
                        );
                        break;
                    case 3:
                        pojoRegim = Arrays.asList(
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Завтрак", String.valueOf(dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000), R.drawable.zavtrak1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Обед", String.valueOf(dateAndTime.getTimeInMillis() + 6 * 60 * 60 * 1000), R.drawable.obed1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 10 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Ужин", String.valueOf(dateAndTime.getTimeInMillis() + 11 * 60 * 60 * 1000), R.drawable.ugin1)
                        );
                        break;
                    case 4:
                        pojoRegim = Arrays.asList(
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Завтрак", String.valueOf(dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000), R.drawable.zavtrak1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Обед", String.valueOf(dateAndTime.getTimeInMillis() + 6 * 60 * 60 * 1000), R.drawable.obed1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 10 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Ужин", String.valueOf(dateAndTime.getTimeInMillis() + 11 * 60 * 60 * 1000), R.drawable.ugin1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 15 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1)
                        );
                        break;
                    case 5:
                        pojoRegim = Arrays.asList(
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 50 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Завтрак", String.valueOf(dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000), R.drawable.zavtrak1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 5 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Обед", String.valueOf(dateAndTime.getTimeInMillis() + 6 * 60 * 60 * 1000), R.drawable.obed1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 10 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1),
                                new PojoRegim("Ужин", String.valueOf(dateAndTime.getTimeInMillis() + 11 * 60 * 60 * 1000), R.drawable.ugin1),
                                new PojoRegim("Вакуум", String.valueOf(dateAndTime.getTimeInMillis() + 15 * 60 * 60 * 1000 + 40 * 60 * 1000), R.drawable.vacuum1)
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

                long vacDelta = 24 * 60 * 60 * 1000;
                long eatDelta = 24 * 60 * 60 * 1000;

    /*        Intent notifyIntent = new Intent(this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, 0, notifyIntent, 0);
            Intent notifyIntent2 = new Intent(this, MyReceiver2.class);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast
                    (this, 0, notifyIntent2, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dateAndTime.getTimeInMillis()+
                   24*60*60*1000 + 5 * 60 * 1000, AlarmManager.INTERVAL_DAY, pendingIntent2);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dateAndTime.getTimeInMillis()+
                    24*60*60*1000 + 10 * 60 * 1000, AlarmManager.INTERVAL_DAY, pendingIntent); */

                for (int ii = 0; ii < pojoRegim.size(); ii++) {
                    //Перебор всех значений времени вакуума и выбор ближайшего
                    PojoRegim pojoElem = (PojoRegim) pojoRegim.toArray()[ii];
                    if (pojoElem.getName() == "Вакуум") {
                        //Если текущее время меньше времени вакуума
                        if (currentTime.getTimeInMillis() < Long.parseLong(pojoElem.getTime())) {
                            //Если для данного элемента время до вакуума меньше чем для предыдущего, то ставим его
                            if ((Long.parseLong(pojoElem.getTime()) - currentTime.getTimeInMillis()) < vacDelta) {
                                vacDelta = Long.parseLong(pojoElem.getTime()) - currentTime.getTimeInMillis();
                            }
                        }
                    }
                    //Проверка всех значений времени для завтрака, обеда и ужина
                    if (pojoElem.getName() == "Завтрак" | pojoElem.getName() == "Обед" | pojoElem.getName() == "Ужин") {
                        //Если текущее время меньше времени еды
                        if (currentTime.getTimeInMillis() < Long.parseLong(pojoElem.getTime())) {
                            //Если для данного элемента время до еды меньше чем для предыдущего, то ставим его
                            if ((Long.parseLong(pojoElem.getTime()) - currentTime.getTimeInMillis()) < eatDelta) {
                                eatDelta = Long.parseLong(pojoElem.getTime()) - currentTime.getTimeInMillis();
                            }
                        }
                    }
                }
                //Если не нашлось ближайшего вакуума в текущий день, то ставится ближайший в следующий день
                if (vacDelta == 24 * 60 * 60 * 1000) {
                    vacDelta = dateAndTime.getTimeInMillis() + 5 * 60 * 1000 + 24 * 60 * 60 * 1000 - currentTime.getTimeInMillis();
                }
                //Если не нашлось ближайшего вакуума в текущий день, то ставится ближайший в следующий день
                if (eatDelta == 24 * 60 * 60 * 1000) {
                    eatDelta = dateAndTime.getTimeInMillis() + 3 * 60 * 60 * 1000 + 24 * 60 * 60 * 1000 - currentTime.getTimeInMillis();
                }
                TextView textViewDay = (TextView) findViewById(R.id.day);
                textViewDay.setText(String.valueOf(day));
                TextView textViewDayLeft = (TextView) findViewById(R.id.dayLeft);
                textViewDayLeft.setText(String.valueOf(14 - day));
                TextView textViewVes = (TextView) findViewById(R.id.vesSvodka);
                if (weight == 0) {
                    textViewVes.setText("--");
                } else {
                    textViewVes.setText(new DecimalFormat("##0.00").format(weight));
                }

                TextView textViewVesDelta = (TextView) findViewById(R.id.vesDelta);
                if (weight - preWeight == weight | - weight + preWeight == weight
                        |weight - preWeight == preWeight | - weight + preWeight == preWeight) {
                    textViewVesDelta.setText("--");
                } else {
                    if (weight - preWeight > 0.001) {
                        textViewVesDelta.setText(new DecimalFormat("+##0.00").format(weight - preWeight));
                    } else {
                        textViewVesDelta.setText(new DecimalFormat("#0.00").format(weight - preWeight));
                    }
                }

                Log.e("testtest", String.valueOf(weight));
                Log.e("testtest", String.valueOf(preWeight));
                TextView textViewCurrentTime = (TextView) findViewById(R.id.clock);
                textViewCurrentTime.setText(DateUtils.formatDateTime(this,
                        currentTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME));

                dateAndTime.set(Calendar.HOUR_OF_DAY, 0);
                dateAndTime.set(Calendar.MINUTE, 0);

                TextView textViewEatLeft = (TextView) findViewById(R.id.eatLeft);
                textViewEatLeft.setText(DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis() + eatDelta, DateUtils.FORMAT_SHOW_TIME));

                TextView textViewVacuumLeft = (TextView) findViewById(R.id.vacuumLeft);
                textViewVacuumLeft.setText(DateUtils.formatDateTime(this,
                        dateAndTime.getTimeInMillis() + vacDelta, DateUtils.FORMAT_SHOW_TIME));

                if (day == 14) {
                    textViewDay.setText("Крайний день");
                    textViewDayLeft.setText("");
                    TextView textView = (TextView) findViewById(R.id.name2);
                    textView.setText("");
                    TextView textView1 = (TextView) findViewById(R.id.name3);
                    textView1.setText("                     ");
                    TextView textView2 = (TextView) findViewById(R.id.name4);
                    textView2.setText("");
                }
                if (day > 14) {
                    textViewDay.setText("Вы справились!");
                    textViewDayLeft.setText("");
                    TextView textView = (TextView) findViewById(R.id.name2);
                    textView.setText("");
                    TextView textView1 = (TextView) findViewById(R.id.name3);
                    textView1.setText("                     ");
                    TextView textView2 = (TextView) findViewById(R.id.name4);
                    textView2.setText("");
                }

            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent1 = new Intent(MainActivity.this, SvodkaActivity.class);
        Intent intent2 = new Intent(MainActivity.this, RegimeActivity.class);
        Intent intent3 = new Intent(MainActivity.this, SpravkaActivity.class);

        switch (view.getId()) {
            case R.id.cardSvodka:
                startActivity(intent1);
                break;

            case R.id.cardRegim:
                startActivity(intent2);
                break;

            case R.id.cardSpravka:
                startActivity(intent3);
                break;
        }

        }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Выйти из приложения?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {

            @Override
            public void onClick (DialogInterface dialog,int which){
                finishAffinity();
            }

        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }

    private void openMarketDialog() {

        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Пожалуйста, оставьте отзыв");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {

            @Override
            public void onClick (DialogInterface dialog,int which){
                //занести в базу а=0
                String sql = "UPDATE REGIME SET HOUR=0 WHERE NUMBER=-1";
                db.execSQL(sql);
                //открыть маркет
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.hfad.slimbelly"));
                startActivity(intent);
            }

        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //занести в базу а=0
                String sql = "UPDATE REGIME SET HOUR=0 WHERE NUMBER=-1";
                db.execSQL(sql);
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        quitDialog.setNeutralButton("Позже", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }

}

