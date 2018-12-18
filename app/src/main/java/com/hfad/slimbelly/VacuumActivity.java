package com.hfad.slimbelly;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;

import java.io.IOException;

public class VacuumActivity extends Activity implements SoundPool.OnLoadCompleteListener,
        View.OnClickListener {

    int time1 = 20;
    int time2 = 20;
    int seconds = time1;

    SoundPool sp;
    int nachId;
    int otdId;
    int endId;

    final Handler handler = new Handler();

    boolean a = true;

    DB dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacuum);

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
        int day = cursor.getInt(0);

        for (int i = 1; i < day; i++) {
            time1+=5;
        }
        seconds = time1;

        TextView textView4 = (TextView) findViewById(R.id.vac_timer);
        textView4.setText(String.format("%02d:%02d", 0, seconds));

    }


    public void onTimerButton(View view) {

        final TextView timeView = (TextView) findViewById(R.id.vac_timer);
        TextView vac2 = (TextView) findViewById(R.id.vac2);
        vac2.setText("Начали");

        sp.play(nachId, 1, 1, 0, 0, 1);

        Button button = (Button) findViewById(R.id.vac_button);
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
                    if (ii>2) {
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

        final TextView timeView = (TextView) findViewById(R.id.vac_timer);
        TextView vac1 = (TextView) findViewById(R.id.vac1);
        TextView vac2 = (TextView) findViewById(R.id.vac2);
        TextView vac3 = (TextView) findViewById(R.id.vac3);
        ImageView vac_image = (ImageView) findViewById(R.id.vac_image);

        if (i<=3) {

            switch (ii) {
                case 1:
                    vac2.setText("Начали");
                    sp.play(nachId, 1, 1, 0, 0, 1);
                    seconds = time1;
                    break;
                case 2:

                    if (i == 3) {
                        vac1.setText("Победа!");
                        vac2.setText("Тренировка окончена");
                        vac_image.setImageResource(R.drawable.kubok);
                        timeView.setText("");
                        vac3.setText("");

                        sp.play(endId, 1, 1, 1, 0, 1);

                        Button button = (Button) findViewById(R.id.vac_button);
                        button.setClickable(true);
                        button.setText("Домой");

                        button.setOnClickListener(VacuumActivity.this);
                    } else {
                        vac2.setText("Отдых");
                        sp.play(otdId, 1, 1, 1, 0, 1);
                        seconds = time2;
                    }
                    break;
            }
        } else {
            vac1.setText("Победа!");
            vac2.setText("Тренировка окончена");
            vac_image.setImageResource(R.drawable.kubok);
            timeView.setText("");
            vac3.setText("");
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(VacuumActivity.this, MainActivity.class);
        handler.post(new QuitLooper());
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

    @Override
    public void onBackPressed() {
        openQuitDialog();
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
                VacuumActivity.this);
        quitDialog.setTitle("Вы хотите закончить тренировку?");
        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {

            @Override
            public void onClick (DialogInterface dialog,int which){

                Intent intent = new Intent(VacuumActivity.this, MainActivity.class);
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

}
