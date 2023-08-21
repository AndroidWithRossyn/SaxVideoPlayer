package com.hammerapp.sx.xxplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hammerapp.sx.xxplayer.R;
import com.pesonal.adsdk.AppManage;

import static com.pesonal.adsdk.AppManage.ADMOB_N1;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N1;
import static com.pesonal.adsdk.AppManage.MOPUB_I1;
import static com.pesonal.adsdk.AppManage.MOPUB_N1;


public class StartActivity extends AppCompatActivity {
   RelativeLayout start_btn;
   RelativeLayout native_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        start_btn=findViewById(R.id.start_btn);
        native_container=findViewById(R.id.native_container);
        AppManage.getInstance(this).show_NATIVE((ViewGroup) findViewById(R.id.native_container), ADMOB_N1, FACEBOOK_N1, MOPUB_N1,StartActivity.this);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppManage.getInstance(StartActivity.this).show_INTERSTIAL(StartActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(),GameAndPlayScreenActivity.class));
                        finish();
                    }
                });

            }
        });
    }


}
