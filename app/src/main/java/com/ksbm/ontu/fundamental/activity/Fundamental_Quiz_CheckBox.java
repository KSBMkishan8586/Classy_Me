package com.ksbm.ontu.fundamental.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ActivityFundamentalQuizCheckBoxBinding;
import com.ksbm.ontu.fundamental.adapter.Fundamental_CheckBox_Quiz_Adapter;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.QuizDetails;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Word_Quiz_List;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fundamental_Quiz_CheckBox extends AppCompatActivity {
    ActivityFundamentalQuizCheckBoxBinding binding;
    SessionManager sessionManager;
    QuizDetails quizDetails;
    String quiz_id;
    List<Word_Quiz_List> wordQuizLists = new ArrayList<>();
    Fundamental_CheckBox_Quiz_Adapter adapter;
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_fundamental__quiz__check_box);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental__quiz__check_box);

        sessionManager = new SessionManager(this);

        String arr[] = sessionManager.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];

        binding.relToolbar.tvLoggedUerName.setText(firstWord);
        binding.tvWorkbookname.setText(sessionManager.getWorkbook().getWorkbookName());
        binding.relToolbar.tvCoin.setText(sessionManager.getWorkbook().getReward());
        binding.tvHeader.setText(sessionManager.getWorkbook().getFundamental_name());
        //   binding.tvHeader.setText(SessionManager.getMyPref(this).getString(sessionManager.getWorkbook().getFundamental_name(), ""));
        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.relToolbar.ivLoggedUser);
        }

        if (getIntent() != null) {
            quizDetails = (QuizDetails) getIntent().getSerializableExtra("QuizDetails");
            quiz_id = getIntent().getStringExtra("quiz_id");

            binding.tvHeading2.setText(quizDetails.getHeading1());
            binding.tvHeading3.setText(quizDetails.getHeading2());

            if (quizDetails.getHeading3() != null && !quizDetails.getHeading3().isEmpty()) {
                binding.tvHeading4.setText(quizDetails.getHeading3());
                binding.tvHeading4.setVisibility(View.VISIBLE);
            } else {
                binding.tvHeading4.setVisibility(View.INVISIBLE);
            }

        }

        setOnListner();
        getTaskList();

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter != null) {
                    adapter.CheckedRadio();
                }
            }
        });

    }

    private void getTaskList() {
        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            adapter = new Fundamental_CheckBox_Quiz_Adapter(wordQuizLists, Fundamental_Quiz_CheckBox.this, quizDetails.getHeading1(), quizDetails.getHeading2(), quizDetails.getHeading3(), quiz_id);
            binding.setPersonalityAdapter(adapter);//set databinding adapter
            adapter.notifyDataSetChanged();
        }

    }

    private void setOnListner() {
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
                String msg = "Please check right answer and make a coin.";
                SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_CheckBox.this, msg);

            }
        });
    }

    @SuppressLint("CheckResult")
    public void FinalQuizResult(int got_total_reward) {

        Intent intent = new Intent(Fundamental_Quiz_CheckBox.this, Fundamental_Quiz_Winner.class);
        intent.putExtra("QuizResult", String.valueOf(got_total_reward));
        startActivity(intent);
        finish();

    }

     @SuppressLint("SetTextI18n")
     public void CoinAnimated(boolean isRight, String reward) {
        if (isRight){
            got_total_reward = got_total_reward + Integer.parseInt(reward);
            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
            binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
            binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
            Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_CheckBox.this);

        }else {
            got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(reward);
            binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
            binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
            Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_CheckBox.this);
        }

         int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                 Integer.parseInt(reward);
         binding.relToolbar.tvCoin.setText(""+remain_coin);

//         Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_CheckBox.this);
    }




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