package com.hfad.slimbelly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hfad.slimbelly.intro.FirstActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class DB extends SQLiteOpenHelper {
   public DB(Context context) {
        super(context, "SlimDB",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DATA ("
                + "NUMBER INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +"DAY INTEGER, "
                +"MONTH INTEGER, "
                +"YEAR INTEGER, "
                +"WEIGHT REAL);");

        db.execSQL("CREATE TABLE REGIME ("
                + "NUMBER INTEGER PRIMARY KEY,"
                +"HOUR INTEGER, "
                +"MINUTE INTEGER);");

        ContentValues values = new ContentValues();
        values.put("NUMBER", 0);
        db.insert("REGIME", null, values);

        ContentValues values0 = new ContentValues();
        values0.put("NUMBER", -1);
        values0.put("HOUR", 1);
        db.insert("REGIME", null, values0);


        Calendar calendar;
        calendar = Calendar.getInstance();

        ContentValues values1 = new ContentValues();
        values1.put("WEIGHT", 0);
        values1.put("DAY", calendar.get(Calendar.DATE));
        values1.put("MONTH", calendar.get(Calendar.MONTH));
        values1.put("YEAR", calendar.get(Calendar.YEAR));

        db.insert("DATA", null, values1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
