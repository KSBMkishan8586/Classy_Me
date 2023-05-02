package com.ksbm.ontu.free_coin.login_signup.add_parent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityProfileUpdateBinding;
import com.ksbm.ontu.free_coin.login_signup.OtpActivity;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModelResponse;
import com.ksbm.ontu.main_screen.model.state_model.StateModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class ProfileUpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ActivityProfileUpdateBinding binding;
    private String Student_id;
    LoginModelResponse loginModelResponse;
    String ClassId, StateId, CityId;
    SimpleDateFormat simpleDateFormat;
    String Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(ProfileUpdateActivity.this), ProfileUpdateActivity.this);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_profile_update);
       // setContentView(R.layout.activity_profile_update);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        if (getIntent()!=null){
            Student_id= getIntent().getStringExtra("Student_id");
            loginModelResponse= (LoginModelResponse) getIntent().getSerializableExtra("LoginModelResponse");

            if(loginModelResponse!=null){
                StateId= loginModelResponse.getStateId();
                ClassId= loginModelResponse.getClassid();

                binding.etSchCode.setText(loginModelResponse.getSchool_code());
                binding.etSchName.setText(loginModelResponse.getSchoolName());
                binding.etEmail.setText(loginModelResponse.getEmail());
                binding.etDob.setText(loginModelResponse.getDob());
                binding.etAddress.setText(loginModelResponse.getAddress());
                binding.etClassName.setText(loginModelResponse.getClassname());
                //binding.etParentFullName.setText(loginModelResponse.getp());

                if (loginModelResponse.getGender().equalsIgnoreCase("Male")){
                    binding.radioMale.setChecked(true);
                    Gender= "Male";
                }else {
                    binding.radioFemale.setChecked(true);
                    Gender= "Female";
                }

            }
        }

        if (Connectivity.isConnected(ProfileUpdateActivity.this)){
           // GetAllClass();
           GetAllState();
        }else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb=(RadioButton)findViewById(checkedId);
               // textViewChoice.setText("You Selected " + rb.getText());
                Gender= rb.getText().toString();
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Et_parent_name=binding.etParentFullName.getText().toString();
                String Et_parent_mobile=binding.etParentPhone.getText().toString();
                String Et_email=binding.etEmail.getText().toString();
                String Et_sch_code=binding.etSchCode.getText().toString();
                String Et_sch_name=binding.etSchName.getText().toString();
                String Et_std_dob=binding.etDob.getText().toString();
                String Et_address=binding.etAddress.getText().toString();
               // String Et_city=binding.etCity.getText().toString();

                if (Et_parent_name.isEmpty()){
                    // Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Sorry", "Please enter parent name!");
              //  }else if (Et_firstname.isEmpty()) {
                    //Toast.makeText(SignupActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                //    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter full name!");
               // }else if (Et_mobile.isEmpty()) {
                    // Toast.makeText(SignupActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                //    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter mobile number!");
               // }else if (Et_email.isEmpty()){
                    //Toast.makeText(SignupActivity.this, "Please enter email id", Toast.LENGTH_SHORT).show();
                 //   SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter email id!");
              //  }else if (Et_pw.isEmpty()){
                    //Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
               //    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter password!");
                } 
                else {
                    if (Et_parent_mobile.length()==10){
                        //if (Patterns.EMAIL_ADDRESS.matcher(Et_email).matches()){
                          //  if (Et_pw.length()>5){
                                if (Connectivity.isConnected(ProfileUpdateActivity.this)){
                                    CallUpdate(Et_parent_name,Et_parent_mobile, Et_email, Et_sch_code, Et_sch_name, Et_std_dob, Et_address, CityId);
                                }else {
                                    SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Sorry", "You have no internet!");
                                }
                           // }else {
                            //    SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Enter Password minimum 6 characters long!");
                           // }

                    //    }else {
                          //  SweetAlt.showErrorMessage(SignupActivity.this, "Error", "Please enter valid email!");
                       // }
                    }else {
                        SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Error", "Please enter valid mobile no.");
                    }

                }
                
            }
        });

        binding.etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCalendar();
            }
        });
        
    }

    private void OpenCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);


        new SpinnerDatePickerDialogBuilder()
                .context(ProfileUpdateActivity.this)
                .callback(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(2015, 0, 1)
                .maxDate(currentYear, currentMonth, currentDay)
                .minDate(1950, 0, 1)
                .build()
                .show();
    }

    @SuppressLint("CheckResult")
    private void GetAllState() {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileUpdateActivity.this, R.style.MyGravity);
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

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileUpdateActivity.this, R.layout.spinner_item, classModelResponseList);
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

                                //******
                                for (int i=0; i<classModelResponseList.size(); i++){
                                    if (loginModelResponse.getState_name().equalsIgnoreCase(classModelResponseList.get(i))){
                                        binding.spinState.setSelection(i);
                                    }
                                }


                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Sorry", response.getMessage());

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
    private void getCity(String stateId) {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileUpdateActivity.this, R.style.MyGravity);
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

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileUpdateActivity.this, R.layout.spinner_item, classModelResponseList);
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

                                //******
                                for (int i=0; i<classModelResponseList.size(); i++){
                                    if (loginModelResponse.getClassid().equalsIgnoreCase(classModelResponseList.get(i))){
                                        binding.spinCity.setSelection(i);
                                    }
                                }


                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Sorry", response.getMessage());

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

