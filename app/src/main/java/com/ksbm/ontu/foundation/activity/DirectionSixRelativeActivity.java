package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityDirectionSixRelativeBinding;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

public class DirectionSixRelativeActivity extends AppCompatActivity {
    ActivityDirectionSixRelativeBinding binding;
    Context context;
    String Click = "U";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_direction_six_relative);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_direction_six_relative);

        Utils.setRainbowColor(binding.tvHeading1);

        context = DirectionSixRelativeActivity.this;
        binding.header.tvTitle.setText(Constant.foundation_name);

        // setImage();

        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        startblink(binding.ivUpDireName);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
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
                Intent intent = new Intent(context, DirectionSwipeNewActivity.class);
                intent.putExtra("ComeFrom", "");
                startActivity(intent);
                finish();
            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DirectionSixRelativeActivity.this, DirectionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.ivSixRelativeDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, "Six Relative Directions", false);
                Utils.ZoomInImage(binding.ivSixRelativeDireName);
            }
        });

        binding.ivUpDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivUpDireName);
                Speach_Record_Activity.speakOut(context, "Up", false);
                stopAnimation(binding.ivLeftDireName);
                stopAnimation(binding.ivForwordDireName);
                stopAnimation(binding.ivBackwordDireName);
                stopAnimation(binding.ivRightDireName);
                stopAnimation(binding.ivDownDireName);
            }
        });
        binding.ivRightDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivRightDireName);
                Speach_Record_Activity.speakOut(context, "Right", false);
                stopAnimation(binding.ivLeftDireName);
                stopAnimation(binding.ivForwordDireName);
                stopAnimation(binding.ivBackwordDireName);
                stopAnimation(binding.ivDownDireName);
                stopAnimation(binding.ivUpDireName);
            }
        });
        binding.ivBackwordDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivBackwordDireName);
                Speach_Record_Activity.speakOut(context, "Backword", false);
                stopAnimation(binding.ivLeftDireName);
                stopAnimation(binding.ivForwordDireName);
                stopAnimation(binding.ivDownDireName);
                stopAnimation(binding.ivRightDireName);
                stopAnimation(binding.ivUpDireName);

            }
        });
        binding.ivForwordDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivForwordDireName);
                stopAnimation(binding.ivLeftDireName);
                stopAnimation(binding.ivDownDireName);
                stopAnimation(binding.ivBackwordDireName);
                stopAnimation(binding.ivRightDireName);
                stopAnimation(binding.ivUpDireName);
                Speach_Record_Activity.speakOut(context, "Forword", false);

            }
        });
        binding.ivLeftDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivLeftDireName);
                stopAnimation(binding.ivDownDireName);
                stopAnimation(binding.ivForwordDireName);
                stopAnimation(binding.ivBackwordDireName);
                stopAnimation(binding.ivRightDireName);
                stopAnimation(binding.ivUpDireName);
                Speach_Record_Activity.speakOut(context, "Left", false);

            }
        });
        binding.ivDownDireName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startblink(binding.ivDownDireName);
                stopAnimation(binding.ivLeftDireName);
                stopAnimation(binding.ivForwordDireName);
                stopAnimation(binding.ivBackwordDireName);
                stopAnimation(binding.ivRightDireName);
                stopAnimation(binding.ivUpDireName);
                Speach_Record_Activity.speakOut(context, "Down", false);

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
        Intent intent = new Intent(DirectionSixRelativeActivity.this, DirectionActivity.class);
        startActivity(intent);
        finish();
    }
}
