package com.hfad.slimbelly.intro;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hfad.slimbelly.MainActivity;
import com.hfad.slimbelly.R;

public class FifthActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
    }

    public void onBtn5 (View view) {
        Intent intent = new Intent(FifthActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
