package com.hfad.slimbelly.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hfad.slimbelly.DB;
import com.hfad.slimbelly.MainActivity;
import com.hfad.slimbelly.R;
import com.hfad.slimbelly.intro.FirstActivity;

import java.text.DecimalFormat;

public class SvodkaActivity extends Activity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svodka);



        SQLiteOpenHelper sqLiteOpenHelper = new DB(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("DATA", new String[]{"NUMBER", "WEIGHT"},
                null, null,
                null, null, null);

        //Заполнение таблицы

        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        LayoutInflater ltInflater = getLayoutInflater();
        cursor.moveToFirst();

        double lastWeight = cursor.getDouble(1);

        cursor.moveToPosition(-1);

        while (cursor.moveToNext()) {

            TableRow tableRow = (TableRow) ltInflater.inflate(R.layout.row, null, false);

            TextView textView = (TextView) tableRow.getChildAt(0);
            textView.setText(String.valueOf(cursor.getInt(0)));

            TextView textView1 = (TextView) tableRow.getChildAt(1);
            if (cursor.getDouble(1) == 0) {
                textView1.setText("--");
            } else {
                textView1.setText(new DecimalFormat("#0.00").format(cursor.getDouble(1)));
            }
            textView1.setOnClickListener(this);


            TextView textView2 = (TextView) tableRow.getChildAt(2);
            if (cursor.getDouble(1) - lastWeight == lastWeight |
                    cursor.getDouble(1) - lastWeight == - lastWeight) {
                textView2.setText("--");
            } else {
                if (cursor.getDouble(1) - lastWeight > 0.001) {
                    textView2.setText(new DecimalFormat("+##0.00").format(cursor.getDouble(1) - lastWeight));
                } else {
                    textView2.setText(new DecimalFormat("#0.00").format(cursor.getDouble(1) - lastWeight));
                }
            }
            tableLayout.addView(tableRow);
            lastWeight = cursor.getDouble(1);

       }

        TextView textView = (TextView) ltInflater.inflate(R.layout.text_view, null, false);
        tableLayout.addView(textView);



    }

    @Override
    public void onClick(View view) {
        String number = String.valueOf(((TextView) ((TableRow)
                view.getParent()).getChildAt(0)).getText());
        Log.e("testtest", number);
        Intent intent = new Intent(SvodkaActivity.this, FirstActivity.class);
        intent.putExtra("NUMBER", number);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(SvodkaActivity.this, MainActivity.class);
       startActivity(intent);
    }
}