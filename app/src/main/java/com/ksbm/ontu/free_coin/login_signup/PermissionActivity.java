package com.ksbm.ontu.free_coin.login_signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.CameraActivity;
import com.ksbm.ontu.databinding.ActivityPermissionBinding;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.main_screen.model.state_model.StateModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.File;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava2.HttpException;

public class PermissionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ActivityPermissionBinding binding;
    String Gender;
    String ClassId, StateId, CityId;
    SimpleDateFormat simpleDateFormat;
    SessionManager sessionManager;
    String imagePath = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_permission);
        sessionManager = new SessionManager(getApplicationContext());
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(PermissionActivity.this), PermissionActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);

        context = PermissionActivity.this;
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        sessionManager = new SessionManager(this);
        //set spinner
        AddSpinList();
        setData();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        if (Connectivity.isConnected(PermissionActivity.this)) {
            // GetAllClass();
            GetAllState();
        } else {
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                // textViewChoice.setText("You Selected " + rb.getText());
                Gender = rb.getText().toString();
                //Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCalendar();
            }
        });

        binding.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PermissionActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
//                                    if (SDK_INT >= Build.VERSION_CODES.R) {
//                                        if (checkPermission1()) {
//                                            isProductImageSelect = true;
//                                            Intent intent1 = new Intent(EditProduct.this, CameraActivity.class);
//                                            startActivityForResult(intent1, 123);
//                                        } else {
//                                            try {
//                                                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                                                intent.addCategory("android.intent.category.DEFAULT");
//                                                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
//                                                startActivityForResult(intent, 2296);
//                                            } catch (Exception e) {
//                                                Intent intent = new Intent();
//                                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                                                startActivityForResult(intent, 2296);
//                                            }
//                                        }
//
//
//                                    } else {
                                    // below android 11
                                    Intent intent1 = new Intent(PermissionActivity.this, CameraActivity.class);
                                    startActivityForResult(intent1, 123);
                                    // }

                                } else {
                                    // TODO - handle permission denied case
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }

                        }).check();
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Et_name = binding.etFullName.getText().toString();
                String Et_mobile = binding.etPhone.getText().toString();
                String Et_email = binding.etEmail.getText().toString();
                String Et_sch_code = binding.etSchCode.getText().toString();
                String Et_sch_name = binding.etSchName.getText().toString();
                String Et_std_dob = binding.etDob.getText().toString();
                String Et_address = binding.etAddress.getText().toString();

                if (Et_name.isEmpty()) {
                    SweetAlt.showErrorMessage(PermissionActivity.this, "Sorry", "Please enter full name!");
                } else if (Et_mobile.isEmpty()) {
                    SweetAlt.showErrorMessage(PermissionActivity.this, "Error", "Please enter mobile number!");
                } else if (!Et_email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(Et_email).matches()) {
                    SweetAlt.showErrorMessage(PermissionActivity.this, "Error", "Please enter valid email id!");
                } else {
                    if (Et_mobile.length() == 10) {
                        if (Connectivity.isConnected(PermissionActivity.this)) {
                            String eye_sight = binding.spinEyeSight.getSelectedItem().toString();
                            String whyEnglish = binding.spinWhyEnglish.getSelectedItem().toString();

                            if (imagePath != null && !imagePath.equalsIgnoreCase("")) {
                                CallUpdate(Et_name, Et_mobile, Et_email, Et_sch_code, Et_sch_name, Et_std_dob, Et_address, eye_sight, whyEnglish);
                            } else {
                                CallUpdateWithoutImage(Et_name, Et_mobile, Et_email, Et_sch_code, Et_sch_name, Et_std_dob, Et_address, eye_sight, whyEnglish);
                            }

                        } else {
                            SweetAlt.showErrorMessage(PermissionActivity.this, "Sorry", "You have no internet!");
                        }

                    } else {
                        SweetAlt.showErrorMessage(PermissionActivity.this, "Error", "Please enter valid mobile no.");
                    }

                }

            }
        });

    }

    @SuppressLint("CheckResult")
    private void CallUpdateWithoutImage(String et_name, String et_mobile, String et_email, String et_sch_code, String et_sch_name,
                                        String et_std_dob, String et_address, String eye_sight, String whyEnglish) {
        final ProgressDialog progressDialog = new ProgressDialog(PermissionActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.UpdateUserProfileNoImage(et_name, et_mobile, sessionManager.getUser().getUserid(), et_email, et_std_dob, et_address, CityId, StateId, Gender,
                eye_sight, whyEnglish, "", "")
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
                                Toast.makeText(PermissionActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(response.getResponse());
                                finish();
                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(PermissionActivity.this, "Sorry", response.getMessage());

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
    private void CallUpdate(String et_parent_name, String et_parent_mobile, String et_email, String et_sch_code, String et_sch_name,
                            String et_std_dob, String et_address, String eye_sight, String whyEnglish) {
        final ProgressDialog progressDialog = new ProgressDialog(PermissionActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        MultipartBody.Part body = null;
        if (imagePath != null && !imagePath.equalsIgnoreCase("")) {
            File mPhotoFile = new File(imagePath);
            RequestBody fileBody;

            fileBody = RequestBody.create(MediaType.parse("image/*"), mPhotoFile);
            body = MultipartBody.Part.createFormData("profile_pic", mPhotoFile.getName(), fileBody);
        }

        RequestBody et_name = RequestBody.create(MediaType.parse("text/plain"), et_parent_name);
        RequestBody et_mobile = RequestBody.create(MediaType.parse("text/plain"), et_parent_mobile);
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getUser().getUserid());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), et_email);
        RequestBody std_dob = RequestBody.create(MediaType.parse("text/plain"), et_std_dob);
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), et_address);
        RequestBody city_id = RequestBody.create(MediaType.parse("text/plain"), CityId);
        RequestBody state_id = RequestBody.create(MediaType.parse("text/plain"), StateId);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), Gender);
        RequestBody eye_quality = RequestBody.create(MediaType.parse("text/plain"), eye_sight);
        RequestBody why_learn = RequestBody.create(MediaType.parse("text/plain"), whyEnglish);
        RequestBody profession_id = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody pincode = RequestBody.create(MediaType.parse("text/plain"), "");


        apiInterface.UpdateUserProfile(et_name, et_mobile, user_id, email, std_dob, address, city_id, state_id, gender,
                eye_quality, why_learn, profession_id, pincode, body)
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
                                Toast.makeText(PermissionActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(response.getResponse());
                                finish();
                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(PermissionActivity.this, "Sorry", response.getMessage());

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

    public String getURLForResource(int resourceId) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    private void OpenCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);


        new SpinnerDatePickerDialogBuilder()
                .context(PermissionActivity.this)
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

    private void setData() {
        binding.etSchCode.setText(sessionManager.getUser().getSchool_code());
        binding.etSchName.setText(sessionManager.getUser().getSchoolName());
        binding.etClassName.setText(sessionManager.getUser().getClassname());
        binding.etFullName.setText(sessionManager.getUser().getFullname());
        binding.etPhone.setText(sessionManager.getUser().getMobile());
        binding.etDob.setText(sessionManager.getUser().getDob());
        binding.etAddress.setText(sessionManager.getUser().getAddress());

        if (sessionManager.getUser().getGender().equalsIgnoreCase("Male")) {
            binding.radioMale.setChecked(true);
            Gender = "Male";
        } else {
            binding.radioFemale.setChecked(true);
            Gender = "Female";
        }
    }

    private void AddSpinList() {
        List<String> eye_sight = new ArrayList<>();
        eye_sight.add("Quality");
        eye_sight.add("Number");

        List<String> whyEnglish = new ArrayList<>();
        whyEnglish.add("For better english skills in daily life (Social Life)");
        whyEnglish.add("To score good marks in exams.");
        whyEnglish.add("To visit abroad.");
        whyEnglish.add("To study abroad.");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PermissionActivity.this, R.layout.spinner_item, eye_sight);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinEyeSight.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(PermissionActivity.this, R.layout.spinner_item, whyEnglish);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinWhyEnglish.setAdapter(arrayAdapter1);
    }

    @SuppressLint("CheckResult")
    private void GetAllState() {
        final ProgressDialog progressDialog = new ProgressDialog(PermissionActivity.this, R.style.MyGravity);
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

                                List<String> classModelResponseList = new ArrayList<>();
                                for (int i = 0; i < response.getResponse().size(); i++) {
                                    classModelResponseList.add(response.getResponse().get(i).getName());

                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PermissionActivity.this, R.layout.spinner_item, classModelResponseList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spinState.setAdapter(arrayAdapter);


                                binding.spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String tutorialsName = parent.getItemAtPosition(position).toString();
                                        StateId = response.getResponse().get(position).getStateId();

                                        if (StateId != null) {
                                            getCity(StateId);
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });

                                //******
//                                for (int i = 0; i < classModelResponseList.size(); i++) {
//                                    if (sessionManager.getUser().getState_name().equalsIgnoreCase(classModelResponseList.get(i))) {
//                                        binding.spinState.setSelection(i);
//                                    }
//                                }

                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(PermissionActivity.this, "Sorry", response.getMessage());

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
        final ProgressDialog progressDialog = new ProgressDialog(PermissionActivity.this, R.style.MyGravity);
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

                                List<String> classModelResponseList = new ArrayList<>();
                                for (int i = 0; i < response.getResponse().size(); i++) {
                                    classModelResponseList.add(response.getResponse().get(i).getName());

                                }

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PermissionActivity.this, R.layout.spinner_item, classModelResponseList);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                binding.spinCity.setAdapter(arrayAdapter);

                                binding.spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String tutorialsName = parent.getItemAtPosition(position).toString();
                                        CityId = response.getResponse().get(position).getCity_id();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });

                                //******
//                                for (int i = 0; i < classModelResponseList.size(); i++) {
//                                    if (sessionManager.getUser().getCityname().equalsIgnoreCase(classModelResponseList.get(i))) {
//                                        binding.spinCity.setSelection(i);
//                                    }
//                                }


                            } else {
                                // Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                SweetAlt.showErrorMessage(PermissionActivity.this, "Sorry", response.getMessage());

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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        binding.etDob.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //*****************************
        if (resultCode == 100 && requestCode == 123) {
            if (data.hasExtra("image")) {
                if (!data.getStringExtra("image").equals("")) {
                    // appSession.setImagePath("");
                    imagePath = data.getStringExtra("image");
                    //  appSession.setImagePath(imagePath);
                    Log.e("image_uri_path", "" + imagePath);

                    Glide.with(PermissionActivity.this)
                            .load(imagePath)
                            // .thumbnail(0.5f)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.ivUser);

                }
            }
        }

    }


}