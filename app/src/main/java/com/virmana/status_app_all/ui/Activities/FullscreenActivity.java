package com.virmana.status_app_all.ui.Activities;

import android.net.Uri;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.leo.simplearcloader.SimpleArcLoader;
import com.virmana.status_app_all.R;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private PlayerView simpleExoPlayerView;
    private ExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;

    private PlayerControlView ivHideControllerButton;
    private String URL;
    private String duration;
    private SimpleArcLoader simple_arc_loader_exo;
    private String local;
    private ImageView exo_pause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras() ;
        this.URL =  bundle.getString("video");
        this.local =  bundle.getString("local");
        this.duration =  bundle.getString("duration");

        setContentView(R.layout.activity_fullscreen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"));
        window = new Timeline.Window();
        ivHideControllerButton = (PlayerControlView) findViewById(R.id.exo_controller);
        simple_arc_loader_exo = (SimpleArcLoader) findViewById(R.id.simple_arc_loader_exo);
        exo_pause = (ImageView) findViewById(R.id.exo_pause);
        ((RelativeLayout) findViewById(R.id.relative_layout_controller)).setVisibility(View.VISIBLE);

    }


    private void initializePlayer() {

        simpleExoPlayerView = (PlayerView) findViewById(R.id.video_view);
        simpleExoPlayerView.requestFocus();


        ExoTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();


        trackSelector = new DefaultTrackSelector(getApplicationContext());

       // player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player = new ExoPlayer.Builder(getApplicationContext()).setTrackSelector(trackSelector).build();

        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(shouldAutoPlay);
/*        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);*/
        Log.v("MY ONE",URL);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource;

            mediaSource  = new ProgressiveMediaSource.Factory(mediaDataSourceFactory,extractorsFactory).createMediaSource(MediaItem.fromUri(URL));

        if (local!=null){
            Log.v("this is path",local);
            Uri imageUri = FileProvider.getUriForFile(FullscreenActivity.this, FullscreenActivity.this.getApplicationContext().getPackageName() + ".provider", new File(local));
            mediaSource =  new ProgressiveMediaSource.Factory(mediaDataSourceFactory,extractorsFactory).createMediaSource(MediaItem.fromUri(imageUri));
        }


        player.seekTo(Integer.parseInt(duration));

        player.prepare(mediaSource);
        player.seekTo(Integer.parseInt(duration));

        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        player.addListener(new Player.Listener() {
            @Override
            public void onEvents(Player player, Player.Events events) {
                Player.Listener.super.onEvents(player, events);
            }
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_READY){
                    simple_arc_loader_exo.setVisibility(View.GONE);
                }
                if (playbackState == ExoPlayer.STATE_BUFFERING){
                    simple_arc_loader_exo.setVisibility(View.VISIBLE);
                    exo_pause.setVisibility(View.GONE);
                }
            }

        });
    }

    private void releasePlayer() {
        if (player != null) {
            player.seekTo(Integer.parseInt(duration));
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
