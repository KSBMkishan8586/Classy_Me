package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivitySingleColorBinding;
import com.ksbm.ontu.foundation.model.PalletColorModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class SingleColorActivity extends AppCompatActivity {
    ActivitySingleColorBinding binding;
    List<PalletColorModel> palletColorModelList;
    int list_count = 0;
    Context context;
    boolean isRecording = false;
    SessionManager sessionManager;
    boolean isPlayed= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_single_color);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_single_color);

        context = SingleColorActivity.this;
        sessionManager= new SessionManager(context);
        binding.header.tvTitle.setText(Constant.foundation_name);
        Speach_Record_Activity.mFileName = null;


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (getIntent()!=null){
            palletColorModelList= getIntent().getParcelableArrayListExtra("palletColorModelList");

        }

        setColorAndText();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });


        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                binding.llGetCoin.setVisibility(View.INVISIBLE);
                isPlayed= false;
                if (list_count != palletColorModelList.size() - 1) {
                    list_count++;
                    setColorAndText();
                }else {
                    finish();
                }

            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                binding.llGetCoin.setVisibility(View.INVISIBLE);
                isPlayed= false;
                if (list_count > 0) {
                    list_count--;
                    setColorAndText();
                }
            }
        });

        binding.llButton.tvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions_record(SingleColorActivity.this)) {

                    if (!isRecording) {
                        isRecording = true;
                        Speach_Record_Activity.recording_start(context);
                        binding.llButton.tvRecordSpeak.setText("Stop");
                        ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#008000")));
                        ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                        //  Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();

                    } else {
                        binding.llButton.tvRecordSpeak.setText("Record");
                        isRecording = false;
                        Speach_Record_Activity.stop_recording();
                        ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                        // Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                    }
                } else {
                    RequestPermissions(SingleColorActivity.this);
                }
            }
        });

        binding.llButton.tvSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, palletColorModelList.get(list_count).getColor_name(), true);
                ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#008000")));
                ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

            }
        });

        binding.tvColorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, palletColorModelList.get(list_count).getColor_name(), false);

            }
        });

        binding.llButton.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = Speach_Record_Activity.getRecordedFileName();
                if (file_name != null) {

                    ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#008000")));
                    ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    // PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);
                    Speach_Record_Activity.speakOut(context, palletColorModelList.get(list_count).getColor_name(), false);
                    Utils.playRecordWithdelay(context, 2);
                    binding.llButton.tvRecordSpeak.setText("Record");
                    binding.tvUserCoin.setText("+"+FoundationLearningCoinBonus );
                    //Toast.makeText(context, ""+FoundationLearningCoinBonus, Toast.LENGTH_SHORT).show();

                    if(!isPlayed){
                        CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), FoundationLearningCoinBonus, "foundation_learn", "");
                        Utils.EarnCoin(binding.llGetCoin, context, 2);
                        isPlayed= true;
                    }
                }else {
                    ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                }
                //play_text to speech
                //  Speach_Record_Activity.init_tts(OfflineQuizActivity.this);
            }
        });

    }


    private void setColorAndText() {
        binding.tvColorName.setText(palletColorModelList.get(list_count).getColor_name());
        binding.tvColorName.setBackgroundColor(palletColorModelList.get(list_count).getColor_code());
    }


}