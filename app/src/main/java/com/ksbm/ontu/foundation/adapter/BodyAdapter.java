package com.ksbm.ontu.foundation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.model.BankNoteSliderModel;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BodyAdapter extends PagerAdapter {
    private Context mContext;
    List<BankNoteSliderModel> bankNoteSliderModels;
    boolean isRecording = false;
    SimpleExoPlayer player;

    public BodyAdapter(Context context, List<BankNoteSliderModel> bankNoteSliderModels) {
        mContext = context;
        this.bankNoteSliderModels = bankNoteSliderModels;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
//        collection.addView(layout);

        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Using different layouts in the view pager instead of images.
        int resId = bankNoteSliderModels.get(position).getLayout();

        View view = inflater.inflate(resId, collection, false);
        ((ViewPager) collection).addView(view, 0);


        if (position == 0) {
            Speach_Record_Activity.mFileName = null;
            TextView bodypart = view.findViewById(R.id.bodypart1);

            bodypart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.ZoomInImage(bodypart);
                    Speach_Record_Activity.speakOut(mContext, "Body Part Front", false);

                }
            });

            TextView tv_Ha = view.findViewById(R.id.tv_Ha);
            TextView tv_Hb = view.findViewById(R.id.tv_Hb);
            TextView tv_Hc = view.findViewById(R.id.tv_Hc);
            TextView tv_Hd = view.findViewById(R.id.tv_Hd);
            TextView tv_He = view.findViewById(R.id.tv_He);
            TextView tv_Hf = view.findViewById(R.id.tv_Hf);
            TextView tv_Hg = view.findViewById(R.id.tv_Hg);
            TextView tv_Hh = view.findViewById(R.id.tv_Hh);
            TextView tv_Hi = view.findViewById(R.id.tv_Hi);
            TextView tv_Hj = view.findViewById(R.id.tv_Hj);
            TextView tv_Hk = view.findViewById(R.id.tv_Hk);
            TextView tv_Hl = view.findViewById(R.id.tv_Hl);
            TextView tv_Hm = view.findViewById(R.id.tv_Hm);
            TextView tv_Hn = view.findViewById(R.id.tv_Hn);
            TextView tv_Ho = view.findViewById(R.id.tv_Ho);
            TextView tv_Hp = view.findViewById(R.id.tv_Hp);

            startblink(tv_Ha);


            tv_Ha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Hair", false);
                    startblink(tv_Ha);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Eye", false);
                    startblink(tv_Hb);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Mouth", false);
                    startblink(tv_Hc);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Shoulder", false);
                    startblink(tv_Hd);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_He.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Finger", false);
                    startblink(tv_He);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Knee", false);
                    startblink(tv_Hf);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Foot", false);
                    startblink(tv_Hg);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Calf", false);
                    startblink(tv_Hh);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Thigh", false);
                    startblink(tv_Hi);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Neck", false);
                    startblink(tv_Hj);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });


            tv_Hk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Nose", false);
                    startblink(tv_Hk);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Ear", false);
                    startblink(tv_Hl);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Shoulder", false);
                    startblink(tv_Hm);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Eye", false);
                    startblink(tv_Hn);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Ho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Ear", false);
                    startblink(tv_Ho);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Hip", false);
                    startblink(tv_Hp);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Ha);
                }
            });


        } else if (position == 1) {
            Speach_Record_Activity.mFileName = null;


            TextView bodypart = view.findViewById(R.id.bodypart1);

            bodypart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.ZoomInImage(bodypart);
                    Speach_Record_Activity.speakOut(mContext, "Body Part Back", false);

                }
            });


            TextView tv_Ha = view.findViewById(R.id.tv_Ha);
            TextView tv_Hb = view.findViewById(R.id.tv_Hb);
            TextView tv_Hc = view.findViewById(R.id.tv_Hc);
            TextView tv_Hd = view.findViewById(R.id.tv_Hd);
            TextView tv_He = view.findViewById(R.id.tv_He);
            TextView tv_Hf = view.findViewById(R.id.tv_Hf);
            TextView tv_Hg = view.findViewById(R.id.tv_Hg);
            TextView tv_Hh = view.findViewById(R.id.tv_Hh);
            TextView tv_Hi = view.findViewById(R.id.tv_Hi);
            TextView tv_Hj = view.findViewById(R.id.tv_Hj);
            TextView tv_Hk = view.findViewById(R.id.tv_Hk);
            TextView tv_Hl = view.findViewById(R.id.tv_Hl);
            TextView tv_Hm = view.findViewById(R.id.tv_Hm);
            TextView tv_Hn = view.findViewById(R.id.tv_Hn);
            TextView tv_Ho = view.findViewById(R.id.tv_Ho);
            TextView tv_Hp = view.findViewById(R.id.tv_Hp);


            startblink(tv_Ha);


            tv_Ha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Head", false);
                    startblink(tv_Ha);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Hand", false);
                    startblink(tv_Hb);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.ZoomInImage(tv_Hc);
                    Speach_Record_Activity.speakOut(mContext, "Waist", false);
                    startblink(tv_Hc);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Hip", false);
                    startblink(tv_Hd);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_He.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Ankle", false);
                    startblink(tv_He);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Heel", false);
                    startblink(tv_Hf);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Calf", false);
                    startblink(tv_Hg);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Elbow", false);
                    startblink(tv_Hh);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Knee", false);
                    startblink(tv_Hi);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Foot", false);
                    startblink(tv_Hj);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });


            tv_Hk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Thigh", false);
                    startblink(tv_Hk);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Arm", false);
                    startblink(tv_Hl);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Shoulder", false);
                    startblink(tv_Hm);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Eye", false);
                    startblink(tv_Hn);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Ho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Ear", false);
                    startblink(tv_Ho);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ha);
                    stopAnimation(tv_Hp);
                }
            });

            tv_Hp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, "Hair", false);
                    startblink(tv_Hp);
                    stopAnimation(tv_Hb);
                    stopAnimation(tv_Hc);
                    stopAnimation(tv_Hd);
                    stopAnimation(tv_He);
                    stopAnimation(tv_Hf);
                    stopAnimation(tv_Hg);
                    stopAnimation(tv_Hh);
                    stopAnimation(tv_Hi);
                    stopAnimation(tv_Hj);
                    stopAnimation(tv_Hk);
                    stopAnimation(tv_Hl);
                    stopAnimation(tv_Hm);
                    stopAnimation(tv_Hn);
                    stopAnimation(tv_Ho);
                    stopAnimation(tv_Ha);
                }
            });


        } else if (position == 2) {
        } else if (position == 3) {
        } else if (position == 4) {

        }

        return view;
    }


    public void startblink(TextView view) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        view.startAnimation(animation); //to start animation
    }

    public void stopAnimation(View view) {
        view.clearAnimation();
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
