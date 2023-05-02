package com.ksbm.ontu.personality_dev;

import static com.ksbm.ontu.utils.Utils.getVideoIdFromYoutubeUrl;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
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
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityPersonalityVideoPlayerBinding;
import com.ksbm.ontu.main_screen.WebViewActivity;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.personality_dev.model.PersonalityMCQTest;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.App;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class PersonalityVideoPlayer extends YouTubeBaseActivity implements Player.EventListener, YouTubePlayer.OnInitializedListener {
    String FilePath, Video_type, Player_type, Drive_Link, Video_Id, Category_Id, ComeFrom, FirstOpen, Duration;
    ActivityPersonalityVideoPlayerBinding binding;
    SessionManager sessionManager;
    Context context;
    SimpleExoPlayer player;
    CountDownTimer counter = null;
    Handler handler;
    String backmessage = "Are you sure want to back video";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    // YouTube player view
    // private YouTubePlayerView youTubeView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_video_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personality_video_player);

        context = PersonalityVideoPlayer.this;
        sessionManager = new SessionManager(this);

        if (getIntent() != null) {
            FilePath = getIntent().getStringExtra("FilePath");
            Video_type = getIntent().getStringExtra("Video_type");
            Player_type = getIntent().getStringExtra("Player_type");
            Drive_Link = getIntent().getStringExtra("Drive_Link");
            Video_Id = getIntent().getStringExtra("Video_Id");
            Category_Id = getIntent().getStringExtra("Category_Id");
            ComeFrom = getIntent().getStringExtra("ComeFrom");
            FirstOpen = getIntent().getStringExtra("first_open");
            Duration = getIntent().getStringExtra("Duration");
        }

        if (FirstOpen.equalsIgnoreCase("2")) {
            if (Drive_Link != null && !Drive_Link.isEmpty()) {
                OpenDriveFormLink();
            }
        } else if (FirstOpen.equalsIgnoreCase("1")) {

        }


        if (!ComeFrom.equalsIgnoreCase("PD")) {
            binding.tvHeader.setText("Situation");
        } else {
            binding.tvHeader.setTextSize(12f);
            binding.tvHeader.setText("Watch Video & Earn Coin");
        }


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        WebView webview = (WebView) findViewById(R.id.webview);
        if (Video_type.equalsIgnoreCase("Youtube")) {
            if (Player_type.equalsIgnoreCase("situation")) {
                backmessage = "Are you sure want to back";
                webview.getSettings().setJavaScriptEnabled(true);
                webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
//                        pDialog.show();
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
//                        pDialog.dismiss();
                    }
                });
                String pdf = FilePath;
                webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
                webview.setVisibility(View.VISIBLE);
                binding.relYoutubePlayer.setVisibility(View.GONE);
                binding.relExo.setVisibility(View.GONE);

            } else if (Player_type.equalsIgnoreCase("youtube")) {
                webview.setVisibility(View.GONE);
                binding.relExo.setVisibility(View.GONE);
                binding.relYoutubePlayer.setVisibility(View.VISIBLE);
                binding.relYoutubePlayer.initialize(Constant.DEVELOPER_KEY, this);
//                int duration = Integer.parseInt(Duration);
                System.out.println("Animesh Mishra2");
            } else {
                //***********webview play*****************
                webview.setVisibility(View.VISIBLE);
                binding.relYoutubePlayer.setVisibility(View.GONE);
                binding.relExo.setVisibility(View.GONE);
                webview.setWebViewClient(new WebViewClient());
                webview.getSettings().setJavaScriptEnabled(true);
                webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webview.getSettings().setPluginState(WebSettings.PluginState.ON);
                webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
                webview.setWebChromeClient(new WebChromeClient());
                webview.loadUrl(FilePath);
                int duration = Integer.parseInt(Duration);
                System.out.println("Animesh Mishra " + duration);
                counter = new CountDownTimer(duration, 1000) {
                    public void onTick(long millisUntilFinished) {
                        //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                        if (millisUntilFinished / 1000 == 2) {
                            getBonusCoin(sessionManager.getUser().getUserid(), Video_Id, Category_Id);
                        }
                    }

                    public void onFinish() {

                    }

                }.start();


            }

        } else if (Video_type.equalsIgnoreCase("Local File")) {
            webview.setVisibility(View.GONE);
            binding.relExo.setVisibility(View.VISIBLE);
            binding.relYoutubePlayer.setVisibility(View.GONE);

            playExo();

        } else {
            webview.setVisibility(View.VISIBLE);
            binding.relYoutubePlayer.setVisibility(View.GONE);
            binding.relExo.setVisibility(View.GONE);

            webview.setWebViewClient(new WebViewClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview.getSettings().setPluginState(WebSettings.PluginState.ON);
            webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
            webview.setWebChromeClient(new WebChromeClient());
            webview.loadUrl(FilePath);
        }

        //***open MCQ Test
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!PersonalityVideoPlayer.this.isFinishing()) {
                    // OpenMCQTest(Video_Id, sessionManager.getLanguageid());
                }

            }
        }, 1000 * 60);


    }


    @SuppressLint("CheckResult")
    private void OpenMCQTest(String video_id, String languageId) {
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetMCQTest(languageId, video_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PersonalityMCQTest>() {
                    @Override
                    public void onNext(PersonalityMCQTest response) {
                        //Handle logic
                        try {
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                if (response.getResponse() != null && response.getResponse().size() > 0) {
                                    Intent intent = new Intent(PersonalityVideoPlayer.this, PersonalityQuizDialog.class);
                                    intent.putParcelableArrayListExtra("QuizData", (ArrayList<? extends Parcelable>) response.getResponse());
                                    intent.putExtra("video_id", video_id);
                                    intent.putExtra("Category_Id", Category_Id);
                                    startActivityForResult(intent, 200);
                                }


                            } else {
                                // SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        // progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        //  progressDialog.dismiss();
                    }
                });

    }


    private void playExo() {
        HttpProxyCacheServer proxy = App.getProxy(context);
        String proxyUrl;
        if (URLUtil.isValidUrl(FilePath)) {
            proxyUrl = proxy.getProxyUrl(FilePath);
        } else {
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
        player.addListener(this);

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

            getBonusCoin(sessionManager.getUser().getUserid(), Video_Id, Category_Id);
            OpenMCQTest(Video_Id, sessionManager.getLanguageid());
            // OpenDriveFormLink();
        }
    }

    // this is lifecyle of the Activity which is importent for play,pause video or relaese the player
    @Override
    public void onResume() {
        super.onResume();
        if ((player != null)) {
            player.setPlayWhenReady(true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }

        try {
            if (counter != null) {
                counter.cancel();
            }
        } catch (Exception e) {
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            System.out.println("MISHH " + errorReason);
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
            System.out.println("MISSH " + wasRestored + "---");
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(getVideoIdFromYoutubeUrl(FilePath));
            //  player.cueVideo(getVideoIdFromYoutubeUrl(FilePath));

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
        if (requestCode == 200 && resultCode == RESULT_OK) {
            getBonusCoin(sessionManager.getUser().getUserid(), Video_Id, Category_Id);
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
            //  finish();+
            OpenMCQTest(Video_Id, sessionManager.getLanguageid());

            // OpenDriveFormLink();
        }

        @Override
        public void onVideoStarted() {
        }
    };

    @SuppressLint("CheckResult")
    private void getBonusCoin(String userid, String video_id, String category_id) {
        final ProgressDialog progressDialog = new ProgressDialog(PersonalityVideoPlayer.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetPersonalityVideoEarning(userid, video_id, category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
//                            Log.e("result_my_test", "" + response.getMessage());

                            if (response.getStatus() == 1) {
                                showSlider(getIntent().getStringExtra("Coin"));
                                //Toast.makeText(PersonalityVideoPlayer.this, getIntent().getStringExtra("Coin") + " bonus coins added successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                // SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);
                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }
                        //  binding.swipeToRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());


                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        String title = getResources().getString(R.string.app_name);
        String msg = backmessage;
        SweetAlt.normalDialog(this, title, msg, true, true, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();

                try {
                    if (counter != null) {
                        counter.cancel();
                    }
                } catch (Exception e) {
                }

                if (Drive_Link != null && !Drive_Link.isEmpty() && FirstOpen.equalsIgnoreCase("1")) {
                    OpenDriveFormLinkafter();
                } else {
                    finish();
                }
            }
        });
    }

    private void OpenDriveFormLink() {
        String title = context.getResources().getString(R.string.app_name);
        String msg = "Please fill Survey form for more improvement";
        SweetAlt.normalDialogFinish(PersonalityVideoPlayer.this, title, msg, false, true, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                if (Drive_Link != null && !Drive_Link.isEmpty()) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("Drive_Link", Drive_Link);
                    context.startActivity(intent);
                    // }else {
                    //finish();
                }

            }
        });
    }

    private void OpenDriveFormLinkafter() {
        String title = context.getResources().getString(R.string.app_name);
        String msg = "Please fill Survey form for more improvement";
        SweetAlt.normalDialogFinish(PersonalityVideoPlayer.this, title, msg, false, true, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                if (Drive_Link != null && !Drive_Link.isEmpty()) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("Drive_Link", Drive_Link);
                    context.startActivity(intent);
                    finish();
                    // }else {
                    //finish();
                }

            }
        });
    }

    public void showSlider(String coin) {
        final Dialog dialog = new Dialog(PersonalityVideoPlayer.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bonus_coin_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView play = dialog.findViewById(R.id.send_now);
        TextView message = dialog.findViewById(R.id.message);
        message.setText(coin + " Bonus coins added successfully.\nPlease watch more videos to get bonus coins.");
        Button close_button = dialog.findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }


}