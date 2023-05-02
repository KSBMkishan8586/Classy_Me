package com.ksbm.ontu.open_market;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModelResponse;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.MeraSharedPreferance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class FillDataActivity extends AppCompatActivity {

    TextView submit;
    SessionManager sessionManager;
    ImageView back_bt;
    LinearLayout main_layout;
    EditText state_edit, age_edit, mobile_edit, email_edit, otp_edit;
    LinearLayout otp_layout;
    TextView send_otp_mobile, send_otp_email;
    CardView state_card, age_card;
    String state_arr[];
    String age_arr[];
    String ls = "";
    int pos = -1;
    int age_pos = -1;
    String otp = "";
    MeraSharedPreferance meraSharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(FillDataActivity.this);
        setContentView(R.layout.activity_fill_data);
        meraSharedPreferance = MeraSharedPreferance.getInstance(this);
        pos = -1;
        age_pos = -1;
        otp = "";
        ls = "";
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        if (getIntent().getExtras().getString("AGE").equalsIgnoreCase("Above 13")) {
            age_arr = new String[]{"13 - 18 Years", "18 - 21 Years", "21 - 25 Years"};
        } else {
            age_arr = new String[]{"3 - 5 Years", "5 - 8 Years", "8 - 13 Years"};
        }
        callStateList();
        state_edit = (EditText) findViewById(R.id.state_edit);
        age_edit = (EditText) findViewById(R.id.age_edit);
        otp_layout = (LinearLayout) findViewById(R.id.otp_layout);
        send_otp_mobile = (TextView) findViewById(R.id.send_otp_mobile);
        send_otp_email = (TextView) findViewById(R.id.send_otp_email);
        state_card = (CardView) findViewById(R.id.state_card);
        age_card = (CardView) findViewById(R.id.age_card);
        mobile_edit = (EditText) findViewById(R.id.mobile_edit);
        email_edit = (EditText) findViewById(R.id.email_edit);
        otp_edit = (EditText) findViewById(R.id.otp_edit);
        submit = (TextView) findViewById(R.id.submit);
        otp_layout.setVisibility(View.GONE);
        email_edit.setText(sessionManager.getUser().getEmail());
        submit.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sessionManager.createSession(response.getResponse());
                Intent intent = new Intent(FillDataActivity.this, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        back_bt = (ImageView) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoProficiency();
            }
        });
        state_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state_arr != null && ls.equalsIgnoreCase("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FillDataActivity.this);
                    builder.setTitle("Select State")
                            //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                            .setSingleChoiceItems(state_arr, pos, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pos = which;
                                    state_edit.setText(state_arr[which]);
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });

        age_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (age_arr != null && ls.equalsIgnoreCase("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FillDataActivity.this);
                    builder.setTitle("Select Age")
                            //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                            .setSingleChoiceItems(age_arr, age_pos, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    age_pos = which;
                                    age_edit.setText(age_arr[which]);
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });

        send_otp_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData("2");
            }
        });
        send_otp_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkData("1");
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(otp + " -- " + otp_edit.getText().toString().trim());
                if (otp_edit.getText().toString().trim().equalsIgnoreCase(otp)) {
                    fillAllData();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void gotoProficiency() {
        Intent intent = new Intent(getApplicationContext(), ProfeciencyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("AGE", getIntent().getExtras().getString("AGE"));
        bundle.putString("GENDER", getIntent().getExtras().getString("GENDER"));
        bundle.putString("PRF", getIntent().getExtras().getString("PRF"));
        bundle.putString("LEVEL", getIntent().getExtras().getString("LEVEL"));
        bundle.putString("TYPE", getIntent().getExtras().getString("TYPE"));
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void checkData(String type) {
        if (state_edit.getText().toString().trim().equalsIgnoreCase("Select State")) {
            Toast.makeText(getApplicationContext(), "Select State", Toast.LENGTH_SHORT).show();
        } else if (age_edit.getText().toString().trim().equalsIgnoreCase("Select Age")) {
            Toast.makeText(getApplicationContext(), "Select Age", Toast.LENGTH_SHORT).show();
        } else if (mobile_edit.getText().toString().trim().length() != 10) {
            Toast.makeText(getApplicationContext(), "Enter 10 Digit Mobile", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email_edit.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
        } else {
            callOTP(type);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onBackPressed() {
        gotoProficiency();
    }

    private void callStateList() {
        final ProgressDialog progressDialog = new ProgressDialog(FillDataActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        main_layout.setVisibility(View.GONE);
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.GetStates("123456", sessionManager.getUser().getUserid(), sessionManager.getLanguageid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        progressDialog.dismiss();
                        main_layout.setVisibility(View.VISIBLE);
                        System.out.println("Final response " + response);
                        try {
                            if (response.get("status").toString().equalsIgnoreCase("1")) {
                                JsonArray data = response.getAsJsonArray("data");
                                state_arr = new String[data.size()];
                                for (int i = 0; i < data.size(); i++) {
                                    JsonObject object = (JsonObject) data.get(i);
                                    state_arr[i] = object.get("state").getAsString();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        main_layout.setVisibility(View.VISIBLE);
                        e.printStackTrace();
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

    private void callOTP(String type) {
        final ProgressDialog progressDialog = new ProgressDialog(FillDataActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.sendOTP("123456", mobile_edit.getText().toString().trim(), email_edit.getText().toString().trim(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        progressDialog.dismiss();
                        main_layout.setVisibility(View.VISIBLE);
                        System.out.println("Final response " + response);
                        try {
                            if (response.get("status").toString().equalsIgnoreCase("1")) {
                                otp_layout.setVisibility(View.VISIBLE);
                                submit.setVisibility(View.VISIBLE);
                                otp = response.get("response").getAsString();
                                Toast.makeText(getApplicationContext(), response.get("message").toString() + " - " + response.get("response").toString(), Toast.LENGTH_SHORT).show();
                                mobile_edit.setFocusable(false);
                                mobile_edit.setEnabled(false);
                                email_edit.setFocusable(false);
                                email_edit.setEnabled(false);
                                ls = "1";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        main_layout.setVisibility(View.VISIBLE);
                        e.printStackTrace();
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


    private void fillAllData() {
        final ProgressDialog progressDialog = new ProgressDialog(FillDataActivity.this, R.style.MyGravity);
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        meraSharedPreferance.ageSave(getIntent().getExtras().getString("AGE"));
        meraSharedPreferance.levelSave(getIntent().getExtras().getString("LEVEL"));
        meraSharedPreferance.professionSave(getIntent().getExtras().getString("PRF"));
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.fillAllData("123456"
                        , state_edit.getText().toString().trim()
                        , getIntent().getExtras().getString("AGE")
                        , mobile_edit.getText().toString().trim()
                        , email_edit.getText().toString().trim()
                        , getIntent().getExtras().getString("GENDER")
                        , getIntent().getExtras().getString("PRF")
                        , getIntent().getExtras().getString("LEVEL")
                        , age_edit.getText().toString().trim(), sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(JsonObject response) {
                        System.out.println("Final response " + response);
                        progressDialog.dismiss();
                        try {
                            if (response.get("status").toString().equalsIgnoreCase("1")) {
                                meraSharedPreferance.isOpenMarketSave(true);
                                JsonObject main_data = response.getAsJsonObject("response");
                                LoginModelResponse loginModelResponse = sessionManager.getUser();
                                loginModelResponse.setUserid(main_data.get("user_id").getAsString());
                                loginModelResponse.setGender(main_data.get("gender").getAsString());
                                loginModelResponse.setProfessionName(main_data.get("profession").getAsString());
                                loginModelResponse.setState_name(main_data.get("state").getAsString());
                                sessionManager.createSession(loginModelResponse);
                                sessionManager.setAge(main_data.get("age").getAsString());
                                sessionManager.setAgeCriteria(main_data.get("age_criteria").getAsString());
                                sessionManager.setEnglishLevel(main_data.get("english_proficiency").getAsString());
                                Intent intent = new Intent(FillDataActivity.this, MainActivity.class);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        e.printStackTrace();

                    }


                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }
}