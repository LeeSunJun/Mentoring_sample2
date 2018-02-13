package com.example.root.testproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18. 1. 25.
 */

public class DBHandler {
    DBOpenHelper helper;
    SQLiteDatabase db;

    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference Ref;

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

    //firebase cdoe
    public void getfirebase(){
        db = helper.getWritableDatabase();

        /*FirebaseDatabase.getInstance().getReference("reserve").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    int reserved = Integer.parseInt(snapshot.child("reserved").getValue().toString());
                    final int seatnum = Integer.parseInt(snapshot.child("seatnum").getValue().toString());

                    if(reserved != -1 && seatnum != -1) {
                        db.execSQL("Update reserve set reserved = " + reserved + " where seatnum = " + seatnum);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue().toString();
                    String pw = snapshot.child("pw").getValue().toString();

                    ContentValues values = new ContentValues();

                    if(!id.isEmpty() && !pw.isEmpty()){
                        values.put("id",id);
                        values.put("pw",pw);

                        db.insert("usr",null,values);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setFirebase(int reserved){
        FirebaseDatabase.getInstance().getReference().child("reserved").setValue(reserved);
    }

    public void reserveExist(){

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

            Ref  = firebase.getReference("reserve");
            String key = Ref.push().getKey();

            Map<String, String> postReserve = new HashMap<>();
            postReserve.put("idx",Integer.toString(i));
            postReserve.put("seatnum",Integer.toString(i));
            postReserve.put("reserved",Integer.toString(0));

            DatabaseReference keyRef = Ref.child(key);
            keyRef.setValue(postReserve);
        }

        select_all_reserve();
    }

    public void insert_to_user(String id, String pw){
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id",id);
        values.put("pw",pw);

        db.insert("usr",null,values);

        Log.d("insert","id : " + id);

        Ref  = firebase.getReference("user");
        String key = Ref.push().getKey();

        Map<String, String> postUser = new HashMap<>();
        postUser.put("id",id);
        postUser.put("pw",pw);

        DatabaseReference keyRef = Ref.child(key);
        keyRef.setValue(postUser);
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

    public Cursor select_user(String id) {
        db = helper.getReadableDatabase();
        Cursor c = db.query("usr", null, null, null, null, null, null);

        if(count_userData() == 0)
            return c;
        else
            c.moveToNext();

        int i = 0;

        Log.d("count","count : " + c.getCount());

        while(!c.getString(c.getColumnIndex("id")).equals(id)) {
            i++;

            Log.d("insert","i : " + i);

            if(i == c.getCount())
                break;

            c.moveToNext();
        }

        return c;
    }

    public int count_reserveData() {
        db = helper.getReadableDatabase();

        Cursor c = db.query("reserve", null, null, null, null, null, null);

        return c.getCount();
    }

    public int count_userData(){
        db = helper.getReadableDatabase();

        Cursor c = db.query("usr", null, null, null, null, null, null);

        return c.getCount();
    }

    //update data in DB
    public void update_reserved(int seatnum, int reserved){
        db = helper.getWritableDatabase();

        db.execSQL("Update reserve set reserved = " + reserved + " where seatnum = " + seatnum);

        Log.d("DB", "update complete");

        FirebaseDatabase.getInstance().getReference("reserve").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d("reserve get firebase", "RV : " + snapshot.child("reserved").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //delete data from DB
    public void delete (int idx) {
        db = helper.getWritableDatabase();
        db.delete("reserve","idx=?",new String[]{String.valueOf(idx)});

        Log.i("DB","Delete complete");
    }
}
