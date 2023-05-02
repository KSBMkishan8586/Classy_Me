package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.databinding.ActivityCountingBinding;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

public class Counting_Activity extends AppCompatActivity {
    ActivityCountingBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_counting);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_counting);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        context = Counting_Activity.this;
        binding.header.tvTitle.setText(Constant.foundation_name);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

        binding.iv30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Context) Counting_Activity.this, CountingStarActivity.class);
                startActivity(intent);
            }
        });

        binding.iv90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent((Context) Counting_Activity.this, Counting_Details_Activity.class);
                intent.putExtra("counting", "90");
                startActivity(intent);
            }
        });

        binding.iv110.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent((Context) Counting_Activity.this, Counting_Details_Activity.class);
                intent.putExtra("counting", "110");
                startActivity(intent);
            }
        });

        binding.iv10Lak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent((Context) Counting_Activity.this, Counting_Note_Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}