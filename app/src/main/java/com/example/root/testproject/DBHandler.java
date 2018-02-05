package com.example.root.testproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by root on 18. 1. 25.
 */

public class DBHandler {
        DBOpenHelper helper;
        SQLiteDatabase db;

    //Create DB
    public DBHandler(Context context) {
        helper = new DBOpenHelper(context, "BusReserve.db",null,1);
    }

    //open DB
    public static DBHandler open(Context context) {
        return new DBHandler(context);
    }

    //close DB
    public void close() {
        db.close();
    }

    //insert data to DB
    public void insert_to_reserve(int id, int seatnum, int reserved) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("seatnum",seatnum);
        values.put("reserved",reserved);

        db.insert("reserve",null,values);

        Log.d("insert","idx : "+id);
    }

    public void init_insertofReserve(int init){
        db = helper.getWritableDatabase();

        for (int i = 1; i<=init ; i++) {
            ContentValues values = new ContentValues();

            values.put("seatnum",i);
            values.put("reserved",0);

            db.insert("reserve",null,values);
        }

        select_all_reserve();
    }

    //select data from DB
    public Cursor select_all_reserve(){
        Log.d("DB","select all");

        db = helper.getReadableDatabase();
        Cursor c = db.query("reserve", null, null, null, null, null, null);

        while(c.moveToNext()) {
            Log.d("DB", "Idx : " + c.getInt(c.getColumnIndex("idx")) +
                    "   /seatnum : " + c.getInt(c.getColumnIndex("seatnum")) +
                    "   /reserved : " + c.getInt(c.getColumnIndex("reserved")));
        }

        c = db.query("reserve", null, null, null, null, null, null);

        return c;
    }

    public Cursor select_seat(int seatnum) {
        db = helper.getReadableDatabase();
        Cursor c = db.query("reserve", null, null, null, null, null, null);

        c.moveToNext();

        while(c.getInt(c.getColumnIndex("seatnum")) != seatnum) {
            c.moveToNext();
        }

        return c;
    }

    public int count_reserveData() {
        db = helper.getReadableDatabase();

        Cursor c = db.query("reserve", null, null, null, null, null, null);

        return c.getCount();
    }

    //update data in DB
    public void update_reserved(int seatnum, int reserved){
        db = helper.getWritableDatabase();

        db.execSQL("Update reserve set reserved = " + reserved + " where seatnum = " + seatnum);

        Log.d("DB", "update complete");
    }

    //delete data from DB
    public void delete (int idx) {
        db = helper.getWritableDatabase();
        db.delete("reserve","idx=?",new String[]{String.valueOf(idx)});

        Log.i("DB","Delete complete");
    }
}
