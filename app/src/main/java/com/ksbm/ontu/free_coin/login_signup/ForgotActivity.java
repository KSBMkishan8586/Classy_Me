package com.ksbm.ontu.free_coin.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityForgotBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class ForgotActivity extends AppCompatActivity {
    ActivityForgotBinding binding;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_forgot);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(ForgotActivity.this), ForgotActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot);

        sessionManager = new SessionManager(ForgotActivity.this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Et_email = binding.etEmail.getText().toString();

                if (Et_email.isEmpty()) {
                    //Toast.makeText(SignupActivity.this, "Please enter email id", Toast.LENGTH_SHORT).show();
                    SweetAlt.showErrorMessage(ForgotActivity.this, "Error", "Please enter email id!");
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(Et_email).matches()) {
                        if (Connectivity.isConnected(ForgotActivity.this)) {
                            CallForgot(Et_email);
                        } else {
                            SweetAlt.showErrorMessage(ForgotActivity.this, "Sorry", "You have no internet!");
                        }

                    } else {
                        //Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                        SweetAlt.showErrorMessage(ForgotActivity.this, "Error", "Please enter valid email!");
                    }

                }

            }
        });

    }

    @SuppressLint("CheckResult")
    private void CallForgot(String et_email) {
        final ProgressDialog progressDialog = new ProgressDialog(ForgotActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.ForgotUser(et_email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            if (response.getStatus() == 1) {
                                String title = getResources().getString(R.string.app_name);
                                String msg = response.getMessage();
                                SweetAlt.succesDialog(ForgotActivity.this, title, msg, false, false, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
                                });
                            } else {
                                SweetAlt.showErrorMessage(ForgotActivity.this, "Sorry", response.getMessage());
                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

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