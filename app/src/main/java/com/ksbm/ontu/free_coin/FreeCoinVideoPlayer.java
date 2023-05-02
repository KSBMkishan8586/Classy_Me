package com.ksbm.ontu.free_coin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.ActivityVideoPlayerBinding;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Splash;
import com.ksbm.ontu.fundamental.activity.VideoPlayer;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.App;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.Utils.getVideoIdFromYoutubeUrl;
import static com.ksbm.ontu.utils.Utils.isValidYoutubeUrl;

public class FreeCoinVideoPlayer extends YouTubeBaseActivity implements Player.EventListener, YouTubePlayer.OnInitializedListener{
    String FilePath;
    ActivityVideoPlayerBinding binding;
    SessionManager sessionManager;
    Context context;
    SimpleExoPlayer player;
    String video_id, coin_amount, is_used;
    boolean FilePathOther=false;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_video_player);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player);

        context= FreeCoinVideoPlayer.this;
        sessionManager = new SessionManager(this);

        if (getIntent()!=null){
            FilePath= getIntent().getStringExtra("FilePath");
            video_id= getIntent().getStringExtra("video_id");
            coin_amount= getIntent().getStringExtra("coin_amount");
            is_used= getIntent().getStringExtra("is_used");
            FilePathOther= getIntent().getBooleanExtra("FilePathOther", false);
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        WebView webview = (WebView) findViewById(R.id.webview);
        if (FilePathOther){
            if (isValidYoutubeUrl(FilePath)){
                webview.setVisibility(View.GONE);
                binding.relExo.setVisibility(View.GONE);
                binding.relYoutubePlayer.setVisibility(View.VISIBLE);
                binding.relYoutubePlayer.initialize(Constant.DEVELOPER_KEY, this);

            }else {
                //***********webview play*****************
                webview.setVisibility(View.VISIBLE);
                binding.relExo.setVisibility(View.GONE);
                binding.relYoutubePlayer.setVisibility(View.GONE);

                webview.setWebViewClient(new WebViewClient());
                webview.getSettings().setJavaScriptEnabled(true);
                webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webview.getSettings().setPluginState(WebSettings.PluginState.ON);
                webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
                webview.setWebChromeClient(new WebChromeClient());
                webview.loadUrl(FilePath);

                if (is_used.equalsIgnoreCase("no")){
                    CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), coin_amount, "daily_video", video_id);
                    Utils.playMusic(R.raw.free_coin, context);
                }
            }

        }else {
            webview.setVisibility(View.GONE);
            binding.relExo.setVisibility(View.VISIBLE);
            playExo();

        }


    }

    private void playExo() {
        HttpProxyCacheServer proxy = App.getProxy(context);
        String proxyUrl;
        if (URLUtil.isValidUrl(FilePath)){
            proxyUrl = proxy.getProxyUrl(FilePath);
        }else {
            proxyUrl = FilePath;
        }


        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setAllocator(new DefaultAllocator(true, 16))
                .setBufferDurationsMs(1024, 1 * 1024, 500, 1024)
                .setTargetBufferBytes(-1)
                .setPrioritizeTimeOverSizeThresholds(true)
                .createDefaultLoadControl();

        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "Ontu"));

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(proxyUrl));

        player.prepare(videoSource);

        // player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.setRepeatMode(Player.REPEAT_MODE_OFF);
        player.addListener(this);

        binding.playerview.setPlayer(player);
        binding.controls.setPlayer(player);//set controls
        binding.playerview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        player.seekTo(1);

        binding.playerview.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    super.onFling(e1, e2, velocityX, velocityY);
                    float deltaX = e1.getX() - e2.getX();
                    float deltaXAbs = Math.abs(deltaX);
                    // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
                    if ((deltaXAbs > 100) && (deltaXAbs < 1000)) {
                        if (deltaX > 0) {
                            //  OpenProfile(item, true);
                        }
                    }


                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    super.onSingleTapUp(e);
//                    if (!player.getPlayWhenReady()) {
//                        is_user_stop_video = false;
//                        privious_player.setPlayWhenReady(true);
//                    } else {
//                        is_user_stop_video = true;
//                        privious_player.setPlayWhenReady(false);
//                    }


                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    // Show_video_option(item);

                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {

//                    if (!player.getPlayWhenReady()) {
//                        is_user_stop_video = false;
//                        privious_player.setPlayWhenReady(true);
//                    }


                    return super.onDoubleTap(e);

                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int state) {

        if (state == SimpleExoPlayer.STATE_ENDED) {
            //player back ended
            if (is_used.equalsIgnoreCase("no")){
                CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), coin_amount, "daily_video", video_id);
                Utils.playMusic(R.raw.free_coin, context);
                finish();
            }
        }
    }

    // this is lifecyle of the Activity which is importent for play,pause video or relaese the player
    @Override
    public void onResume() {
        super.onResume();
        if((player!=null) ){
            player.setPlayWhenReady(true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if(player!=null){
            player.setPlayWhenReady(false);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if(player!=null){
            player.setPlayWhenReady(false);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player!=null){
            player.release();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format("error_player", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(getVideoIdFromYoutubeUrl(FilePath));

            // Hiding player controls
            // player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constant.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.rel_youtube_player);
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
            //  finish();
            if (is_used.equalsIgnoreCase("no")){
                CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), coin_amount, "daily_video", video_id);
                Utils.playMusic(R.raw.free_coin, context);
                finish();
            }
        }

        @Override
        public void onVideoStarted() {
        }
    };
}
