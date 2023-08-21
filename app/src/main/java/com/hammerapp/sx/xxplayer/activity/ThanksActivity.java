package com.hammerapp.sx.xxplayer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hammerapp.sx.xxplayer.R;

public class ThanksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
    }

    @Override
    public void onBackPressed() {

        finishAffinity();
    }
}
