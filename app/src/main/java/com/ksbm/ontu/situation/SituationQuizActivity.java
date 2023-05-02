package com.ksbm.ontu.situation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivitySituationQuizBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.adapter.SituationSentenseWordAdapter;
import com.ksbm.ontu.situation.model.SituationSentenceModel;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SituationQuizActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    ActivitySituationQuizBinding binding;
    SessionManager sessionManager;
    String situation_id, situation_name;
    Context context;
    List<SituationSentenceModel.Sentence> sentence_list = new ArrayList<>();
    List<SituationSentenceModel.Word> words_list = new ArrayList<>();
    ArrayAdapter<String> listadapter;
    int sentence_pos = 0;
    boolean last_quiz = false;
    TextToSpeech textToSpeech;
    private Dialog process_tts;  // Dialog box for Text to Speech Engine Language Switch
    HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(SituationQuizActivity.this), SituationQuizActivity.this);
        // setContentView(R.layout.activity_situation_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_situation_quiz);
        sessionManager = new SessionManager(this);
        context = this;
        Constant.NEXT_SENTENCES = false;

        process_tts = new Dialog(context);
        process_tts.setContentView(R.layout.dialog_processing_tts);
        process_tts.setTitle(getString(R.string.process_tts));

        textToSpeech = new TextToSpeech(context, this);

        if (getIntent() != null) {
            situation_id = getIntent().getStringExtra("situation_id");
            situation_name = getIntent().getStringExtra("situation_name");

            binding.tvHeader.setText(situation_name);
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(context)) {
            getAllSentences();
        } else {
            Toast.makeText(context, "Please check Internet", Toast.LENGTH_SHORT).show();
        }


//        binding.textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//
//                final TextView tv_id = (TextView) v.findViewById(R.id.text_row);
//                String txt = tv_id.getText().toString();
//                Speach_Record_Activity.speakOut(SituationQuizActivity.this, txt, false);
//
//            }
//        });
//
//        binding.textList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//                final TextView tv_id = (TextView) v.findViewById(R.id.text_row);
//                String txt = tv_id.getText().toString();
//                Speach_Record_Activity.speakOut(SituationQuizActivity.this, txt, false);
//            }
//        });

        binding.tvQuesNameEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(SituationQuizActivity.this, binding.tvQuesNameEn.getText().toString(), false);
//                 Speach_Record_Activity.speakOutGoogleTTS(context, binding.tvQuesNameEn.getText().toString(), false);
            }
        });

        binding.tvQuesNameHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(SituationQuizActivity.this, binding.tvQuesNameHi.getText().toString(), false);
                // Speach_Record_Activity.speakOutGoogleTTS(context, binding.tvQuesNameHi.getText().toString(), false);
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (!last_quiz){
                if (sentence_list != null && sentence_list.size() > 0) {
                    Intent intent = new Intent(SituationQuizActivity.this, SituationQuizPatternActivity.class);
                    intent.putExtra("situation_id", sentence_list.get(sentence_pos).getSituationId());
                    intent.putExtra("sentence_id", sentence_list.get(sentence_pos).getSentenceId());
                    intent.putExtra("situation_name", situation_name);
                    startActivity(intent);
                    finish();
                }

                //  }else {
                //finish();
                // Toast.makeText(context, "No Quiz Found", Toast.LENGTH_SHORT).show();
                //}
            }
        });

        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sentence_pos > 0) {
                    sentence_pos--;

                    setQuiz();
                } else {
                    // finish();
                }
            }
        });

    }

    //  TEXT TO SPEECH ACTION
    @SuppressWarnings("deprecation")
    private void speakOut(String txt) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        //Log.e("Inside", "speakOut " + mLanguageCodeTo + " " + result);
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            Toast.makeText(context, getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
        } else {
            String textMessage = txt;
            // process_tts.show();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            textToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    @Override
    public void onInit(int status) {
        Log.e("Inside----->", "onInit");
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(context, getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }

            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.e("Inside", "OnStart");
                    process_tts.hide();
                }

                @Override
                public void onDone(String utteranceId) {
                }

                @Override
                public void onError(String utteranceId) {
                }
            });
        } else {
            Log.e("LOG_TAG", "TTS Initilization Failed");
        }
    }

    @SuppressLint("CheckResult")
    private void getAllSentences() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetSituationSetences(sessionManager.getLanguageid(),sessionManager.getUser().getUserid(), situation_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SituationSentenceModel>() {
                    @Override
                    public void onNext(SituationSentenceModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            //  Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                sentence_list = response.getSentence();
                                setQuiz();

                            } else {
                                SweetAlt.showError(context, "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setQuiz() {
        words_list = sentence_list.get(sentence_pos).getWords();

        binding.tvSentenceNo.setText("" + (sentence_pos + 1) + ")");
        binding.tvQuesNameEn.setText(sentence_list.get(sentence_pos).getSentenceEn());
        binding.tvQuesNameHi.setText(sentence_list.get(sentence_pos).getSentenceHi());

        Speach_Record_Activity.speakOut(getApplicationContext(), sentence_list.get(sentence_pos).getSentenceEn(), false);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();

        for (int i = 0; i < words_list.size(); i++) {
            list.add(words_list.get(i).getSituationWordEn());
            list1.add(words_list.get(i).getSituationWordHi());
        }

//        listadapter = new ArrayAdapter<String>(this, R.layout.rowtext_word, R.id.text_row, list);
//        binding.textList.setAdapter(listadapter);
//
//        listadapter = new ArrayAdapter<String>(this, R.layout.rowtext_word, R.id.text_row, list1);
//        binding.textList1.setAdapter(listadapter);

        SituationSentenseWordAdapter friendsAdapter = new SituationSentenseWordAdapter(list, SituationQuizActivity.this);
        binding.setAdapter(friendsAdapter);//set databinding adapter
        // friendsAdapter.notifyDataSetChanged();

        SituationSentenseWordAdapter friendsAdapter1 = new SituationSentenseWordAdapter(list1, SituationQuizActivity.this);
        binding.setAdapter1(friendsAdapter1);//set databinding adapter
        // friendsAdapter.notifyDataSetChanged();

    }

    //  WHEN ACTIVITY IS PAUSED
    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (Constant.NEXT_SENTENCES) {
            if (sentence_pos != sentence_list.size() - 1) {
                sentence_pos++;
                setQuiz();
                last_quiz = false;
            } else {
                last_quiz = true;
                Toast.makeText(context, "Quiz End", Toast.LENGTH_SHORT).show();
            }

        }

        super.onResume();
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