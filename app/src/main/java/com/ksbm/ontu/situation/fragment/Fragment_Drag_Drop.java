package com.ksbm.ontu.situation.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentMannersBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.SituationQuizActivity;
import com.ksbm.ontu.situation.adapter.SituationJumbleWord_Adapter;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Drag_Drop extends Fragment implements TextToSpeech.OnInitListener{
    public static FragmentMannersBinding binding;
    SessionManager sessionManager;
    SituationQuestionModel.QuestionList questionDetails;
    List<SituationQuestionModel.Word_Question> wordQuizLists = new ArrayList<>();
    TextView result;
    RelativeLayout resultView;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manners, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        textToSpeech = new TextToSpeech(getActivity(), this);

        result = (TextView) view.findViewById(R.id.result);
        resultView = view.findViewById(R.id.resultView);
        resultView.setOnDragListener(mydragListener);



        result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(result.getText().toString().isEmpty())
                {
                    binding.tvCheckAns.setBackgroundResource(R.drawable.rectangle_bg_grey);
                    //binding.tvCheckAns.setBackgroundColor(Color.GRAY);
                }
                else
                {
                    StringBuffer sb= new StringBuffer(result.getText().toString());
                    sb.deleteCharAt(sb.length()-1);

                    if (questionDetails.getRightAnswer().equalsIgnoreCase(sb.toString()))
                    {
                        binding.tvCheckAns.setBackgroundResource(R.drawable.rectangle_bg_green);
                        binding.ivIQuiz.setVisibility(View.GONE);
                        result.setTextColor(getResources().getColor(R.color.green));
                    }
                    else
                    {
                        binding.tvCheckAns.setBackgroundResource(R.drawable.rectangle_bg_red);
                        result.setTextColor(getResources().getColor(R.color.red));
                        binding.ivIQuiz.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (getArguments()!=null){
            questionDetails= ( SituationQuestionModel.QuestionList) getArguments().getSerializable("QuestionDetails");
        }


        setQuiz();

        binding.tvCheckAns.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (!result.getText().toString().isEmpty()){

                    StringBuffer sb= new StringBuffer(result.getText().toString());
                    sb.deleteCharAt(sb.length()-1);

                    if (questionDetails.getRightAnswer().equalsIgnoreCase(sb.toString())) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "Right", Toast.LENGTH_SHORT).show();
                        result.setTextColor(getResources().getColor(R.color.green));
                        binding.ivIQuiz.setVisibility(View.GONE);
                       // quizDetails.get(position).setQuizRight(true);

                       // got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(position).getReward());
                        CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), questionDetails.getSituationId(), questionDetails.getSentenceId(), questionDetails.getSituationQuestionId(),
                                questionDetails.getReward(), "1");//1=right ans
                       // binding.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                      //  binding.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                       // binding.tvUserCoin.setText(""+got_total_reward);
                        binding.tvUserCoin.setText("+ " + questionDetails.getReward());
                        binding.llGetCoin.setVisibility(View.VISIBLE);
                        Utils.playMusic(R.raw.coin_sound, getActivity());
                        Toast.makeText(getActivity(), questionDetails.getSentenceId()+"", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("ResumeQuize", MODE_PRIVATE).edit();
                        editor.putString("QuestionId", "drag and drop");
                        editor.putString("situation_id", questionDetails.getSituationId()+"");
                        editor.putString("situation_name", questionDetails.getSituation_heading()+"");
                        editor.putString("sentence_id", questionDetails.getSentenceId()+"");
                        editor.putBoolean("value", true);
                        editor.apply();


                    } else {
                        result.setTextColor(getResources().getColor(R.color.red));
                        binding.ivIQuiz.setVisibility(View.VISIBLE);
                       // showCorrectDialog();
                       // quizDetails.get(position).setQuizRight(false);

                      //  got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(position).getReward());

                      //  CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), questionDetails.getSituationId(), questionDetails.getSentenceId(), questionDetails.getSituationQuestionId(),
                            //    questionDetails.getReward(), "0");//0= wrong answer

                      //  binding.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                      //  binding.tvOntuCoin.setText(""+got_ontu_total_reward);
                    }

                   // questionDetails.get(position).setQuizAttend(true);

//                    int remain_coin= Integer.parseInt(binding.tvCoin.getText().toString()) -
//                            Integer.parseInt(quizDetails.get(position).getReward());
//                    binding.tvCoin.setText(""+remain_coin);

                }
            }
        });

        binding.tvQuizName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // speakOut(binding.tvQuizName.getText().toString());
                Speach_Record_Activity.speakOut(getActivity(), binding.tvQuizName.getText().toString(), false);
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
        //result.setText(questionDetails.getSituation_heading());
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

            SituationJumbleWord_Adapter friendsAdapter = new SituationJumbleWord_Adapter(wordQuizLists, getActivity(), Fragment_Drag_Drop.this);
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();

        }
    }


    View.OnDragListener mydragListener = new View.OnDragListener() {
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
                    //  item_count++;

//                    if (item_count == wordQuizLists.size()) {
//                        binding.tvSubmit.setVisibility(View.VISIBLE);
//                    }

                    result.append(wordQuizLists.get(word_position).getSituationWord() + " ");
                    view.setVisibility(View.INVISIBLE);

                    break;
            }

            return true;
        }
    };

    public void DragTextItem(View v, int position) {
        this.word_position = position;
        this.view = v;

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
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

}
