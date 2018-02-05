package com.example.root.testproject;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by root on 18. 2. 5.
 */

public class SignActivity extends Activity {

    DBHandler controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_screen);

        controller = new DBHandler(getApplicationContext());
    }
}
