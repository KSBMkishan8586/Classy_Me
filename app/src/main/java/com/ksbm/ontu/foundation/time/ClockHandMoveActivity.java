package com.ksbm.ontu.foundation.time;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityClockHandMoveBinding;
import com.ksbm.ontu.foundation.activity.ClockPastActivity;
import com.ksbm.ontu.foundation.model.ClockLearningTime;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.inflate;
import static com.ksbm.ontu.foundation.time.AnalogClock1.hourHand;
import static com.ksbm.ontu.foundation.time.AnalogClock1.minuteHand;

public class ClockHandMoveActivity extends AppCompatActivity {
    ActivityClockHandMoveBinding binding;
    SessionManager sessionManager;
    Context context;
    AnalogClock1 analogClock;
    List<ClockLearningTime> clockLearningTimes = new ArrayList<>();
    int clock_pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_clock_learn);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clock_hand_move);

        context = ClockHandMoveActivity.this;
        sessionManager = new SessionManager(this);

        binding.header.tvTitle.setText(Constant.foundation_name);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        analogClock = findViewById(R.id.analogClock);
        Constant.hourHandStatus = true;
        // Button digitalClock = findViewById(R.id.digitalClock);
        analogClock.setOnTimeChanged(new AnalogClock1.OnTimeChanged() {
            @Override
            public void onTimeChanged(int newHour, int newMinute) {
                setTime(newHour, newMinute);
            }
        });

        //setTime(analogClock.getHour(), analogClock.getMinute());

        addClockTime();
        setClockTime();

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clock_pos != clockLearningTimes.size() - 1) {
                    clock_pos++;
                    setClockTime();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ClockPastActivity.class);
                    startActivity(intent);
                    finish();
                    //finish();
                    //Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clock_pos > 0) {
                    clock_pos--;
                    setClockTime();
                } else {
                    finish();
                }
            }
        });

        binding.tvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvHeader.getText().toString(), false);
            }
        });

        binding.tvHeaderTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvHeaderTask.getText().toString(), false);
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void setClockTime() {
        Constant.hourHandStatus = true;
        binding.tvHeader.setText("Let's rotate the hour hand to learn ");
        Utils.setRainbowColor(binding.tvHeader);

        binding.tvHeaderTask.setText(Utils.UnderlineText(clockLearningTimes.get(clock_pos).getTitle_text()));
        Utils.setRainbowColor(binding.tvHeaderTask);

        analogClock.minute = clockLearningTimes.get(clock_pos).getMint();
        analogClock.hour = clockLearningTimes.get(clock_pos).getHour();
        minuteHand.setTime(analogClock.hour, analogClock.minute);
        hourHand.setTime(analogClock.hour, analogClock.minute);
        analogClock.invalidate();

        setTime(analogClock.getHour(), analogClock.getMinute());
    }

    private void addClockTime() {
        clockLearningTimes.add(new ClockLearningTime(12, 00, "O'Clock", "O'Clock"));
        clockLearningTimes.add(new ClockLearningTime(12, 05, "5 Minutes Past", "Five Past"));
        clockLearningTimes.add(new ClockLearningTime(12, 15, "Quarter Past", "Quarter Past"));
        clockLearningTimes.add(new ClockLearningTime(12, 30, "Half Past", "Half Past"));
        clockLearningTimes.add(new ClockLearningTime(12, 35, "25 Minutes To", "25 Minutes To"));
        clockLearningTimes.add(new ClockLearningTime(12, 45, "Quarter To", "Quarter To"));
        clockLearningTimes.add(new ClockLearningTime(12, 55, "5 Minutes To", "Five Minutes To"));

    }

    private void setTime(int hour, int minute) {
        if (hour > 12) {
            hour = hour - 12;
        } else if (hour == 0) {
            hour = 12;
        }
        final int finalHour = hour;
        //fetch actual time on the clock
        String time = null;
        if (clock_pos == 0) {
            time = finalHour + " " + clockLearningTimes.get(clock_pos).getLearning_text();
        } else if (clock_pos == 1 || clock_pos == 2 || clock_pos == 3) {
            time = clockLearningTimes.get(clock_pos).getLearning_text() + " " + finalHour;
        } else {
            int hour_1 = (finalHour + 1);

            if (hour_1 > 12) {
                hour_1 = hour_1 - 12;
            }

            time = clockLearningTimes.get(clock_pos).getLearning_text() + " " + hour_1;
        }

        String finalTime = time;
        binding.tvTime.setText(finalTime);
        Utils.setRainbowColor(binding.tvTime);

        Speach_Record_Activity.speakOut(context, finalTime, false);

        binding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, finalTime, false);
                // Log.e("time_listen", ""+ finalTime);
            }
        });

        binding.tvListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, finalTime, false);
            }
        });

    }

}