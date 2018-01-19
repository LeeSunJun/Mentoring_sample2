package com.example.root.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by root on 18. 1. 17.
 */

public class DetailActivity extends Activity{

    int seatNum; //for sending info about state of seat with the seat number
    int num; //for getting seat number from previous Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        Intent intent = getIntent();
        num = intent.getIntExtra("num",-1);

        if(num < 0)
            finish();

        seatNum = NtoSN(num);

        if(seatNum < 0)
            finish();
    }

    //reserve
    public void ok_b_clicked(View v) {
        seatNum += 10;

        Intent returnIntent = new Intent();
        returnIntent.putExtra("seatNum",seatNum);
        setResult(Activity.RESULT_OK,returnIntent);

        finish();
    }

    //not reserve
    public void cancel_b_clicked(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("seatNum",seatNum);
        setResult(Activity.RESULT_OK,returnIntent);

        finish();
    }

    //There are 2 valuable : num & seatNum
    //change the format from num to seatNum
    public int NtoSN(int num) {
        switch (num) {
            case 11:
                return 0;
            case 12:
                return 1;
            case 21:
                return 2;
            case 22:
                return 3;
            case 31:
                return 4;
            case 32:
                return 5;
        }

        //-1 mean error
        return -1;
    }
}
