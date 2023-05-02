package com.ksbm.ontu.foundation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;

public class BodyPartBackA extends AppCompatActivity {

    int correctAnswer=10;
    String texts[] = {"Head","Shoulder","Ankle","Hip"};
    RelativeLayout layout;
    TextView ankle,beck,hip,head;
    ImageView back,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodypartbacka);



        layout = (RelativeLayout) findViewById(R.id.linear);

        back = (ImageView) findViewById(R.id.back);
        next = (ImageView) findViewById(R.id.next);
        ankle = (TextView) findViewById(R.id.tv_Hi);
        beck = (TextView) findViewById(R.id.tv_Hd);
        hip = (TextView) findViewById(R.id.tv_Hf);
        head = (TextView) findViewById(R.id.tv_Ha);

        ankle.setAllCaps(true);
        beck.setAllCaps(true);
        hip.setAllCaps(true);
        hip.setAllCaps(true);
//        correctAnswer = 10;

        generateTv(texts);
//        generateTv2(texts2);
//        generateTv3(texts3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyPartBackA.this,BodyPartDropB.class);
                intent.putExtra("totalans",correctAnswer+"");
                startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(correctAnswer == 14)
                {
                    Intent intent = new Intent(BodyPartBackA.this,BodyPartBackB.class);
                    intent.putExtra("totalans",correctAnswer+"");
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(BodyPartBackA.this, "Please complete task first.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        TextView tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(correctAnswer == 14)
                {

                    Intent intent = new Intent(BodyPartBackA.this,BodyPartBackB.class);
                    intent.putExtra("totalans",correctAnswer+"");
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(BodyPartBackA.this, "Please complete task first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView tv_previous = findViewById(R.id.tv_previous);
        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BodyPartBackA.this,BodyPartDropB.class);
                intent.putExtra("totalans",correctAnswer+"");
                startActivity(intent);
                finish();
            }
        });


    }
    TextView tv;

    private void generateTv(String texts[]) {

        int Xposition = 80;
        int Yposition = 1770;

        for (int i = 0; i < texts.length; i++) {

            tv = new TextView(this);
            tv.setText("" + texts[i]);
            tv.setX(Xposition);
            tv.setY(Yposition);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(16);
            tv.setAllCaps(true);
            //or to support all versions use
            Typeface typeface = ResourcesCompat.getFont(BodyPartBackA.this, R.font.futuramdbd);
            tv.setTypeface(typeface);

            if(i==0)
            {
                tv.setOnTouchListener(mOnTouchListenerTv2);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackA.this, R.color.chat_box_left));
                Xposition += 200;

            } else if(i==1)
            {
                tv.setOnTouchListener(mOnTouchListenerTv9);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackA.this, R.color.pink));
                Xposition += 350;
            } else if(i==2)
            {
                tv.setOnTouchListener(mOnTouchListenerTv4);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackA.this, R.color.chat_box_left));
                Xposition += 250;
            } else if(i==3)
            {
                tv.setOnTouchListener(mOnTouchListenerTv8);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackA.this, R.color.powder_blue));
                Xposition += 100;
            }

            layout.addView(tv);


        }
    }

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
                    head.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) head.getX();
                    int objecty = (int) head.getY()+200;

                    int resultx=0,resulty=0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx*resultx;

                    resulty = resulty*resulty;


                    if((resultx) < 2500 && (resulty) < 2500)
                    {
                        head.setText("Head");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Right Answer", false);
                    }
                    else
                    {
                        int Xposition = 80;
                        int Yposition = 1770;
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Head", false);
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
                    ankle.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) ankle.getX();
                    int objecty = (int) ankle.getY()+200;

                    int resultx=0,resulty=0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx*resultx;

                    resulty = resulty*resulty;



                    if((resultx) < 2500 && (resulty) < 2500)
                    {
                        ankle.setText("Ankle");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Right Answer", false);
                    }
                    else
                    {
                        int Xposition = 80 + 450;
                        int Yposition = 1770;
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Ankle", false);
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
                    hip.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) hip.getX();
                    int objecty = (int) hip.getY()+200;

                    int resultx=0,resulty=0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx*resultx;

                    resulty = resulty*resulty;


                    if((resultx) < 2500 && (resulty) < 2500)
                    {
                        hip.setText("Hip");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Right Answer", false);
                    }
                    else
                    {
                        int Xposition = 80 + 700;
                        int Yposition = 1770;
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Hip", false);
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
                    beck.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) beck.getX();
                    int objecty = (int) beck.getY()+200;

                    int resultx=0,resulty=0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx*resultx;

                    resulty = resulty*resulty;


                    if((resultx) < 2500 && (resulty) < 2500)
                    {
                        beck.setText("SHOULDER");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Right Answer", false);
                    }
                    else
                    {
                        int Xposition = 80 + 200;
                        int Yposition = 1770;
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartBackA.this,  "Shoulder", false);
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

}
