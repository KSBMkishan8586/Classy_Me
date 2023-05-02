package com.ksbm.ontu.situation.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentSpeakQuizBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class Fragment_Speak_Quiz extends Fragment implements TextToSpeech.OnInitListener{
    FragmentSpeakQuizBinding binding;
    SessionManager sessionManager;
    SituationQuestionModel.QuestionList QuestionDetails;
    private static final int REQ_CODE_SPEECH_INPUT = 1;
    TextToSpeech textToSpeech;
    HashMap<String, String> map = new HashMap<>();
    boolean isRecording = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_speak_quiz, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        textToSpeech = new TextToSpeech(getActivity(), this);

        if (getArguments()!=null){
            QuestionDetails= ( SituationQuestionModel.QuestionList) getArguments().getSerializable("QuestionDetails");
        }


        setQuiz();

        new tempTask().execute();

        binding.tvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckPermissions_record(getActivity())) {

                    if (!isRecording) {
                        isRecording = true;
                        Speach_Record_Activity.recording_start(getActivity());
                        binding.tvRecordSpeak.setText("Stop");
                        //  Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();

                    } else {
                        binding.tvRecordSpeak.setText("Record");
                        isRecording = false;
                        Speach_Record_Activity.stop_recording();
                        // Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                    }
                } else {
                    RequestPermissions(getActivity());
                }


//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
//                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
//                try {
//                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//                } catch (ActivityNotFoundException a) {
//                    Toast.makeText(getActivity(), getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
//                }
            }
        });

        binding.tvQuesNameEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(),binding.tvQuesNameEn.getText().toString(), false);

            }
        });

        binding.tvQuesNameHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(),binding.tvQuesNameHi.getText().toString(), false);
            }
        });

        binding.tvSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(), binding.tvQuesNameEn.getText().toString(), true);
            }
        });

//        binding.tvSpeakText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Speach_Record_Activity.speakOut(getActivity(), binding.tvSpeakText.getText().toString(), false);
//            }
//        });

        binding.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.tvQuesNameEn.getText().toString().isEmpty())
                    Speach_Record_Activity.speakOut(getActivity(), binding.tvQuesNameEn.getText().toString(), false);

                String file_name = Speach_Record_Activity.getRecordedFileName();
                if (file_name != null) {
                  //  PlayRecording(Speach_Record_Activity.getRecordedFileName(), getActivity());
                    Utils.playRecordWithdelay(getActivity(), 1);
                    binding.tvRecordSpeak.setText("Record");
                }

                //getCoin
                CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), QuestionDetails.getSituationId(), QuestionDetails.getSentenceId(), QuestionDetails.getSituationQuestionId(),
                        QuestionDetails.getReward(), "1");//1=right ans

                binding.tvUserCoin.setText("+ " + QuestionDetails.getReward());
                binding.llGetCoin.setVisibility(View.VISIBLE);
                Utils.playMusic(R.raw.coin_sound, getActivity());
                Toast.makeText(getActivity(), QuestionDetails.getSentenceId()+"", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("ResumeQuize", MODE_PRIVATE).edit();
                editor.putString("QuestionId", "speak only");
                editor.putString("situation_id", QuestionDetails.getSituationId()+"");
                editor.putString("situation_name", QuestionDetails.getSituation_heading()+"");
                editor.putString("sentence_id", QuestionDetails.getSentenceId()+"");
                editor.putBoolean("value", true);
                editor.apply();


            }
        });

        return view;
    }

    private void setQuiz() {
       // binding.tvSpeakText.setVisibility(View.INVISIBLE);
        binding.tvQuesNameEn.setText(QuestionDetails.getSituationQuestion());
        binding.tvQuesNameHi.setText(QuestionDetails.getSituation_question_hi());

        binding.tvHeadingName.setText(QuestionDetails.getSituation_heading());
    }

    TextToSpeech firstTTSObj, secondTTSObj;
    private class tempTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object... params) {
             firstTTSObj = new TextToSpeech(getApplicationContext(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                firstTTSObj.setLanguage(Locale.getDefault());
                            }
                        }
                    });
            secondTTSObj = new TextToSpeech(getApplicationContext(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if(status != TextToSpeech.ERROR){
                                secondTTSObj .setLanguage(Locale.getDefault());
                            }
                        }
                    });
            return null;
        }
    }

    //  TEXT TO SPEECH ACTION
    @SuppressWarnings("deprecation")
    private void speakOut(String txt, boolean slow) {
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

            if (slow){
                textToSpeech.setPitch((float) 0.5);
                textToSpeech.setSpeechRate((float) 0.5);
            }else {
                textToSpeech.setPitch((float) 1.0);
                textToSpeech.setSpeechRate((float) 1.0);
            }
            textToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    @SuppressWarnings("deprecation")
    private void speakOut1(String txt, boolean slow) {
        int result = secondTTSObj.setLanguage(Locale.getDefault());
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

            if (slow){
                secondTTSObj.setPitch((float) 0.5);
                secondTTSObj.setSpeechRate((float) 0.5);
            }else {
                secondTTSObj.setPitch((float) 1.0);
                secondTTSObj.setSpeechRate((float) 1.0);
            }
            secondTTSObj.speak(textMessage, TextToSpeech.QUEUE_FLUSH, map);
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

    //  RESULT OF SPEECH INPUT
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    /*
                            Dialog box to show list of processed Speech to text results
                            User selects matching text to display in chat
                     */
                    final Dialog match_text_dialog = new Dialog(getActivity());
                    match_text_dialog.setContentView(R.layout.dialog_matches_frag);
                    match_text_dialog.setTitle(getString(R.string.select_matching_text));
                    ListView textlist = (ListView) match_text_dialog.findViewById(R.id.list);
                    final ArrayList<String> matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, matches_text);
                    textlist.setAdapter(adapter);
                    textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            binding.tvSpeakText.setVisibility(View.VISIBLE);
                            binding.tvSpeakText.setText(matches_text.get(position));
                            match_text_dialog.dismiss();
                        }
                    });
                    match_text_dialog.show();
                    break;
                }
            }
        }
    }

    //  WHEN ACTIVITY IS PAUSED
    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();
    }

    //  WHEN ACTIVITY IS DESTROYED
    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

}
