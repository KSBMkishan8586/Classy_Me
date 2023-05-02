package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.databinding.ActivityCountingNoteBinding;
import com.ksbm.ontu.foundation.adapter.AlphabetAdapter;
import com.ksbm.ontu.foundation.adapter.CustomPagerAdapter;
import com.ksbm.ontu.foundation.model.BankNoteSliderModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;

public class Counting_Note_Activity extends AppCompatActivity {
    ActivityCountingNoteBinding binding;
    int quiz_position = 0;
    CustomPagerAdapter note_adapter;
    List<BankNoteSliderModel> bankNoteSliderModels = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_counting_note);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_counting_note);

        context = Counting_Note_Activity.this;
        binding.header.tvTitle.setText(Constant.foundation_name);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

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

                    if (mPlayer != null) {
                        mPlayer.release();
                        mPlayer = null;
                    }

                } else if (quiz_position == 0) {
                    binding.viewPager.setCurrentItem(0);
                }
                //   Log.e("current_page--", ""+quiz_position);
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (note_adapter != null) {
                    if (quiz_position != bankNoteSliderModels.size() - 1) {
                        quiz_position++;
                        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);

                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }

                    } else {
                        Toast.makeText((Context) Counting_Note_Activity.this, "Completed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

    }

    private void setLayoutModel() {
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_one_hundred, "", "One Hundred"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_two_hundred, "", "Two Hundred"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_five_hundred, "", "Five Hundred"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_one_thousand, "", "One Thousand"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_two_thousand, "", "Two Thousand"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_five_thousand, "", "Five Thousand"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_ten_thousand, "10K = 10000", "Ten Thousand"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_one_lakh, "", "One Lakh"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_ten_lakh, "1M = 1 Million = 1000000", "Ten Lakh or One Million"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_one_billion, "1 Billion = 1B  = 100 crores", "One Billion or Hundred Crores"));
        bankNoteSliderModels.add(new BankNoteSliderModel(R.layout.screen_one_trillion, "1 Trillion = 1000 Billions = 1 Lakh crores", "One Trillion or One Lakh Crores"));

        note_adapter = new CustomPagerAdapter(this, bankNoteSliderModels);
        binding.viewPager.setAdapter(note_adapter);
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

//            if (position != quizDetails.size()-1){
//                binding.tvNext.setVisibility(View.VISIBLE);
//            }else {
//                binding.tvNext.setVisibility(View.INVISIBLE);
//            }
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