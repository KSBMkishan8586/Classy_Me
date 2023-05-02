package com.ksbm.ontu.foundation.activity;

import static com.ksbm.ontu.utils.Constant.Passing_percent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ActivityFoundationWinnerBinding;
import com.ksbm.ontu.databinding.ActivityFundamentalQuizWinnerBinding;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Winner;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Utils;

public class FoundationWinnerActivity extends AppCompatActivity {
    SessionManager sessionManager1;
    ActivityFoundationWinnerBinding binding;
    String quizResult, QuizReward, foundation_name, foundation_id;
    Double overall_percent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_foundation_winner);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_foundation_winner);
        sessionManager1 = new SessionManager(this);

        if (getIntent() != null) {
            quizResult = getIntent().getStringExtra("QuizResult");
            QuizReward = getIntent().getStringExtra("QuizReward");
            foundation_name = getIntent().getStringExtra("foundation_name");
            foundation_id = getIntent().getStringExtra("foundation_id");

            binding.tvGotCoins.setText(quizResult);
            binding.textView4.setText(QuizReward);
            binding.textView5.setText(quizResult);

            int ontu_coin = Integer.parseInt(QuizReward) - Integer.parseInt(quizResult);

            if (ontu_coin<0){
                binding.tvGotCoinsOntu.setText("0" );
            }else {
                binding.tvGotCoinsOntu.setText("" + ontu_coin);
            }


            //get customer coin percent
            double amount = Double.parseDouble(quizResult);
            overall_percent = (amount / Double.parseDouble(QuizReward)) * 100;

            Log.e("overall_percent", "" + overall_percent);

            if (overall_percent.intValue() >= Passing_percent) {
                binding.tvUserWin.setVisibility(View.VISIBLE);
               // binding.tvStatus.setText("Next Quiz!");
            } else {
                if (overall_percent.intValue() == Passing_percent) {
                    binding.tvOntuWin.setText("Tie");
                    binding.tvOntuWin.setVisibility(View.VISIBLE);
                    binding.tvStatus.setText("Retake!");
                } else {
                    binding.tvOntuWin.setVisibility(View.VISIBLE);
                    binding.tvStatus.setText("Retake!");
                }

            }
        }

       // binding.tvWorkbookName.setText(sessionManager.getWorkbook().getWorkbookName());
        binding.tvTitle.setText(foundation_name);

        String arr[] = sessionManager1.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];
        binding.tvUserName.setText(firstWord);

        if (sessionManager1.getUser().getProfilePic() != null && !sessionManager1.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager1.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.ivLoggedUser);
        }
        //animate user
        ViewAnimator();
        Utils.playMusic(R.raw.winning_sound, FoundationWinnerActivity.this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.llRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (overall_percent.intValue() >= Passing_percent) {
                    finish();
                } else {
                    Intent intent = new Intent(FoundationWinnerActivity.this, FoundationQuizActivity.class);
                    intent.putExtra("foundation_id", foundation_id);
                    intent.putExtra("foundation_name", foundation_name);
                    startActivity(intent);
                }
            }
        });


    }

    private void ViewAnimator() {
        ViewAnimator
                .animate(binding.relUser)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .thenAnimate(binding.relUser)
                .scale(1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();

        ViewAnimator
                .animate(binding.ivOntuUser)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .thenAnimate(binding.ivOntuUser)
                .scale(1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }

}