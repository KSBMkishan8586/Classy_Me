package com.ksbm.ontu.foundation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityDirectionSwipeBinding;
import com.ksbm.ontu.foundation.adapter.AlphabetAdapter;
import com.ksbm.ontu.foundation.adapter.DirectionSliderAdapter;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.foundation.model.AnimalModel;
import com.ksbm.ontu.foundation.model.DirectionTask;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class DirectionSwipeActivity extends AppCompatActivity {
    ActivityDirectionSwipeBinding binding;
    Context context;
    int quiz_position=0;
    List<DirectionTask> directionTasks = new ArrayList<>();
    DirectionSliderAdapter directionSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_direction_swipe);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_direction_swipe);

        binding.header.tvTitle.setText("Touch Direction");
        context = DirectionSwipeActivity.this;


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

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

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0,0,0,0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        addTast();

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
                    // binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() -1);

                } else if (quiz_position == 0) {
                    binding.sliderEditor.setCurrentItem(0);
                }
                //   Log.e("current_page--", ""+quiz_position);
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (directionSlider!=null){
                    if (quiz_position != directionTasks.size() - 1) {
                        quiz_position++;
                        binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1);

                    }else {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

    }

    private void addTast() {

        directionTasks.add(new DirectionTask("Touch Left Star", "Left", "Position"));
        directionTasks.add(new DirectionTask("Touch Right Star", "Right", "Position"));
        directionTasks.add(new DirectionTask("Touch Top Star", "Top", "Position"));
        directionTasks.add(new DirectionTask("Touch Bottom Star", "Bottom", "Position"));
        directionTasks.add(new DirectionTask("Touch East Direction", "East", "Direction"));
        directionTasks.add(new DirectionTask("Touch West Direction", "West", "Direction"));
        directionTasks.add(new DirectionTask("Touch North Direction", "North", "Direction"));
        directionTasks.add(new DirectionTask("Touch South Direction", "South", "Direction"));

        directionSlider = new DirectionSliderAdapter(context, directionTasks);
        binding.sliderEditor.setAdapter(directionSlider);
        binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.sliderEditor.setCurrentItem(0);
        binding.sliderEditor.addOnPageChangeListener(pageChangeListenerEditor);

    }

    ViewPager.OnPageChangeListener pageChangeListenerEditor = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //currentPage=position;

            if (position==0){
                binding.layoutButton.tvPrevious.setVisibility(View.INVISIBLE);
            }else {
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


}