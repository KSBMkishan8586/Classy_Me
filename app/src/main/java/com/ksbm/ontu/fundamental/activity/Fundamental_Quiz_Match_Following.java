package com.ksbm.ontu.fundamental.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityFundamentalQuizMatchFollowingBinding;
import com.ksbm.ontu.fundamental.model.match_following_model.Match_Following_QuizData;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fundamental_Quiz_Match_Following extends AppCompatActivity {
    Context context;
    SessionManager sessionManager;
    ActivityFundamentalQuizMatchFollowingBinding binding;
    List<Match_Following_QuizData> quizDetails =new ArrayList<>();
    String quiz_id, quiz_name;
    int quiz_position=0;
    ArrayAdapter<String> listadapter;
    String TxtSelectQuestion= "", TxtRighAns="";
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;
    int ques_position;
    int ans_position;
    int total_attempt=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fundamental__quiz__match__following);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental__quiz__match__following);
        context = Fundamental_Quiz_Match_Following.this;
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
            quizDetails =  getIntent().getParcelableArrayListExtra("QuizDetails");
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
                String msg = "Please match the following and make a coin.";
                SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Match_Following.this, msg);

            }
        });

//        if (quiz_position == 0) {
        binding.tvPrevious.setVisibility(View.VISIBLE);
        binding.tvNext.setVisibility(View.VISIBLE);
//        }else {
//            binding.tvPrevious.setVisibility(View.VISIBLE);
//        }

        setQuiz();
        setOnListner();
        //*******************************************

//        binding.textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//
//                final TextView tv_id = (TextView) v.findViewById(R.id.text_row);
//               //// String txt = tv_id.getText().toString();
//
//                if (!quizDetails.get(position).isSelected() && TxtSelectQuestion.isEmpty()){
//                  //  Toast.makeText(Fundamental_Quiz_Match_Following.this, ""+txt, Toast.LENGTH_SHORT).show();
//                    tv_id.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
//                    tv_id.setEnabled(false);
//                    quizDetails.get(position).setSelected(true);
//
//                    TxtSelectQuestion= quizDetails.get(position).getQuizQuestion();
//                    TxtRighAns= quizDetails.get(position).getRightAnswer();
//                }
//
//            }
//        });

