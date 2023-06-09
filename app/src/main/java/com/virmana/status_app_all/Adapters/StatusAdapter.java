package com.virmana.status_app_all.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.mediation.ApplovinAdapter;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAdListener;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.google.ads.mediation.facebook.FacebookMediationAdapter;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.like.LikeButton;
import com.applovin.mediation.MaxAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxAdListener;;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;;

import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.api.apiClient;
import com.virmana.status_app_all.api.apiRest;
import com.virmana.status_app_all.model.ApiResponse;
import com.virmana.status_app_all.model.Category;
import com.virmana.status_app_all.model.Slide;
import com.virmana.status_app_all.model.Status;
import com.virmana.status_app_all.model.User;
import com.virmana.status_app_all.Provider.DownloadStorage;
import com.virmana.status_app_all.Provider.FavoritesStorage;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.ui.Activities.AllFullScreenCategoryActivity;
import com.virmana.status_app_all.ui.Activities.AllFullScreenFollowActivity;
import com.virmana.status_app_all.ui.Activities.AllFullScreenSearchActivity;
import com.virmana.status_app_all.ui.Activities.AllFullScreenUserActivity;
import com.virmana.status_app_all.ui.Activities.AllPortaitVideosActivity;
import com.virmana.status_app_all.ui.Activities.GifActivity;
import com.virmana.status_app_all.ui.Activities.ImageActivity;
import com.virmana.status_app_all.ui.Activities.PlayerActivity;
import com.virmana.status_app_all.ui.Activities.QuoteActivity;
import com.virmana.status_app_all.ui.Activities.SupportActivity;
import com.virmana.status_app_all.ui.Activities.UserActivity;
import com.virmana.status_app_all.ui.Activities.VideoActivity;
import com.squareup.picasso.Picasso;
import com.virmana.status_app_all.ui.Activities.YoutubeActivity;
import com.virmana.status_app_all.ui.view.ClickableViewPager;
import com.whygraphics.gifview.gif.GIFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

/**
 * Created by Tamim on 08/10/2017.
 */


public class StatusAdapter extends RecyclerView.Adapter {
    private  ProgressDialog register_progress;

    private  Integer id ;
    private  String type = "home";
    private  String name = "";
    private  String image = "";
    private  Boolean downloads = false;
    private  Boolean favorites = false;
    private  PeekAndPop peekAndPop;
    private List<Status> fullScreenVdeos =new ArrayList<>();
    private List<Status> statusList =new ArrayList<>();
    private List<Category> categoryList =new ArrayList<>();
    private Activity activity;
    private static final String WHATSAPP_ID="com.whatsapp";
    private static final String FACEBOOK_ID="com.facebook.katana";
    private static final String MESSENGER_ID="com.facebook.orca";
    private static final String INSTAGRAM_ID="com.instagram.android";
    private static final String SHARE_ID="com.android.all";

    
    private InterstitialAd admobInterstitialAd;
    private AppLovinInterstitialAdDialog applovinInterstitialAd;
    private AppLovinAd applovinInterstitialAdBlock;
    private MaxInterstitialAd maxInterstitialAd;


    private LinearLayoutManager linearLayoutManager;

    private PlayerView simpleExoPlayerView;
    private ExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    private ImageView ivHideControllerButton;
    private Timeline.Window window;
    private SubscribeAdapter subscribeAdapter;
    private List<User> userList;
    private List<Slide> slideList= new ArrayList<>();
    private SlideAdapter slide_adapter;
    private SearchUserAdapter searchUserAdapter;
    private LinearLayoutManager linearLayoutManagerSearch;