//    @SuppressLint("CheckResult")
//    private void GetAllClass() {
//        final ProgressDialog progressDialog = new ProgressDialog(ProfileUpdateActivity.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();
//
//        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
//
//        apiInterface.GetAllClassName()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<ClassModel>() {
//                    @Override
//                    public void onNext(ClassModel response) {
//                        //Handle logic
//                        try {
//                            progressDialog.dismiss();
//                            Log.e("result_my_test", "" + response.getMessage());
//                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
//                            if (response.getStatus() == 1) {
//
//                                List<String> classModelResponseList= new ArrayList<>();
//                                for (int i=0; i<response.getResponse().size(); i++){
//                                    classModelResponseList.add(response.getResponse().get(i).getClassname());
//
//                                }
//
//                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileUpdateActivity.this, R.layout.spinner_item, classModelResponseList);
//                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                binding.spinClass.setAdapter(arrayAdapter);
//
//                                binding.spinClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        String tutorialsName = parent.getItemAtPosition(position).toString();
//                                         ClassId= response.getResponse().get(position).getClassid();
//
//                                    }
//                                    @Override
//                                    public void onNothingSelected(AdapterView <?> parent) {
//                                    }
//                                });
//
//                                //******
//                                for (int i=0; i<classModelResponseList.size(); i++){
//                                    if (loginModelResponse.getClassname().equalsIgnoreCase(classModelResponseList.get(i))){
//                                        binding.spinClass.setSelection(i);
//                                    }
//                                }
//
//
//                            } else {
//                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
//                                SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Sorry", response.getMessage());
//
//                            }
//
//                        } catch (Exception e) {
//                            progressDialog.dismiss();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        //Handle error
//                        progressDialog.dismiss();
//                        Log.e("mr_product_error", e.toString());
//
//                        if (e instanceof HttpException) {
//                            int code = ((HttpException) e).code();
//                            switch (code) {
//                                case 403:
//                                    break;
//                                case 404:
//                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 409:
//                                    break;
//                                default:
//                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                        } else {
//                            if (TextUtils.isEmpty(e.getMessage())) {
//                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
//                            } else {
//                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        progressDialog.dismiss();
//                    }
//                });
//
//    }

    @SuppressLint("CheckResult")
    private void CallUpdate(String et_parent_name, String et_parent_mobile, String et_email, String et_sch_code, String et_sch_name,
                            String et_std_dob, String et_address, String et_city) {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileUpdateActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.UpdateProfile(et_parent_name, et_parent_mobile, Student_id, et_email, et_sch_code, et_sch_name, et_std_dob,
                et_address,et_city , ClassId, StateId, Gender)
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

                                Intent intent = new Intent(ProfileUpdateActivity.this, OtpActivity.class);
                                intent.putExtra("Mobile_No", et_parent_mobile);
                                intent.putExtra("Student_id", Student_id);
                                startActivity(intent);

                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(ProfileUpdateActivity.this, "Sorry", response.getMessage());

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
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        binding.etDob.setText(simpleDateFormat.format(calendar.getTime()));
    }


}