package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationMcqImageBinding;
import com.ksbm.ontu.databinding.FoundationMcqTextBinding;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Foundation_Objective_Image extends Fragment {
    static FoundationMcqImageBinding binding;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_mcq_image, container, false);
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

            // binding.tvWorkbookname.setText(quizDetails.getHeading());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());
            //  binding.tvHeader.setText(quizDetails.getQuizType());

            // binding.tvRightTopic.setText(quizDetails.getHeading1());

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

       // if (sub_pattern_type.equalsIgnoreCase("text_to_image")){
            binding.tvQuestion.setVisibility(View.VISIBLE);
            binding.ivQuizImage.setVisibility(View.GONE);
            binding.tvQuestion.setText(quizDetails.getQuestionText());

       // }

        Glide.with(context)
                //.setDefaultRequestOptions(requestOptions)
                .load(quizDetails.getOption1())
                .into(binding.ivRadio1);

        Glide.with(context)
                //.setDefaultRequestOptions(requestOptions)
                .load(quizDetails.getOption2())
                .into(binding.ivRadio2);

        if (!quizDetails.getOption3().isEmpty() && quizDetails.getOption3() != null) {
            binding.llRadio3.setVisibility(View.VISIBLE);
            Glide.with(context)
                    //.setDefaultRequestOptions(requestOptions)
                    .load(quizDetails.getOption3())
                    .into(binding.ivRadio3);

        } else {
            binding.llRadio3.setVisibility(View.INVISIBLE);
        }

        if (!quizDetails.getOption4().isEmpty() && quizDetails.getOption4() != null) {
            binding.llRadio4.setVisibility(View.VISIBLE);
            Glide.with(context)
                    //.setDefaultRequestOptions(requestOptions)
                    .load(quizDetails.getOption4())
                    .into(binding.ivRadio4);
        } else {
            binding.llRadio4.setVisibility(View.INVISIBLE);
        }


//        if (quizDetails.isChecked_radio_button()) {
//            for (int i = 0; i < binding.radioGroupQuiz.getChildCount(); i++) {
//                binding.radioGroupQuiz.getChildAt(i).setEnabled(false);
//            }
//        }

        //**********
//        binding.radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
//
//                if (!quizDetails.isChecked_radio_button()) {
//                    quizDetails.setChecked_radio_button(true);
//
//                    if (checkedId == R.id.radio1) {
//                        quizDetails.setChecked_radio("option_1");
//                        CheckRightAns(quizDetails.isChecked_radio(), binding.radio1,  binding.ivIQuiz);
//                    } else if (checkedId == R.id.radio2) {
//                        quizDetails.setChecked_radio("option_2");
//                        CheckRightAns(quizDetails.isChecked_radio(),  binding.radio2, binding.ivIQuiz);
//                    } else if (checkedId == R.id.radio3) {
//                        quizDetails.setChecked_radio("option_3");
//                        CheckRightAns(quizDetails.isChecked_radio(),  binding.radio3, binding.ivIQuiz);
//                    } else if (checkedId == R.id.radio4) {
//                        quizDetails.setChecked_radio("option_4");
//                        CheckRightAns(quizDetails.isChecked_radio(), binding.radio4,  binding.ivIQuiz);
//                    }
//                }
//
//
//                for (int i = 0; i <binding.radioGroupQuiz.getChildCount(); i++) {
//                    binding.radioGroupQuiz.getChildAt(i).setEnabled(false);
//                }
//
//            }
//        });

        binding.radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                quizDetails.setChecked_radio("option_1");
                CheckRightAns(quizDetails.isChecked_radio(), binding.radio1,  binding.ivIQuiz);

                binding.radio2.setEnabled(false);
                binding.radio3.setEnabled(false);
                binding.radio4.setEnabled(false);
            }
        });

        binding.radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                quizDetails.setChecked_radio("option_2");
                CheckRightAns(quizDetails.isChecked_radio(),  binding.radio2, binding.ivIQuiz);

                binding.radio1.setEnabled(false);
                binding.radio3.setEnabled(false);
                binding.radio4.setEnabled(false);
            }
        });

        binding.radio3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                quizDetails.setChecked_radio("option_3");
                CheckRightAns(quizDetails.isChecked_radio(),  binding.radio3, binding.ivIQuiz);

                binding.radio2.setEnabled(false);
                binding.radio1.setEnabled(false);
                binding.radio4.setEnabled(false);
            }
        });

        binding.radio4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                quizDetails.setChecked_radio("option_4");
                CheckRightAns(quizDetails.isChecked_radio(), binding.radio4,  binding.ivIQuiz);

                binding.radio2.setEnabled(false);
                binding.radio3.setEnabled(false);
                binding.radio1.setEnabled(false);
            }
        });

        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n"+ "'" +quizDetails.getRightAnswer() +"'";
                SweetAlt.OpenFreeCoinDialog(context, msg);

            }
        });

    }

    private void CheckRightAns(String checked_radio, RadioButton radio, ImageView ivIQuiz) {
        if (checked_radio != null) {
            if (checked_radio.equalsIgnoreCase(quizDetails.getRightAnswer())) {
                Utils.showToast(getActivity(), "Right", true);
               // radio.setTextColor(context.getResources().getColor(R.color.green));
                ivIQuiz.setVisibility(View.GONE);

                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "1", quizDetails.getQuestionId());//1=right ans

                // got_total_reward = got_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvUserCoin.setText(""+quizDetails.getReward());

                int user_win_reward = Integer.parseInt( quizDetails.getReward());
                quizDetails.setUserTotalReward(String.valueOf(user_win_reward));
                userTotalReward = userTotalReward + user_win_reward;

            } else {
                Utils.showToast(getActivity(), "Wrong", false);
               // radio.setTextColor(context.getResources().getColor(R.color.red));
                ivIQuiz.setVisibility(View.VISIBLE);

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

}
