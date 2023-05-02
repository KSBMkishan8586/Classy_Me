package com.ksbm.ontu.foundation.activity;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.adapter.AlphabetAdapter;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BodyPartBackB extends AppCompatActivity {

    String texts[] = {"Elbow", "Heel", "Calf"};
    String texts3[] = {"Waist", "Ankel", "Hip"};
    RelativeLayout layout;
    TextView Waist, Heel, Calf, Elbow, Hip, Ankel;
    ImageView back;
    int correctAnswer = 12;
    DisplayMetrics displayMetrics;
    int height;
    int width;
    SessionManager sessionManager;

    LinearLayout ll_get_coin;


    boolean bodypart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodypartbackb);
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        layout = (RelativeLayout) findViewById(R.id.linear);
        sessionManager = new SessionManager(this);
        back = (ImageView) findViewById(R.id.back);
        ll_get_coin = (LinearLayout) findViewById(R.id.ll_get_coin);
        Waist = (TextView) findViewById(R.id.tv_Hi);
        Heel = (TextView) findViewById(R.id.tv_Hd);
        Calf = (TextView) findViewById(R.id.tv_Hf);
        Elbow = (TextView) findViewById(R.id.tv_Ha);
        Hip = (TextView) findViewById(R.id.tv_Ht);
        Ankel = (TextView) findViewById(R.id.tv_Hq);

        Waist.setAllCaps(true);
        Heel.setAllCaps(true);
        Calf.setAllCaps(true);
        Elbow.setAllCaps(true);
        Hip.setAllCaps(true);
        Ankel.setAllCaps(true);
