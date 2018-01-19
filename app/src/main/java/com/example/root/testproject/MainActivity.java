package com.example.root.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //mainB_1 is reservation button
    public void mainB_1_clicked(View v) {
        Intent intent = new Intent(this,ReserveActivity.class);
        startActivity(intent);
    }

    //this button is for admin
    public void mainB_2_clicked(View v) {
        ;
    }

    //quit this app
    public void mainB_3_clicked(View v) {
        finish();
    }
}
