package com.ksbm.ontu.foundation.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.utils.Utils;

public class BodyPartDrop extends AppCompatActivity {

    //    String texts[] = {"    HAIR  ","    Ear  ","    Mouth  ","    Thigh  "};
    String texts[] = {"HAIR", "NOSE", "FINGURE"};
    String texts3[] = {"FOOT", "EYE", "KNEE"};
    //    String texts2[] = {"    KNEE  ","    FINGURE  ","    Neck  ","    EYE  "};
//    String texts3[] = {"    FOOT  ","    NOSE  "};
    RelativeLayout layout;
    int correctAnswer = 0;

    TextView hair, ear, eye, thigh, foot, knee, fingure, neck, mouth, nose;

    ImageView back, next;
    DisplayMetrics displayMetrics;
    int height;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodypartdropa);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        layout = (RelativeLayout) findViewById(R.id.linear);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        System.out.println("Aniesh " + width + "---" + height);
        hair = (TextView) findViewById(R.id.tv_Ha);
        nose = (TextView) findViewById(R.id.tv_Hd);
        fingure = (TextView) findViewById(R.id.tv_Hf);
        knee = (TextView) findViewById(R.id.tv_Hh);
        foot = (TextView) findViewById(R.id.tv_Hi);
        eye = (TextView) findViewById(R.id.tv_Hj);

        back = (ImageView) findViewById(R.id.back);
        next = (ImageView) findViewById(R.id.next);
        ear = (TextView) findViewById(R.id.tv_Hk);
        thigh = (TextView) findViewById(R.id.tv_Hn);
        neck = (TextView) findViewById(R.id.tv_Ho);
        mouth = (TextView) findViewById(R.id.tv_Hp);

        hair.setAllCaps(true);
        nose.setAllCaps(true);
        fingure.setAllCaps(true);
        knee.setAllCaps(true);
        foot.setAllCaps(true);
        eye.setAllCaps(true);
        ear.setAllCaps(true);
        thigh.setAllCaps(true);
        neck.setAllCaps(true);
        mouth.setAllCaps(true);

        generateTv(texts);
