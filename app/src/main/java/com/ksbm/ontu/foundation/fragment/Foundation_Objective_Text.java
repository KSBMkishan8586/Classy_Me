package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationMcqTextBinding;
import com.ksbm.ontu.databinding.FoundationTouchFillBinding;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizMCQ;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Foundation_Objective_Text extends Fragment {
    static FoundationMcqTextBinding binding;
    RelativeLayout resultView;
    SessionManager sessionManager;
    static FoundationQuizModel.FoundationQuizResponse quizDetails;
    String sub_pattern_type;
    Context context;
    boolean isPlayed= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_mcq_text, container, false);
        View view = binding.getRoot();//using data binding
        context= getActivity();
        sessionManager = new SessionManager(getActivity());

        resultView = view.findViewById(R.id.resultView);

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
            sub_pattern_type= getArguments().getString("sub_pattern_type");

            binding.relToolbar.tvCoin.setText(quizDetails.getReward());

        }

        getTaskList();

        binding.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvQuestion.getText().toString(), false);
            }
        });

        binding.tvQuizName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvQuizName.getText().toString(), false);
            }
        });

        return view;
    }

    private void getTaskList() {
        quizDetails.setChecked_radio_button(false);
        for (int i = 0; i <binding.radioGroupQuiz.getChildCount(); i++) {
            binding.radioGroupQuiz.getChildAt(i).setEnabled(true);
        }

        binding.tvQuizName.setText(quizDetails.getHeading());

        if (sub_pattern_type.equalsIgnoreCase("image_to_text")){
            binding.tvQuestion.setVisibility(View.GONE);
            binding.ivQuizImage.setVisibility(View.VISIBLE);

            Glide.with(context)
                    //.setDefaultRequestOptions(requestOptions)
                    .load(quizDetails.getQuestionImage())
                    .into(binding.ivQuizImage);

        }else {
            binding.tvQuestion.setVisibility(View.VISIBLE);
            binding.ivQuizImage.setVisibility(View.GONE);
            binding.tvQuestion.setText(quizDetails.getQuestionText());
        }

        binding.radio1.setText(quizDetails.getOption1());
        binding.radio2.setText(quizDetails.getOption2());

        if (!quizDetails.getOption3().isEmpty() && quizDetails.getOption3() != null) {
            binding.radio3.setText(quizDetails.getOption3());
            binding.radio3.setVisibility(View.VISIBLE);
        } else {
            binding.radio3.setVisibility(View.INVISIBLE);
        }

        if (!quizDetails.getOption4().isEmpty() && quizDetails.getOption4() != null) {
            binding.radio4.setText(quizDetails.getOption4());
            binding.radio4.setVisibility(View.VISIBLE);
        } else {
            binding.radio4.setVisibility(View.INVISIBLE);
        }


        if (quizDetails.isChecked_radio_button()) {
            for (int i = 0; i < binding.radioGroupQuiz.getChildCount(); i++) {
                binding.radioGroupQuiz.getChildAt(i).setEnabled(false);
            }
        }

        //**********
        binding.radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);

                Speach_Record_Activity.speakOut(context, rb.getText().toString(), false);

                if (!quizDetails.isChecked_radio_button()) {
                    quizDetails.setChecked_radio_button(true);

                    if (checkedId == R.id.radio1) {
                         quizDetails.setChecked_radio("option_1");
                        CheckRightAns(quizDetails.isChecked_radio(), binding.radio1,  binding.ivIQuiz);
                    } else if (checkedId == R.id.radio2) {
                          quizDetails.setChecked_radio("option_2");
                        CheckRightAns(quizDetails.isChecked_radio(),  binding.radio2, binding.ivIQuiz);
                    } else if (checkedId == R.id.radio3) {
                          quizDetails.setChecked_radio("option_3");
                        CheckRightAns(quizDetails.isChecked_radio(),  binding.radio3, binding.ivIQuiz);
                    } else if (checkedId == R.id.radio4) {
                         quizDetails.setChecked_radio("option_4");
                        CheckRightAns(quizDetails.isChecked_radio(), binding.radio4,  binding.ivIQuiz);
                    }
                }


                for (int i = 0; i <binding.radioGroupQuiz.getChildCount(); i++) {
                    binding.radioGroupQuiz.getChildAt(i).setEnabled(false);
                }

            }
        });

        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String right_option= "";
                if (quizDetails.getRightAnswer().equalsIgnoreCase("option_1")){
                    right_option = quizDetails.getOption1();
                }else if (quizDetails.getRightAnswer().equalsIgnoreCase("option_2")){
                    right_option = quizDetails.getOption2();
                }else if (quizDetails.getRightAnswer().equalsIgnoreCase("option_3")){
                    right_option = quizDetails.getOption3();
                }else if (quizDetails.getRightAnswer().equalsIgnoreCase("option_4")){
                    right_option = quizDetails.getOption4();
                }

                String msg = "Correct Answer is \n"+ "'" +right_option +"'";
                SweetAlt.OpenFreeCoinDialog(context, msg);

            }
        });

    }


    private void CheckRightAns(String checked_radio, RadioButton radio, ImageView ivIQuiz) {
        if (checked_radio != null) {
            if (checked_radio.equalsIgnoreCase(quizDetails.getRightAnswer())) {
                //Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                radio.setTextColor(context.getResources().getColor(R.color.green));
                ivIQuiz.setVisibility(View.GONE);

                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "1", quizDetails.getQuestionId());//1=right ans

                // got_total_reward = got_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvUserCoin.setText(""+quizDetails.getReward());

                int user_win_reward = Integer.parseInt( quizDetails.getReward());
                quizDetails.setUserTotalReward(String.valueOf(user_win_reward));
                userTotalReward = userTotalReward + user_win_reward;

                Utils.showToast(getActivity(), Constant.Right, true);
            } else {
                // Toast.makeText(context, "wrong", Toast.LENGTH_SHORT).show();
                radio.setTextColor(context.getResources().getColor(R.color.red));
                ivIQuiz.setVisibility(View.VISIBLE);

                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "0", quizDetails.getQuestionId());//1=right ans//0= wrong answer
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvOntuCoin.setText(""+quizDetails.getReward());

                Utils.showToast(getActivity(), Constant.Wrong, false);

            }
            quizDetails.setAttemptQuiz(true);
            if(!isPlayed){
                Utils.playMusic(R.raw.coin_sound, context);
                isPlayed= true;
            }
        }
    }

}
