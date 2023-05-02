package com.ksbm.ontu.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.ksbm.ontu.BuildConfig;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModelResponse;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.databinding.FragmentProfileBinding;
import com.ksbm.ontu.profile.activity.MyChildActivity;
import com.ksbm.ontu.profile.model.ProfileResponse;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.ApiInterface;
import com.ksbm.ontu.utils.Apis;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.ProgressRequestBody;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bot.box.appusage.contract.PackageContracts;
import bot.box.appusage.handler.Monitor;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.DurationRange;
import bot.box.appusage.utils.UsageUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.HttpException;

public class Profile_Fragment extends Fragment implements ProgressRequestBody.UploadCallbacks {
    FragmentProfileBinding binding;
    SessionManager sessionManager;
    ProgressDialog progress;
    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        progress = new ProgressDialog(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        if (sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.parent_role_id)) {
            binding.tvMychild.setVisibility(View.VISIBLE);
        }
        if (!sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.studend_role_id)) {
            binding.tvSchoolStateRankText.setText("State Rank");
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        binding.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestGallery(getActivity())) {
                /*    Matisse.from(Profile_Fragment.this)
                            .choose(MimeType.ofAll())
                            .countable(true)
                            .maxSelectable(1).capture(true).captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".provider"))
                            .gridExpectedSize(300)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine()).setOnSelectedListener(new OnSelectedListener() {*/
                    Matisse.from(Profile_Fragment.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(1).capture(true).captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".provider"))
                            .gridExpectedSize(300)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine()).setOnSelectedListener(new OnSelectedListener() {
                        @Override
                        public void onSelected(@NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                            Log.e("pathlistsize", String.valueOf(pathList.size()));
                            /*if (pathList.size() > 0) {*/

                                System.out.println(pathList.get(0));
                          /*  }*/
                        }
                    })
                            .showPreview(false) // Default is `true`
                            .forResult(21);
                }

            }
        });

        binding.tvMychild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyChildActivity.class);
                startActivity(intent);
            }
        });


        binding.rlscreeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.rlscreeen, getActivity());
            }
        });


        binding.tvLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment_home = new leaderboard_fragment();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
            }
        });

        binding.bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankData();
            }
        });


        binding.tvCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment_home = new certificate_fragment();
                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
                ft_home.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
            }
        });

        if (meraSharedPreferance.isOpenMarketGet()){
            binding.schoolName.setVisibility(View.GONE);
            binding.classeName.setVisibility(View.GONE);
        }else{
            binding.schoolName.setVisibility(View.VISIBLE);
            binding.classeName.setVisibility(View.VISIBLE);
        }

        if (Connectivity.isConnected(getActivity())) {
            getProfile();
        } else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        if (meraSharedPreferance.isOpenMarketGet()){
            binding.schoolRankLl.setVisibility(View.GONE);
        }else{
            binding.schoolRankLl.setVisibility(View.VISIBLE);
        }

        //*********************

        Log.d("useriduserid",sessionManager.getUser().getUserid());
