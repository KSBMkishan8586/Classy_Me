package com.ksbm.ontu.practice_offline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityOfflineQuizBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.practice_offline.OfflineQuizScoreDB;
import com.ksbm.ontu.practice_offline.model.OfflineLevelList;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mTextToSpeech;
import static com.ksbm.ontu.utils.Constant.IS_PLAYED;
import static com.ksbm.ontu.utils.Constant.LEVEL_ID;
import static com.ksbm.ontu.utils.Constant.QUIZ_COIN;
import static com.ksbm.ontu.utils.Constant.QUIZ_ID;
import static com.ksbm.ontu.utils.Constant.STAGE_ID;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;
import static com.ksbm.ontu.utils.Utils.getVideoIdFromYoutubeUrl;

import org.json.JSONObject;

public class OfflineQuizActivity extends YouTubeBaseActivity {
    ActivityOfflineQuizBinding binding;
    SessionManager sessionManager;
    Context context;
    List<OfflineLevelList.LevelQuiz> levelQuizData = new ArrayList<>();
    String level_name, FilePath;
    int question_pos = 0;
    OfflineQuizScoreDB offlineQuizScoreDB;
    boolean isRecording = false;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    SimpleExoPlayer player;
    boolean selfcheck = false;

    boolean back = false;


    YouTubePlayer players;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(OfflineQuizActivity.this), OfflineQuizActivity.this);
        //setContentView(R.layout.activity_offline_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offline_quiz);
        sessionManager = new SessionManager(this);
        context = this;
        offlineQuizScoreDB = new OfflineQuizScoreDB(context);

        if (getIntent() != null) {
            levelQuizData = getIntent().getParcelableArrayListExtra("LevelQuizData");
            level_name = getIntent().getStringExtra("level_name");
            binding.tvHeader.setText("Level " + level_name);

        }


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setQuestion();

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                if (question_pos != levelQuizData.size() - 1) {
                    binding.tvPrevious.setVisibility(View.VISIBLE);
//                    if (selfcheck){
                    question_pos++;
                    //binding.youtubePlayerView.release();
                    setQuestion();
//                    }else {
//                        Toast.makeText(getApplicationContext(), "Please Complete Record and Self Check", Toast.LENGTH_SHORT).show();
//                    }

                } else {


                    if (selfcheck) {
                        AllLeveldone(levelQuizData.get(question_pos).getStageId(), levelQuizData.get(question_pos).getLevelId());

                    } else {

                        finish();
                    }


                    //finish();
                }
            }
        });

        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                if (question_pos > 0) {
                    question_pos--;
                    setQuestion();
                    if (question_pos == 0) {
                        binding.tvPrevious.setVisibility(View.GONE);
                    }
                }
            }
        });

        binding.tvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (CheckPermissions_record(OfflineQuizActivity.this)) {

                    if (!isRecording) {
                        isRecording = true;
                        Speach_Record_Activity.recording_start(context);
                        binding.tvRecordSpeak.setText("Stop");
                        binding.mic1.setVisibility(View.VISIBLE);
                        binding.mic.setVisibility(View.GONE);
                        binding.speaker.setVisibility(View.VISIBLE);
                        binding.speaker1.setVisibility(View.GONE);
                        binding.self.setVisibility(View.VISIBLE);
                        binding.self1.setVisibility(View.GONE);

                        //  Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();

                    } else {
                        binding.mic.setVisibility(View.VISIBLE);
                        binding.mic1.setVisibility(View.GONE);
                        binding.speaker.setVisibility(View.VISIBLE);
                        binding.speaker1.setVisibility(View.GONE);
                        binding.self.setVisibility(View.VISIBLE);
                        binding.self1.setVisibility(View.GONE);
                        binding.tvRecordSpeak.setText("Record");
                        isRecording = false;
                        Speach_Record_Activity.stop_recording();
                        // Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                    }
                } else {
                    RequestPermissions(OfflineQuizActivity.this);
                }
            }
        });

        binding.tvSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(OfflineQuizActivity.this, levelQuizData.get(question_pos).getQuestionEn(), true);

                binding.mic.setVisibility(View.VISIBLE);
                binding.mic1.setVisibility(View.GONE);
                binding.speaker1.setVisibility(View.VISIBLE);
                binding.speaker.setVisibility(View.GONE);
                binding.self.setVisibility(View.VISIBLE);
                binding.self1.setVisibility(View.GONE);
            }
        });

        binding.tvQuesNameEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(OfflineQuizActivity.this, levelQuizData.get(question_pos).getQuestionEn(), false);

            }
        });

        binding.tvQuesNameHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(OfflineQuizActivity.this, levelQuizData.get(question_pos).getQuestionHi(), false);

            }
        });

        binding.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String file_name = Speach_Record_Activity.getRecordedFileName();
                if (file_name != null) {
                    binding.mic.setVisibility(View.VISIBLE);
                    binding.mic1.setVisibility(View.GONE);
                    binding.speaker.setVisibility(View.VISIBLE);
                    binding.speaker1.setVisibility(View.GONE);
                    binding.self1.setVisibility(View.VISIBLE);
                    binding.self.setVisibility(View.GONE);
                    //PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);

                    //play_text to speech
                    //  Speach_Record_Activity.init_tts(OfflineQuizActivity.this);
                    // Speach_Record_Activity.speakOut(OfflineQuizActivity.this, levelQuizData.get(question_pos).getQuestionEn(), false);
                    Utils.playRecordWithdelay(context, 0);
                    Utils.EarnCoin(binding.llGetCoin, context, 0);
                    binding.tvRecordSpeak.setText("Record");
                    //binding.llGetCoin.setVisibility(View.VISIBLE);
                    selfcheck = true;
                    binding.tvUserCoin.setText("+" + levelQuizData.get(question_pos).getCoinPerQuestion());

                    HashMap<String, String> map = new HashMap<>();
                    if (!offlineQuizScoreDB.isInTable(levelQuizData.get(question_pos).getQuestionId())) {
                        // map.put(UserId, sessionManager.getUser().getUserid());
                        map.put(STAGE_ID, levelQuizData.get(question_pos).getStageId());
                        map.put(LEVEL_ID, levelQuizData.get(question_pos).getLevelId());
                        map.put(QUIZ_COIN, levelQuizData.get(question_pos).getCoinPerQuestion());
                        map.put(IS_PLAYED, String.valueOf(true));
                        map.put(QUIZ_ID, levelQuizData.get(question_pos).getQuestionId());


                        offlineQuizScoreDB.save_score(map);

                        Selfcheckdone(levelQuizData.get(question_pos).getQuestionId(), levelQuizData.get(question_pos).getLevelId());

                    }
                } else {
                    Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                }

