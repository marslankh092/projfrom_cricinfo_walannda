package com.virmana.status_app_all.ui.Activities;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.applovin.adview.AppLovinAdView;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.Adapters.AllCategoryVideoAdapter;
import com.virmana.status_app_all.api.apiClient;
import com.virmana.status_app_all.api.apiRest;
import com.virmana.status_app_all.model.Category;
import com.virmana.status_app_all.Provider.PrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.applovin.mediation.MaxAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxAdListener;;
import com.applovin.mediation.MaxError;

import com.applovin.mediation.ads.MaxAdView;
public class AllCategoryActivity extends AppCompatActivity {

    private int id;
    private String title;
    private String image;

    private Integer page = 0;
    private String language = "0";
    private Boolean loaded=false;

    private View view;
    private SwipeRefreshLayout swipe_refreshl_all_category;
    private LinearLayout linear_layout_load_all_category;
    private RecyclerView recycler_view_all_category;
    private List<Category> categoryList =new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private PrefManager prefManager;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RelativeLayout relative_layout_load_more;
    private LinearLayout linear_layout_page_error;
    private Button button_try_again;
    private AllCategoryVideoAdapter allCategoryVideoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prefManager= new PrefManager(getApplicationContext());
        this.language=prefManager.getString("LANGUAGE_DEFAULT");

        setContentView(R.layout.activity_all_category);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.all_categories_status));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showAdsBanner();
        initView();
        loadStatusCategory();
        initAction();

    }
    public boolean checkSUBSCRIBED(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        if (!prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            return false;
        }
        return true;
    }


    public void showAdsBanner() {

          if (!checkSUBSCRIBED()) {
            PrefManager prefManager= new PrefManager(getApplicationContext());
            if (prefManager.getString("ADMIN_BANNER_TYPE").equals("ADMOB")){
                showAdmobBanner();
            }
            if (prefManager.getString("ADMIN_BANNER_TYPE").equals("APPLOVIN")){
                showAppLivinBanner();
            }
            if (prefManager.getString("ADMIN_BANNER_TYPE").equals("MAX")){
                showMaxBanner();
            }
            if (prefManager.getString("ADMIN_BANNER_TYPE").equals("IS")) {
                showISBanner();
            }
        }
    }

    private void showAppLivinBanner() {
        final AppLovinAdView adView = new AppLovinAdView( AppLovinAdSize.BANNER, this );



        final boolean isTablet = AppLovinSdkUtils.isTablet( this );
        final int heightPx = AppLovinSdkUtils.dpToPx( this, isTablet ? 90 : 50 );
        adView.setLayoutParams( new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, heightPx ) );

        // Load an ad!


        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        adView.setVisibility(View.GONE);


        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
        adView.setAdLoadListener(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd ad) {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void failedToReceiveAd(int errorCode) {
                adView.setVisibility(View.GONE);

            }
        });
        LinearLayout linear_layout_ads =  (LinearLayout) findViewById(R.id.adView);

        linear_layout_ads.addView(adView);

        adView.loadNextAd();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }
    public void showISBanner(){

        String userId = IronSource.getAdvertiserId(this);
        PrefManager prefManager= new PrefManager(getApplicationContext());
        IronSource.setUserId( userId);

        IronSource.init(this, prefManager.getString("ADMIN_BANNER_ADMOB_ID"), IronSource.AD_UNIT.BANNER);
        LinearLayout linear_layout_ads =  (LinearLayout) findViewById(R.id.adView);
        IronSourceBannerLayout banner = IronSource.createBanner(this, ISBannerSize.BANNER);
        linear_layout_ads.addView(banner);
        banner.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                Log.v("IROUNSOURCE","loaded");
                banner.setVisibility(View.VISIBLE);

            }
            @Override
            public void onBannerAdLoadFailed(IronSourceError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        linear_layout_ads.removeAllViews();
                    }
                });
                Log.v("IROUNSOURCE",error.getErrorMessage());

            }
            @Override
            public void onBannerAdClicked() {
                Log.v("IROUNSOURCE","onBannerAdClicked");

            }
            @Override
            public void onBannerAdScreenPresented() {
                Log.v("IROUNSOURCE","onBannerAdScreenPresented");

            }
            @Override
            public void onBannerAdScreenDismissed() {
                Log.v("IROUNSOURCE","onBannerAdScreenDismissed");

            }
            @Override
            public void onBannerAdLeftApplication() {
                Log.v("IROUNSOURCE","onBannerAdLeftApplication");

            }
        });
        IronSource.loadBanner(banner);

    }
     public void showMaxBanner(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        MaxAdView adView = new MaxAdView( prefManager.getString("ADMIN_BANNER_ADMOB_ID"), this );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                adView.setVisibility(View.VISIBLE);
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

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });

        adView.setVisibility(View.GONE);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize( this ).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx( this, heightDp );

        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );






        LinearLayout linear_layout_ads =  (LinearLayout) findViewById(R.id.adView);


        linear_layout_ads.addView(adView);



        // Load the ad
        adView.loadAd();
    }
    public void showAdmobBanner(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        LinearLayout linear_layout_ads =  (LinearLayout) findViewById(R.id.adView);
        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(prefManager.getString("ADMIN_BANNER_ADMOB_ID"));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        linear_layout_ads.addView(mAdView);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView(){
        this.relative_layout_load_more=(RelativeLayout) findViewById(R.id.relative_layout_load_more);
        this.button_try_again =(Button) findViewById(R.id.button_try_again);
        this.swipe_refreshl_all_category=(SwipeRefreshLayout) findViewById(R.id.swipe_refreshl_all_category);
        this.linear_layout_page_error=(LinearLayout) findViewById(R.id.linear_layout_page_error);
        this.linear_layout_load_all_category=(LinearLayout) findViewById(R.id.linear_layout_load_all_category);
        this.recycler_view_all_category=(RecyclerView) findViewById(R.id.recycler_view_all_category);
        this.gridLayoutManager=  new GridLayoutManager(this,1);
        allCategoryVideoAdapter =  new AllCategoryVideoAdapter(categoryList,this);
        recycler_view_all_category.setHasFixedSize(true);
        recycler_view_all_category.setAdapter(allCategoryVideoAdapter);
        recycler_view_all_category.setLayoutManager(gridLayoutManager);

    }
    public void initAction(){
        swipe_refreshl_all_category.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categoryList.clear();
                page = 0;
                loading=true;
                loadStatusCategory();
            }
        });
    }
    public void loadStatusCategory(){
        swipe_refreshl_all_category.setRefreshing(true);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Category>> call = service.categoriesImageAll();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    if (response.body().size()!=0){
                        categoryList.clear();
                        for (int i=0;i<response.body().size();i++){
                            categoryList.add(response.body().get(i));
                        }
                        allCategoryVideoAdapter.notifyDataSetChanged();
                    }
                    recycler_view_all_category.setVisibility(View.VISIBLE);
                    linear_layout_load_all_category.setVisibility(View.GONE);
                    linear_layout_page_error.setVisibility(View.GONE);
                    swipe_refreshl_all_category.setRefreshing(false);
                }else{
                    recycler_view_all_category.setVisibility(View.GONE);
                    linear_layout_load_all_category.setVisibility(View.GONE);
                    linear_layout_page_error.setVisibility(View.VISIBLE);
                    swipe_refreshl_all_category.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                recycler_view_all_category.setVisibility(View.GONE);
                linear_layout_load_all_category.setVisibility(View.GONE);
                linear_layout_page_error.setVisibility(View.VISIBLE);
                swipe_refreshl_all_category.setRefreshing(false);

            }
        });
    }
}
