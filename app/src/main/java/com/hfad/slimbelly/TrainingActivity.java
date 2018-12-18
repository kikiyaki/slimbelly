package com.hfad.slimbelly;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.hfad.slimbelly.intro.FourthActivity;

import java.io.IOException;
import java.util.Calendar;


public class TrainingActivity extends Activity implements SoundPool.OnLoadCompleteListener, View.OnClickListener {

    int img1, img2, img3;
    String txt1, txt2, txt3;
    String txtEx1, txtEx2, txtEx3;

    int time1 = 25;
    int time2 = 15;
    int seconds = time1;

    SoundPool sp;
    int nachId;
    int otdId;
    int endId;

    boolean a = true;

    final Handler handler = new Handler();

    DB dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        dbHelper = new DB(this);
        db = dbHelper.getWritableDatabase();

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);

        try {
            nachId = sp.load(getAssets().openFd("nach.ogg"), 1);
            otdId = sp.load(getAssets().openFd("otd.ogg"), 1);
            endId = sp.load(getAssets().openFd("end.ogg"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Cursor cursor = db.query("DATA", new String[]{"MAX(NUMBER)"}, null,
                null, null, null, null);

        cursor.moveToFirst();
        int day = cursor.getInt(0) % 3;


        switch (day) {
            case 1:
                txt1 = "Бег с подъемом колен";
                img1 = R.drawable.koleni;
                txtEx1 = "Поднимите левое колено как можно выше(1), в прыжке поменяйте" +
                        " левое колено на правое(2) и повторяйте";

                txt2 = "Берпи";
                img2 = R.drawable.berpi;
                txtEx2 = "Присядьте, коснувшись руками пола (1), примите положение лежа, каса" +
                        "ясь грудью пола (2), подтяните колени к груди (3), выпрыгните с хлопком(4)";

                txt3 = "Скручивания";
                img3 = R.drawable.skruch;
                txtEx3 = "Лягте на пол, согнув ноги в коленях (1), скручивайте корпус, " +
                        "пытаясь достать колени головой(2). Спина должна быть круглой";
                break;

            case 2:
                txt1 = "Бег с захлестом";
                img1 = R.drawable.zahlest;
                txtEx1 = "Совершайте бег на месте, пытаясь достать пятками ягодицы";

                txt2 = "Скалолаз";
                img2 = R.drawable.alpinist;
                txtEx2 = "Займите исходное положение как при низком старте, максимально подтянув " +
                        "правое колено к груди (1), не отпуская рук от пола, одним движением " +
                        "поменяйте ноги местами (2)";

                txt3 = "Складка";
                img3 = R.drawable.skladka;
                txtEx3 = "Лягте на пол, вытянув руки вверх(1), одновременно поднимайте ноги и " +
                        "туловище вверх до их прикосновения (2), пытаясь ноги держать прямыми, " +
                        "а руки над головой";
                break;

            case 0:
                txt1 = "Прыжки с выпадами";
                img1 = R.drawable.vypady;
                txtEx1 = "Сделайте максимально глубокий выпад на левую ногу (1), затем выпрыгните " +
                        "как можно выше с хлопком (2), приземляясь, сделайте выпад на правую ногу " +
                        "(3), после чего прыжок (4)";

                txt2 = "Поднятие ног в обратной планке";
                img2 = R.drawable.planka;
                txtEx2 = "Обопритесь руками о пол и поднимите таз (1), поочередно поднимайте ноги " +
                        "на максимальную высоту (2)";

                txt3 = "Велосипед";
                img3 = R.drawable.velosiped;
                txtEx3 = "Лягте на сругленную спину (1), держа руки за головой, не касаясь ими пола, " +
                        "и подтяните одно колено максимально близко к груди, затем, не опуская " +
                        "голову на пол, поменяйте колени местами (2)";
                break;

        }
        TextView textView1 = (TextView) findViewById(R.id.exer1);
        TextView textView2 = (TextView) findViewById(R.id.exer2);
        TextView textView3 = (TextView) findViewById(R.id.textExercise);
        TextView textView4 = (TextView) findViewById(R.id.timer);
        TextView textView5 = (TextView) findViewById(R.id.ex_text);

        ImageView imageView = (ImageView) findViewById(R.id.imageExercise);

        textView1.setText("Круг 1 из 5");
        textView2.setText("Упражнение 1");
        textView3.setText(txt1);
        imageView.setImageResource(img1);
        textView5.setText(txtEx1);
        textView4.setText(String.format("%02d:%02d", 0, seconds));

    }


    public void onTimerButton(View view) {

        final TextView timeView = (TextView) findViewById(R.id.timer);

        sp.play(nachId, 1, 1, 0, 0, 1);

        Button button = (Button) findViewById(R.id.timerButton);
        button.setClickable(false);
        button.setText("Удачи");


        handler.post(new Runnable() {

            int i=1, ii=1;
            @Override
            public void run() {

                String time = String.format("%02d:%02d", 0, seconds);
                timeView.setText(time);

                    seconds--;

                if (seconds < 0) {
                    ii++;
                    if (ii>6) {
                        i++;
                        ii=1;
                    }
                    setEx(i, ii);
                }

                handler.postDelayed(this, 1000);

            }
        });

    }

    public void setEx (int i, int ii) {

        final TextView timeView = (TextView) findViewById(R.id.timer);

        TextView textView1 = (TextView) findViewById(R.id.exer1);
        TextView textView2 = (TextView) findViewById(R.id.exer2);
        TextView textView3 = (TextView) findViewById(R.id.textExercise);
        ImageView imageView = (ImageView) findViewById(R.id.imageExercise);
        TextView textView5 = (TextView) findViewById(R.id.ex_text);

        if (i<=5) {

            textView1.setText("Круг " + String.valueOf(i) + " из 5");


            switch (ii) {
                case 1:
                    textView3.setText(txt1);
                    textView2.setText("Упражнение 1. Начали!");
                    imageView.setImageResource(img1);
                    textView5.setText(txtEx1);
                    sp.play(nachId, 1, 1, 0, 0, 1);
                    seconds = time1;
                    break;
                case 2:
                    textView3.setText(txt2);
                    textView2.setText("Упражнение 2. Приготовьтесь");
                    imageView.setImageResource(img2);
                    textView5.setText(txtEx2);
                    sp.play(otdId, 1, 1, 1, 0, 1);
                    seconds = time2;
                    break;
                case 3:
                    textView3.setText(txt2);
                    textView2.setText("Упражнение 2. Начали!");
                    imageView.setImageResource(img2);
                    textView5.setText(txtEx2);
                    sp.play(nachId, 1, 1, 1, 0, 1);
                    seconds = time1;
                    break;
                case 4:
                    textView3.setText(txt3);
                    textView2.setText("Упражнение 3. Приготовьтесь");
                    imageView.setImageResource(img3);
                    textView5.setText(txtEx3);
                    sp.play(otdId, 1, 1, 1, 0, 1);
                    seconds = time2;
                    break;
                case 5:
                    textView3.setText(txt3);
                    imageView.setImageResource(img3);
                    textView2.setText("Упражнение 3. Начали! ");
                    textView5.setText(txtEx3);
                    sp.play(nachId, 1, 1, 1, 0, 1);
                    seconds = time1;
                    break;
                case 6:

                    if (i == 5) {
                        sp.play(endId, 1, 1, 1, 0, 1);
                        textView1.setText("Победа!");
                        textView2.setText("Тренировка окончена");
                        textView3.setText("");
                        textView5.setText("");
                        imageView.setImageResource(R.drawable.kubok);
                        timeView.setText("");

                        Button button = (Button) findViewById(R.id.timerButton);
                        button.setClickable(true);
                        button.setText("Домой");

                        button.setOnClickListener(TrainingActivity.this);
                    } else {
                        textView3.setText(txt1);
                        textView2.setText("Упражнение 1. Приготовьтесь!");
                        imageView.setImageResource(img1);
                        textView5.setText(txtEx1);
                        sp.play(otdId, 1, 1, 1, 0, 1);
                        seconds = time2;
                    }
                    break;
            }
        } else {
            textView1.setText("Победа!");
            textView2.setText("Тренировка окончена");
            textView3.setText("");
            textView5.setText("");
            imageView.setImageResource(R.drawable.kubok);
            timeView.setText("");
        }
    }

    public void onClick(View view) {
        handler.post(new QuitLooper());
        Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }

    class QuitLooper implements Runnable {
        @Override
        public void run() {
            Looper.myLooper().quit();
        }
    }
    private void openQuitDialog() {

        Cursor cursor = db.query("REGIME", new String[]{"HOUR"},
                "NUMBER=-1", null, null, null, null);

        if (cursor.moveToFirst()) {
            if (cursor.getInt(0)==1) {
                a = true;
            } else {
                a = false;
            }
        }

        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                TrainingActivity.this);
        quitDialog.setTitle("Вы хотите закончить тренировку?");
        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {

            @Override
            public void onClick (DialogInterface dialog,int which){

                    Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
                    intent.putExtra("NOTICE", a);
                    handler.post(new QuitLooper());
                    startActivity(intent);

            }

        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

}
