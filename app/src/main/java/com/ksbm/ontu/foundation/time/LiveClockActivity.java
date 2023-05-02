package com.ksbm.ontu.foundation.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.time.live_clock.AnalogClock;

import java.util.Locale;

public class LiveClockActivity extends AppCompatActivity {

    AnalogClock analogClock;
    Button digitalClock;
    // TimeReadout timeReadout = new TimeReadout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_clock);

        analogClock = findViewById(R.id.analogClock);
        digitalClock = findViewById(R.id.digitalClock);
        analogClock.setOnTimeChanged(new AnalogClock.OnTimeChanged() {
            @Override
            public void onTimeChanged(int newHour, int newMinute) {
                setTime(newHour, newMinute);
            }
        });

        setTime(analogClock.getHour(), analogClock.getMinute());

        ImageView ivBack= findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        digitalClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Speak the digital readout text
               // Speach_Record_Activity.speakOut(LiveClockActivity.this, digitalClock.getText().toString(), false);

                String time = digitalClock.getText().toString();
                if (time.contains(":00")){
                    String result = time.substring(0, time.length() - 1);
                    Speach_Record_Activity.speakOut(LiveClockActivity.this, result + " o'clock", false);
                }else {
                    Speach_Record_Activity.speakOut(LiveClockActivity.this, time, false);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void setTime(int hour, int minute) {
        digitalClock.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        String result = time.substring(0, time.length() - 3);
        if (time.contains(":00")){
           // String result = time.substring(0, time.length() - 3);
            Speach_Record_Activity.speakOut(LiveClockActivity.this, result + " o'clock", false);
        }else if (time.contains(":01")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero one", false);
        }else if (time.contains(":02")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero two", false);
        }else if (time.contains(":03")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero three", false);
        }else if (time.contains(":04")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero four", false);
        }else if (time.contains(":05")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero five", false);
        }else if (time.contains(":06")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero six", false);
        }else if (time.contains(":07")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero seven", false);
        }else if (time.contains(":08")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero eight", false);
        }else if (time.contains(":09")){
                Speach_Record_Activity.speakOut(LiveClockActivity.this, result + "zeero nine", false);
        }else {
            Speach_Record_Activity.speakOut(LiveClockActivity.this, time, false);
        }

       // final Button digitalReadout = findViewById(R.id.digitalReadout);
      //  digitalReadout.setText(timeReadout.formatDigital(hour, minute));
    }

}