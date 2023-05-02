package com.ksbm.ontu.utils;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.adapter.BookMarkAdapter;
import com.ksbm.ontu.foundation.model.BookMarkModel;
import com.ksbm.ontu.foundation.model.RandomBgFoundation;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.LocalDatabase.myDbAdapter;
import com.skydoves.rainbow.Rainbow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static AudioManager.OnAudioFocusChangeListener afChangeListener;

    public static void setLanguageForApp(String lang, Context context) {

        LocaleHelper.setLocale(context, lang);

        Resources activityRes = context.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(lang);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = context.getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());


    }

    public static String getVideoIdFromYoutubeUrl(String url) {
        String videoId = null;
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }

    public static boolean isValidYoutubeUrl(String url) {
        if (url == null) {
            return false;
        }
        if (URLUtil.isValidUrl(url)) {
            // Check host of url if youtube exists
            Uri uri = Uri.parse(url);
            if ("www.youtube.com".equals(uri.getHost())) {
                return true;
            }
            // Other way You can check into url also like
            if (url.startsWith("https://www.youtube.com/")) {
                return true;
            }
        }
        // In other any case
        return false;
    }

    public static String findWordForRightHanded(String str, int offset) {
        // when you touch ' ', this method returns left word.
        if (str.length() == offset) {
            offset--; // without this code, you will get exception when touching end of the text
        }

        if (str.charAt(offset) == ' ') {
            offset--;
        }
        int startIndex = offset;
        int endIndex = offset;

        try {
            while (str.charAt(startIndex) != ' ' && str.charAt(startIndex) != '\n') {
                startIndex--;
            }
        } catch (StringIndexOutOfBoundsException e) {
            startIndex = 0;
        }

        try {
            while (str.charAt(endIndex) != ' ' && str.charAt(endIndex) != '\n') {
                endIndex++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            endIndex = str.length();
        }

        // without this code, you will get 'here!' instead of 'here'
        // if you use only english, just check whether this is alphabet,
        // but 'I' use korean, so i use below algorithm to get clean word.
        char last = str.charAt(endIndex - 1);
        if (last == ',' || last == '.' ||
                last == '!' || last == '?' ||
                last == ':' || last == ';') {
            endIndex--;
        }

        return str.substring(startIndex, endIndex);
    }

    public static void setRainbowColor(TextView textView) {
        new Rainbow(textView)
                .addContextColor(R.color.red)
                .addContextColor(R.color.purple)
                .addContextColor(R.color.green)
                .addContextColor(R.color.red_500)
                .addContextColor(R.color.blue)
                .addContextColor(R.color.indigo)
                .addContextColor(R.color.fire_brick)
                .shade();
    }

    public static void setRandomColor(TextView textView, Context context) {
        List<Integer> colors;
        colors = new ArrayList<Integer>();
        colors.add(context.getResources().getColor(R.color.red));
        colors.add(context.getResources().getColor(R.color.purple));
        colors.add(context.getResources().getColor(R.color.green));
        colors.add(context.getResources().getColor(R.color.red_500));
        colors.add(context.getResources().getColor(R.color.blue));
        colors.add(context.getResources().getColor(R.color.indigo));
        colors.add(context.getResources().getColor(R.color.fire_brick));

        // genrating random num from 0 to 7 because you can add more or less
        Random r = new Random();
        int i1 = r.nextInt(7);
        //genrating shape with colors
        //GradientDrawable draw = new GradientDrawable();
        //draw.setShape(GradientDrawable.OVAL);
        // draw.setColor(Color.parseColor(colors.get(i1)));
        // assigning to textview
        // textView.setBackground(draw); //textview
        textView.setTextColor(colors.get(i1)); //textview

    }

    public static void setRandomColorWord(CardView textView, Context context) {
        List<Integer> colors;
        colors = new ArrayList<Integer>();
        colors.add(context.getResources().getColor(R.color.word_color1));
        colors.add(context.getResources().getColor(R.color.word_color2));
        colors.add(context.getResources().getColor(R.color.word_color3));
        colors.add(context.getResources().getColor(R.color.word_color4));
        colors.add(context.getResources().getColor(R.color.word_color5));

        // genrating random num from 0 to 5 because you can add more or less
        Random r = new Random();
        int i1 = r.nextInt(5);
        textView.setCardBackgroundColor(colors.get(i1)); //textview

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static void setRandomBgImage(RelativeLayout rel, LinearLayout llBottom, Context context) {
        List<RandomBgFoundation> image = new ArrayList<RandomBgFoundation>();
        image.add(new RandomBgFoundation(R.drawable.card1, R.drawable.rect_bg_dark_pink));
        image.add(new RandomBgFoundation(R.drawable.card2, R.drawable.rectangle_bg_card2));
        image.add(new RandomBgFoundation(R.drawable.card3, R.drawable.rectangle_bg_card3));
        image.add(new RandomBgFoundation(R.drawable.card4, R.drawable.rectangle_bg_card4));
        image.add(new RandomBgFoundation(R.drawable.card5, R.drawable.rectangle_bg_card5));
        image.add(new RandomBgFoundation(R.drawable.card6, R.drawable.rectangle_bg_card6));
        image.add(new RandomBgFoundation(R.drawable.card7, R.drawable.rectangle_bg_card7));
        image.add(new RandomBgFoundation(R.drawable.card10, R.drawable.rectangle_bg_card10));

        // genrating random num from 0 to 5 because you can add more or less
        Random r = new Random();
        int i = r.nextInt(3);
        rel.setBackgroundResource(image.get(i).getCard());
        llBottom.setBackgroundResource(image.get(i).getDrawable_bg());
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("currencies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // MediaPlayer mPlayer = null;
    public static void playMusic(int musicFile, Context context) {
        try {
            MediaPlayer mPlayer = MediaPlayer.create(context, musicFile);

            AudioManager am = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // Start playback.
                mPlayer.setLooping(false);
                // final float volume = (float) (1 - (Math.log(MAX_VOLUME - 85) / Math.log(MAX_VOLUME)));
                // mPlayer.setVolume(volume, volume);
                mPlayer.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playRecordWithdelay(Context context, int delayTime) {
        Speach_Record_Activity.stop_recording();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);

            }
        }, delayTime * 1000L);
    }

    static myDbAdapter helper;
    static SessionManager sessionManager;

    public static void screenShot(View view, Context context) {
        sessionManager = new SessionManager(context);
        helper = new myDbAdapter(context);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        //screenShot.setImageBitmap(bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        long time = System.currentTimeMillis();

        screenshot(context, sessionManager.getUser().getUserid(), encoded, time + "");


//        long identity = helper.insertData(time+"",encoded);
//        if(identity<=0)
//        {
//            Toast.makeText(context,"Insertion UnSuccessful", Toast.LENGTH_LONG).show();
//        } else
//        {
//            Toast.makeText(context,"Insertion Successful", Toast.LENGTH_LONG).show();
//        }
    }


    public static void screenshot(Context context, String UserId, String image, String name) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, "http://classyme.org/index.php/apis/bookmarks/add_bookmark", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    progressDialog.dismiss();
                    JSONObject object = new JSONObject(response);
                    try {
                        String status = object.getString("status");
                        if (status.equals("1")) {
                            Toast.makeText(context, "Screenshot saved.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Screenshot not saved, try again later.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Screenshot not saved, try again later.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Screenshot not saved, try again later.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Screenshot not saved, try again later.", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", UserId);
                params.put("name", name);
                params.put("image", image);
                params.put("API-KEY", "123456");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("API-KEY", "123456");
                return params;
            }
        };
        queue.add(sr);

    }


    public void viewdata(View view, Context context) {
        helper = new myDbAdapter(context);
        String data = helper.getData();
    }

    public void update(View view, Context context, String Uname, String ImageString) {
        helper = new myDbAdapter(context);

        if (Uname.isEmpty() || ImageString.isEmpty()) {
            Toast.makeText(context, "Enter Data", Toast.LENGTH_LONG).show();
        } else {
            int a = helper.updateName(Uname, ImageString);
            if (a <= 0) {
                Toast.makeText(context, "Unsuccessful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void delete(View view, Context context, String Uname) {
        helper = new myDbAdapter(context);
        String uname = Uname.toString();
        if (uname.isEmpty()) {
            Toast.makeText(context, "Enter Data", Toast.LENGTH_LONG).show();
        } else {
            int a = helper.delete(uname);
            if (a <= 0) {
                Toast.makeText(context, "Unsuccessful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "DELETED", Toast.LENGTH_LONG).show();
            }
        }
    }


    public static void EarnCoin(LinearLayout ll_get_coin, Context context, int delayAfterRecordPlay) {
        ll_get_coin.setVisibility(View.VISIBLE);
        Utils.playMusic(R.raw.coin_sound, context);
        MediaPlayer mp = MediaPlayer.create(context, Uri.parse(Speach_Record_Activity.getRecordedFileName()));
        int duration = mp.getDuration();
        Log.e("record_duration", "" + duration);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_get_coin.setVisibility(View.INVISIBLE);
            }
        }, duration + (delayAfterRecordPlay * 1000L) + 500);
        //recording duration + recording play delay duration + half second more delay play coin sound
    }

    public static void showToast(FragmentActivity getActivity, String message, boolean isRight) {
        LayoutInflater inflater = getActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) getActivity.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        CardView cardView = (CardView) layout.findViewById(R.id.card_toast);
        if (isRight) {
            cardView.setCardBackgroundColor(getActivity.getResources().getColor(R.color.green));
        } else {
            cardView.setCardBackgroundColor(getActivity.getResources().getColor(R.color.red));
        }

        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        // toast.setGravity(Gravity.BOTTOM, 0, 0);
        // toast.setMargin(30,300);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showToastInActivity(AppCompatActivity getActivity, String message, boolean isRight) {
        LayoutInflater inflater = getActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) getActivity.findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        CardView cardView = (CardView) layout.findViewById(R.id.card_toast);
        if (isRight) {
            cardView.setCardBackgroundColor(getActivity.getResources().getColor(R.color.green));
        } else {
            cardView.setCardBackgroundColor(getActivity.getResources().getColor(R.color.red));
        }

        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        // toast.setGravity(Gravity.BOTTOM, 0, 0);
        // toast.setMargin(30,100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void ZoomInImage(View imageView) {
        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        imageView.startAnimation(animZoomIn);

    }

    public static void SetSeperateCharacterInBox(String name, Context context, LinearLayout ll_dynamic_text) {
        final TextView[] myTextViews = new TextView[name.length()]; // create an empty array;

        ArrayList<String> al = new ArrayList<String>();
        for (int i = 0; i < name.length(); i++) {
            al.add(Character.toString(name.charAt(i)));
        }

        for (int i = 0; i < name.length(); i++) {
            TextView textView1 = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            textView1.setLayoutParams(params);
            textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView1.setGravity(Gravity.CENTER);
            textView1.setTextColor(context.getResources().getColor(R.color.white));
            textView1.setText(" " + al.get(i).toUpperCase() + " ");

            textView1.setBackgroundResource(R.drawable.rectangle_bg_dark);

            // textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 32);
            ll_dynamic_text.addView(textView1);
            // save a reference to the textview for later
            myTextViews[i] = textView1;

            int finalI = i;
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(context, al.get(finalI), false);
                }
            });
        }
    }

    public static SpannableString UnderlineText(String textData) {
        SpannableString content = new SpannableString(textData);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        //textView.setText(content);

        return content;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 120);
        return noOfColumns;
    }

    public static void blinkTextView(TextView txt, Context mContext) {
        txt.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
        txt.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_out));

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.setVisibility(View.GONE);
            }
        }, 500);

        final Handler handler1 = new Handler(Looper.getMainLooper());
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.setVisibility(View.VISIBLE);
            }
        }, 500);

