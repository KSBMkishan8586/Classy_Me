package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityAnimalBinding;
import com.ksbm.ontu.foundation.adapter.AnimalAdapter;
import com.ksbm.ontu.foundation.model.LearningActivityModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;

public class Animal_Activity extends AppCompatActivity {
    ActivityAnimalBinding binding;
    Context context;
    SessionManager sessionManager;
    List<LearningActivityModel> animalList;
    AnimalAdapter animalAdapter;
    int quiz_position = 0;
    boolean isPlayed = false;
    String foundationTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_animal);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_animal);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        context = Animal_Activity.this;
        sessionManager = new SessionManager(this);

        if (getIntent() != null) {
            animalList = getIntent().getParcelableArrayListExtra("AnimalList");
            foundationTypeName = getIntent().getStringExtra("foundationTypeName");
        }

        binding.header.tvTitle.setText(foundationTypeName);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0, 0, 0, 0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        if (animalList != null && animalList.size() > 0) {
            animalAdapter = new AnimalAdapter(context, animalList);
            binding.sliderEditor.setAdapter(animalAdapter);
            // binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
            binding.sliderEditor.setCurrentItem(0);
            binding.sliderEditor.addOnPageChangeListener(pageChangeListenerEditor);

        }

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

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
                    // binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() - 1);

                    Speach_Record_Activity.mFileName = null;

                    if (mPlayer != null) {
                        mPlayer.release();
                        mPlayer = null;
                    }

                } else if (quiz_position == 0) {
                    binding.sliderEditor.setCurrentItem(0);
                }
                //   Log.e("current_page--", ""+quiz_position);
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (animalAdapter != null) {
                    if (quiz_position != animalList.size() - 1) {
                        quiz_position++;
                        binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1);

                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }
                        Speach_Record_Activity.mFileName = null;

                    } else {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

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

    public void SubmitCoin(String foundationLearningCoinBonus, String foundation_learn, int quiz_position) {
        if (!isPlayed) {
            CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), foundationLearningCoinBonus, foundation_learn, animalList.get(quiz_position).getLearning_id());
            // Utils.playMusic(R.raw.coin_sound, context);
            isPlayed = true;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}