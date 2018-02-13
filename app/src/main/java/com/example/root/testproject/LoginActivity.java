package com.example.root.testproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 18. 2. 5.
 */

public class LoginActivity extends Activity {

    DBHandler controller;

    EditText id;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        controller = new DBHandler(getApplicationContext());

        id = (EditText)findViewById(R.id.id_edit);
        pw = (EditText)findViewById(R.id.pw_edit);
    }

    @Override
    public void onBackPressed(){
        //super.onBackpressed();
    }

    public void login_b_clicked(View v) {
        String id_value = id.getText().toString();
        String pw_value = pw.getText().toString();

        Log.d("id",id_value);

        Cursor c =controller.select_user(id_value);

        if(c.getCount() != 0) {
            if (c.getString(c.getColumnIndex("id")).equals(id_value) && c.getString(c.getColumnIndex("pw")).equals(pw_value)) {
                Toast.makeText(this, "Login complete", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Please check your id & pw", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please check your id & pw", Toast.LENGTH_LONG).show();
        }
    }

    public void signin_b_clicked(View v) {
        startActivity(new Intent(this, SignActivity.class));
    }
}
