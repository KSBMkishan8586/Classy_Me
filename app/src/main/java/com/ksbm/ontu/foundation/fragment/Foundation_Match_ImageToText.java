package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;
import static com.ksbm.ontu.utils.Constant.Passing_percent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationMatchImageBinding;
import com.ksbm.ontu.foundation.ArrowLayout;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class Foundation_Match_ImageToText extends Fragment {
    FoundationMatchImageBinding binding;
    int ques_position;
    int ans_position;
    SessionManager sessionManager;
    FoundationQuizModel.FoundationQuizResponse quizDetails;
    int total_right_ans = 0;
    int total_attempt = 0;
    String TxtSelectQuestion= "", TxtRighAns="";
    Context context;
    ArrowLayout arrowLayout;
    String sub_pattern_type;
    boolean isPlayed= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_match_image, container, false);
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
        // binding.tvHeader.setText(sessionManager.getWorkbook().getFundamental_name());
        //   binding.tvHeader.setText(SessionManager.getMyPref(this).getString(sessionManager.getWorkbook().getFundamental_name(), ""));

        if (getArguments() != null) {
            quizDetails = (FoundationQuizModel.FoundationQuizResponse) getArguments().getSerializable("QuestionDetails");
            sub_pattern_type = getArguments().getString("sub_pattern_type");
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
        binding.tvImg1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvImg1.getText().toString(), false);
            }
        });

        binding.tvImg2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvImg2.getText().toString(), false);
            }
        });

        binding.tvImg3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvImg3.getText().toString(), false);
            }
        });

        binding.tvImg4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvImg4.getText().toString(), false);
            }
        });

        binding.tvImg5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvImg5.getText().toString(), false);
            }
        });

        binding.ivRow1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=0;
                    binding.relRow1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.ivRow1.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionImage();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();
                }

            }
        });

        binding.ivRow2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=1;

                    binding.relRow2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.ivRow2.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionImage();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();
                }
            }
        });

        binding.ivRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=2;

                    binding.relRow3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.ivRow3.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionImage();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();
                }
            }
        });

        binding.ivRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=3;

                   binding.relRow4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.ivRow4.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionImage();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();
                }
            }
        });

        binding.ivRow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=4;

                    binding.relRow5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.ivRow5.setEnabled(false);

                    TxtSelectQuestion= quizDetails.getWords().get(ques_position).getQuestionImage();
                    TxtRighAns= quizDetails.getWords().get(ques_position).getAnswer();
                }
            }
        });


        //click right answer in right seide view
        binding.textRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow1.getText().toString(), false);
                ans_position=5;

                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRow1.getText().toString())){
                        binding.textRow1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRow1.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRow1.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRow1.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }

                }

                TxtSelectQuestion="";
                TxtRighAns= "";

                total_attempt++;
                getCoin();
            }
        });

        binding.textRow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow2.getText().toString(), false);
                ans_position=6;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRow2.getText().toString())){
                        binding.textRow2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRow2.setEnabled(false);
                        total_right_ans = total_right_ans + 1;
                        Utils.showToast(getActivity(), Constant.Right, true);
                    }else {
                        binding.textRow2.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRow2.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";

                total_attempt++;
                getCoin();
            }
        });

        binding.textRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow3.getText().toString(), false);
                ans_position=7;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRow3.getText().toString())){
                        binding.textRow3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRow3.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRow3.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRow3.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

        binding.textRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow4.getText().toString(), false);
                ans_position=8;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRow4.getText().toString())){
                        binding.textRow4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRow4.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRow4.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRow4.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                total_attempt++;
                getCoin();
            }
        });

        binding.textRow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.textRow5.getText().toString(), false);
                ans_position=9;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRow5.getText().toString())){
                        binding.textRow5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRow5.setEnabled(false);
                        Utils.showToast(getActivity(), Constant.Right, true);
                        total_right_ans = total_right_ans + 1;
                    }else {
                        binding.textRow5.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRow5.setEnabled(false);
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

                int user_win_reward = Integer.parseInt(quizDetails.getReward());
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
                list.add(quizDetails.getWords().get(i).getQuestionImage());
                list1.add( quizDetails.getWords().get(i).getAnswer());
            }

            Collections.shuffle(list1);// set random

            if (sub_pattern_type.equalsIgnoreCase("mix")){
                binding.tvImg1.setText(quizDetails.getWords().get(0).getQuestionText());
                binding.tvImg2.setText(quizDetails.getWords().get(1).getQuestionText());
                binding.tvImg3.setText(quizDetails.getWords().get(2).getQuestionText());

                if (quizDetails.getWords().size()>3 && quizDetails.getWords().get(3)!=null){
                    binding.tvImg4.setText(quizDetails.getWords().get(3).getQuestionText());
                }
                if (quizDetails.getWords().size()>4 && quizDetails.getWords().get(4)!=null){
                    binding.tvImg5.setText(quizDetails.getWords().get(4).getQuestionText());
                }
            }else {
                binding.tvImg1.setVisibility(View.GONE);
                binding.tvImg2.setVisibility(View.GONE);
                binding.tvImg3.setVisibility(View.GONE);
                binding.tvImg4.setVisibility(View.GONE);
                binding.tvImg5.setVisibility(View.GONE);
            }

            //set left side question image
            Glide.with(context)
                    .load(list.get(0))
                    .placeholder(R.drawable.basics_foundation)
                    .error(R.drawable.basics_foundation)
                    .into(binding.ivRow1);

            Glide.with(context)
                    .load(list.get(1))
                    .placeholder(R.drawable.basics_foundation)
                    .error(R.drawable.basics_foundation)
                    .into(binding.ivRow2);

            Glide.with(context)
                    .load(list.get(2))
                    .placeholder(R.drawable.basics_foundation)
                    .error(R.drawable.basics_foundation)
                    .into(binding.ivRow3);

            if (list.size()>3 && list.get(3)!=null){
                Glide.with(context)
                        .load(list.get(3))
                        .placeholder(R.drawable.basics_foundation)
                        .error(R.drawable.basics_foundation)
                        .into(binding.ivRow4);
            }else {
                binding.relRow4.setVisibility(View.GONE);
            }

            if (list.size()>4 && list.get(4)!=null){
                Glide.with(context)
                        .load(list.get(4))
                        .placeholder(R.drawable.basics_foundation)
                        .error(R.drawable.basics_foundation)
                        .into(binding.ivRow5);
            }else {
                binding.relRow5.setVisibility(View.GONE);
            }

            //set right side answer
            binding.textRow1.setText(list1.get(0));
            binding.textRow2.setText(list1.get(1));
            binding.textRow3.setText(list1.get(2));
            if (list1.size()>3 && list1.get(3)!=null){
                binding.textRow4.setText(list1.get(3));
            }else {
                binding.textRow4.setVisibility(View.GONE);
            }
            if (list1.size()>4 && list1.get(4)!=null){
                binding.textRow5.setText(list1.get(4));
            }else {
                binding.textRow5.setVisibility(View.GONE);
            }

        }
    }



}
