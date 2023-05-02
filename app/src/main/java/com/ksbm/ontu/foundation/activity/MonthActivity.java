package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityMonthBinding;
import com.ksbm.ontu.foundation.model.MonthModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class MonthActivity extends AppCompatActivity {
    ActivityMonthBinding binding;
    SessionManager sessionManager;
    Context context;
    List<MonthModel> monthModelList= new ArrayList<>();
    int question_pos = 0;
    boolean isRecording = false;
    private String DayType;
    boolean isPlayed= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_month);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_month);

        if (Constant.previous_btn){
            binding.layoutButton.tvPrevious.setVisibility(View.GONE);
        }

        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        context= MonthActivity.this;
        sessionManager = new SessionManager(this);
        binding.header.tvTitle.setText(Constant.foundation_name);

        if (getIntent()!=null){
            DayType= getIntent().getStringExtra("DayType");
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (DayType.equalsIgnoreCase("Month")){
            getMonthName();
        }else {
            getWeekName();
        }
        setQuestion();

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

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
                Speach_Record_Activity.mFileName = null;
                if (question_pos != monthModelList.size() - 1) {
                    question_pos++;
                    setQuestion();
                } else {
                    finish();
                }
            }
        });

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                if (question_pos > 0) {
                    question_pos--;
                    setQuestion();
                }
            }
        });

        binding.llButton.tvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions_record(MonthActivity.this)) {

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
                    RequestPermissions(MonthActivity.this);
                }
            }
        });

        binding.llButton.tvSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(MonthActivity.this, monthModelList.get(question_pos).getMonth_name(), true);
                ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#008000")));
                ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

            }
        });

        binding.tvMonthName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(MonthActivity.this, binding.tvMonthName.getText().toString(), false);

            }
        });

        binding.tvMonthDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(MonthActivity.this, binding.tvMonthDetails.getText().toString(), false);

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


                    Speach_Record_Activity.speakOut(MonthActivity.this, monthModelList.get(question_pos).getMonth_name(), false);
                    Utils.playRecordWithdelay(context, 2);
                    binding.llButton.tvRecordSpeak.setText("Record");
                    binding.tvUserCoin.setText("+"+FoundationLearningCoinBonus );
                   // Toast.makeText(context, ""+FoundationLearningCoinBonus, Toast.LENGTH_SHORT).show();

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


    private void getWeekName() {
        monthModelList.add(new MonthModel("Sunday", "1st Day of the Week"));
        monthModelList.add(new MonthModel("Monday", "2nd Day of the Week"));
        monthModelList.add(new MonthModel("Tuesday", "3rd Day of the Week"));
        monthModelList.add(new MonthModel("Wednesday", "4th Day of the Week"));
        monthModelList.add(new MonthModel("Thursday", "5th Day of the Week"));
        monthModelList.add(new MonthModel("Friday", "6th Day of the Week"));
        monthModelList.add(new MonthModel("Saturday", "7th Day of the Week"));

    }

    private void setQuestion() {
        isPlayed= false;
        binding.llGetCoin.setVisibility(View.INVISIBLE);

        binding.tvMonthName.setText(monthModelList.get(question_pos).getMonth_name());
        binding.tvMonthDetails.setText(monthModelList.get(question_pos).getMonth_details());
    }

    private void getMonthName() {
        monthModelList.add(new MonthModel("January", "1st Month of the Year"));
        monthModelList.add(new MonthModel("February", "2nd Month of the Year"));
        monthModelList.add(new MonthModel("March", "3rd Month of the Year"));
        monthModelList.add(new MonthModel("April", "4th Month of the Year"));
        monthModelList.add(new MonthModel("May", "5th Month of the Year"));
        monthModelList.add(new MonthModel("June", "6th Month of the Year"));
        monthModelList.add(new MonthModel("July", "7th Month of the Year"));
        monthModelList.add(new MonthModel("August", "8th Month of the Year"));
        monthModelList.add(new MonthModel("September", "9th Month of the Year"));
        monthModelList.add(new MonthModel("October", "10th Month of the Year"));
        monthModelList.add(new MonthModel("November", "11th Month of the Year"));
        monthModelList.add(new MonthModel("December", "12th Month of the Year"));

    }


}