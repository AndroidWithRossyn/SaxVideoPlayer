package com.pesonal.adsdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
import com.mopub.nativeads.AdapterHelper;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class AppManage {

    public static int count_banner = -1;
    public static int count_native = -1;
    public static int count_click = -1;

    public static int count_click_for_alt = -1;
    public static String app_privacyPolicyLink = "";
    public static int app_needInternet = 0;
    public static int app_updateAppDialogStatus = 0;
    public static int app_dialogBeforeAdShow = 0;
    public static String app_versionCode = "";
    public static int app_redirectOtherAppStatus = 0;
    public static String app_newPackageName = "";
    public static int app_adShowStatus = 1;
    public static int app_howShowAd = 0;
    public static String app_adPlatformSequence = "";
    public static String app_alernateAdShow = "";
    public static int app_mainClickCntSwAd = 0;
    public static int app_innerClickCntSwAd = 0;

    public static int app_showAdLevel = 0;

    public static int app_nativehowShowAd = 0;
    public static String app_nativePlatforms = "";

    public static int app_bannerhowShowAd = 0;
    public static String app_bannerPlatforms = "";

    public static int app_interstitialhowShowAd = 0;
    public static String app_interstitialPlatforms = "";

    public static String ADMOB_APPID = "";
    public static String ADMOB_I1 = "";
    public static String ADMOB_I2 = "";
    public static String ADMOB_B1 = "";
    public static String ADMOB_B2 = "";
    public static String ADMOB_N1 = "";
    public static String ADMOB_N2 = "";
    public static String ADMOB_N3 = "";
    public static String ADMOB_AppOpen1 = "";
    public static String FACEBOOK_I1 = "";
    public static String FACEBOOK_I2 = "";
    public static String FACEBOOK_B1 = "";
    public static String FACEBOOK_B2 = "";
    public static String FACEBOOK_NB1 = "";
    public static String FACEBOOK_NB2 = "";
    public static String FACEBOOK_N1 = "";
    public static String FACEBOOK_N2 = "";
    public static String FACEBOOK_N3 = "";
    public static int admob_AdStatus = 0;
    public static int facebook_AdStatus = 0;
    public static SharedPreferences mysharedpreferences;
    public static int ad_dialog_time_in_second = 2;
    public boolean isLast = false;
    public  boolean is_fb_running = false;
    public  boolean is_am_running = false; //admob mate



    public Context getActivity() {
        return activity;
    }

    public void setActivity(Context activity) {
        this.activity = activity;
    }

    public Context activity;
    static MyCallback myCallback;
    private static AppManage mInstance;
    public InterstitialAd interstitial1;
    String admob_b, facebook_nb, facebook_b;
    String admob_n, facebook_n;

    ArrayList<String> banner_sequence = new ArrayList<>();
    ArrayList<String> native_sequence = new ArrayList<>();
    ArrayList<String> interstitial_sequence = new ArrayList<>();
    boolean is_foursesully;
    private com.facebook.ads.InterstitialAd fbinterstitialAd1;
    private Dialog dialog;
    private AdView adView;


    private MoPubView moPubView;
    private MoPubInterstitial moPubInterstitial1;
    private MoPubNative moPubNative;
    private AdapterHelper adapterHelper;
    public static String MOPUB_APPID = "";
    public static String MOPUB_I1 = "";
    public static String MOPUB_I2 = "";
    public static String MOPUB_B1 = "";
    public static String MOPUB_B2 = "";
    public static String MOPUB_N1 = "";
    public static String MOPUB_N2 = "";
    public static String MOPUB_N3 = "";
    public static String MOPUB_NB1 = "";
    public static String MOPUB_NB2 = "";
    public static int mopub_AdStatus = 0;
    private String mopub_b = "";
    private String mopub_n = "";
    private String mopub_nb = "";

    public static long app_amDelay = 0;
    public static long app_fbDelay = 0;


    public AppManage(Context activity) {
        setActivity(activity);
        mysharedpreferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        getResponseFromPref();

    }

    public static AppManage getInstance(Context activity) {
        if (mInstance == null) {
            mInstance = new AppManage(activity);
            return mInstance;
        } else {

            mInstance.setActivity(activity);
            return mInstance;

        }

    }

    public static boolean hasActiveInternetConnection(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void getResponseFromPref() {
        String response1 = mysharedpreferences.getString("response", "");
        if (!response1.isEmpty()) {
            try {
                JSONObject response = new JSONObject(response1);
                JSONObject settingsJsonObject = response.getJSONObject("APP_SETTINGS");

                app_privacyPolicyLink = settingsJsonObject.getString("app_privacyPolicyLink");
                app_needInternet = settingsJsonObject.getInt("app_needInternet");
                app_updateAppDialogStatus = settingsJsonObject.getInt("app_updateAppDialogStatus");
                app_versionCode = settingsJsonObject.getString("app_versionCode");

                app_redirectOtherAppStatus = settingsJsonObject.getInt("app_redirectOtherAppStatus");
                app_newPackageName = settingsJsonObject.getString("app_newPackageName");
                app_dialogBeforeAdShow = settingsJsonObject.getInt("app_dialogBeforeAdShow");
                app_adShowStatus = settingsJsonObject.getInt("app_adShowStatus");
                app_howShowAd = settingsJsonObject.getInt("app_howShowAd");
                app_adPlatformSequence = settingsJsonObject.getString("app_adPlatformSequence");
                app_alernateAdShow = settingsJsonObject.getString("app_alernateAdShow");
                app_mainClickCntSwAd = settingsJsonObject.getInt("app_mainClickCntSwAd");
                app_innerClickCntSwAd = settingsJsonObject.getInt("app_innerClickCntSwAd");
                app_amDelay = settingsJsonObject.getInt("app_amDelay");
                app_fbDelay = settingsJsonObject.getInt("app_fbDelay");

                app_showAdLevel = settingsJsonObject.getInt("app_showAdLevel");
                app_nativehowShowAd = settingsJsonObject.getInt("app_nativehowShowAd");
                app_nativePlatforms = settingsJsonObject.getString("app_nativePlatforms");
                app_bannerhowShowAd = settingsJsonObject.getInt("app_bannerhowShowAd");
                app_bannerPlatforms = settingsJsonObject.getString("app_bannerPlatforms");
                app_interstitialhowShowAd = settingsJsonObject.getInt("app_interstitialhowShowAd");
                app_interstitialPlatforms = settingsJsonObject.getString("app_interstitialPlatforms");

                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putString("app_privacyPolicyLink", app_privacyPolicyLink);
                editor.putInt("app_needInternet", app_needInternet);
                editor.putInt("app_updateAppDialogStatus", app_updateAppDialogStatus);
                editor.putString("app_versionCode", app_versionCode);
                editor.putInt("app_redirectOtherAppStatus", app_redirectOtherAppStatus);
                editor.putString("app_newPackageName", app_newPackageName);
                editor.putInt("app_adShowStatus", app_adShowStatus);
                editor.putInt("app_howShowAd", app_howShowAd);
                editor.putString("app_adPlatformSequence", app_adPlatformSequence);
                editor.putString("app_alernateAdShow", app_alernateAdShow);
                editor.putInt("app_mainClickCntSwAd", app_mainClickCntSwAd);
                editor.putInt("app_innerClickCntSwAd", app_innerClickCntSwAd);
                editor.putLong("app_amDelay", app_amDelay);
                editor.putLong("app_fbDelay", app_fbDelay);

                editor.putInt("app_showAdLevel", app_showAdLevel);
                editor.putInt("app_nativehowShowAd", app_nativehowShowAd);
                editor.putString("app_nativePlatforms", app_nativePlatforms);
                editor.putInt("app_bannerhowShowAd", app_bannerhowShowAd);
                editor.putString("app_bannerPlatforms", app_bannerPlatforms);
                editor.putInt("app_interstitialhowShowAd", app_interstitialhowShowAd);
                editor.putString("app_interstitialPlatforms", app_interstitialPlatforms);

                editor.commit();

                JSONObject AdmobJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Admob");
                admob_AdStatus = AdmobJsonObject.getInt("ad_showAdStatus");
                ADMOB_APPID = AdmobJsonObject.getString("AppID");
                ADMOB_B1 = AdmobJsonObject.getString("Banner1");
                ADMOB_B2 = AdmobJsonObject.getString("Banner2");
                ADMOB_I1 = AdmobJsonObject.getString("Interstitial1");
                ADMOB_I2 = AdmobJsonObject.getString("Interstitial2");
                ADMOB_N1 = AdmobJsonObject.getString("Native1");
                ADMOB_N2 = AdmobJsonObject.getString("Native2");

                if (AdmobJsonObject != null) {


                    for (String key : iterate(AdmobJsonObject.keys())) {
                        // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                        //    Log.e ("Ads", "getResponseFromPref: " + key);
                        if (key.equalsIgnoreCase("Native3")) {
                            ADMOB_N3 = AdmobJsonObject.getString("Native3");
                        } else if (key.equalsIgnoreCase("AppOpen1")) {
                            ADMOB_AppOpen1 = AdmobJsonObject.getString("AppOpen1");

                        }
                    }
                }

                JSONObject FBJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Facebookaudiencenetwork");
                facebook_AdStatus = FBJsonObject.getInt("ad_showAdStatus");

                FACEBOOK_B1 = FBJsonObject.getString("Banner1");
                FACEBOOK_B2 = FBJsonObject.getString("Banner2");
                FACEBOOK_NB1 = FBJsonObject.getString("NativeBanner1");
                FACEBOOK_NB2 = FBJsonObject.getString("NativeBanner2");
                FACEBOOK_I1 = FBJsonObject.getString("Interstitial1");
                FACEBOOK_I2 = FBJsonObject.getString("Interstitial2");
                FACEBOOK_N1 = FBJsonObject.getString("Native1");
                FACEBOOK_N2 = FBJsonObject.getString("Native2");
                if (FBJsonObject != null) {

                    for (String key : iterate(FBJsonObject.keys())) {
                        // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                        //    Log.e ("Ads", "getResponseFromPref: " + key);
                        if (key.equalsIgnoreCase("Native3")) {
                            FACEBOOK_N3 = FBJsonObject.getString("Native3");
                        }
                    }
                }

                JSONObject MoPubJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("MoPub");
                MOPUB_APPID = MoPubJsonObject.getString("AppID");
                mopub_AdStatus = MoPubJsonObject.getInt("ad_showAdStatus");
                MOPUB_B1 = MoPubJsonObject.getString("Banner1");
                MOPUB_B2 = MoPubJsonObject.getString("Banner2");
                MOPUB_I1 = MoPubJsonObject.getString("Interstitial1");
                MOPUB_I2 = MoPubJsonObject.getString("Interstitial2");
                MOPUB_N1 = MoPubJsonObject.getString("Native1");
                MOPUB_N2 = MoPubJsonObject.getString("Native2");

                if (MoPubJsonObject != null) {

                    for (String key : iterate(MoPubJsonObject.keys())) {
                        // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                        // Log.e ("Ads", "getResponseFromPref: " + key);
                        if (key.equalsIgnoreCase("Native3")) {
                            MOPUB_N3 = MoPubJsonObject.getString("Native3");
                            //  Log.e ("Ads", "getResponseFromPref: " + "Value");
                        } else if (key.equalsIgnoreCase("NativeBanner1")) {
                            MOPUB_NB1 = MoPubJsonObject.getString("NativeBanner1");
                        } else if (key.equalsIgnoreCase("NativeBanner2")) {
                            MOPUB_NB2 = MoPubJsonObject.getString("NativeBanner2");
                        }
                    }
                }


            } catch (Exception e) {
                Log.e("rrrrr", "" + e.getMessage());
            }

        } else {
            Log.e("Ads", "getResponseFromPref: " + "Empty");
        }

    }

    public List<MoreApp_Data> get_MoreAppData() {
        List<MoreApp_Data> data = new ArrayList<>();
        SharedPreferences preferences = activity.getSharedPreferences("ad_pref", 0);
        try {

            JSONArray array = new JSONArray(preferences.getString("MORE_APP", ""));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                data.add(new MoreApp_Data(object.getString("app_id"), object.getString("app_name"), object.getString("app_packageName"), object.getString("app_logo"), object.getString("app_status")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }


    private static void initAd(Context activity) {
        if (admob_AdStatus == 1) {
            MobileAds.initialize(activity, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
        }

        if (facebook_AdStatus == 1) {
            AudienceNetworkAds.initialize(activity);

            //  AdSettings.addTestDevice ("dd28c2e1-11bb-440d-83e8-ff517767eef7");
            AdSettings.addTestDevice("ff13c1c8-b60d-4576-9888-b80e7e99e6c2");
            //    AdSettings.addTestDevice ("HASH_ID");
        }
    }

    private static boolean checkUpdate(int cversion) {


        if (mysharedpreferences.getInt("app_updateAppDialogStatus", 0) == 1) {
            String versions = mysharedpreferences.getString("app_versionCode", "");
            Log.e("Ads", "checkUpdate: " + versions);
            String str[] = versions.split(",");

            if (str != null) {
                Log.e("Ads", "checkUpdate: " + str[0] + " " + cversion);
            }

            try {

                if (Arrays.asList(str).contains(cversion + "")) {
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    public void getResponseFromPref(getDataListner listner, int cversion) {
        String response1 = mysharedpreferences.getString("response", "");
        if (!response1.isEmpty()) {
            try {
                JSONObject response = new JSONObject(response1);
                JSONObject settingsJsonObject = response.getJSONObject("APP_SETTINGS");

                app_privacyPolicyLink = settingsJsonObject.getString("app_privacyPolicyLink");
                app_needInternet = settingsJsonObject.getInt("app_needInternet");
                app_updateAppDialogStatus = settingsJsonObject.getInt("app_updateAppDialogStatus");
                app_versionCode = settingsJsonObject.getString("app_versionCode");

                app_redirectOtherAppStatus = settingsJsonObject.getInt("app_redirectOtherAppStatus");
                app_newPackageName = settingsJsonObject.getString("app_newPackageName");
                app_dialogBeforeAdShow = settingsJsonObject.getInt("app_dialogBeforeAdShow");
                app_adShowStatus = settingsJsonObject.getInt("app_adShowStatus");
                app_howShowAd = settingsJsonObject.getInt("app_howShowAd");
                app_adPlatformSequence = settingsJsonObject.getString("app_adPlatformSequence");
                app_alernateAdShow = settingsJsonObject.getString("app_alernateAdShow");
                app_mainClickCntSwAd = settingsJsonObject.getInt("app_mainClickCntSwAd");
                app_innerClickCntSwAd = settingsJsonObject.getInt("app_innerClickCntSwAd");
                app_amDelay = settingsJsonObject.getInt("app_amDelay");
                app_fbDelay = settingsJsonObject.getInt("app_fbDelay");

                app_showAdLevel = settingsJsonObject.getInt("app_showAdLevel");
                app_nativehowShowAd = settingsJsonObject.getInt("app_nativehowShowAd");
                app_nativePlatforms = settingsJsonObject.getString("app_nativePlatforms");
                app_bannerhowShowAd = settingsJsonObject.getInt("app_bannerhowShowAd");
                app_bannerPlatforms = settingsJsonObject.getString("app_bannerPlatforms");
                app_interstitialhowShowAd = settingsJsonObject.getInt("app_interstitialhowShowAd");
                app_interstitialPlatforms = settingsJsonObject.getString("app_interstitialPlatforms");

                Log.e("Ads", "getResponseFromPref: app_showAdLevel " + app_showAdLevel);

                SharedPreferences.Editor editor = mysharedpreferences.edit();
                editor.putString("app_privacyPolicyLink", app_privacyPolicyLink);
                editor.putInt("app_needInternet", app_needInternet);
                editor.putInt("app_updateAppDialogStatus", app_updateAppDialogStatus);
                editor.putString("app_versionCode", app_versionCode);
                editor.putInt("app_redirectOtherAppStatus", app_redirectOtherAppStatus);
                editor.putString("app_newPackageName", app_newPackageName);
                editor.putInt("app_adShowStatus", app_adShowStatus);
                editor.putInt("app_howShowAd", app_howShowAd);
                editor.putString("app_adPlatformSequence", app_adPlatformSequence);
                editor.putString("app_alernateAdShow", app_alernateAdShow);
                editor.putInt("app_mainClickCntSwAd", app_mainClickCntSwAd);
                editor.putInt("app_innerClickCntSwAd", app_innerClickCntSwAd);
                editor.putLong("app_amDelay", app_amDelay);
                editor.putLong("app_fbDelay", app_fbDelay);

                editor.putInt("app_showAdLevel", app_showAdLevel);
                editor.putInt("app_nativehowShowAd", app_nativehowShowAd);
                editor.putString("app_nativePlatforms", app_nativePlatforms);
                editor.putInt("app_bannerhowShowAd", app_bannerhowShowAd);
                editor.putString("app_bannerPlatforms", app_bannerPlatforms);
                editor.putInt("app_interstitialhowShowAd", app_interstitialhowShowAd);
                editor.putString("app_interstitialPlatforms", app_interstitialPlatforms);

                editor.commit();

                JSONObject AdmobJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Admob");
                admob_AdStatus = AdmobJsonObject.getInt("ad_showAdStatus");
                ADMOB_APPID = AdmobJsonObject.getString("AppID");
                ADMOB_B1 = AdmobJsonObject.getString("Banner1");
                ADMOB_B2 = AdmobJsonObject.getString("Banner2");
                ADMOB_I1 = AdmobJsonObject.getString("Interstitial1");
                ADMOB_I2 = AdmobJsonObject.getString("Interstitial2");
                ADMOB_N1 = AdmobJsonObject.getString("Native1");
                ADMOB_N2 = AdmobJsonObject.getString("Native2");

                if (AdmobJsonObject != null) {

                    for (String key : iterate(AdmobJsonObject.keys())) {
                        // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                        // Log.e ("Ads", "getResponseFromPref: " + key);
                        if (key.equalsIgnoreCase("Native3")) {
                            ADMOB_N3 = AdmobJsonObject.getString("Native3");
                        } else if (key.equalsIgnoreCase("AppOpen1")) {
                            ADMOB_AppOpen1 = AdmobJsonObject.getString("AppOpen1");

                        }
                    }

                }

                JSONObject FBJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("Facebookaudiencenetwork");
                facebook_AdStatus = FBJsonObject.getInt("ad_showAdStatus");
                FACEBOOK_B1 = FBJsonObject.getString("Banner1");
                FACEBOOK_B2 = FBJsonObject.getString("Banner2");
                FACEBOOK_NB1 = FBJsonObject.getString("NativeBanner1");
                FACEBOOK_NB2 = FBJsonObject.getString("NativeBanner2");
                FACEBOOK_I1 = FBJsonObject.getString("Interstitial1");
                FACEBOOK_I2 = FBJsonObject.getString("Interstitial2");
                FACEBOOK_N1 = FBJsonObject.getString("Native1");
                FACEBOOK_N2 = FBJsonObject.getString("Native2");
                if (FBJsonObject != null) {


                    for (String key : iterate(FBJsonObject.keys())) {
                        // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                        // Log.e ("Ads", "getResponseFromPref: " + key);
                        if (key.equalsIgnoreCase("Native3")) {
                            FACEBOOK_N3 = FBJsonObject.getString("Native3");
                        }
                    }
                }

                JSONObject MoPubJsonObject = response.getJSONObject("PLACEMENT").getJSONObject("MoPub");
                MOPUB_APPID = MoPubJsonObject.getString("AppID");
                mopub_AdStatus = MoPubJsonObject.getInt("ad_showAdStatus");
                MOPUB_B1 = MoPubJsonObject.getString("Banner1");
                MOPUB_B2 = MoPubJsonObject.getString("Banner2");
                MOPUB_I1 = MoPubJsonObject.getString("Interstitial1");
                MOPUB_I2 = MoPubJsonObject.getString("Interstitial2");
                MOPUB_N1 = MoPubJsonObject.getString("Native1");
                MOPUB_N2 = MoPubJsonObject.getString("Native2");
                if (MoPubJsonObject != null) {
                    Iterator<String> keys = MoPubJsonObject.keys();


                    for (String key : iterate(MoPubJsonObject.keys())) {
                        // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.
                        //  Log.e ("Ads", "getResponseFromPref: " + key);
                        if (key.equalsIgnoreCase("Native3")) {
                            MOPUB_N3 = MoPubJsonObject.getString("Native3");
                            Log.e("Ads", "getResponseFromPref: " + "Value");
                        } else if (key.equalsIgnoreCase("NativeBanner1")) {
                            MOPUB_NB1 = MoPubJsonObject.getString("NativeBanner1");
                        } else if (key.equalsIgnoreCase("NativeBanner2")) {
                            MOPUB_NB2 = MoPubJsonObject.getString("NativeBanner2");
                        }
                    }
                }


                listner.ongetExtradata(response.getJSONObject("EXTRA_DATA"));


            } catch (Exception e) {

                Log.e("Ads", "getResponseFromPref: Exception: " + e.getMessage() + " " + e.toString());
            }

            if (app_redirectOtherAppStatus == 1) {
                String redirectNewPackage = mysharedpreferences.getString("app_newPackageName", "");
                listner.onRedirect(redirectNewPackage);
            } else if (app_updateAppDialogStatus == 1 && checkUpdate(cversion)) {
                listner.onUpdate("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
            } else {
                listner.onsuccess();
                initAd(getActivity());
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
            }
        }
    }

    public void loadintertialads(Activity activity, String google_i, String facebook_i, String mopub_i) {

        if (app_adShowStatus == 0) {
            return;
        }

        if (admob_AdStatus == 1 && !google_i.isEmpty()) {

            loadAdmobInterstitial(activity, google_i);

        }

        if (facebook_AdStatus == 1 && !facebook_i.isEmpty()) {
            loadFacebookInterstitial(activity, facebook_i);
        }

        if (mopub_AdStatus == 1 && !mopub_i.isEmpty()) {


            loadMoPubInterstitial(activity, mopub_i);
        }


    }


    private void loadFacebookInterstitial(Activity activity, String facebook_i) {


        fbinterstitialAd1 = new com.facebook.ads.InterstitialAd(activity, facebook_i);
        AdSettings.addTestDevice("dd28c2e1-11bb-440d-83e8-ff517767eef7");


        fbinterstitialAd1.loadAd(fbinterstitialAd1.buildLoadAdConfig().withAdListener(new AbstractAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                super.onError(ad, error);
                Log.e("Ads", "onError: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("Ads", "onAdLoaded: " + "FbInterstitial");
                super.onAdLoaded(ad);
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                super.onInterstitialDismissed(ad);
                //  fbinterstitialAd1.loadAd ();

                if (!isLast) {
                    delayFBLoads();
                }
                interstitialCallBack();
            }
        }).build());
    }

    private void loadAdmobInterstitial(Activity activity, String google_i) {
        this.interstitial1 = new InterstitialAd(activity);
        this.interstitial1.setAdUnitId(google_i);
        interstitial1.loadAd(new AdRequest.Builder().addTestDevice("429C0FF2EBDAD6A5F043454557EF10FF").build());
        this.interstitial1.setAdListener(new AdListener() {
            public void onAdLoaded() {

                Log.e("Ads", "onAdLoaded: " + "Admob Interstitial");

            }

            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            public void onAdClosed() {
                super.onAdClosed();

                //  interstitial1.loadAd (new AdRequest.Builder ().build ());

                if (!isLast) {
                    delayAdmobInterLoads();
                }


                interstitialCallBack();

            }

            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

    private void loadMoPubInterstitial(Activity activity, String mopub_i) {

        moPubInterstitial1 = new MoPubInterstitial(activity, mopub_i);
        moPubInterstitial1.setInterstitialAdListener(new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                Log.e("Ads", "onInterstitialLoaded: " + "MoPub interstitial");

            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                Log.e("Ads", "onInterstitialFailed: " + errorCode.toString());
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {

            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {

            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {

                if (!isLast) {

                    moPubInterstitial1.load();
                }

                interstitialCallBack();
            }
        });
        moPubInterstitial1.load();

    }

    public void show_INTERSTIAL(Context context, MyCallback myCallback) {
        displayInterstitial(context, myCallback, false, 0);
    }

    public void show_INTERSTIAL(Context context, MyCallback myCallback, boolean is_foursesully) {
        displayInterstitial(context, myCallback, is_foursesully, 0);
    }

    public void show_INTERSTIAL(Context context, MyCallback myCallback, int how_many_clicks) {
        displayInterstitial(context, myCallback, false, how_many_clicks);
    }

    public void show_INTERSTIAL(Context context, MyCallback myCallback, boolean is_foursesully, int how_many_clicks) {
        displayInterstitial(context, myCallback, is_foursesully, how_many_clicks);
    }

    public void show_INTERSTIAL(Context context, MyCallback myCallback, int how_many_clicks, boolean is_foursesully) {
        displayInterstitial(context, myCallback, is_foursesully, how_many_clicks);
    }

    public void displayInterstitial(Context context, MyCallback myCallback2, boolean is_foursesully, int how_many_clicks) {
        this.myCallback = myCallback2;
        this.is_foursesully = is_foursesully;
        count_click++;

        if (app_adShowStatus == 0) {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
            return;
        }
        if (how_many_clicks != 0) {
            if (count_click % how_many_clicks != 0) {
                if (myCallback != null) {
                    myCallback.callbackCall();
                    myCallback = null;
                }
                return;
            }
        }

        count_click_for_alt++;


      /*  int app_howShowAd = mysharedpreferences.getInt ("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString ("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString ("app_alernateAdShow", "");*/

        int app_howShowAd = 0;
        String adPlatformSequence = "";
        String alernateAdShow = "";

        if (mysharedpreferences.getInt("app_showAdLevel", 0) == 1) {
            app_howShowAd = mysharedpreferences.getInt("app_interstitialhowShowAd", 0);
            if (app_howShowAd == 0) {
                adPlatformSequence = mysharedpreferences.getString("app_interstitialPlatforms", "");
            } else {
                alernateAdShow = mysharedpreferences.getString("app_interstitialPlatforms", "");
            }

        } else {
            app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
            adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
            alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");
        }


        interstitial_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                interstitial_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_click_for_alt % alernateAd.length == j) {
                    index = j;
                    interstitial_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (interstitial_sequence.size() != 0) {
                    if (!interstitial_sequence.get(0).equals(adSequence[j])) {
                        interstitial_sequence.add(adSequence[j]);
                    }
                }

            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall();
                myCallback = null;
            }
        }

        if (interstitial_sequence.size() != 0) {

            Log.e("Ads", "displayInterstitial: " + interstitial_sequence.get(0));
            showInterstitialAd(interstitial_sequence.get(0), context);
        }


    }

    private void showInterstitialAd(String platform, final Context context) {

        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.ad_progress_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
     /*   Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
*/

        if (platform.equals("Admob") && admob_AdStatus == 1 && interstitial1 != null) {
            if (interstitial1.isLoaded()) {
                if (app_dialogBeforeAdShow == 1) {
                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            interstitial1.show();


                        }
                    }.start();

                } else {
                    interstitial1.show();

                }
            } else {
                // interstitial1.loadAd (new AdRequest.Builder ().build ());
                Log.e("Ads", "showInterstitialAd: Admob " + "Not Ready");
                delayAdmobInterLoads();

                nextInterstitialPlatform(context);
            }
        } else if (platform.equals("MoPub") && mopub_AdStatus == 1 && moPubInterstitial1 != null) {
            if (moPubInterstitial1.isReady()) {
                if (app_dialogBeforeAdShow == 1) {
                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            //interstitial1.show ();
                            moPubInterstitial1.show();

                        }
                    }.start();

                } else {
                    //  interstitial1.show ();

                    moPubInterstitial1.show();

                }
            } else {

                Log.e("Ads", "showInterstitialAd: " + "Not Ready");
                moPubInterstitial1.load();

                nextInterstitialPlatform(context);
            }
        } else if (platform.equals("Facebookaudiencenetwork") && facebook_AdStatus == 1 && fbinterstitialAd1 != null) {

            if (fbinterstitialAd1.isAdLoaded()) {
                if (app_dialogBeforeAdShow == 1) {

                    dialog.show();

                    final CircularProgressIndicator circular_progress = view.findViewById(R.id.circular_progress);
                    circular_progress.setProgress(0, 100);
                    new CountDownTimer(ad_dialog_time_in_second * 1000, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            double time = (millisUntilFinished / 10) / ad_dialog_time_in_second;
                            circular_progress.setProgress(time, 100);
                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            fbinterstitialAd1.show();
                        }
                    }.start();

                } else {
                    fbinterstitialAd1.show();
                }
            } else {
                //   fbinterstitialAd1.loadAd ();

                Log.e("Ads", "showInterstitialAd: Fb " + "Not Ready");
                delayFBLoads();
                nextInterstitialPlatform(context);
            }

        } else {

            nextInterstitialPlatform(context);

        }
    }

    private void nextInterstitialPlatform(Context context) {

        if (interstitial_sequence.size() != 0) {
            interstitial_sequence.remove(0);

            if (interstitial_sequence.size() != 0) {
                showInterstitialAd(interstitial_sequence.get(0), context);
            } else {
                if (is_foursesully == true && hasActiveInternetConnection(activity)) {

                } else {
                    interstitialCallBack();
                }
            }

        } else {

            if (is_foursesully == true && hasActiveInternetConnection(activity)) {

            } else {
                interstitialCallBack();
            }

        }
    }

    public void interstitialCallBack() {

        if (myCallback != null) {
            myCallback.callbackCall();
            AppManage.this.myCallback = null;
        }
    }

    public void show_BANNER(ViewGroup banner_container, String admob_b, String facebook_b, String moPub_b,Context context) {
        this.admob_b = admob_b;
        this.facebook_b = facebook_b;
        this.mopub_b = moPub_b;
        activity=context;
        if (!hasActiveInternetConnection(activity)) {
            return;
        }

        if (app_adShowStatus == 0) {
            return;
        }

        count_banner++;


     /*   int app_howShowAd = mysharedpreferences.getInt ("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString ("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString ("app_alernateAdShow", "");*/

        int app_howShowAd = 0;
        String adPlatformSequence = "";
        String alernateAdShow = "";

        if (mysharedpreferences.getInt("app_showAdLevel", 0) == 1) {
            app_howShowAd = mysharedpreferences.getInt("app_bannerhowShowAd", 0);
            if (app_howShowAd == 0) {
                adPlatformSequence = mysharedpreferences.getString("app_bannerPlatforms", "");
            } else {
                alernateAdShow = mysharedpreferences.getString("app_bannerPlatforms", "");
            }

        } else {
            app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
            adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
            alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");
        }

        banner_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                banner_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_banner % alernateAd.length == j) {
                    index = j;
                    banner_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (banner_sequence.size() != 0) {
                    if (!banner_sequence.get(0).equals(adSequence[j])) {
                        banner_sequence.add(adSequence[j]);
                    }
                }
            }
        }


        if (banner_sequence.size() != 0) {
            showBanner(banner_sequence.get(0), banner_container);
        }


    }

    public void showBanner(String platform, ViewGroup banner_container) {
        if (platform.equals("Admob") && admob_AdStatus == 1) {

            Log.e("Ads", "showBanner: " + "AdMob");
            showAdmobBanner(banner_container);
        } else if (platform.equals("Facebookaudiencenetwork") && facebook_AdStatus == 1) {
            showFacebookBanner(banner_container);
        } else if (platform.equals("MoPub") && mopub_AdStatus == 1) {

            Log.e("Ads", "showBanner: " + "Mopub");

            showMoPubBanner(banner_container);
        } else {
            nextBannerPlatform(banner_container);
        }
    }

    private void nextBannerPlatform(ViewGroup banner_container) {
        if (banner_sequence.size() != 0) {
            banner_sequence.remove(0);
            if (banner_sequence.size() != 0) {
                showBanner(banner_sequence.get(0), banner_container);
            }
        }
    }

    private void showFacebookBanner(final ViewGroup banner_container) {
        if ((facebook_b != null && facebook_b.isEmpty()) || facebook_AdStatus == 0) {
            nextBannerPlatform(banner_container);
            return;
        }

        final com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, facebook_b, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                nextBannerPlatform(banner_container);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
                banner_container.removeAllViews();

                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView); // <- fix
                }

                banner_container.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());


    }

    private void showAdmobBanner(final ViewGroup banner_container) {
        if (admob_b.isEmpty() || admob_AdStatus == 0) {
            nextBannerPlatform(banner_container);
            return;
        }


        adView = new AdView(activity);
        adView.setAdUnitId(admob_b);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                banner_container.removeAllViews();

                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView); // <- fix
                }
                banner_container.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                banner_container.removeAllViews();

                nextBannerPlatform(banner_container);

            }
        });

        loadBanner();

    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice("429C0FF2EBDAD6A5F043454557EF10FF")
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);


        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = ((Activity) activity).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    private void showMoPubBanner(final ViewGroup banner_container) {

        if (mopub_b.isEmpty() || mopub_AdStatus == 0) {
            nextBannerPlatform(banner_container);
            return;
        }
        moPubView = new MoPubView(activity);
        moPubView.setAdUnitId(mopub_b);
        moPubView.setAdSize(MoPubView.MoPubAdSize.MATCH_VIEW);

        moPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(@NonNull MoPubView banner) {
                Log.e("Ads", "onBannerLoaded: " + "Loaded");

                banner_container.removeAllViews();
                if (moPubView.getParent() != null) {
                    ((ViewGroup) moPubView.getParent()).removeView(moPubView); // <- fix
                }
                banner_container.addView(moPubView);
            }

            @Override
            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

                Log.e("Ads", "onBannerFailed: " + "Failled " + errorCode.toString());

                banner_container.removeAllViews();

                nextBannerPlatform(banner_container);
            }

            @Override
            public void onBannerClicked(MoPubView banner) {

            }

            @Override
            public void onBannerExpanded(MoPubView banner) {

            }

            @Override
            public void onBannerCollapsed(MoPubView banner) {

            }
        });
        moPubView.loadAd();

    }

    public static void initMoPubSdk(final Activity activity, final ShowAdsOnInitialization showAdsOnInitialization) {
        if (MoPub.isSdkInitialized()) {
            showAdsOnInitialization.loadInter();
            showAdsOnInitialization.showAds();
        } else {
            final SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(MOPUB_B1);
            MoPub.initializeSdk(activity, configBuilder.build(), new SdkInitializationListener() {
                @Override
                public void onInitializationFinished() {
                    Log.e("Ads", "onInitializationFinished: " + "");

                    showAdsOnInitialization.loadInter();
                    showAdsOnInitialization.showAds();
                }
            });
        }


    }


    public void show_NATIVEBANNER(ViewGroup banner_container, String admobB, String facebookNB, String MOPUB_NB) {
        this.admob_b = admobB;
        this.facebook_nb = facebookNB;
        this.mopub_nb = MOPUB_NB.trim();
        Log.e("Ads", "show_NATIVEBANNER: " + mopub_nb);
        if (app_adShowStatus == 0) {
            return;
        }

        count_banner++;
        /*int app_howShowAd = mysharedpreferences.getInt ("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString ("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString ("app_alernateAdShow", "");*/

        int app_howShowAd = 0;
        String adPlatformSequence = "";
        String alernateAdShow = "";

        Log.e("Ads", "show_NATIVEBANNER: app_showAdLevel: " + mysharedpreferences.getInt("app_showAdLevel", 0));

        if (mysharedpreferences.getInt("app_showAdLevel", 0) == 1) {
            app_howShowAd = mysharedpreferences.getInt("app_bannerhowShowAd", 0);
            if (app_howShowAd == 0) {
                adPlatformSequence = mysharedpreferences.getString("app_bannerPlatforms", "");
            } else {
                alernateAdShow = mysharedpreferences.getString("app_bannerPlatforms", "");
            }

        } else {
            app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
            adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
            alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");
        }


        banner_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                banner_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_banner % alernateAd.length == j) {
                    index = j;
                    banner_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (banner_sequence.size() != 0) {
                    if (!banner_sequence.get(0).equals(adSequence[j])) {
                        banner_sequence.add(adSequence[j]);
                    }
                }
            }
        }

        for (int i = 0; i < banner_sequence.size(); i++) {
            if (banner_sequence.get(i).equalsIgnoreCase("Unity")) {
                banner_sequence.remove(i);
            }
        }

        if (banner_sequence.size() != 0) {
            showNativeBanner(banner_sequence.get(0), banner_container);
        }
    }

    public void showNativeBanner(String platform, ViewGroup banner_container) {
        if (platform.equals("Admob") && admob_AdStatus == 1) {
            Log.e("Ads", "showNativeBanner: " + "Admob");
            showNativeAdmobBanner(banner_container);

        } else if (platform.equals("Facebookaudiencenetwork") && facebook_AdStatus == 1) {
            Log.e("Ads", "showNativeBanner: " + "Facebookaudiencenetwork");
            showNativeFacebookBanner(banner_container);
        } else if (platform.equals("MoPub") && mopub_AdStatus == 1) {
            Log.e("Ads", "showNativeBanner: " + "MoPub");
            showNativeMoPubBanner(banner_container);
        } else {
            nextNativeBannerPlatform(banner_container);
        }
    }

    private void nextNativeBannerPlatform(ViewGroup banner_container) {
        if (banner_sequence.size() != 0) {
            banner_sequence.remove(0);
            if (banner_sequence.size() != 0) {
                showNativeBanner(banner_sequence.get(0), banner_container);
            }
        }
    }


    private void showNativeFacebookBanner(final ViewGroup container) {
        if (facebook_nb.isEmpty() || facebook_AdStatus == 0) {
            nextNativeBannerPlatform(container);
            return;
        }

        final NativeBannerAd nativeAd1 = new NativeBannerAd(activity, facebook_nb);
        nativeAd1.loadAd(nativeAd1.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                container.removeAllViews();
                container.setVisibility(View.VISIBLE);
                new Inflate_ADS(activity).inflate_NB_FB(nativeAd1, container);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                nextNativeBannerPlatform(container);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("Ads", "onAdLoaded: " + "Banner Admob");
                if (nativeAd1 == null || nativeAd1 != ad) {
                    return;
                }
                nativeAd1.downloadMedia();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());

    }

    private void showNativeAdmobBanner(final ViewGroup banner_container) {

        if (admob_b.isEmpty() || admob_AdStatus == 0) {
            nextNativeBannerPlatform(banner_container);
            return;
        }


        adView = new AdView(activity);
        adView.setAdUnitId(admob_b);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("Ads", "onAdLoaded: " + "Banner Admob");
                banner_container.removeAllViews();

                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView); // <- fix
                }
                banner_container.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                banner_container.removeAllViews();

                nextBannerPlatform(banner_container);

            }
        });

        loadBanner();

    }

    private void showNativeMoPubBanner(final ViewGroup banner_container) {

        if (mopub_nb.isEmpty() || mopub_AdStatus == 0) {
            nextNativeBannerPlatform(banner_container);
            return;
        }

        moPubView = new MoPubView(activity);
        moPubView.setAdUnitId(mopub_nb);
        moPubView.setAdSize(MoPubView.MoPubAdSize.MATCH_VIEW);// Enter your Ad Unit ID from www.mopub.com

        moPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(@NonNull MoPubView banner) {
                Log.e("Ads", "onBannerLoaded: " + "Loaded");

                banner_container.removeAllViews();

                if (moPubView.getParent() != null) {
                    ((ViewGroup) moPubView.getParent()).removeView(moPubView); // <- fix
                }
                banner_container.addView(moPubView);
            }

            @Override
            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

                Log.e("Ads", "onBannerFailed: " + "Failled " + errorCode.toString());

                banner_container.removeAllViews();

                nextBannerPlatform(banner_container);
            }

            @Override
            public void onBannerClicked(MoPubView banner) {

            }

            @Override
            public void onBannerExpanded(MoPubView banner) {

            }

            @Override
            public void onBannerCollapsed(MoPubView banner) {

            }
        });
        moPubView.loadAd();

    }


    public void show_NATIVE(ViewGroup nativeAdContainer, String admobN1, String facebookN1, String mopubN1,Context context) {
        this.admob_n = admobN1;
        this.facebook_n = facebookN1;
        this.mopub_n = mopubN1;
        this.activity=context;
        if (app_adShowStatus == 0) {
            return;
        }

        if (this.admob_n.isEmpty() && this.facebook_n.isEmpty() && this.mopub_n.isEmpty()) {
            this.admob_n = ADMOB_N1;
            this.facebook_n = FACEBOOK_N1;
            this.mopub_n = MOPUB_N1;
        }

        count_native++;
        /*int app_howShowAd = mysharedpreferences.getInt ("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString ("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString ("app_alernateAdShow", "");*/
        int app_howShowAd = 0;
        String adPlatformSequence = "";
        String alernateAdShow = "";

        if (mysharedpreferences.getInt("app_showAdLevel", 0) == 1) {
            app_howShowAd = mysharedpreferences.getInt("app_nativehowShowAd", 0);
            if (app_howShowAd == 0) {
                adPlatformSequence = mysharedpreferences.getString("app_nativePlatforms", "");
            } else {
                alernateAdShow = mysharedpreferences.getString("app_nativePlatforms", "");
            }

        } else {
            app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
            adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
            alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");
        }


        native_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                native_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_native % alernateAd.length == j) {
                    index = j;
                    native_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (native_sequence.size() != 0) {
                    if (!native_sequence.get(0).equals(adSequence[j])) {
                        native_sequence.add(adSequence[j]);
                    }
                }
            }
        }


        for (int i = 0; i < native_sequence.size(); i++) {
            if (native_sequence.get(i).equalsIgnoreCase("Unity")) {
                native_sequence.remove(i);
            }
        }

        if (native_sequence.size() != 0) {

            Log.e("Ads", "show_NATIVE: " + native_sequence.get(0));

            showNative(native_sequence.get(0), nativeAdContainer);
        }

    }
    public void show_half_NATIVE(ViewGroup nativeAdContainer, String admobN1, String facebookN1, String mopubN1,Context context) {
        this.admob_n = admobN1;
        this.facebook_n = facebookN1;
        this.mopub_n = mopubN1;
        this.activity=context;
        if (app_adShowStatus == 0) {
            return;
        }

        if (this.admob_n.isEmpty() && this.facebook_n.isEmpty() && this.mopub_n.isEmpty()) {
            this.admob_n = ADMOB_N1;
            this.facebook_n = FACEBOOK_N1;
            this.mopub_n = MOPUB_N1;
        }

        count_native++;
        /*int app_howShowAd = mysharedpreferences.getInt ("app_howShowAd", 0);
        String adPlatformSequence = mysharedpreferences.getString ("app_adPlatformSequence", "");
        String alernateAdShow = mysharedpreferences.getString ("app_alernateAdShow", "");*/
        int app_howShowAd = 0;
        String adPlatformSequence = "";
        String alernateAdShow = "";

        if (mysharedpreferences.getInt("app_showAdLevel", 0) == 1) {
            app_howShowAd = mysharedpreferences.getInt("app_nativehowShowAd", 0);
            if (app_howShowAd == 0) {
                adPlatformSequence = mysharedpreferences.getString("app_nativePlatforms", "");
            } else {
                alernateAdShow = mysharedpreferences.getString("app_nativePlatforms", "");
            }

        } else {
            app_howShowAd = mysharedpreferences.getInt("app_howShowAd", 0);
            adPlatformSequence = mysharedpreferences.getString("app_adPlatformSequence", "");
            alernateAdShow = mysharedpreferences.getString("app_alernateAdShow", "");
        }


        native_sequence = new ArrayList<String>();
        if (app_howShowAd == 0 && !adPlatformSequence.isEmpty()) {
            String adSequence[] = adPlatformSequence.split(",");
            for (int i = 0; i < adSequence.length; i++) {
                native_sequence.add(adSequence[i]);
            }

        } else if (app_howShowAd == 1 && !alernateAdShow.isEmpty()) {
            String alernateAd[] = alernateAdShow.split(",");

            int index = 0;
            for (int j = 0; j <= 10; j++) {
                if (count_native % alernateAd.length == j) {
                    index = j;
                    native_sequence.add(alernateAd[index]);
                }
            }

            String adSequence[] = adPlatformSequence.split(",");
            for (int j = 0; j < adSequence.length; j++) {
                if (native_sequence.size() != 0) {
                    if (!native_sequence.get(0).equals(adSequence[j])) {
                        native_sequence.add(adSequence[j]);
                    }
                }
            }
        }


        for (int i = 0; i < native_sequence.size(); i++) {
            if (native_sequence.get(i).equalsIgnoreCase("Unity")) {
                native_sequence.remove(i);
            }
        }

        if (native_sequence.size() != 0) {

            Log.e("Ads", "show_NATIVE: " + native_sequence.get(0));

            showHalfNative(native_sequence.get(0), nativeAdContainer);
        }

    }

    private void showNative(String platform, ViewGroup nativeAdContainer) {
        if (platform.equals("Admob") && admob_AdStatus == 1) {
            showAdmobNative(nativeAdContainer);
        } else if (platform.equals("Facebookaudiencenetwork") && facebook_AdStatus == 1) {
            showFacebookNative(nativeAdContainer);
        } else if (platform.equals("MoPub") && mopub_AdStatus == 1) {
            showMoPubNative(nativeAdContainer);
        } else {
            nextNativePlatform(nativeAdContainer);
        }
    }
    private void showHalfNative(String platform, ViewGroup nativeAdContainer) {
        if (platform.equals("Admob") && admob_AdStatus == 1) {
            showHalfAdmobNative(nativeAdContainer);
        } else if (platform.equals("Facebookaudiencenetwork") && facebook_AdStatus == 1) {
            showHalfFacebookNative(nativeAdContainer);
        } else if (platform.equals("MoPub") && mopub_AdStatus == 1) {
            showHalfMoPubNative(nativeAdContainer);
        } else {
            nextHalfNativePlatform(nativeAdContainer);
        }
    }

    private void nextNativePlatform(ViewGroup nativeAdContainer) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                showNative(native_sequence.get(0), nativeAdContainer);
            }
        }
    }

    private void nextHalfNativePlatform(ViewGroup nativeAdContainer) {
        if (native_sequence.size() != 0) {
            native_sequence.remove(0);
            if (native_sequence.size() != 0) {
                showHalfNative(native_sequence.get(0), nativeAdContainer);
            }
        }
    }

    private void showNative(final ViewGroup nativeAdContainer) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }

        AdLoader.Builder builder = new AdLoader.Builder(activity, admob_n);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                new Inflate_ADS(activity).inflate_NATIV_ADMOB(unifiedNativeAd, nativeAdContainer);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Request an ad
                nextNativePlatform(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }


    private void showFacebookNative(final ViewGroup nativeAdContainer) {
        if (facebook_n.isEmpty() || facebook_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }

        final NativeAd nativeAd = new NativeAd(activity, facebook_n);

        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                nextNativePlatform(nativeAdContainer);
                Log.e("Ads", "onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                Log.e("Ads", "onAdLoaded: " + "Fb Native");
                new Inflate_ADS(activity).inflate_NATIV_FB(nativeAd, nativeAdContainer);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }

    private void showHalfFacebookNative(final ViewGroup nativeAdContainer) {
        if (facebook_n.isEmpty() || facebook_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }

        final NativeAd nativeAd = new NativeAd(activity, facebook_n);

        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                nextNativePlatform(nativeAdContainer);
                Log.e("Ads", "onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                Log.e("Ads", "onAdLoaded: " + "Fb Native");
                new Inflate_ADS(activity).inflate_NATIV_half_FB(nativeAd, nativeAdContainer);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }


    private void showMoPubNative(final ViewGroup nativeAdContainer) {
        Log.e("Ads", "showMoPubNative: " + mopub_n);
        if (mopub_n.isEmpty() || mopub_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }
        adapterHelper = new AdapterHelper(activity, 0, 2);
        //Load MoPub Native


        MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            @Override
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {

                Log.e("Ads", "onNativeLoad: " + "Loaded");
                LayoutInflater vi = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup child = (ViewGroup) vi.inflate(R.layout.ads_native_mopub, null);
                View v = adapterHelper.getAdView(null, child, nativeAd, new ViewBinder.Builder(0).build());

                if (nativeAdContainer.getChildCount() == 0) {
                    // Add the ad view to our view hierarchy
                    nativeAdContainer.addView(v);
                } else {
                    Log.e("Native---", "onNativeLoad: " + "Child found");
                }


            }

            @Override
            public void onNativeFail(NativeErrorCode errorCode) {
                Log.e("Ads", "onNativeLoad: " + errorCode.toString());
                //    Toast.makeText (activity, errorCode.toString (), Toast.LENGTH_LONG).show ();
            }
        };
        moPubNative = new MoPubNative(activity, mopub_n, moPubNativeNetworkListener);
        ViewBinder viewBinder = new ViewBinder.Builder(R.layout.ads_native_mopub)
                .mainImageId(R.id.native_ad_main_image)
                .iconImageId(R.id.native_ad_icon_image)
                .titleId(R.id.native_ad_title)
                .textId(R.id.native_ad_text)
                .build();
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(viewBinder);
        moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);

        EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                RequestParameters.NativeAdAsset.TITLE,
                RequestParameters.NativeAdAsset.TEXT,
                RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT,
                RequestParameters.NativeAdAsset.MAIN_IMAGE,
                RequestParameters.NativeAdAsset.ICON_IMAGE,
                RequestParameters.NativeAdAsset.STAR_RATING
        );

        final RequestParameters mRequestParameters = new RequestParameters.Builder()

                .desiredAssets(desiredAssets)
                .build();
        moPubNative.makeRequest(mRequestParameters);

    }

    private void showHalfMoPubNative(final ViewGroup nativeAdContainer) {
        Log.e("Ads", "showMoPubNative: " + mopub_n);
        if (mopub_n.isEmpty() || mopub_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }
        adapterHelper = new AdapterHelper(activity, 0, 2);
        //Load MoPub Native


        MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener = new MoPubNative.MoPubNativeNetworkListener() {
            @Override
            public void onNativeLoad(com.mopub.nativeads.NativeAd nativeAd) {

                Log.e("Ads", "onNativeLoad: " + "Loaded");
                LayoutInflater vi = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup child = (ViewGroup) vi.inflate(R.layout.ads_native_half_mopub, null);
                View v = adapterHelper.getAdView(null, child, nativeAd, new ViewBinder.Builder(0).build());

                if (nativeAdContainer.getChildCount() == 0) {
                    // Add the ad view to our view hierarchy
                    nativeAdContainer.addView(v);
                } else {
                    Log.e("Native---", "onNativeLoad: " + "Child found");
                }


            }

            @Override
            public void onNativeFail(NativeErrorCode errorCode) {
                Log.e("Ads", "onNativeLoad: " + errorCode.toString());
                //    Toast.makeText (activity, errorCode.toString (), Toast.LENGTH_LONG).show ();
            }
        };
        moPubNative = new MoPubNative(activity, mopub_n, moPubNativeNetworkListener);
        ViewBinder viewBinder = new ViewBinder.Builder(R.layout.ads_native_mopub)
                .mainImageId(R.id.native_ad_main_image)
                .iconImageId(R.id.native_ad_icon_image)
                .titleId(R.id.native_ad_title)
                .textId(R.id.native_ad_text)
                .build();
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(viewBinder);
        moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);

        EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                RequestParameters.NativeAdAsset.TITLE,
                RequestParameters.NativeAdAsset.TEXT,
                RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT,
                RequestParameters.NativeAdAsset.MAIN_IMAGE,
                RequestParameters.NativeAdAsset.ICON_IMAGE,
                RequestParameters.NativeAdAsset.STAR_RATING
        );

        final RequestParameters mRequestParameters = new RequestParameters.Builder()

                .desiredAssets(desiredAssets)
                .build();
        moPubNative.makeRequest(mRequestParameters);

    }

    private void loadMoPubNativeAds() {
        final SdkConfiguration.Builder configBuilderN = new SdkConfiguration.Builder(mopub_n).withLegitimateInterestAllowed(true);
        SdkConfiguration sdkConfiguration = null;
        try {
            sdkConfiguration = configBuilderN.build();
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("Ads", "loadMoPubNativeAds: " + e.getMessage());

        }

        MoPub.initializeSdk(activity, sdkConfiguration, new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {

                EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                        RequestParameters.NativeAdAsset.TITLE,
                        RequestParameters.NativeAdAsset.TEXT,
                        RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT,
                        RequestParameters.NativeAdAsset.MAIN_IMAGE,
                        RequestParameters.NativeAdAsset.ICON_IMAGE,
                        RequestParameters.NativeAdAsset.STAR_RATING
                );

                final RequestParameters mRequestParameters = new RequestParameters.Builder()

                        .desiredAssets(desiredAssets)
                        .build();

                moPubNative.makeRequest(mRequestParameters);
            }

        });


    }


    private void showAdmobNative(final ViewGroup nativeAdContainer) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }

        AdLoader.Builder builder = new AdLoader.Builder(activity, admob_n);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                new Inflate_ADS(activity).inflate_NATIV_ADMOB(unifiedNativeAd, nativeAdContainer);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Request an ad
                nextNativePlatform(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }
    private void showHalfAdmobNative(final ViewGroup nativeAdContainer) {
        if (admob_n.isEmpty() || admob_AdStatus == 0) {
            nextNativePlatform(nativeAdContainer);
            return;
        }

        AdLoader.Builder builder = new AdLoader.Builder(activity, admob_n);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                new Inflate_ADS(activity).inflate_Half_NATIV_ADMOB(unifiedNativeAd, nativeAdContainer);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Request an ad
                nextNativePlatform(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }

    private void delayFBLoads() {

        if(!fbinterstitialAd1.isAdLoaded() && is_fb_running == false ) {
            is_fb_running = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    fbinterstitialAd1.loadAd();
                    is_fb_running = false;
                }
            }, (long) mysharedpreferences.getLong("app_fbDelay", 0));
        }

    }

/*    private void delayFBLoads() {

        if (!fbinterstitialAd1.isAdLoaded()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    fbinterstitialAd1.loadAd();
                }
            }, (long) mysharedpreferences.getLong("app_fbDelay", 0));
        }

    }*/

    private void delayAdmobInterLoads() {

        if (!interstitial1.isLoaded() && is_am_running == false) {
            is_am_running = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    interstitial1.loadAd(new AdRequest.Builder().build());
                    is_am_running = false;

                }
            }, (long) mysharedpreferences.getLong("app_amDelay", 0));
        }
    }


    public interface MyCallback {
        void callbackCall();
    }

    private static <T> Iterable<T> iterate(final Iterator<T> i) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }


}
