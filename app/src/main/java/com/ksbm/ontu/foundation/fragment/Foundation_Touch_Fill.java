package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;
import static com.ksbm.ontu.utils.Constant.Passing_percent;
import static com.ksbm.ontu.utils.Utils.calculateNoOfColumns;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.FoundationTouchFillBinding;
import com.ksbm.ontu.databinding.FoundationTouchWordBinding;
import com.ksbm.ontu.foundation.activity.FoundationQuizActivity;
import com.ksbm.ontu.foundation.adapter.Foundation_Drag_Drop_Adapter;
import com.ksbm.ontu.foundation.adapter.Foundation_TouchFillWord_Adapter;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizTouchFill;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Winner;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Foundation_Touch_Fill extends Fragment {
    static FoundationTouchFillBinding binding;
    RelativeLayout resultView;
    SessionManager sessionManager;
    static FoundationQuizModel.FoundationQuizResponse quizDetails;
    //static List<FoundationQuizModel.FoundationWord> wordQuizLists = new ArrayList<>();
    static int total_right_ans = 0;
    static int quiz_position = 0;
    Context context;
    boolean isPlayed = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_touch_fill, container, false);
        View view = binding.getRoot();//using data binding
        context = getActivity();
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


        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n" + "'" + quizDetails.getWords().get(quiz_position).getAnswer() + "'";
                SweetAlt.OpenFreeCoinDialog(getActivity(), msg);

            }
        });

        binding.ivLeftEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quiz_position > 0) {
                    quiz_position--;
                    setQuestion();
                }
            }
        });

        binding.ivRightEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizDetails.getWords().get(quiz_position).isClickAnswer()) {
                    if (quiz_position != quizDetails.getWords().size() - 1) {
                        quiz_position++;
                        setQuestion();
                    } else {
                        binding.tvSubmit.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get passing percent
                Log.e("total_right_ans", "" + total_right_ans);
                double amount = Double.parseDouble(String.valueOf(total_right_ans));
                double overall_percent = (amount / Double.parseDouble(String.valueOf(quizDetails.getWords().size()))) * 100;
                Log.e("overall_per", "" + overall_percent);
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
                quizDetails.setAttemptQuiz(true);
                if (!isPlayed) {
                    Utils.playMusic(R.raw.coin_sound, context);
                    isPlayed = true;
                }
                FoundationQuizActivity.binding.layoutButton.tvNext.performClick();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        quizDetails = null;
        total_right_ans = 0;
        quiz_position = 0;
        if (getArguments() != null) {
            quizDetails = (FoundationQuizModel.FoundationQuizResponse) getArguments().getSerializable("QuestionDetails");
            // quiz_id= getArguments().getString("quiz_id");

            binding.tvWorkbookname.setText(quizDetails.getQuestionText());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());

        }


        getTaskList();
    }

    private void getTaskList() {
        quiz_position = 0;
        binding.tvQuizName.setText(quizDetails.getHeading());

        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            Log.e("word_size_touch_fill", "" + quizDetails.getWords().size());
            // wordQuizLists.clear();
            // wordQuizLists = quizDetails.getWords();

            ArrayList<String> list = new ArrayList<String>();

            for (int i = 0; i < quizDetails.getWords().size(); i++) {
                list.add(quizDetails.getWords().get(i).getAnswer());
            }
            Collections.shuffle(list);

            Foundation_TouchFillWord_Adapter friendsAdapter = new Foundation_TouchFillWord_Adapter(list, getActivity(), "text_touch_fill");
            binding.recyclelist.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns(getActivity())));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();

            //********
            setQuestion();

        }
    }

    private void setQuestion() {
        binding.tvAns.setText("");
        binding.ivIQuiz.setVisibility(View.GONE);

        binding.tvQuestion.setText(quizDetails.getWords().get(quiz_position).getQuestionText());
        Glide.with(context)
                //.setDefaultRequestOptions(requestOptions)
                .load(quizDetails.getWords().get(quiz_position).getQuestionImage())
                .into(binding.ivQuizQues);
    }

    @SuppressLint("SetTextI18n")
    public static void CheckRightAnswer(CardView view, int position, String word, Context context) {
        // this.view=view;

        //       if (!quizDetails.getWords().get(quiz_position).isClickAnswer()){
        binding.tvAns.setText(word);


        if (quizDetails.getWords().get(quiz_position).getAnswer().equalsIgnoreCase(word)) {
            //view.setBackgroundResource(R.drawable.circle_fill_green);
            view.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            // Toast.makeText(, "Right", Toast.LENGTH_SHORT).show();

            quizDetails.getWords().get(quiz_position).setAns_right(true);
            binding.ivIQuiz.setVisibility(View.GONE);
            total_right_ans = total_right_ans + 1;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    view.setVisibility(View.INVISIBLE);
//                        wordQuizLists.get(quiz_position).setQuizQuestion(
//                                wordQuizLists.get(quiz_position).getComplete_right_answer());
                }
            }, 100);

        } else {
            //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            // view.setBackgroundResource(R.drawable.circle_fill_red);
            view.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            quizDetails.getWords().get(quiz_position).setAns_right(false);
            binding.ivIQuiz.setVisibility(View.VISIBLE);

            // got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());

//                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.getQuizData().get(quiz_position).getReward(), "0", quizDetails.getQuizData().get(quiz_position).getQuiz_question_id());//0= wrong answer
//                binding.ivOntuSideCoin.setVisibility(View.VISIBLE);
//                binding.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
//                binding.tvOntuCoin.setText(""+got_ontu_total_reward);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // view.setBackgroundResource(R.drawable.list_row_selector);
                    view.setCardBackgroundColor(context.getResources().getColor(R.color.word_color1));
                }
            }, 500);


        }

//            int remain_coin= Integer.parseInt(binding.tvCoin.getText().toString()) -
//                    Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
//            binding.tvCoin.setText(""+remain_coin);

        quizDetails.getWords().get(quiz_position).setClickAnswer(true);
        //}


    }


}
