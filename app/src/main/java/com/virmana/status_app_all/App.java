package com.virmana.status_app_all;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.facebook.FacebookSdk;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.appevents.AppEventsLogger;
import com.google.ads.consent.ConsentInformation;
import com.google.android.gms.ads.MobileAds;
import com.unity3d.ads.UnityAds;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.Provider.ThemeHelper;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import timber.log.Timber;
import timber.log.Timber.DebugTree;
import static timber.log.Timber.DebugTree;

/**
 * Created by Tamim on 28/09/2017.
 */



public class App extends MultiDexApplication {
    private static App instance;

    @Override
    public void onCreate()
    {
        MultiDex.install(this);
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }

        Timber.i("Creating our Application");
        PrefManager prf= new PrefManager(getApplicationContext());

        if (prf.getString("theme").equals("dark")) {
            ThemeHelper.applyTheme("dark");
        } else if (prf.getString("theme").equals("light")){

            ThemeHelper.applyTheme("light");

        }else{
            ThemeHelper.applyTheme("default");

        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        MobileAds.initialize(this, initializationStatus -> {});
        AudienceNetworkAds.initialize(instance);
        AppEventsLogger.activateApp(this);
        UnityAds.initialize (this, getResources().getString(R.string.unity_ads_app_id));
        AppLovinSdk.initializeSdk(instance);
        AppLovinSdk.getInstance( instance ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( instance, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
            }
        } );
    }

    public static App getInstance ()
    {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
