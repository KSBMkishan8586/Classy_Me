package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.activity.Animal_Activity;
import com.ksbm.ontu.foundation.model.LearningActivityModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class AnimalAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<LearningActivityModel> quizDetails;
    SessionManager sessionManager;
    boolean isRecording = false;
    boolean isPlayed= false;

    public AnimalAdapter(Context context, List<LearningActivityModel> listarray1) {
        this.context = context;
        this.quizDetails = listarray1;
        sessionManager = new SessionManager(context);
    }

    @Override
    public int getCount() {
        return quizDetails.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int quiz_position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_animal_quiz, container, false);

        LearningActivityModel alphabetModelResponse = quizDetails.get(quiz_position);

        TextView tv_alphabet_name = view.findViewById(R.id.tv_name);
        ImageView iv_image = view.findViewById(R.id.iv_image);
        LinearLayout tv_speak = view.findViewById(R.id.tv_speak);
        LinearLayout tv_slow = view.findViewById(R.id.tv_slow);
        LinearLayout tv_check = view.findViewById(R.id.tv_check);
        TextView tv_record_speak = view.findViewById(R.id.tv_record_speak);
        TextView tv_user_coin = view.findViewById(R.id.tv_user_coin);
        LinearLayout ll_get_coin = view.findViewById(R.id.ll_get_coin);
        LinearLayout ll_dynamic_text = view.findViewById(R.id.ll_dynamic_text);

        ImageView slowsp=view.findViewById(R.id.slowsp);
        ImageView recordImg=view.findViewById(R.id.record_img);
        ImageView checkimg=view.findViewById(R.id.checkimg);

        ll_get_coin.setVisibility(View.GONE);
        Speach_Record_Activity.mFileName = null;
        isPlayed= false;

        //****set character in seperate box***
        if (alphabetModelResponse.getAlphabet_word()!=null && !alphabetModelResponse.getAlphabet_word().equalsIgnoreCase("")){
            ll_dynamic_text.setVisibility(View.VISIBLE);
            String name= alphabetModelResponse.getAlphabet_word();

            Utils.SetSeperateCharacterInBox(name, context, ll_dynamic_text);
        }else {
            ll_dynamic_text.setVisibility(View.INVISIBLE);
        }


        tv_alphabet_name.setText(alphabetModelResponse.getName());

        RequestOptions requestOptions = new RequestOptions();
       // requestOptions.placeholder(R.drawable.placeholder_square);
       // requestOptions.error(R.drawable.placeholder_square);
        requestOptions.isMemoryCacheable();
        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load(alphabetModelResponse.getImage())
                .into(iv_image);

        tv_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions_record(((AppCompatActivity) context))) {
                    if (!isRecording) {
                        isRecording = true;
                        Speach_Record_Activity.recording_start(context);
                        tv_record_speak.setText("Stop");
                        ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#008000")));
                        ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                        //  Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();

                    } else {
                        tv_record_speak.setText("Record");
                        isRecording = false;
                        Speach_Record_Activity.stop_recording();
                        ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                        ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                        // Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                    }
                } else {
                    RequestPermissions(((AppCompatActivity) context));
                }
            }
        });

        tv_slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#008000")));
                ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                Speach_Record_Activity.speakOut(context, alphabetModelResponse.getName(), true);

            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alphabetModelResponse.getAudioFile() != null && !alphabetModelResponse.getAudioFile().isEmpty()) {
                    PlayRecording(alphabetModelResponse.getAudioFile(), context);
                } else {
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getLearningText(), false);
                }
            }
        });

        tv_alphabet_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, alphabetModelResponse.getName(), false);
            }
        });

        tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = Speach_Record_Activity.getRecordedFileName();
                if (file_name != null) {
                    ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#008000")));
                    ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    // PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getName(), false);
                    Utils.playRecordWithdelay(context, 2);
                    tv_user_coin.setText("+"+FoundationLearningCoinBonus);
                    tv_record_speak.setText("Record");
                    if(!isPlayed){
                        ((Animal_Activity)context).SubmitCoin(FoundationLearningCoinBonus, "foundation_learn",quiz_position);
                        Utils.EarnCoin(ll_get_coin, context, 0);
                        isPlayed= true;
                    }

                }else {
                    ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        container.addView(view);
        return view;
    }

    private void EarnCoin(LinearLayout ll_get_coin) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_get_coin.setVisibility(View.VISIBLE);

            }
        }, 4000);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
