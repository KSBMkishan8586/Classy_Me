package com.ksbm.ontu.foundation.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class TimeLearnAdapter extends PagerAdapter {
    private Context mContext;
    List<BankNoteSliderModel> bankNoteSliderModels;
    boolean isRecording = false;
    SimpleExoPlayer player;


    public TimeLearnAdapter(Context context, List<BankNoteSliderModel> bankNoteSliderModels) {
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

        TextView tv_text1 = view.findViewById(R.id.tv_text1);
        TextView tv_text2 = view.findViewById(R.id.tv_text2);
        TextView tv_text3 = view.findViewById(R.id.tv_text3);
        TextView tv_text4 = view.findViewById(R.id.tv_text4);
        TextView tv_text5 = view.findViewById(R.id.tv_text5);
        TextView tv_text6 = view.findViewById(R.id.tv_text6);
        TextView tv_text7 = view.findViewById(R.id.tv_text7);

        if (position == 0) {
            Speach_Record_Activity.mFileName = null;
            TextView secondhand = view.findViewById(R.id.secondhand);
            TextView secondhands = view.findViewById(R.id.secondhands);
            View second_tick = view.findViewById(R.id.second_tick);
            TextView longesthand = view.findViewById(R.id.longesthand);
            TextView longesthands = view.findViewById(R.id.longesthands);
            TextView fastesthand = view.findViewById(R.id.fastesthand);
            TextView fastesthandf = view.findViewById(R.id.fastesthandf);
            TextView tv_heading = view.findViewById(R.id.tv_heading);
            TextView tv_heading1 = view.findViewById(R.id.tv_heading1);
            TextView ll_text4 = view.findViewById(R.id.ll_text4);
            TextView ll_text5 = view.findViewById(R.id.ll_text5);
            TextView ll_text6 = view.findViewById(R.id.ll_text6);
            View secondhandV = view.findViewById(R.id.secondhandV);
            View longesthandV = view.findViewById(R.id.longesthandV);
            View fastesthandV = view.findViewById(R.id.fastesthandV);

            Utils.setRainbowColor(tv_heading);
            Utils.setRainbowColor(tv_heading1);
            tv_heading.setText(Utils.UnderlineText(tv_heading.getText().toString()));

            startblink(secondhand);
            startblink(second_tick);

            secondhand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(longesthand);
                    stopAnimation(fastesthand);
                    startblink(secondhand);
                    Speach_Record_Activity.speakOut(mContext, secondhand.getText().toString(), false);
                }
            });

            longesthand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Utils.blinkTextView(longesthand, mContext);
                    stopAnimation(secondhand);
                    stopAnimation(fastesthand);
                    startblink(longesthand);
                    Speach_Record_Activity.speakOut(mContext, longesthand.getText().toString(), false);
                }
            });


            fastesthand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Utils.blinkTextView(fastesthand, mContext);
                    stopAnimation(longesthand);
                    stopAnimation(secondhand);
                    startblink(fastesthand);
                    Speach_Record_Activity.speakOut(mContext, fastesthand.getText().toString(), false);

                }
            });

            ll_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);
                    if (tv_text4.isEnabled()) {
                        tv_text4.performClick();
                    }
                }
            });

            tv_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text4, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);

                    secondhand.setEnabled(false);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(false);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(true);
                    tv_text6.setEnabled(false);
                    startblink(tv_text5);
                }
            });

            ll_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);
                    if (tv_text5.isEnabled()) {
                        tv_text5.performClick();
                    }
                }
            });

            tv_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text5, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);

                    secondhand.setEnabled(false);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(false);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(false);
                    tv_text6.setEnabled(true);
                    startblink(tv_text6);
                }
            });

            ll_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);
                    if (tv_text6.isEnabled()) {
                        tv_text6.performClick();
                    }
                }
            });

            tv_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text6, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);

                    secondhand.setEnabled(true);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(true);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(false);
                    tv_text6.setEnabled(false);
                    startblink(secondhand);
                }
            });

            tv_heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_heading.getText().toString(), false);
                }
            });
            tv_heading1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_heading1.getText().toString(), false);
                }
            });


        } else if (position == 1) {
            Speach_Record_Activity.mFileName = null;
            TextView secondhand = view.findViewById(R.id.secondhand);
            TextView secondhands = view.findViewById(R.id.secondhands);
            TextView longesthand = view.findViewById(R.id.longesthand);
            View minute_tick = view.findViewById(R.id.minute_tick);
            TextView longesthands = view.findViewById(R.id.longesthands);
            TextView fastesthand = view.findViewById(R.id.fastesthand);
            TextView fastesthandf = view.findViewById(R.id.fastesthandf);
            TextView tv_heading = view.findViewById(R.id.tv_heading);
            TextView tv_heading1 = view.findViewById(R.id.tv_heading1);
            TextView ll_text4 = view.findViewById(R.id.ll_text4);
            TextView ll_text5 = view.findViewById(R.id.ll_text5);
            TextView ll_text6 = view.findViewById(R.id.ll_text6);
            View secondhandV = view.findViewById(R.id.secondhandV);
            View longesthandV = view.findViewById(R.id.longesthandV);
            View fastesthandV = view.findViewById(R.id.fastesthandV);

            Utils.setRainbowColor(tv_heading);
            Utils.setRainbowColor(tv_heading1);
            tv_heading.setText(Utils.UnderlineText(tv_heading.getText().toString()));

            startblink(secondhand);
            startblink(minute_tick);


            secondhand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(longesthand);
                    stopAnimation(fastesthand);
                    startblink(secondhand);
                    Speach_Record_Activity.speakOut(mContext, secondhand.getText().toString(), false);
                }
            });


            longesthand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(fastesthand);
                    stopAnimation(secondhand);
                    startblink(longesthand);
                    Speach_Record_Activity.speakOut(mContext, longesthand.getText().toString(), false);
                }
            });


            fastesthand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(secondhand);
                    stopAnimation(longesthand);
                    startblink(fastesthand);
                    Speach_Record_Activity.speakOut(mContext, fastesthand.getText().toString(), false);
                }
            });

            ll_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);
                    if (tv_text4.isEnabled()) {
                        tv_text4.performClick();
                    }
                }
            });

            tv_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text4, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);

                    secondhand.setEnabled(false);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(false);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(true);
                    tv_text6.setEnabled(false);
                    startblink(tv_text5);
                }
            });

            ll_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);
                    if (tv_text5.isEnabled()) {
                        tv_text5.performClick();
                    }
                }
            });

            tv_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text5, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);

                    secondhand.setEnabled(false);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(false);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(false);
                    tv_text6.setEnabled(true);
                    startblink(tv_text6);
                }
            });

            ll_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);
                    if (tv_text6.isEnabled()) {
                        tv_text6.performClick();
                    }
                }
            });

            tv_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text6, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);

                    secondhand.setEnabled(true);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(true);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(false);
                    tv_text6.setEnabled(false);
                    startblink(secondhand);
                }
            });

            tv_heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_heading.getText().toString(), false);
                }
            });
            tv_heading1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_heading1.getText().toString(), false);
                }
            });
        } else if (position == 2) {
            Speach_Record_Activity.mFileName = null;
            TextView secondhand = view.findViewById(R.id.secondhand);
            View hour_tick = view.findViewById(R.id.hour_tick);
            TextView secondhands = view.findViewById(R.id.secondhands);
            TextView longesthand = view.findViewById(R.id.longesthand);
            TextView longesthands = view.findViewById(R.id.longesthands);
            TextView fastesthand = view.findViewById(R.id.fastesthand);
            TextView fastesthandf = view.findViewById(R.id.fastesthandf);
            TextView tv_heading = view.findViewById(R.id.tv_heading);
            TextView tv_heading1 = view.findViewById(R.id.tv_heading1);
            TextView ll_text4 = view.findViewById(R.id.ll_text4);
            TextView ll_text5 = view.findViewById(R.id.ll_text5);
            TextView ll_text6 = view.findViewById(R.id.ll_text6);
            View secondhandV = view.findViewById(R.id.secondhandV);
            View longesthandV = view.findViewById(R.id.longesthandV);
            View fastesthandV = view.findViewById(R.id.fastesthandV);

            Utils.setRainbowColor(tv_heading);
            Utils.setRainbowColor(tv_heading1);
            tv_heading.setText(Utils.UnderlineText(tv_heading.getText().toString()));

            startblink(secondhand);
            startblink(hour_tick);

            secondhand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(longesthand);
                    stopAnimation(fastesthand);
                    startblink(secondhand);
                    Speach_Record_Activity.speakOut(mContext, secondhand.getText().toString(), false);
                }
            });


            longesthand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(fastesthand);
                    stopAnimation(secondhand);
                    startblink(longesthand);
                    Speach_Record_Activity.speakOut(mContext, longesthand.getText().toString(), false);
                }
            });


            fastesthand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopAnimation(secondhand);
                    stopAnimation(longesthand);
                    startblink(fastesthand);
                    Speach_Record_Activity.speakOut(mContext, fastesthand.getText().toString(), false);
                }
            });

            ll_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);
                    if (tv_text4.isEnabled()) {
                        tv_text4.performClick();
                    }
                }
            });

            tv_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text4, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);

                    secondhand.setEnabled(false);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(false);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(true);
                    tv_text6.setEnabled(false);
                    startblink(tv_text5);
                }
            });

            ll_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);
                    if (tv_text5.isEnabled()) {
                        tv_text5.performClick();
                    }
                }
            });

            tv_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text5, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);

                    secondhand.setEnabled(false);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(false);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(false);
                    tv_text6.setEnabled(true);
                    startblink(tv_text6);
                }
            });

            ll_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);
                    if (tv_text6.isEnabled()) {
                        tv_text6.performClick();
                    }
                }
            });

            tv_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.blinkTextView(tv_text6, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);

                    secondhand.setEnabled(true);
                    longesthand.setEnabled(false);
                    fastesthand.setEnabled(true);
                    tv_text4.setEnabled(false);
                    tv_text5.setEnabled(false);
                    tv_text6.setEnabled(false);
                    startblink(secondhand);
                }
            });

            tv_heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_heading.getText().toString(), false);
                }
            });
            tv_heading1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(mContext, tv_heading1.getText().toString(), false);
                }
            });
        } else if (position == 3) {
            TextView tv_text8 = view.findViewById(R.id.tv_text8);
            TextView tv_text9 = view.findViewById(R.id.tv_text9);
            TextView tv_text10 = view.findViewById(R.id.tv_text10);
            TextView tv_text_letslearn = view.findViewById(R.id.tv_text_letslearn);
            ImageView iv_eyeblink = view.findViewById(R.id.iv_eyeblink);

            Glide.with(mContext)
                    .load(R.drawable.clock_trans)
                    // .error(R.drawable.ankit)
                    //.placeholder(R.drawable.ankit)
                    .into(iv_eyeblink);

            startblink(tv_text_letslearn);

            tv_text_letslearn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text_letslearn.getText().toString(), false);
                    startblink(tv_text1);
                }
            });
            tv_text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text1.getText().toString(), false);
                    startblink(tv_text2);
                }
            });
            tv_text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text2.getText().toString(), false);
                    startblink(tv_text3);
                }
            });
            tv_text3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text3.getText().toString(), false);
                    startblink(tv_text4);
                }
            });
            tv_text4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text4.getText().toString(), false);
                    startblink(tv_text5);
                }
            });
            tv_text5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text5.getText().toString(), false);
                    startblink(tv_text6);
                }
            });
            tv_text6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text6.getText().toString(), false);
                    startblink(tv_text7);
                }
            });
            tv_text7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text7.getText().toString(), false);
                    startblink(tv_text8);
                }
            });


            tv_text8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text8.getText().toString(), false);
                    startblink(tv_text9);
                }
            });
            tv_text9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text9.getText().toString(), false);
                    startblink(tv_text10);
                }
            });
            tv_text10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RefreshTextView(tv_text_letslearn, mContext);
                    Utils.RefreshTextView(tv_text1, mContext);
                    Utils.RefreshTextView(tv_text2, mContext);
                    Utils.RefreshTextView(tv_text3, mContext);
                    Utils.RefreshTextView(tv_text4, mContext);
                    Utils.RefreshTextView(tv_text5, mContext);
                    Utils.RefreshTextView(tv_text6, mContext);
                    Utils.RefreshTextView(tv_text7, mContext);
                    Utils.RefreshTextView(tv_text8, mContext);
                    Utils.RefreshTextView(tv_text9, mContext);
                    Utils.RefreshTextView(tv_text10, mContext);
                    Speach_Record_Activity.speakOut(mContext, tv_text10.getText().toString(), false);
                    startblink(tv_text1);
                }
            });

        } else if (position == 4) {
            Speach_Record_Activity.mFileName = null;
            ImageView CI1 = view.findViewById(R.id.CI1);
            ImageView CI2 = view.findViewById(R.id.CI2);
            ImageView CI3 = view.findViewById(R.id.CI3);
            ImageView CI4 = view.findViewById(R.id.CI4);
            ImageView CI5 = view.findViewById(R.id.CI5);
            ImageView CI6 = view.findViewById(R.id.CI6);
            ImageView CI7 = view.findViewById(R.id.CI7);
            ImageView CI8 = view.findViewById(R.id.CI8);
            ImageView CI9 = view.findViewById(R.id.CI9);
            ImageView CI10 = view.findViewById(R.id.CI10);
            ImageView CI11 = view.findViewById(R.id.CI11);
            ImageView CI12 = view.findViewById(R.id.CI12);
            TextView tv_0_clock = view.findViewById(R.id.tv_0_clock);
            TextView tv_halfpast = view.findViewById(R.id.tv_halfpast);
            TextView tv_heading = view.findViewById(R.id.tv_heading);

            Utils.setRainbowColor(tv_0_clock);
            Utils.setRainbowColor(tv_halfpast);
            Utils.setRainbowColor(tv_heading);


            CI12.setEnabled(false);
            CI1.setEnabled(true);
            CI2.setEnabled(false);
            CI3.setEnabled(false);
            CI4.setEnabled(false);
            CI5.setEnabled(false);
            CI6.setEnabled(false);
            CI7.setEnabled(false);
            CI8.setEnabled(false);
            CI9.setEnabled(false);
            CI10.setEnabled(false);
            CI11.setEnabled(false);
            startblinkimage(CI1);


            CI1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Five past, Twelve", false);
                    Utils.blinkImageView(CI1, mContext);
                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(true);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI2);

                }
            });

            CI2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Ten past, Twelve", false);
                    Utils.blinkImageView(CI2, mContext);
                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(true);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI3);

                }
            });

            CI3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Kwaatar past, Twelve", false);
                    Utils.blinkImageView(CI3, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(true);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI4);

                }
            });

            CI4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Twenty past, Twelve", false);
                    Utils.blinkImageView(CI4, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(true);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI5);

                }
            });

            CI5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Twenty Five past, Twelve", false);
                    Utils.blinkImageView(CI5, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(true);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI6);

                }
            });

            CI6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Haalf past, Twelve", false);
                    Utils.blinkImageView(CI6, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(true);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI7);

                }
            });

            CI7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Twenty Five minutes Too, One", false);
                    Utils.blinkImageView(CI7, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(true);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI8);

                }
            });

            CI8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Twenty minutes, Too, One", false);
                    Utils.blinkImageView(CI8, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(true);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI9);

                }
            });

            CI9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "15 minutes Too, One", false);
                    Utils.blinkImageView(CI9, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(true);
                    CI11.setEnabled(false);
                    startblinkimage(CI10);

                }
            });

            CI10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "10 minutes, Too, One", false);
                    Utils.blinkImageView(CI10, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(true);
                    startblinkimage(CI11);

                }
            });

            CI11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Five, Too, One", false);
                    Utils.blinkImageView(CI11, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(false);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(true);
                    startblinkimage(CI2);

                }
            });

            CI12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Twelve, O Clock", false);
                    Utils.blinkImageView(CI12, mContext);

                    CI12.setEnabled(false);
                    CI1.setEnabled(true);
                    CI2.setEnabled(false);
                    CI3.setEnabled(false);
                    CI4.setEnabled(false);
                    CI5.setEnabled(false);
                    CI6.setEnabled(false);
                    CI7.setEnabled(false);
                    CI8.setEnabled(false);
                    CI9.setEnabled(false);
                    CI10.setEnabled(false);
                    CI11.setEnabled(false);
                    startblinkimage(CI1);
                }
            });

            tv_0_clock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Twelve, O Clock", false);

                }
            });

            tv_halfpast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, "Haalf past, Twelve", false);

                }
            });

            tv_heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Speach_Record_Activity.speakOut(mContext, tv_heading.getText().toString(), false);

                }
            });

        }

        return view;
    }


    public void startblink(View view) {
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

    public void startblinkimage(ImageView view) {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        view.startAnimation(animation); //to start animation
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
