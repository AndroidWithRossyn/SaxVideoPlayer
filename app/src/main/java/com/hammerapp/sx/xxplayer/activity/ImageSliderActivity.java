package com.hammerapp.sx.xxplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hammerapp.sx.xxplayer.R;
import com.hammerapp.sx.xxplayer.adapter.MyCustomPagerAdapter;
import com.hammerapp.sx.xxplayer.comman.Utils;
import com.hammerapp.sx.xxplayer.model.PictureFace;
import com.pesonal.adsdk.AppManage;

import java.io.File;

import static com.pesonal.adsdk.AppManage.ADMOB_B1;
import static com.pesonal.adsdk.AppManage.ADMOB_N1;
import static com.pesonal.adsdk.AppManage.FACEBOOK_B1;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N1;
import static com.pesonal.adsdk.AppManage.MOPUB_B1;

public class ImageSliderActivity extends AppCompatActivity {
   TextView title_img,share_btn;
   int pos;
   ViewPager view_pager;
   RelativeLayout back_btn,banner_ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        title_img=findViewById(R.id.title_img);
        view_pager=findViewById(R.id.view_pager);
        back_btn=findViewById(R.id.back_btn);
        share_btn=findViewById(R.id.share_btn);
        banner_ads=findViewById(R.id.banner_ads);

        pos=getIntent().getIntExtra("name",0);
        title_img.setText(Utils.picture.size()+"/"+pos);
        AppManage.getInstance(this).show_BANNER((ViewGroup)banner_ads, ADMOB_B1, FACEBOOK_B1,MOPUB_B1,this);
        view_pager.setCurrentItem(pos);
        MyCustomPagerAdapter adapter=new MyCustomPagerAdapter(this,pos);
        view_pager.setAdapter(adapter);
        adapter.addData(Utils.picture);


        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos=position+1;
                title_img.setText(Utils.picture.size()+"/"+pos);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.picture.clear();
                finish();
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=view_pager.getCurrentItem();
                PictureFace face=Utils.picture.get(pos);
                shareImage(face.getPicturePath());

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.picture.clear();
    }

    private void shareImage(String img) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        File imageFileToShare = new File(img);
        Uri uri= FileProvider.getUriForFile(getApplicationContext(),getPackageName()+".provider",imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image!"));
    }

}