//        final Handler handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int timeToBlink = 500;
//                try {
//                    Thread.sleep(timeToBlink);
//                } catch (Exception e) {
//                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (txt.getVisibility() == View.VISIBLE){
//                            txt.setVisibility(View.INVISIBLE);
//                        } else{
//                            txt.setVisibility(View.VISIBLE);
//                        }
//                         blinkTextView(txt);
//                    }
//                });
//            }
//        }).start();

    }

    public static void blinkImageView(ImageView txt, Context mContext) {
        txt.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
        txt.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_out));

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.setVisibility(View.GONE);
            }
        }, 500);

        final Handler handler1 = new Handler(Looper.getMainLooper());
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt.setVisibility(View.VISIBLE);
            }
        }, 500);

//        final Handler handler = new Handler();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int timeToBlink = 500;
//                try {
//                    Thread.sleep(timeToBlink);
//                } catch (Exception e) {
//                }
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (txt.getVisibility() == View.VISIBLE){
//                            txt.setVisibility(View.INVISIBLE);
//                        } else{
//                            txt.setVisibility(View.VISIBLE);
//                        }
//                         blinkTextView(txt);
//                    }
//                });
//            }
//        }).start();

    }

    public static void RefreshTextView(TextView txt, Context mContext) {
        Animation animation = new AlphaAnimation(1, 1); //to change visibility from visible to invisible
        animation.setDuration(50); //1 second duration for each animation cycle
        txt.setAnimation(animation);
    }
}
