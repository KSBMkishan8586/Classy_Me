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
import com.ksbm.ontu.databinding.ActivityCurrencyLearnBinding;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

public class CurrencyLearnActivity extends AppCompatActivity {
    ActivityCurrencyLearnBinding binding;
    SessionManager sessionManager;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_currency_learn);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_currency_learn);

        context= CurrencyLearnActivity.this;
        sessionManager = new SessionManager(this);
        binding.header.tvTitle.setText(Constant.foundation_name);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.studend_role_id)) {
            binding.llConverter.setVisibility(View.GONE);
        }else {
            binding.llConverter.setVisibility(View.VISIBLE);
        }

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.llConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrencyExchangeActivity.class);
                startActivity(intent);
            }
        });

         binding.llDenomination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CurrencyDenominationActivity.class);
                startActivity(intent);
            }
        });

         binding.llCountryCurrenry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Currency_Country_Activity.class);
                startActivity(intent);
            }
        });


    }
}