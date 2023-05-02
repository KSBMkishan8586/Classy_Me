package com.ksbm.ontu.custom_class;

import static com.ksbm.ontu.main_screen.MainActivity.mMainViewModel;
import static com.ksbm.ontu.utils.Constant.audio_record_folder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.ksbm.ontu.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Speach_Record_Activity {
    public static String mFileName = null;
    public static MediaRecorder mRecorder;
    public static MediaPlayer mPlayer;
    public static TextToSpeech mTextToSpeech;
    public static Dialog process_tts;

    public static void recording_start(Context context) {
        stop_recording();
        String mPath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            mPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + audio_record_folder;
        } else {
            mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + audio_record_folder;
        }

        deleteFromExternalStorage(mPath, "Rec" + "Quiz" + ".mp3");

        File file = new File(mPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        mFileName = mPath + "Rec" + "Quiz" + ".mp3";
        Log.e("filename", mFileName);

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e("prepare() failed ", "" + e.toString());

        }
    }

    public static void deleteFromExternalStorage(String filePath, String fileName) {
       // String fullPath = "/mnt/sdcard/";
        try
        {
            File file = new File(filePath, fileName);
            if(file.exists())
                file.delete();
        }
        catch (Exception e)
        {
            Log.e("App", "Exception while deleting file " + e.getMessage());
        }
    }

    public static void stop_recording() {
        try {
            mRecorder.stop();
            mRecorder.release();
            //mRecorder.reset();
            mRecorder = null;
        } catch (Exception e) {

        }
    }

    public static String getRecordedFileName() {
        if (mFileName != null) {
            return mFileName;
        }
        return null;
    }

    public static void PlayRecording(String recordingUri, Context context) {
        if (mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
        }

        mPlayer = new MediaPlayer();

            try {
                mPlayer.reset();
                Uri uri = Uri.parse(recordingUri);
                mPlayer.setDataSource(context, uri); // "this" refers to context
                mPlayer.prepare();
                mPlayer.start();

                /** once the audio is complete, timer is stopped here**/
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mPlayer!=null){
                            mPlayer.release();
                            mPlayer = null;
                        }
                        // isPLAYING = false;
                    }
                });

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

//        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                if (progressDialog != null && progressDialog.isShowing()){
//                    progressDialog.dismiss();
//                }
//                mp.start();
//            }
//        });


    }

    public static void init_tts(Activity context) {
        process_tts = new Dialog(context);
        process_tts.setContentView(R.layout.dialog_processing_tts);
        process_tts.setTitle(context.getString(R.string.process_tts));

        // create an object textToSpeech and adding features into it
        mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = mTextToSpeech.setLanguage(new Locale("en","IN"));
                    if (result == TextToSpeech.LANG_MISSING_DATA) {
                        Toast.makeText(context, context.getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
                    } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
                    }
                    // mImageSpeak.setEnabled(true);
                    mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener(){
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
                    Log.e("LOG_TAG", "TTS Initilization Failed");
                }
            }
        });
    }

    //  TEXT TO SPEECH ACTION
    @SuppressWarnings("deprecation")
    public static void speakOut(Context context, String textMessage, boolean slow) {
        int result = mTextToSpeech.setLanguage(new Locale("en","IN"));
        //  Log.e("Inside", "speakOut " + mLanguageCodeTo + " " + result);
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            Toast.makeText(context, context.getString(R.string.language_pack_missing), Toast.LENGTH_SHORT).show();
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            context.startActivity(installIntent);
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
        } else {
            // String textMessage = mTextTranslated.getText().toString();
            if (context != null) {
                // process_tts.show();
            }

            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");

            if (slow) {
                mTextToSpeech.setPitch((float) 0.5);
                mTextToSpeech.setSpeechRate((float) 0.5);
            } else {
                mTextToSpeech.setPitch((float) 1.0);
                mTextToSpeech.setSpeechRate((float) 1.0);
            }

            mTextToSpeech.speak(textMessage, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    public static void speakOutGoogleTTS(Context context, String speakText, boolean slow) {
        mMainViewModel.speak(speakText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(t -> initTTSVoice(slow))
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onComplete() {
                        // makeToast("Speak success", false);

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //makeToast("Speak failed " + e.getMessage(), true);
                        Log.e("speak_failed ", "Speak failed", e);
                    }
                });
    }

    private static void initTTSVoice(boolean slow) {
        //https://cloud.google.com/text-to-speech/docs/voices
        //India Hindi voice
        String languageCode = "hi-IN";
        String voiceName;
       // if (VoiceType.equalsIgnoreCase("male")) {
       //    voiceName = "hi-IN-Standard-B";
      //  } else {
            voiceName = "hi-IN-Standard-A";
       // }

        float pitch, speakRate;
        if (slow) {
             pitch = ((float) 0.5); //between -20.0 to 20.0
             speakRate = ((float) 0.5);//75
        } else {
             pitch = ((float) 1.0); //between -20.0 to 20.0
             speakRate = ((float) 1.0);//75
        }

        mMainViewModel.initTTSVoice(languageCode, voiceName, pitch, speakRate);
    }
}
