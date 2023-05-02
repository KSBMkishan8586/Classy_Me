package com.ksbm.ontu.foundation.fragment;

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
import com.ksbm.ontu.databinding.FoundationImageDragDropBinding;
import com.ksbm.ontu.foundation.adapter.Foundation_Drag_Drop_Adapter;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;
import static com.ksbm.ontu.utils.Utils.calculateNoOfColumns;
import static com.ksbm.ontu.utils.Utils.findWordForRightHanded;

public class Foundation_Drag_Drop_Image extends Fragment {
    FoundationImageDragDropBinding binding;
    RelativeLayout resultView;
    static int position = 0;
    int item_count = 0;
    SessionManager sessionManager;
    FoundationQuizModel.FoundationQuizResponse quizDetails;
    List<FoundationQuizModel.FoundationWord> wordQuizLists = new ArrayList<>();
    List<WordResultStatus> wordResultStatusList = new ArrayList<>();
    Context context;
    static boolean isDrag=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.foundation_image_drag_drop, container, false);
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
            // quiz_id= getArguments().getString("quiz_id");

            binding.tvWorkbookname.setText(quizDetails.getHeading());
            binding.relToolbar.tvCoin.setText(quizDetails.getReward());
            //  binding.tvHeader.setText(quizDetails.getQuizType());

           // binding.tvRightTopic.setText(quizDetails.getHeading1());

        }

        setOnListner();
        getTaskList();

        return view;
    }

    private void getTaskList() {
        isDrag= false;
        if (quizDetails.getQuestionImage()!=null && !quizDetails.getQuestionImage().isEmpty()){
            Glide.with(context)
                    //.setDefaultRequestOptions(requestOptions)
                    .load(quizDetails.getQuestionImage())
                    .into(binding.ivQues);
        }

        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            Foundation_Drag_Drop_Adapter friendsAdapter = new Foundation_Drag_Drop_Adapter(wordQuizLists, getActivity(), "image");
            binding.recyclelist.setLayoutManager(new GridLayoutManager(getActivity(),calculateNoOfColumns(getActivity())));
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }
    }

    private void setOnListner() {

        resultView.setOnDragListener(mydragListener);

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

                            String msg = "Right Answer " + "is " + quizDetails.getRightAnswer();
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
                    isDrag= true;

                    if (item_count == wordQuizLists.size()) {
                      //  binding.tvSubmit.setVisibility(View.VISIBLE);
                    }

                    // result.append(wordQuizLists.get(position).getQuizWords() + "\n");
                    view.setVisibility(View.INVISIBLE);

                    if (wordQuizLists.get(position).getWord().equalsIgnoreCase(quizDetails.getRightAnswer())) {

                        binding.result.setMovementMethod(LinkMovementMethod.getInstance());
                        binding.result.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getWord() + "</font>" + "<br/>" ));

                        CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "1", quizDetails.getQuestionId());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+quizDetails.getReward());

                        int user_win_reward = Integer.parseInt( quizDetails.getReward());
                        quizDetails.setUserTotalReward(String.valueOf(user_win_reward));
                        userTotalReward = userTotalReward + user_win_reward;

                        Utils.showToast(getActivity(), Constant.Right, true);

                    } else {
                        binding.result.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getWord() + "</font>" + "<br/>"));
                        //  Toast.makeText(Fundamental_Quiz_Drag_Drop.this, "Wrong", Toast.LENGTH_SHORT).show();
                        CommonUtil.SubmitFoundationActivityQuiz(sessionManager.getUser().getUserid(), quizDetails.getFoundationId(), quizDetails.getReward(), "0", quizDetails.getQuestionId());//1=right ans//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+quizDetails.getReward());

                        String msg = "Right Answer " + "is " + quizDetails.getRightAnswer();
                        SweetAlt.OpenFreeCoinDialog(context, msg);

                        Utils.showToast(getActivity(), Constant.Wrong, false);
                    }

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getWord(),  wordQuizLists.get(position).getRightAnswer()));
                    quizDetails.setAttemptQuiz(true);
                    Utils.playMusic(R.raw.coin_sound, context);

                    break;
            }

            return true;
        }
    };

    public static void DragTextItem(View v, int pos) {
        if (!isDrag){
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

}
