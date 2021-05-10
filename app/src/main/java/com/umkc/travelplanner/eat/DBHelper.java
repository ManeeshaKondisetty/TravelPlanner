package com.umkc.travelplanner.eat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "history";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "MainActivity1";

//    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists venue (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, context TEXT, address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addVenue(Venue venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", venue.getName());
        cv.put("context", venue.getContextLine());
        cv.put("address", venue.getFormattedaAddress());

        long insert = db.insert("venue", null, cv);
        if(insert==-1) {
            return false;
        }
        return true;
    }

    public List<Venue> fetch() {
        List<Venue> results =  new ArrayList<>();
        String query = "select * from venue order by id desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.d(TAG, "fetch: ");

        if(cursor.moveToFirst()) {
            do {
                Venue v = new Venue();
                v.setName(cursor.getString(1));
                v.setContextLine(cursor.getString(2));
                v.setFormattedaAddress(cursor.getString(3));;
                results.add(v);
            } while(cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        Log.d(TAG, "fetch: " + results);
        return results;
    }

}
