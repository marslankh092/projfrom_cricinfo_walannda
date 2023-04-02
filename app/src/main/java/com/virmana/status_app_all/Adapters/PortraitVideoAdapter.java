package com.virmana.status_app_all.Adapters;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.applovin.mediation.MaxAd;
import com.applovin.sdk.AppLovinSdkUtils;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxAdListener;;

import com.squareup.picasso.Picasso;
import com.virmana.status_app_all.Provider.PrefManager;
import com.virmana.status_app_all.R;
import com.virmana.status_app_all.model.Status;
import com.virmana.status_app_all.ui.Activities.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PortraitVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Status> statusList =new ArrayList<>();
    private Activity activity;

    public PortraitVideoAdapter(List<Status> statusList, Activity activity) {
        this.statusList = statusList;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1: {
                View v1 = inflater.inflate(R.layout.item_portrait_video, null);
                viewHolder = new PortraitVideoHolder(v1);
                break;
            }
            case 2: {
                View v2 = inflater.inflate(R.layout.item_portrait_video_full, null);
                viewHolder = new PortraitVideoHolderFull(v2);
                break;
            }
            case 11: {
                View v11 = inflater.inflate(R.layout.item_admob_native_ads, parent, false);
                viewHolder = new AdmobNativeHolder(v11);
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
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (statusList.get(position).getViewType()){
            case 1:
                PortraitVideoHolder portraitVideoHolder = (PortraitVideoHolder) holder;
                if (statusList.get(position).getReview()){
                    portraitVideoHolder.relative_layout_item_video_review.setVisibility(View.VISIBLE);
                }else {
                    portraitVideoHolder.relative_layout_item_video_review.setVisibility(View.GONE);
                }
                Picasso.get().load(statusList.get(position).getThumbnail()).into(portraitVideoHolder.image_view_item_category_status);
                Picasso.get().load(statusList.get(position).getUserimage()).into(portraitVideoHolder.circle_image_view_item_gif_user);
                portraitVideoHolder.text_view_item_gif_name_user.setText(statusList.get(position).getUser());
                portraitVideoHolder.image_view_item_category_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,PlayerActivity.class);
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

                break;
            case 2:
                PortraitVideoHolderFull portraitVideoHolderfull = (PortraitVideoHolderFull) holder;
                if (statusList.get(position).getReview()){
                    portraitVideoHolderfull.relative_layout_item_video_review.setVisibility(View.VISIBLE);
                }else {
                    portraitVideoHolderfull.relative_layout_item_video_review.setVisibility(View.GONE);
                }
                Picasso.get().load(statusList.get(position).getThumbnail()).into(portraitVideoHolderfull.image_view_item_category_status);
                Picasso.get().load(statusList.get(position).getUserimage()).into(portraitVideoHolderfull.circle_image_view_item_gif_user);
                portraitVideoHolderfull.text_view_item_gif_name_user.setText(statusList.get(position).getUser());
                portraitVideoHolderfull.image_view_item_category_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(activity,PlayerActivity.class);
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

                break;
        }




    }

    public static class PortraitVideoHolder extends RecyclerView.ViewHolder {
        private final TextView text_view_item_gif_name_user;
        private ImageView image_view_item_category_status;
        private AppCompatImageView circle_image_view_item_gif_user;
        private final RelativeLayout relative_layout_item_video_review;

        public PortraitVideoHolder(View view) {
            super(view);
            this.relative_layout_item_video_review = (RelativeLayout) view.findViewById(R.id.relative_layout_item_video_review);

            this.image_view_item_category_status = (ImageView) view.findViewById(R.id.image_view_item_category_status);
            this.circle_image_view_item_gif_user = (AppCompatImageView) view.findViewById(R.id.circle_image_view_item_gif_user);
            this.text_view_item_gif_name_user = (TextView) view.findViewById(R.id.text_view_item_gif_name_user);
        }
    }
    public static class PortraitVideoHolderFull extends RecyclerView.ViewHolder {
        private final TextView text_view_item_gif_name_user;
        private final RelativeLayout relative_layout_item_video_review;
        private ImageView image_view_item_category_status;
        private AppCompatImageView circle_image_view_item_gif_user;

        public PortraitVideoHolderFull(View view) {
            super(view);
            this.relative_layout_item_video_review = (RelativeLayout) view.findViewById(R.id.relative_layout_item_video_review);
            this.image_view_item_category_status = (ImageView) view.findViewById(R.id.image_view_item_category_status);
            this.circle_image_view_item_gif_user = (AppCompatImageView) view.findViewById(R.id.circle_image_view_item_gif_user);
            this.text_view_item_gif_name_user = (TextView) view.findViewById(R.id.text_view_item_gif_name_user);
        }
    }
    @Override
    public int getItemCount() {
        return statusList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  statusList.get(position).getViewType();
    }
    public class AdmobNativeHolder extends RecyclerView.ViewHolder {
        private final AdLoader adLoader;
        private com.google.android.gms.ads.nativead.NativeAd nativeAd;
        private FrameLayout frameLayout;

        public AdmobNativeHolder(@NonNull View itemView) {
            super(itemView);

            PrefManager prefManager= new PrefManager(activity);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.fl_adplaceholder);
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

}
