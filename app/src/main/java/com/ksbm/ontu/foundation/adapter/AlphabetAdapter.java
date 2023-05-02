package com.ksbm.ontu.foundation.adapter;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.ABCD;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class AlphabetAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<AlphabetModelModel.AlphabetModelResponse> quizDetails;
    SessionManager sessionManager;
    boolean isRecording = false;
    boolean isPlayed = false;

    public AlphabetAdapter(Context context, List<AlphabetModelModel.AlphabetModelResponse> listarray1) {
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
        View view = layoutInflater.inflate(R.layout.slide_alphabet_quiz, container, false);

        AlphabetModelModel.AlphabetModelResponse alphabetModelResponse = quizDetails.get(quiz_position);


        /// Toast.makeText(context, ":- "+get, Toast.LENGTH_SHORT).show();


        TextView tv_alphabet_name = view.findViewById(R.id.tv_alphabet_name);
        TextView tv_alphabet_name_small = view.findViewById(R.id.tv_alphabet_name_small);
        ImageView iv_image = view.findViewById(R.id.iv_image);
        LinearLayout tv_speak = view.findViewById(R.id.tv_speak);
        LinearLayout alphabatestv = view.findViewById(R.id.alphabatestv);
        LinearLayout tv_slow = view.findViewById(R.id.tv_slow);
        LinearLayout tv_check = view.findViewById(R.id.tv_check);
        TextView tv_record_speak = view.findViewById(R.id.tv_record_speak);
        TextView tv_user_coin = view.findViewById(R.id.tv_user_coin);
        LinearLayout ll_get_coin = view.findViewById(R.id.ll_get_coin);
        LinearLayout ll_dynamic_text = view.findViewById(R.id.ll_dynamic_text);
        TextView txtlearn = view.findViewById(R.id.txtlearn);

        ImageView slowsp = view.findViewById(R.id.slowsp);
        ImageView recordImg = view.findViewById(R.id.record_img);
        ImageView checkimg = view.findViewById(R.id.checkimg);

        isPlayed = false;
        //****set character in seperate box***
        if (alphabetModelResponse.getAlphabet_word() != null && !alphabetModelResponse.getAlphabet_word().equalsIgnoreCase("")) {
            ll_dynamic_text.setVisibility(View.VISIBLE);
            String name = alphabetModelResponse.getAlphabet_word();
            txtlearn.setText(alphabetModelResponse.getLearningText());
            //txtlearn.setVisibility(View.GONE);
            Utils.SetSeperateCharacterInBox(name, context, ll_dynamic_text);
        } else {
            ll_dynamic_text.setVisibility(View.INVISIBLE);
            if (alphabetModelResponse.getCapital_alphabet_letter().isEmpty()) {
                // txtlearn.setVisibility(View.VISIBLE);
                txtlearn.setText(alphabetModelResponse.getLearningText());
            }
        }

        //******

        ll_get_coin.setVisibility(View.GONE);
        Speach_Record_Activity.mFileName = null;

        txtlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getAlphabet_word() + "", false);
                } else {
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getLearningText() + "", false);
                }
            }
        });

        if (alphabetModelResponse.getCapital_alphabet_letter().isEmpty()) {
            alphabatestv.setVisibility(View.GONE);
        } else {
            alphabatestv.setVisibility(View.VISIBLE);
        }

        tv_alphabet_name.setText(alphabetModelResponse.getCapital_alphabet_letter());
        tv_alphabet_name_small.setText(alphabetModelResponse.getSmall_alphabet_letter());

        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.abc);
//        requestOptions.error(R.drawable.abc);
        requestOptions.isMemoryCacheable();
        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load(alphabetModelResponse.getImage())
                .into(iv_image);

        container.addView(view);

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

                if (!Constant.foundation_id.equalsIgnoreCase(ABCD)) {
                    if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                        Speach_Record_Activity.speakOut(context, alphabetModelResponse.getAlphabet_word() + "", false);
                    } else {
                        if (alphabetModelResponse.getCapital_alphabet_letter().isEmpty()) {
                            Speach_Record_Activity.speakOut(context, alphabetModelResponse.getLearningText() + "", true);
                        } else {
                            Speach_Record_Activity.speakOut(context, alphabetModelResponse.getAlphabet_word() + "", true);
                        }
                    }


                } else {

                    if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                        Speach_Record_Activity.speakOut(context, alphabetModelResponse.getAlphabet_word() + "", false);
                    } else {
                        if (alphabetModelResponse.getCapital_alphabet_letter().isEmpty()) {
                            Speach_Record_Activity.speakOut(context, alphabetModelResponse.getLearningText() + "", true);
                        } else {
                            Speach_Record_Activity.speakOut(context, alphabetModelResponse.getAlphabet_word() + "", true);
                        }
                    }
                }

            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //******
//                Intent intent= new Intent(context, PhotoViewActivity.class);
//                intent.putExtra("imgUrl", alphabetModelResponse.getImage());
//                context.startActivity(intent);
                if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getAlphabet_word() + "", false);
                } else {
                    if (alphabetModelResponse.getAudioFile() != null && !alphabetModelResponse.getAudioFile().isEmpty()) {
                        Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show();
                        PlayRecording(alphabetModelResponse.getAudioFile(), context);
                    } else {
                        Speach_Record_Activity.speakOut(context, "" + alphabetModelResponse.getLearningText(), false);
                    }
                }

            }
        });

        tv_alphabet_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, "" + alphabetModelResponse.getCapital_alphabet_letter(), false);
            }
        });

        tv_alphabet_name_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, "" + alphabetModelResponse.getSmall_alphabet_letter(), false);

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
                    //Speach_Record_Activity.speakOut(context, alphabetModelResponse.getCapital_alphabet_letter(), false);
                    Utils.playRecordWithdelay(context, 0);
                    tv_record_speak.setText("Record");
                    tv_user_coin.setText("+" + FoundationLearningCoinBonus );

                    if (!isPlayed) {
                        CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), FoundationLearningCoinBonus, "foundation_learn", alphabetModelResponse.getLearning_id());
                        Utils.EarnCoin(ll_get_coin, context, 0);
                        isPlayed = true;
                    }
                } else {
                    ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}
