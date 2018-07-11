package com.test.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winson.ui.widget.EmptyViewUtils;
import com.winson.ui.widget.RateLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup emptyGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyGroup = findViewById(R.id.empty_panel);

        findViewById(R.id.load).setOnClickListener(this);
        findViewById(R.id.error).setOnClickListener(this);
        findViewById(R.id.empty).setOnClickListener(this);
        findViewById(R.id.load_c).setOnClickListener(this);
        findViewById(R.id.error_c).setOnClickListener(this);
        findViewById(R.id.empty_c).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.load:
                EmptyViewUtils.showLoadingView(emptyGroup);
                break;
            case R.id.error:
                EmptyViewUtils.showErrorView(emptyGroup, null);
                break;
            case R.id.empty:
                EmptyViewUtils.showEmptyView(emptyGroup, null);
                break;
            case R.id.load_c:
                View load_c = LayoutInflater.from(this).inflate(R.layout.load_c, emptyGroup, false);
                EmptyViewUtils.showLoadingView(emptyGroup, load_c);
                break;
            case R.id.error_c:
                View error_c = LayoutInflater.from(this).inflate(R.layout.error_c, emptyGroup, false);
                EmptyViewUtils.showLoadingView(emptyGroup, error_c);
                break;
            case R.id.empty_c:
                View empty_c = LayoutInflater.from(this).inflate(R.layout.empty_c, emptyGroup, false);
                EmptyViewUtils.showLoadingView(emptyGroup, empty_c);
                break;
        }
    }
}
