package com.ksbm.ontu.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.firebase.NotificationUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundMusicService extends Service {

    MediaPlayer mPlayer = null;
    private final static int MAX_VOLUME = 100;
    Context context;
    AudioManager.OnAudioFocusChangeListener afChangeListener;
    private int length;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

      //  int musicflag = (int) intent.getExtras().get("songindex");
       // if (musicflag == 1) {
          //  playMusic(R.raw.s1_pondambience);
       // } else {
            playMusic(R.raw.game_music);
       // }
        startTimer();

        return Service.START_REDELIVER_INTENT;
    }

    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {

        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, to wake up every 300 second
        timer.schedule(timerTask, 1000 * 5, 1000 * 5);
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            public void run() {

               // Log.i("in timer", "in timer++  " + (counter++));
                //call webservice****
                if(!NotificationUtils.isAppIsInBackground(getApplicationContext())){
                    //play music
                } else {
                   // app is in background
                    Speach_Record_Activity.stop_recording();
                    if (mPlayer!=null){
                        stopMusic();
                    }
                }

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }

        stoptimertask();
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

    /*
     * playmusic custom method for manage two different background sounds for application
     * */

    public void playMusic(int musicFile) {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                try {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(this, musicFile);

                    AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // Start playback.
                        mPlayer.setLooping(true);
                        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 85) / Math.log(MAX_VOLUME)));
                        mPlayer.setVolume(volume, volume);
                        mPlayer.start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    mPlayer = MediaPlayer.create(this, musicFile);

                    AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                    int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // Start playback.
                        mPlayer.setLooping(true);
                        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 85) / Math.log(MAX_VOLUME)));
                        mPlayer.setVolume(volume, volume);
                        mPlayer.prepare();
                        mPlayer.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            try {
                mPlayer = MediaPlayer.create(this, musicFile);

                AudioManager am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.
                    mPlayer.setLooping(true);
                    final float volume = (float) (1 - (Math.log(MAX_VOLUME - 85) / Math.log(MAX_VOLUME)));
                    mPlayer.setVolume(volume, volume);
                    mPlayer.start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * MediaPlayer methods
     * */

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic() {
        if (!mPlayer.isPlaying()) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void stopMusic() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
