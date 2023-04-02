package com.virmana.status_app_all.ui.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;


import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.applovin.mediation.MaxError;

import com.virmana.status_app_all.Adapters.SelectableViewHolder;
import com.virmana.status_app_all.config.Global;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.Adapters.LanguageAdapter;
import com.virmana.status_app_all.api.apiClient;
import com.virmana.status_app_all.api.apiRest;
import com.virmana.status_app_all.model.ApiResponse;
import com.virmana.status_app_all.model.Language;
import com.virmana.status_app_all.services.BillingSubs;
import com.virmana.status_app_all.services.CallBackBilling;
import com.virmana.status_app_all.ui.fragement.CategroiesFragement;
import com.virmana.status_app_all.ui.fragement.DownloadsFragement;
import com.virmana.status_app_all.ui.fragement.FavoritesFragment;
import com.virmana.status_app_all.ui.fragement.HomeFragment;
import com.virmana.status_app_all.ui.fragement.FollowFragment;
import com.virmana.status_app_all.ui.fragement.PopularFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.virmana.status_app_all.ui.view.ScrollHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements SelectableViewHolder.OnItemSelectedListener ,NavigationView.OnNavigationItemSelectedListener  {


    private Boolean EarningSystem  = true;
    private Dialog rateDialog;


    private static final String TAG ="MainActivity ----- : " ;
    ConsentForm form;

    private MaterialSearchView searchView;
    private ViewPagerAdapter adapter;
    private NavigationView navigationView;

    private RewardedAd mRewardedVideoAd;
    private AppLovinIncentivizedInterstitial applovinRewardedVideoAd;
    private MaxRewardedAd maxRewardedVideoAd;

    private Boolean autoDisplay =  false;


    private final List<Language> languageList = new ArrayList<>();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private AlertDialog.Builder builderLanguage;
    private PrefManager prefManager;
    private Menu menu;
    private LanguageAdapter languageAdapter;
    private ViewPager viewPager;
    private List<Fragment> fragments;// used for ViewPager Adapters

    private int tab_fab;
    private TextView text_view_name_nave_header;
    private CircleImageView circle_image_view_profile_nav_header;

    private  Boolean FromLogin = false;


    private FollowFragment followFragment;
    private Dialog dialog;
    private  Boolean DialogOpened = false;
    private TextView text_view_go_pro;


    private String old_language;
    private MenuItem item_language;
    private SpeedDialView speed_dial_main_activity;
    private int openAction =0 ;


    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,   Manifest.permission.READ_CONTACTS)) {
                    Intent intent_status  =  new Intent(getApplicationContext(), PermissionActivity.class);
                    startActivity(intent_status);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                } else {
                    Intent intent_status  =  new Intent(getApplicationContext(), PermissionActivity.class);
                    startActivity(intent_status);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initBuy();
        prefManager= new PrefManager(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        PrefManager prf= new PrefManager(getApplicationContext());
        if (prf.getString("EARNING_SYSTEM").equals("TRUE")){
            EarningSystem = true;
        }else{
            EarningSystem = false;
        }
        checkPermission();
        initData();
        iniView();
        loadLang();
        initAction();
        firebaseSubscribe();
        initEvent();
        initGDPR();
        PrefManager   prefManager= new PrefManager(getApplicationContext());
        if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
            loadAdmobRewardedVideoAd();
        }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
            loadAppLovinRewardedVideoAd();
        }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
            loadISRewardedVideoAd();
        }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("MAX")){
            loadMaxRewardedVideoAd();
        }

    }
    BillingSubs billingSubs;
    public void initBuy(){
        List<String> listSkuStoreSubs = new ArrayList<>();
        listSkuStoreSubs.add(Global.SUBSCRIPTION_ID);
        billingSubs = new BillingSubs(this, listSkuStoreSubs, new CallBackBilling() {
            @Override
            public void onPurchase() {
                PrefManager prefManager= new PrefManager(getApplicationContext());
                prefManager.setString("SUBSCRIBED","TRUE");
                Toasty.success(MainActivity.this, "you have successfully subscribed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotPurchase() {
                Toasty.warning(MainActivity.this, "Operation has been cancelled  ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotLogin() {
                Toasty.warning(MainActivity.this, "Operation has been cancelled  ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void subscribe(){
        billingSubs.purchase(Global.SUBSCRIPTION_ID);
    }
    public void loadMaxRewardedVideoAd() {


        PrefManager   prefManager= new PrefManager(getApplicationContext());

        maxRewardedVideoAd = MaxRewardedAd.getInstance( prefManager.getString("ADMIN_REWARDED_ADMOB_ID"), this );
        maxRewardedVideoAd.setListener(new MaxRewardedAdListener() {
            @Override
            public void onRewardedVideoStarted(MaxAd ad) {

            }

            @Override
            public void onRewardedVideoCompleted(MaxAd ad) {

            }

            @Override
            public void onUserRewarded(MaxAd ad, MaxReward reward) {
                openAction();

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                if (autoDisplay){
                    dialog.dismiss();
                    maxRewardedVideoAd.showAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"));
                    autoDisplay = false;
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                // Rewarded ad failed to load
                // We recommend retrying with exponentially higher delays up to a maximum delay (in this case 64 seconds)


            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });

        maxRewardedVideoAd.loadAd();



    }
    public void loadISRewardedVideoAd() {
        if(!IronSource.isRewardedVideoAvailable()) {
            PrefManager prefManager = new PrefManager(getApplicationContext());
            IronSource.init(this, prefManager.getString("ADMIN_REWARDED_ADMOB_ID"), IronSource.AD_UNIT.REWARDED_VIDEO);
            IronSource.setRewardedVideoListener(new RewardedVideoListener() {
                @Override
                public void onRewardedVideoAdOpened() {

                }

                @Override
                public void onRewardedVideoAdClosed() {
                    openAction();
                }

                @Override
                public void onRewardedVideoAvailabilityChanged(boolean available) {
                    if (available) {
                        if (autoDisplay){
                            dialog.dismiss();
                            IronSource.showRewardedVideo();
                            autoDisplay = false;
                        }
                    }
                }

                @Override
                public void onRewardedVideoAdStarted() {

                }

                @Override
                public void onRewardedVideoAdEnded() {

                }

                @Override
                public void onRewardedVideoAdRewarded(Placement placement) {





                }

                @Override
                public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {

                }

                @Override
                public void onRewardedVideoAdClicked(Placement placement) {

                }
            });
            IronSource.loadRewardedVideo();
        }
    }
    public void loadAppLovinRewardedVideoAd() {

        if(applovinRewardedVideoAd ==null){
            applovinRewardedVideoAd = AppLovinIncentivizedInterstitial.create( getApplicationContext() );
            applovinRewardedVideoAd.preload(new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd ad) {
                }
                @Override
                public void failedToReceiveAd(int errorCode) {

                }
            });
        }else{
            if(!applovinRewardedVideoAd.isAdReadyToDisplay()){
                applovinRewardedVideoAd.preload(new AppLovinAdLoadListener() {
                    @Override
                    public void adReceived(AppLovinAd ad) {
                    }
                    @Override
                    public void failedToReceiveAd(int errorCode) {

                    }
                });
            }
        }
    }
    public void loadAdmobRewardedVideoAd() {


        PrefManager   prefManager= new PrefManager(getApplicationContext());
        mRewardedVideoAd.load(getApplicationContext(), prefManager.getString("ADMIN_REWARDED_ADMOB_ID"),
                new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        super.onAdLoaded(rewardedAd);
                        mRewardedVideoAd = rewardedAd;


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mRewardedVideoAd = null;
                    }

                });



    }

    public  void openAction(){
        if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
            loadAdmobRewardedVideoAd();
        }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
            loadAppLovinRewardedVideoAd();
        }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
            loadISRewardedVideoAd();
        }
        if(openAction != 0) {
            switch (openAction) {
                case 80001:
                    if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                        startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FromLogin = true;
                    }
                    break;
                case 80002:
                    if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                        startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FromLogin = true;
                    }
                    break;
                case 80003:
                    if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                        startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FromLogin = true;
                    }
                    break;
                case 80004:
                    if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                        startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FromLogin = true;
                    }
                    break;
                case 80005:
                    if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                        startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        FromLogin = true;
                    }
                    break;
            }
        }

    }
    @Override
    public void onPause() {
        super.onPause();
    }



    private void initGDPR() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        ConsentInformation consentInformation =
                ConsentInformation.getInstance(MainActivity.this);
