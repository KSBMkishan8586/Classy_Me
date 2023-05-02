package com.ksbm.ontu.alerts_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.model.NoticeBoardModel;
import com.ksbm.ontu.alerts_module.model.WebinarModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityWebinarDetailsBinding;
import com.ksbm.ontu.databinding.ActivityWebinarListBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Webinar_Details_Activity extends AppCompatActivity {
    ActivityWebinarDetailsBinding binding;
    SessionManager sessionManager;
    Context context;
    WebinarModel.WebinarModelResponse WebinarData;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_webinar_details);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(Webinar_Details_Activity.this), Webinar_Details_Activity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webinar_details);
        context = Webinar_Details_Activity.this;

        sessionManager = new SessionManager(context);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent() != null) {
            WebinarData = (WebinarModel.WebinarModelResponse) getIntent().getSerializableExtra("WebinarData");
            binding.setModel(WebinarData);

        }

        binding.tvWebinarDate.setText("Webinar Date- "+ WebinarData.getWebinarDate() + " "+ WebinarData.getWebinarTime());
        if (WebinarData.getIsJoin()){
            binding.tvJoin.setText("Joined");
            binding.tvJoin.setBackground(context.getResources().getDrawable(R.drawable.redius_green_bg));

            binding.llWebinarLink.setVisibility(View.VISIBLE);
        }

        binding.tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date strDate = null;
                try {
                    strDate = sdf.parse(WebinarData.getWebinarDate() + " "+ WebinarData.getWebinarTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.e("new_date", ""+ new Date());
                Log.e("strDate", ""+ strDate);

                if (!WebinarData.getIsJoin()){
                    if (new Date().before(strDate)) {
                        JoinWebinarVideo(sessionManager.getUser().getUserid(), WebinarData);
                    }else if (new Date().equals(strDate)){
                        JoinWebinarVideo(sessionManager.getUser().getUserid(), WebinarData);

                    }else if (new Date().after(strDate)){
                        Toast.makeText(context, "Joining Time Expire", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(context, "Already Join", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WebinarData.getWebinarLink() != null || !WebinarData.getWebinarLink().equalsIgnoreCase("")) {
                    String link=  WebinarData.getWebinarLink();

                    if (!link.startsWith("https://") && !link.startsWith("http://")){
                        link = "http://" + link;
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                }
            }
        });

        binding.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", WebinarData.getWebinarLink());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void JoinWebinarVideo(String userid, WebinarModel.WebinarModelResponse dataModel) {
        String title = context.getResources().getString(R.string.app_name);
        String msg = "Webinar charge Coin - "+ dataModel.getJoinAmount() + "\n" + "Reward Coin - "+ dataModel.getReward();
        SweetAlt.normalDialog(context, title, msg, true, true, "Cancel", "Join", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                JoinWebinar(sessionManager.getUser().getUserid(), dataModel.getWebinarId(), dataModel.getJoinAmount(), dataModel.getwJoinId());
            }
        });
    }

    @SuppressLint("CheckResult")
    private void JoinWebinar(String userid, String webinarId, String joinAmount, String w_join_id) {

        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.Join_webinar(userid, webinarId, joinAmount, w_join_id)
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
                                Toast.makeText(context, "Join Sussessful", Toast.LENGTH_SHORT).show();
                                WebinarData.setIsJoin(true);
                               // notifyDataSetChanged();
                                if (WebinarData.getIsJoin()){
                                    binding.tvJoin.setText("Joined");
                                    binding.tvJoin.setBackground(context.getResources().getDrawable(R.drawable.redius_green_bg));
                                    binding.llWebinarLink.setVisibility(View.VISIBLE);
                                }

                            } else {
                                SweetAlt.showErrorMessage(context, ""+response.getMessage(), response.getMessage());

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

}