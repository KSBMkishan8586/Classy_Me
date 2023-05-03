package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;
import static com.ksbm.ontu.utils.Constant.Passing_percent;
import static com.ksbm.ontu.utils.Utils.calculateNoOfColumns;
import static com.ksbm.ontu.utils.Utils.findWordForRightHanded;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.FoundationTextDragDropBinding;
import com.ksbm.ontu.foundation.adapter.Foundation_Drag_Drop_Adapter;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Foundation_Drag_Drop extends Fragment {
    FoundationTextDragDropBinding binding;
    RelativeLayout resultView, resultView_other, resultView_3;
    static int position = 0;
    int item_count = 0;
    SessionManager sessionManager;
    FoundationQuizModel.FoundationQuizResponse quizDetails;
    List<FoundationQuizModel.FoundationWord> wordQuizLists = new ArrayList<>();
    List<WordResultStatus> wordResultStatusList = new ArrayList<>();
    int total_right_ans = 0;
    Context context;
    boolean isPlayed= false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_text_drag_drop, container, false);
        View view = binding.getRoot();//using data binding
        context= getActivity();
        sessionManager = new SessionManager(getActivity());

        resultView = view.findViewById(R.id.resultView);
        resultView_other = view.findViewById(R.id.resultView_other);
        resultView_3 = view.findViewById(R.id.resultView_3);

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
           // quiz_id= getArguments().getString("quiz_id");

            binding.tvWorkbookname.setText(quizDetails.getQuestionText());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());
          //  binding.tvHeader.setText(quizDetails.getQuizType());

            binding.tvRightTopic.setText(quizDetails.getHeading1());
            binding.tvWrongTopic.setText(quizDetails.getHeading2());

            if (quizDetails.getHeading3()!=null && !quizDetails.getHeading3().isEmpty()){
                binding.tv3rdTopic.setText(quizDetails.getHeading3());
                binding.resultView3.setVisibility(View.VISIBLE);
                binding.tv3rdTopic.setVisibility(View.VISIBLE);
            }
        }

        setOnListner();
        getTaskList();

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        Utils.showToast(getActivity(), Constant.Right, true);
                    }else {
                        CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "0", quizDetails.getQuestionId());//1=right ans//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+quizDetails.getReward());

                        Utils.showToast(getActivity(), Constant.Wrong, false);
                        Utils.playMusic(R.raw.coin_sound, context);
                    }

                if(!isPlayed){
                    Utils.playMusic(R.raw.coin_sound, context);
                    isPlayed= true;
                }
                    quizDetails.setAttemptQuiz(true);

            }
        });

        return view;
    }

    private void getTaskList() {
        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            Foundation_Drag_Drop_Adapter friendsAdapter = new Foundation_Drag_Drop_Adapter(wordQuizLists, getActivity(), "text");
            binding.recyclelist.setLayoutManager(new GridLayoutManager(getActivity(),calculateNoOfColumns(getActivity())));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }

    }

    private void setOnListner() {

        resultView.setOnDragListener(mydragListener);
        resultView_other.setOnDragListener(mydragListener1);
        resultView_3.setOnDragListener(mydragListener2);


        binding.result.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    int mOffset = binding.result.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    //  mTxtOffset.setText("" + mOffset);
                    String touch_word = findWordForRightHanded(binding.result.getText().toString(), mOffset);
                    //remove only the spaces at the beginning or end of the String
                    touch_word = touch_word.trim();
                    Log.e("touch_right_word", "R " + touch_word);

                    for (int i=0; i<wordResultStatusList.size(); i++){
                        if (touch_word.equalsIgnoreCase(wordResultStatusList.get(i).getQuizWords())){

                            String right_option= "";
                            if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_1")){
                                right_option = quizDetails.getHeading1();
                            }else if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_2")){
                                right_option = quizDetails.getHeading2();
                            }else if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_3")){
                                right_option = quizDetails.getHeading3();
                            }

                            String msg = "'" + wordResultStatusList.get(i).getQuizWords() + "' " + "is " + right_option;
                            SweetAlt.OpenFreeCoinDialog(context, msg);
                        }
                    }
                }

                return false;
            }
        });

        binding.result3.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    int mOffset = binding.result3.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    //  mTxtOffset.setText("" + mOffset);
                    String touch_word = findWordForRightHanded(binding.result3.getText().toString(), mOffset);
                    //remove only the spaces at the beginning or end of the String
                    touch_word = touch_word.trim();
                    Log.e("touch_right_word", "R " + touch_word);

                    for (int i=0; i<wordResultStatusList.size(); i++){
                        if (touch_word.equalsIgnoreCase(wordResultStatusList.get(i).getQuizWords())){

                            String right_option= "";
                            if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_1")){
                                right_option = quizDetails.getHeading1();
                            }else if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_2")){
                                right_option = quizDetails.getHeading2();
                            }else if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_3")){
                                right_option = quizDetails.getHeading3();
                            }

                            String msg = "'" + wordResultStatusList.get(i).getQuizWords() + "' " + "is " + right_option;
                            SweetAlt.OpenFreeCoinDialog(context, msg);
                        }
                    }
                }

                return false;
            }
        });

        binding.resultOther.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    int  mOffset = binding.resultOther.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    //  mTxtOffset.setText("" + mOffset);
                    String touch_word = findWordForRightHanded(binding.resultOther.getText().toString(), mOffset);
                    //remove only the spaces at the beginning or end of the String
                    touch_word = touch_word.trim();
                    Log.e("touch_right_word", "R " + touch_word);

                    for (int i=0; i<wordResultStatusList.size(); i++){
                        if (touch_word.equalsIgnoreCase(wordResultStatusList.get(i).getQuizWords())){

                            String right_option= "";
                            if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_1")){
                                right_option = quizDetails.getHeading1();
                            }else if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_2")){
                                right_option = quizDetails.getHeading2();
                            }else if (wordResultStatusList.get(i).getRightAnswer().equalsIgnoreCase("heading_3")){
                                right_option = quizDetails.getHeading3();
                            }

                            String msg = "'" + wordResultStatusList.get(i).getQuizWords() + "' " + "is " + right_option;
                            SweetAlt.OpenFreeCoinDialog(context, msg);
                        }
                    }
                }

                return false;
            }
        });

    }

    View.OnDragListener mydragListener = new View.OnDragListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    item_count++;

                    if (item_count == wordQuizLists.size()) {
                        binding.tvSubmit.setVisibility(View.VISIBLE);
                    }

                    // result.append(wordQuizLists.get(position).getQuizWords() + "\n");
                    view.setVisibility(View.INVISIBLE);

                    if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_1")) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "Right", Toast.LENGTH_SHORT).show();
                       // got_total_reward = got_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        total_right_ans = total_right_ans + 1;

                        binding.result.setMovementMethod(LinkMovementMethod.getInstance());
                        binding.result.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getWord() + "</font>" + "<br/>" ));

