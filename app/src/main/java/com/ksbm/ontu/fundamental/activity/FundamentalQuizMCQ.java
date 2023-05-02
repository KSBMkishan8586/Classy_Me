package com.ksbm.ontu.fundamental.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ActivityFundamentalQuizMCQBinding;
import com.ksbm.ontu.fundamental.adapter.SliderMCQAdapter;
import com.ksbm.ontu.fundamental.model.fundamental_mcq_model.Fundamental_MCQ_Data;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FundamentalQuizMCQ extends AppCompatActivity {
    SessionManager sessionManager;
    ActivityFundamentalQuizMCQBinding binding;
    List<Fundamental_MCQ_Data> quizDetails = new ArrayList<>();
    String quiz_id, quiz_name, devilRightAnswer;
    int quiz_position=0;
    SliderMCQAdapter sliderAdapter;
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_fundamental_quiz_m_c_q);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental_quiz_m_c_q);

        sessionManager = new SessionManager(this);

        String arr[] = sessionManager.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];

        binding.relToolbar.tvLoggedUerName.setText(firstWord);
        binding.tvWorkbookname.setText(sessionManager.getWorkbook().getWorkbookName());
        binding.relToolbar.tvCoin.setText(sessionManager.getWorkbook().getReward());
        binding.tvHeader.setText(sessionManager.getWorkbook().getFundamental_name());
        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.relToolbar.ivLoggedUser);
        }
        if (getIntent() != null) {
            quizDetails = getIntent().getParcelableArrayListExtra("QuizDetails");
            quiz_id= getIntent().getStringExtra("quiz_id");
            quiz_name= getIntent().getStringExtra("quiz_name");


            binding.tvQuizName.setText(quiz_name);
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivIButton.setVisibility(View.GONE);
        binding.ivIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Select right answer and make a coin.";
                SweetAlt.OpenFreeCoinDialog(FundamentalQuizMCQ.this, msg);

            }
        });

        getTaskList();

        if (quiz_position == 0) {
            binding.tvPrevious.setVisibility(View.INVISIBLE);
        }else {
            binding.tvPrevious.setVisibility(View.VISIBLE);
        }

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0,0,0,0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
//                    binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() -1);
                } else if (quiz_position == 0) {
                    binding.sliderEditor.setCurrentItem(0);
                }
                Log.e("current_page--", ""+quiz_position);
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sliderAdapter!=null){
                    if (sliderAdapter.CheckRadioStatus(quiz_position)){
                        if (quiz_position != quizDetails.size() - 1) {
                            quiz_position++;
                            binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() +1);
                        }else {
                            Intent intent = new Intent(FundamentalQuizMCQ.this, Fundamental_Quiz_Winner.class);
                            intent.putExtra("QuizResult", String.valueOf(got_total_reward));
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(FundamentalQuizMCQ.this, "Please select option", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.e("current_page++", ""+quiz_position);
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void getTaskList() {

        sliderAdapter = new SliderMCQAdapter(FundamentalQuizMCQ.this, quizDetails, quiz_id);
        binding.sliderEditor.setAdapter(sliderAdapter);
        binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.sliderEditor.setCurrentItem(0);
        binding.sliderEditor.addOnPageChangeListener(pageChangeListenerEditor);

    }

    @SuppressLint("SetTextI18n")
    public void CoinAnimated(boolean isRight, String reward) {
        if (isRight){
            got_total_reward = got_total_reward + Integer.parseInt(reward);
            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
            binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
            binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
        }else {
            got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(reward);
            binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
            binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
        }
        Utils.playMusic(R.raw.coin_sound, FundamentalQuizMCQ.this);
        int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                Integer.parseInt(reward);
        binding.relToolbar.tvCoin.setText(""+remain_coin);
    }

    ViewPager.OnPageChangeListener pageChangeListenerEditor = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //currentPage=position;

            if (position==0){
                binding.tvPrevious.setVisibility(View.INVISIBLE);
            }else {
                binding.tvPrevious.setVisibility(View.VISIBLE);
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
        String title = getResources().getString(R.string.app_name);
        String msg = "Are you sure, you want close quiz";
        SweetAlt.normalDialog(this, title, msg, true, true, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                finish();
            }
        });
    }

}