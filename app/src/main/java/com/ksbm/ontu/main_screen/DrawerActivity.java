package com.ksbm.ontu.main_screen;

import static com.ksbm.ontu.utils.Constant.LEVEL_ID;
import static com.ksbm.ontu.utils.Constant.QUIZ_COIN;
import static com.ksbm.ontu.utils.Constant.QUIZ_ID;
import static com.ksbm.ontu.utils.Constant.STAGE_ID;
import static com.ksbm.ontu.utils.Constant.WHATSAPP_NO;
import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.ksbm.ontu.BuildConfig;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.main_screen.fragment.bookmarkandnotes;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.practice_offline.OfflineQuizScoreDB;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class DrawerActivity extends AppCompatActivity {

    RelativeLayout drawer, drawer1, man_ls;
    LinearLayout ll_sync_coin, ll_invite_frnd, ll_rate_app, bookmarkAndNotes, ll_ask_a_question, ll_teacher_registration, contact_us;

    SessionManager sessionManager;
    OfflineQuizScoreDB offlineQuizScoreDB;

    LinearLayout upgrade_course;
    MeraSharedPreferance meraSharedPreferance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        meraSharedPreferance = MeraSharedPreferance.getInstance(this);
        ll_sync_coin = findViewById(R.id.ll_sync_coin);
        ll_invite_frnd = findViewById(R.id.ll_invite_frnd);
        bookmarkAndNotes = findViewById(R.id.bookmark);
        man_ls = findViewById(R.id.man_ls);
        contact_us = findViewById(R.id.contact_us);
        ll_rate_app = findViewById(R.id.ll_rate_app);
        ll_ask_a_question = findViewById(R.id.ll_ask_a_question);
        ll_teacher_registration = findViewById(R.id.ll_teacher_registration);
        drawer = findViewById(R.id.drawer);
        drawer1 = findViewById(R.id.drawer1);
        upgrade_course = findViewById(R.id.upgrade_course);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppBarLayout top = findViewById(R.id.top);
        TranslateAnimation animation = new TranslateAnimation(-700.0f, 0.0f, 0.0f, 0.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
        animation.setDuration(500); // animation duration
        animation.setRepeatCount(0); // animation repeat count if u repeat only once set to 1 if u don't repeat set to 0
        animation.setFillAfter(true);
        drawer.startAnimation(animation);//your_view for mine is imageView
        drawer1.startAnimation(animation);//your_view for mine is imageView

        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(this), DrawerActivity.this);

        sessionManager = new SessionManager(DrawerActivity.this);
        offlineQuizScoreDB = new OfflineQuizScoreDB(DrawerActivity.this);

        ll_sync_coin.setVisibility(View.GONE);
        ll_sync_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offlineQuizScoreDB.getScore().size() > 0) {
                    SaveDataJsonArray();
                } else {
                    Toast.makeText((Context) DrawerActivity.this, "Already Synced", Toast.LENGTH_SHORT).show();
                }
            }
        });

        man_ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WHATSAPP_NO!=null && !WHATSAPP_NO.equalsIgnoreCase("")){
                    openWhatsApp();
                }
            }
        });

        ll_invite_frnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String shareMessage = "\nLet me recommend you this application\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        ll_rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp();
            }
        });


        ll_ask_a_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ll_teacher_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        bookmarkAndNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment newFragment = new bookmarkandnotes();

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame, newFragment);
                ft.commit();

//                Fragment fragment_home = new Foundations_List_Fragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("Title", "Book Mark");
//                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
//                ft_home.replace(R.id.frame, fragment_home);
//                ft_home.addToBackStack(null);
//                ft_home.commit();
//                fragment_home.setArguments(bundle);


            }
        });

        if (meraSharedPreferance.isOpenMarketGet() && !meraSharedPreferance.isPackageUpgraded()) {
            upgrade_course.setVisibility(View.VISIBLE);
        } else {
            upgrade_course.setVisibility(View.GONE);
        }

        upgrade_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class).putExtra("price", upgradePackage));
            }
        });



    }


    private void SaveDataJsonArray() {
        // retrive data from cart database
        ArrayList<HashMap<String, String>> items = offlineQuizScoreDB.getScore();
        if (items.size() > 0) {
            JSONArray passArray = new JSONArray();
            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> map = items.get(i);

                JSONObject jObjP = new JSONObject();

                try {
                    jObjP.put("stage_id", map.get(STAGE_ID));
                    jObjP.put("level_id", map.get(LEVEL_ID));
                    jObjP.put("coin_per_question", map.get(QUIZ_COIN));
                    jObjP.put("quiz_id", map.get(QUIZ_ID));
                    jObjP.put("classid", sessionManager.getUser().getClassid());

                    passArray.put(jObjP);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (Connectivity.isConnected(DrawerActivity.this)) {
                Log.e("ndata:", "" + passArray.toString());
                SyncScoreData(passArray, sessionManager.getUser().getUserid());
            } else {
                Toast.makeText((Context) DrawerActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
            }

        } else {
            // Toast.makeText(getActivity(), "Already Synced", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("CheckResult")
    private void SyncScoreData(JSONArray passArray, String userid) {
        final ProgressDialog progressDialog = new ProgressDialog((Context) DrawerActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.UploadScoreData(userid, passArray.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                offlineQuizScoreDB.clearCart();

                                Log.e("offlince_db_size", "" + offlineQuizScoreDB.getItemCount());

                                String title = getResources().getString(R.string.app_name);
                                String msg = response.getMessage();
                                SweetAlt.succesDialog(DrawerActivity.this, title, msg, false, false, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        // finish();
                                    }
                                });

                            } else {
                                SweetAlt.showErrorMessage(DrawerActivity.this, "Sorry", response.getMessage());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                        //  binding.swipeToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    /*
     * Start with rating the app
     * Determine if the Play Store is installed on the device
     *
     * */
    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private void openWhatsApp() {
        String toNumber;
        if (WHATSAPP_NO.length()==10){
            toNumber = "91"+ WHATSAPP_NO;
        }else {
            toNumber =  WHATSAPP_NO;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        String message = "Hi, \n I want to some support about the app";
        try {
            String url = "https://api.whatsapp.com/send?phone=" + toNumber + "&text=" + URLEncoder.encode(message, "UTF-8");
            /// i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            //if (i.resolveActivity(packageManager) != null) {
            startActivity(i);
            // }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "it may be you dont have whats app", Toast.LENGTH_LONG).show();
        }

    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, BuildConfig.APPLICATION_ID)));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }


    @Override
    public void onBackPressed() {
        AppBarLayout top = findViewById(R.id.top);
        TranslateAnimation animation = new TranslateAnimation(0.0f, -7000.0f, 0.0f, 0.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
        animation.setDuration(500); // animation duration
        animation.setRepeatCount(0); // animation repeat count if u repeat only once set to 1 if u don't repeat set to 0
        animation.setFillAfter(true);
        drawer.startAnimation(animation);//your_view for mine is imageView
        drawer1.startAnimation(animation);//your_view for mine is imageView
//        finish();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 100);

    }
}
