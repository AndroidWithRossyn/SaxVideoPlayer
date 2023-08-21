package com.pesonal.adsdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.pesonal.adsdk.AppManage.mysharedpreferences;

public class ADS_SplashActivity extends AppCompatActivity {

    String bytemode = "";

    private Runnable runnable;
    private Handler refreshHandler;
    boolean is_retry;
    public static boolean need_internet = false;
    private int timeout = 10000;

    private static String TAG = ADS_SplashActivity.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_ads_splash);

    }


    public void ADSinit (final Activity activity, final int cversion, final getDataListner myCallback1) {
        final Dialog dialog = new Dialog (activity);
        dialog.setCancelable (false);
        View view = getLayoutInflater ().inflate (R.layout.retry_layout, null);
        dialog.setContentView (view);
        final TextView retry_buttton = view.findViewById (R.id.retry_buttton);

        if (!isNetworkAvailable ()) {
            is_retry = false;
            dialog.show ();
        }
        SharedPreferences preferences = activity.getSharedPreferences ("ad_pref", 0);
        final SharedPreferences.Editor editor_AD_PREF = preferences.edit ();

        need_internet = preferences.getBoolean ("need_internet", need_internet);

        dialog.dismiss ();
        refreshHandler = new Handler ();
        runnable = new Runnable () {
            @Override
            public void run () {
                if (isNetworkAvailable ()) {
                    is_retry = true;
                    retry_buttton.setText (activity.getString (R.string.retry));
                } else {
                    dialog.show ();
                    is_retry = false;
                    retry_buttton.setText (activity.getString (R.string.connect_internet));
                }
                refreshHandler.postDelayed (this, 1000);
            }
        };

        refreshHandler.postDelayed (runnable, 1000);

        retry_buttton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Log.e (TAG, "onClick: " + "Retry");
                if (is_retry) {
                    Log.e (TAG, "onClick: " + "is_retry" + "true");
                    if (need_internet) {
                        myCallback1.reloadActivity ();
                        Log.e (TAG, "onClick: " + "Reload");
                    } else {
                        Log.e (TAG, "onClick: " + "Sucess");

                        if (dialog.isShowing ()) {
                            dialog.dismiss ();
                        }

                        myCallback1.onsuccess ();
                    }


                } else {
                    //startActivityForResult (new Intent (android.provider.Settings.ACTION_SETTINGS), 0);

                    Toast.makeText (ADS_SplashActivity.this, "Please check internet connection", Toast.LENGTH_SHORT).show ();
                }
            }
        });

        mysharedpreferences = activity.getSharedPreferences (activity.getPackageName (), Context.MODE_PRIVATE);

        Calendar calender = Calendar.getInstance ();
        calender.setTimeZone (TimeZone.getTimeZone ("Asia/Calcutta"));
        SimpleDateFormat df = new SimpleDateFormat ("dd-MMM-yyyy", Locale.US);
        String currentDate = df.format (calender.getTime ());


        int addfdsf123;
        String status = mysharedpreferences.getString ("firsttime", "true");
        final SharedPreferences.Editor editor = mysharedpreferences.edit ();
        if (status.equals ("true")) {
            editor.putString ("date", currentDate).apply ();
            editor.putString ("firsttime", "false").apply ();
            addfdsf123 = 13421;


        } else {
            String date = mysharedpreferences.getString ("date", "");
            if (!currentDate.equals (date)) {
                editor.putString ("date", currentDate).apply ();
                addfdsf123 = 26894;
            } else {
                addfdsf123 = 87332;
            }
        }

        String akbsvl679056 = "7CFD5B16FABD72F0E9378005E0BF62E780BB1EC788E4298C94D8E6536846ED5D6E0CA9E87257C32CBD6547C0D553D30F39CF30144E16B31FD4EA4E5424794582";

        try {
            bytemode = AESSUtils.decryptA (activity, akbsvl679056);

        } catch (Exception e) {
            e.printStackTrace ();
        }

        bytemode = bytemode + "?PHSUGSG6783019KG=" + activity.getPackageName ();
        bytemode = bytemode + "&AFHJNTGDGD563200K=" + getKeyHash (activity);
        bytemode = bytemode + "&DTNHGNH7843DFGHBSA=" + addfdsf123;

        if (BuildConfig.DEBUG) {
            bytemode = bytemode + "&DBMNBXRY4500991G=TRSOFTAG12789I";
        } else {
            bytemode = bytemode + "&DBMNBXRY4500991G=TRSOFTAG82382I";
        }
        Log.e (TAG, "ADSinit: " + bytemode);

        RequestQueue requestQueue = Volley.newRequestQueue (activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, bytemode, null, new Response.Listener<JSONObject> () {
            @Override
            public void onResponse (JSONObject response) {

                Log.e (TAG, "onResponse: " + response.toString ());

                try {
                    boolean status = response.getBoolean ("STATUS");
                    if (status == true) {

                        String need_in = response.getJSONObject ("APP_SETTINGS").getString ("app_needInternet");
                        if (need_in.endsWith ("1")) {
                            need_internet = true;
                        } else {
                            need_internet = false;
                        }
                        editor_AD_PREF.putBoolean ("need_internet", need_internet).apply ();
                        editor_AD_PREF.putString ("MORE_APP", response.getJSONArray ("MORE_APP").toString ()).apply ();

                        Log.e (TAG, "onResponse: " + response.toString ());
                        SharedPreferences.Editor editor1 = mysharedpreferences.edit ();
                        editor1.putString ("response", response.toString ());
                        editor1.apply ();


                    } else {
                        Log.e ("status", "false");
                    }


                } catch (Exception e) {
                    if (need_internet) {
                        dialog.dismiss ();
                        refreshHandler = new Handler ();
                        runnable = new Runnable () {
                            @Override
                            public void run () {
                                if (isNetworkAvailable ()) {
                                    dialog.show ();
                                    is_retry = true;
                                    retry_buttton.setText (activity.getString (R.string.retry));
                                } else {
                                    dialog.show ();
                                    is_retry = false;
                                    retry_buttton.setText (activity.getString (R.string.connect_internet));
                                }
                                refreshHandler.postDelayed (this, 1000);
                            }
                        };
                    } else {
                        myCallback1.onsuccess ();
                    }
                }

                AppManage.getInstance (activity).getResponseFromPref (new getDataListner () {
                    @Override
                    public void onsuccess () {
                        myCallback1.onsuccess ();
                    }

                    @Override
                    public void onUpdate (String url) {
                        myCallback1.onUpdate (url);
                    }

                    @Override
                    public void onRedirect (String url) {
                        myCallback1.onRedirect (url);
                    }

                    @Override
                    public void reloadActivity () {

                    }

                    @Override
                    public void ongetExtradata (JSONObject extraData) {
                        myCallback1.ongetExtradata (extraData);

                    }
                }, cversion);


            }
        }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse (VolleyError error) {

                Log.e (TAG, "onErrorResponse: " + error.getMessage ());

                if (error instanceof TimeoutError) {
                    Log.e (TAG, "onErrorResponse: " + error);
                    myCallback1.onsuccess ();

                } else {
                    if (need_internet) {
                        dialog.dismiss ();
                        refreshHandler = new Handler ();
                        runnable = new Runnable () {
                            @Override
                            public void run () {
                                if (isNetworkAvailable ()) {
                                    is_retry = true;
                                    retry_buttton.setText (activity.getString (R.string.retry));
                                } else {
                                    dialog.show ();
                                    is_retry = false;
                                    retry_buttton.setText (activity.getString (R.string.connect_internet));
                                }
                                refreshHandler.postDelayed (this, 1000);
                            }
                        };
                        //   refreshHandler.postDelayed (runnable, 1000);
                    } else {
                        myCallback1.onsuccess ();
                    }
                }


            }
        });
        jsonObjectRequest.setRetryPolicy (new DefaultRetryPolicy (
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add (jsonObjectRequest);
    }

    public String getKeyHash(Activity activity) {
        PackageInfo info;
        try {
            info = activity.getPackageManager ().getPackageInfo (activity.getPackageName (), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance ("SHA");
                md.update (signature.toByteArray ());
                String something = (Base64.encodeToString (md.digest (), Base64.NO_WRAP));
                return something.replace ("+", "*");
            }
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace ();

        } catch (NoSuchAlgorithmException e) {

        } catch (Exception e) {

        }
        return null;
    }


    private boolean isNetworkAvailable () {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo ();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected ()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        refreshHandler.removeCallbacks (runnable);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        Log.e (TAG, "onActivityResult: " + requestCode);
    }
}