//        generateTv2(texts2);
        generateTv3(texts3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correctAnswer == 6) {
                    Intent intent = new Intent((Context) BodyPartDrop.this, BodyPartDropB.class);
                    intent.putExtra("totalans", correctAnswer + "");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText((Context) BodyPartDrop.this, "Please complete task first.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        TextView tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correctAnswer == 6) {
                    Intent intent = new Intent((Context) BodyPartDrop.this, BodyPartDropB.class);
                    intent.putExtra("totalans", correctAnswer + "");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText((Context) BodyPartDrop.this, "Please complete task first.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        TextView tv_previous = findViewById(R.id.tv_previous);
        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    TextView tv;

    private void generateTv(String texts[]) {

        int Xposition;
        int Yposition;
        if (width > 1000) {
            Xposition = 70;
        } else {
            Xposition = 50;
        }
        if (height > 2000) {
            Yposition = 1670;
        } else {
            Yposition = 1040;
        }

        for (int i = 0; i < texts.length; i++) {

            tv = new TextView((Context) this);
            tv.setText("" + texts[i]);
            tv.setX(Xposition);
            tv.setY(Yposition);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(16);
            tv.setAllCaps(true);

            //or to support all versions use
            Typeface typeface = ResourcesCompat.getFont(BodyPartDrop.this, R.font.futuramdbd);
            tv.setTypeface(typeface);

            if (i == 0) {
                tv.setOnTouchListener(mOnTouchListenerTv1);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartDrop.this, R.color.chat_box_left));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }

            } else if (i == 1) {
                tv.setOnTouchListener(mOnTouchListenerTv10);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartDrop.this, R.color.pink));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }
            } else if (i == 2) {
                tv.setOnTouchListener(mOnTouchListenerTv7);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartDrop.this, R.color.chat_box_left));
                if (width > 1000) {
                    Xposition += 110;
                } else {
                    Xposition += 185;
                }
            }
            System.out.println("Animesh " + i);
            layout.addView(tv);

        }
    }

    public final View.OnTouchListener mOnTouchListenerTv1 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);
                    int[] location = new int[2];
                    hair.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) hair.getX();
                    int objecty = (int) hair.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        hair.setText("HAIR");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70;
                        } else {
                            Xposition = 50;
                        }
                        if (height > 2000) {
                            Yposition = 1670;
                        } else {
                            Yposition = 1040;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Hair", false);
                    }

                    return true;
                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);
                    int[] location = new int[2];
                    hair.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv2 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    nose.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) nose.getX();
                    int objecty = (int) nose.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;


                    Toast.makeText(BodyPartDrop.this, nose.getX() + "" + "-" + (nose.getY()) + "" + "(" + resultx + "" + " " + resulty + "" + ")" + ", " + v.getX() + "" + "-" + v.getY() + "", Toast.LENGTH_SHORT).show();
                    if ((resultx) < 2500 && (resulty) < 2500) {
                        ear.setText("NOSE");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Wrrong Answer", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);

                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv3 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    eye.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) eye.getX();
                    int objecty = (int) eye.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        eye.setText("EYE");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70 + 350;
                        } else {
                            Xposition = 50 + 185;
                        }
                        if (height > 2000) {
                            Yposition = 1550;
                        } else {
                            Yposition = 960;
                        }

                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Eye", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);

                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv4 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    thigh.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) thigh.getX();
                    int objecty = (int) thigh.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;


                    Toast.makeText(BodyPartDrop.this, thigh.getX() + "" + "-" + (thigh.getY()) + "" + "(" + resultx + "" + " " + resulty + "" + ")" + ", " + v.getX() + "" + "-" + v.getY() + "", Toast.LENGTH_SHORT).show();
                    if ((resultx) < 2500 && (resulty) < 2500) {
                        thigh.setText("Thigh");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Thigh", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);

                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv5 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    foot.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) foot.getX();
                    int objecty = (int) foot.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        foot.setText("FOOT");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70;
                        } else {
                            Xposition = 50;
                        }
                        if (height > 2000) {
                            Yposition = 1550;
                        } else {
                            Yposition = 960;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Foot", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv6 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    knee.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) knee.getX();
                    int objecty = (int) knee.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        knee.setText("KNEE");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70 + 700;
                        } else {
                            Xposition = 50 + 370;
                        }
                        if (height > 2000) {
                            Yposition = 1550;
                        } else {
                            Yposition = 960;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Knee", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv7 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    fingure.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) fingure.getX();
                    int objecty = (int) fingure.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        fingure.setText("FINGURE");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {

                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70+350+110;
                        } else {
                            Xposition = 50+370;
                        }
                        if (height > 2000) {
                            Yposition = 1670;
                        } else {
                            Yposition = 1040;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Fingure", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv8 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    neck.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) neck.getX();
                    int objecty = (int) neck.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;


                    Toast.makeText(BodyPartDrop.this, neck.getX() + "" + "-" + (neck.getY()) + "" + "(" + resultx + "" + " " + resulty + "" + ")" + ", " + v.getX() + "" + "-" + v.getY() + "", Toast.LENGTH_SHORT).show();
                    if ((resultx) < 2500 && (resulty) < 2500) {
                        neck.setText("Neck");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Wrong Answer", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);

                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv9 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    mouth.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) mouth.getX();
                    int objecty = (int) mouth.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;


                    Toast.makeText(BodyPartDrop.this, mouth.getX() + "" + "-" + (mouth.getY()) + "" + "(" + resultx + "" + " " + resulty + "" + ")" + ", " + v.getX() + "" + "-" + v.getY() + "", Toast.LENGTH_SHORT).show();
                    if ((resultx) < 2500 && (resulty) < 2500) {
                        mouth.setText("Mouth");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Wrong Answer", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);

                    return true;
                }
            }
            return false;
        }
    };
    public final View.OnTouchListener mOnTouchListenerTv10 = new View.OnTouchListener() {
        int prevX, prevY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    prevY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevX;
                    prevX = (int) event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    par.topMargin += (int) event.getRawY() - prevY;
                    par.leftMargin += (int) event.getRawX() - prevX;
                    v.setLayoutParams(par);

                    int[] location = new int[2];
                    nose.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) nose.getX();
                    int objecty = (int) nose.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        nose.setText("NOSE");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Write Answer", false);
                    } else {
                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70+350;
                        } else {
                            Xposition = 50+185;
                        }
                        if (height > 2000) {
                            Yposition = 1670;
                        } else {
                            Yposition = 1040;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartDrop.this, "Nose", false);
                    }

                    return true;

                }
                case MotionEvent.ACTION_DOWN: {
                    prevX = (int) event.getRawX();
                    prevY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    v.setLayoutParams(par);


                    return true;
                }
            }
            return false;
        }
    };


    private void generateTv3(String texts[]) {

        int Xposition;
        int Yposition;

        if (width > 1000) {
            Xposition = 70;
        } else {
            Xposition = 50;
        }
        if (height > 2000) {
            Yposition = 1550;
        } else {
            Yposition = 960;
        }
        for (int i = 0; i < texts.length; i++) {

            TextView tv = new TextView(this);
            tv.setText("" + texts[i]);
            tv.setX(Xposition);
            tv.setY(Yposition);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(16);
            tv.setAllCaps(true);
            //or to support all versions use
            Typeface typeface = ResourcesCompat.getFont(BodyPartDrop.this, R.font.futuramdbd);
            tv.setTypeface(typeface);

            if (i == 0) {
                tv.setOnTouchListener(mOnTouchListenerTv5);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartDrop.this, R.color.powder_blue));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }

            } else if (i == 1) {
                tv.setOnTouchListener(mOnTouchListenerTv3);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartDrop.this, R.color.pink));

                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }
            } else if (i == 2) {
                tv.setOnTouchListener(mOnTouchListenerTv6);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartDrop.this, R.color.powder_blue));
                if (width > 1000) {
                    Xposition += 110;
                } else {
                    Xposition += 185;
                }
            }

            layout.addView(tv);
        }
    }


}
