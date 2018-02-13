package com.example.root.testproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 18. 2. 5.
 */

public class SignActivity extends Activity {

    DBHandler controller;

    EditText id;
    EditText pw;
    EditText pwc;

    String id_value;
    String pw_value;
    String pwc_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_screen);

        controller = new DBHandler(getApplicationContext());

        id = (EditText)findViewById(R.id.id_edit);
        pw = (EditText)findViewById(R.id.pw_edit);
        pwc = (EditText)findViewById(R.id.pwc_edit);
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
    }

    public void signup_b_clicked(View v) {
        get_txt();

        Log.d("login","id : " + id_value + "  /pw : " + pw_value + "  /pwc : " + pwc_value);

        if(!id_value.isEmpty() && pw_value.equals(pwc_value)) {
            Toast.makeText(this,"Signed well",Toast.LENGTH_LONG).show();

            controller.insert_to_user(id_value,pw_value);

            finish();
        } else {
            Toast.makeText(this,"Please check your id & pw(pwc)",Toast.LENGTH_LONG).show();
        }
    }

    public void cancel_b_clicked(View v) {
        finish();
    }

    public void get_txt(){
        id_value = id.getText().toString();
        pw_value = pw.getText().toString();
        pwc_value = pwc.getText().toString();
    }
}