//      correctAnswer = Integer.parseInt(getIntent().getStringExtra("totalans"));

        generateTv(texts);
        generateTv3(texts3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bodypart) {
                    Intent intent = new Intent(BodyPartBackB.this, BodyPartDrop.class);
                    intent.putExtra("totalans", correctAnswer + "");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(BodyPartBackB.this, BodyPartDropB.class);
                    intent.putExtra("totalans", correctAnswer + "");
                    startActivity(intent);
                    finish();
                }

            }
        });


        TextView tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (correctAnswer == 18) {
                    if (Connectivity.isConnected(BodyPartBackB.this)) {
                        if (!bodypart) {
                            GetAllAlphabets();
                        } else {
                            // SweetAlt.showErrorMessage(BodyPartBackB.this, "Alert!", "You have already done.");
                        }

                    } else {
                        SweetAlt.showErrorMessage(BodyPartBackB.this, "Sorry", "You have no internet!");
                    }

                } else {
                    Toast.makeText(BodyPartBackB.this, "Please complete task first.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        TextView tv_previous = findViewById(R.id.tv_previous);
        tv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BodyPartBackB.this, BodyPartBackA.class);
                intent.putExtra("totalans", correctAnswer + "");
                startActivity(intent);
                finish();
            }
        });

    }


    private void GetAllAlphabets() {

        final ProgressDialog progressDialog = new ProgressDialog(BodyPartBackB.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        // url to post our data
        String url = "http://classyme.org/index.php/apis/auth/update_coins";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(BodyPartBackB.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                // on below line we are displaying a success toast message.

                // CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), "100", "foundation_learn", "");
                bodypart = true;
                ll_get_coin.setVisibility(View.VISIBLE);
                Utils.playMusic(R.raw.coin_sound, BodyPartBackB.this);
                finish();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                progressDialog.dismiss();
                Toast.makeText(BodyPartBackB.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("API-KEY", "123456");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();
                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("userid", sessionManager.getUser().getUserid());
                params.put("foundation_id", Constant.foundation_id);
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
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

            tv = new TextView(this);
            tv.setText("" + texts[i]);
            tv.setX(Xposition);
            tv.setY(Yposition);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(16);
            tv.setAllCaps(true);

            //or to support all versions use
            Typeface typeface = ResourcesCompat.getFont(BodyPartBackB.this, R.font.futuramdbd);
            tv.setTypeface(typeface);

            if (i == 0) {
                tv.setOnTouchListener(mOnTouchListenerTv2);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackB.this, R.color.chat_box_left));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }
            } else if (i == 1) {
                tv.setOnTouchListener(mOnTouchListenerTv9);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackB.this, R.color.pink));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }
            } else if (i == 2) {
                tv.setOnTouchListener(mOnTouchListenerTv4);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackB.this, R.color.chat_box_left));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 70;
                }
            }

            layout.addView(tv);


        }
    }

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

            tv = new TextView(this);
            tv.setText("" + texts[i]);
            tv.setX(Xposition);
            tv.setY(Yposition);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(16);
            tv.setAllCaps(true);

            //or to support all versions use
            Typeface typeface = ResourcesCompat.getFont(BodyPartBackB.this, R.font.futuramdbd);
            tv.setTypeface(typeface);

            if (i == 0) {
                tv.setOnTouchListener(mOnTouchListenerTv8);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackB.this, R.color.powder_blue));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }
            } else if (i == 1) {
                tv.setOnTouchListener(mOnTouchListenerTv13);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackB.this, R.color.pink));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 185;
                }
            } else if (i == 2) {
                tv.setOnTouchListener(mOnTouchListenerTv14);
                tv.setBackgroundResource(R.drawable.two_sided_border);
                tv.setBackgroundTintList(ContextCompat.getColorStateList(BodyPartBackB.this, R.color.chat_box_left));
                if (width > 1000) {
                    Xposition += 350;
                } else {
                    Xposition += 225;
                }
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
                    Elbow.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) Elbow.getX();
                    int objecty = (int) Elbow.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        Elbow.setText("Elbow");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Right Answer", false);
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
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Elbow", false);
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
                    Calf.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) Calf.getX();
                    int objecty = (int) Calf.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        Calf.setText("Calf");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Right Answer", false);
                    } else {

                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70 + 700;
                        } else {
                            Xposition = 50 + 370;
                        }
                        if (height > 2000) {
                            Yposition = 1670;
                        } else {
                            Yposition = 1040;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Calf", false);
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
                    Waist.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) Waist.getX();
                    int objecty = (int) Waist.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        Waist.setText("Waist");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Right Answer", false);
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
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Waist", false);
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
                    Heel.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) Heel.getX();
                    int objecty = (int) Heel.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        Heel.setText("Heel");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Right Answer", false);
                    } else {
                        int Xposition;
                        int Yposition;
                        if (width > 1000) {
                            Xposition = 70 + 350;
                        } else {
                            Xposition = 50 + 185;
                        }
                        if (height > 2000) {
                            Yposition = 1670;
                        } else {
                            Yposition = 1040;
                        }
                        v.setX(Xposition);
                        v.setY(Yposition);
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Heel", false);
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

    @Override
    public void onBackPressed() {
        back.performClick();
    }

    public final View.OnTouchListener mOnTouchListenerTv14 = new View.OnTouchListener() {
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
                    Hip.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) Hip.getX();
                    int objecty = (int) Hip.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        Hip.setText("Hip");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Right Answer", false);
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
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Hip", false);
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
    public final View.OnTouchListener mOnTouchListenerTv13 = new View.OnTouchListener() {
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
                    Ankel.getLocationOnScreen(location);
                    int x = (int) v.getX();
                    int y = (int) v.getY();

                    int objectx = (int) Ankel.getX();
                    int objecty = (int) Ankel.getY();

                    int resultx = 0, resulty = 0;
                    resultx = objectx - x;
                    resulty = objecty - y;

                    int prerex = resultx;
                    int prerey = resulty;
                    resultx = resultx * resultx;

                    resulty = resulty * resulty;

                    if ((resultx) < 2500 && (resulty) < 2500) {
                        Ankel.setText("Ankel");
                        v.setVisibility(View.GONE);
                        correctAnswer++;
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Right Answer", false);
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
                        Speach_Record_Activity.speakOut(BodyPartBackB.this, "Ankel", false);
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
