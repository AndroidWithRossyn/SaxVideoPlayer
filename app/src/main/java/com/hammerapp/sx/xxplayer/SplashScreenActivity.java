package com.hammerapp.sx.xxplayer;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Dialog;
        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.graphics.drawable.ColorDrawable;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.util.Log;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.hammerapp.sx.xxplayer.activity.GameAndPlayScreenActivity;
        import com.hammerapp.sx.xxplayer.activity.StartActivity;
        import com.pesonal.adsdk.ADS_SplashActivity;
        import com.pesonal.adsdk.AppManage;
        import com.pesonal.adsdk.getDataListner;

        import org.json.JSONObject;

        import static com.pesonal.adsdk.AppManage.ADMOB_I1;
        import static com.pesonal.adsdk.AppManage.FACEBOOK_I1;
        import static com.pesonal.adsdk.AppManage.MOPUB_I1;

public class SplashScreenActivity extends ADS_SplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        AppManage.getInstance(SplashScreenActivity.this).loadintertialads(SplashScreenActivity.this, ADMOB_I1, FACEBOOK_I1,MOPUB_I1);
        ADSinit(SplashScreenActivity.this,getCurrentVersionCode(), new getDataListner() {
            @Override
            public void onsuccess() {
                AppManage.getInstance(SplashScreenActivity.this).show_INTERSTIAL(SplashScreenActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        AppManage.getInstance(SplashScreenActivity.this).show_INTERSTIAL(SplashScreenActivity.this, new AppManage.MyCallback() {
                            public void callbackCall() {

                                startActivity(new Intent(SplashScreenActivity.this, StartActivity.class));
                                finish();
                            }
                        });


                    }
                });

            }

            @Override
            public void onUpdate(String url) {
                Log.e("my_log", "onUpdate: "+url );
                showUpdateDialog(url);
            }

            @Override
            public void onRedirect(String url) {
                Log.e("my_log", "onRedirect: "+url );
                showRedirectDialog(url);
            }

            @Override
            public void reloadActivity() {


                startActivity(new Intent(SplashScreenActivity.this, SplashScreenActivity.class));
                finish();
            }

            @Override
            public void ongetExtradata(JSONObject extraData) {
                Log.e("my_log", "ongetExtradata: "+extraData.toString() );
            }
        });


    }

    public void showUpdateDialog(final String url) {

        final Dialog dialog = new Dialog(SplashScreenActivity.this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update = view.findViewById(R.id.update);
        TextView txt_title = view.findViewById(R.id.txt_title);
        TextView txt_decription = view.findViewById(R.id.txt_decription);

        update.setText("Update Now");
        txt_title.setText("Update our new app now and enjoy");
        txt_decription.setText("");
        txt_decription.setVisibility(View.GONE);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri marketUri = Uri.parse(url);
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException ignored1) {
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }
    public void showRedirectDialog(final String url) {

        final Dialog dialog = new Dialog(SplashScreenActivity.this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update = view.findViewById(R.id.update);
        TextView txt_title = view.findViewById(R.id.txt_title);
        TextView txt_decription = view.findViewById(R.id.txt_decription);

        update.setText("Install Now");
        txt_title.setText("Install our new app now and enjoy");
        txt_decription.setText("We have transferred our server, so install our new app by clicking the button below to enjoy the new features of app.");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri marketUri = Uri.parse(url);
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException ignored1) {
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }
    public int getCurrentVersionCode() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getPackageName(), 0);
            return info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
