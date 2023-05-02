package com.ksbm.ontu.foundation.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.databinding.ActivityClockLearnBinding;
import com.ksbm.ontu.foundation.adapter.CustomPagerAdapter;
import com.ksbm.ontu.foundation.adapter.TimeLearnAdapter;
import com.ksbm.ontu.foundation.model.BankNoteSliderModel;
import com.ksbm.ontu.foundation.time.ClockHandMoveActivity;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;

public class ClockLearnActivity extends AppCompatActivity {
    ActivityClockLearnBinding binding;
    int quiz_position = 0;
    TimeLearnAdapter timeLearnAdapter;
    List<BankNoteSliderModel> bankNoteSliderModels = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_clock_learn);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_clock_learn);
        context = ClockLearnActivity.this;


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (Constant.previous_btn) {
            binding.layoutButton.tvPrevious.setVisibility(View.GONE);
        }
        binding.header.tvTitle.setText(Constant.foundation_name);

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
                    Utils.screenShot(binding.relativeLayout, getWindow().getContext());
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

                if (timeLearnAdapter != null) {
                    binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
                    if (quiz_position != bankNoteSliderModels.size() - 1) {
                        quiz_position++;
                        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);

                    } else {
                        // Toast.makeText(ClockLearnActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ClockLearnActivity.this, ClockHandMoveActivity.class);
                        startActivityForResult(intent, 22);
                        finish();

                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

    }

    private void setLayoutModel() {
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.time_second_hand, "","second"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.time_minute_hand, "","Minute"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.time_hour_hand, "","Hour"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.time_details, "","Time"));
        //bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.time_to_past, ""));

        timeLearnAdapter = new TimeLearnAdapter(this, bankNoteSliderModels);
        binding.viewPager.setAdapter(timeLearnAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK) {

        }
    }
}