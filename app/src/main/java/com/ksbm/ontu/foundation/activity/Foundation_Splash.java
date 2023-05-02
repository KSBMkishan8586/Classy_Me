package com.ksbm.ontu.foundation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentOfflineSplashBinding;
import com.ksbm.ontu.foundation.time.LiveClockActivity;
import com.ksbm.ontu.foundation.drawing.DrawingImageActivity;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import static com.ksbm.ontu.utils.Constant.ABCD;
import static com.ksbm.ontu.utils.Constant.Animals;
import static com.ksbm.ontu.utils.Constant.Body_Parts;
import static com.ksbm.ontu.utils.Constant.Body_Partsoriginal;
import static com.ksbm.ontu.utils.Constant.Colors;
import static com.ksbm.ontu.utils.Constant.Common_Currency;
import static com.ksbm.ontu.utils.Constant.Counting;
import static com.ksbm.ontu.utils.Constant.Date;
import static com.ksbm.ontu.utils.Constant.Direction;
import static com.ksbm.ontu.utils.Constant.Fruits_Vegetables;
import static com.ksbm.ontu.utils.Constant.Months;
import static com.ksbm.ontu.utils.Constant.Time;
import static com.ksbm.ontu.utils.Constant.Weekdays;

public class Foundation_Splash extends AppCompatActivity {
    FragmentOfflineSplashBinding binding;
    SessionManager sessionManager;
    private String foundation_id, foundation_name;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(Foundation_Splash.this), Foundation_Splash.this);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.fragment_offline_splash);
        context = Foundation_Splash.this;
        sessionManager = new SessionManager(context);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (getIntent() != null) {
            foundation_id = getIntent().getStringExtra("foundation_id");
            foundation_name = getIntent().getStringExtra("foundation_name");
            binding.header.tvTitle.setText(foundation_name);
        }


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (foundation_id.equalsIgnoreCase(Colors)) {
            binding.relOther.setVisibility(View.VISIBLE);
            binding.tvTypeName.setText("Drawing");
        } else if (foundation_id.equalsIgnoreCase(Time)) {
            binding.relOther.setVisibility(View.VISIBLE);
            binding.tvTypeName.setText("Live Clock");
        }


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

        binding.relOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foundation_id.equalsIgnoreCase(Colors)) {
                    Intent intent = new Intent(context, DrawingImageActivity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Time)) {
                    Intent intent = new Intent(context, LiveClockActivity.class);
                    startActivity(intent);
                }
            }
        });

        binding.relLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.previous_btn = true;
                Speach_Record_Activity.speakOut(context,"Learn",false);
//                Toast.makeText(context, ""+foundation_id, Toast.LENGTH_LONG).show();
                if (foundation_id.equalsIgnoreCase(ABCD)) {
                    Intent intent = new Intent(context, Alphabet_Activity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Animals)) {
                    Intent intent = new Intent(context, AnimalTypeActivity.class);
                    intent.putExtra("SubType", "Animal");
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Body_Partsoriginal) || foundation_id.equalsIgnoreCase(Body_Parts)) {
                    Constant.foundation_id = foundation_id;
                    Intent intent = new Intent(context, BodyPartActivity.class);
                    // Intent intent = new Intent(context, MatchFollowingActivity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Counting)) {
                    Intent intent = new Intent(context, Counting_Activity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Colors)) {
                    Intent intent = new Intent(context, ColorActivity.class);
                    // Intent intent = new Intent(context, DrawingImageActivity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Common_Currency)) {
                    Intent intent = new Intent(context, CurrencyLearnActivity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Date)) {
                    Intent intent = new Intent(context, DateActivity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Direction)) {
                    Intent intent = new Intent(context, Direction_Add_Activity.class);
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Fruits_Vegetables)) {
                    Intent intent = new Intent(context, AnimalTypeActivity.class);
                    intent.putExtra("SubType", "Fruits");
                    startActivity(intent);
                } else if (foundation_id.equalsIgnoreCase(Months)) {
                    Intent intent = new Intent(context, MonthActivity.class);
                    intent.putExtra("DayType", "Month");
                    startActivity(intent);

                } else if (foundation_id.equalsIgnoreCase(Time)) {
                    Intent intent = new Intent(context, ClockLearnActivity.class);
                    startActivity(intent);

                } else if (foundation_id.equalsIgnoreCase(Weekdays)) {
//                    Intent intent = new Intent(context, MatchFollowingActivity.class);
//                    startActivity(intent);
                    Intent intent = new Intent(context, MonthActivity.class);
                    intent.putExtra("DayType", "Week");
                    startActivity(intent);
                } else {
//                    Toast.makeText(Foundation_Splash.this, "Coming soon", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, BodyPartActivity.class);
//                    Intent intent = new Intent(context, MatchFollowingActivity.class);
//                    startActivity(intent);

                    Intent intent = new Intent(context, Alphabet_Activity.class);
                    startActivity(intent);

                }
            }
        });

        binding.relActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.previous_btn = true;
                Speach_Record_Activity.speakOut(context,"Activity",false);
                if (foundation_id.equalsIgnoreCase(Body_Partsoriginal) || foundation_id.equalsIgnoreCase(Body_Parts)) {

                    Intent intent = new Intent(context, BodyPartDrop.class);
//                    intent.putExtra("foundation_id", foundation_id);
//                    intent.putExtra("foundation_name", foundation_name);
                    startActivity(intent);

                } else {
//                    if(foundation_id.equals("20") || foundation_id.equals("35") || foundation_id.equals("36"))
//                    {
//                        Toast.makeText(Foundation_Splash.this, "Coming soon", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//
//                    }
                    Intent intent = new Intent(context, FoundationQuizActivity.class);
                    intent.putExtra("foundation_id", foundation_id);
                    intent.putExtra("foundation_name", foundation_name);
                    startActivity(intent);

                }


            }
        });


    }

    @Override
    protected void onResume() {
        Speach_Record_Activity.stop_recording();
        super.onResume();
    }

    @Override
    protected void onStop() {
        Speach_Record_Activity.stop_recording();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Speach_Record_Activity.stop_recording();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Speach_Record_Activity.stop_recording();
        super.onPause();
    }
}
