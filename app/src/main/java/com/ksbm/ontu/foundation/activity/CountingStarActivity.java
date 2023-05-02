package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.ksbm.ontu.databinding.ActivityCountingStarBinding;
import com.ksbm.ontu.foundation.adapter.Counting_Star_Adapter;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.EnglishNumberToWords;
import com.ksbm.ontu.utils.Utils;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class CountingStarActivity extends AppCompatActivity {
    ActivityCountingStarBinding binding;
    private int currentPage = 0;
    Context context;
    boolean isRecording = false;
    SessionManager sessionManager;
    boolean isPlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_counting_star);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_counting_star);

        context = CountingStarActivity.this;
        sessionManager = new SessionManager(this);
        binding.header.tvTitle.setText(Constant.foundation_name);
        Speach_Record_Activity.mFileName = null;


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        AddCountStart();

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                binding.llGetCoin.setVisibility(View.INVISIBLE);
                isPlayed = false;
                if (currentPage < 30) {
                    currentPage++;
                    AddCountStart();
                } else {
                    Intent intent = new Intent((Context) CountingStarActivity.this, Counting_Details_Activity.class);
                    intent.putExtra("counting", "30");
                    startActivity(intent);
                    finish();
                    // finish();
                }

            }
        });

        binding.header.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Utils.screenShot(binding.fullScreen, getWindow().getContext());
                }
            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.mFileName = null;
                binding.llGetCoin.setVisibility(View.INVISIBLE);
                isPlayed = false;
                if (currentPage > 0) {
                    currentPage--;
                    AddCountStart();
                }
            }
        });

        binding.llButton.tvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions_record(((AppCompatActivity) context))) {
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
                    RequestPermissions(((AppCompatActivity) context));
                }
            }
        });

        binding.llButton.tvSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Speach_Record_Activity.speakOut(context, binding.tvCountingName.getText().toString(), true);
                ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#008000")));
                ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

            }
        });


        binding.tvCountingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvCountingName.getText().toString(), false);

            }
        });

        binding.tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvCount.getText().toString(), false);

            }
        });

        binding.llButton.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = Speach_Record_Activity.getRecordedFileName();
                if (file_name != null) {
                    //PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);

                    ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#008000")));
                    ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    Speach_Record_Activity.speakOut(context, binding.tvCountingName.getText().toString(), false);
                    Utils.playRecordWithdelay(context, 2);
                    binding.llButton.tvRecordSpeak.setText("Record");
                    binding.tvUserCoin.setText("+" + FoundationLearningCoinBonus );
                    CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), FoundationLearningCoinBonus, "foundation_learn", "");
                    if (!isPlayed) {
                        Utils.EarnCoin(binding.llGetCoin, context, 2);
                        isPlayed = true;
                    }

                } else {
                    ImageViewCompat.setImageTintList(binding.llButton.slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(binding.llButton.checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AddCountStart() {
        if (currentPage < 30) {
            binding.tvCount.setText(String.valueOf(currentPage + 1));
            binding.tvCountingName.setText(EnglishNumberToWords.convert(Long.parseLong(String.valueOf(currentPage + 1))));

            int NUMBER_OF_IMAGES = currentPage + 1;

            Counting_Star_Adapter friendsAdapter = new Counting_Star_Adapter(NUMBER_OF_IMAGES, CountingStarActivity.this);
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }

}