//        Toast.makeText(getActivity(), sessionManager.getUser().getUserid(), Toast.LENGTH_SHORT).show();


        return view;
    }

    private void FetchTotalTimeSpend() {
        Monitor.scan().queryFor(new PackageContracts.View() {

            @Override
            public void getUsageForPackage(AppData appData, int duration) {
                Log.e("total_uses_time", "" + UsageUtils.humanReadableMillis(appData.mUsageTime));

//                ((TextView) findViewById(R.id.name)).setText(appData.mName);
//                ((TextView) findViewById(R.id.total_times_launched)).setText(appData.mCount + " " + getResources().getQuantityString(R.plurals.times_launched, appData.mCount));
//                ((TextView) findViewById(R.id.data_used)).setText(UsageUtils.humanReadableByteCount(appData.mWifi + appData.mMobile) + " Consumed");
//                ((TextView) findViewById(R.id.last_launched)).setText(String.format(Locale.getDefault(),
//                        "%s", "Last Launch " +
//                                new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date(appData.mEventTime))));
                binding.tvTodaySpendTime.setText(UsageUtils.humanReadableMillis(appData.mUsageTime));

            }

            @Override
            public void showProgress() {
                //optional
            }

            @Override
            public void hideProgress() {
                //optional
            }
        }).whichPackage(getActivity().getPackageName()).fetchFor(DurationRange.TODAY);
    }

    @SuppressLint("CheckResult")
    private void getProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetProfileDetails(sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginModel>() {
                    @Override
                    public void onNext(LoginModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getResponse());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                setData(response.getResponse());

                            } else {
                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

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

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }


    private void setData(LoginModelResponse response) {
        binding.tvName.setText(" "+response.getFullname());
        binding.tvTotalCoin.setText(" "+response.getCoin_balance());
        binding.tvSchRank.setText(" "+response.getSchool_rank());
        binding.tvNationalRank.setText(" "+response.getNational_rank());
        binding.tvBestHit.setText(" "+response.getBest_hit() + "%");
        binding.tvTodayHit.setText(" "+response.getToday_hit() + "%");
        binding.tvEnglishScore.setText(" "+response.getEnglish_score());

        if (response.getSchoolName()!=null){
            if (response.getSchoolName().equalsIgnoreCase("")) {
                binding.tvNameSchool.setText("NA");
            } else {
                binding.tvNameSchool.setText(response.getSchoolName());
            }
        }
        binding.tvStudentClass.setText(response.getClassname());
        String replace_persign = response.getMemory_map().replace("%", "");
        binding.offlineScoreBar.setProgress((int) Double.parseDouble(replace_persign));
        binding.offlineScoreBar1.setProgress((int) Double.parseDouble(replace_persign));
        binding.tvMemorymap.setText(50 + "%");
     //   binding.offlineScoreBar1.setProgress(70);
        binding.offlineScoreBar1.setProgressText(replace_persign + "%");


        String[] splited = response.getCreatedOn().split(" ");
        binding.tvJoinDate.setText(splited[0]);

        // binding.tvAvgTotalSpentTime.setText(response.getClassname());
        // binding.tvTodaySpendTime.setText(response.getClassname());

        if (response.getProfilePic() != null && !response.getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.man)
                    .placeholder(R.drawable.man)
                    .into(binding.ivUser);
        } else {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.man)
                    .placeholder(R.drawable.man)
                    .into(binding.ivUser);
        }

    }


    private void bankData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        final View customLayout = getLayoutInflater().inflate(R.layout.fragment_bank_details, null);
        builder.setView(customLayout);

        ImageView cut_iv = customLayout.findViewById(R.id.cut_iv);
        EditText account_number = customLayout.findViewById(R.id.account_number);
        EditText account_holder_name = customLayout.findViewById(R.id.account_holder_name);
        EditText ifsc = customLayout.findViewById(R.id.ifsc_code);
        EditText branchName = customLayout.findViewById(R.id.branch_name);
        Button submit_btn = customLayout.findViewById(R.id.submit_btn);

        AlertDialog dialog = builder.create();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account_number.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "accoun_number_empty", Toast.LENGTH_SHORT).show();
                } else if (account_number.getText().toString().length() < 10) {
                    Toast.makeText(getActivity(), "account number should be 10 number", Toast.LENGTH_SHORT).show();
                } else if (account_holder_name.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "account holder name is empty", Toast.LENGTH_SHORT).show();
                }else if (ifsc.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "IFSC code is empty", Toast.LENGTH_SHORT).show();
                } else if (branchName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "branch name is empty", Toast.LENGTH_SHORT).show();
                }else{
                    setBankDetail(account_number.getText().toString()
                            ,account_holder_name.getText().toString()
                            ,ifsc.getText().toString()
                            ,branchName.getText().toString());
                    Toast.makeText(getActivity(), "bank details submitted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cut_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();



    }

    public void setBankDetail(String acct_no, String acct_hldr_name, String ifsc, String branchName){

        final ProgressDialog progressDialog = new ProgressDialog(requireContext(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();


        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.bankDetails(sessionManager.getUser().getUserid(), acct_no, acct_hldr_name, ifsc, branchName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ProfileResponse>() {
                    @Override
                    public void onNext(ProfileResponse response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMsg());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.isStatus()) {
                                meraSharedPreferance.isPackageUpgradedSave(true);
//                                succes.setVisibility(View.VISIBLE);
//                                orderid.setText(transactionId);
                            } else {
                                SweetAlt.showErrorMessage(requireContext(), "Sorry", response.getMsg());

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

    @Override
    public void onResume() {
        super.onResume();

        if (Monitor.hasUsagePermission()) {

            FetchTotalTimeSpend();
        } else {
            String title = getActivity().getResources().getString(R.string.app_name);
            String msg = "Allow permission for app usage time spent";
            SweetAlt.normalDialog(getActivity(), title, msg, true, true, "Cancel", "Allow", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    Monitor.requestUsagePermission();
                }
            });
        }

    }

    public static boolean checkAndRequestGallery(final Activity context) {
        int ExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    102);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 21 && resultCode == RESULT_OK) {
            System.out.println("Vishal ss " + Matisse.obtainPathResult(data).get(0));
            uploadPhoto(sessionManager.getUser().getUserid(), Matisse.obtainPathResult(data).get(0), "profile_pic");
        }
    }


    public void uploadPhoto(String user_id, String paths, String names) {
        progress.setMessage("Uploading...");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();
        progress.setProgress(0);
        File file = new File(paths);
        ProgressRequestBody fileBody = new ProgressRequestBody(file, "profile_pic", this);
        ApiInterface apiInterface = Apis.postApiClient().create(ApiInterface.class);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(names, file.getName(), fileBody);
        RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody api_key = RequestBody.create(MediaType.parse("text/plain"), "123456");
        Call<JsonObject> request = apiInterface.apiUploadPhoto(filePart, api_key, uid);
        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    progress.dismiss();

                    LoginModelResponse loginModelResponse = sessionManager.getUser();
                    try {
                        loginModelResponse.setProfilePic(response.body().get("data").getAsString());
                    }catch (Exception e){
                        loginModelResponse.setProfilePic("https://cdn-icons-png.flaticon.com/512/3177/3177440.png");
                        Toast.makeText(getActivity(), "Backend Issue", Toast.LENGTH_SHORT).show();
                    }
                    sessionManager.setUser(loginModelResponse);
                    getProfile();

                    Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                /* we can also stop our progress update here, although I have not check if the onError is being called when the file could not be downloaded, so I will just use this as a backup plan just in case the onError did not get called. So I can stop the progress right here. */
            }
        });

    }

    @Override
    public void onProgressUpdate(int percentage) {
        progress.setProgress(percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}
