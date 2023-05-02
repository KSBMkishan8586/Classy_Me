package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.databinding.ActivityColorBinding;
import com.ksbm.ontu.foundation.adapter.ColorPalletAdapter;
import com.ksbm.ontu.foundation.adapter.Counting_Adapter;
import com.ksbm.ontu.foundation.model.PalletColorModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;
import com.skydoves.rainbow.Rainbow;

import java.util.ArrayList;
import java.util.List;

public class ColorActivity extends AppCompatActivity {
    ActivityColorBinding binding;
    List<PalletColorModel> palletColorModelList = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_color);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_color);

        context = ColorActivity.this;


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (Constant.previous_btn) {
            binding.layoutButton.tvPrevious.setVisibility(View.GONE);
        }
        binding.header.tvTitle.setText(Constant.foundation_name);
        Utils.setRainbowColor(binding.tvFavTitle);
        setPalletColor();

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

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ColorActivity.this, SingleColorActivity.class);
                intent.putParcelableArrayListExtra("palletColorModelList", (ArrayList<? extends Parcelable>) palletColorModelList);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setPalletColor() {
        palletColorModelList.clear();
        palletColorModelList.add(new PalletColorModel("Red", getResources().getColor(R.color.red)));
        palletColorModelList.add(new PalletColorModel("Orange", getResources().getColor(R.color.orange)));
        palletColorModelList.add(new PalletColorModel("Yellow", getResources().getColor(R.color.yellow)));
        palletColorModelList.add(new PalletColorModel("Green", getResources().getColor(R.color.green)));
        palletColorModelList.add(new PalletColorModel("Blue", getResources().getColor(R.color.blue)));
        palletColorModelList.add(new PalletColorModel("Purple", getResources().getColor(R.color.purple)));
        palletColorModelList.add(new PalletColorModel("Indigo", getResources().getColor(R.color.indigo)));
        palletColorModelList.add(new PalletColorModel("Pink", getResources().getColor(R.color.pink)));
        palletColorModelList.add(new PalletColorModel("Silver", getResources().getColor(R.color.silver)));

        palletColorModelList.add(new PalletColorModel("Brown", getResources().getColor(R.color.brown)));
        palletColorModelList.add(new PalletColorModel("Grey", getResources().getColor(R.color.gray)));

        palletColorModelList.add(new PalletColorModel("Violet", getResources().getColor(R.color.violet)));
        palletColorModelList.add(new PalletColorModel("Maroon", getResources().getColor(R.color.maroon)));
        palletColorModelList.add(new PalletColorModel("Magenta", getResources().getColor(R.color.magenta)));
        palletColorModelList.add(new PalletColorModel("Olive", getResources().getColor(R.color.olive)));


        ColorPalletAdapter friendsAdapter = new ColorPalletAdapter(palletColorModelList, ColorActivity.this);
        binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
        friendsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}