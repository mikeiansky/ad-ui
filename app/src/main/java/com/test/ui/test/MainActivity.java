package com.test.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winson.ui.EmptyUIUtils;

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
                EmptyUIUtils.showLoadingView(emptyGroup);
                break;
            case R.id.error:
                EmptyUIUtils.showErrorView(emptyGroup, null);
                break;
            case R.id.empty:
                EmptyUIUtils.showEmptyView(emptyGroup, null);
                break;
            case R.id.load_c:
                View load_c = LayoutInflater.from(this).inflate(R.layout.load_c, emptyGroup, false);
                EmptyUIUtils.showLoadingView(emptyGroup, load_c);
                break;
            case R.id.error_c:
                View error_c = LayoutInflater.from(this).inflate(R.layout.error_c, emptyGroup, false);
                EmptyUIUtils.showLoadingView(emptyGroup, error_c);
                break;
            case R.id.empty_c:
                View empty_c = LayoutInflater.from(this).inflate(R.layout.empty_c, emptyGroup, false);
                EmptyUIUtils.showLoadingView(emptyGroup, empty_c);
                break;
        }
    }
}
