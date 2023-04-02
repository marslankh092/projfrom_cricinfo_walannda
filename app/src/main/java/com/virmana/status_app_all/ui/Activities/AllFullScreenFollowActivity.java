package com.virmana.status_app_all.ui.Activities;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.applovin.adview.AppLovinAdView;
import com.applovin.mediation.MaxError;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.applovin.mediation.MaxAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxAdListener;;
import com.applovin.mediation.ads.MaxAdView;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.virmana.status_app_all.Adapters.PortraitVideoAdapter;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.api.apiClient;
import com.virmana.status_app_all.api.apiRest;
import com.virmana.status_app_all.model.Category;
import com.virmana.status_app_all.model.Status;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllFullScreenFollowActivity extends AppCompatActivity {


    private String query;

    private Integer page = 0;
    private String language = "0";
    private String order = "created";
    private Boolean loaded=false;
    private Integer type_ads = 0;

    private SwipeRefreshLayout swipe_refreshl_image_category;
    private RecyclerView recycler_view_image_category;
    private List<Status> statusList =new ArrayList<>();
    private List<Category> categoryList =new ArrayList<>();
    private PortraitVideoAdapter portraitVideoAdapter;
    private GridLayoutManager gridLayoutManager;
    private PrefManager prefManager;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RelativeLayout relative_layout_load_more;
    private LinearLayout linear_layout_page_error;
    private Button button_try_again;
    private ImageView imageView_empty_category;
    private PeekAndPop peekAndPop;
    private Integer item = 0 ;
    private Integer lines_beetween_ads = 8 ;
    private Boolean native_ads_enabled = false ;
    private String from = null;
    private int id_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras() ;
        this.prefManager= new PrefManager(getApplicationContext());
        this.language=prefManager.getString("LANGUAGE_DEFAULT");

        setContentView(R.layout.activity_all_full_screen_follow);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Subscription Fullscreen videos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            PrefManager prf= new PrefManager(getApplicationContext());
            if (prf.getString("LOGGED").toString().equals("TRUE")){
                this.id_user = Integer.parseInt(prf.getString("ID_USER"));
            }else{
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
        }catch (java.lang.NullPointerException e){
            startActivity(new Intent(this,MainActivity.class));
           finish();
        }
        initView();
        loadStatus();
        showAdsBanner();
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
        if (from!=null){
            Intent intent = new Intent(AllFullScreenFollowActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
            finish();
        }else{
            super.onBackPressed();
            overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
        }
        return;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem itemMenu) {
        switch (itemMenu.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (from!=null){
                    Intent intent = new Intent(AllFullScreenFollowActivity.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                    finish();
                }else{
                    super.onBackPressed();
                    overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                }
                return true;
        }
        return super.onOptionsItemSelected(itemMenu);
    }

    public void initView(){
        PrefManager prefManager= new PrefManager(getApplicationContext());

        if (!prefManager.getString("ADMIN_NATIVE_TYPE").equals("FALSE")){
            native_ads_enabled=true;
            lines_beetween_ads=Integer.parseInt(prefManager.getString("ADMIN_NATIVE_LINES"));
        }
        if (prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            native_ads_enabled=false;
        }

        this.imageView_empty_category=(ImageView) findViewById(R.id.imageView_empty_category);
        this.relative_layout_load_more=(RelativeLayout) findViewById(R.id.relative_layout_load_more);
        this.button_try_again =(Button) findViewById(R.id.button_try_again);
        this.swipe_refreshl_image_category=(SwipeRefreshLayout) findViewById(R.id.swipe_refreshl_image_category);
        this.linear_layout_page_error=(LinearLayout) findViewById(R.id.linear_layout_page_error);
        this.recycler_view_image_category=(RecyclerView) findViewById(R.id.recycler_view_image_category);
        this.gridLayoutManager=  new GridLayoutManager(this,2);
        if (native_ads_enabled){
            this.gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return ((position +1 ) % (lines_beetween_ads + 1 ) == 0) ? 2 : 1;
                }
            });
        }else{
            this.gridLayoutManager=  new GridLayoutManager(getApplicationContext(),2,RecyclerView.VERTICAL,false);
        }
        this.peekAndPop = new PeekAndPop.Builder(this)
                .parentViewGroupToDisallowTouchEvents(recycler_view_image_category)
                .peekLayout(R.layout.dialog_view)
                .build();
        portraitVideoAdapter =new PortraitVideoAdapter(statusList,this);
        recycler_view_image_category.setHasFixedSize(true);
        recycler_view_image_category.setAdapter(portraitVideoAdapter);
        recycler_view_image_category.setLayoutManager(gridLayoutManager);
        recycler_view_image_category.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {

                    visibleItemCount    = gridLayoutManager.getChildCount();
                    totalItemCount      = gridLayoutManager.getItemCount();
                    pastVisiblesItems   = gridLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            loadNextStatus();
                        }
                    }
                }else{

                }
            }
        });

    }
    public void initAction(){
        swipe_refreshl_image_category.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                statusList.clear();
                portraitVideoAdapter.notifyDataSetChanged();
                page = 0;
                item = 0;
                loading=true;
                loadStatus();
            }
        });
    }
    public void loadNextStatus(){
        relative_layout_load_more.setVisibility(View.VISIBLE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Status>> call = service.followFullScreen(page,language,id_user);
        call.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                apiClient.FormatData(AllFullScreenFollowActivity.this,response);

                if(response.isSuccessful()){
                    if (response.body().size()!=0){
                        for (int i=0;i<response.body().size();i++){
                            if (isNotBlocked(response.body().get(i))){
                                statusList.add(response.body().get(i).setViewType(2));
                                if (native_ads_enabled){
                                    item++;
                                    if (item == lines_beetween_ads ){
                                        item= 0;
                                        if (prefManager.getString("ADMIN_NATIVE_TYPE").equals("ADMOB")) {
                                            statusList.add(new Status().setViewType(11));
                                        }
                                        if (prefManager.getString("ADMIN_NATIVE_TYPE").equals("MAX")) {
                                            statusList.add(new Status().setViewType(13));
                                        }
                                    }
                                }
                            }
                        }
                        portraitVideoAdapter.notifyDataSetChanged();
                        page++;
                        loading=true;

                    }else {

                    }
                    relative_layout_load_more.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                relative_layout_load_more.setVisibility(View.GONE);
            }
        });
    }
    public void loadStatus(){
        swipe_refreshl_image_category.setRefreshing(true);
        imageView_empty_category.setVisibility(View.GONE);
        recycler_view_image_category.setVisibility(View.GONE);
        linear_layout_page_error.setVisibility(View.GONE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Status>> call = service.followFullScreen(page,language,id_user);
        call.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                swipe_refreshl_image_category.setRefreshing(false);
                apiClient.FormatData(AllFullScreenFollowActivity.this,response);

                if(response.isSuccessful()){
                    if (response.body().size()!=0){
                        statusList.clear();
                        for (int i=0;i<response.body().size();i++){
                            if (isNotBlocked(response.body().get(i))){
                                statusList.add(response.body().get(i).setViewType(2));
                                if (native_ads_enabled){
                                    item++;
                                    if (item == lines_beetween_ads ){
                                        item= 0;
                                        if (prefManager.getString("ADMIN_NATIVE_TYPE").equals("ADMOB")) {
                                            statusList.add(new Status().setViewType(11));
                                        }
                                        if (prefManager.getString("ADMIN_NATIVE_TYPE").equals("MAX")) {
                                            statusList.add(new Status().setViewType(13));
                                        }
                                    }
                                }
                            }
                        }
                        portraitVideoAdapter.notifyDataSetChanged();
                        page++;
                        loaded=true;
                        imageView_empty_category.setVisibility(View.GONE);
                        recycler_view_image_category.setVisibility(View.VISIBLE);
                        linear_layout_page_error.setVisibility(View.GONE);
                    }else {
                        imageView_empty_category.setVisibility(View.VISIBLE);
                        recycler_view_image_category.setVisibility(View.GONE);
                        linear_layout_page_error.setVisibility(View.GONE);
                    }


                }else{
                    imageView_empty_category.setVisibility(View.GONE);
                    recycler_view_image_category.setVisibility(View.GONE);
                    linear_layout_page_error.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                imageView_empty_category.setVisibility(View.GONE);
                recycler_view_image_category.setVisibility(View.GONE);
                linear_layout_page_error.setVisibility(View.VISIBLE);
            }
        });
    }
    public boolean isNotBlocked(Status status) {
        final PrefManager prefManager = new PrefManager(getApplicationContext());
        if(prefManager.getString("user_reported_"+status.getUserid()).equals("TRUE"))
            return  false;
        if(prefManager.getString("status_reported_"+status.getId()).equals("TRUE"))
            return  false;
        return true;
    }
}
