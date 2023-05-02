package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.databinding.ActivityCountingDetailsBinding;
import com.ksbm.ontu.foundation.adapter.Counting_Adapter;
import com.ksbm.ontu.foundation.model.CountingModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.fundamental.adapter.Toodle_List_Adapter;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Counting_Details_Activity extends AppCompatActivity {
    ActivityCountingDetailsBinding binding;
    private String counting;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_counting_details);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_counting_details);

        context = Counting_Details_Activity.this;

        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (getIntent() != null) {
            counting = getIntent().getStringExtra("counting");
        }

        binding.header.tvTitle.setText(Constant.foundation_name);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FreeCoinDialog.class);
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

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        List<CountingModel> countingModelList = new ArrayList<CountingModel>();

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        if (counting.equalsIgnoreCase("30")) {
            //  binding.tvNext.setVisibility(View.VISIBLE);
            for (int i = 1; i <= 30; i++) {
                countingModelList.add(new CountingModel(String.valueOf(i)));
            }
            Counting_Adapter friendsAdapter = new Counting_Adapter(countingModelList, Counting_Details_Activity.this);
            binding.relCountList.setLayoutManager(new GridLayoutManager(context, 4));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        } else if (counting.equalsIgnoreCase("90")) {
            for (int i = 31; i <= 90; i++) {
                countingModelList.add(new CountingModel(String.valueOf(i)));
            }
            Counting_Adapter friendsAdapter = new Counting_Adapter(countingModelList, Counting_Details_Activity.this);
            binding.relCountList.setLayoutManager(new GridLayoutManager(context, 4));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        } else if (counting.equalsIgnoreCase("110")) {
            for (int i = 91; i <= 110; i++) {
                countingModelList.add(new CountingModel(String.valueOf(i)));
            }
            Counting_Adapter friendsAdapter = new Counting_Adapter(countingModelList, Counting_Details_Activity.this);
            binding.relCountList.setLayoutManager(new GridLayoutManager(context, 3));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onBackPressed() {
        finish();

        //super.onBackPressed();
    }
}