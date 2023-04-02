package com.virmana.status_app_all.ui.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.applovin.adview.AppLovinAdView;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.squareup.picasso.Picasso;
import com.virmana.status_app_all.Adapters.CommentAdapter;
import com.virmana.status_app_all.Adapters.StatusAdapter;
import com.virmana.status_app_all.App;
import com.virmana.status_app_all.Provider.DownloadStorage;
import com.virmana.status_app_all.Provider.FavoritesStorage;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.api.apiClient;
import com.virmana.status_app_all.api.apiRest;
import com.virmana.status_app_all.model.ApiResponse;
import com.virmana.status_app_all.model.Category;
import com.virmana.status_app_all.model.Comment;
import com.virmana.status_app_all.model.Language;
import com.virmana.status_app_all.model.Status;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ImageActivity extends AppCompatActivity {


    private int id;
    private int userid;
    private String user;
    private String userimage;
    private String created;
    private boolean comment;
    private int comments  = 0;
    private String from;
    private String title;
    private String kind;
    private String original;
    private String thumbnail;
    private String type;
    private String extension;
    private int downloads;
    private int views;
    private String tags;
    private boolean review;

    private int like;
    private int love;
    private int angry;
    private int haha;
    private int woow;
    private int sad;
    private String color;

    private Boolean downloading =false;
    private String path;
    private String urlToDownload;


    private ViewPager main_view_pager;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private PrefManager prefManager;
    private String language = "0";
    private String local;
    private String description;



    private CircleImageView circle_image_view_activity_image_user;
    private TextView text_view_activity_image_title;
    private TextView text_view_activity_image_name_user;
    private LikeButton like_button_whatsapp_activity_image;
    private LikeButton like_button_messenger_activity_image;
    private LikeButton like_button_twitter_activity_image;
    private LikeButton like_button_snapshat_activity_image;
    private LikeButton like_button_hike_activity_image;
    private LikeButton like_button_fav_activity_image;
    private LikeButton like_button_copy_activity_image;
    private LikeButton like_botton_share_activity_image;


    private static final String COPY_ID="com.android.copy";

    private static final String WHATSAPP_ID="com.whatsapp";
    private static final String FACEBOOK_ID="com.facebook.katana";
    private static final String MESSENGER_ID="com.facebook.orca";
    private static final String INSTAGRAM_ID="com.instagram.android";
    private static final String SHARE_ID="com.android.all";
    private static final String DOWNLOAD_ID="com.android.download";
    private static final String TWITTER_ID="com.twitter.android";
    private static final String SNAPSHAT_ID="com.snapchat.android";
    private static final String HIKE_ID="com.bsb.hike";




    private TextView text_view_wallpaper_comments_count;
    private RelativeLayout relative_layout_comment_section;
    private EditText edit_text_comment_add;
    private ProgressBar progress_bar_comment_add;
    private ProgressBar progress_bar_comment_list;
    private ImageView image_button_comment_add;
    private RecyclerView recycle_view_comment;
    private ImageView imageView_empty_comment;
    private LikeButton like_button_comments_wallpaper;
    private RelativeLayout relative_layout_wallpaper_comments;


    private ArrayList<Comment> commentList= new ArrayList<>();
    private CommentAdapter commentAdapter;
    private LinearLayoutManager linearLayoutManagerCOmment;
    private RelativeLayout relative_layout_dialog_top;
    private Button button_follow_user_activity;
    private RelativeLayout relative_layout_main;
    private ScrollView scroll_view_main;
    private View view;
    private TextView text_view_comment_box_count;
    private ImageView image_view_comment_box_close;
    private TextView text_view_sad_activity_image;
    private TextView text_view_angry_activity_image;
    private TextView text_view_haha_activity_image;
    private TextView text_view_love_activity_image;
    private TextView text_view_like_activity_image;
    private TextView text_view_woow_activity_image;
    private LikeButton like_button_sad_activity_image;
    private LikeButton like_button_angry_activity_image;
    private LikeButton like_button_woow_activity_image;
    private LikeButton like_button_like_activity_image;
    private LikeButton like_button_haha_activity_image;
    private LikeButton like_button_love_activity_image;

    private RelativeLayout relative_layout_progress_activity_image;
    private TextView text_view_progress_activity_image;
    private ProgressBar progress_bar_activity_image;
    private LikeButton like_button_instagram_activity_image;
    private LikeButton like_button_download_activity_image;
    private LikeButton like_button_facebook_activity_image;
    private LinearLayout linear_layout_reactions_loading;
    private int strtext;

    private ImageView image_view_load_video_item;
    private RelativeLayout relative_layout_activity_image_thumbnail;
    private ImageView image_view_activity_image_original;


    private List<Status> statusList =new ArrayList<>();
    private List<Category> categoryList =new ArrayList<>();
    private StatusAdapter statusAdapter;
    private PeekAndPop peekAndPop;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view_status_load_more;
    private CardView card_view_reactions;
    private LikeButton like_button_reaction_activity_image;
    private TextView text_view_reaction_count;
    private int reaction_count;
    private TextView text_view_activity_image_description;
    private ImageView image_view_status_verified;
    private ImageView image_view_report;
    private MaxInterstitialAd maxInterstitialAd;
    private AppLovinInterstitialAdDialog applovinInterstitialAd;
    private AppLovinAd applovinInterstitialAdBlock;

    private InterstitialAd admobInterstitialAd;
    private Integer code_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        checkPermission();
        loadLang();
        Bundle bundle = getIntent().getExtras() ;
        this.from =  bundle.getString("from");


        this.id = bundle.getInt("id");
        this.title = bundle.getString("title");
        this.description = bundle.getString("description");
        this.thumbnail = bundle.getString("thumbnail");
        this.userid = bundle.getInt("userid");
        this.user = bundle.getString("user");
        this.userimage = bundle.getString("userimage");
        this.type = bundle.getString("type");
        this.original = bundle.getString("original");
        this.extension = bundle.getString("extension");
        this.comment = bundle.getBoolean("comment");
        this.downloads = bundle.getInt("downloads");
        this.views = bundle.getInt("views");
        this.tags = bundle.getString("tags");
        this.review = bundle.getBoolean("review");
        this.comments = bundle.getInt("comments");
        this.created = bundle.getString("created");
        this.local = bundle.getString("local");

        this.woow = bundle.getInt("woow");
        this.like = bundle.getInt("like");
        this.love = bundle.getInt("love");
        this.angry = bundle.getInt("angry");
        this.sad = bundle.getInt("sad");
        this.haha = bundle.getInt("haha");
        this.kind = bundle.getString("kind");
        this.color = bundle.getString("color");

        urlToDownload = original;

        this.prefManager= new PrefManager(getApplicationContext());
        this.language=prefManager.getString("LANGUAGE_DEFAULT");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.reaction_count = haha + like + love + woow +  sad + angry ;


        if(!checkSUBSCRIBED()) {
            if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("ADMOB")) {
                requestAdmobInterstitial();
            }
            if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("APPLOVIN")) {
                requestAppLovinInterstitial();
            }
            if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("IS")) {
                requestISInterstitial();
            }
            if (prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("MAX")) {
                requestMaxInterstitial();
            }
        }


        initView();
        initAction();
        initStatus();
        getUser();
        setReaction(prefManager.getString("reaction_"+id));
        loadMore();

        showAdsBanner();
        addView();

    }

    private void requestISInterstitial() {
        if(!IronSource.isInterstitialReady()){
            PrefManager prefManager= new PrefManager(this);
            IronSource.init(this, prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"), IronSource.AD_UNIT.INTERSTITIAL);
            IronSource.setInterstitialListener(new InterstitialListener() {
                @Override
                public void onInterstitialAdReady() {
                    Log.v("IROUNSOURCE","onInterstitialAdReady");

                }
                @Override
                public void onInterstitialAdLoadFailed(IronSourceError error) {
                    Log.v("IROUNSOURCE",error.getErrorMessage());

                }
                @Override
                public void onInterstitialAdOpened() {
                    Log.v("IROUNSOURCE","onInterstitialAdOpened");

                }
                @Override
                public void onInterstitialAdClosed() {

                    selectOperation(code_selected);

                    requestISInterstitial();

                    Log.v("IROUNSOURCE","onInterstitialAdClosed");

                }
                @Override
                public void onInterstitialAdShowFailed(IronSourceError error) {
                    Log.v("IROUNSOURCE",error.getErrorMessage());

                }
                @Override
                public void onInterstitialAdClicked() {
                    Log.v("IROUNSOURCE","onInterstitialAdClicked");

                }
                @Override
                public void onInterstitialAdShowSucceeded() {
                    Log.v("IROUNSOURCE","onInterstitialAdShowSucceeded");

                }
            });
            IronSource.loadInterstitial();
        }

    }

    private void requestAdmobInterstitial() {
        if (admobInterstitialAd==null){
            PrefManager prefManager= new PrefManager(this);
            AdRequest adRequest = new AdRequest.Builder().build();
            admobInterstitialAd.load(getApplicationContext(), prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    admobInterstitialAd = interstitialAd;


                    admobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            selectOperation(code_selected);

                            Log.d("TAG", "The ad was dismissed.");
                        }


                        @Override
                        public void onAdShowedFullScreenContent() {
                            admobInterstitialAd = null;
                            Log.d("TAG", "The ad was shown.");
                        }
                    });

                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    admobInterstitialAd = null;
                    Log.d("TAG_ADS", "onAdFailedToLoad: "+loadAdError.getMessage());

                }
            });

        }


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
            Intent intent = new Intent(ImageActivity.this,MainActivity.class);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (from!=null){
                    Intent intent = new Intent(ImageActivity.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                    finish();
                }else{
                    super.onBackPressed();
                    overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
        IronSource.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        IronSource.onPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();

    }
    private void initStatus() {


        Picasso.get().load(original).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(this.image_view_activity_image_original);
        Picasso.get().load(userimage).error(R.drawable.profile).placeholder(R.drawable.profile).into(this.circle_image_view_activity_image_user);
        this.text_view_activity_image_title.setText(title);
        if(description != null){
            if (!description.equals("")){
                this.text_view_activity_image_description.setText(description);
                this.text_view_activity_image_description.setVisibility(View.VISIBLE);

            }
        }
        this.text_view_activity_image_name_user.setText(user);
        this.text_view_wallpaper_comments_count.setText(comments+"");
        this.text_view_reaction_count.setText(format(reaction_count));
        if (this.comment){
            relative_layout_comment_section.setVisibility(View.VISIBLE);
        }else{
            relative_layout_comment_section.setVisibility(View.GONE);
        }


        this.text_view_wallpaper_comments_count.setText(comments+"");
        this.text_view_comment_box_count.setText(comments+" "+ImageActivity.this.getResources().getString(R.string.comments));
        if (this.comment){
            relative_layout_comment_section.setVisibility(View.VISIBLE);
        }else{
            relative_layout_comment_section.setVisibility(View.GONE);
        }

        this.text_view_like_activity_image.setText(format(like));
        this.text_view_love_activity_image.setText(format(love));
        this.text_view_angry_activity_image.setText(format(angry));
        this.text_view_haha_activity_image.setText(format(haha));
        this.text_view_woow_activity_image.setText(format(woow));
        this.text_view_sad_activity_image.setText(format(sad));



    }

    private void initView() {
        this.image_view_report=(ImageView) findViewById(R.id.image_view_report);
        this.card_view_reactions=(CardView) findViewById(R.id.card_view_reactions);
        this.recycler_view_status_load_more=(RecyclerView) findViewById(R.id.recycler_view_status_load_more);
        this.peekAndPop = new PeekAndPop.Builder(ImageActivity.this)
                .parentViewGroupToDisallowTouchEvents(recycler_view_status_load_more)
                .peekLayout(R.layout.dialog_view)
                .build();
        this.linearLayoutManager=  new LinearLayoutManager(ImageActivity.this, RecyclerView.VERTICAL, false);
        statusAdapter =new StatusAdapter(statusList,categoryList,ImageActivity.this,peekAndPop);
        recycler_view_status_load_more.setHasFixedSize(true);
        recycler_view_status_load_more.setAdapter(statusAdapter);
        recycler_view_status_load_more.setLayoutManager(linearLayoutManager);

        this.image_view_status_verified=(ImageView) findViewById(R.id.image_view_status_verified);

        this.image_view_activity_image_original=(ImageView) findViewById(R.id.image_view_activity_image_original);
        this.image_view_load_video_item=(ImageView) findViewById(R.id.image_view_load_video_item);
        this.relative_layout_activity_image_thumbnail=(RelativeLayout) findViewById(R.id.relative_layout_activity_image_thumbnail);
        this.text_view_reaction_count = (TextView) findViewById(R.id.text_view_reaction_count);
        this.linear_layout_reactions_loading=(LinearLayout) findViewById(R.id.linear_layout_reactions_loading);
        this.like_button_reaction_activity_image=(LikeButton) findViewById(R.id.like_button_reaction_activity_image);
        this.like_button_download_activity_image=(LikeButton) findViewById(R.id.like_button_download_activity_image);
        this.like_button_facebook_activity_image=(LikeButton) findViewById(R.id.like_button_facebook_activity_image);
        this.like_button_instagram_activity_image=(LikeButton) findViewById(R.id.like_button_instagram_activity_image);
        this.progress_bar_activity_image=(ProgressBar) findViewById(R.id.progress_bar_activity_image);
        this.text_view_progress_activity_image=(TextView) findViewById(R.id.text_view_progress_activity_image);
        this.relative_layout_progress_activity_image=(RelativeLayout) findViewById(R.id.relative_layout_progress_activity_image);
        this.like_button_angry_activity_image=(LikeButton) findViewById(R.id.like_button_angry_activity_image);
        this.like_button_like_activity_image=(LikeButton) findViewById(R.id.like_button_like_activity_image);
        this.like_button_love_activity_image=(LikeButton) findViewById(R.id.like_button_love_activity_image);
        this.like_button_sad_activity_image=(LikeButton) findViewById(R.id.like_button_sad_activity_image);
        this.like_button_woow_activity_image=(LikeButton) findViewById(R.id.like_button_woow_activity_image);
        this.like_button_haha_activity_image=(LikeButton) findViewById(R.id.like_button_haha_activity_image);

        this.text_view_sad_activity_image=(TextView) findViewById(R.id.text_view_sad_activity_image);
        this.text_view_angry_activity_image=(TextView) findViewById(R.id.text_view_angry_activity_image);
        this.text_view_haha_activity_image=(TextView) findViewById(R.id.text_view_haha_activity_image);
        this.text_view_love_activity_image=(TextView) findViewById(R.id.text_view_love_activity_image);
        this.text_view_like_activity_image=(TextView) findViewById(R.id.text_view_like_activity_image);
        this.text_view_woow_activity_image=(TextView) findViewById(R.id.text_view_woow_activity_image);
        this.image_view_comment_box_close=(ImageView) findViewById(R.id.image_view_comment_box_close);
        this.text_view_comment_box_count=(TextView) findViewById(R.id.text_view_comment_box_count);
        this.scroll_view_main=(ScrollView) findViewById(R.id.scroll_view_main);
        this.relative_layout_main=(RelativeLayout) findViewById(R.id.relative_layout_main);
        this.like_button_whatsapp_activity_image=(LikeButton) findViewById(R.id.like_button_whatsapp_activity_image);
        this.like_button_messenger_activity_image=(LikeButton) findViewById(R.id.like_button_messenger_activity_image);
        this.like_button_twitter_activity_image=(LikeButton) findViewById(R.id.like_button_twitter_activity_image);
        this.like_button_snapshat_activity_image=(LikeButton) findViewById(R.id.like_button_snapshat_activity_image);
        this.like_button_hike_activity_image=(LikeButton) findViewById(R.id.like_button_hike_activity_image);

        this.like_button_fav_activity_image=(LikeButton) findViewById(R.id.like_button_fav_activity_image);
        this.like_botton_share_activity_image=(LikeButton) findViewById(R.id.like_botton_share_activity_image);

        this.text_view_activity_image_name_user=(TextView) findViewById(R.id.text_view_activity_image_name_user);
        this.text_view_activity_image_title=(TextView) findViewById(R.id.text_view_activity_image_title);
        this.text_view_activity_image_description=(TextView) findViewById(R.id.text_view_activity_image_description);
        this.circle_image_view_activity_image_user=(CircleImageView) findViewById(R.id.circle_image_view_activity_image_user);




        this.linearLayoutManager=  new LinearLayoutManager(ImageActivity.this, RecyclerView.VERTICAL, false);


        this.linearLayoutManagerCOmment=  new LinearLayoutManager(ImageActivity.this, RecyclerView.VERTICAL, false);

        this.text_view_wallpaper_comments_count=(TextView) findViewById(R.id.text_view_wallpaper_comments_count);
        this.relative_layout_comment_section=(RelativeLayout) findViewById(R.id.relative_layout_comment_section);
        this.edit_text_comment_add=(EditText) findViewById(R.id.edit_text_comment_add);
        this.progress_bar_comment_add=(ProgressBar) findViewById(R.id.progress_bar_comment_add);
        this.progress_bar_comment_list=(ProgressBar) findViewById(R.id.progress_bar_comment_list);
        this.image_button_comment_add=(ImageView) findViewById(R.id.image_button_comment_add);
        this.recycle_view_comment=(RecyclerView) findViewById(R.id.recycle_view_comment);
        this.commentAdapter = new CommentAdapter(commentList, ImageActivity.this);
        this.recycle_view_comment.setHasFixedSize(true);
        this.recycle_view_comment.setAdapter(commentAdapter);
        this.recycle_view_comment.setLayoutManager(linearLayoutManagerCOmment);
        this.imageView_empty_comment=(ImageView) findViewById(R.id.imageView_empty_comment);
        this.like_button_comments_wallpaper=(LikeButton) findViewById(R.id.like_botton_comment_activity_gif);
        this.relative_layout_wallpaper_comments=(RelativeLayout) findViewById(R.id.relative_layout_wallpaper_comments);
        image_button_comment_add.setEnabled(false);


        this.button_follow_user_activity=(Button) findViewById(R.id.button_follow_user_activity);
        this.relative_layout_dialog_top=(RelativeLayout) findViewById(R.id.relative_layout_dialog_top);


        final FavoritesStorage storageFavorites= new FavoritesStorage(ImageActivity.this.getApplicationContext());

        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
        Boolean exist = false;
        if (favorites_list==null){
            favorites_list= new ArrayList<>();
        }
        for (int i = 0; i <favorites_list.size() ; i++) {
            if (favorites_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            like_button_fav_activity_image.setLiked(false);
        }else{
            like_button_fav_activity_image.setLiked(true);
        }
    }

    private void initAction() {
        this.image_view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.setOnMenuItemClickListener(item -> {
                    Report(item,ImageActivity.this);
                    return true;
                });
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
        this.like_button_like_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addLike(id);
                card_view_reactions.setVisibility(View.GONE);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLike(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });

        this.like_button_love_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addLove(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteLove(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.like_button_woow_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addWoow(id);
                card_view_reactions.setVisibility(View.GONE);


            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteWoow(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });

        this.like_button_angry_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addAngry(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteAngry(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.like_button_sad_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addSad(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteSad(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.like_button_haha_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addHaha(id);
                card_view_reactions.setVisibility(View.GONE);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                deleteHaha(id);
                setReaction("none");
                prefManager.setString("reaction_"+id,"none");
                card_view_reactions.setVisibility(View.GONE);

            }
        });
        this.image_view_comment_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentBox();
            }
        });
        this.button_follow_user_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow();
            }
        });
        this.relative_layout_dialog_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageActivity.this,UserActivity.class);
                intent.putExtra("id",userid);
                intent.putExtra("name",user);
                intent.putExtra("image",userimage);
                startActivity(intent);
            }
        });

        this.like_button_whatsapp_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_whatsapp_activity_image.setLiked(false);

                Operation(90001);

            }
        });
        this.like_button_messenger_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_messenger_activity_image.setLiked(false);

                Operation(90002);

            }
        });
        this.like_button_facebook_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_facebook_activity_image.setLiked(false);

                Operation(90003);

            }
        });
        this.like_button_instagram_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_instagram_activity_image.setLiked(false);



                Operation(90004);

            }
        });
        this.like_button_twitter_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_twitter_activity_image.setLiked(false);


                Operation(90005);

            }
        });
        this.like_botton_share_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_botton_share_activity_image.setLiked(false);

                Operation(90006);
            }
        });
        this.like_button_download_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_download_activity_image.setLiked(false);

                Operation(90007);
            }
        });

        this.like_button_hike_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_hike_activity_image.setLiked(false);




                Operation(90008);

            }
        });
        this.like_button_snapshat_activity_image.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(LikeButton likeButton) {
                like_button_snapshat_activity_image.setLiked(false);



                Operation(90009);
            }
        });
        this.like_button_fav_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                favorite();
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                favorite();
            }
        });


        this.image_button_comment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });

        this.edit_text_comment_add.addTextChangedListener(new ImageActivity.CommentTextWatcher(this.edit_text_comment_add));



        this.like_button_comments_wallpaper.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                like_button_comments_wallpaper.setLiked(false);
                showCommentBox();
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                like_button_comments_wallpaper.setLiked(false);
                showCommentBox();
            }
        });
        this.like_button_reaction_activity_image.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                like_button_comments_wallpaper.setLiked(false);
                card_view_reactions.setVisibility(View.VISIBLE);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                like_button_comments_wallpaper.setLiked(false);
                card_view_reactions.setVisibility(View.VISIBLE);
            }
        });

    }
    private class CommentTextWatcher implements TextWatcher {
        private View view;
        private CommentTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edit_text_comment_add:
                    ValidateComment();
                    break;
            }
        }
    }

    private boolean ValidateComment() {
        String email = edit_text_comment_add.getText().toString().trim();
        if (email.isEmpty()) {
            image_button_comment_add.setEnabled(false);
            return false;
        }else{
            image_button_comment_add.setEnabled(true);
        }
        return true;
    }
    public void getComments(){
        progress_bar_comment_list.setVisibility(View.VISIBLE);
        recycle_view_comment.setVisibility(View.GONE);
        imageView_empty_comment.setVisibility(View.GONE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Comment>> call = service.getComments(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()) {
                    commentList.clear();
                    comments = response.body().size();
                    text_view_wallpaper_comments_count.setText(comments+"");
                    text_view_comment_box_count.setText(comments+" "+ImageActivity.this.getResources().getString(R.string.comments));
                    if (response.body().size() != 0) {
                        for (int i = 0; i < response.body().size(); i++) {
                            commentList.add(response.body().get(i));
                        }
                        commentAdapter.notifyDataSetChanged();

                        progress_bar_comment_list.setVisibility(View.GONE);
                        recycle_view_comment.setVisibility(View.VISIBLE);
                        imageView_empty_comment.setVisibility(View.GONE);


                    } else {
                        progress_bar_comment_list.setVisibility(View.GONE);
                        recycle_view_comment.setVisibility(View.GONE);
                        imageView_empty_comment.setVisibility(View.VISIBLE);

                    }
                }else{

                }
                recycle_view_comment.setNestedScrollingEnabled(false);

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {


            }
        });
    }
    public void showCommentBox(){
        getComments();
        if (relative_layout_wallpaper_comments.getVisibility() == View.VISIBLE)
        {
            Animation c= AnimationUtils.loadAnimation(ImageActivity.this.getApplicationContext(),
                    R.anim.slide_down);
            c.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    relative_layout_wallpaper_comments.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            relative_layout_wallpaper_comments.startAnimation(c);


        }else{
            Animation c= AnimationUtils.loadAnimation(ImageActivity.this.getApplicationContext(),
                    R.anim.slide_up);
            c.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    relative_layout_wallpaper_comments.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            relative_layout_wallpaper_comments.startAnimation(c);

        }

    }

    public void favorite(){
        final FavoritesStorage storageFavorites= new FavoritesStorage(ImageActivity.this.getApplicationContext());

        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
        Boolean exist = false;
        if (favorites_list==null){
            favorites_list= new ArrayList<>();
        }
        for (int i = 0; i <favorites_list.size() ; i++) {
            if (favorites_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            ArrayList<Status> audios= new ArrayList<Status>();
            for (int i = 0; i < favorites_list.size(); i++) {
                audios.add(favorites_list.get(i));
            }




            Status fragement_Status_joke = new Status();
            fragement_Status_joke.setId(id);
            fragement_Status_joke.setTitle(title);
            fragement_Status_joke.setDescription(description);
            fragement_Status_joke.setDownloads(downloads);
            fragement_Status_joke.setViews(views);
            fragement_Status_joke.setThumbnail(thumbnail);
            fragement_Status_joke.setOriginal(original);
            fragement_Status_joke.setUserid(userid);
            fragement_Status_joke.setUser(user);
            fragement_Status_joke.setTags(tags);
            fragement_Status_joke.setReview(review);
            fragement_Status_joke.setUserimage(userimage);
            fragement_Status_joke.setComments(comments);
            fragement_Status_joke.setComment(comment);
            fragement_Status_joke.setCreated(created);
            fragement_Status_joke.setExtension(extension);
            fragement_Status_joke.setType(type);

            fragement_Status_joke.setLike(like);
            fragement_Status_joke.setKind(kind);
            fragement_Status_joke.setLove(love);
            fragement_Status_joke.setAngry(angry);
            fragement_Status_joke.setSad(sad);
            fragement_Status_joke.setHaha(haha);
            fragement_Status_joke.setWoow(woow);



            audios.add(fragement_Status_joke);
            storageFavorites.storeImage(audios);
        }else{
            ArrayList<Status> new_favorites= new ArrayList<Status>();
            for (int i = 0; i < favorites_list.size(); i++) {
                if (!favorites_list.get(i).getId().equals(id)){
                    new_favorites.add(favorites_list.get(i));

                }
            }
            storageFavorites.storeImage(new_favorites);
        }
    }
    public void AddDownloadLocal(String localpath){
        final DownloadStorage downloadStorage= new DownloadStorage(ImageActivity.this.getApplicationContext());
        List<Status> download_list = downloadStorage.loadImagesFavorites();
        Boolean exist = false;
        if (download_list==null){
            download_list= new ArrayList<>();
        }
        for (int i = 0; i <download_list.size() ; i++) {
            if (download_list.get(i).getId().equals(id)){
                exist = true;
            }
        }
        if (exist  == false) {
            ArrayList<Status> audios= new ArrayList<Status>();
            for (int i = 0; i < download_list.size(); i++) {
                audios.add(download_list.get(i));
            }
            Status videodownloaded = new Status();
            videodownloaded.setId(id);
            videodownloaded.setTitle(title);
            videodownloaded.setDescription(description);
            videodownloaded.setDownloads(downloads);
            videodownloaded.setViews(views);
            videodownloaded.setThumbnail(thumbnail);
            videodownloaded.setOriginal(original);
            videodownloaded.setUser(user);
            videodownloaded.setUserid(userid);
            videodownloaded.setTags(tags);
            videodownloaded.setReview(review);
            videodownloaded.setUserimage(userimage);
            videodownloaded.setComments(comments);
            videodownloaded.setComment(comment);
            videodownloaded.setCreated(created);
            videodownloaded.setExtension(extension);
            videodownloaded.setType(type);
            videodownloaded.setKind(kind);

            videodownloaded.setLike(like);
            videodownloaded.setLove(love);
            videodownloaded.setAngry(angry);
            videodownloaded.setSad(sad);
            videodownloaded.setHaha(haha);
            videodownloaded.setWoow(woow);
            videodownloaded.setLocal(localpath);

            audios.add(videodownloaded);
            downloadStorage.storeImage(audios);
        }
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(ImageActivity.this,   Manifest.permission.READ_CONTACTS)) {
                    Intent intent_image  =  new Intent(ImageActivity.this.getApplicationContext(), PermissionActivity.class);
                    startActivity(intent_image);
                    ImageActivity.this.overridePendingTransition(R.anim.enter, R.anim.exit);
                    ImageActivity.this.finish();
                } else {
                    Intent intent_image  =  new Intent(ImageActivity.this.getApplicationContext(), PermissionActivity.class);
                    startActivity(intent_image);
                    ImageActivity.this.overridePendingTransition(R.anim.enter, R.anim.exit);
                    ImageActivity.this.finish();
                }
            }

        }
    }




    public void loadLang(){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Language>> call = service.languageAll();
        call.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, final Response<List<Language>> response) {

            }
            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
            }
        });
    }
    public void addComment(){


        PrefManager prf= new PrefManager(ImageActivity.this.getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")){

            byte[] data = new byte[0];
            String comment_final ="";
            try {
                data = edit_text_comment_add.getText().toString().getBytes("UTF-8");
                comment_final = Base64.encodeToString(data, Base64.DEFAULT);

            } catch (UnsupportedEncodingException e) {
                comment_final = edit_text_comment_add.getText().toString();
                e.printStackTrace();
            }
            progress_bar_comment_add.setVisibility(View.VISIBLE);
            image_button_comment_add.setVisibility(View.GONE);
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.addCommentImage(prf.getString("ID_USER").toString(),id,comment_final);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode()==200){
                            comments++ ;
                            text_view_wallpaper_comments_count.setText(comments +"");
                            text_view_comment_box_count.setText(text_view_wallpaper_comments_count.getText()+" "+ImageActivity.this.getResources().getString(R.string.comments));
                            recycle_view_comment.setVisibility(View.VISIBLE);
                            imageView_empty_comment.setVisibility(View.GONE);
                            Toasty.success(ImageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            edit_text_comment_add.setText("");
                            String id="";
                            String content="";
                            String user="";
                            String image="";
                            String trusted="false";

                            for (int i=0;i<response.body().getValues().size();i++){
                                if (response.body().getValues().get(i).getName().equals("id")){
                                    id=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("content")){
                                    content=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("user")){
                                    user=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("trusted")){
                                    trusted=response.body().getValues().get(i).getValue();
                                }
                                if (response.body().getValues().get(i).getName().equals("image")){
                                    image=response.body().getValues().get(i).getValue();
                                }
                            }
                            Comment comment= new Comment();
                            comment.setId(Integer.parseInt(id));
                            comment.setUser(user);
                            comment.setContent(content);
                            comment.setImage(image);
                            comment.setEnabled(true);
                            comment.setTrusted(trusted);
                            comment.setCreated(getResources().getString(R.string.now_time));
                            commentList.add(comment);
                            commentAdapter.notifyDataSetChanged();

                        }else{
                            Toasty.error(ImageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                    recycle_view_comment.scrollToPosition(recycle_view_comment.getAdapter().getItemCount()-1);
                    commentAdapter.notifyDataSetChanged();
                    progress_bar_comment_add.setVisibility(View.GONE);
                    image_button_comment_add.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    progress_bar_comment_add.setVisibility(View.VISIBLE);
                    image_button_comment_add.setVisibility(View.GONE);
                }
            });
        }else{
            Intent intent = new Intent(ImageActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }
    public void follow(){

        PrefManager prf= new PrefManager(ImageActivity.this.getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user_activity.setText(getResources().getString(R.string.loading));
            button_follow_user_activity.setEnabled(false);
            String follower = prf.getString("ID_USER");
            String key = prf.getString("TOKEN_USER");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.follow(userid, Integer.parseInt(follower), key);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode().equals(200)){
                            button_follow_user_activity.setText("UnFollow");
                        }else if (response.body().getCode().equals(202)) {
                            button_follow_user_activity.setText("Follow");

                        }
                    }
                    button_follow_user_activity.setEnabled(true);

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    button_follow_user_activity.setEnabled(true);
                }
            });
        }else{
            Intent intent = new Intent(ImageActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    private void getUser() {
        PrefManager prf= new PrefManager(ImageActivity.this.getApplicationContext());
        Integer follower= -1;
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user_activity.setEnabled(false);
            follower = Integer.parseInt(prf.getString("ID_USER"));
        }
        if (follower!=userid){
            button_follow_user_activity.setVisibility(View.VISIBLE);
        }
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.getUser(userid,follower);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){

                    for (int i=0;i<response.body().getValues().size();i++){
                        if (response.body().getValues().get(i).getName().equals("trusted")) {
                            if (response.body().getValues().get(i).getValue().equals("true"))
                                image_view_status_verified.setVisibility(View.VISIBLE);
                            else
                                image_view_status_verified.setVisibility(View.GONE);
                        }
                        if (response.body().getValues().get(i).getName().equals("follow")){
                            if (response.body().getValues().get(i).getValue().equals("true"))
                                button_follow_user_activity.setText("UnFollow");
                            else
                                button_follow_user_activity.setText("Follow");
                        }
                    }

                }else{


                }
                button_follow_user_activity.setEnabled(true);
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                button_follow_user_activity.setEnabled(true);
            }
        });
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }
    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public void addLike(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        reaction_count++;

        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddLike(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    like=response.body();
                    text_view_like_activity_image.setText(format(like));
                    setReaction("like");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addLove(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddLove(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    love=response.body();
                    text_view_love_activity_image.setText(format(love));
                    setReaction("love");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addSad(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddSad(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    sad=response.body();
                    text_view_sad_activity_image.setText(format(sad));
                    setReaction("sad");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addAngry(Integer id){
        removeReaction(prefManager.getString("reaction_"+id));
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);
        reaction_count++;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddAngry(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    angry=response.body();
                    text_view_angry_activity_image.setText(format(angry));
                    setReaction("angry");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addHaha(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        removeReaction(prefManager.getString("reaction_"+id));
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddHaha(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    haha=response.body();
                    text_view_haha_activity_image.setText(format(haha));
                    setReaction("haha");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void addWoow(Integer id){
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        removeReaction(prefManager.getString("reaction_"+id));
        reaction_count++;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageAddWoow(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    woow=response.body();
                    text_view_woow_activity_image.setText(format(woow));
                    setReaction("woow");
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
        });
    }
    public void deleteWoow(Integer id){
        reaction_count--;

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteWoow(id);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    woow=response.body();
                    text_view_woow_activity_image.setText(format(woow));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);
            }
        });
    }
    public void deleteLike(Integer id){
        reaction_count--;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteLike(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    like=response.body();
                    text_view_like_activity_image.setText(format(like));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteAngry(Integer id){
        reaction_count--;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteAngry(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    angry=response.body();
                    text_view_angry_activity_image.setText(format(angry));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteHaha(Integer id){
        reaction_count--;
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Call<Integer> call = service.imageDeleteHaha(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    haha=response.body();
                    text_view_haha_activity_image.setText(format(haha));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteSad(Integer id){
        reaction_count--;

        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteSad(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    sad=response.body();
                    text_view_sad_activity_image.setText(format(sad));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void deleteLove(Integer id){
        reaction_count--;

        linear_layout_reactions_loading.setVisibility(View.VISIBLE);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.imageDeleteLove(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                if(response.isSuccessful()){
                    love=response.body();
                    text_view_love_activity_image.setText(format(love));
                }
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                linear_layout_reactions_loading.setVisibility(View.GONE);

            }
        });
    }
    public void setReaction(String reaction){
        text_view_like_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_love_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_angry_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_sad_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_haha_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));
        text_view_woow_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count));

        text_view_like_activity_image.setTextColor(getResources().getColor(R.color.textPrimaryColor));
        text_view_woow_activity_image.setTextColor(getResources().getColor(R.color.textPrimaryColor));
        text_view_love_activity_image.setTextColor(getResources().getColor(R.color.textPrimaryColor));
        text_view_sad_activity_image.setTextColor(getResources().getColor(R.color.textPrimaryColor));
        text_view_angry_activity_image.setTextColor(getResources().getColor(R.color.textPrimaryColor));
        text_view_haha_activity_image.setTextColor(getResources().getColor(R.color.textPrimaryColor));

        like_button_like_activity_image.setLiked(false);
        like_button_love_activity_image.setLiked(false);
        like_button_angry_activity_image.setLiked(false);
        like_button_haha_activity_image.setLiked(false);
        like_button_sad_activity_image.setLiked(false);
        like_button_woow_activity_image.setLiked(false);


        if (reaction.equals("like")){
            prefManager.setString("reaction_"+id,"like");
            text_view_like_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_like_activity_image.setTextColor(getResources().getColor(R.color.white));
            like_button_like_activity_image.setLiked(true);

        }else if (reaction.equals("woow")){
            prefManager.setString("reaction_"+id,"woow");
            text_view_woow_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_woow_activity_image.setTextColor(getResources().getColor(R.color.white));
            like_button_woow_activity_image.setLiked(true);

        }else if (reaction.equals("love")){
            prefManager.setString("reaction_"+id,"love");
            text_view_love_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_love_activity_image.setTextColor(getResources().getColor(R.color.white));
            like_button_love_activity_image.setLiked(true);

        }else if (reaction.equals("angry")){
            prefManager.setString("reaction_"+id,"angry");
            text_view_angry_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_angry_activity_image.setTextColor(getResources().getColor(R.color.white));
            like_button_angry_activity_image.setLiked(true);

        }else if (reaction.equals("sad")){
            prefManager.setString("reaction_"+id,"sad");
            text_view_sad_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_sad_activity_image.setTextColor(getResources().getColor(R.color.white));
            like_button_sad_activity_image.setLiked(true);

        }else if (reaction.equals("haha")){
            prefManager.setString("reaction_"+id,"haha");
            text_view_haha_activity_image.setBackground(getResources().getDrawable(R.drawable.bg_card_count_select));
            text_view_haha_activity_image.setTextColor(getResources().getColor(R.color.white));
            like_button_haha_activity_image.setLiked(true);
        }
        this.text_view_reaction_count.setText(format(reaction_count));

    }

    public void removeReaction(String  reaction){
        if (reaction.equals("like")){
            deleteLike(id);
        }else if (reaction.equals("woow")){
            deleteWoow(id);
        }else if (reaction.equals("love")){
            deleteLove(id);
        }else if (reaction.equals("angry")){
            deleteAngry(id);
        }else if (reaction.equals("sad")){
            deleteSad(id);
        }else if (reaction.equals("haha")){
            deleteHaha(id);
        }
    }



    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<Object, String, String> {

        private int position;
        private String old = "-100";
        private boolean runing = true;
        private String share_app;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setDownloading(true);
            Log.v("prepost","ok");
        }
        public boolean dir_exists(String dir_path)
        {
            boolean ret = false;
            File dir = new File(dir_path);
            if(dir.exists() && dir.isDirectory())
                ret = true;
            return ret;
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            runing = false;
        }
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(Object... f_url) {
            int count;
            try {
                URL url = new URL((String) f_url[0]);
                String title = (String) f_url[1];
                String extension = (String) f_url[2];
                this.position = (int) f_url[3];
                this.share_app = (String) f_url[4];
                Log.v("v",(String) f_url[0]);

                URLConnection conection = url.openConnection();
                conection.setRequestProperty("Accept-Encoding", "identity");
                conection.connect();

                int lenghtOfFile = conection.getContentLength();
                Log.v("lenghtOfFile",lenghtOfFile+"");

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);


                String dir_path = Environment.getExternalStorageDirectory().toString() + "/StatusVideos/";
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    dir_path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                }

                if (!dir_exists(dir_path)){
                    File directory = new File(dir_path);
                    if(directory.mkdirs()){
                        Log.v("dir","is created 1");
                    }else{
                        Log.v("dir","not created 1");

                    }
                    if(directory.mkdir()){
                        Log.v("dir","is created 2");
                    }else{
                        Log.v("dir","not created 2");

                    }
                }else{
                    Log.v("dir","is exist");
                }
                File file= new File(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);
                if(!file.exists()){
                    Log.v("dir","file is exist");
                    OutputStream output = new FileOutputStream(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);


                    byte data[] = new byte[1024];

                    long total = 0;


                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress(""+(int)((total*100)/lenghtOfFile));
                        // writing data to file
                        output.write(data, 0, count);
                        if (!runing){
                            Log.v("v","not rurning");
                        }
                    }

                    output.flush();

                    output.close();
                    input.close();

                }
                MediaScannerConnection.scanFile(ImageActivity.this.getApplicationContext(), new String[] { dir_path+title.toString().replace("/","_")+"_"+id+"."+extension },
                        null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    final Uri contentUri = Uri.fromFile(new File(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension));
                    scanIntent.setData(contentUri);
                    ImageActivity.this.sendBroadcast(scanIntent);
                } else {
                    final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                    ImageActivity.this.sendBroadcast(intent);
                }
                path = dir_path+title.toString().replace("/","_")+"_"+id+"."+extension;
            } catch (Exception e) {
                //Log.v("ex",e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            try {
                if(!progress[0].equals(old)){
                    old=progress[0];
                    Log.v("download",progress[0]+"%");
                    setDownloading(true);
                    setProgressValue(Integer.parseInt(progress[0]));
                }
            }catch (Exception e){

            }

        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {

            setDownloading(false);
            if (path==null){
                if (local!=null){
                    switch (share_app) {
                        case WHATSAPP_ID:
                            shareWhatsapp(local);
                            break;
                        case FACEBOOK_ID:
                            shareFacebook(local);
                            break;
                        case MESSENGER_ID:
                            shareMessenger(local);
                            break;
                        case INSTAGRAM_ID:
                            shareInstagram(local);
                            break;
                        case SHARE_ID:
                            share(local);
                            break;
                        case TWITTER_ID:
                            shareTwitter(local);
                            break;
                        case SNAPSHAT_ID:
                            shareSnapshat(local);
                            break;
                        case HIKE_ID:
                            shareHike(local);
                            break;
                        case DOWNLOAD_ID:
                            download();
                            break;
                    }
                }else {
                    try {
                        Toasty.error(App.getInstance(), getResources().getString(R.string.download_failed), Toast.LENGTH_SHORT, true).show();
                    }catch (Exception e){

                    }
                }

            }else {
                addShare(id);
                AddDownloadLocal(path);
                switch (share_app) {
                    case WHATSAPP_ID:
                        shareWhatsapp(path);
                        break;
                    case FACEBOOK_ID:
                        shareFacebook(path);
                        break;
                    case MESSENGER_ID:
                        shareMessenger(path);
                        break;
                    case INSTAGRAM_ID:
                        shareInstagram(path);
                        break;
                    case SHARE_ID:
                        share(path);
                        break;
                    case TWITTER_ID:
                        shareTwitter(path);
                        break;
                    case SNAPSHAT_ID:
                        shareSnapshat(path);
                        break;
                    case HIKE_ID:
                        shareHike(path);
                        break;
                    case DOWNLOAD_ID:
                        download();
                        break;
                }
            }
        }

        private void download() {
            Toasty.success(ImageActivity.this.getApplicationContext(), getResources().getString(R.string.images_downloaded), Toast.LENGTH_SHORT, true).show();
        }
        public void shareWhatsapp(String path){

            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(WHATSAPP_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(),getResources().getString(R.string.whatsapp_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareFacebook(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(FACEBOOK_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(),getResources().getString(R.string.facebook_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareMessenger(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(MESSENGER_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(),getResources().getString(R.string.messenger_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareSnapshat(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(SNAPSHAT_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(),getResources().getString(R.string.snapchat_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareHike(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(HIKE_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);


            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(),getResources().getString(R.string.hike_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareInstagram(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(INSTAGRAM_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(), getResources().getString(R.string.instagram_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareTwitter(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(TWITTER_ID);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(), getResources().getString(R.string.twitter_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void share(String path){
            Uri imageUri = FileProvider.getUriForFile(ImageActivity.this, ImageActivity.this.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);


            final String final_text = getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(type);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(Intent.createChooser(shareIntent,getResources().getString(R.string.share_via)+ " " + getResources().getString(R.string.app_name) ));
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(ImageActivity.this.getApplicationContext(),getResources().getString(R.string.app_not_installed) , Toast.LENGTH_SHORT, true).show();
            }
        }
    }
    public void setDownloading(Boolean downloading){
        if (downloading){
            relative_layout_progress_activity_image.setVisibility(View.VISIBLE);
        }else{
            relative_layout_progress_activity_image.setVisibility(View.GONE);
        }
        this.downloading = downloading;
    }
    public void setProgressValue(int progress){
        this.progress_bar_activity_image.setProgress(progress);
        this.text_view_progress_activity_image.setText(getResources().getString(R.string.downloading)+" "+progress+" %");
    }
    public void addShare(Integer id){
        final PrefManager prefManager = new PrefManager(this);
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(id+"_share").equals("true")) {
            prefManager.setString(id+"_share", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<Integer> call = service.addShare(id,id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {

                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        }
    }
    public void addView(){
        final PrefManager prefManager = new PrefManager(this);
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(id+"_view").equals("true")) {
            prefManager.setString(id+"_view", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            String ishash="";
            try {
                ishash   = VideoActivity.sha1((id+55463938)+"");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            };
            Call<Integer> call = service.addView(ishash,id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                }
            });
        }
    }

    private void loadMore() {
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<Status>> call = service.ImageByRandom(language);
        call.enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()!=0){
                        statusList.clear();
                        for (int i=0;i<response.body().size();i++){
                            if(isNotBlocked(response.body().get(i))) {
                                statusList.add(response.body().get(i));
                            }
                        }
                        statusAdapter.notifyDataSetChanged();
                        recycler_view_status_load_more.setNestedScrollingEnabled(false);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {

            }
        });
    }


    private void Report(MenuItem item, Activity activity) {
        final PrefManager prefManager = new PrefManager(activity);

        switch (item.getItemId()){
            case R.id.report:
                Intent intent = new Intent(activity, SupportActivity.class);
                intent.putExtra("message","Hi Admin, Please check this status i think should be removed status id : "+id );
                activity.startActivity(intent);
                break;
            case R.id.report_user:
                Intent intent_user = new Intent(activity, SupportActivity.class);
                intent_user.putExtra("message","Hi Admin, Please check this user i think should be removed user id : "+userid );
                startActivity(intent_user);
                break;
            case R.id.block_user:


                prefManager.setString("user_reported_"+userid,"TRUE");
                Toasty.warning(activity,"User : "+ userid+" has been blocked !").show();

                break;

        };


    }
    private void addReport(int id, String message,Dialog rateDialog) {
        ProgressDialog register_progress= ProgressDialog.show(this,null,getString(R.string.progress_login));
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.addReport(id,message);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    Toasty.success(getApplicationContext(), getResources().getString(R.string.message_sended), Toast.LENGTH_SHORT).show();
                }else{
                    Toasty.error(getApplicationContext(), getString(R.string.no_connexion), Toast.LENGTH_SHORT).show();
                }
                register_progress.dismiss();
                rateDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                register_progress.dismiss();
                rateDialog.dismiss();
                Toasty.error(getApplicationContext(), getString(R.string.no_connexion), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void selectOperation(Integer code){
        if(code!=null){
        switch (code){
            case 90001:
                DownloadFileBySocial(WHATSAPP_ID);
                break;
            case 90002:
                DownloadFileBySocial(MESSENGER_ID);
                break;
            case 90003:
                DownloadFileBySocial(FACEBOOK_ID);
                break;
            case 90004:
                DownloadFileBySocial(INSTAGRAM_ID);
                break;
            case 90005:
                DownloadFileBySocial(TWITTER_ID);
                break;
            case 90006:
                DownloadFileBySocial(SHARE_ID);
                break;
            case 90007:
                DownloadFileBySocial(DOWNLOAD_ID);
                break;
            case 90008:
                DownloadFileBySocial(HIKE_ID);
                break;
            case 90009:
                DownloadFileBySocial(SNAPSHAT_ID);
                break;
        }
        }
    }

    private void DownloadFileBySocial(String code) {
        if (!downloading) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

                new ImageActivity.DownloadFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,urlToDownload, title,extension,0,code);
            else
                new ImageActivity.DownloadFileFromURL().execute(AsyncTask.THREAD_POOL_EXECUTOR,urlToDownload, title,extension,0,code);
        }
    }
    private void requestMaxInterstitial() {
        if (maxInterstitialAd==null){
            PrefManager prefManager= new PrefManager(getApplicationContext());




            maxInterstitialAd = new MaxInterstitialAd(prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"),ImageActivity.this);

            maxInterstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                }
                @Override
                public void onAdLoaded(MaxAd ad) {


                    Log.v("mInterstitial","onInterstitialLoaded2222");
                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    Log.v("mInterstitial",error.toString());
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    selectOperation(code_selected);
                    maxInterstitialAd = new MaxInterstitialAd(prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"),ImageActivity.this);
                    requestMaxInterstitial();
                }
            });

            maxInterstitialAd.loadAd();


        }


    }
    private void requestAppLovinInterstitial() {

        if (applovinInterstitialAdBlock==null){

            applovinInterstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( this ), this );


            applovinInterstitialAd.setAdLoadListener(new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd ad) {
                    applovinInterstitialAdBlock = ad;
                }

                @Override
                public void failedToReceiveAd(int errorCode) {

                }
            });
            applovinInterstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
                @Override
                public void adDisplayed(AppLovinAd ad) {

                }

                @Override
                public void adHidden(AppLovinAd ad) {
                    selectOperation(code_selected);
                    requestISInterstitial();
                }
            });
            applovinInterstitialAd.setAdClickListener(ad -> {

            });
            applovinInterstitialAd.setAdVideoPlaybackListener(new AppLovinAdVideoPlaybackListener() {
                @Override
                public void videoPlaybackBegan(AppLovinAd ad) {

                }

                @Override
                public void videoPlaybackEnded(AppLovinAd ad, double percentViewed, boolean fullyWatched) {

                }
            });
            AppLovinSdk.getInstance( getApplicationContext() ).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
                @Override
                public void adReceived(AppLovinAd ad) {
                    applovinInterstitialAdBlock = ad;
                }

                @Override
                public void failedToReceiveAd(int errorCode) {

                }
            });


        }


    }
    public void Operation(Integer code){
        code_selected = code;
        PrefManager prefManager= new PrefManager(this);
        if(checkSUBSCRIBED()) {
            selectOperation(code);
        }else{
            if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("ADMOB")) {
                requestAdmobInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                    if (admobInterstitialAd != null) {
                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                        admobInterstitialAd.show(ImageActivity.this);
                    }else{
                        selectOperation(code);
                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);

                }
            }else  if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("MAX")) {
                requestMaxInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS") <= prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                    if (maxInterstitialAd != null) {
                        if (maxInterstitialAd.isReady()) {
                            prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS", 0);
                            maxInterstitialAd.showAd();

                        } else {
                            selectOperation(code);
                        }
                    } else {
                        selectOperation(code);
                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                }
            }else  if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("APPLOVIN")) {
                requestAppLovinInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS") <= prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                    if (applovinInterstitialAd != null) {
                        if (applovinInterstitialAdBlock!=null) {
                            prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS", 0);
                            applovinInterstitialAd.showAndRender(applovinInterstitialAdBlock);

                        } else {
                            selectOperation(code);
                        }
                    } else {
                        selectOperation(code);
                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                }
            }else  if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("IS")) {
                requestISInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS") <= prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")) {
                    if(IronSource.isInterstitialReady()){
                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS", 0);
                        IronSource.showInterstitial();
                        selectOperation(code);

                    }else{
                        selectOperation(code);

                    }
                }else{
                    selectOperation(code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                }

            }else{
                selectOperation(code);
            }
        }
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
