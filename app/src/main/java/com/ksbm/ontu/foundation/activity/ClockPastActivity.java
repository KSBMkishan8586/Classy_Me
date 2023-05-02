package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityClockPastBinding;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Utils;

public class ClockPastActivity extends AppCompatActivity {

    ImageView CI1, CI2, CI3, CI4, CI5, CI6, CI7, CI8, CI9, CI10, CI11, CI12;
    TextView tv_0_clock, tv_halfpast, tv_heading;
    TextView tv_previous, tv_next, tv_title;
    RelativeLayout ll_alert;
    LinearLayout ll_free_coin;
    ActivityClockPastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_past);

        ImageView iv_alert = findViewById(R.id.iv_alert);

        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(iv_alert);

        CI1 = findViewById(R.id.CI1);
        CI2 = findViewById(R.id.CI2);
        CI3 = findViewById(R.id.CI3);
        CI4 = findViewById(R.id.CI4);
        CI5 = findViewById(R.id.CI5);
        CI6 = findViewById(R.id.CI6);
        CI7 = findViewById(R.id.CI7);
        CI8 = findViewById(R.id.CI8);
        CI9 = findViewById(R.id.CI9);
        CI10 = findViewById(R.id.CI10);
        CI11 = findViewById(R.id.CI11);
        CI12 = findViewById(R.id.CI12);
        ll_alert = (RelativeLayout) findViewById(R.id.ll_alert);
        ll_free_coin = (LinearLayout) findViewById(R.id.ll_free_coin);
        tv_previous = (TextView) findViewById(R.id.tv_previous);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_previous.setVisibility(View.INVISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setVisibility(View.INVISIBLE);
        tv_next.setText("Complete");
        tv_0_clock = findViewById(R.id.tv_0_clock);
        tv_halfpast = findViewById(R.id.tv_halfpast);
        tv_heading = findViewById(R.id.tv_heading);
        Utils.setRainbowColor(tv_0_clock);
        Utils.setRainbowColor(tv_halfpast);
        Utils.setRainbowColor(tv_heading);
        CI12.setEnabled(false);
        CI1.setEnabled(true);
        CI2.setEnabled(false);
        CI3.setEnabled(false);
        CI4.setEnabled(false);
        CI5.setEnabled(false);
        CI6.setEnabled(false);
        CI7.setEnabled(false);
        CI8.setEnabled(false);
        CI9.setEnabled(false);
        CI10.setEnabled(false);
        CI11.setEnabled(false);
        startblinkimage(CI1);


        ll_free_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Utils.screenShot(binding.fullScreen, getWindow().getContext());
                }
            }
        });



        ll_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*binding.tvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvHeader.getText().toString(), false);
            }
        });

        binding.tvHeaderTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvHeaderTask.getText().toString(), false);
            }
        });*/


        CI1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Five past, Twelve", false);
                Utils.blinkImageView(CI1, getApplicationContext());
                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(true);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI2);

            }
        });

        CI2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Ten past, Twelve", false);
                Utils.blinkImageView(CI2, getApplicationContext());
                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(true);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI3);

            }
        });

        CI3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Kwaatar past, Twelve", false);
                Utils.blinkImageView(CI3, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(true);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI4);

            }
        });

        CI4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Twenty past, Twelve", false);
                Utils.blinkImageView(CI4, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(true);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI5);

            }
        });

        CI5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Twenty Five past, Twelve", false);
                Utils.blinkImageView(CI5, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(true);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI6);

            }
        });

        CI6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Haalf past, Twelve", false);
                Utils.blinkImageView(CI6, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(true);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI7);

            }
        });

        CI7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Twenty Five minutes Too, One", false);
                Utils.blinkImageView(CI7, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(true);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI8);

            }
        });

        CI8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Twenty minutes, Too, One", false);
                Utils.blinkImageView(CI8, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(true);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI9);

            }
        });

        CI9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "15 minutes Too, One", false);
                Utils.blinkImageView(CI9, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(true);
                CI11.setEnabled(false);
                startblinkimage(CI10);

            }
        });

        CI10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "10 minutes, Too, One", false);
                Utils.blinkImageView(CI10, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(true);
                startblinkimage(CI11);

            }
        });

        CI11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Five, Too, One", false);
                Utils.blinkImageView(CI11, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(false);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(true);
                startblinkimage(CI2);

            }
        });

        CI12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Twelve, O Clock", false);
                Utils.blinkImageView(CI12, getApplicationContext());

                CI12.setEnabled(false);
                CI1.setEnabled(true);
                CI2.setEnabled(false);
                CI3.setEnabled(false);
                CI4.setEnabled(false);
                CI5.setEnabled(false);
                CI6.setEnabled(false);
                CI7.setEnabled(false);
                CI8.setEnabled(false);
                CI9.setEnabled(false);
                CI10.setEnabled(false);
                CI11.setEnabled(false);
                startblinkimage(CI1);
            }
        });

        tv_0_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Twelve, O Clock", false);

            }
        });

        tv_halfpast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), "Haalf past, Twelve", false);

            }
        });

        tv_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(getApplicationContext(), tv_heading.getText().toString(), false);

            }
        });
    }

    public void startblinkimage(ImageView view) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        view.startAnimation(animation); //to start animation
    }

}