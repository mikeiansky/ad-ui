package com.test.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.winson.ui.widget.RateLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RateLayout rateLayout = new RateLayout(this);
    }
}
