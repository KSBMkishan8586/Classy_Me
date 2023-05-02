package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;
import static com.ksbm.ontu.utils.Utils.calculateNoOfColumns;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationImageDragDropBinding;
import com.ksbm.ontu.databinding.FoundationTouchWordBinding;
import com.ksbm.ontu.foundation.adapter.Foundation_Drag_Drop_Adapter;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizTouchFill;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Foundation_Touch_Word_Text extends Fragment {
    static FoundationTouchWordBinding binding;
    RelativeLayout resultView;
   static SessionManager sessionManager;
    static FoundationQuizModel.FoundationQuizResponse quizDetails;
    String sub_pattern_type;
    List<FoundationQuizModel.FoundationWord> wordQuizLists = new ArrayList<>();
    public static Context context;
    static boolean isDrag=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_touch_word, container, false);
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

            binding.tvWorkbookname.setText(quizDetails.getQuestionText());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());
        }

        getTaskList();

        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n"+ "'" +quizDetails.getRightAnswer() +"'";
                SweetAlt.OpenFreeCoinDialog(getActivity(), msg);

            }
        });

        return view;
    }


    private void getTaskList() {
        isDrag= false;

        binding.tvQuizName.setText(quizDetails.getHeading());

        if (quizDetails.getFoundationId().equalsIgnoreCase(Constant.ABCD)){
            binding.tvAlphabetName.setVisibility(View.VISIBLE);
            binding.tvQuestion.setVisibility(View.GONE);
            binding.tvAlphabetName.setText(quizDetails.getQuestionText());
        }else {
            binding.tvAlphabetName.setVisibility(View.GONE);
            binding.tvQuestion.setVisibility(View.VISIBLE);
            binding.tvQuestion.setText(quizDetails.getQuestionText());
        }

        binding.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvQuestion.getText().toString(), false);
            }
        });

        binding.tvAlphabetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvAlphabetName.getText().toString(), false);
            }
        });

        if (sub_pattern_type.equalsIgnoreCase("image_to_text")){
            binding.tvQuestion.setVisibility(View.GONE);
            binding.ivQuesImg.setVisibility(View.VISIBLE);

            Glide.with(context)
                    //.setDefaultRequestOptions(requestOptions)
                    .load(quizDetails.getQuestionImage())
                    .into(binding.ivQuesImg);
        }

        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            Foundation_Drag_Drop_Adapter friendsAdapter = new Foundation_Drag_Drop_Adapter(wordQuizLists, getActivity(), "text_touch_word");
            binding.recyclelist.setLayoutManager(new GridLayoutManager(getActivity(),calculateNoOfColumns(getActivity())));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("SetTextI18n")
    public static  void CheckRightAnswer(CardView view, int position, String word) {

      if (!isDrag){
            if (word.equals(quizDetails.getRightAnswer())){
               // view.setBackgroundResource(R.drawable.circle_fill_green);
                view.setCardBackgroundColor(context.getResources().getColor(R.color.green));
                //Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();

               // quizDetails.getQuizData().get(quiz_position).setAns_right(true);
                binding.ivIQuiz.setVisibility(View.GONE);

                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "1", quizDetails.getQuestionId());//1=right ans
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvUserCoin.setText(""+quizDetails.getReward());

                int user_win_reward = Integer.parseInt( quizDetails.getReward());
                quizDetails.setUserTotalReward(String.valueOf(user_win_reward));
                userTotalReward = userTotalReward + user_win_reward;

                Utils.showToast((FragmentActivity) context, Constant.Right, true);
            }else {
                //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                //view.setBackgroundResource(R.drawable.circle_fill_red);
                view.setCardBackgroundColor(context.getResources().getColor(R.color.red));
               // quizDetails.getQuizData().get(quiz_position).setAns_right(false);
                binding.ivIQuiz.setVisibility(View.VISIBLE);

              //  got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());

                CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "0", quizDetails.getQuestionId());//1=right ans//0= wrong answer
                binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                binding.relToolbar.tvOntuCoin.setText(""+quizDetails.getReward());

                Utils.showToast((FragmentActivity) context, Constant.Wrong, false);

            }

            int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                    Integer.parseInt(quizDetails.getReward());
            binding.relToolbar.tvCoin.setText(""+remain_coin);

           // quizDetails.getQuizData().get(quiz_position).setClickAnswer(true);

          isDrag= true;
          quizDetails.setAttemptQuiz(true);

        }

    }
}