//        binding.textList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//                final TextView tv_id = (TextView) v.findViewById(R.id.text_row);
//                String txt = tv_id.getText().toString();
//
//                if (!quizDetails.get(position).isSelectedAns() && !TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
//                   // Toast.makeText(Fundamental_Quiz_Match_Following.this, ""+txt, Toast.LENGTH_SHORT).show();
//
//                    String TxtSelectAnswer= txt;
//                    if (TxtRighAns.equalsIgnoreCase(TxtSelectAnswer)){
//                        tv_id.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
//                        tv_id.setEnabled(false);
//                        quizDetails.get(position).setSelectedAns(true);
//                        Toast.makeText(Fundamental_Quiz_Match_Following.this, "Right", Toast.LENGTH_SHORT).show();
//
//                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(position).getReward());
//                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(position).getReward(), "1", quizDetails.get(position).getQuiz_question_id());//1=right ans
//                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
//                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
//                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
//
//                    }else {
//                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(position).getReward());
//                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(position).getReward(), "0", quizDetails.get(position).getQuiz_question_id());//0= wrong answer
//                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
//                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
//                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
//                        Toast.makeText(Fundamental_Quiz_Match_Following.this, "Wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                    TxtSelectQuestion="";
//                    TxtRighAns= "";
//                    quiz_position++;
//
//                    if (quiz_position==quizDetails.size()){
//                        binding.tvNext.setVisibility(View.VISIBLE);
//                    }
//
//                }else {
//                    //Toast.makeText(Fundamental_Quiz_Match_Following.this, "Please select question", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        binding.tvNext.setOnClickListener(view -> {
            Intent intent = new Intent(Fundamental_Quiz_Match_Following.this, Fundamental_Quiz_Winner.class);
            intent.putExtra("QuizResult", String.valueOf(got_total_reward));
            startActivity(intent);
            finish();
        });

    }

    private void setOnListner() {
        //left side question touch
        binding.textRow1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow1.getText().toString(), false);
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=0;
                    binding.textRow1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow1.setEnabled(false);

                    TxtSelectQuestion= quizDetails.get(ques_position).getQuizQuestion();
                    TxtRighAns= quizDetails.get(ques_position).getRightAnswer();
                }
            }
        });

        binding.textRow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow2.getText().toString(), false);
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=1;

                    binding.textRow2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow2.setEnabled(false);

                    TxtSelectQuestion= quizDetails.get(ques_position).getQuizQuestion();
                    TxtRighAns= quizDetails.get(ques_position).getRightAnswer();
                }
            }
        });

        binding.textRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow3.getText().toString(), false);
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=2;

                    binding.textRow3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow3.setEnabled(false);

                    TxtSelectQuestion= quizDetails.get(ques_position).getQuizQuestion();
                    TxtRighAns= quizDetails.get(ques_position).getRightAnswer();

                }
            }
        });

        binding.textRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow4.getText().toString(), false);
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=3;

                    binding.textRow4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow4.setEnabled(false);

                    TxtSelectQuestion= quizDetails.get(ques_position).getQuizQuestion();
                    TxtRighAns= quizDetails.get(ques_position).getRightAnswer();

                }
            }
        });

        binding.textRow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow5.getText().toString(), false);
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=4;

                    binding.textRow5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow5.setEnabled(false);

                    TxtSelectQuestion= quizDetails.get(ques_position).getQuizQuestion();
                    TxtRighAns= quizDetails.get(ques_position).getRightAnswer();


                }
            }
        });


        //click right answer in right seide view
        binding.textRowRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans_position=5;
                Speach_Record_Activity.speakOut(context, binding.textRowRight1.getText().toString(), false);

                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight1.getText().toString())){
                        binding.textRowRight1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight1.setEnabled(false);
                        Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "1", quizDetails.get(ques_position).getQuiz_question_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Match_Following.this);

                    }else {
                        binding.textRowRight1.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight1.setEnabled(false);
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "0", quizDetails.get(ques_position).getQuiz_question_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_Match_Following.this);
                    }


                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                // quiz_position++;

                if (quiz_position==quizDetails.size()){
                    binding.tvNext.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.textRowRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight2.getText().toString(), false);
                total_attempt++;
                ans_position=6;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight2.getText().toString())){
                        binding.textRowRight2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight2.setEnabled(false);
                        Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();

                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "1", quizDetails.get(ques_position).getQuiz_question_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Match_Following.this);
                    }else {
                        binding.textRowRight2.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight2.setEnabled(false);
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "0", quizDetails.get(ques_position).getQuiz_question_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_Match_Following.this);
                    }

                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                // quiz_position++;

                if (quiz_position==quizDetails.size()){
                    binding.tvNext.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.textRowRight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight3.getText().toString(), false);
                ans_position=7;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight3.getText().toString())){
                        binding.textRowRight3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight3.setEnabled(false);
                        Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();

                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "1", quizDetails.get(ques_position).getQuiz_question_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Match_Following.this);
                    }else {
                        binding.textRowRight3.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight3.setEnabled(false);
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "0", quizDetails.get(ques_position).getQuiz_question_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_Match_Following.this);
                    }

                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                // quiz_position++;

                if (quiz_position==quizDetails.size()){
                    binding.tvNext.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.textRowRight4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight4.getText().toString(), false);
                ans_position=8;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight4.getText().toString())){
                        binding.textRowRight4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight4.setEnabled(false);
                        Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "1", quizDetails.get(ques_position).getQuiz_question_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Match_Following.this);
                    }else {
                        binding.textRowRight4.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight4.setEnabled(false);
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "0", quizDetails.get(ques_position).getQuiz_question_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_Match_Following.this);
                    }

                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                //quiz_position++;

                if (quiz_position==quizDetails.size()){
                    binding.tvNext.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.textRowRight5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight5.getText().toString(), false);
                ans_position=9;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight5.getText().toString())){
                        binding.textRowRight5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight5.setEnabled(false);
                        Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "1", quizDetails.get(ques_position).getQuiz_question_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Match_Following.this);
                    }else {
                        binding.textRowRight5.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight5.setEnabled(false);
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(ques_position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(ques_position).getReward(), "0", quizDetails.get(ques_position).getQuiz_question_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_Match_Following.this);
                    }
                }
                TxtSelectQuestion="";
                TxtRighAns= "";
                // quiz_position++;

                if (total_attempt==quizDetails.size()){
                    binding.tvNext.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setQuiz() {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();

        for (int i=0; i<quizDetails.size(); i++){
            list.add(quizDetails.get(i).getQuizQuestion());
            list1.add( quizDetails.get(i).getRightAnswer());
        }

//        listadapter = new ArrayAdapter<String>(this, R.layout.rowtext,R.id.text_row, list);
//        binding.textList.setAdapter(listadapter);

        Collections.shuffle(list1);

//        listadapter = new ArrayAdapter<String>(this, R.layout.rowtext, R.id.text_row, list1);
//        binding.textList1.setAdapter(listadapter);

        //set left side question
        binding.textRow1.setText(list.get(0));
        binding.textRow2.setText(list.get(1));

        if (list.size()>2 && list.get(2)!=null){
            binding.textRow3.setText(list.get(2));
        }else {
            binding.textRow3.setVisibility(View.GONE);
        }
        if (list.size()>3 && list.get(3)!=null){
            binding.textRow4.setText(list.get(3));
        }else {
            binding.textRow4.setVisibility(View.GONE);
        }
        if (list.size()>4 && list.get(4)!=null){
            binding.textRow5.setText(list.get(4));
        }else {
            binding.textRow5.setVisibility(View.GONE);
        }

        //set right side answer
        binding.textRowRight1.setText(list1.get(0));
        binding.textRowRight2.setText(list1.get(1));

        if (list1.size()>2 && list1.get(2)!=null){
            binding.textRowRight3.setText(list1.get(2));
        }else {
            binding.textRowRight3.setVisibility(View.GONE);
        }
        if (list1.size()>3 && list1.get(3)!=null){
            binding.textRowRight4.setText(list1.get(3));
        }else {
            binding.textRowRight4.setVisibility(View.GONE);
        }
        if (list1.size()>4 && list1.get(4)!=null){
            binding.textRowRight5.setText(list1.get(4));
        }else {
            binding.textRowRight5.setVisibility(View.GONE);
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