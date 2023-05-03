package com.ksbm.ontu.fundamental.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.ActivityFundamentalQuizTouchFillBinding;
import com.ksbm.ontu.fundamental.adapter.Fundamental_Touch_Fill_Word_Adapter;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.Touch_FillWord;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.Touch_Fill_Response;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FundamentalQuizTouchFill extends AppCompatActivity {
    SessionManager sessionManager;
    ActivityFundamentalQuizTouchFillBinding binding;
    Touch_Fill_Response quizDetails ;
    String quiz_id, quiz_name;
    int quiz_position=0;
    List<Touch_FillWord> wordQuizLists = new ArrayList<>();
    View view;
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_fundamental_quiz_touch_fill);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental_quiz_touch_fill);

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
            quizDetails = (Touch_Fill_Response) getIntent().getSerializableExtra("QuizDetails");
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
                String msg = "Touch and select right answer and make a coin.";
                SweetAlt.OpenFreeCoinDialog(FundamentalQuizTouchFill.this, msg);

            }
        });

        getOptionList();
        setQuestion();

        if (quiz_position == 0) {
            binding.tvPrevious.setVisibility(View.INVISIBLE);
        }else {
            binding.tvPrevious.setVisibility(View.VISIBLE);
        }

        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quiz_position > 0){
                    quiz_position--;
                    setQuestion();
                }
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizDetails.getQuizData().get(quiz_position).isClickAnswer()){
                    if (quiz_position != quizDetails.getQuizData().size()-1){
                        quiz_position++;
                        setQuestion();
                    }else {
                        Intent intent = new Intent(FundamentalQuizTouchFill.this, Fundamental_Quiz_Winner.class);
                        intent.putExtra("QuizResult", String.valueOf(got_total_reward));
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(FundamentalQuizTouchFill.this, "Please select answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n"+ "'" +quizDetails.getQuizData().get(quiz_position).getRightAnswer() +"'";
                SweetAlt.OpenFreeCoinDialog(FundamentalQuizTouchFill.this, msg);
                Log.e("onClick: ", ""+msg);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setQuestion() {
        if (quiz_position == 0) {
            binding.tvPrevious.setVisibility(View.INVISIBLE);
        }else {
            binding.tvPrevious.setVisibility(View.VISIBLE);
        }

        if (quizDetails.getQuizData().get(quiz_position).isClickAnswer()){
            if (!quizDetails.getQuizData().get(quiz_position).isAns_right()){
                binding.ivIQuiz.setVisibility(View.VISIBLE);
            }else {
                binding.ivIQuiz.setVisibility(View.GONE);
            }
        }else {
            binding.ivIQuiz.setVisibility(View.GONE);
        }

        if (quizDetails.getQuizData() != null && quizDetails.getQuizData().size() > 0) {

            binding.tvQuestion.setText(""+ (quiz_position+1) +". "+ quizDetails.getQuizData().get(quiz_position).getQuizQuestion());

        }

    }

    private void getOptionList() {
        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            Fundamental_Touch_Fill_Word_Adapter friendsAdapter = new Fundamental_Touch_Fill_Word_Adapter(wordQuizLists, FundamentalQuizTouchFill.this);
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("SetTextI18n")
    public void CheckRightAnswer(View view, int position, String word) {
        this.view=view;

        if (!quizDetails.getQuizData().get(quiz_position).isClickAnswer()){
            if (quizDetails.getQuizData().get(quiz_position).getRightAnswer().equalsIgnoreCase(word)){
                view.setBackgroundResource(R.drawable.circle_fill_green);
                //Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();

                quizDetails.getQuizData().get(quiz_position).setAns_right(true);
                binding.ivIQuiz.setVisibility(View.GONE);

                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.getQuizData().get(quiz_position).getReward(), "1", quizDetails.getQuizData().get(quiz_position).getQuiz_question_id());//1=right ans

                got_total_reward = got_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                Utils.playMusic(R.raw.coin_sound, FundamentalQuizTouchFill.this);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        view.setVisibility(View.INVISIBLE);
                        binding.tvQuestion.setText(""+ (quiz_position+1) +". "+ quizDetails.getQuizData().get(quiz_position).getComplete_right_answer());

                        quizDetails.getQuizData().get(quiz_position).setQuizQuestion(
                                quizDetails.getQuizData().get(quiz_position).getComplete_right_answer());
                    }
                }, 100);

            }else {
                //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                view.setBackgroundResource(R.drawable.circle_fill_red);
                quizDetails.getQuizData().get(quiz_position).setAns_right(false);
                binding.ivIQuiz.setVisibility(View.VISIBLE);

                got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());

                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.getQuizData().get(quiz_position).getReward(), "0", quizDetails.getQuizData().get(quiz_position).getQuiz_question_id());//0= wrong answer
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
                Utils.playMusic(R.raw.wrong_selected, FundamentalQuizTouchFill.this);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundResource(R.drawable.circle_fill);
                    }
                }, 2000);


            }

            int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                    Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
            binding.relToolbar.tvCoin.setText(""+remain_coin);

            quizDetails.getQuizData().get(quiz_position).setClickAnswer(true);
        }


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