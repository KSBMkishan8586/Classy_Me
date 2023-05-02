package com.ksbm.ontu.alerts_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.URLUtil;

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
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.model.NoticeBoardModel;
import com.ksbm.ontu.databinding.ActivityNoticeBoardDetailsBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.App;
import com.ksbm.ontu.utils.Utils;

public class NoticeBoard_Details_Activity extends AppCompatActivity implements Player.EventListener{
    ActivityNoticeBoardDetailsBinding binding;
    SessionManager sessionManager;
    Context context;
    NoticeBoardModel.NoticeBoardModelResponse NoticeData;
    SimpleExoPlayer player;
    boolean is_user_stop_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_notice_board__details);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(NoticeBoard_Details_Activity.this), NoticeBoard_Details_Activity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice_board__details);
        context = NoticeBoard_Details_Activity.this;

        sessionManager = new SessionManager(context);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent() != null) {
            NoticeData = (NoticeBoardModel.NoticeBoardModelResponse) getIntent().getSerializableExtra("NoticeData");
            binding.setModel(NoticeData);

            if (NoticeData.getNoticetype().equalsIgnoreCase("alert")){
                binding.tvTitle.setText("New Updates");
            }
        }

        if (NoticeData.getFilename() == null || NoticeData.getFilename().equalsIgnoreCase("")) {
            binding.rlImage.setVisibility(View.GONE);
        }else {
            if (NoticeData.getNoticetype().equalsIgnoreCase("alert")){
                if (NoticeData.getMimetype().equalsIgnoreCase("image/png")){
                     binding.ivNoticeImage.setVisibility(View.VISIBLE);
                    binding.playerview.setVisibility(View.GONE);
                }else {
                    binding.ivNoticeImage.setVisibility(View.GONE);
                    binding.playerview.setVisibility(View.VISIBLE);
                    playExo();
                }
            }
        }


        if (NoticeData.getLink() != null && !NoticeData.getLink().equalsIgnoreCase("")) {
            binding.tvLink.setVisibility(View.VISIBLE);
        } else {
            binding.tvLink.setVisibility(View.GONE);
        }

        binding.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoticeData.getLink() != null || !NoticeData.getLink().equalsIgnoreCase("")) {
                  String link=  NoticeData.getLink();

                    if (!link.startsWith("https://") && !link.startsWith("http://")){
                        link = "http://" + link;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                }

            }
        });


    }

    private void playExo() {
        HttpProxyCacheServer proxy = App.getProxy(context);
        String proxyUrl;
        if (URLUtil.isValidUrl(NoticeData.getFilename())){
            proxyUrl = proxy.getProxyUrl(NoticeData.getFilename());
        }else {
            proxyUrl = NoticeData.getFilename();
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
       // binding.controls.setPlayer(player);//set controls
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
                    if (!player.getPlayWhenReady()) {
                        is_user_stop_video = false;
                        player.setPlayWhenReady(true);
                    } else {
                        is_user_stop_video = true;
                        player.setPlayWhenReady(false);
                    }


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

}