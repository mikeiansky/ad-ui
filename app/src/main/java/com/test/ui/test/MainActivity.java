package com.test.ui.test;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.winson.widget.DialogUtils;
import com.winson.widget.EmptyViewUtils;
import com.winson.widget.ImageUtils;
import com.winson.widget.PhotoSelectUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewGroup emptyGroup;
    ImageView testRoundIV;
    PhotoSelectUtils photoSelectUtils;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        photoSelectUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoSelectUtils.onActivityResultForCrop(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoSelectUtils = new PhotoSelectUtils(this);
        photoSelectUtils.setOnPhotoSelectListener(new PhotoSelectUtils.OnPhotoSelectListener() {
            @Override
            public void onPhotoSelect(String path) {
                Log.d("TAG", "onPhotoSelect ------> " + path);
            }
        });
        emptyGroup = findViewById(R.id.empty_panel);

        findViewById(R.id.load).setOnClickListener(this);
        findViewById(R.id.error).setOnClickListener(this);
        findViewById(R.id.empty).setOnClickListener(this);
        findViewById(R.id.load_c).setOnClickListener(this);
        findViewById(R.id.error_c).setOnClickListener(this);
        findViewById(R.id.empty_c).setOnClickListener(this);

        testRoundIV = findViewById(R.id.iv_test_round);
//        testRoundIV.setImageBitmap(ImageUtils.convertRoundBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.test_round),200));

        testRoundIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bitmap bitmap = ImageUtils.convertRoundBitmap(MainActivity.this, R.mipmap.test_round,
//                testRoundIV.getWidth(), testRoundIV.getHeight(), 50, ImageView.ScaleType.CENTER_INSIDE);
//                testRoundIV.setImageBitmap(bitmap);

            }
        });

        testRoundIV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Bitmap bitmap = ImageUtils.convertRoundBitmap(MainActivity.this, R.mipmap.test_round,
                        testRoundIV.getWidth(), testRoundIV.getHeight(), 50, ImageView.ScaleType.CENTER_CROP);
                testRoundIV.setImageBitmap(bitmap);
                testRoundIV.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.load:
//                EmptyViewUtils.showLoadingView(emptyGroup);

//                photoSelectUtils.selectFromPhotoAlbum();
//                photoSelectUtils.selectByCamera();
//                photoSelectUtils.showPhotoSelectActionSheet(view.getContext());

                new DialogUtils.IOSBuilder(view.getContext())
                        .setTitleText(R.string.album)
                        .setMessageText("Welcome to ciwei!Welcome to ciwei!Welcome to ciwei!Welcome to ciwei!Welcome to ciwei!Welcome to ciwei!")
                        .setIsSingleNotify(true)
                        .setDialogCallback(new DialogUtils.DialogCallback() {
                            @Override
                            public void onNegativeClick(Dialog dialog) {

                            }

                            @Override
                            public void onPositiveClick(Dialog dialog) {

                            }
                        })
                        .show();

                break;
            case R.id.error:

                final Dialog dialog = new Dialog(this, com.winson.widget.R.style.IOSDialogStyle);
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 245, getResources().getDisplayMetrics());
                View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
                contentView.setMinimumWidth(width);

                dialog.setContentView(contentView);
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.x = 0;
                lp.y = 0;
                dialogWindow.setAttributes(lp);
                dialog.show();

//                EmptyViewUtils.showErrorView(emptyGroup, null);
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
