package com.ksbm.ontu.alerts_module;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.adapter.Noticeboard_Adapter;
import com.ksbm.ontu.alerts_module.adapter.Updateboard_Adapter;
import com.ksbm.ontu.alerts_module.adapter.WebinarList_Adapter;
import com.ksbm.ontu.alerts_module.model.NoticeBoardModel;
import com.ksbm.ontu.alerts_module.model.WebinarModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.CustomAlertDialogBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Alerts_Dialog extends AppCompatActivity {
    SessionManager sessionManager;
    CustomAlertDialogBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  setContentView(R.layout.activity_personality_quiz_dialog);
        binding = DataBindingUtil.setContentView(this, R.layout.custom_alert_dialog);

        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.ivAlert);
//        Glide.with(this).load(R.drawable.alert_error).into(binding.alertIcon2);


        sessionManager = new SessionManager(this);

        Glide.with(this).load(R.drawable.join_webinar_alert).into(binding.ivAletWebinar);
//        iv_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });



        getNewUpdateList();
//        Animation anim = new AlphaAnimation(0.1f, 4.0f);
//        anim.setDuration(300); //You can manage the blinking time with this parameter
//        anim.setStartOffset(40);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        binding.tvUpdates.setAnimation(anim);

        getNoticeboardList();
//        Animation anim1 = new AlphaAnimation(0.1f, 4.0f);
//        anim1.setDuration(300); //You can manage the blinking time with this parameter
//        anim1.setStartOffset(40);
//        anim1.setRepeatMode(Animation.REVERSE);
//        anim1.setRepeatCount(Animation.INFINITE);
//        binding.rlNotice.setAnimation(anim1);

        getWebinarList();
//        Animation anim2 = new AlphaAnimation(0.1f, 4.0f);
//        anim2.setDuration(300); //You can manage the blinking time with this parameter
//        anim2.setStartOffset(40);
//        anim2.setRepeatMode(Animation.REVERSE);
//        anim2.setRepeatCount(Animation.INFINITE);
//        binding.llWebinar.setAnimation(anim2);

        binding.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

        binding.tvUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Alerts_Dialog.this, UpdateAlert_Activity.class);
                startActivity(intent);
            }
        });

        binding.tvNoticeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Alerts_Dialog.this, NoticeBoard_Activity.class);
                startActivity(intent);

            }
        });

    binding.llWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Alerts_Dialog.this, WebinarListActivity.class);
                startActivity(intent);

            }
        });


    binding.tvUpdateCount.setVisibility(View.GONE);
    binding.tvNoticeboardCount.setVisibility(View.GONE);
    binding.tvWebinarCount.setVisibility(View.GONE);


    binding.tvCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar calendarEvent = Calendar.getInstance();
//                Intent i = new Intent(Intent.ACTION_EDIT);
//                i.setType("vnd.android.cursor.item/event");
//                i.putExtra("beginTime", calendarEvent.getTimeInMillis());
//                i.putExtra("allDay", true);
//                i.putExtra("rule", "FREQ=YEARLY");
//                i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
//                i.putExtra("title", "Calendar Event");
//                startActivity(i);
                Intent intent= new Intent(Alerts_Dialog.this, CalendarActivity.class);
                startActivity(intent);

            }
        });

    }



    @SuppressLint("CheckResult")
    private void getNewUpdateList() {
        final ProgressDialog progressDialog = new ProgressDialog(Alerts_Dialog.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetNewUpdateList(sessionManager.getLanguageid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NoticeBoardModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(NoticeBoardModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                Animation anim = new AlphaAnimation(0.1f, 4.0f);
                                anim.setDuration(300); //You can manage the blinking time with this parameter
                                anim.setStartOffset(40);
                                anim.setRepeatMode(Animation.REVERSE);
                                anim.setRepeatCount(Animation.INFINITE);
                                binding.tvUpdates.setAnimation(anim);

//                                Updateboard_Adapter friendsAdapter = new Updateboard_Adapter(response.getResponse(), context);
//                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
//                                friendsAdapter.notifyDataSetChanged();

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
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

    @SuppressLint("CheckResult")
    private void getNoticeboardList() {
        final ProgressDialog progressDialog = new ProgressDialog(Alerts_Dialog.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetNoticeboardListList(sessionManager.getLanguageid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NoticeBoardModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(NoticeBoardModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                Animation anim1 = new AlphaAnimation(0.1f, 4.0f);
                                anim1.setDuration(300); //You can manage the blinking time with this parameter
                                anim1.setStartOffset(40);
                                anim1.setRepeatMode(Animation.REVERSE);
                                anim1.setRepeatCount(Animation.INFINITE);
                                binding.rlNotice.setAnimation(anim1);
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
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

    @SuppressLint("CheckResult")
    private void getWebinarList() {
        final ProgressDialog progressDialog = new ProgressDialog(Alerts_Dialog.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetWebinarList(sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WebinarModel>() {
                    @Override
                    public void onNext(WebinarModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                Animation anim2 = new AlphaAnimation(0.1f, 4.0f);
                                anim2.setDuration(300); //You can manage the blinking time with this parameter
                                anim2.setStartOffset(40);
                                anim2.setRepeatMode(Animation.REVERSE);
                                anim2.setRepeatCount(Animation.INFINITE);
                                binding.llWebinar.setAnimation(anim2);


                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
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





}
