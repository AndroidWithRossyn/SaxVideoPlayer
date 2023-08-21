package com.hammerapp.sx.xxplayer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;
import com.hammerapp.sx.xxplayer.BuildConfig;
import com.hammerapp.sx.xxplayer.R;
import com.hammerapp.sx.xxplayer.fragement.FolderVideoFragment;
import com.pesonal.adsdk.AppManage;

import static com.pesonal.adsdk.AppManage.ADMOB_N1;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N1;
import static com.pesonal.adsdk.AppManage.MOPUB_N1;

public class HomeActivity extends AppCompatActivity {
    RelativeLayout start_btn,share_btn,rate_us_btn,native_container,banner_ads;
    String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_SETTINGS,Manifest.permission.MEDIA_CONTENT_CONTROL};
    int PERMISSION_ALL = 303;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        start_btn=findViewById(R.id.start_btn);
        share_btn=findViewById(R.id.share_btn);
        rate_us_btn=findViewById(R.id.rate_us_btn);
        native_container=findViewById(R.id.native_container);
        banner_ads=findViewById(R.id.banner_ads);
        if (!hasPermissions(HomeActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(HomeActivity.this, PERMISSIONS, PERMISSION_ALL);
        }
        AppManage.getInstance(this).show_half_NATIVE((ViewGroup) banner_ads, ADMOB_N1, FACEBOOK_N1, MOPUB_N1,this);
        AppManage.getInstance(this).show_NATIVE((ViewGroup) native_container, ADMOB_N1, FACEBOOK_N1, MOPUB_N1,this);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(HomeActivity.this).show_INTERSTIAL(HomeActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                });

            }
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sax Video Player");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        rate_us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            }
        });

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(Build.VERSION.SDK_INT < 23 || context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }


}
