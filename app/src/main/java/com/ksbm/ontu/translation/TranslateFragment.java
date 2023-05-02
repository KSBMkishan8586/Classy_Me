package com.ksbm.ontu.translation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentTranslateBinding;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.main_screen.model.language_model.LanguageModelResponse;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ksbm.ontu.utils.Constant.DEFAULT_LANG_POS;
import static com.ksbm.ontu.utils.Constant.DEFAULT_LANG_POS_TO;
import static com.ksbm.ontu.utils.Constant.TranslateCoinCharge;
import static com.ksbm.ontu.utils.Constant.upgradePackage;


public class TranslateFragment extends Fragment implements TextToSpeech.OnInitListener {
    FragmentTranslateBinding binding;
    SessionManager sessionManager;
    Context context;
    public static final String LOG_TAG = TranslateFragment.class.getName();
    private static final int REQ_CODE_SPEECH_INPUT = 1;

    private TextToSpeech mTextToSpeech;                     //    Text to Speech Engine
    private Spinner mSpinnerLanguageFrom;                   //    Dropdown list for selecting base language (From)
    private Spinner mSpinnerLanguageTo;                     //    Dropdown list for selecting translation language (To)
    private String mLanguageCodeFrom = "hi";                //    Language Code (From)
    private String mLanguageCodeTo = "en";                  //    Language Code (To)
    private ImageView mImageSpeak;                          //    Speak button to read out translated text (Text to Speech)
    private EditText mTextInput;                            //    Input text ( in From language )
    private TextView mTextTranslated;                       //    Output Translated text ( in To language )
    private Dialog process_tts;                             //    Dialog box for Text to Speech Engine Language Switch
    HashMap<String, String> map = new HashMap<>();
    volatile boolean activityRunning;                       //    To track status of current activity
    ProgressDialog progressDialog;
    ArrayList<LanguageModelResponse> LANGUAGE_CODES = new ArrayList<LanguageModelResponse>();
    ArrayList<String> LANGUAGE_NAME = new ArrayList<String>();

    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        // View view = inflater.inflate(R.layout.fragment_translate, null);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_translate, container, false);
        View view = binding.getRoot();//using data binding
        context = getActivity();
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());
        sessionManager = new SessionManager(context);

        try {
           ((MainActivity) getActivity()).removeBottomNavigationLabels( 2);
            ((MainActivity) getActivity()).CheckBottom(2);
        } catch (Exception e) {
        }

        activityRunning = true;
        // TextView mEmptyTextView = (TextView) view.findViewById(R.id.empty_view_not_connected);
        mSpinnerLanguageFrom = (Spinner) view.findViewById(R.id.spinner_language_from);
        mSpinnerLanguageTo = (Spinner) view.findViewById(R.id.spinner_language_to);
        TextView mButtonTranslate = (TextView) view.findViewById(R.id.button_translate);         //      Translate button to translate text
        ImageView mImageSwap = (ImageView) view.findViewById(R.id.image_swap);               //      Swap Language button to swap languages
        ImageView mImageListen = (ImageView) view.findViewById(R.id.image_listen);           //      Mic button for Speech to text
        mImageSpeak = (ImageView) view.findViewById(R.id.image_speak);
        ImageView mClearText = (ImageView) view.findViewById(R.id.clear_text);               //      Clear button to clear text fields
        mTextInput = (EditText) view.findViewById(R.id.text_input);
        mTextTranslated = (TextView) view.findViewById(R.id.text_translated);
        mTextTranslated.setMovementMethod(new ScrollingMovementMethod());

        process_tts = new Dialog(context);
        process_tts.setContentView(R.layout.dialog_processing_tts);
        process_tts.setTitle(getString(R.string.process_tts));
       // TextView title = (TextView) process_tts.findViewById(android.R.id.title);
        // title.setSingleLine(false);
        mTextToSpeech = new TextToSpeech(context, this);

        //  CHECK INTERNET CONNECTION
        if (!Connectivity.isConnected(context)) {
            // mEmptyTextView.setVisibility(View.VISIBLE);
            SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        } else {
            //  mEmptyTextView.setVisibility(View.GONE);
            //  GET LANGUAGES LIST
           //  new GetLanguages().execute();
             AddLanguage();
            //  SPEECH TO TEXT
            mImageListen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, mLanguageCodeFrom);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
                    try {
                        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                    } catch (ActivityNotFoundException a) {
                        Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //  TEXT TO SPEECH
            mImageSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speakOut(mTextTranslated.getText().toString());
                }
            });


            binding.llscreen.setOnClickListener(new DoubleClick(new DoubleClickListener() {
                        @Override
                        public void onSingleClick(View view) {

                        }

                        @Override
                        public void onDoubleClick(View view) {
                            Utils.screenShot(binding.llscreen,getActivity());
                        }
                }));





            //  TEXT TO SPEECH
            binding.imageSpeakEntertext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speakOut( binding.textInput.getText().toString());
                }
            });
            //  TRANSLATE
            mButtonTranslate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = mTextInput.getText().toString();
                    if (!input.isEmpty()){
                        translateLanguage(input);
                        //translate coin charge
                        CommonUtil.DeductCoin(sessionManager.getUser().getUserid(), TranslateCoinCharge, "translation");
                    }else {
                        Toast.makeText(context, "Please enter text", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            //  SWAP BUTTON
            mImageSwap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String temp = mLanguageCodeFrom;
                    mLanguageCodeFrom = mLanguageCodeTo;
                    mLanguageCodeTo = temp;
                    int posFrom = mSpinnerLanguageFrom.getSelectedItemPosition();
                    int posTo = mSpinnerLanguageTo.getSelectedItemPosition();
                    mSpinnerLanguageFrom.setSelection(posTo);
                    mSpinnerLanguageTo.setSelection(posFrom);
                    String textFrom = mTextInput.getText().toString();
                    String textTo = mTextTranslated.getText().toString();
                    mTextInput.setText(textTo);
                    mTextTranslated.setText(textFrom);
                }
            });
            //  CLEAR TEXT
            mClearText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTextInput.setText("");
                    mTextTranslated.setText("");
                }
            });
            //  SPINNER LANGUAGE FROM
            mSpinnerLanguageFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mLanguageCodeFrom = LANGUAGE_CODES.get(position).getLanguageCode();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getActivity(), "No option selected", Toast.LENGTH_SHORT).show();
                }
            });
            //  SPINNER LANGUAGE TO
            mSpinnerLanguageTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mLanguageCodeTo = LANGUAGE_CODES.get(position).getLanguageCode();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getActivity(), "No option selected", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (meraSharedPreferance.isOpenMarketGet()&&!meraSharedPreferance.isPackageUpgraded()){
            SweetAlt.OpenPaymentDialog(getActivity(), Constant.upgrade_course, new ClickListionerss() {
                @Override
                public void clickYes() {
                    startActivity(new Intent(getApplicationContext(), PaymentActivity.class).putExtra("price",upgradePackage));
                }
                @Override
                public void clickNo() {
                    getActivity().onBackPressed();
                }
            });
        }



        return view;
    }

    private void AddLanguage() {
        LANGUAGE_CODES.clear();
        LANGUAGE_NAME.clear();
        LANGUAGE_CODES.add(new LanguageModelResponse("ENGLISH", "en"));
        LANGUAGE_CODES.add(new LanguageModelResponse("HINDI","hi"));
        LANGUAGE_CODES.add(new LanguageModelResponse("MARATHI","mr"));
        LANGUAGE_CODES.add(new LanguageModelResponse("BENGALI","bn"));
        LANGUAGE_CODES.add(new LanguageModelResponse("GUJARATI","gu"));
        LANGUAGE_CODES.add(new LanguageModelResponse("TAMIL","ta"));
        LANGUAGE_CODES.add(new LanguageModelResponse("TELUGU","te"));
        LANGUAGE_CODES.add(new LanguageModelResponse("PANJABI","pa"));
        LANGUAGE_CODES.add(new LanguageModelResponse("MALAYALAM","ml"));

        for (int i=0; i<LANGUAGE_CODES.size(); i++){
            LANGUAGE_NAME.add(LANGUAGE_CODES.get(i).getLanguageName());
        }

        if (activityRunning) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, LANGUAGE_NAME);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerLanguageFrom.setAdapter(adapter);
            mSpinnerLanguageTo.setAdapter(adapter);
            //  SET DEFAULT LANGUAGE SELECTIONS
            mSpinnerLanguageFrom.setSelection(DEFAULT_LANG_POS);
            mSpinnerLanguageTo.setSelection(DEFAULT_LANG_POS_TO);
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
                    final Dialog match_text_dialog = new Dialog(context);
                    match_text_dialog.setContentView(R.layout.dialog_matches_frag);
                    match_text_dialog.setTitle(getString(R.string.select_matching_text));
                    ListView textlist = (ListView) match_text_dialog.findViewById(R.id.list);
                    final ArrayList<String> matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, matches_text);
                    textlist.setAdapter(adapter);
                    textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mTextInput.setText(matches_text.get(position));
                            match_text_dialog.dismiss();
                        }
                    });
                    match_text_dialog.show();
                    break;
                }
            }
        }
    }

    //  INITIALISE TEXT TO SPEECH ENGINE
    @Override
    public void onInit(int status) {
        Log.e("Inside----->", "onInit");
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(context, getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
            mImageSpeak.setEnabled(true);
            mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
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
            Log.e(LOG_TAG, "TTS Initilization Failed");
        }
    }

    //  TEXT TO SPEECH ACTION
    @SuppressWarnings("deprecation")
    private void speakOut(String textMessage) {
        int result = mTextToSpeech.setLanguage(Locale.getDefault());
        Log.e("Inside", "speakOut " + mLanguageCodeTo + " " + result);
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            Toast.makeText(context, getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(context, getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
        } else {
           // String textMessage = mTextTranslated.getText().toString();
            process_tts.show();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            mTextToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    private void translateLanguage(String input) {
        Translate_Api translate=new Translate_Api();
        translate.setOnTranslationCompleteListener(new Translate_Api.OnTranslationCompleteListener() {
            @Override
            public void onStartTranslation() {
                // here you can perform initial work before translated the text like displaying progress bar
                progressDialog = new ProgressDialog(context, R.style.MyGravity);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.show();
                // progressDialog.setCancelable(false);
            }

            @Override
            public void onCompleted(String text) {
                // "text" variable will give you the translated text
                //  Log.e("tranrrrr", ""+text);
                progressDialog.dismiss();
                mTextTranslated.setText(text);
            }

            @Override
            public void onError(Exception e) {
                progressDialog.dismiss();
            }
        });
        translate.execute(input,mLanguageCodeFrom,mLanguageCodeTo);

    }


    //  WHEN ACTIVITY IS PAUSED
    @Override
    public void onPause() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
        }
        super.onPause();
    }

    //  WHEN ACTIVITY IS DESTROYED
    @Override
    public void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        activityRunning = false;
        process_tts.dismiss();
        super.onDestroy();
    }

}
