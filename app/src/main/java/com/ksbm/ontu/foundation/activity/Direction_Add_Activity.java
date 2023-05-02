package com.ksbm.ontu.foundation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityAddDirectionBinding;
import com.ksbm.ontu.databinding.ActivityDirectionBinding;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

public class Direction_Add_Activity extends AppCompatActivity {
    ActivityAddDirectionBinding binding;
    int list_count = 0;
    Context context;

    String Click = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_direction);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_add_direction);

        context = Direction_Add_Activity.this;
        binding.header.tvTitle.setText(Constant.foundation_name);



        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (Constant.previous_btn) {
            binding.layoutButton.tvPrevious.setVisibility(View.GONE);
        }

        Utils.setRainbowColor(binding.tvHeading1);
        binding.tvHeading1.setText(Utils.UnderlineText(binding.tvHeading1.getText().toString()));


        startblink(binding.ivNorthDireName);

        binding.tvHeading1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvHeading1.getText().toString(), false);
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Direction_Add_Activity.this, DirectionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.ivEightDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, "Four Cardinal Directions", false);
                Utils.ZoomInImage(binding.ivEightDireName);
            }
        });


        binding.ivNorthDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivNorthDireName);
                stopAnimation(binding.ivSouthDireName);
                stopAnimation(binding.ivEastDireName);
                stopAnimation(binding.ivWestDireName);
                Speach_Record_Activity.speakOut(context, "North", false);
            }
        });

        binding.ivSouthDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivSouthDireName);
                stopAnimation(binding.ivNorthDireName);
                stopAnimation(binding.ivEastDireName);
                stopAnimation(binding.ivWestDireName);
                Speach_Record_Activity.speakOut(context, "South", false);
            }
        });

        binding.ivEastDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivEastDireName);
                stopAnimation(binding.ivSouthDireName);
                stopAnimation(binding.ivNorthDireName);
                stopAnimation(binding.ivWestDireName);
                Speach_Record_Activity.speakOut(context, "East", false);
            }
        });

        binding.ivWestDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivWestDireName);
                stopAnimation(binding.ivSouthDireName);
                stopAnimation(binding.ivEastDireName);
                stopAnimation(binding.ivNorthDireName);
                Speach_Record_Activity.speakOut(context, "West", false);
            }
        });

    }


    public void startblink(ImageView view) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        view.startAnimation(animation); //to start animation
    }

    public void stopAnimation(View view) {
        view.clearAnimation();
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}