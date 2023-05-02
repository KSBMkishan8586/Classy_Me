package com.ksbm.ontu.free_coin.login_signup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ksbm.ontu.R;
import com.ksbm.ontu.free_coin.login_signup.add_parent.ProfileUpdateActivity;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityLoginBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(LoginActivity.this), LoginActivity.this);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_login);

        sessionManager = new SessionManager(LoginActivity.this);

        binding.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Context) LoginActivity.this, ForgotActivity.class);
                startActivity(intent);

            }
        });

//        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//                startActivity(intent);
//            }
//        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Et_email = binding.etEmail.getText().toString();
                String Et_pw = binding.etPw.getText().toString();

                if (Et_email.isEmpty() && Et_pw.isEmpty()) {
                    // Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    SweetAlt.showErrorMessage(LoginActivity.this, "Sorry", "Please enter all fields!");
                } else if (Et_email.isEmpty()) {
                    //  Toast.makeText(LoginActivity.this, "Please enter passsword", Toast.LENGTH_SHORT).show();
                    SweetAlt.showErrorMessage(LoginActivity.this, "Error", "Please enter user id!");
                }
//                else if (!Patterns.EMAIL_ADDRESS.matcher(Et_email).matches()){
//                    SweetAlt.showErrorMessage(LoginActivity.this, "Error", "Please enter valid email!");
//                }
                else if (Et_pw.isEmpty()) {
                    //  Toast.makeText(LoginActivity.this, "Please enter passsword", Toast.LENGTH_SHORT).show();
                    SweetAlt.showErrorMessage(LoginActivity.this, "Error", "Please enter passsword!");
                } else {
                    if (Et_pw.length() > 5) {
                        if (Connectivity.isConnected(LoginActivity.this)) {
                            CallLogin(Et_email, Et_pw);
                        } else {
                            //  Toast.makeText(LoginActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                            SweetAlt.showErrorMessage(LoginActivity.this, "Sorry", "You have no internet!");
                        }

                    } else {
                        // Toast.makeText(LoginActivity.this, "Enter Password minimum 6 characters long", Toast.LENGTH_SHORT).show();
                        SweetAlt.showErrorMessage(LoginActivity.this, "Error", "Enter Password minimum 6 characters long");
                    }
                }
            }
        });

    }

    @SuppressLint("CheckResult")
    private void CallLogin(String et_email, String et_pw) {
        final ProgressDialog progressDialog = new ProgressDialog((Context) LoginActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.LoginUser(et_email, et_pw, sessionManager.getDeviceId(), sessionManager.getTokenId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginModel>() {
                    @Override
                    public void onNext(LoginModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                if (response.getResponse().getRolename().equalsIgnoreCase("Student") &&
                                        response.getResponse().getIsParentAdd().equalsIgnoreCase("yes")) {

                                    sessionManager.createSession(response.getResponse());

                                    Toast.makeText((Context) LoginActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent((Context) LoginActivity.this, MainActivity.class);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else if (response.getResponse().getRolename().equalsIgnoreCase("Student") &&
                                            response.getResponse().getIsParentAdd().equalsIgnoreCase("no")) {
                                        Intent intent = new Intent((Context) LoginActivity.this, ProfileUpdateActivity.class);
                                        intent.putExtra("Student_id", response.getResponse().getUserid());
                                        intent.putExtra("LoginModelResponse", response.getResponse());
                                        startActivity(intent);


                                }else {
                                    sessionManager.createSession(response.getResponse());

                                    Toast.makeText((Context) LoginActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent((Context) LoginActivity.this, MainActivity.class);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }


                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(LoginActivity.this, "Sorry", response.getMessage());

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