    private Integer position_selected;
    private Integer code_selected;
    public StatusAdapter(final List<Status> statusList, List<Category> categoryList, final Activity activity, final PeekAndPop peekAndPop) {
        this.statusList = statusList;
        this.categoryList = categoryList;
        this.activity = activity;
        this.peekAndPop=peekAndPop;

        peekAndPop.addHoldAndReleaseView(R.id.like_button_fav_image_dialog);
        peekAndPop.addHoldAndReleaseView(R.id.like_button_messenger_image_dialog);
        peekAndPop.addHoldAndReleaseView(R.id.like_button_facebook_image_dialog);
        peekAndPop.addHoldAndReleaseView(R.id.like_button_instagram_image_dialog);
        peekAndPop.addHoldAndReleaseView(R.id.like_button_share_image_dialog);
        peekAndPop.addHoldAndReleaseView(R.id.like_button_whatsapp_image_dialog);
        peekAndPop.addHoldAndReleaseView(R.id.like_button_copy_image_dialog);

        peekAndPop.setOnHoldAndReleaseListener(new PeekAndPop.OnHoldAndReleaseListener() {
            @Override
            public void onHold(View view, int i) {
                Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(40);
            }
            @Override
            public void onLeave(View view, int i) {
            }

            @Override
            public void onRelease(View view,final int position) {
                final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();

                switch (view.getId()){
                    case R.id.like_button_copy_image_dialog:
                        Operation(position,1);
                        break;
                    case R.id.like_button_facebook_image_dialog:
                        Operation(position,2);
                        break;
                    case R.id.like_button_messenger_image_dialog:
                        Operation(position,3);
                        break;
                    case R.id.like_button_whatsapp_image_dialog:
                        Operation(position,4);
                        break;
                    case R.id.like_button_instagram_image_dialog:
                        Operation(position,5);
                        break;
                    case R.id.like_button_share_image_dialog:
                        Operation(position,6);
                        break;
                    case R.id.like_button_fav_image_dialog:
                        final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());

                        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
                        Boolean exist = false;
                        if (favorites_list==null){
                            favorites_list= new ArrayList<>();
                        }
                        for (int i = 0; i <favorites_list.size() ; i++) {
                            if (favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Status> audios= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                audios.add(favorites_list.get(i));
                            }
                            audios.add(statusList.get(position));
                            storageFavorites.storeImage(audios);
                        }else{
                            ArrayList<Status> new_favorites= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                if (!favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                    new_favorites.add(favorites_list.get(i));

                                }
                            }
                            if (favorites==true){
                                statusList.remove(position);
                                notifyDataSetChanged();
                                //holder.ripple_view_wallpaper_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeImage(new_favorites);

                        }
                        notifyDataSetChanged();
                        break;

                }
            }


        });
        peekAndPop.setOnGeneralActionListener(new PeekAndPop.OnGeneralActionListener() {
            @Override
            public void onPeek(View view, int position) {

                LikeButton like_button_fav_image_dialog =(LikeButton) peekAndPop.getPeekView().findViewById(R.id.like_button_fav_image_dialog);

                final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());
                List<Status> statuses = storageFavorites.loadImagesFavorites();
                Boolean exist = false;
                if (statuses ==null){
                    statuses = new ArrayList<>();
                }
                for (int i = 0; i < statuses.size() ; i++) {
                    if (statuses.get(i).getId().equals(statusList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist == false) {
                    like_button_fav_image_dialog.setLiked(false);
                } else {
                    like_button_fav_image_dialog.setLiked(true);
                }


                LikeButton like_button_copy_image_dialog =(LikeButton) peekAndPop.getPeekView().findViewById(R.id.like_button_copy_image_dialog);
                LikeButton like_button_facebook_image_dialog =(LikeButton) peekAndPop.getPeekView().findViewById(R.id.like_button_facebook_image_dialog);
                LikeButton like_button_instagram_image_dialog =(LikeButton) peekAndPop.getPeekView().findViewById(R.id.like_button_instagram_image_dialog);
                RelativeLayout relative_layout_quote =(RelativeLayout) peekAndPop.getPeekView().findViewById(R.id.relative_layout_quote);
                RelativeLayout relative_layout_media =(RelativeLayout) peekAndPop.getPeekView().findViewById(R.id.relative_layout_media);
                RelativeLayout relative_layout_dialog_title =(RelativeLayout) peekAndPop.getPeekView().findViewById(R.id.relative_layout_dialog_title);
                ImageView circle_image_view_dialog_image =(ImageView) peekAndPop.getPeekView().findViewById(R.id.circle_image_view_dialog_image);
                GIFView gif_view_dialog_view_gif =(GIFView) peekAndPop.getPeekView().findViewById(R.id.gif_view_dialog_view_gif);
                final   ImageView image_view_dialog_view_image =(ImageView) peekAndPop.getPeekView().findViewById(R.id.image_view_dialog_view_image);
                simpleExoPlayerView = (PlayerView) peekAndPop.getPeekView().findViewById(R.id.video_view_dialog_image);
                TextView text_view_view_dialog_user=(TextView) peekAndPop.getPeekView().findViewById(R.id.text_view_view_dialog_user);
                TextView text_view_view_dialog_title=(TextView) peekAndPop.getPeekView().findViewById(R.id.text_view_view_dialog_title);
                TextView text_view_quote_status=(TextView) peekAndPop.getPeekView().findViewById(R.id.text_view_quote_status);
                TextView text_view_downloads=(TextView) peekAndPop.getPeekView().findViewById(R.id.text_view_downloads);

                Picasso.get().load(statusList.get(position).getUserimage()).error(R.drawable.profile).placeholder(R.drawable.profile).into(circle_image_view_dialog_image);
                text_view_view_dialog_user.setText(statusList.get(position).getUser());
                text_view_view_dialog_title.setText(statusList.get(position).getTitle());
                text_view_downloads.setText(format(statusList.get(position).getDownloads()));

                if (statusList.get(position).getKind().equals("video") || statusList.get(position).getKind().equals("fullscreen")){
                    like_button_copy_image_dialog.setVisibility(View.GONE);
                    like_button_facebook_image_dialog.setVisibility(View.VISIBLE);
                    like_button_instagram_image_dialog.setVisibility(View.VISIBLE);
                    relative_layout_quote.setVisibility(View.GONE);
                    relative_layout_media.setVisibility(View.VISIBLE);
                    relative_layout_dialog_title.setVisibility(View.VISIBLE);
                    simpleExoPlayerView.setVisibility(View.VISIBLE);
                    image_view_dialog_view_image.setVisibility(View.GONE);
                    gif_view_dialog_view_gif.setVisibility(View.GONE);
                    shouldAutoPlay = true;
                    bandwidthMeter  = new DefaultBandwidthMeter.Builder(activity).build();
                    mediaDataSourceFactory = new DefaultDataSourceFactory(activity.getApplicationContext(), Util.getUserAgent(activity.getApplication(), "mediaPlayerSample"));
                    window = new Timeline.Window();
                    ivHideControllerButton = (ImageView) peekAndPop.getPeekView().findViewById(R.id.exo_controller_full);
                    initializePlayer(position);
                }else if (statusList.get(position).getKind().equals("gif")){
                    like_button_facebook_image_dialog.setVisibility(View.VISIBLE);
                    like_button_instagram_image_dialog.setVisibility(View.VISIBLE);
                    like_button_copy_image_dialog.setVisibility(View.GONE);
                    relative_layout_quote.setVisibility(View.GONE);
                    relative_layout_media.setVisibility(View.VISIBLE);
                    relative_layout_dialog_title.setVisibility(View.VISIBLE);
                    gif_view_dialog_view_gif.setGifResource("url:"+statusList.get(position).getOriginal());
                    simpleExoPlayerView.setVisibility(View.GONE);
                    Picasso.get().load(statusList.get(position).getThumbnail()).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(image_view_dialog_view_image);

                    image_view_dialog_view_image.setVisibility(View.GONE);
                    gif_view_dialog_view_gif.setVisibility(View.VISIBLE);
                    gif_view_dialog_view_gif.setOnSettingGifListener(new GIFView.OnSettingGifListener() {
                        @Override
                        public void onSuccess(GIFView view, Exception e) {
                            image_view_dialog_view_image.setVisibility(View.GONE);
                        }
                        @Override
                        public void onFailure(GIFView view, Exception e) {
                        }
                    });
                }
                else if (statusList.get(position).getKind().equals("image")) {
                    like_button_facebook_image_dialog.setVisibility(View.VISIBLE);
                    like_button_instagram_image_dialog.setVisibility(View.VISIBLE);
                    like_button_copy_image_dialog.setVisibility(View.GONE);
                    relative_layout_quote.setVisibility(View.GONE);
                    relative_layout_media.setVisibility(View.VISIBLE);
                    relative_layout_dialog_title.setVisibility(View.VISIBLE);
                    gif_view_dialog_view_gif.setVisibility(View.GONE);
                    simpleExoPlayerView.setVisibility(View.GONE);
                    image_view_dialog_view_image.setVisibility(View.VISIBLE);
                    Picasso.get().load(statusList.get(position).getOriginal()).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(image_view_dialog_view_image);
                }else if (statusList.get(position).getKind().equals("quote")){
                    like_button_copy_image_dialog.setVisibility(View.VISIBLE);
                    like_button_facebook_image_dialog.setVisibility(View.GONE);
                    like_button_instagram_image_dialog.setVisibility(View.GONE);
                    relative_layout_dialog_title.setVisibility(View.GONE);
                    relative_layout_quote.setVisibility(View.VISIBLE);
                    relative_layout_media.setVisibility(View.GONE);

                    String text = null;
                    try {
                        byte[] data = Base64.decode(statusList.get(position).getTitle(), Base64.DEFAULT);
                        text = new String(data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    text_view_quote_status.setText(text);
                    relative_layout_quote.setBackgroundResource(R.drawable.bg_quote_background);
                    Typeface tf = Typeface.createFromAsset(activity.getAssets(), "font_"+statusList.get(position).getFont()+".ttf");
                    text_view_quote_status.setTypeface(tf);
                    GradientDrawable drawable = (GradientDrawable)  relative_layout_quote.getBackground();
                    drawable.setColor(Color.parseColor("#"+statusList.get(position).getColor()));
                }
            }

            @Override
            public void onPop(View view, int position) {
                try {
                    releasePlayer();
                    bandwidthMeter=null;
                    mediaDataSourceFactory=null;
                    window=null;
                }catch (Exception e){

                }
                addView(position);

            }
        });
    }
    private void initializePlayer(Integer position) {

        simpleExoPlayerView.requestFocus();

        ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();



        trackSelector = new DefaultTrackSelector(activity.getApplicationContext());

     //   player = MediaPlayerFactory.newSimpleInstance(activity, trackSelector);

        player = new ExoPlayer.Builder(activity).setTrackSelector(trackSelector).build();


        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);
        simpleExoPlayerView.setControllerHideOnTouch(false);
        simpleExoPlayerView.setUseController(false);
        simpleExoPlayerView.setControllerAutoShow(false);
/*        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);*/

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
      //  MediaSource mediaSource = new ExtractorMediaSource(),mediaDataSourceFactory, extractorsFactory, null, null);

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory,extractorsFactory).createMediaSource(MediaItem.fromUri(Uri.parse(statusList.get(position).getOriginal())));
        if (downloads){
//            Log.v("this is path",statusList.get(position).getPath());
            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(statusList.get(position).getLocal()));
          //  mediaSource = new ExtractorMediaSource(imageUri, mediaDataSourceFactory, extractorsFactory, null, null);
            mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory,extractorsFactory).createMediaSource(MediaItem.fromUri(imageUri));

        }


        player.prepare(mediaSource);
        simpleExoPlayerView.hideController();

        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleExoPlayerView.hideController();
            }
        });
    }
    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }
    public StatusAdapter(final List<Status> statusList, List<Category> categoryList, final Activity activity, final PeekAndPop peekAndPop, Boolean favorites_) {
        this(statusList,categoryList,activity,peekAndPop);
        this.favorites=favorites_;
    }
    public StatusAdapter(final List<Status> statusList, List<Category> categoryList, final Activity activity, final PeekAndPop peekAndPop, List<Slide> slideList_,List<Status> _fullScreenVdeos,Integer _id,String _name,String _image,String _type) {
        this(statusList,categoryList,activity,peekAndPop);
        this.slideList=slideList_;
        this.fullScreenVdeos=_fullScreenVdeos;
        this.id=_id;
        this.name=_name;
        this.image=_image;
        this.type=_type;
    }
    public StatusAdapter(final List<Status> statusList, List<Category> categoryList, final Activity activity, final PeekAndPop peekAndPop, List<Slide> slideList_,List<Status> _fullScreenVdeos,Integer _id,String _name,String _image,String _type,List<User> userList_) {
        this(statusList,categoryList,activity,peekAndPop);
        this.userList=userList_;
        this.slideList=slideList_;
        this.fullScreenVdeos=_fullScreenVdeos;
        this.id=_id;
        this.name=_name;
        this.image=_image;
        this.type=_type;
    }
    public StatusAdapter(final List<Status> statusList, List<Category> categoryList, final Activity activity, final PeekAndPop peekAndPop, Boolean favorites_, Boolean downloads_) {
        this(statusList,categoryList,activity,peekAndPop);
        this.favorites=favorites_;
        this.downloads=downloads_;
    }
    public StatusAdapter(final List<Status> statusList, List<Category> categoryList, final Activity activity, final PeekAndPop peekAndPop, Boolean favorites_, Boolean downloads_, List<User> userList_,List<Status> _fullScreenVdeos,String _name,String _type) {
        this(statusList,categoryList,activity,peekAndPop);
        this.favorites=favorites_;
        this.downloads=downloads_;
        this.userList=userList_;
        this.fullScreenVdeos=_fullScreenVdeos;
        this.type=_type;
        this.name=_name;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case 0: {
                View v0 = inflater.inflate(R.layout.item_empty, parent, false);
                viewHolder = new StatusAdapter.EmptyHolder(v0);
                break;
            }
            case 1: {
                View v1 = inflater.inflate(R.layout.item_categories, parent, false);
                viewHolder = new StatusAdapter.CategoriesHolder(v1);
                break;
            }
            case 2: {
                View v2 = inflater.inflate(R.layout.item_video, parent, false);
                viewHolder = new StatusAdapter.VideoHolder(v2);
                break;
            }
            case 3: {
                View v3 = inflater.inflate(R.layout.item_image, parent, false);
                viewHolder = new StatusAdapter.ImageHolder(v3);
                break;
            }
            case 4: {
                View v4 = inflater.inflate(R.layout.item_gif, parent, false);
                viewHolder = new StatusAdapter.GifHolder(v4);
                break;
            }
            case 5: {
                View v5 = inflater.inflate(R.layout.item_quote, parent, false);
                viewHolder = new StatusAdapter.QuoteHolder(v5);
                break;
            }

            case 7: {
                View v7 = inflater.inflate(R.layout.item_subscriptions, parent, false);
                viewHolder = new SubscriptionHolder(v7);
                break;
            }
            case 8: {
                View v8 = inflater.inflate(R.layout.item_slide, parent, false);
                viewHolder = new SlideHolder(v8);
                break;
            }
            case 9: {
                View v9 = inflater.inflate(R.layout.item_users_search, parent, false);
                viewHolder = new SearchUserListHolder(v9);
                break;
            }
            case 10: {
                View v10 = inflater.inflate(R.layout.item_portrait_list, parent, false);
                viewHolder = new PortraitVideoListHolder(v10);
                break;
            }
            case 11: {
                View v11 = inflater.inflate(R.layout.item_admob_native_ads, parent, false);
                viewHolder = new AdmobNativeHolder(v11);
                break;
            }
            case 12: {
                View v12 = inflater.inflate(R.layout.item_youtube, parent, false);
                viewHolder = new YoutubeHolder(v12);
                break;
            }
            case 13: {
                View v13 = inflater.inflate(R.layout.item_max_native_ads, parent, false);
                viewHolder = new MaxNativeHolder(v13);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder_parent,  final int xposition) {
        int position= holder_parent.getAdapterPosition();
        switch (getItemViewType(position)) {
            case 12: {
                final StatusAdapter.YoutubeHolder holder = (StatusAdapter.YoutubeHolder) holder_parent;
                holder.image_view_menu_item.setOnClickListener(v->{
                    PopupMenu popup = new PopupMenu(activity, v);
                    popup.setOnMenuItemClickListener(item -> {
                        Report(statusList.get(position),item);
                        return true;
                    });
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                });
                if(statusList.get(position).getDescription() != null){
                    if(statusList.get(position).getDescription() != ""){
                        holder.text_view_item_youtube_description.setVisibility(View.VISIBLE);
                        holder.text_view_item_youtube_description.setText(statusList.get(position).getDescription());
                    }
                }
                Picasso.get().load(statusList.get(position).getThumbnail()).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(holder.image_view_thumbnail_item_youtube);
                Picasso.get().load(statusList.get(position).getUserimage()).error(R.drawable.profile).placeholder(R.drawable.profile).into(holder.circle_image_view_item_youtube_user);
                holder.text_view_downloads_item_youtube.setText(format(statusList.get(position).getDownloads()) );
                holder.text_view_views_item_youtube.setText(format(statusList.get(position).getViews()));
                holder.text_view_created_item_youtube.setText(statusList.get(position).getCreated());
                holder.text_view_item_youtube_name_user.setText(statusList.get(position).getUser());
                holder.text_view_item_youtube_title.setText(statusList.get(position).getTitle());

                if (statusList.get(position).getReview()) {
                    holder.relative_layout_item_post_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.relative_layout_item_post_delete.setVisibility(View.GONE);
                }
                holder.relative_layout_item_post_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(activity)
                                .setTitle(R.string.confirm_delete)
                                .setMessage(R.string.delete_statsu)
                                .setIcon(R.drawable.ic_delete_black)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        register_progress= ProgressDialog.show(activity, null,activity.getResources().getString(R.string.operation_progress), true);

                                        final PrefManager prefManager = new PrefManager(activity);
                                        Integer id_user = 0;
                                        String key_user = "";
                                        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                                            id_user = Integer.parseInt(prefManager.getString("ID_USER"));
                                            key_user = prefManager.getString("TOKEN_USER");
                                        }
                                        Retrofit retrofit = apiClient.getClient();
                                        apiRest service = retrofit.create(apiRest.class);
                                        Call<ApiResponse> call = service.deletePost(statusList.get(position).getId(), id_user, key_user);
                                        call.enqueue(new Callback<ApiResponse>() {
                                            @Override
                                            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                                                register_progress.dismiss();
                                                if (response.isSuccessful()){
                                                    Toasty.success(activity,activity.getResources().getString(R.string.post_delete_success),Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(activity,UserActivity.class);
                                                    intent.putExtra("id", statusList.get(position).getUserid());
                                                    intent.putExtra("name", statusList.get(position).getUser());
                                                    intent.putExtra("image", statusList.get(position).getUserimage());
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                                    activity.finish();
                                                }else{
                                                    Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                                Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                register_progress.dismiss();
                                            }
                                        });


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }
                });

                if (statusList.get(position).getReview())
                    holder.relative_layout_item_youtube_review.setVisibility(View.VISIBLE);
                else
                    holder.relative_layout_item_youtube_review.setVisibility(View.GONE);

                final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());
                List<Status> statuses = storageFavorites.loadImagesFavorites();
                Boolean exist = false;
                if (statuses ==null){
                    statuses = new ArrayList<>();
                }
                for (int i = 0; i < statuses.size() ; i++) {
                    if (statuses.get(i).getId().equals(statusList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist) {
                    holder.image_view_fav_item_youtube.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                }else{
                    holder.image_view_fav_item_youtube.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                }

                holder.image_view_thumbnail_item_youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity,YoutubeActivity.class);

                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                holder.relative_layout_item_youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity,YoutubeActivity.class);

                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });


                holder.image_view_fav_item_youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
                        Boolean exist = false;
                        if (favorites_list==null){
                            favorites_list= new ArrayList<>();
                        }
                        for (int i = 0; i <favorites_list.size() ; i++) {
                            if (favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Status> audios= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                audios.add(favorites_list.get(i));
                            }
                            audios.add(statusList.get(position));
                            storageFavorites.storeImage(audios);
                            holder.image_view_fav_item_youtube.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                        }else{
                            ArrayList<Status> new_favorites= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                if (!favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                    new_favorites.add(favorites_list.get(i));

                                }
                            }
                            if (favorites==true){
                                Log.v("DOWNLOADED","favorites==true");
                                statusList.remove(position);
                                notifyDataSetChanged();
                                //holder.ripple_view_wallpaper_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeImage(new_favorites);
                            holder.image_view_fav_item_youtube.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                });

                holder.image_view_share_item_youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,7);
                    }
                });

                holder.image_view_whatsapp_item_youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,8);
                    }

                });

                break;
            }
            case 0: {

                break;
            }
            case 1: {
                final StatusAdapter.CategoriesHolder holder = (StatusAdapter.CategoriesHolder) holder_parent;
                holder.categoryVideoAdapter.notifyDataSetChanged();

            }
            break;
            case 2: {

                final StatusAdapter.VideoHolder holder = (StatusAdapter.VideoHolder) holder_parent;
                peekAndPop.addLongClickView(holder.image_view_thumbnail_item_video, position);

                Picasso.get().load(statusList.get(position).getThumbnail()).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(holder.image_view_thumbnail_item_video);
                Picasso.get().load(statusList.get(position).getUserimage()).error(R.drawable.profile).placeholder(R.drawable.profile).into(holder.circle_image_view_item_video_user);
                holder.image_view_menu_item.setOnClickListener(v->{
                    PopupMenu popup = new PopupMenu(activity, v);
                    popup.setOnMenuItemClickListener(item -> {
                        Report(statusList.get(position),item);
                        return true;
                    });
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                });
                holder.text_view_downloads_item_video.setText(format(statusList.get(position).getDownloads()) );
                holder.text_view_views_item_video.setText(format(statusList.get(position).getViews()));
                holder.text_view_created_item_video.setText(statusList.get(position).getCreated());

                holder.text_view_item_video_name_user.setText(statusList.get(position).getUser());
                holder.text_view_item_video_title.setText(statusList.get(position).getTitle());
                if(statusList.get(position).getDescription() != null){
                    if(statusList.get(position).getDescription() != ""){
                        holder.text_view_item_video_description.setVisibility(View.VISIBLE);
                        holder.text_view_item_video_description.setText(statusList.get(position).getDescription());
                    }
                }
                if (statusList.get(position).getReview())
                    holder.relative_layout_item_video_review.setVisibility(View.VISIBLE);
                else
                    holder.relative_layout_item_video_review.setVisibility(View.GONE);
                if (downloads){
                    holder.image_view_delete_item_video.setVisibility(View.VISIBLE);
                    holder.image_view_fav_item_video.setVisibility(View.GONE);
                    holder.relative_layout_fav_item_video.setVisibility(View.GONE);

                }else{
                    holder.image_view_delete_item_video.setVisibility(View.GONE);
                    holder.image_view_fav_item_video.setVisibility(View.VISIBLE);
                    holder.relative_layout_fav_item_video.setVisibility(View.VISIBLE);
                }
                if (!statusList.get(position).isDownloading()){
                    holder.relative_layout_progress_item_video.setVisibility(View.GONE);
                }else{
                    holder.relative_layout_progress_item_video.setVisibility(View.VISIBLE);
                    holder.progress_bar_item_video.setProgress(statusList.get(position).getProgress());
                    holder.text_view_progress_item_video.setText("Downloading : "+ statusList.get(position).getProgress()+" %");
                }


                if (statusList.get(position).getReview()) {
                    holder.relative_layout_item_post_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.relative_layout_item_post_delete.setVisibility(View.GONE);
                }
                holder.relative_layout_item_post_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(activity)
                                .setTitle(R.string.confirm_delete)
                                .setMessage(R.string.delete_statsu)
                                .setIcon(R.drawable.ic_delete_black)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        register_progress= ProgressDialog.show(activity, null,activity.getResources().getString(R.string.operation_progress), true);

                                        final PrefManager prefManager = new PrefManager(activity);
                                        Integer id_user = 0;
                                        String key_user = "";
                                        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                                            id_user = Integer.parseInt(prefManager.getString("ID_USER"));
                                            key_user = prefManager.getString("TOKEN_USER");
                                        }
                                        Retrofit retrofit = apiClient.getClient();
                                        apiRest service = retrofit.create(apiRest.class);
                                        Call<ApiResponse> call = service.deletePost(statusList.get(position).getId(), id_user, key_user);
                                        call.enqueue(new Callback<ApiResponse>() {
                                            @Override
                                            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                                                register_progress.dismiss();
                                                if (response.isSuccessful()){
                                                    Toasty.success(activity,activity.getResources().getString(R.string.post_delete_success),Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(activity,UserActivity.class);
                                                    intent.putExtra("id", statusList.get(position).getUserid());
                                                    intent.putExtra("name", statusList.get(position).getUser());
                                                    intent.putExtra("image", statusList.get(position).getUserimage());
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                                    activity.finish();
                                                }else{
                                                    Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                                Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                register_progress.dismiss();
                                            }
                                        });


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }
                });


                final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());
                List<Status> statuses = storageFavorites.loadImagesFavorites();
                Boolean exist = false;
                if (statuses ==null){
                    statuses = new ArrayList<>();
                }
                for (int i = 0; i < statuses.size() ; i++) {
                    if (statuses.get(i).getId().equals(statusList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist) {
                    holder.image_view_fav_item_video.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                }else{
                    holder.image_view_fav_item_video.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
                holder.image_view_thumbnail_item_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity,VideoActivity.class);
                        if (statusList.get(position).getKind().equals("video")){
                            intent  = new Intent(activity,VideoActivity.class);
                        }else if (statusList.get(position).getKind().equals("fullscreen")){
                            intent  = new Intent(activity,PlayerActivity.class);
                        }
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });

                holder.relative_layout_item_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity,VideoActivity.class);
                        if (statusList.get(position).getKind().equals("video")){
                            intent  = new Intent(activity,VideoActivity.class);
                        }else if (statusList.get(position).getKind().equals("fullscreen")){
                            intent  = new Intent(activity,PlayerActivity.class);
                        }
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                holder.image_view_fav_item_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
                        Boolean exist = false;
                        if (favorites_list==null){
                            favorites_list= new ArrayList<>();
                        }
                        for (int i = 0; i <favorites_list.size() ; i++) {
                            if (favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Status> audios= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                audios.add(favorites_list.get(i));
                            }
                            audios.add(statusList.get(position));
                            storageFavorites.storeImage(audios);
                            holder.image_view_fav_item_video.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                        }else{
                            ArrayList<Status> new_favorites= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                if (!favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                    new_favorites.add(favorites_list.get(i));

                                }
                            }
                            if (favorites==true){
                                Log.v("DOWNLOADED","favorites==true");
                                statusList.remove(position);
                                notifyDataSetChanged();
                                //holder.ripple_view_wallpaper_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeImage(new_favorites);
                            holder.image_view_fav_item_video.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                });
                final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
                holder.image_view_share_item_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,6);

                    }
                });

                holder.image_view_whatsapp_item_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,4);

                    }
                });
                holder.image_view_delete_item_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DownloadStorage downloadStorage= new DownloadStorage(activity.getApplicationContext());

                        List<Status> downloadedListStatus = downloadStorage.loadImagesFavorites();
                        Boolean exist = false;
                        if (downloadedListStatus ==null){
                            downloadedListStatus = new ArrayList<>();
                        }
                        for (int i = 0; i < downloadedListStatus.size() ; i++) {
                            if (downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == true) {
                            String pathlocal =  statusList.get(position).getLocal();
                            ArrayList<Status> new_dwonloads= new ArrayList<Status>();
                            for (int i = 0; i < downloadedListStatus.size(); i++) {
                                if (!downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                    new_dwonloads.add(downloadedListStatus.get(i));

                                }
                            }
                            if (downloads==true){
                                statusList.remove(position);
                                notifyDataSetChanged();
                            }
                            downloadStorage.storeImage(new_dwonloads);
                            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(pathlocal));
                            File file = new File(pathlocal);
                            if (file.exists()) {
                                file.delete();
                                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                            }
                        }
                    }
                });
            }
            break;
            case 3: {
                final StatusAdapter.ImageHolder holder = (StatusAdapter.ImageHolder) holder_parent;
                peekAndPop.addLongClickView(holder.image_view_thumbnail_item_image, position);
                holder.image_view_menu_item.setOnClickListener(v->{
                    PopupMenu popup = new PopupMenu(activity, v);
                    popup.setOnMenuItemClickListener(item -> {
                        Report(statusList.get(position),item);
                        return true;
                    });
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                });
                if(statusList.get(position).getDescription() != null){
                    if(statusList.get(position).getDescription() != ""){
                        holder.text_view_item_image_description.setVisibility(View.VISIBLE);
                        holder.text_view_item_image_description.setText(statusList.get(position).getDescription());
                    }
                }
                Picasso.get().load(statusList.get(position).getThumbnail()).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(holder.image_view_thumbnail_item_image);
                Picasso.get().load(statusList.get(position).getUserimage()).error(R.drawable.profile).placeholder(R.drawable.profile).into(holder.circle_image_view_item_image_user);
                holder.text_view_downloads_item_image.setText(format(statusList.get(position).getDownloads()) );
                holder.text_view_views_item_image.setText(format(statusList.get(position).getViews()));
                holder.text_view_created_item_image.setText(statusList.get(position).getCreated());
                holder.text_view_item_image_name_user.setText(statusList.get(position).getUser());
                holder.text_view_item_image_title.setText(statusList.get(position).getTitle());

                if (statusList.get(position).getReview()) {
                    holder.relative_layout_item_post_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.relative_layout_item_post_delete.setVisibility(View.GONE);
                }
                holder.relative_layout_item_post_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(activity)
                                .setTitle(R.string.confirm_delete)
                                .setMessage(R.string.delete_statsu)
                                .setIcon(R.drawable.ic_delete_black)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        register_progress= ProgressDialog.show(activity, null,activity.getResources().getString(R.string.operation_progress), true);

                                        final PrefManager prefManager = new PrefManager(activity);
                                        Integer id_user = 0;
                                        String key_user = "";
                                        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                                            id_user = Integer.parseInt(prefManager.getString("ID_USER"));
                                            key_user = prefManager.getString("TOKEN_USER");
                                        }
                                        Retrofit retrofit = apiClient.getClient();
                                        apiRest service = retrofit.create(apiRest.class);
                                        Call<ApiResponse> call = service.deletePost(statusList.get(position).getId(), id_user, key_user);
                                        call.enqueue(new Callback<ApiResponse>() {
                                            @Override
                                            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                                                register_progress.dismiss();
                                                if (response.isSuccessful()){
                                                    Toasty.success(activity,activity.getResources().getString(R.string.post_delete_success),Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(activity,UserActivity.class);
                                                    intent.putExtra("id", statusList.get(position).getUserid());
                                                    intent.putExtra("name", statusList.get(position).getUser());
                                                    intent.putExtra("image", statusList.get(position).getUserimage());
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                                    activity.finish();
                                                }else{
                                                    Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                                Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                register_progress.dismiss();
                                            }
                                        });


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }
                });


                if (statusList.get(position).getReview())
                    holder.relative_layout_item_image_review.setVisibility(View.VISIBLE);
                else
                    holder.relative_layout_item_image_review.setVisibility(View.GONE);
                if (downloads){
                    holder.image_view_delete_item_image.setVisibility(View.VISIBLE);
                    holder.image_view_fav_item_image.setVisibility(View.GONE);
                    holder.relative_layout_fav_item_image.setVisibility(View.GONE);

                }else{
                    holder.image_view_delete_item_image.setVisibility(View.GONE);
                    holder.image_view_fav_item_image.setVisibility(View.VISIBLE);
                    holder.relative_layout_fav_item_image.setVisibility(View.VISIBLE);
                }
                if (!statusList.get(position).isDownloading()){
                    holder.relative_layout_progress_item_image.setVisibility(View.GONE);
                }else{
                    holder.relative_layout_progress_item_image.setVisibility(View.VISIBLE);
                    holder.progress_bar_item_image.setProgress(statusList.get(position).getProgress());
                    holder.text_view_progress_item_image.setText("Downloading : "+ statusList.get(position).getProgress()+" %");
                }
                holder.relative_layout_item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,ImageActivity.class);
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("local",statusList.get(position).getLocal());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                holder.image_view_thumbnail_item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,ImageActivity.class);
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("local",statusList.get(position).getLocal());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());
                List<Status> statuses = storageFavorites.loadImagesFavorites();
                Boolean exist = false;
                if (statuses ==null){
                    statuses = new ArrayList<>();
                }
                for (int i = 0; i < statuses.size() ; i++) {
                    if (statuses.get(i).getId().equals(statusList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist) {
                    holder.image_view_fav_item_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                }else{
                    holder.image_view_fav_item_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
                holder.image_view_fav_item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
                        Boolean exist = false;
                        if (favorites_list==null){
                            favorites_list= new ArrayList<>();
                        }
                        for (int i = 0; i <favorites_list.size() ; i++) {
                            if (favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Status> audios= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                audios.add(favorites_list.get(i));
                            }
                            audios.add(statusList.get(position));
                            storageFavorites.storeImage(audios);
                            holder.image_view_fav_item_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                        }else{
                            ArrayList<Status> new_favorites= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                if (!favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                    new_favorites.add(favorites_list.get(i));

                                }
                            }
                            if (favorites==true){
                                Log.v("DOWNLOADED","favorites==true");
                                statusList.remove(position);
                                notifyDataSetChanged();
                                //holder.ripple_view_wallpaper_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeImage(new_favorites);
                            holder.image_view_fav_item_image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                });
                final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
                holder.image_view_share_item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,6);

                    }
                });

                holder.image_view_whatsapp_item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,4);

                    }

                });
                holder.image_view_delete_item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DownloadStorage downloadStorage= new DownloadStorage(activity.getApplicationContext());

                        List<Status> downloadedListStatus = downloadStorage.loadImagesFavorites();
                        Boolean exist = false;
                        if (downloadedListStatus ==null){
                            downloadedListStatus = new ArrayList<>();
                        }
                        for (int i = 0; i < downloadedListStatus.size() ; i++) {
                            if (downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == true) {
                            String pathlocal =  statusList.get(position).getLocal();
                            ArrayList<Status> new_dwonloads= new ArrayList<Status>();
                            for (int i = 0; i < downloadedListStatus.size(); i++) {
                                if (!downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                    new_dwonloads.add(downloadedListStatus.get(i));

                                }
                            }
                            if (downloads==true){
                                statusList.remove(position);
                                notifyDataSetChanged();
                            }
                            downloadStorage.storeImage(new_dwonloads);
                            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(pathlocal));
                            File file = new File(pathlocal);
                            if (file.exists()) {
                                file.delete();
                                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                            }
                        }
                    }
                });
            }
            break;
            case 4: {
                final StatusAdapter.GifHolder holder = (StatusAdapter.GifHolder) holder_parent;
                peekAndPop.addLongClickView(holder.image_view_thumbnail_item_gif, position);
                holder.image_view_menu_item.setOnClickListener(v->{
                    PopupMenu popup = new PopupMenu(activity, v);
                    popup.setOnMenuItemClickListener(item -> {
                        Report(statusList.get(position),item);
                        return true;
                    });
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                });
                if (statusList.get(position).getReview()) {
                    holder.relative_layout_item_post_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.relative_layout_item_post_delete.setVisibility(View.GONE);
                }
                if(statusList.get(position).getDescription() != null){
                    if(statusList.get(position).getDescription() != ""){
                        holder.text_view_item_gif_description.setVisibility(View.VISIBLE);
                        holder.text_view_item_gif_description.setText(statusList.get(position).getDescription());
                    }
                }
                holder.relative_layout_item_post_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(activity)
                                .setTitle(R.string.confirm_delete)
                                .setMessage(R.string.delete_statsu)
                                .setIcon(R.drawable.ic_delete_black)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        register_progress= ProgressDialog.show(activity, null,activity.getResources().getString(R.string.operation_progress), true);

                                        final PrefManager prefManager = new PrefManager(activity);
                                        Integer id_user = 0;
                                        String key_user = "";
                                        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                                            id_user = Integer.parseInt(prefManager.getString("ID_USER"));
                                            key_user = prefManager.getString("TOKEN_USER");
                                        }
                                        Retrofit retrofit = apiClient.getClient();
                                        apiRest service = retrofit.create(apiRest.class);
                                        Call<ApiResponse> call = service.deletePost(statusList.get(position).getId(), id_user, key_user);
                                        call.enqueue(new Callback<ApiResponse>() {
                                            @Override
                                            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                                                register_progress.dismiss();
                                                if (response.isSuccessful()){
                                                    Toasty.success(activity,activity.getResources().getString(R.string.post_delete_success),Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(activity,UserActivity.class);
                                                    intent.putExtra("id", statusList.get(position).getUserid());
                                                    intent.putExtra("name", statusList.get(position).getUser());
                                                    intent.putExtra("image", statusList.get(position).getUserimage());
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                                    activity.finish();
                                                }else{
                                                    Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                                Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                register_progress.dismiss();
                                            }
                                        });


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }
                });



                Picasso.get().load(statusList.get(position).getThumbnail()).error(R.drawable.bg_transparant).placeholder(R.drawable.bg_transparant).into(holder.image_view_thumbnail_item_gif);
                Picasso.get().load(statusList.get(position).getUserimage()).error(R.drawable.profile).placeholder(R.drawable.profile).into(holder.circle_image_view_item_gif_user);
                holder.text_view_downloads_item_gif.setText(format(statusList.get(position).getDownloads()) );
                holder.text_view_views_item_gif.setText(format(statusList.get(position).getViews()));
                holder.text_view_created_item_gif.setText(statusList.get(position).getCreated());
                holder.text_view_item_gif_name_user.setText(statusList.get(position).getUser());
                holder.text_view_item_gif_title.setText(statusList.get(position).getTitle());

                if (statusList.get(position).getReview())
                    holder.relative_layout_item_gif_review.setVisibility(View.VISIBLE);
                else
                    holder.relative_layout_item_gif_review.setVisibility(View.GONE);
                if (downloads){
                    holder.image_view_delete_item_gif.setVisibility(View.VISIBLE);
                    holder.image_view_fav_item_gif.setVisibility(View.GONE);
                    holder.relative_layout_fav_item_gif.setVisibility(View.GONE);

                }else{
                    holder.image_view_delete_item_gif.setVisibility(View.GONE);
                    holder.image_view_fav_item_gif.setVisibility(View.VISIBLE);
                    holder.relative_layout_fav_item_gif.setVisibility(View.VISIBLE);
                }
                if (!statusList.get(position).isDownloading()){
                    holder.relative_layout_progress_item_gif.setVisibility(View.GONE);
                }else{
                    holder.relative_layout_progress_item_gif.setVisibility(View.VISIBLE);
                    holder.progress_bar_item_gif.setProgress(statusList.get(position).getProgress());
                    holder.text_view_progress_item_gif.setText("Downloading : "+ statusList.get(position).getProgress()+" %");
                }

                holder.relative_layout_item_gif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,GifActivity.class);
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                holder.image_view_thumbnail_item_gif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,GifActivity.class);
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());

                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());
                List<Status> statuses = storageFavorites.loadImagesFavorites();
                Boolean exist = false;
                if (statuses ==null){
                    statuses = new ArrayList<>();
                }
                for (int i = 0; i < statuses.size() ; i++) {
                    if (statuses.get(i).getId().equals(statusList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist) {
                    holder.image_view_fav_item_gif.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                }else{
                    holder.image_view_fav_item_gif.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
                holder.image_view_fav_item_gif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
                        Boolean exist = false;
                        if (favorites_list==null){
                            favorites_list= new ArrayList<>();
                        }
                        for (int i = 0; i <favorites_list.size() ; i++) {
                            if (favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Status> audios= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                audios.add(favorites_list.get(i));
                            }
                            audios.add(statusList.get(position));
                            storageFavorites.storeImage(audios);
                            holder.image_view_fav_item_gif.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                        }else{
                            ArrayList<Status> new_favorites= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                if (!favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                    new_favorites.add(favorites_list.get(i));

                                }
                            }
                            if (favorites==true){
                                Log.v("DOWNLOADED","favorites==true");
                                statusList.remove(position);
                                notifyDataSetChanged();
                                //holder.ripple_view_wallpaper_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeImage(new_favorites);
                            holder.image_view_fav_item_gif.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                });
                holder.image_view_share_item_gif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,6);
                    }
                });
                final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();

                holder.image_view_whatsapp_item_gif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,4);

                    }
                });
                holder.image_view_delete_item_gif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DownloadStorage downloadStorage= new DownloadStorage(activity.getApplicationContext());

                        List<Status> downloadedListStatus = downloadStorage.loadImagesFavorites();
                        Boolean exist = false;
                        if (downloadedListStatus ==null){
                            downloadedListStatus = new ArrayList<>();
                        }
                        for (int i = 0; i < downloadedListStatus.size() ; i++) {
                            if (downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == true) {
                            String pathlocal =  statusList.get(position).getLocal();
                            ArrayList<Status> new_dwonloads= new ArrayList<Status>();
                            for (int i = 0; i < downloadedListStatus.size(); i++) {
                                if (!downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                    new_dwonloads.add(downloadedListStatus.get(i));

                                }
                            }
                            if (downloads==true){
                                statusList.remove(position);
                                notifyDataSetChanged();
                            }
                            downloadStorage.storeImage(new_dwonloads);
                            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(pathlocal));
                            File file = new File(pathlocal);
                            if (file.exists()) {
                                file.delete();
                                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                            }
                        }
                    }
                });
            }
            break;
            case 5: {
                final StatusAdapter.QuoteHolder holder = (StatusAdapter.QuoteHolder) holder_parent;
                peekAndPop.addLongClickView(holder.relative_layout_background_item_quote, position);
                holder.image_view_menu_item.setOnClickListener(v->{
                    PopupMenu popup = new PopupMenu(activity, v);
                    popup.setOnMenuItemClickListener(item -> {
                        Report(statusList.get(position),item);
                        return true;
                    });
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                });
                byte[] data = android.util.Base64.decode(statusList.get(position).getTitle(), android.util.Base64.DEFAULT);
                final String final_text = new String(data, Charset.forName("UTF-8"));

                if (statusList.get(position).getReview()) {
                    holder.relative_layout_item_post_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.relative_layout_item_post_delete.setVisibility(View.GONE);
                }
                holder.relative_layout_item_post_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(activity)
                                .setTitle(R.string.delete_statsu)
                                .setMessage(R.string.confirm_delete)
                                .setIcon(R.drawable.ic_delete_black)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        register_progress= ProgressDialog.show(activity, null,activity.getResources().getString(R.string.operation_progress), true);

                                        final PrefManager prefManager = new PrefManager(activity);
                                        Integer id_user = 0;
                                        String key_user = "";
                                        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
                                            id_user = Integer.parseInt(prefManager.getString("ID_USER"));
                                            key_user = prefManager.getString("TOKEN_USER");
                                        }
                                        Retrofit retrofit = apiClient.getClient();
                                        apiRest service = retrofit.create(apiRest.class);
                                        Call<ApiResponse> call = service.deletePost(statusList.get(position).getId(), id_user, key_user);
                                        call.enqueue(new Callback<ApiResponse>() {
                                            @Override
                                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                                register_progress.dismiss();
                                                if (response.isSuccessful()){
                                                    Toasty.success(activity,activity.getResources().getString(R.string.post_delete_success),Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(activity,UserActivity.class);
                                                    intent.putExtra("id", statusList.get(position).getUserid());
                                                    intent.putExtra("name", statusList.get(position).getUser());
                                                    intent.putExtra("image", statusList.get(position).getUserimage());
                                                    activity.startActivity(intent);
                                                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                                    activity.finish();
                                                }else{
                                                    Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                                Toasty.error(activity,activity.getResources().getString(R.string.post_delete_failed),Toast.LENGTH_LONG).show();
                                                register_progress.dismiss();
                                            }
                                        });


                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }
                });


                holder.text_view_item_quote_status.setText(final_text);
                holder.relative_layout_background_item_quote.setBackgroundResource(R.drawable.bg_item_quote_background);
                Typeface tf = Typeface.createFromAsset(activity.getAssets(), "font_"+statusList.get(position).getFont()+".ttf");
                holder.text_view_item_quote_status.setTypeface(tf);
                GradientDrawable drawable = (GradientDrawable)  holder.relative_layout_background_item_quote.getBackground();
                drawable.setColor(Color.parseColor("#"+statusList.get(position).getColor()));
                Picasso.get().load(statusList.get(position).getUserimage()).error(R.drawable.profile).placeholder(R.drawable.profile).into(holder.circle_image_view_item_quote_user);
                holder.text_view_downloads_item_quote.setText(format(statusList.get(position).getDownloads()) );
                holder.text_view_views_item_quote.setText(format(statusList.get(position).getViews()));
                holder.text_view_item_quote_name_user.setText(statusList.get(position).getUser());
                holder.text_view_item_quote_title.setText(statusList.get(position).getTitle());

                if (statusList.get(position).getReview())
                    holder.relative_layout_item_quote_review.setVisibility(View.VISIBLE);
                else
                    holder.relative_layout_item_quote_review.setVisibility(View.GONE);
                if (downloads){
                    holder.image_view_delete_item_quote.setVisibility(View.VISIBLE);
                    holder.image_view_fav_item_quote.setVisibility(View.GONE);

                }else{
                    holder.image_view_delete_item_quote.setVisibility(View.GONE);
                    holder.image_view_fav_item_quote.setVisibility(View.VISIBLE);
                }
                if (!statusList.get(position).isDownloading()){
                    holder.relative_layout_progress_item_quote.setVisibility(View.GONE);
                }else{
                    holder.relative_layout_progress_item_quote.setVisibility(View.VISIBLE);
                    holder.progress_bar_item_quote.setProgress(statusList.get(position).getProgress());
                    holder.text_view_progress_item_quote.setText("Downloading : "+ statusList.get(position).getProgress()+" %");
                }

                holder.relative_layout_item_quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,QuoteActivity.class);
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());
                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                holder.relative_layout_background_item_quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,QuoteActivity.class);
                        intent.putExtra("id",statusList.get(position).getId());
                        intent.putExtra("title",statusList.get(position).getTitle());
                        intent.putExtra("kind",statusList.get(position).getKind());
                        intent.putExtra("description",statusList.get(position).getDescription());
                        intent.putExtra("review",statusList.get(position).getReview());
                        intent.putExtra("comment",statusList.get(position).getComment());
                        intent.putExtra("comments",statusList.get(position).getComments());
                        intent.putExtra("downloads",statusList.get(position).getDownloads());
                        intent.putExtra("views",statusList.get(position).getViews());
                        intent.putExtra("font",statusList.get(position).getFont());
                        intent.putExtra("user",statusList.get(position).getUser());
                        intent.putExtra("userid",statusList.get(position).getUserid());
                        intent.putExtra("userimage",statusList.get(position).getUserimage());
                        intent.putExtra("thumbnail",statusList.get(position).getThumbnail());
                        intent.putExtra("original",statusList.get(position).getOriginal());
                        intent.putExtra("type",statusList.get(position).getType());
                        intent.putExtra("extension",statusList.get(position).getExtension());
                        intent.putExtra("color",statusList.get(position).getColor());
                        intent.putExtra("created",statusList.get(position).getCreated());
                        intent.putExtra("tags",statusList.get(position).getTags());
                        intent.putExtra("like",statusList.get(position).getLike());
                        intent.putExtra("love",statusList.get(position).getLove());
                        intent.putExtra("woow",statusList.get(position).getWoow());
                        intent.putExtra("angry",statusList.get(position).getAngry());
                        intent.putExtra("sad",statusList.get(position).getSad());
                        intent.putExtra("haha",statusList.get(position).getHaha());
                        intent.putExtra("local",statusList.get(position).getLocal());

                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                    }
                });
                final FavoritesStorage storageFavorites= new FavoritesStorage(activity.getApplicationContext());
                List<Status> statuses = storageFavorites.loadImagesFavorites();
                Boolean exist = false;
                if (statuses ==null){
                    statuses = new ArrayList<>();
                }
                for (int i = 0; i < statuses.size() ; i++) {
                    if (statuses.get(i).getId().equals(statusList.get(position).getId())){
                        exist = true;
                    }
                }
                if (exist) {
                    holder.image_view_fav_item_quote.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                }else{
                    holder.image_view_fav_item_quote.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
                holder.image_view_fav_item_quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Status> favorites_list = storageFavorites.loadImagesFavorites();
                        Boolean exist = false;
                        if (favorites_list==null){
                            favorites_list= new ArrayList<>();
                        }
                        for (int i = 0; i <favorites_list.size() ; i++) {
                            if (favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == false) {
                            ArrayList<Status> audios= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                audios.add(favorites_list.get(i));
                            }
                            audios.add(statusList.get(position));
                            storageFavorites.storeImage(audios);
                            holder.image_view_fav_item_quote.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                        }else{
                            ArrayList<Status> new_favorites= new ArrayList<Status>();
                            for (int i = 0; i < favorites_list.size(); i++) {
                                if (!favorites_list.get(i).getId().equals(statusList.get(position).getId())){
                                    new_favorites.add(favorites_list.get(i));

                                }
                            }
                            if (favorites==true){
                                Log.v("DOWNLOADED","favorites==true");
                                statusList.remove(position);
                                notifyDataSetChanged();
                                //holder.ripple_view_wallpaper_item.setVisibility(View.GONE);
                            }
                            storageFavorites.storeImage(new_favorites);
                            holder.image_view_fav_item_quote.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                });
                final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
                holder.image_view_share_item_quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,7)    ;
                    }
                });

                holder.image_view_whatsapp_item_quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Operation(position,8)    ;

                    }

                });
                holder.image_view_delete_item_quote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DownloadStorage downloadStorage= new DownloadStorage(activity.getApplicationContext());

                        List<Status> downloadedListStatus = downloadStorage.loadImagesFavorites();
                        Boolean exist = false;
                        if (downloadedListStatus ==null){
                            downloadedListStatus = new ArrayList<>();
                        }
                        for (int i = 0; i < downloadedListStatus.size() ; i++) {
                            if (downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                exist = true;
                            }
                        }
                        if (exist  == true) {
                            String pathlocal =  statusList.get(position).getLocal();
                            ArrayList<Status> new_dwonloads= new ArrayList<Status>();
                            for (int i = 0; i < downloadedListStatus.size(); i++) {
                                if (!downloadedListStatus.get(i).getId().equals(statusList.get(position).getId())){
                                    new_dwonloads.add(downloadedListStatus.get(i));

                                }
                            }
                            if (downloads==true){
                                statusList.remove(position);
                                notifyDataSetChanged();
                            }
                            downloadStorage.storeImage(new_dwonloads);
                            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(pathlocal));
                            File file = new File(pathlocal);
                            if (file.exists()) {
                                file.delete();
                                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                            }
                        }
                    }
                });
            }
            break;
            case 7: {

                final SubscriptionHolder holder = (SubscriptionHolder) holder_parent;
                this.linearLayoutManager=  new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false);
                this.subscribeAdapter =new SubscribeAdapter(userList,activity);
                holder.recycle_view_follow_items.setHasFixedSize(true);
                holder.recycle_view_follow_items.setAdapter(subscribeAdapter);
                holder.recycle_view_follow_items.setLayoutManager(linearLayoutManager);
                subscribeAdapter.notifyDataSetChanged();
                break;
            }
            case 8: {
                final SlideHolder holder = (SlideHolder) holder_parent;

                slide_adapter = new SlideAdapter(activity, slideList);
                holder.view_pager_slide.setAdapter(slide_adapter);
                holder.view_pager_slide.setOffscreenPageLimit(1);

                holder.view_pager_slide.setClipToPadding(false);
                holder.view_pager_slide.setPageMargin(0);
                holder.view_pager_indicator.setupWithViewPager(holder.view_pager_slide);
                slide_adapter.notifyDataSetChanged();
                break;
            }
            case 9: {

                final SearchUserListHolder holder = (SearchUserListHolder) holder_parent;
                this.linearLayoutManagerSearch=  new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false);
                this.searchUserAdapter =new SearchUserAdapter(userList,activity);
                holder.recycle_view_users_items.setHasFixedSize(true);
                holder.recycle_view_users_items.setAdapter(searchUserAdapter);
                holder.recycle_view_users_items.setLayoutManager(linearLayoutManagerSearch);
                searchUserAdapter.notifyDataSetChanged();
                break;
            }
            case 10:{
                final PortraitVideoListHolder holder = (PortraitVideoListHolder) holder_parent;
                switch (type){
                    case "home":
                        holder.text_view_item_full_screen_title.setText("Fullscreen videos");
                        break;
                    case "category":
                        holder.text_view_item_full_screen_title.setText(name+" Fullscreen videos");
                        break;
                    case "user":
                        holder.text_view_item_full_screen_title.setText(name+" Fullscreen videos");
                        break;
                    case "search":
                        holder.text_view_item_full_screen_title.setText(name+" Fullscreen videos");
                        break;
                    case "follow":
                        holder.text_view_item_full_screen_title.setText(name+" Fullscreen videos");
                        break;
                }
                holder.portraitVideoAdapter.notifyDataSetChanged();
                holder.relative_layout_show_all_portrait_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case "home":
                                Intent intent  = new Intent(activity,AllPortaitVideosActivity.class);
                                activity.startActivity(intent);
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                break;
                            case "category":
                                Intent intent1  = new Intent(activity,AllFullScreenCategoryActivity.class);
                                intent1.putExtra("id",id);
                                intent1.putExtra("title",name);
                                activity.startActivity(intent1);
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                break;
                            case "user":
                                Intent intent2  = new Intent(activity,AllFullScreenUserActivity.class);
                                intent2.putExtra("id",id);
                                intent2.putExtra("name",name);
                                intent2.putExtra("image",image);
                                activity.startActivity(intent2);
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                break;
                            case "search":
                                Intent intent3  = new Intent(activity,AllFullScreenSearchActivity.class);
                                intent3.putExtra("query",name);
                                activity.startActivity(intent3);
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                break;
                            case "follow":
                                Intent intent4  = new Intent(activity,AllFullScreenFollowActivity.class);
                                activity.startActivity(intent4);
                                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                break;
                        }

                    }
                });
            }
            break;
        }


    }


    public static class ImageHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relative_layout_fav_item_image;
        private final RelativeLayout relative_layout_item_image_review;
        private final RelativeLayout relative_layout_item_image;
        private final AppCompatImageView image_view_menu_item;
        private final AppCompatImageView image_view_delete_item_image;
        private final AppCompatImageView image_view_fav_item_image;
        private final AppCompatImageView image_view_share_item_image;
        private final AppCompatImageView image_view_whatsapp_item_image;
        private final AppCompatImageView image_view_icon_item_image;
        private final TextView text_view_progress_item_image;
        private final RelativeLayout relative_layout_progress_item_image;
        private final ProgressBar progress_bar_item_image;
        private final CircleImageView circle_image_view_item_image_user;
        private final TextView text_view_item_image_name_user;
        private final TextView text_view_item_image_title;
        private final TextView text_view_item_image_description;
        private final AppCompatImageView image_view_thumbnail_item_image;
        private final TextView text_view_downloads_item_image;
        private final TextView text_view_views_item_image;
        private final TextView text_view_created_item_image;
        private final RelativeLayout relative_layout_item_post_delete;

        public ImageHolder(View itemView) {
            super(itemView);
            this.relative_layout_fav_item_image = (RelativeLayout) itemView.findViewById(R.id.relative_layout_fav_item_image);
            this.relative_layout_item_post_delete = (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_post_delete);

            this.relative_layout_item_image_review=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_image_review);
            this.relative_layout_item_image=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_image);
            this.image_view_menu_item=(AppCompatImageView) itemView.findViewById(R.id.image_view_menu_item);
            this.image_view_delete_item_image=(AppCompatImageView) itemView.findViewById(R.id.image_view_delete_item_image);
            this.image_view_fav_item_image=(AppCompatImageView) itemView.findViewById(R.id.image_view_fav_item_image);
            this.image_view_share_item_image=(AppCompatImageView) itemView.findViewById(R.id.image_view_share_item_image);
            this.image_view_whatsapp_item_image=(AppCompatImageView) itemView.findViewById(R.id.image_view_whatsapp_item_image);
            this.image_view_icon_item_image=(AppCompatImageView) itemView.findViewById(R.id.image_view_icon_item_image);
            this.text_view_progress_item_image=(TextView) itemView.findViewById(R.id.text_view_progress_item_image);
            this.relative_layout_progress_item_image=(RelativeLayout) itemView.findViewById(R.id.relative_layout_progress_item_image);
            this.progress_bar_item_image=(ProgressBar) itemView.findViewById(R.id.progress_bar_item_image);
            this.circle_image_view_item_image_user=(CircleImageView) itemView.findViewById(R.id.circle_image_view_item_image_user);
            this.text_view_item_image_name_user=(TextView) itemView.findViewById(R.id.text_view_item_image_name_user);
            this.text_view_item_image_title = (TextView) itemView.findViewById(R.id.text_view_item_image_title);
            this.text_view_item_image_description = (TextView) itemView.findViewById(R.id.text_view_item_image_description);
            this.image_view_thumbnail_item_image=(AppCompatImageView) itemView.findViewById(R.id.image_view_thumbnail_item_image);
            this.text_view_downloads_item_image=(TextView) itemView.findViewById(R.id.text_view_downloads_item_image);
            this.text_view_created_item_image=(TextView) itemView.findViewById(R.id.text_view_created_item_image);
            this.text_view_views_item_image=(TextView) itemView.findViewById(R.id.text_view_views_item_image);
        }
    }
    public static class GifHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relative_layout_fav_item_gif;
        private final RelativeLayout relative_layout_item_gif_review;
        private final RelativeLayout relative_layout_item_gif;
        private final AppCompatImageView image_view_menu_item;
        private final AppCompatImageView image_view_delete_item_gif;
        private final AppCompatImageView image_view_fav_item_gif;
        private final AppCompatImageView image_view_share_item_gif;
        private final AppCompatImageView image_view_whatsapp_item_gif;
        private final AppCompatImageView image_view_icon_item_gif;
        private final TextView text_view_progress_item_gif;
        private final RelativeLayout relative_layout_progress_item_gif;
        private final ProgressBar progress_bar_item_gif;
        private final CircleImageView circle_image_view_item_gif_user;
        private final TextView text_view_item_gif_name_user;
        private final TextView text_view_item_gif_title;
        private final TextView text_view_item_gif_description;
        private final AppCompatImageView image_view_thumbnail_item_gif;
        private final TextView text_view_downloads_item_gif;
        private final TextView text_view_views_item_gif;
        private final TextView text_view_created_item_gif;
        private final RelativeLayout relative_layout_item_post_delete;

        public GifHolder(View itemView) {
            super(itemView);
            this.relative_layout_fav_item_gif = (RelativeLayout) itemView.findViewById(R.id.relative_layout_fav_item_gif);

            this.relative_layout_item_post_delete = (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_post_delete);

            this.relative_layout_item_gif_review=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_gif_review);
            this.relative_layout_item_gif=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_gif);
            this.image_view_menu_item=(AppCompatImageView) itemView.findViewById(R.id.image_view_menu_item);
            this.image_view_delete_item_gif=(AppCompatImageView) itemView.findViewById(R.id.image_view_delete_item_gif);
            this.image_view_fav_item_gif=(AppCompatImageView) itemView.findViewById(R.id.image_view_fav_item_gif);
            this.image_view_share_item_gif=(AppCompatImageView) itemView.findViewById(R.id.image_view_share_item_gif);
            this.image_view_whatsapp_item_gif=(AppCompatImageView) itemView.findViewById(R.id.image_view_whatsapp_item_gif);
            this.image_view_icon_item_gif=(AppCompatImageView) itemView.findViewById(R.id.image_view_icon_item_gif);
            this.text_view_progress_item_gif=(TextView) itemView.findViewById(R.id.text_view_progress_item_gif);
            this.relative_layout_progress_item_gif=(RelativeLayout) itemView.findViewById(R.id.relative_layout_progress_item_gif);
            this.progress_bar_item_gif=(ProgressBar) itemView.findViewById(R.id.progress_bar_item_gif);
            this.circle_image_view_item_gif_user=(CircleImageView) itemView.findViewById(R.id.circle_image_view_item_gif_user);
            this.text_view_item_gif_name_user=(TextView) itemView.findViewById(R.id.text_view_item_gif_name_user);
            this.text_view_item_gif_title = (TextView) itemView.findViewById(R.id.text_view_item_gif_title);
            this.text_view_item_gif_description = (TextView) itemView.findViewById(R.id.text_view_item_gif_description);
            this.image_view_thumbnail_item_gif=(AppCompatImageView) itemView.findViewById(R.id.image_view_thumbnail_item_gif);
            this.text_view_downloads_item_gif=(TextView) itemView.findViewById(R.id.text_view_downloads_item_gif);
            this.text_view_created_item_gif=(TextView) itemView.findViewById(R.id.text_view_created_item_gif);
            this.text_view_views_item_gif=(TextView) itemView.findViewById(R.id.text_view_views_item_gif);
        }
    }
    public static class QuoteHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relative_layout_item_quote_review;
        private final RelativeLayout relative_layout_item_quote;
        private final AppCompatImageView image_view_delete_item_quote;
        private final AppCompatImageView image_view_menu_item;
        private final AppCompatImageView image_view_fav_item_quote;
        private final AppCompatImageView image_view_share_item_quote;
        private final AppCompatImageView image_view_whatsapp_item_quote;
        private final AppCompatImageView image_view_icon_item_quote;
        private final TextView text_view_progress_item_quote;
        private final RelativeLayout relative_layout_progress_item_quote;
        private final ProgressBar progress_bar_item_quote;
        private final CircleImageView circle_image_view_item_quote_user;
        private final TextView text_view_item_quote_name_user;
        private final TextView text_view_item_quote_title;
        private final TextView text_view_downloads_item_quote;
        private final TextView text_view_views_item_quote;
        private final TextView text_view_item_quote_status;
        private final RelativeLayout relative_layout_background_item_quote;
        private final RelativeLayout relative_layout_item_post_delete;

        public QuoteHolder(View itemView) {
            super(itemView);

            this.relative_layout_item_post_delete= (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_post_delete);

            this.relative_layout_item_quote_review=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_quote_review);
            this.relative_layout_item_quote=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_quote);
            this.image_view_delete_item_quote=(AppCompatImageView) itemView.findViewById(R.id.image_view_delete_item_quote);
            this.image_view_menu_item=(AppCompatImageView) itemView.findViewById(R.id.image_view_menu_item);
            this.image_view_fav_item_quote=(AppCompatImageView) itemView.findViewById(R.id.image_view_fav_item_quote);
            this.image_view_share_item_quote=(AppCompatImageView) itemView.findViewById(R.id.image_view_share_item_quote);
            this.image_view_whatsapp_item_quote=(AppCompatImageView) itemView.findViewById(R.id.image_view_whatsapp_item_quote);
            this.image_view_icon_item_quote=(AppCompatImageView) itemView.findViewById(R.id.image_view_icon_item_quote);
            this.text_view_progress_item_quote=(TextView) itemView.findViewById(R.id.text_view_progress_item_quote);
            this.relative_layout_progress_item_quote=(RelativeLayout) itemView.findViewById(R.id.relative_layout_progress_item_quote);
            this.progress_bar_item_quote=(ProgressBar) itemView.findViewById(R.id.progress_bar_item_quote);
            this.circle_image_view_item_quote_user=(CircleImageView) itemView.findViewById(R.id.circle_image_view_item_quote_user);
            this.text_view_item_quote_name_user=(TextView) itemView.findViewById(R.id.text_view_item_quote_name_user);
            this.text_view_item_quote_title = (TextView) itemView.findViewById(R.id.text_view_item_quote_title);
            this.relative_layout_background_item_quote=(RelativeLayout) itemView.findViewById(R.id.relative_layout_background_item_quote);
            this.text_view_item_quote_status=(TextView) itemView.findViewById(R.id.text_view_item_quote_status);
            this.text_view_downloads_item_quote=(TextView) itemView.findViewById(R.id.text_view_downloads_item_quote);
            this.text_view_views_item_quote=(TextView) itemView.findViewById(R.id.text_view_views_item_quote);
        }
    }
    public static class YoutubeHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relative_layout_item_youtube_review;
        private final RelativeLayout relative_layout_item_youtube;
        private final AppCompatImageView image_view_menu_item;
        private final AppCompatImageView image_view_fav_item_youtube;
        private final AppCompatImageView image_view_share_item_youtube;
        private final AppCompatImageView image_view_whatsapp_item_youtube;
        private final CircleImageView circle_image_view_item_youtube_user;
        private final TextView text_view_item_youtube_name_user;
        private final TextView text_view_item_youtube_title;
        private final TextView text_view_item_youtube_description;
        private final AppCompatImageView image_view_thumbnail_item_youtube;
        private final TextView text_view_downloads_item_youtube;
        private final TextView text_view_views_item_youtube;
        private final TextView text_view_created_item_youtube;
        private final RelativeLayout relative_layout_item_post_delete;

        public YoutubeHolder(View itemView) {
            super(itemView);
            this.relative_layout_item_post_delete = (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_post_delete);

            this.relative_layout_item_youtube_review=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_youtube_review);
            this.relative_layout_item_youtube=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_youtube);
            this.image_view_menu_item=(AppCompatImageView) itemView.findViewById(R.id.image_view_menu_item);
            this.image_view_fav_item_youtube=(AppCompatImageView) itemView.findViewById(R.id.image_view_fav_item_youtube);
            this.image_view_share_item_youtube=(AppCompatImageView) itemView.findViewById(R.id.image_view_share_item_youtube);
            this.image_view_whatsapp_item_youtube=(AppCompatImageView) itemView.findViewById(R.id.image_view_whatsapp_item_youtube);
            this.circle_image_view_item_youtube_user=(CircleImageView) itemView.findViewById(R.id.circle_image_view_item_youtube_user);
            this.text_view_item_youtube_name_user=(TextView) itemView.findViewById(R.id.text_view_item_youtube_name_user);
            this.text_view_item_youtube_title = (TextView) itemView.findViewById(R.id.text_view_item_youtube_title);
            this.text_view_item_youtube_description = (TextView) itemView.findViewById(R.id.text_view_item_youtube_description);
            this.image_view_thumbnail_item_youtube=(AppCompatImageView) itemView.findViewById(R.id.image_view_thumbnail_item_youtube);
            this.text_view_downloads_item_youtube=(TextView) itemView.findViewById(R.id.text_view_downloads_item_youtube);
            this.text_view_created_item_youtube=(TextView) itemView.findViewById(R.id.text_view_created_item_youtube);
            this.text_view_views_item_youtube=(TextView) itemView.findViewById(R.id.text_view_views_item_youtube);
        }
    }
    public static class VideoHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relative_layout_fav_item_video;
        private final RelativeLayout relative_layout_item_video_review;
        private final RelativeLayout relative_layout_item_video;
        private final AppCompatImageView image_view_menu_item;
        private final AppCompatImageView image_view_delete_item_video;
        private final AppCompatImageView image_view_fav_item_video;
        private final AppCompatImageView image_view_share_item_video;
        private final AppCompatImageView image_view_whatsapp_item_video;
        private final AppCompatImageView image_view_icon_item_video;
        private final TextView text_view_progress_item_video;
        private final RelativeLayout relative_layout_progress_item_video;
        private final ProgressBar progress_bar_item_video;
        private final CircleImageView circle_image_view_item_video_user;
        private final TextView text_view_item_video_name_user;
        private final TextView text_view_item_video_title;
        private final TextView text_view_item_video_description;
        private final AppCompatImageView image_view_thumbnail_item_video;
        private final TextView text_view_downloads_item_video;
        private final TextView text_view_views_item_video;
        private final TextView text_view_created_item_video;
        private final RelativeLayout relative_layout_item_post_delete;

        public VideoHolder(View itemView) {
            super(itemView);
            this.relative_layout_item_post_delete = (RelativeLayout) itemView.findViewById(R.id.relative_layout_item_post_delete);
            this.relative_layout_fav_item_video = (RelativeLayout) itemView.findViewById(R.id.relative_layout_fav_item_video);

            this.relative_layout_item_video_review=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_video_review);
            this.relative_layout_item_video=(RelativeLayout) itemView.findViewById(R.id.relative_layout_item_video);
            this.image_view_menu_item=(AppCompatImageView) itemView.findViewById(R.id.image_view_menu_item);
            this.image_view_delete_item_video=(AppCompatImageView) itemView.findViewById(R.id.image_view_delete_item_video);
            this.image_view_fav_item_video=(AppCompatImageView) itemView.findViewById(R.id.image_view_fav_item_video);
            this.image_view_share_item_video=(AppCompatImageView) itemView.findViewById(R.id.image_view_share_item_video);
            this.image_view_whatsapp_item_video=(AppCompatImageView) itemView.findViewById(R.id.image_view_whatsapp_item_video);
            this.image_view_icon_item_video=(AppCompatImageView) itemView.findViewById(R.id.image_view_icon_item_video);
            this.text_view_progress_item_video=(TextView) itemView.findViewById(R.id.text_view_progress_item_video);
            this.relative_layout_progress_item_video=(RelativeLayout) itemView.findViewById(R.id.relative_layout_progress_item_video);
            this.progress_bar_item_video=(ProgressBar) itemView.findViewById(R.id.progress_bar_item_video);
            this.circle_image_view_item_video_user=(CircleImageView) itemView.findViewById(R.id.circle_image_view_item_video_user);
            this.text_view_item_video_name_user=(TextView) itemView.findViewById(R.id.text_view_item_video_name_user);
            this.text_view_item_video_title = (TextView) itemView.findViewById(R.id.text_view_item_video_title);
            this.text_view_item_video_description = (TextView) itemView.findViewById(R.id.text_view_item_video_description);
            this.image_view_thumbnail_item_video=(AppCompatImageView) itemView.findViewById(R.id.image_view_thumbnail_item_video);
            this.text_view_downloads_item_video=(TextView) itemView.findViewById(R.id.text_view_downloads_item_video);
            this.text_view_created_item_video=(TextView) itemView.findViewById(R.id.text_view_created_item_video);
            this.text_view_views_item_video=(TextView) itemView.findViewById(R.id.text_view_views_item_video);
        }
    }
    public  class CategoriesHolder extends RecyclerView.ViewHolder {
        private final LinearLayoutManager linearLayoutManager;
        private final CategoryAdapter categoryVideoAdapter;
        public RecyclerView recycler_view_item_categories;

        public CategoriesHolder(View view) {
            super(view);
            this.recycler_view_item_categories = (RecyclerView) itemView.findViewById(R.id.recycler_view_item_categories);
            this.linearLayoutManager=  new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            this.categoryVideoAdapter =new CategoryAdapter(categoryList,activity);
            recycler_view_item_categories.setHasFixedSize(true);
            recycler_view_item_categories.setAdapter(categoryVideoAdapter);
            recycler_view_item_categories.setLayoutManager(linearLayoutManager);
        }
    }
    public  class EmptyHolder extends RecyclerView.ViewHolder {


        public EmptyHolder(View view) {
            super(view);

        }
    }
    @Override
    public int getItemCount() {
        return statusList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (statusList.get(position).getKind()!=null){
            switch (statusList.get(position).getKind()) {
                case "video":
                    return 2;
                case "image":
                    return 3;
                case "gif":
                    return 4;
                case "quote":
                    return 5;
                case "youtube":
                    return 12;
                case "fullscreen":
                    return 2;
            }
        }
        return statusList.get(position).getViewType();
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
                String id = statusList.get(position).getId().toString();


                statusList.get(position).setDownloading(true);
                URLConnection conection = url.openConnection();
                conection.setRequestProperty("Accept-Encoding", "identity");

                conection.connect();
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);



                String dir_path = Environment.getExternalStorageDirectory().toString() + "/StatusVideos/";
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    dir_path = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
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
                    // Output stream
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
                    MediaScannerConnection.scanFile(activity.getApplicationContext(), new String[] { dir_path+title.toString().replace("/","_")+"_"+id+"."+extension },
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
                        activity.sendBroadcast(scanIntent);
                    } else {
                        final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                        activity.sendBroadcast(intent);
                    }
                }
                statusList.get(position).setPath(dir_path+title.toString().replace("/","_")+"_"+id+"."+extension);
            } catch (Exception e) {
                Log.v("ex",e.getMessage());
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
                    statusList.get(position).setProgress(Integer.valueOf(progress[0]));
                    notifyDataSetChanged();
                    old=progress[0];
                    Log.v("download",progress[0]+"%");
                    statusList.get(position).setDownloading(true);
                    statusList.get(position).setProgress(Integer.parseInt(progress[0]));
                }
            }catch (Exception e){

            }

        }
        public void AddDownloadLocal(Integer position){
            final DownloadStorage downloadStorage= new DownloadStorage(activity.getApplicationContext());
            List<com.virmana.status_app_all.model.Status> download_list = downloadStorage.loadImagesFavorites();
            Boolean exist = false;
            if (download_list==null){
                download_list= new ArrayList<>();
            }
            for (int i = 0; i <download_list.size() ; i++) {
                if (download_list.get(i).getId().equals(statusList.get(position).getId())){
                    exist = true;
                }
            }
            if (exist  == false) {
                ArrayList<com.virmana.status_app_all.model.Status> audios= new ArrayList<com.virmana.status_app_all.model.Status>();
                for (int i = 0; i < download_list.size(); i++) {
                    audios.add(download_list.get(i));
                }
                com.virmana.status_app_all.model.Status videodownloaded = statusList.get(position);

                videodownloaded.setLocal(statusList.get(position).getPath());

                audios.add(videodownloaded);
                downloadStorage.storeImage(audios);
            }
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {

            statusList.get(position).setDownloading(false);
            if (statusList.get(position).getPath()==null){
                if (downloads){
                    switch (share_app) {
                        case WHATSAPP_ID:
                            shareWhatsapp(position);
                            break;
                        case FACEBOOK_ID:
                            shareFacebook(position);
                            break;
                        case MESSENGER_ID:
                            shareMessenger(position);
                            break;
                        case INSTAGRAM_ID:
                            shareInstagram(position);
                            break;
                        case SHARE_ID:
                            share(position);
                            break;
                    }
                }else{
                    Toasty.error(activity.getApplicationContext(), activity.getString(R.string.download_failed), Toast.LENGTH_SHORT, true).show();
                }
            }else {
                addShare(position);
                AddDownloadLocal(position);
                switch (share_app) {
                    case WHATSAPP_ID:
                        shareWhatsapp(position);
                        break;
                    case FACEBOOK_ID:
                        shareFacebook(position);
                        break;
                    case MESSENGER_ID:
                        shareMessenger(position);
                        break;
                    case INSTAGRAM_ID:
                        shareInstagram(position);
                        break;
                    case SHARE_ID:
                        share(position);
                        break;
                }
            }
            notifyDataSetChanged();
        }

        public void shareWhatsapp(Integer position){
            String path = statusList.get(position).getPath();
            if (statusList.get(position).getLocal()!=null){
                if (new File(statusList.get(position).getLocal()).exists()){
                    path =  statusList.get(position).getLocal();
                }
            }
            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(WHATSAPP_ID);



            final String final_text = activity.getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);


            shareIntent.setType(statusList.get(position).getType());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                activity.startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(activity.getApplicationContext(), activity.getResources().getString(R.string.whatsapp_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareFacebook(Integer position){
            String path = statusList.get(position).getPath();

            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(FACEBOOK_ID);



            final String final_text = activity.getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(statusList.get(position).getType());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                activity.startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(activity.getApplicationContext(), activity.getResources().getString(R.string.facebook_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareMessenger(Integer position){
            String path = statusList.get(position).getPath();

            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(MESSENGER_ID);

            final String final_text = activity.getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(statusList.get(position).getType());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                activity.startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(activity.getApplicationContext(), activity.getResources().getString(R.string.messenger_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void shareInstagram(Integer position){
            String path = statusList.get(position).getPath();

            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(INSTAGRAM_ID);



            final String final_text = activity.getResources().getString(R.string.download_more_from_link);

            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(statusList.get(position).getType());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                activity.startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(activity.getApplicationContext(), activity.getResources().getString(R.string.instagram_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }
        public void share(Integer position){
            String path = statusList.get(position).getPath();

            Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(path));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);



            final String final_text = activity.getResources().getString(R.string.download_more_from_link);
            shareIntent.putExtra(Intent.EXTRA_TEXT,final_text );
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            shareIntent.setType(statusList.get(position).getType());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                activity.startActivity(Intent.createChooser(shareIntent, "Shared via " + activity.getResources().getString(R.string.app_name) ));
            } catch (android.content.ActivityNotFoundException ex) {
                Toasty.error(activity.getApplicationContext(), activity.getResources().getString(R.string.app_not_installed), Toast.LENGTH_SHORT, true).show();
            }
        }

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
    public void addShare(final Integer position){
        final PrefManager prefManager = new PrefManager(activity);
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(statusList.get(position).getId().toString()+"_share").equals("true")) {
            prefManager.setString(statusList.get(position).getId().toString()+"_share", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<Integer> call = service.addShare(statusList.get(position).getId(),id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                    if(response.isSuccessful()){
                        statusList.get(position).setDownloads(response.body());
                        notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        }
    }
    public void addView(final Integer position){
        final PrefManager prefManager = new PrefManager(activity);
        Integer id_user = 0;
        String key_user= "";
        if (prefManager.getString("LOGGED").toString().equals("TRUE")) {
            id_user=  Integer.parseInt(prefManager.getString("ID_USER"));
            key_user=  prefManager.getString("TOKEN_USER");
        }
        if (!prefManager.getString(statusList.get(position).getId().toString()+"_view").equals("true")) {
            prefManager.setString(statusList.get(position).getId().toString()+"_view", "true");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            String ishash="";
            try {
                ishash   = VideoActivity.sha1((statusList.get(position).getId()+55463938)+"");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            };
            Call<Integer> call = service.addView(ishash,id_user,key_user);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                    if (response.isSuccessful()) {
                        statusList.get(position).setViews(response.body());
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                }
            });
        }
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
    private void requestISInterstitial() {
        if(!IronSource.isInterstitialReady()){
            PrefManager prefManager= new PrefManager(activity);
            IronSource.init(activity, prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"), IronSource.AD_UNIT.INTERSTITIAL);
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

                    selectOperation(position_selected,code_selected);

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
    private void requestMaxInterstitial() {
        if (maxInterstitialAd==null){
            PrefManager prefManager= new PrefManager(activity);




            maxInterstitialAd = new  MaxInterstitialAd(prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"), activity);;

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
                    selectOperation(position_selected,code_selected);

                    requestMaxInterstitial();
                }
            });

            maxInterstitialAd.loadAd();


        }


    }
    private void requestAppLovinInterstitial() {

        if (applovinInterstitialAdBlock==null){

                    applovinInterstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( activity ), activity );


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
                            selectOperation(position_selected,code_selected);
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
                    AppLovinSdk.getInstance( activity.getApplicationContext() ).getAdService().loadNextAd(AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener() {
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



    private void requestAdmobInterstitial() {
        if (admobInterstitialAd==null){
            PrefManager prefManager= new PrefManager(activity);
            AdRequest adRequest = new AdRequest.Builder().build();
            admobInterstitialAd.load(activity.getApplicationContext(), prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    admobInterstitialAd = interstitialAd;


                    admobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            selectOperation(position_selected,code_selected);

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


    public void shareTextWith(Integer  position,String APP_ID){

        String shareBody = "";

        if (statusList.get(position).getKind().equals("youtube")){
            shareBody = statusList.get(position).getTitle() + "\n\n" + statusList.get(position).getOriginal();
        }
        else{
            String text = null;
            try {
                byte[] data = Base64.decode(statusList.get(position).getTitle(), Base64.DEFAULT);
                text = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            shareBody = text;

        }
        shareBody += " \n\n  "+activity.getResources().getString(R.string.download_more_from_link);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        if (!SHARE_ID.equals(APP_ID)){
            sharingIntent.setPackage(APP_ID);
        }
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.share_via)));
        addShare(position);
    }

    public static class SubscriptionHolder extends  RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_follow_items;
        public SubscriptionHolder(View view) {
            super(view);
            recycle_view_follow_items = (RecyclerView) itemView.findViewById(R.id.recycle_view_follow_items);
        }
    }
    public static class SearchUserListHolder extends  RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_users_items;
        public SearchUserListHolder(View view) {
            super(view);
            recycle_view_users_items = (RecyclerView) itemView.findViewById(R.id.recycle_view_users_items);
        }
    }
    private class SlideHolder extends RecyclerView.ViewHolder {
        private final ViewPagerIndicator view_pager_indicator;
        private final ClickableViewPager view_pager_slide;
        public SlideHolder(View itemView) {
            super(itemView);
            this.view_pager_indicator=(ViewPagerIndicator) itemView.findViewById(R.id.view_pager_indicator);
            this.view_pager_slide=(ClickableViewPager) itemView.findViewById(R.id.view_pager_slide);
        }

    }

    private class PortraitVideoListHolder extends RecyclerView.ViewHolder {
        private final LinearLayoutManager linearLayoutManager;
        private final PortraitVideoAdapter portraitVideoAdapter;
        private final RelativeLayout relative_layout_show_all_portrait_all;
        private final TextView text_view_item_full_screen_title;
        public RecyclerView recycler_view_item_portrait_list;

        public PortraitVideoListHolder(View itemView) {
            super(itemView);
            this.text_view_item_full_screen_title = (TextView) itemView.findViewById(R.id.text_view_item_full_screen_title);
            this.relative_layout_show_all_portrait_all = (RelativeLayout) itemView.findViewById(R.id.relative_layout_show_all_portrait_all);
            this.recycler_view_item_portrait_list = (RecyclerView) itemView.findViewById(R.id.recycler_view_item_portrait_list);
            this.linearLayoutManager=  new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            this.portraitVideoAdapter =new PortraitVideoAdapter(fullScreenVdeos,activity);
            recycler_view_item_portrait_list.setHasFixedSize(true);
            recycler_view_item_portrait_list.setAdapter(portraitVideoAdapter);
            recycler_view_item_portrait_list.setLayoutManager(linearLayoutManager);
            portraitVideoAdapter.notifyDataSetChanged();
        }
    }
    public class MaxNativeHolder extends RecyclerView.ViewHolder {
        private MaxNativeAdLoader nativeAdLoader;
        private MaxAd             loadedNativeAd;
        private FrameLayout         native_ad_layout;

        public MaxNativeHolder(@NonNull View itemView) {
            super(itemView);
            this.native_ad_layout = itemView.findViewById(R.id.native_ad_layout);
            PrefManager prefManager= new PrefManager(activity);
            nativeAdLoader = new MaxNativeAdLoader( prefManager.getString("ADMIN_NATIVE_ADMOB_ID"), activity );
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoadFailed(String s, MaxError maxError) {
                    super.onNativeAdLoadFailed(s, maxError);
                    Log.v("AppLovin",maxError.getMessage());
                }

                @Override
                public void onNativeAdClicked(MaxAd maxAd) {
                    super.onNativeAdClicked(maxAd);
                    Log.v("AppLovin","onNativeAdClicked");

                }

                @Override
                public void onNativeAdExpired(MaxAd maxAd) {
                    super.onNativeAdExpired(maxAd);
                    Log.v("AppLovin","onNativeAdExpired");

                }

                @Override
                public void onNativeAdLoaded(MaxNativeAdView nativeAdView, MaxAd nativeAd) {
                    if ( loadedNativeAd != null )
                    {
                        nativeAdLoader.destroy( loadedNativeAd );
                    }

                    // Save ad for cleanup.
                    loadedNativeAd = nativeAd;

                    native_ad_layout.removeAllViews();
                    native_ad_layout.addView( nativeAdView );



                }
            });

            nativeAdLoader.loadAd();
        }

    }
    public class AdmobNativeHolder extends RecyclerView.ViewHolder {
        private final AdLoader adLoader;
        private NativeAd nativeAd;
        private FrameLayout frameLayout;

        public AdmobNativeHolder(@NonNull View itemView) {
            super(itemView);

            PrefManager prefManager= new PrefManager(activity);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.fl_adplaceholder);
           // AdLoader.Builder builder = new AdLoader.Builder(activity, "ca-app-pub-3940256099942544/2247696110");
            AdLoader.Builder builder = new AdLoader.Builder(activity, prefManager.getString("ADMIN_NATIVE_ADMOB_ID"));



            builder.forNativeAd(
                    nativeAd -> {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.

                        if (nativeAd == null) {
                            nativeAd.destroy();
                            return;
                        }


                        AdmobNativeHolder.this.nativeAd = nativeAd;
                        FrameLayout frameLayout = activity.findViewById(R.id.fl_adplaceholder);
                        NativeAdView adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, null);

                        populateNativeAdView(nativeAd, adView);
                        if(frameLayout != null){
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }

                    });

            VideoOptions videoOptions =
                    new VideoOptions.Builder().setStartMuted(true).build();

            com.google.android.gms.ads.nativead.NativeAdOptions adOptions =
                    new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

            builder.withNativeAdOptions(adOptions);

            adLoader =
                    builder
                            .withAdListener(
                                    new AdListener() {
                                        @Override
                                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                                            String error =
                                                    String.format(
                                                            "domain: %s, code: %d, message: %s",
                                                            loadAdError.getDomain(),
                                                            loadAdError.getCode(),
                                                            loadAdError.getMessage());

                                            Log.d("ADMOB_TES", error);

                                        }
                                    })
                            .build();

            adLoader.loadAd(new AdRequest.Builder().build());

        }
    }

    /**
     * Populates a {@link NativeAdView} object with data from a given {@link com.google.android.gms.ads.nativead.NativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView the view to be populated
     */
    private void populateNativeAdView(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.

                    super.onVideoEnd();
                }
            });
        } else {

        }
    }

    public void CopyQuote(Integer position){
        String text = null;
        try {
            byte[] data = Base64.decode(statusList.get(position).getTitle(), Base64.DEFAULT);
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("quote"+statusList.get(position).getId(), text);
        clipboard.setPrimaryClip(clip);
        Toasty.success(activity,activity.getResources().getString(R.string.status_copied_success)).show();
        addShare(position);
    }
    public boolean checkSUBSCRIBED(){
        PrefManager prefManager= new PrefManager(activity);
        if (!prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            return false;
        }
        return true;
    }
    public void selectOperation(Integer position,Integer code){
        if(code != null && position != null) {
            switch (code) {
                case 1:
                    CopyQuote(position);
                    break;
                case 2:
                    DownloadSocial(position, FACEBOOK_ID);
                    break;
                case 3:
                    DownloadSocial(position, MESSENGER_ID);
                    break;
                case 4:
                    DownloadSocial(position, WHATSAPP_ID);
                    break;
                case 5:
                    DownloadSocial(position, INSTAGRAM_ID);
                    break;
                case 6:
                    DownloadSocial(position, SHARE_ID);
                    break;
                case 7:
                    shareTextWith(position, SHARE_ID);
                    break;
                case 8:
                    shareTextWith(position, WHATSAPP_ID);
                    break;
            }
        }
    }

    public void DownloadSocial(Integer position, String SOCIAL_ID){
        final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();
        if (!statusList.get(position).isDownloading()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                downloadFileFromURL.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, statusList.get(position).getOriginal(), statusList.get(position).getTitle(), statusList.get(position).getExtension(), position,SOCIAL_ID);
            else
                downloadFileFromURL.execute(statusList.get(position).getOriginal(), statusList.get(position).getTitle(), statusList.get(position).getExtension(), position,SOCIAL_ID);
        }
    }
    public void Operation(Integer position,Integer code){
        PrefManager prefManager= new PrefManager(activity);
        code_selected = code;
        position_selected = position;
        if(checkSUBSCRIBED()) {
            selectOperation(position,code);
        }else{
            if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("ADMOB")) {
                requestAdmobInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                    if (admobInterstitialAd != null) {
                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                        admobInterstitialAd.show(activity);

                    }else{
                        selectOperation(position,code);
                    }
                }else{
                    selectOperation(position,code);
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
                            selectOperation(position,code);
                        }
                    } else {
                        selectOperation(position,code);
                    }
                }else{
                    selectOperation(position,code);
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
                            selectOperation(position,code);
                        }
                    } else {
                        selectOperation(position,code);
                    }
                }else{
                    selectOperation(position,code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                }
            }
            else  if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("IS")) {
                requestISInterstitial();
                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS") <= prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")) {
                    if(IronSource.isInterstitialReady()){
                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS", 0);
                        IronSource.showInterstitial();
                        selectOperation(position,code);

                    }else{
                        selectOperation(position,code);

                    }
                }else{
                    selectOperation(position,code);
                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                }

            }else{
                selectOperation(position,code);
            }
        }
    }
    private void Report(Status status, MenuItem item) {
        final PrefManager prefManager = new PrefManager(activity);

        switch (item.getItemId()){
            case R.id.report:
                Intent intent = new Intent(activity, SupportActivity.class);
                intent.putExtra("message","Hi Admin, Please check this status i think should be removed status id : "+status.getId() );
                activity.startActivity(intent);
                break;
            case R.id.report_user:
                Intent intent_user = new Intent(activity, SupportActivity.class);
                intent_user.putExtra("message","Hi Admin, Please check this user i think should be removed user id : "+status.getUserid() );
                activity.startActivity(intent_user);
                break;
            case R.id.block_user:


                prefManager.setString("user_reported_"+status.getUserid(),"TRUE");
                Toasty.warning(activity,"User : "+ status.getUser()+" has been blocked !").show();

                break;
            case R.id.hide_post:
                statusList.remove(status);
                notifyDataSetChanged();
                prefManager.setString("status_reported_"+status.getId(),"TRUE");
                Toasty.warning(activity,"this post has been hidden !").show();
                break;
        };


    }

}