//                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "1", wordQuizLists.get(position).getQuiz_word_id());//1=right ans
//                        binding.ivUsersideCoinImg.setVisibility(View.VISIBLE);
//                        binding.ivOntuSideCoin.setVisibility(View.INVISIBLE);
//                        binding.tvUserCoin.setText(""+got_total_reward);

                    } else {
                        binding.result.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getWord() + "</font>" + "<br/>"));

                        //  Toast.makeText(Fundamental_Quiz_Drag_Drop.this, "Wrong", Toast.LENGTH_SHORT).show();
                        String right_option= "";
                        if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_1")){
                            right_option = quizDetails.getHeading1();
                        }else if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_2")){
                            right_option = quizDetails.getHeading2();
                        }else if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_3")){
                            right_option = quizDetails.getHeading3();
                        }

                        String msg = "'" + wordQuizLists.get(position).getWord() + "' " + "is " + right_option;
                        SweetAlt.OpenFreeCoinDialog(context, msg);
                    }

//                    int remain_coin= Integer.parseInt(binding.tvCoin.getText().toString()) -
//                            Integer.parseInt(wordQuizLists.get(position).getReward());
//                    binding.tvCoin.setText(""+remain_coin);

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getWord(),  wordQuizLists.get(position).getRightAnswer()));

                    break;
            }

            return true;
        }
    };

    View.OnDragListener mydragListener1 = new View.OnDragListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
