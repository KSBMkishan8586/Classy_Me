package com.ksbm.ontu.free_coin.login_signup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.OtpEditText;
import com.ksbm.ontu.free_coin.login_signup.add_parent.AddParentModel;
import com.ksbm.ontu.free_coin.login_signup.add_parent.ProfileUpdateActivity;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class OtpActivity extends AppCompatActivity {
    private String OtpValue = "";
    String Mobile_No, Student_id ;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        sessionManager = new SessionManager(OtpActivity.this);

        if (getIntent() != null) {

            Mobile_No = getIntent().getStringExtra("Mobile_No");
            Student_id = getIntent().getStringExtra("Student_id");
        }

        //******************
        OtpEditText txtPinEntry = (OtpEditText) findViewById(R.id.et_otp);
        TextView btn_submit_otp = findViewById(R.id.tv_next);
        TextView btn_resend_otp = findViewById(R.id.btn_resend_otp);



        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                btn_resend_otp.setText("Resend OTP in: " + millisUntilFinished / 1000 + " Sec");
                // logic to set the EditText could go here
            }

            public void onFinish() {
               btn_resend_otp.setText("Resend OTP");
                btnOtp();
            }

        }.start();


        txtPinEntry.setTextColor(ContextCompat.getColor(OtpActivity.this, R.color.black));

        txtPinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                OtpValue = s.toString();

                Log.e("Otp_lenght", s.toString());

            }
        });


        btn_submit_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OtpValue != null && !OtpValue.isEmpty()) {
                    VerifyCode(OtpValue);
                } else {
                    Toast.makeText(OtpActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void btnOtp(){
        TextView otpBtn = (TextView)  findViewById(R.id.btn_resend_otp);
        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendCode(Mobile_No);
            }

        });
    }

    @SuppressLint("CheckResult")
    private void ResendCode(String mobile_no) {
        final ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.ResendOtp(mobile_no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AddParentModel>() {
                    @Override
                    public void onNext(AddParentModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                Toast.makeText(OtpActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(OtpActivity.this, "Sorry", response.getMessage());

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

    @SuppressLint("CheckResult")
    private void VerifyCode(String OtpValue) {
        final ProgressDialog progressDialog = new ProgressDialog(OtpActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.VerifyOtp(OtpValue, Mobile_No, sessionManager.getDeviceId(), sessionManager.getTokenId(), Student_id)
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

                                    Toast.makeText(OtpActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (response.getResponse().getRolename().equalsIgnoreCase("Student") &&
                                            response.getResponse().getIsParentAdd().equalsIgnoreCase("no")) {
                                        Intent intent = new Intent(OtpActivity.this, ProfileUpdateActivity.class);
                                        intent.putExtra("Student_id", response.getResponse().getUserid());
                                        intent.putExtra("LoginModelResponse", response.getResponse());
                                        startActivity(intent);

                                    }
                                }

                            } else {
                                 Toast.makeText(OtpActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(OtpActivity.this, "Sorry", response.getMessage());

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