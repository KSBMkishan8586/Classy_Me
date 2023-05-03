package com.ksbm.ontu.fundamental.activity;

import static com.ksbm.ontu.utils.Utils.findWordForRightHanded;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.ActivityFundamentalQuizStartBinding;
import com.ksbm.ontu.fundamental.adapter.Fundamental_Drag_Drop_Adapter;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.QuizDetails;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Word_Quiz_List;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fundamental_Quiz_Drag_Drop extends AppCompatActivity {
    ActivityFundamentalQuizStartBinding binding;
    // TextView result, result_other, result_3;
    RelativeLayout resultView, resultView_other, resultView_3;
    MediaPlayer mediaPlayer;
    int position = 0;
    int item_count = 0;
    View view;
    SessionManager sessionManager;
    QuizDetails quizDetails;
    String quiz_id;
    List<Word_Quiz_List> wordQuizLists = new ArrayList<>();
    List<WordResultStatus> wordResultStatusList = new ArrayList<>();
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;
    int total_right_ans = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fundamental_quiz__start);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental_quiz__start);

        sessionManager = new SessionManager(this);

        resultView = findViewById(R.id.resultView);
        resultView_other = findViewById(R.id.resultView_other);
        resultView_3 = findViewById(R.id.resultView_3);

        String arr[] = sessionManager.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];

        binding.relToolbar.tvLoggedUerName.setText(firstWord);
        binding.tvWorkbookname.setText(sessionManager.getWorkbook().getWorkbookName());
        binding.relToolbar.tvCoin.setText(sessionManager.getWorkbook().getReward());
        binding.tvHeader.setText(sessionManager.getWorkbook().getFundamental_name());
        //   binding.tvHeader.setText(SessionManager.getMyPref(this).getString(sessionManager.getWorkbook().getFundamental_name(), ""));
        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.relToolbar.ivLoggedUser);
        }
        if (getIntent() != null) {
            quizDetails = (QuizDetails) getIntent().getParcelableExtra("QuizDetails");
            quiz_id= getIntent().getStringExtra("quiz_id");

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
                Intent intent = new Intent(Fundamental_Quiz_Drag_Drop.this, Fundamental_Quiz_Winner.class);
                intent.putExtra("QuizResult", String.valueOf(got_total_reward));
                startActivity(intent);
                finish();
            }
        });
    }


    private void getTaskList() {
        if (quizDetails.getWords() != null && quizDetails.getWords().size() > 0) {
            wordQuizLists = quizDetails.getWords();

            Fundamental_Drag_Drop_Adapter friendsAdapter = new Fundamental_Drag_Drop_Adapter(wordQuizLists, Fundamental_Quiz_Drag_Drop.this);
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }

    }


    private void setOnListner() {

        resultView.setOnDragListener(mydragListener);
        resultView_other.setOnDragListener(mydragListener1);
        resultView_3.setOnDragListener(mydragListener2);

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
                String msg = "Please drag the words in below box and make a coin.";
                SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);

            }
        });


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
                            String msg = "'" + wordResultStatusList.get(i).getQuizWords() + "' " + "is " + wordResultStatusList.get(i).getRightAnswer();
                            SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);

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
                            String msg = "'" + wordResultStatusList.get(i).getQuizWords() + "' " + "is " + wordResultStatusList.get(i).getRightAnswer();
                            SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);

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
                            String msg = "'" + wordResultStatusList.get(i).getQuizWords() + "' " + "is " + wordResultStatusList.get(i).getRightAnswer();
                            SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);

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

                    if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase(quizDetails.getHeading1En())) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "Right", Toast.LENGTH_SHORT).show();
                        got_total_reward = got_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        total_right_ans = total_right_ans + 1;

                        binding.result.setMovementMethod(LinkMovementMethod.getInstance());

                        binding.result.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getQuizWords() + "</font>" + "<br/>" ));

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "1", wordQuizLists.get(position).getQuiz_word_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Drag_Drop.this);

                    } else {
                        binding.result.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getQuizWords() + "</font>" + "<br/>"));

                        //  Toast.makeText(Fundamental_Quiz_Drag_Drop.this, "Wrong", Toast.LENGTH_SHORT).show();

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, wordQuizLists.get(position).getReward(), "0", wordQuizLists.get(position).getQuiz_word_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);

                        String msg = "'" + wordQuizLists.get(position).getQuizWords() + "' " + "is " + wordQuizLists.get(position).getRightAnswer();
                        SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Quiz_Drag_Drop.this);
                    }

                    int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                            Integer.parseInt(wordQuizLists.get(position).getReward());
                    binding.relToolbar.tvCoin.setText(""+remain_coin);

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getQuizWords(),  wordQuizLists.get(position).getRightAnswer()));
//                    Utils.playMusic(R.raw.coin_sound, Fundamental_Quiz_Drag_Drop.this);
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

                    if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase(quizDetails.getHeading2En())) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "right", Toast.LENGTH_SHORT).show();
                        binding.resultOther.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getQuizWords() + "</font>"+ "<br/>"));
                        got_total_reward = got_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        total_right_ans = total_right_ans + 1;

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "1", wordQuizLists.get(position).getQuiz_word_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);

                    } else {

                        //Toast.makeText(Fundamental_Quiz_Start.this, "Wrong", Toast.LENGTH_SHORT).show();
                        binding.resultOther.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getQuizWords() + "</font>" + "<br/>"));

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "0", wordQuizLists.get(position).getQuiz_word_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);

                        String msg = "'" + wordQuizLists.get(position).getQuizWords() + "' " + "is " + wordQuizLists.get(position).getRightAnswer();
                        SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);

                    }

                    int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                            Integer.parseInt(wordQuizLists.get(position).getReward());
                    binding.relToolbar.tvCoin.setText(""+remain_coin);

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getQuizWords(),  wordQuizLists.get(position).getRightAnswer()));

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

                    if (wordQuizLists.get(position).getRightAnswer().equalsIgnoreCase(quizDetails.getHeading3En())) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "right", Toast.LENGTH_SHORT).show();
                        binding.result3.append(Html.fromHtml("<font color=#000000>"+ wordQuizLists.get(position).getQuizWords() + "</font>"+ "<br/>"));
                        got_total_reward = got_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        total_right_ans = total_right_ans + 1;

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "1", wordQuizLists.get(position).getQuiz_word_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText(""+got_total_reward);

                    } else {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "Wrong", Toast.LENGTH_SHORT).show();
                        binding.result3.append(Html.fromHtml("<font color=#FF0000>"+ wordQuizLists.get(position).getQuizWords() + "</font>" + "<br/>"));

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(wordQuizLists.get(position).getReward());
                        binding.relToolbar.tvOntuCoin.setText(""+got_ontu_total_reward);

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id,wordQuizLists.get(position).getReward(), "0", wordQuizLists.get(position).getQuiz_word_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);

                        String msg = "'" + wordQuizLists.get(position).getQuizWords() + "' " + "is " + wordQuizLists.get(position).getRightAnswer();
                        SweetAlt.OpenFreeCoinDialog(Fundamental_Quiz_Drag_Drop.this, msg);

                    }

                    int remain_coin= Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                            Integer.parseInt(wordQuizLists.get(position).getReward());
                    binding.relToolbar.tvCoin.setText(""+remain_coin);

                    wordResultStatusList.add(new WordResultStatus(wordQuizLists.get(position).getQuizWords(),  wordQuizLists.get(position).getRightAnswer()));

                    break;
            }

            return true;
        }
    };


    public void DragTextItem(View v, int position) {
        this.position = position;
        this.view = v;

        if (mediaPlayer != null) {
            // mediaPlayer = MediaPlayer.create(Fundamental_Quiz_Drag_Drop.this, R.raw.sound_jumble);
            mediaPlayer.start();
        }

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
    }


    public void onDestroy() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.onDestroy();

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