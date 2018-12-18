package com.hfad.slimbelly.main.spravka;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfad.slimbelly.R;

public class TrainTrainActivity extends Activity {

    int imageId;
    String name;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_train);

        Intent intent = getIntent();
        int i = intent.getIntExtra("EX", 1);

        Resources resources = this.getResources();

        switch (i) {
            case 1:
                name = "Бег с подъемом колен";
                text = resources.getString(R.string.train1);
                imageId = R.drawable.koleni;
                break;
            case 2:
                name = "Берпи";
                text = resources.getString(R.string.train2);
                imageId = R.drawable.berpi;
                break;
            case 3:
                name = "Скручивания";
                text = resources.getString(R.string.train3);
                imageId = R.drawable.skruch;
                break;
            case 4:
                name = "Бег с захлестом";
                text = resources.getString(R.string.train4);
                imageId = R.drawable.zahlest;
                break;
            case 5:
                name = "Скалолаз";
                text = resources.getString(R.string.train5);
                imageId = R.drawable.alpinist;
                break;
            case 6:
                name = "Складка";
                text = resources.getString(R.string.train6);
                imageId = R.drawable.skladka;
                break;
            case 7:
                name = "Прыжки с выпадами";
                text = resources.getString(R.string.train7);
                imageId = R.drawable.vypady;
                break;
            case 8:
                name = "Поднятие ног в обратной планке";
                text = resources.getString(R.string.train8);
                imageId = R.drawable.planka;
                break;
            case 9:
                name = "Велосипед";
                text = resources.getString(R.string.train9);
                imageId = R.drawable.velosiped;
                break;
        }

        TextView textView1 = (TextView) findViewById(R.id.train_train1);
        TextView textView2 = (TextView) findViewById(R.id.train_train2);
        ImageView imageView = (ImageView) findViewById(R.id.image_train_train);

        textView1.setText(name);
        imageView.setImageResource(imageId);
        textView2.setText(text);
    }
}
