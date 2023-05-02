package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityDateBinding;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

@SuppressLint("SetTextI18n")
public class DateActivity extends AppCompatActivity {
    ActivityDateBinding binding;
    String Date_word= "";
    Context context;
    boolean isRecording = false;
    SessionManager sessionManager;
    boolean isPlayed= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_date);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_date);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        context= DateActivity.this;
        sessionManager=new SessionManager(context);
        binding.header.tvTitle.setText(Constant.foundation_name);
        Speach_Record_Activity.mFileName = null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //first open
        binding.tvMonthName.setText(getMonth(month + 1));
        binding.tvYearName.setText(""+ year);
        binding.tvDate.setText(""+ day);

        Date_word = binding.tvDate.getText().toString() + " "+ binding.tvMonthName.getText().toString() + binding.tvYearName.getText().toString();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePickerChange(datePicker, year, month, dayOfMonth);
            }
        });

        binding.llButton.tvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions_record(DateActivity.this)) {

                    if (!isRecording) {
                        isRecording = true;
                        Speach_Record_Activity.recording_start(context);
                        binding.llButton.tvRecordSpeak.setText("Stop");
                        ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#008000")));
                        ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                        //  Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();

                    } else {
                        binding.llButton.tvRecordSpeak.setText("Record");
                        isRecording = false;
                        Speach_Record_Activity.stop_recording();
                        ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                        // Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                    }
                } else {
                    RequestPermissions(DateActivity.this);
                }
            }
        });

        binding.llButton.tvSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, Date_word, true);
                ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#008000")));
                ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

            }
        });

        binding.tvMonthName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context,  binding.tvMonthName.getText().toString(), false);

            }
        });

        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context,  binding.tvDate.getText().toString(), false);

            }
        });

        binding.tvYearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context,  binding.tvYearName.getText().toString(), false);

            }
        });

        binding.llButton.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = Speach_Record_Activity.getRecordedFileName();
                if (file_name != null) {
                   // PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);

                    ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#008000")));
                    ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));


                    Speach_Record_Activity.speakOut(context, Date_word, false);
                    Utils.playRecordWithdelay(context, 3);
                    binding.tvUserCoin.setText("+"+FoundationLearningCoinBonus );
                    //Toast.makeText(context, ""+FoundationLearningCoinBonus, Toast.LENGTH_SHORT).show();

                    if(!isPlayed){
                        CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), FoundationLearningCoinBonus, "foundation_learn", "");
                        Utils.EarnCoin(binding.llGetCoin, context, 2);
                        isPlayed= true;
                    }
                }else {
                    ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void datePickerChange(DatePicker datePicker, int year, int month, int dayOfMonth) {
        // Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
        playMusic(R.raw.tic_tic);
       // binding.tvMonthName.setText(""+ (month + 1));
        binding.tvMonthName.setText(getMonth(month + 1));
        binding.tvYearName.setText(""+ year);
        binding.tvDate.setText(""+ dayOfMonth);

        Date_word = binding.tvDate.getText().toString() + " "+ binding.tvMonthName.getText().toString() + binding.tvYearName.getText().toString();
    }

    MediaPlayer mPlayer = null;
    AudioManager.OnAudioFocusChangeListener afChangeListener;
    private final static int MAX_VOLUME = 100;
    private void playMusic(int musicFile) {
        try {
            mPlayer = MediaPlayer.create((Context) this, musicFile);

            AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // Start playback.
                mPlayer.setLooping(false);
               // final float volume = (float) (1 - (Math.log(MAX_VOLUME - 85) / Math.log(MAX_VOLUME)));
               // mPlayer.setVolume(volume, volume);
                mPlayer.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }


    @Override
    protected void onDestroy() {
        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onStop();
    }

    @Override
    protected void onPause() {

        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onPause();
    }
}