//                if (offlineQuizScoreDB != null) {
//                    Log.e("ofline_score_size", "" + offlineQuizScoreDB.getItemCount());
//                }

            }
        });

    }

    private void setQuestion() {

        binding.relYoutubePlayer.destroyDrawingCache();

        binding.mic.setVisibility(View.VISIBLE);
        binding.mic1.setVisibility(View.GONE);
        binding.speaker.setVisibility(View.VISIBLE);
        binding.speaker1.setVisibility(View.GONE);
        binding.self.setVisibility(View.VISIBLE);
        binding.self1.setVisibility(View.GONE);
        Speach_Record_Activity.speakOut(OfflineQuizActivity.this, levelQuizData.get(question_pos).getQuestionEn(), false);
        Speach_Record_Activity.mFileName = null;
        //selfcheck=false;
        binding.llGetCoin.setVisibility(View.INVISIBLE);
        binding.tvQuesNameEn.setText(levelQuizData.get(question_pos).getQuestionEn());
        binding.tvQuesNameHi.setText(levelQuizData.get(question_pos).getQuestionHi());


        if (levelQuizData.get(question_pos).getType().equalsIgnoreCase("image")) {
            binding.imgqz.setVisibility(View.VISIBLE);
            binding.relExo.setVisibility(View.GONE);
            binding.youtubePlayerView.setVisibility(View.GONE);
            binding.relYoutubePlayer.setVisibility(View.GONE);


            if (levelQuizData.get(question_pos).getMedia_url().equalsIgnoreCase("http:\\/\\/classyme.org\\/uploads\\/offline_mode\\/question\\/")) {


                Glide.with(context)
                        .load(levelQuizData.get(question_pos).getMedia_url())
                        .into(binding.imgqz);

            } else {
                Glide.with(context)
                        .load(levelQuizData.get(question_pos).getMedia_url())
                        .into(binding.imgqz);
            }


        } else {

            WebView webview = (WebView) findViewById(R.id.webview);
            webview.setVisibility(View.GONE);


            if (levelQuizData.get(question_pos).getType().equalsIgnoreCase("youtube")) {

                System.out.println("you_url" + Utils.getVideoIdFromYoutubeUrl(levelQuizData.get(question_pos).getMedia_url()));

                binding.imgqz.setVisibility(View.GONE);
                binding.relExo.setVisibility(View.GONE);
                binding.youtubePlayerView.setVisibility(View.GONE);

                binding.relYoutubePlayer.setVisibility(View.VISIBLE);

                init(Utils.getVideoIdFromYoutubeUrl(levelQuizData.get(question_pos).getMedia_url()));

                // binding.relYoutubePlayer.initialize(Constant.DEVELOPER_KEY, OfflineQuizActivity.this);
//                binding.youtubePlayerView.getEnableAutomaticInitialization();
//                binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady(YouTubePlayer youTubePlayer) {
//                        String word = levelQuizData.get(question_pos).getMedia_url();
//                        String remove = "yt:video:";
//                        String videoId = "CjWrfkvRwJY";  //"at-T2PJQgOg";
//                        youTubePlayer.loadVideo(Utils.getVideoIdFromYoutubeUrl(word),0);
//                        //youTubePlayer.pause();
//                        youTubePlayer.play();
//
//                        //start youtube player activity by passing selected video id via intent
////                Intent intent=new Intent(context,YoutubePlayerActivity.class);
////                intent.putExtra("video_id", removeWords(word, remove));
////                context.startActivity(intent);
//
//                    }
//                });
            } else {
                Glide.with(context)
                        .load(levelQuizData.get(question_pos).getMedia_url())
                        .into(binding.imgqz);
                binding.imgqz.setVisibility(View.VISIBLE);
                binding.relExo.setVisibility(View.GONE);
                binding.youtubePlayerView.setVisibility(View.GONE);
                binding.relYoutubePlayer.setVisibility(View.GONE);
            }


        }
    }


    private void init(final String VIdeoId) {


        if (players != null) {
            try {
                players.pause();
                players.loadVideo(VIdeoId);
                //players.release();
                binding.relYoutubePlayer.destroyDrawingCache();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {

            binding.relYoutubePlayer.initialize(Constant.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                    players = youTubePlayer;
                    if (!b) {
                        try {
                            youTubePlayer.loadVideo(VIdeoId);
                            youTubePlayer.play();
                            players.play();
//                        youTubePlayer.isPlaying();
//                        // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Somrthing Went Wrong with this video ", Toast.LENGTH_SHORT).show();

                        }

                    }

                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    if (youTubeInitializationResult.isUserRecoverableError()) {
                        //youTubeInitializationResult.getErrorDialog(OfflineQuizActivity.this, RECOVERY_REQUEST).show();
                    } else {

                        // Toast.makeText(OfflineQuizActivity.this, youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public static String removeWords(String word, String remove) {
        return word.replace(remove, "");
    }

//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
//        if (errorReason.isUserRecoverableError()) {
//            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
//        } else {
//            String errorMessage = String.format("error_player", errorReason.toString());
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
//        player.setPlayerStateChangeListener(playerStateChangeListener);
//        player.setPlaybackEventListener(playbackEventListener);
//        if (!wasRestored) {
//            // loadVideo() will auto play video
//            // Use cueVideo() method, if you don't want to play it automatically
//            player.cueVideo(getVideoIdFromYoutubeUrl(levelQuizData.get(question_pos).getMedia_url()));
//            player.play();
//            //  player.cueVideo(getVideoIdFromYoutubeUrl(FilePath));
//
//            // Hiding player controls
//            // player.setPlayerStyle(PlayerStyle.CHROMELESS);
//        }
//    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onBackPressed() {
        String title = getResources().getString(R.string.app_name);
        String msg = "We suggest you to complete this level and then go back. Are you sure, you want to go back?";
        SweetAlt.normalDialog(this, title, msg, true, true, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                if (selfcheck) {
                    back = true;
                    AllLeveldone(levelQuizData.get(question_pos).getStageId(), levelQuizData.get(question_pos).getLevelId());

                } else {

                    finish();
                }

                //finish();
            }
        });
    }

    //  WHEN ACTIVITY IS PAUSED
    @Override
    public void onPause() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
        }
        super.onPause();
    }

    //  WHEN ACTIVITY IS DESTROYED
    @Override
    public void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            // mTextToSpeech.shutdown();
        }
        binding.youtubePlayerView.release();
        // process_tts.dismiss();
        super.onDestroy();
    }


    private void AllLeveldone(String StageId, String lid) {
        final ProgressDialog progressDialog = new ProgressDialog(OfflineQuizActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        System.out.println("Animesh -- "+lid+" -- "+sessionManager.getUser().getUserid()+" --- "+StageId);
        apiInterface.GetLeveldone(StageId, sessionManager.getUser().getUserid(), lid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        //Handle logic
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response));
                            System.out.println("product" + response.toString());
                            progressDialog.dismiss();
                            //Log.e("result_my_test", "" + response.getStage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getInt("status") == 1) {

                                if (back) {
                                    finish();
                                } else {
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    //JSONObject jsonObject1=new JSONObject(new Gson().toJson(jsonObject.get("data")));
                                    if (jsonObject1.getInt("status") == 1) {

                                        showSlider();
                                      /*  SweetAlertDialog swt = new SweetAlertDialog(OfflineQuizActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Memory Quiz")
                                                .setContentText("YOU ARE GOING GREAT! Now let's check your Memory Retention")
                                                .setConfirmText("Play")

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                    }
                                                });
                                        swt.getWindow().setLayout(800, 400);
                                        swt.show();*/

                                    } else {
                                        finish();
                                    }

                                }


                            } else {
                                //SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

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
                        progressDialog.dismiss();
                    }
                });

    }


    public void showSlider() {
        final Dialog dialog = new Dialog(OfflineQuizActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.memory_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView play = dialog.findViewById(R.id.send_now);
        Button close_button = dialog.findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MemoryMapQuiz.class);
                intent.putExtra("stage_id", levelQuizData.get(question_pos).getStageId());
                intent.putExtra("level_id", levelQuizData.get(question_pos).getLevelId());
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void Selfcheckdone(String Qid, String lid) {
//        final ProgressDialog progressDialog = new ProgressDialog(OfflineQuizActivity.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        System.out.println(lid+"  --  Animesh --- "+Qid+" --- "+sessionManager.getUser().getUserid());
        apiInterface.GetSelfcheckdone(Qid, sessionManager.getUser().getUserid(), lid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            //progressDialog.dismiss();
                            Log.e("result_my_test", "" + response);
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
//                            if (response.getStatus() == 1) {
//
//
//
//                            } else {
//                                //SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());
//
//                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }

                        //  binding.swipeToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //progressDialog.dismiss();
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
                        //progressDialog.dismiss();
                    }
                });

    }


}
