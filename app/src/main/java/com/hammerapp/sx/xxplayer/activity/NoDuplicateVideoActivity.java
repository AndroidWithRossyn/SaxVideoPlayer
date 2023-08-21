package com.hammerapp.sx.xxplayer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hammerapp.sx.xxplayer.R;


public class NoDuplicateVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_duplicate_video);
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        finish();
    }
}
