package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;
import static com.ksbm.ontu.utils.Constant.Passing_percent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationMatchTextBinding;
import com.ksbm.ontu.foundation.ArrowLayout;
import com.ksbm.ontu.foundation.adapter.Foundation_CheckBox_Quiz_Adapter;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class Foundation_Match_Text extends Fragment {
    FoundationMatchTextBinding binding;
    int ques_position;
    int ans_position;
    SessionManager sessionManager;
    FoundationQuizModel.FoundationQuizResponse quizDetails;
    int total_right_ans = 0;
    int total_attempt = 0;
    String TxtSelectQuestion= "", TxtRighAns="";
    Context context;
    ArrowLayout arrowLayout;
    boolean isPlayed= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_match_text, container, false);
        View view = binding.getRoot();//using data binding
        context = getActivity();
        sessionManager = new SessionManager(getActivity());

        arrowLayout= new ArrowLayout(getActivity());

        String arr[] = sessionManager.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];

        binding.relToolbar.tvLoggedUerName.setText(firstWord);
        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.relToolbar.ivLoggedUser);
        }

        if (getArguments() != null) {
            quizDetails = (FoundationQuizModel.FoundationQuizResponse) getArguments().getSerializable("QuestionDetails");
            // quiz_id = getIntent().getStringExtra("quiz_id");
            binding.tvWorkbookname.setText(quizDetails.getQuizType());
            binding.tvQuizName.setText(quizDetails.getHeading());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());

        }

        getTaskList();
        setOnListner();

        return view;

    }

    private void setOnListner() {
        //left side question touch
        binding.textRow1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=0;
                    binding.textRow1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow1.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionText();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(context, quizDetails.getWords().get(ques_position).getQuestionText(), false);
                }
            }
        });

        binding.textRow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=1;

                    binding.textRow2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow2.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionText();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(context, quizDetails.getWords().get(ques_position).getQuestionText(), false);
                }
            }
        });

        binding.textRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=2;

                    binding.textRow3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow3.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionText();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(context, quizDetails.getWords().get(ques_position).getQuestionText(), false);
                }
            }
        });

        binding.textRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=3;

                    binding.textRow4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow4.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionText();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(context, quizDetails.getWords().get(ques_position).getQuestionText(), false);
                }
            }
        });

        binding.textRow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=4;

                    binding.textRow5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow5.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionText();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(context, quizDetails.getWords().get(ques_position).getQuestionText(), false);
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

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight1.getText().toString())){
                        binding.textRowRight1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight1.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;

                    }else {
                        binding.textRowRight1.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight1.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }

                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight2.getText().toString(), false);

                ans_position=6;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight2.getText().toString())){
                        binding.textRowRight2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight2.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRowRight2.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight2.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight3.getText().toString(), false);
                ans_position=7;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight3.getText().toString())){
                        binding.textRowRight3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight3.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRowRight3.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight3.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight4.getText().toString(), false);
                ans_position=8;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight4.getText().toString())){
                        binding.textRowRight4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight4.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRowRight4.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight4.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRowRight5.getText().toString(), false);
                ans_position=9;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight5.getText().toString())){
                        binding.textRowRight5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight5.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRowRight5.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight5.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

    }

    private void getCoin() {
        if ( total_attempt== quizDetails.getWords().size()){
            //get passing percent
            double amount = Double.parseDouble(String.valueOf(total_right_ans));
            double overall_percent = (amount / Double.parseDouble(String.valueOf(quizDetails.getWords().size()))) * 100;

            //call coin api
            if ((int) overall_percent >= Passing_percent) {
                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "1", quizDetails.getQuestionId());//1=right ans
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvUserCoin.setText(""+quizDetails.getReward());

                int user_win_reward = Integer.parseInt( quizDetails.getReward());
                quizDetails.setUserTotalReward(String.valueOf(user_win_reward));
                userTotalReward = userTotalReward + user_win_reward;

            }else{
                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "0", quizDetails.getQuestionId());//1=right ans//0= wrong answer
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvOntuCoin.setText(""+quizDetails.getReward());
            }
            quizDetails.setAttemptQuiz(true);
            if(!isPlayed){
                Utils.playMusic(R.raw.coin_sound, context);
                isPlayed= true;
            }
        }
    }

    private void getTaskList() {
        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String> list1 = new ArrayList<String>();

            for (int i=0; i<quizDetails.getWords().size(); i++){
                list.add(quizDetails.getWords().get(i).getQuestionText());
                list1.add( quizDetails.getWords().get(i).getAnswer());
            }

            Collections.shuffle(list1); //set random

            //set left side question
            binding.textRow1.setText(list.get(0));
            if (list.size()>1){
                binding.textRow2.setText(list.get(1));
            }else{
                binding.textRow2.setVisibility(View.GONE);
            }

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
            if (list1.size()>1){
                binding.textRowRight2.setText(list1.get(1));
            }else{
                binding.textRowRight2.setVisibility(View.GONE);
            }

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
    }


}
