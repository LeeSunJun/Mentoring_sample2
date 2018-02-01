package com.example.root.testproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 18. 1. 25.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    @Override
    public  void onCreate(SQLiteDatabase db) {
        String sql = "create table reserve (" +
                "idx integer not null primary key autoincrement , " +
                "seatnum integer, " +
                "reserved integer)";

        Log.d("DB","created");

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists seat";
        db.execSQL(sql);

        onCreate(db);
    }
}
