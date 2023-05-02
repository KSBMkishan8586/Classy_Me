package com.ksbm.ontu.foundation.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.model.BankNoteSliderModel;
import com.ksbm.ontu.fundamental.activity.Fundamental_Jumble_Arrange;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class CustomPagerAdapter extends PagerAdapter {
    private Context mContext;
    List<BankNoteSliderModel> bankNoteSliderModels;
    boolean isRecording = false;
   // String Speak_word = "";
    SessionManager sessionManager;
    boolean isPlayed= false;

    public CustomPagerAdapter(Context context, List<BankNoteSliderModel> bankNoteSliderModels) {
        mContext = context;
        sessionManager= new SessionManager(mContext);
        this.bankNoteSliderModels= bankNoteSliderModels;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
//        collection.addView(layout);

        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Using different layouts in the view pager instead of images.
        int resId= bankNoteSliderModels.get(position).getLayout();

        View view = inflater.inflate(resId, collection, false);
        ((ViewPager) collection).addView(view, 0);
        isPlayed= false;

        Speach_Record_Activity.mFileName = null;
        TextView tv_100 = view.findViewById(R.id.tv_100);
        TextView huns = view.findViewById(R.id.hun);
        ImageView iv_girl = view.findViewById(R.id.iv_girl);

        TextView tv_user_coin = view.findViewById(R.id.tv_user_coin);
        LinearLayout ll_get_coin = view.findViewById(R.id.ll_get_coin);
      //  Speak_word= tv_100.getText().toString();

        tv_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_100.getText().toString().equalsIgnoreCase("100000")){
                    Speach_Record_Activity.speakOut(mContext, "one lakh", false);
                }else {
                    Speach_Record_Activity.speakOut(mContext, bankNoteSliderModels.get(position).getCounts(), false);
                }

            }
        });

        iv_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_100.getText().toString().equalsIgnoreCase("100000")){
                    Speach_Record_Activity.speakOut(mContext, "one lakh", false);
                }else {
                    Speach_Record_Activity.speakOut(mContext, bankNoteSliderModels.get(position).getCounts(), false);
                }

            }
        });

        huns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_100.getText().toString().equalsIgnoreCase("100000")){
                    Speach_Record_Activity.speakOut(mContext, "one lakh", false);
                }else {
                    Speach_Record_Activity.speakOut(mContext, bankNoteSliderModels.get(position).getCounts(), false);
                }

            }
        });

        //******************************************************
        LinearLayout tv_speak = view.findViewById(R.id.tv_speak);
        LinearLayout tv_slow = view.findViewById(R.id.tv_slow);
        LinearLayout tv_check = view.findViewById(R.id.tv_check);
        TextView tv_record_speak = view.findViewById(R.id.tv_record_speak);

        ImageView slowsp=view.findViewById(R.id.slowsp);
        ImageView recordImg=view.findViewById(R.id.record_img);
        ImageView checkimg=view.findViewById(R.id.checkimg);

        tv_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions_record(((AppCompatActivity) mContext))) {
                    if (!isRecording) {
                        isRecording = true;
                        Speach_Record_Activity.recording_start(mContext);
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
                    RequestPermissions(((AppCompatActivity) mContext));
                }
            }
        });

        tv_slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#008000")));
                ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                Speach_Record_Activity.speakOut(mContext, bankNoteSliderModels.get(position).getCounts(), true);
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

                    // PlayRecording(Speach_Record_Activity.getRecordedFileName(), mContext);
                    //play_text to speech
                    //Speach_Record_Activity.speakOut(mContext, tv_100.getText().toString(), false);
                    Utils.playRecordWithdelay(mContext, 0);
                    tv_record_speak.setText("Record");
                    tv_user_coin.setText("+"+FoundationLearningCoinBonus );
                    //Toast.makeText(mContext, ""+FoundationLearningCoinBonus + " coins", Toast.LENGTH_SHORT).show();
                    CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), FoundationLearningCoinBonus, "foundation_learn", "");
                    if(!isPlayed){
                        ///Utils.playMusic(R.raw.coin_sound, mContext);
                        Utils.EarnCoin(ll_get_coin, mContext, 0);
                        isPlayed= true;
                    }
                }else {
                    ImageViewCompat.setImageTintList(slowsp, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(recordImg, ColorStateList.valueOf(Color.parseColor("#FF000000")));
                    ImageViewCompat.setImageTintList(checkimg, ColorStateList.valueOf(Color.parseColor("#FF000000")));

                    Toast.makeText(mContext, "Please record first!", Toast.LENGTH_SHORT).show();
                }

            }
        });



        if (position == 6 || position ==8 || position ==9 || position == 10) {
            ImageView iv_i_button = view.findViewById(R.id.iv_i_button);
            iv_i_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msg = bankNoteSliderModels.get(position).getiButtonText();
                    SweetAlt.OpenFreeCoinDialog(mContext, msg);
                }
            });
        }


        return view;
    }


    @Override
    public int getCount() {
        return bankNoteSliderModels.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
