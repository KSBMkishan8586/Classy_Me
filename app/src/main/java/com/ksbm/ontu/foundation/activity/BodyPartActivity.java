package com.ksbm.ontu.foundation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;

import com.ksbm.ontu.databinding.ActivityBodyPartsBinding;
import com.ksbm.ontu.foundation.adapter.BodyAdapter;

import com.ksbm.ontu.foundation.model.BankNoteSliderModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BodyPartActivity extends AppCompatActivity {
    ActivityBodyPartsBinding binding;
    int quiz_position = 0;
    BodyAdapter BodyAdapter;
    List<BankNoteSliderModel> bankNoteSliderModels = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_clock_learn);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_body_parts);
        context = BodyPartActivity.this;


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        binding.header.tvTitle.setText(Constant.foundation_name);

        if (Constant.previous_btn) {
            binding.layoutButton.tvPrevious.setVisibility(View.GONE);
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setLayoutModel();

        if (quiz_position == 0) {
            binding.layoutButton.tvPrevious.setVisibility(View.INVISIBLE);
        } else {
            binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
        }


        binding.viewPager.beginFakeDrag(); //disable swiping
        binding.viewPager.setClipToPadding(true);
        binding.viewPager.setPadding(0, 0, 0, 0);
        binding.viewPager.setPageMargin(40);
        binding.viewPager.setPagingEnabled(false);


        Utils.setRainbowColor(binding.tvHeading1);

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

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {

                    quiz_position--;
                    // binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() - 1);

                } else if (quiz_position == 0) {
                    binding.viewPager.setCurrentItem(0);
                }
                //   Log.e("current_page--", ""+quiz_position);
            }

        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (BodyAdapter != null) {
                    binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
                    if (quiz_position != bankNoteSliderModels.size() - 1) {
                        quiz_position++;
                        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                    } else {
                        Constant.previous_btn = false;
//                         Toast.makeText(BodyPartActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Alphabet_Activity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

    }

    private void setLayoutModel() {
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.bodypartfront, "","Body Parts Front"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.bodypartback, "","Body Parts Back"));
//        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.bodypart_c, ""));
//        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.bodypart_d, ""));
//        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.bodypart_e, ""));
//        // bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.time_to_past, ""));

        BodyAdapter = new BodyAdapter(this, bankNoteSliderModels);
        binding.viewPager.setAdapter(BodyAdapter);
        binding.viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.addOnPageChangeListener(pageChangeListenerEditor);
    }

    ViewPager.OnPageChangeListener pageChangeListenerEditor = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //currentPage=position;
            if (position == 0) {
                binding.layoutButton.tvPrevious.setVisibility(View.INVISIBLE);
            } else {
                binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }

}