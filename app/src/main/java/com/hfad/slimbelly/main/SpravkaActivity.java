package com.hfad.slimbelly.main;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.hfad.slimbelly.MainActivity;
import com.hfad.slimbelly.R;
import com.hfad.slimbelly.main.spravka.MealActivity;
import com.hfad.slimbelly.main.spravka.TheoryActivity;
import com.hfad.slimbelly.main.spravka.TrainActivity;
import com.hfad.slimbelly.main.spravka.VacActivity;

public class SpravkaActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spravka);

        CardView card1 = (CardView) findViewById(R.id.cardMeal);
        CardView card2 = (CardView) findViewById(R.id.cardTraining);
        CardView card3 = (CardView) findViewById(R.id.cardVacuum);
        CardView card4 = (CardView) findViewById(R.id.cardTheory);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent1 = new Intent(SpravkaActivity.this, MealActivity.class);
        Intent intent2 = new Intent(SpravkaActivity.this, TrainActivity.class);
        Intent intent3 = new Intent(SpravkaActivity.this, VacActivity.class);
        Intent intent4 = new Intent(SpravkaActivity.this, TheoryActivity.class);

        switch (view.getId()) {
            case R.id.cardMeal:
                startActivity(intent1);
                break;

            case R.id.cardTraining:
                startActivity(intent2);
                break;

            case R.id.cardVacuum:
                startActivity(intent3);
                break;

            case R.id.cardTheory:
                startActivity(intent4);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SpravkaActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