//// test
        PrefManager prf= new PrefManager(getApplicationContext());
        String[] publisherIds = {  prf.getString("ADMIN_PUBLISHER_ID")};
        consentInformation.requestConsentInfoUpdate(publisherIds, new
                ConsentInfoUpdateListener() {
                    @Override
                    public void onConsentInfoUpdated(ConsentStatus consentStatus) {
// User's consent status successfully updated.
                        Log.d(TAG,"onConsentInfoUpdated");
                        switch (consentStatus){
                            case PERSONALIZED:
                                Log.d(TAG,"PERSONALIZED");
                                ConsentInformation.getInstance(MainActivity.this)
                                        .setConsentStatus(ConsentStatus.PERSONALIZED);
                                break;
                            case NON_PERSONALIZED:
                                Log.d(TAG,"NON_PERSONALIZED");
                                ConsentInformation.getInstance(MainActivity.this)
                                        .setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                                break;


                            case UNKNOWN:
                                Log.d(TAG,"UNKNOWN");
                                if
                                        (ConsentInformation.getInstance(MainActivity.this).isRequestLocationInEeaOrUnknown
                                        ()){
                                    URL privacyUrl = null;
                                    try {
// TODO: Replace with your app's privacy policy URL.
                                        privacyUrl = new URL(Global.API_URL.replace("/api/","/privacy_policy.html"));

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
// Handle error.

                                    }
                                    form = new ConsentForm.Builder(MainActivity.this,
                                            privacyUrl)
                                            .withListener(new ConsentFormListener() {
                                                @Override
                                                public void onConsentFormLoaded() {
                                                    Log.d(TAG,"onConsentFormLoaded");
                                                    showform();
                                                }
                                                @Override
                                                public void onConsentFormOpened() {
                                                    Log.d(TAG,"onConsentFormOpened");
                                                }
                                                @Override
                                                public void onConsentFormClosed( ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                                                    Log.d(TAG,"onConsentFormClosed");
                                                }
                                                @Override
                                                public void onConsentFormError(String errorDescription) {
                                                    Log.d(TAG,"onConsentFormError");
                                                    Log.d(TAG,errorDescription);
                                                }
                                            })
                                            .withPersonalizedAdsOption()
                                            .withNonPersonalizedAdsOption()
                                            .build();
                                    form.load();
                                } else {
                                    Log.d(TAG,"PERSONALIZED else");
                                    ConsentInformation.getInstance(MainActivity.this).setConsentStatus(ConsentStatus.PERSONALIZED);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    @Override
                    public void onFailedToUpdateConsentInfo(String errorDescription) {
// User's consent status failed to update.
                        Log.d(TAG,"onFailedToUpdateConsentInfo");
                        Log.d(TAG,errorDescription);
                    }
                });
    }
    private void showform(){
        if (form!=null){
            Log.d(TAG,"show ok");
            form.show();
        }
    }

    private void firebaseSubscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("StatusAllInOne");
    }

    private void initAction() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.custom_cursor);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent  = new Intent(MainActivity.this,SearchActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        /*this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prf= new PrefManager(getApplicationContext());
                if (prf.getString("LOGGED").toString().equals("TRUE")){


                        Intent intent_video  =  new Intent(getApplicationContext(), UploadVideoActivity.class);
                        startActivity(intent_video);
                        overridePendingTransition(R.anim.enter, R.anim.exit);


                }else{
                    Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    FromLogin=true;

                }
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);


        if(checkSUBSCRIBED()){
            MenuItem item_action_pro = menu.findItem(R.id.action_pro);
            item_action_pro.setVisible(false);//
        }


        this.menu = menu;
        MenuItem item = menu.findItem(R.id.action_search);
        item_language = menu.findItem(R.id.action_language);
        searchView.setMenuItem(item);

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_language :
                Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.action_pro :
                showDialog();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void setIconItem(final MenuItem item,String url){

        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                item.setIcon(mBitmapDrawable);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("DEBUG", "onBitmapFailed");
                item.setIcon(getResources().getDrawable(R.drawable.ic_global));

            }
            @Override
            public void onPrepareLoad(Drawable drawable) {
                Log.d("DEBUG", "onPrepareLoad");
            }
        };
        Picasso.get().load(url).placeholder(R.drawable.flag_placeholder).error(R.drawable.flag_placeholder).into(mTarget);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(0);
        }else if(id == R.id.login){
            Intent intent= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            FromLogin=true;

        }else if (R.id.whatsapp_saver== id){
            Intent intent= new Intent(MainActivity.this, WhatsAppActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_exit) {
            final PrefManager prf = new PrefManager(getApplicationContext());
            if (prf.getString("NOT_RATE_APP").equals("TRUE")) {
                super.onBackPressed();
            } else {
                rateDialog(true);
            }
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }else if (id==R.id.my_profile){
            PrefManager prf= new PrefManager(getApplicationContext());
            if (prf.getString("LOGGED").toString().equals("TRUE")){
                Intent intent  =  new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", Integer.parseInt(prf.getString("ID_USER")));
                intent.putExtra("image",prf.getString("IMAGE_USER").toString());
                intent.putExtra("name",prf.getString("NAME_USER").toString());
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }else{
                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                FromLogin=true;
            }
        }else if (id==R.id.my_earnings){
            PrefManager prf= new PrefManager(getApplicationContext());
            if (prf.getString("LOGGED").toString().equals("TRUE")){
                Intent intent  =  new Intent(getApplicationContext(), EarningActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }else{
                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                FromLogin=true;
            }
        }else if (id==R.id.logout){
            logout();
        }
        else if (id==R.id.nav_share){
            final String appPackageName=getApplication().getPackageName();
            String shareBody = "Download "+getString(R.string.app_name)+" From :  "+"http://play.google.com/store/apps/details?id=" + appPackageName;
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.app_name));
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
        }else if (id == R.id.nav_rate) {
            rateDialog(false);
        }else if (id == R.id.nav_help){
            Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.nav_policy  ){
            Intent intent = new Intent(getApplicationContext(), PolicyActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }else if (id == R.id.buy_now){
            showDialog();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void iniView() {
        com.google.android.material.floatingactionbutton.FloatingActionButton floating_action_button = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        this.speed_dial_main_activity = (SpeedDialView) findViewById(R.id.speed_dial_main_activity);
        if (
                        prefManager.getString("UPLOAD_GIF").toString().equals("FALSE") &&
                        prefManager.getString("UPLOAD_VIDEO").toString().equals("FALSE") &&
                        prefManager.getString("UPLOAD_IMAGE").toString().equals("FALSE") &&
                        prefManager.getString("UPLOAD_QUOTE").toString().equals("FALSE") &&
                        prefManager.getString("UPLOAD_YOUTUBE").toString().equals("FALSE")
        ) {
            speed_dial_main_activity.setVisibility(View.GONE);
        }else{
            speed_dial_main_activity.setVisibility(View.VISIBLE);
        }
        final PrefManager prefManager = new PrefManager(this);
        if (!prefManager.getString("UPLOAD_GIF").toString().equals("FALSE")) {
            speed_dial_main_activity.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_gif,R.drawable.ic_gif)
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.bgColor, getTheme()))
                            .setLabel(getString(R.string.upload_gif))
                            .setFabBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelColor(getResources().getColor((R.color.bgColor)))
                            .create()
            );
        }
        if (!prefManager.getString("UPLOAD_VIDEO").toString().equals("FALSE")) {

            speed_dial_main_activity.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_video, R.drawable.ic_videocam)
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.bgColor, getTheme()))
                            .setLabel(getString(R.string.upload_video))
                            .setFabBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelColor(getResources().getColor((R.color.bgColor)))
                            .create()
            );

        }
        if (!prefManager.getString("UPLOAD_YOUTUBE").toString().equals("FALSE")) {

            speed_dial_main_activity.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_youtube, R.drawable.ic_youtube)
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.bgColor, getTheme()))
                            .setLabel(getString(R.string.upload_youtube))
                            .setFabBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelColor(getResources().getColor((R.color.bgColor)))
                            .create()
            );
        }
        if (!prefManager.getString("UPLOAD_IMAGE").toString().equals("FALSE")) {

            speed_dial_main_activity.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_image, R.drawable.ic_image)
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.bgColor, getTheme()))
                            .setLabel(getString(R.string.upload_image))
                            .setFabBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelColor(getResources().getColor((R.color.bgColor)))
                            .create()
            );
        }
        if (!prefManager.getString("UPLOAD_QUOTE").toString().equals("FALSE")) {

            speed_dial_main_activity.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.fab_quote, R.drawable.ic_quote)
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.bgColor, getTheme()))
                            .setLabel(getString(R.string.write_quote))
                            .setFabBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelBackgroundColor(getResources().getColor((R.color.textPrimaryColor)))
                            .setLabelColor(getResources().getColor((R.color.bgColor)))
                            .create()
            );
        }
        final PrefManager prf= new PrefManager(getApplicationContext());

        speed_dial_main_activity.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_youtube:
                       if (!checkSUBSCRIBED()) {
                           if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
                               if (mRewardedVideoAd!=null) {
                                   openAction = 80001;
                                   mRewardedVideoAd.show(MainActivity.this, rewardItem -> openAction());

                               } else {
                                   loadAdmobRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                           }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("MAX")){
                               if(maxRewardedVideoAd != null){
                                   if(maxRewardedVideoAd.isReady()){
                                       openAction = 80001;
                                       maxRewardedVideoAd.showAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"));

                                   }else{
                                       loadMaxRewardedVideoAd();
                                       if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                           startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                           overridePendingTransition(R.anim.enter, R.anim.exit);
                                       } else {
                                           Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                           startActivity(intent);
                                           FromLogin = true;
                                       }
                                   }
                               }else{
                                   loadMaxRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                           }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
                               if(applovinRewardedVideoAd != null){
                                   if(applovinRewardedVideoAd.isAdReadyToDisplay()){
                                       openAction = 80001;
                                       applovinRewardedVideoAd.show(getApplicationContext(), null, null, new AppLovinAdDisplayListener() {
                                           @Override
                                           public void adDisplayed(AppLovinAd ad) {}
                                           @Override
                                           public void adHidden(AppLovinAd ad) {
                                               openAction();
                                           }
                                       });

                                   }else{
                                       loadAppLovinRewardedVideoAd();
                                       if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                           startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                           overridePendingTransition(R.anim.enter, R.anim.exit);
                                       } else {
                                           Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                           startActivity(intent);
                                           FromLogin = true;
                                       }
                                   }
                               }else{
                                   loadAppLovinRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                           }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
                               if(IronSource.isRewardedVideoAvailable()){
                                       openAction = 80001;
                                        IronSource.showRewardedVideo();
                               }else{
                                   loadISRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                           }else{
                               if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                   startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                   overridePendingTransition(R.anim.enter, R.anim.exit);
                               } else {
                                   Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                   startActivity(intent);
                                   FromLogin = true;
                               }
                           }
                        }else{
                            if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                startActivity(new Intent(MainActivity.this, UploadYoutubeActivity.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            } else {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                FromLogin = true;
                            }
                        }

                        return false; // true to keep the Speed Dial open
                    case R.id.fab_gif:

                        if (!checkSUBSCRIBED()) {
                            if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
                                if (mRewardedVideoAd!=null) {
                                    openAction = 80002;
                                    mRewardedVideoAd.show(MainActivity.this,rewardItem -> {
                                        openAction();
                                    });

                                } else {
                                    loadAdmobRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("MAX")){
                                if(maxRewardedVideoAd != null){
                                    if(maxRewardedVideoAd.isReady()){
                                        openAction = 80002;
                                        maxRewardedVideoAd.showAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"));
                                    }else {
                                        loadMaxRewardedVideoAd();
                                        if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                            startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                            overridePendingTransition(R.anim.enter, R.anim.exit);
                                        } else {
                                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            FromLogin = true;
                                        }
                                    }
                                }else {
                                    loadMaxRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
                               if(applovinRewardedVideoAd != null){
                                   if(applovinRewardedVideoAd.isAdReadyToDisplay()){
                                    openAction = 80002;
                                       applovinRewardedVideoAd.show(getApplicationContext(), null, null, new AppLovinAdDisplayListener() {
                                           @Override
                                           public void adDisplayed(AppLovinAd ad) {}
                                           @Override
                                           public void adHidden(AppLovinAd ad) {
                                               openAction();
                                           }
                                       });
                                   }else {
                                    loadAppLovinRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                               }else {
                                   loadAppLovinRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
                                if(IronSource.isRewardedVideoAvailable()){
                                    openAction = 80002;
                                    IronSource.showRewardedVideo();
                                }else{
                                    loadISRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else{
                                if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                    startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    FromLogin = true;
                                }
                            }
                        }else{
                            if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                startActivity(new Intent(MainActivity.this, UploadGifActivity.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            } else {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                FromLogin = true;
                            }
                        }

                        return false; // true to keep the Speed Dial open
                    case R.id.fab_image:



                        if (!checkSUBSCRIBED()) {
                            if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
                                if (mRewardedVideoAd!=null) {
                                    openAction = 80003;
                                    mRewardedVideoAd.show(MainActivity.this,rewardItem -> {
                                        openAction();
                                    });

                                } else {
                                    loadAdmobRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("MAX")){
                                if(maxRewardedVideoAd != null){
                                    if(maxRewardedVideoAd.isReady()){
                                        openAction = 80003;
                                        maxRewardedVideoAd.showAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"));
                                    }else {
                                        loadMaxRewardedVideoAd();
                                        if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                            startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                            overridePendingTransition(R.anim.enter, R.anim.exit);
                                        } else {
                                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            FromLogin = true;
                                        }
                                    }
                                }else {
                                    loadMaxRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
                               if(applovinRewardedVideoAd != null){
                                   if(applovinRewardedVideoAd.isAdReadyToDisplay()){
                                    openAction = 80003;
                                       applovinRewardedVideoAd.show(getApplicationContext(), null, null, new AppLovinAdDisplayListener() {
                                           @Override
                                           public void adDisplayed(AppLovinAd ad) {}
                                           @Override
                                           public void adHidden(AppLovinAd ad) {
                                               openAction();
                                           }
                                       });
                                }else {
                                    loadAppLovinRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                               }else {
                                   loadAppLovinRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
                                if(IronSource.isRewardedVideoAvailable()){
                                    openAction = 80003;
                                    IronSource.showRewardedVideo();
                                }else{
                                    loadISRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else{
                                if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                    startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    FromLogin = true;
                                }
                            }
                        }else{
                            if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            } else {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                FromLogin = true;
                            }
                        }

                        return false; // true to keep the Speed Dial open
                    case R.id.fab_video:




                        if (!checkSUBSCRIBED()) {
                            if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
                                if (mRewardedVideoAd!=null) {
                                    openAction = 80004;
                                    mRewardedVideoAd.show(MainActivity.this,rewardItem -> {
                                        openAction();
                                    });

                                } else {
                                    loadAdmobRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("MAX")){
                                if(maxRewardedVideoAd != null){
                                    if(maxRewardedVideoAd.isReady()){
                                        openAction = 80004;
                                        maxRewardedVideoAd.showAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"));
                                    }else {
                                        loadMaxRewardedVideoAd();
                                        if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                            startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                            overridePendingTransition(R.anim.enter, R.anim.exit);
                                        } else {
                                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            FromLogin = true;
                                        }
                                    }
                                }else {
                                    loadMaxRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
                               if(applovinRewardedVideoAd != null){
                                   if(applovinRewardedVideoAd.isAdReadyToDisplay()){
                                    openAction = 80004;
                                       applovinRewardedVideoAd.show(getApplicationContext(), null, null, new AppLovinAdDisplayListener() {
                                           @Override
                                           public void adDisplayed(AppLovinAd ad) {}
                                           @Override
                                           public void adHidden(AppLovinAd ad) {
                                               openAction();
                                           }
                                       });
                                }else {
                                    loadAppLovinRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                               }else {
                                   loadAppLovinRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
                                if(IronSource.isRewardedVideoAvailable()){
                                    openAction = 80004;
                                    IronSource.showRewardedVideo();
                                }else{
                                    loadISRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else{
                                if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                    startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    FromLogin = true;
                                }
                            }
                        }else{
                            if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                startActivity(new Intent(MainActivity.this, UploadVideoActivity.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            } else {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                FromLogin = true;
                            }
                        }


                        return false; // true to keep the Speed Dial open
                    case R.id.fab_quote:

                        if (!checkSUBSCRIBED()) {
                            if(prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("ADMOB")){
                                if (mRewardedVideoAd!=null) {
                                    openAction = 80005;
                                    mRewardedVideoAd.show(MainActivity.this,rewardItem -> {
                                        openAction();
                                    });

                                } else {
                                    loadAdmobRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("MAX")){
                                if(maxRewardedVideoAd != null){
                                    if(maxRewardedVideoAd.isReady()){
                                        openAction = 80005;
                                        maxRewardedVideoAd.showAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"));
                                    }else {
                                        loadMaxRewardedVideoAd();
                                        if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                            startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                            overridePendingTransition(R.anim.enter, R.anim.exit);
                                        } else {
                                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            FromLogin = true;
                                        }
                                    }
                                }else {
                                    loadMaxRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("APPLOVIN")){
                               if(applovinRewardedVideoAd != null){
                                   if(applovinRewardedVideoAd.isAdReadyToDisplay()){
                                        openAction = 80005;
                                       applovinRewardedVideoAd.show(getApplicationContext(), null, null, new AppLovinAdDisplayListener() {
                                           @Override
                                           public void adDisplayed(AppLovinAd ad) {}
                                           @Override
                                           public void adHidden(AppLovinAd ad) {
                                               openAction();
                                           }
                                       });
                                }else {
                                    loadAppLovinRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                               }else {
                                   loadAppLovinRewardedVideoAd();
                                   if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                       startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                       overridePendingTransition(R.anim.enter, R.anim.exit);
                                   } else {
                                       Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                       startActivity(intent);
                                       FromLogin = true;
                                   }
                               }
                            }else if (prefManager.getString("ADMIN_REWARDED_AD_TYPE").equals("IS")){
                                if(IronSource.isRewardedVideoAvailable()){
                                    openAction = 80005;
                                    IronSource.showRewardedVideo();
                                }else{
                                    loadISRewardedVideoAd();
                                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                        startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        FromLogin = true;
                                    }
                                }
                            }else{
                                if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                    startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    FromLogin = true;
                                }
                            }
                        }else{
                            if (prf.getString("LOGGED").toString().equals("TRUE")) {
                                startActivity(new Intent(MainActivity.this, UploadQuoteActivity.class));
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            } else {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                FromLogin = true;
                            }
                        }


                        return false; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });
        this.followFragment = new FollowFragment();
        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setOffscreenPageLimit(10);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new PopularFragment());
        adapter.addFragment(followFragment);
        adapter.addFragment(new CategroiesFragement());
        adapter.addFragment(new FavoritesFragment());
        adapter.addFragment(new DownloadsFragement());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);



        final BubbleNavigationConstraintView bubbleNavigationLinearView =(BubbleNavigationConstraintView) findViewById(R.id.top_navigation_constraint);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bubbleNavigationLinearView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });



        View headerview = navigationView.getHeaderView(0);
        this.text_view_name_nave_header=(TextView) headerview.findViewById(R.id.text_view_name_nave_header);
        this.circle_image_view_profile_nav_header=(CircleImageView) headerview.findViewById(R.id.circle_image_view_profile_nav_header);
        this.viewPager=(ViewPager) findViewById(R.id.vp_horizontal_ntb);
        this.viewPager.setOffscreenPageLimit(100);

        // set Adapters

        viewPager.setAdapter(adapter);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);

    }
    private void initEvent() {
        // set listener to change the current item of view pager when click bottom nav item

    }
    public boolean checkSUBSCRIBED(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        if (!prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            return false;
        }
        return true;
    }

    private void initData() {
        fragments = new ArrayList<>(4);

        // create music fragment and add it
        HomeFragment homeFragment = new HomeFragment();


        // create backup fragment and add it
        PopularFragment popularFragment = new PopularFragment();

        // create friends fragment and add it
        FavoritesFragment favorFragment = new FavoritesFragment();

        // create friends fragment and add it
        followFragment = new FollowFragment();


        // add to fragments for Adapters
        fragments.add(homeFragment);
        fragments.add(popularFragment);
        fragments.add(followFragment);
        fragments.add(favorFragment);

    }
    public void setFromLogin(){
        this.FromLogin = true;
    }


    @Override
    public void onItemSelected(Language item) {

        List<Language> selectedItems = languageAdapter.getSelectedItems();
        //  Toast.makeText(MainActivity.this,"Selected item is "+ item.getLanguage()+ ", Totally  selectem item count is "+selectedItems.size(),Toast.LENGTH_LONG).show();
    }



    public int getDefaultLangiage(){
        return prefManager.getInt("LANGUAGE_DEFAULT");
    }

    public void loadLang(){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Language>> call = service.languageAll();
        call.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, final Response<List<Language>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()>1){
                        try {
                            item_language.setVisible(true);
                        }catch (NullPointerException e){

                        }
                        if (!prefManager.getString("first_lang_set").equals("true")){
                            prefManager.setString("first_lang_set","true");
                            Intent intent_status  =  new Intent(getApplicationContext(), LanguageActivity.class);
                            startActivity(intent_status);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                        }
                    }else{
                        if (item_language!=null){
                            item_language.setVisible(false);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
            }
        });
    }
    public      void logout(){
        PrefManager prf= new PrefManager(getApplicationContext());
        prf.remove("ID_USER");
        prf.remove("SALT_USER");
        prf.remove("TOKEN_USER");
        prf.remove("NAME_USER");
        prf.remove("TYPE_USER");
        prf.remove("USERN_USER");
        prf.remove("IMAGE_USER");
        prf.remove("LOGGED");
        if (prf.getString("LOGGED").toString().equals("TRUE")){
            text_view_name_nave_header.setText(prf.getString("NAME_USER").toString());
            Picasso.get().load(prf.getString("IMAGE_USER").toString()).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
            if (prf.getString("TYPE_USER").toString().equals("google")){
            }else {
            }
        }else{
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.my_profile).setVisible(false);
            nav_Menu.findItem(R.id.my_earnings).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(false);
            nav_Menu.findItem(R.id.login).setVisible(true);
            text_view_name_nave_header.setText(getResources().getString(R.string.please_login));
            Picasso.get().load(R.drawable.profile).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
        }
        followFragment.Resume();

        Toast.makeText(getApplicationContext(),getString(R.string.message_logout),Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {


        super.onResume();
        checkPermission();



        PrefManager prf= new PrefManager(getApplicationContext());

        Menu nav_Menu = navigationView.getMenu();
        if(checkSUBSCRIBED()){
            nav_Menu.findItem(R.id.buy_now).setVisible(false);
        }else{
            nav_Menu.findItem(R.id.buy_now).setVisible(true);
        }


        if (prf.getString("LOGGED").toString().equals("TRUE")){
            nav_Menu.findItem(R.id.my_profile).setVisible(true);
            if (EarningSystem){
                nav_Menu.findItem(R.id.my_earnings).setVisible(true);
            }else{
                nav_Menu.findItem(R.id.my_earnings).setVisible(false);

            }
            nav_Menu.findItem(R.id.logout).setVisible(true);
            nav_Menu.findItem(R.id.login).setVisible(false);
            text_view_name_nave_header.setText(prf.getString("NAME_USER").toString());
            Picasso.get().load(prf.getString("IMAGE_USER").toString()).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
            if (prf.getString("TYPE_USER").toString().equals("google")){
            }else {
            }
        }else{
            nav_Menu.findItem(R.id.my_earnings).setVisible(false);
            nav_Menu.findItem(R.id.my_profile).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(false);
            nav_Menu.findItem(R.id.login).setVisible(true);

            text_view_name_nave_header.setText(getResources().getString(R.string.please_login));
            Picasso.get().load(R.drawable.profile).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
        }
        if (FromLogin){
            followFragment.Resume();
            FromLogin = false;
        }
        if(old_language==null){
            old_language = prefManager.getString("LANGUAGE_DEFAULT");
        }else{
            if (old_language!=prefManager.getString("LANGUAGE_DEFAULT")){
                old_language = prefManager.getString("LANGUAGE_DEFAULT");
                Intent intent_save = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent_save);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final PrefManager prf = new PrefManager(getApplicationContext());
            if (prf.getString("NOT_RATE_APP").equals("TRUE")) {
                super.onBackPressed();
            } else {
                rateDialog(true);
                return;
            }
        }

    }
    public void showDialog(){
        this.dialog = new Dialog(this,
                R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_subscribe);

        this.text_view_go_pro=(TextView) dialog.findViewById(R.id.text_view_go_pro);
        RelativeLayout relativeLayout_close_rate_gialog=(RelativeLayout) dialog.findViewById(R.id.relativeLayout_close_rate_gialog);
        relativeLayout_close_rate_gialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        this.text_view_go_pro=(TextView) dialog.findViewById(R.id.text_view_go_pro);
        text_view_go_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribe();
            }
        });
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    dialog.dismiss();
                }
                return true;
            }
        });
        dialog.show();
        DialogOpened=true;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void rateDialog(final boolean close){
        this.rateDialog = new Dialog(this,R.style.Theme_Dialog);

        rateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateDialog.setCancelable(true);
        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = rateDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        final   PrefManager prf= new PrefManager(getApplicationContext());
        rateDialog.setCancelable(false);
        rateDialog.setContentView(R.layout.dialog_rating_app);
        final AppCompatRatingBar AppCompatRatingBar_dialog_rating_app=(AppCompatRatingBar) rateDialog.findViewById(R.id.AppCompatRatingBar_dialog_rating_app);
        final LinearLayout linear_layout_feedback=(LinearLayout) rateDialog.findViewById(R.id.linear_layout_feedback);
        final LinearLayout linear_layout_rate=(LinearLayout) rateDialog.findViewById(R.id.linear_layout_rate);
        final Button buttun_send_feedback=(Button) rateDialog.findViewById(R.id.buttun_send_feedback);
        final Button button_later=(Button) rateDialog.findViewById(R.id.button_later);
        final Button button_never=(Button) rateDialog.findViewById(R.id.button_never);
        final Button button_cancel=(Button) rateDialog.findViewById(R.id.button_cancel);
        button_never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prf.setString("NOT_RATE_APP", "TRUE");
                if (close)
                    finish();
            }
        });
        button_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
                if (close)
                    finish();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
                if (close)
                    finish();
            }
        });
        final EditText edit_text_feed_back=(EditText) rateDialog.findViewById(R.id.edit_text_feed_back);
        buttun_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prf.setString("NOT_RATE_APP", "TRUE");
                Retrofit retrofit = apiClient.getClient();
                apiRest service = retrofit.create(apiRest.class);
                Call<ApiResponse> call = service.addSupport("Application rating feedback",AppCompatRatingBar_dialog_rating_app.getRating()+" star(s) Rating".toString(),edit_text_feed_back.getText().toString());
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if(response.isSuccessful()){
                            Toasty.success(getApplicationContext(), getResources().getString(R.string.message_sended), Toast.LENGTH_SHORT).show();
                        }else{
                            Toasty.error(getApplicationContext(), getString(R.string.no_connexion), Toast.LENGTH_SHORT).show();
                        }
                        rateDialog.dismiss();

                        if (close)
                            finish();

                    }
                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toasty.error(getApplicationContext(), getString(R.string.no_connexion), Toast.LENGTH_SHORT).show();
                        rateDialog.dismiss();

                        if (close)
                            finish();
                    }
                });
            }
        });
        AppCompatRatingBar_dialog_rating_app.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser){
                    if (rating>3){
                        final String appPackageName = getApplication().getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        prf.setString("NOT_RATE_APP", "TRUE");
                        rateDialog.dismiss();
                    }else{
                        linear_layout_feedback.setVisibility(View.VISIBLE);
                        linear_layout_rate.setVisibility(View.GONE);
                    }
                }else{

                }
            }
        });
        rateDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    rateDialog.dismiss();
                    if (close)
                        finish();
                }
                return true;

            }
        });
        rateDialog.show();

    }
}
