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

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.FoundationCheckBoxBinding;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_CheckBox;
import com.ksbm.ontu.foundation.adapter.Foundation_CheckBox_Quiz_Adapter;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Foundation_CheckBox extends Fragment {
    static FoundationCheckBoxBinding binding;
    static SessionManager sessionManager;
    static FoundationQuizModel.FoundationQuizResponse quizDetails;
    String quiz_id;
    static List<FoundationQuizModel.FoundationWord> wordQuizLists = new ArrayList<>();
    Context context;
    Foundation_CheckBox_Quiz_Adapter adapter;
    boolean isPlayed= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_check_box, container, false);
        View view = binding.getRoot();//using data binding
        context = getActivity();
        sessionManager = new SessionManager(getActivity());

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
            binding.tvWorkbookname.setText(quizDetails.getHeading());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());

            binding.tvHeading2.setText(quizDetails.getHeading1());
            binding.tvHeading3.setText(quizDetails.getHeading2());

            if (quizDetails.getHeading2() != null && !quizDetails.getHeading2().isEmpty()) {
                binding.tvHeading3.setText(quizDetails.getHeading2());
                binding.tvHeading3.setVisibility(View.VISIBLE);
            } else {
                binding.tvHeading3.setVisibility(View.INVISIBLE);
            }

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

        return view;
    }


    private void getTaskList() {
        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            adapter = new Foundation_CheckBox_Quiz_Adapter(wordQuizLists, getActivity(), quizDetails.getHeading1(), quizDetails.getHeading2(), quizDetails.getHeading3(), quiz_id, Foundation_CheckBox.this);
            binding.setPersonalityAdapter(adapter);//set databinding adapter
            adapter.notifyDataSetChanged();
        }

    }

    private void setOnListner() {
//        binding.ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

//        binding.ivIButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String msg = "Please check right answer and make a coin.";
//                SweetAlt.OpenFreeCoinDialog(getActivity(), msg);
//
//            }
//        });
    }


    @SuppressLint("SetTextI18n")
    public void FinalQuizResult(int total_rightanswer_count) {
        //get passing percent
        double amount = Double.parseDouble(String.valueOf(total_rightanswer_count));
        double overall_percent = (amount / Double.parseDouble(String.valueOf(quizDetails.getWords().size()))) * 100;

        //call coin api
        if ((int) overall_percent >= Passing_percent) {
            CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "1", quizDetails.getQuestionId());//1=right ans
            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
            binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
            binding.relToolbar.tvUserCoin.setText("" + quizDetails.getReward());

            int user_win_reward = Integer.parseInt(quizDetails.getReward());
            quizDetails.setUserTotalReward(String.valueOf(user_win_reward));
            userTotalReward = userTotalReward + user_win_reward;

           // Utils.showToast(getActivity(), Constant.Right, true);
        } else {
            CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "0", quizDetails.getQuestionId());//1=right ans//0= wrong answer
            binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
            binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
            binding.relToolbar.tvOntuCoin.setText("" + quizDetails.getReward());

           // Utils.showToast(getActivity(), Constant.Wrong, false);
        }

        if(!isPlayed){
            Utils.playMusic(R.raw.coin_sound, context);
            isPlayed= true;
        }
        quizDetails.setAttemptQuiz(true);
    }


}
