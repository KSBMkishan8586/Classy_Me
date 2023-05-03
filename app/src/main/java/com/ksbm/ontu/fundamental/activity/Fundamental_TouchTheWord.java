package com.ksbm.ontu.fundamental.activity;

import static com.ksbm.ontu.utils.Utils.findWordForRightHanded;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.ActivityFundamentalTouchTheWordBinding;
import com.ksbm.ontu.fundamental.model.touch_the_word.TouchWordQuizData;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fundamental_TouchTheWord extends AppCompatActivity {
    ActivityFundamentalTouchTheWordBinding binding;
    SessionManager sessionManager;
    String quiz_id, quiz_name;
    int quiz_position=0;
    List<TouchWordQuizData> quizDetails = new ArrayList<>();
    int mOffset;
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_fundamental_touch_the_word);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental_touch_the_word);

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
                String msg = "Touch the right word and make a coin.";
                SweetAlt.OpenFreeCoinDialog(Fundamental_TouchTheWord.this, msg);

            }
        });

        getTaskList();

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
                    getTaskList();
                }
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( quizDetails.get(quiz_position).isAttendAnswer()){
                    if (quiz_position != quizDetails.size()-1){
                        quiz_position++;
                        getTaskList();
                    } else {
                        Intent intent = new Intent(Fundamental_TouchTheWord.this, Fundamental_Quiz_Winner.class);
                        intent.putExtra("QuizResult", String.valueOf(got_total_reward));
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(Fundamental_TouchTheWord.this, "Please attend quiz first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.tvQuizQuestion.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mOffset = binding.tvQuizQuestion.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    //  mTxtOffset.setText("" + mOffset);
                    String touch_word = findWordForRightHanded(binding.tvQuizQuestion.getText().toString(), mOffset);
                    //remove only the spaces at the beginning or end of the String
                    touch_word = touch_word.trim();
                    Log.e("touch_right_word", "R"+touch_word);

                    //*****using sppanable color of touch part
                    Spannable spannable = new SpannableString(binding.tvQuizQuestion.getText().toString());
                    String str = spannable.toString();
                    int iStart = str.indexOf(touch_word);
                    int iEnd = iStart + touch_word.length();

                    if (!quizDetails.get(quiz_position).isAttendAnswer()){
                        //check right or wrong selection
                        if (quizDetails.get(quiz_position).getRightAnswer().equalsIgnoreCase(touch_word)){
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            quizDetails.get(quiz_position).setRightAnswer(true);
                            binding.ivIQuiz.setVisibility(View.GONE);
                            Utils.playMusic(R.raw.coin_sound, Fundamental_TouchTheWord.this);
                            got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(quiz_position).getReward());
                            CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(quiz_position).getReward(), "1", quizDetails.get(quiz_position).getQuiz_question_id());//1=right ans
                            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                            binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                            binding.relToolbar.tvUserCoin.setText(""+got_total_reward);

                        }else {
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            quizDetails.get(quiz_position).setRightAnswer(false);
                            binding.ivIQuiz.setVisibility(View.VISIBLE);
                            Utils.playMusic(R.raw.wrong_selected, Fundamental_TouchTheWord.this);
                            got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(quiz_position).getReward());
                            CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(quiz_position).getReward(), "0", quizDetails.get(quiz_position).getQuiz_question_id());//0= wrong answer
                            binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                            binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);

                        }


                        if (!binding.relToolbar.tvCoin.getText().toString().equalsIgnoreCase("0")){
                            int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                                    Integer.parseInt(quizDetails.get(quiz_position).getReward());
                            binding.relToolbar.tvCoin.setText(""+remain_coin);
                        }

                        binding.tvQuizQuestion.setText(spannable);
                        //set attend quiz -true or false
                        quizDetails.get(quiz_position).setAttendAnswer(true);

                        // convert spanable to html string
                        String htmlString = Html.toHtml(spannable);
                        Log.e("span_html", ""+htmlString);
                        quizDetails.get(quiz_position).setQuizQuestion(htmlString);
                    }


                }
                return false;
            }
        });

        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCorrectDialog();

            }
        });


    }

    private void showCorrectDialog() {
        String msg = "Correct Answer is '" + quizDetails.get(quiz_position).getRightAnswer() +"'";
        SweetAlt.OpenFreeCoinDialog(Fundamental_TouchTheWord.this, msg);
    }

    private void getTaskList() {
        if (quiz_position == 0) {
            binding.tvPrevious.setVisibility(View.INVISIBLE);
        }else {
            binding.tvPrevious.setVisibility(View.VISIBLE);
        }
        if (quizDetails.get(quiz_position).isAttendAnswer()){
            if (!quizDetails.get(quiz_position).isRightAnswer()){
                binding.ivIQuiz.setVisibility(View.VISIBLE);
            }else {
                binding.ivIQuiz.setVisibility(View.GONE);
            }
        }else {
            binding.ivIQuiz.setVisibility(View.GONE);
        }
        if (quizDetails != null && quizDetails.size() > 0) {
            // binding.tvQuizQuestion.setText(quizDetails.get(quiz_position).getQuizQuestion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvQuizQuestion.setText(Html.fromHtml(quizDetails.get(quiz_position).getQuizQuestion(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvQuizQuestion.setText(Html.fromHtml(quizDetails.get(quiz_position).getQuizQuestion()));
            }
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