package com.ksbm.ontu.situation.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentMannersBinding;
import com.ksbm.ontu.databinding.FragmentTouchFillBinding;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizTouchFill;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.adapter.SituationJumbleWord_Adapter;
import com.ksbm.ontu.situation.adapter.SituationTouchWord_Adapter;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Touch_Fill extends Fragment implements TextToSpeech.OnInitListener {
    FragmentTouchFillBinding binding;
    SessionManager sessionManager;
    SituationQuestionModel.QuestionList questionDetails;
    List<SituationQuestionModel.Word_Question> wordQuizLists = new ArrayList<>();
    TextView result;
    int position = 0;
    int word_position = 0;
    TextToSpeech textToSpeech;
    HashMap<String, String> map = new HashMap<>();
    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_touch_fill, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        textToSpeech = new TextToSpeech(getActivity(), this);

        result = (TextView) view.findViewById(R.id.result);

        if (getArguments() != null) {
            questionDetails = (SituationQuestionModel.QuestionList) getArguments().getSerializable("QuestionDetails");
        }


        setQuiz();

        binding.tvQuizName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(), binding.tvQuizName.getText().toString(), false);
            }
        });

        binding.tvCheckAns.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (result.getText().toString().equalsIgnoreCase(questionDetails.getRightAnswer())){
                    Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                    binding.ivIQuiz.setVisibility(View.GONE);

                    CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), questionDetails.getSituationId(), questionDetails.getSentenceId(), questionDetails.getSituationQuestionId(),
                            questionDetails.getReward(), "1");//1=right ans

                    //animate coin
                    binding.tvUserCoin.setText("+ " + questionDetails.getReward());
                    binding.llGetCoin.setVisibility(View.VISIBLE);
                    Utils.playMusic(R.raw.coin_sound, getActivity());
                    Toast.makeText(getActivity(), questionDetails.getSentenceId()+"", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("ResumeQuize", MODE_PRIVATE).edit();
                    editor.putString("QuestionId", "touch and fill or fill in the blanks");
                    editor.putString("situation_id", questionDetails.getSituationId()+"");
                    editor.putString("situation_name", questionDetails.getSituation_heading()+"");
                    editor.putString("sentence_id", questionDetails.getSentenceId()+"");
                    editor.putBoolean("value", true);
                    editor.apply();

                }else {
                    Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                    binding.ivIQuiz.setVisibility(View.VISIBLE);
                    //CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), questionDetails.getSituationId(), questionDetails.getSentenceId(), questionDetails.getSituationQuestionId(),
                           // questionDetails.getReward(), "0");//1=right ans
                }
            }
        });
        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n"+ "'" +questionDetails.getRightAnswer() +"'";
                SweetAlt.OpenFreeCoinDialog(getActivity(), msg);

            }
        });

        return view;


    }

    @SuppressLint("CheckResult")
    private void setQuiz() {
        binding.tvQuizName.setText(questionDetails.getSituationQuestion());
        binding.tvHeadingName.setText(questionDetails.getSituation_heading());

        if (questionDetails.getIconImage()!=null && !questionDetails.getIconImage().isEmpty()){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.girl_image_splash);
            requestOptions.error(R.drawable.girl_image_splash);
            requestOptions.isMemoryCacheable();

            Glide.with(getActivity()).setDefaultRequestOptions(requestOptions)
                    .load(questionDetails.getIconImage())
                    .into(binding.ivCartoon);
        }

        if (questionDetails.getWords() != null && questionDetails.getWords().size() > 0) {
            wordQuizLists.clear();
            wordQuizLists = questionDetails.getWords();

            SituationTouchWord_Adapter friendsAdapter = new SituationTouchWord_Adapter(wordQuizLists, getActivity(), Fragment_Touch_Fill.this);
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }
    }


    //  TEXT TO SPEECH ACTION
    @SuppressWarnings("deprecation")
    private void speakOut(String txt) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        //Log.e("Inside", "speakOut " + mLanguageCodeTo + " " + result);
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            Toast.makeText(getActivity(), getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(getActivity(), getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
        } else {
            String textMessage = txt;
            // process_tts.show();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            textToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    //  INITIALISE TEXT TO SPEECH ENGINE
    @Override
    public void onInit(int status) {
        Log.e("Inside----->", "onInit");
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(getActivity(), getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity(), getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
            // binding.tvCheck.setEnabled(true);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.e("Inside", "OnStart");
                    // process_tts.hide();
                }

                @Override
                public void onDone(String utteranceId) {
                }

                @Override
                public void onError(String utteranceId) {
                }
            });
        } else {
            // Log.e(LOG_TAG, "TTS Initilization Failed");
        }
    }

    @SuppressLint("SetTextI18n")
    public void CheckRightAnswer(View view, int position, String word) {
        this.view=view;

        result.setText(word);

//        if (!quizDetails.getQuizData().get(quiz_position).isClickAnswer()){
//            if (quizDetails.getQuizData().get(quiz_position).getRightAnswer().equalsIgnoreCase(word)){
//                view.setBackgroundResource(R.drawable.circle_fill_green);
//                //Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
//
//                quizDetails.getQuizData().get(quiz_position).setAns_right(true);
//                binding.ivIQuiz.setVisibility(View.GONE);
//
//                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.getQuizData().get(quiz_position).getReward(), "1", quizDetails.getQuizData().get(quiz_position).getQuiz_question_id());//1=right ans
//
//                got_total_reward = got_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
//                binding.ivUsersideCoinImg.setVisibility(View.VISIBLE);
//                binding.ivOntuSideCoin.setVisibility(View.INVISIBLE);
//                binding.tvUserCoin.setText(""+got_total_reward);
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void run() {
//                        view.setVisibility(View.INVISIBLE);
//                        binding.tvQuestion.setText(""+ (quiz_position+1) +". "+ quizDetails.getQuizData().get(quiz_position).getComplete_right_answer());
//
//                        quizDetails.getQuizData().get(quiz_position).setQuizQuestion(
//                                quizDetails.getQuizData().get(quiz_position).getComplete_right_answer());
//                    }
//                }, 100);
//
//            }else {
//                //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
//                view.setBackgroundResource(R.drawable.circle_fill_red);
//                quizDetails.getQuizData().get(quiz_position).setAns_right(false);
//                binding.ivIQuiz.setVisibility(View.VISIBLE);
//
//                got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
//
//                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.getQuizData().get(quiz_position).getReward(), "0", quizDetails.getQuizData().get(quiz_position).getQuiz_question_id());//0= wrong answer
//                binding.ivOntuSideCoin.setVisibility(View.VISIBLE);
//                binding.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
//                binding.tvOntuCoin.setText(""+got_ontu_total_reward);
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.setBackgroundResource(R.drawable.circle_fill);
//                    }
//                }, 2000);
//
//
//            }
//
//            int remain_coin= Integer.parseInt(binding.tvCoin.getText().toString()) -
//                    Integer.parseInt(quizDetails.getQuizData().get(quiz_position).getReward());
//            binding.tvCoin.setText(""+remain_coin);
//
//            quizDetails.getQuizData().get(quiz_position).setClickAnswer(true);
//        }


    }

}