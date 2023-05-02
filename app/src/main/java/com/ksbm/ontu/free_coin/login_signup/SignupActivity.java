package com.ksbm.ontu.free_coin.login_signup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivitySignupBinding;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.SignupModel;
import com.ksbm.ontu.main_screen.model.state_model.StateModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    String StateId, CityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_signup);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(SignupActivity.this), SignupActivity.this);
        binding= DataBindingUtil.setContentView((Activity) this, R.layout.activity_signup);

        if (Connectivity.isConnected(SignupActivity.this)){
           // GetAllClass();
            GetAllState();
        }else {
            Toast.makeText((Context) this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Et_firstname=binding.etFirstName.getText().toString();
                String Et_mobile=binding.etPhone.getText().toString();
                String Et_email=binding.etEmail.getText().toString();
                String Et_pw=binding.etPw.getText().toString();
                CallSignup(Et_firstname,Et_mobile,Et_email,Et_pw);
//                if (Et_firstname.isEmpty()  && Et_mobile.isEmpty() && Et_email.isEmpty() && Et_pw.isEmpty()){
//                    // Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                    SweetAlt.showErrorMessage(SignupActivity.this, "Sorry", "Please enter all fields!");
//                }else if (Et_firstname.isEmpty()) {
//                    //Toast.makeText(SignupActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
//                    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter full name!");
//                }else if (Et_mobile.isEmpty()) {
//                    // Toast.makeText(SignupActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
//                    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter mobile number!");
//                }else if (Et_email.isEmpty()){
//                    //Toast.makeText(SignupActivity.this, "Please enter email id", Toast.LENGTH_SHORT).show();
//                    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter email id!");
//                }else if (Et_pw.isEmpty()){
//                    //Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
//                    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter password!");
//                } else {
//                    if (Et_mobile.length()==10){
//                        if (Patterns.EMAIL_ADDRESS.matcher(Et_email).matches()){
//                            if (Et_pw.length()>5){
//                                if (Connectivity.isConnected(SignupActivity.this)){
//                                    CallSignup(Et_firstname,Et_mobile,Et_email,Et_pw);
//                                }else {
//                                    SweetAlt.showErrorMessage(SignupActivity.this, "Sorry", "You have no internet!");
//                                }
//                            }else {
//                                //Toast.makeText(SignupActivity.this, "Enter Password minimum 6 characters long", Toast.LENGTH_SHORT).show();
//                                SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Enter Password minimum 6 characters long!");
//                            }
//
//                        }else {
//                            //Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
//                            SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter valid email!");
//                        }
//                    }else {
//                        // Toast.makeText(SignupActivity.this, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
//                        SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter valid mobile no.");
//                    }
//
//                }

//                Intent intent = new Intent(SignupActivity.this, PermissionActivity.class);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
            }
        });

    }

    @SuppressLint("CheckResult")
    private void CallSignup(String et_firstname, String et_mobile, String et_email, String et_pw) {
        final ProgressDialog progressDialog = new ProgressDialog((Context) SignupActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.SignupUser(et_firstname,et_mobile, et_email,et_pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SignupModel>() {
                    @Override
                    public void onNext(SignupModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus()==1) {
                                // Toast.makeText(SignupActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                                String title = getResources().getString(R.string.app_name);
                                String msg = response.getMessage();
                                SweetAlt.succesDialog(SignupActivity.this, title, msg, false, false, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
                                });

                            } else {
                                //  Toast.makeText(SignupActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(SignupActivity.this, "Sorry", response.getMessage());
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
    private void GetAllState() {
        final ProgressDialog progressDialog = new ProgressDialog((Context) SignupActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAllStateName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<StateModel>() {
                    @Override
                    public void onNext(StateModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                List<String> classModelResponseList= new ArrayList<>();
                                for (int i=0; i<response.getResponse().size(); i++){
                                    classModelResponseList.add(response.getResponse().get(i).getName());

                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>((Context) SignupActivity.this, R.layout.spinner_item, classModelResponseList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spinState.setAdapter(arrayAdapter);

                                binding.spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String tutorialsName = parent.getItemAtPosition(position).toString();
                                        StateId= response.getResponse().get(position).getStateId();

                                        if (StateId !=null ){
                                            getCity(StateId);
                                        }

                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView <?> parent) {
                                    }
                                });


                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(SignupActivity.this, "Sorry", response.getMessage());

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

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getCity(String stateId) {
        final ProgressDialog progressDialog = new ProgressDialog((Context) SignupActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAllCityName(stateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<StateModel>() {
                    @Override
                    public void onNext(StateModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                List<String> classModelResponseList= new ArrayList<>();
                                for (int i=0; i<response.getResponse().size(); i++){
                                    classModelResponseList.add(response.getResponse().get(i).getName());

                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>((Context) SignupActivity.this, R.layout.spinner_item, classModelResponseList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spinCity.setAdapter(arrayAdapter);

                                binding.spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String tutorialsName = parent.getItemAtPosition(position).toString();
                                        CityId= response.getResponse().get(position).getCity_id();

                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView <?> parent) {
                                    }
                                });

                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(SignupActivity.this, "Sorry", response.getMessage());

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
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        finish();
    }
}