//                    view.animate()
//                            .x(resultView.getX())
//                            .y(resultView.getY())
//                            .setDuration(500)
//                            .start();

                    item_count++;
                    if (item_count == wordQuizLists.size()) {
                        binding.tvSubmit.setVisibility(View.VISIBLE);
                    }

                    //  result_other.append(wordQuizLists.get(position).getQuizWords() + "\n");
                    view.setVisibility(View.INVISIBLE);

                    if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_2")) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "right", Toast.LENGTH_SHORT).show();
                        binding.resultOther.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getWord() + "</font>"+ "<br/>"));
//                        got_total_reward = got_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        total_right_ans = total_right_ans + 1;

//                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "1", wordQuizLists.get(position).getQuiz_word_id());//1=right ans
//                        binding.ivUsersideCoinImg.setVisibility(View.VISIBLE);
//                        binding.ivOntuSideCoin.setVisibility(View.INVISIBLE);
//                        binding.tvUserCoin.setText(""+got_total_reward);

                    } else {

                        //Toast.makeText(Fundamental_Quiz_Start.this, "Wrong", Toast.LENGTH_SHORT).show();
                        binding.resultOther.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getWord() + "</font>" + "<br/>"));

                        String right_option= "";
                        if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_1")){
                            right_option = quizDetails.getHeading1();
                        }else if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_2")){
                            right_option = quizDetails.getHeading2();
                        }else if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_3")){
                            right_option = quizDetails.getHeading3();
                        }

                        String msg = "'" + wordQuizLists.get(position).getWord() + "' " + "is " + right_option;
                        SweetAlt.OpenFreeCoinDialog(context, msg);

                    }

//                    int remain_coin= Integer.parseInt(binding.tvCoin.getText().toString()) -
//                            Integer.parseInt(wordQuizLists.get(position).getReward());
//                    binding.tvCoin.setText(""+remain_coin);

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getWord(),  wordQuizLists.get(position).getRightAnswer()));

                    break;
            }

            return true;
        }
    };

    View.OnDragListener mydragListener2 = new View.OnDragListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
//                    view.animate()
//                            .x(resultView.getX())
//                            .y(resultView.getY())
//                            .setDuration(500)
//                            .start();

                    item_count++;
                    if (item_count == wordQuizLists.size()) {
                        binding.tvSubmit.setVisibility(View.VISIBLE);
                    }

                    //  result_other.append(wordQuizLists.get(position).getQuizWords() + "\n");
                    view.setVisibility(View.INVISIBLE);

                    if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_3")) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "right", Toast.LENGTH_SHORT).show();
                        binding.result3.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getWord() + "</font>"+ "<br/>"));
//                        got_total_reward = got_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        total_right_ans = total_right_ans + 1;

//                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "1", wordQuizLists.get(position).getQuiz_word_id());//1=right ans
//                        binding.ivUsersideCoinImg.setVisibility(View.VISIBLE);
//                        binding.ivOntuSideCoin.setVisibility(View.INVISIBLE);
//                        binding.tvUserCoin.setText(""+got_total_reward);

                    } else {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "Wrong", Toast.LENGTH_SHORT).show();
                        binding.result3.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getWord() + "</font>" + "<br/>"));

                        String right_option= "";
                        if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_1")){
                            right_option = quizDetails.getHeading1();
                        }else if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_2")){
                            right_option = quizDetails.getHeading2();
                        }else if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase("heading_3")){
                            right_option = quizDetails.getHeading3();
                        }

                        String msg = "'" + wordQuizLists.get(position).getWord() + "' " + "is " + right_option;
                        SweetAlt.OpenFreeCoinDialog(context, msg);

                    }

//                    int remain_coin= Integer.parseInt(binding.tvCoin.getText().toString()) -
//                            Integer.parseInt(wordQuizLists.get(position).getReward());
//                    binding.tvCoin.setText(""+remain_coin);

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getWord(),  wordQuizLists.get(position).getRightAnswer()));

                    break;
            }

            return true;
        }
    };

    public static void DragTextItem(View v, int pos) {
        position = pos;
      //  view = v;

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
    }


}
