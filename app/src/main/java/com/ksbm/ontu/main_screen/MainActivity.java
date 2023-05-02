package com.ksbm.ontu.main_screen;

import static com.ksbm.ontu.api_client.Base_Url.API_KEY;
import static com.ksbm.ontu.utils.Constant.SOUND_EFFECT;
import static com.ksbm.ontu.utils.Constant.TranslateCoinCharge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityMainBinding;
import com.ksbm.ontu.firebase.NotificationUtils;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.main_screen.fragment.Home_Fragment;
import com.ksbm.ontu.main_screen.fragment.Setting_Fragment;
import com.ksbm.ontu.main_screen.fragment.ShopFragment;
import com.ksbm.ontu.main_screen.model.ResponseUpgradeCourseStatus;
import com.ksbm.ontu.main_screen.model.SettingsModel;
import com.ksbm.ontu.profile.Profile_Fragment;
import com.ksbm.ontu.progression_report.ProgressionReportFragment;
import com.ksbm.ontu.progression_report.model.ProgressionReportResponse;
import com.ksbm.ontu.service.BackgroundMusicService;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.translation.MainViewModel;
import com.ksbm.ontu.translation.TranslateFragment;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;

import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.GoogleCloudTTSFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public BottomNavigationView navView;
    TextView tv_main_header;
    SessionManager sessionManager;
    ActivityMainBinding binding;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    public static MainViewModel mMainViewModel;
    MeraSharedPreferance meraSharedPreferance;

    private Handler mHandler;
    private TextView text;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    setDefaultFragment();
                    return true;

                case R.id.settings:
                    Setting_Fragment myFragment = (Setting_Fragment) getSupportFragmentManager().findFragmentByTag("Settings");
                    if (myFragment != null && myFragment.isVisible()) {
                        // add your code here
                        // Toast.makeText(MainActivity.this, "Already Open", Toast.LENGTH_SHORT).show();
                    } else {
                        removeBottomNavigationLabels(1);
                        Fragment fragment_home = new Setting_Fragment();
                        FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                        ft_home.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                        ft_home.replace(R.id.frame, fragment_home, "Settings");
                        ft_home.addToBackStack(null);
                        ft_home.commit();
                    }


                    return true;
                case R.id.translate:
                    TranslateFragment myFragment1 = (TranslateFragment) getSupportFragmentManager().findFragmentByTag("TranslateFragment");
                    if (myFragment1 != null && myFragment1.isVisible()) {
                        // add your code here
                        // Toast.makeText(MainActivity.this, "Already Open", Toast.LENGTH_SHORT).show();
                    } else {
                        removeBottomNavigationLabels(2);
                        Fragment fragment_create = new TranslateFragment();
                        FragmentTransaction ft_create = getSupportFragmentManager().beginTransaction();
                        ft_create.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                        ft_create.replace(R.id.frame, fragment_create, "TranslateFragment");
                        ft_create.addToBackStack(null);
                        ft_create.commit();
                    }

                    return true;

                case R.id.shop:
                    ShopFragment myFragment2 = (ShopFragment) getSupportFragmentManager().findFragmentByTag("ShopFragment");
                    if (myFragment2 != null && myFragment2.isVisible()) {
                        // add your code here
                        // Toast.makeText(MainActivity.this, "Already Open", Toast.LENGTH_SHORT).show();
                    } else {
                        removeBottomNavigationLabels(3);

                        Fragment fragment_privacy = new ShopFragment();
                        FragmentTransaction ft_p = getSupportFragmentManager().beginTransaction();
                        ft_p.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                        ft_p.replace(R.id.frame, fragment_privacy, "ShopFragment");
                        ft_p.addToBackStack(null);
                        ft_p.commit();

                    }

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  if (SessionManager.getCurrentLanguage(MainActivity.this)!=null)
        // Utils.setLanguageForApp(SessionManager.getCurrentLanguage(MainActivity.this), MainActivity.this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        meraSharedPreferance = MeraSharedPreferance.getInstance(this);
        sessionManager = new SessionManager(this);
        //iv_logo = findViewById(R.id.iv_logo);
        // tv_main_header = findViewById(R.id.tv_main_header);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setItemIconTintList(null);
        getProgressionReport();

//        binding.appbar.tvReport.setText(sessionManager.getUser().getCoin_balance()+"");
        //google cloud text to speech
//        Toast.makeText((Context) this, sessionManager.getUser().getUserid(), Toast.LENGTH_SHORT).show();
        GoogleCloudTTS googleCloudTTS = GoogleCloudTTSFactory.create(Constant.DEVELOPER_KEY);
        mMainViewModel = new MainViewModel(getApplication(), googleCloudTTS);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder((Context) MainActivity.this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        binding.appbar.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new Profile_Fragment();
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
            }
        });


        binding.appbar.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  OpenFreeCoinDialog();
                Intent intent = new Intent((Context) MainActivity.this, FreeCoinDialog.class);
                startActivity(intent);
            }
        });


        binding.appbar.llReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment comment_f = new ProgressionReportFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                Bundle args = new Bundle();
                // args.putBoolean("Pickup", false);
                // args.putString("user_id", item.fb_id);
                comment_f.setArguments(args);
                transaction.addToBackStack(null);
                transaction.add(R.id.frame, comment_f, "FreeCoinFragment").commit();

            }
        });


        binding.appbar.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OpenAlertDialog();
                Intent intent = new Intent((Context) MainActivity.this, Alerts_Dialog.class);
                startActivity(intent);

            }
        });

        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.appbar.ivAlert);

        //***set default open fragment
        setDefaultFragment();

        if (Connectivity.isConnected(this)) {
            GetAllSettings();
            getUpgradeCourseStatus();

            if (!sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.studend_role_id)) {
               // OpenOneTimeBanner dialog
                if (!sessionManager.isOneTimeBanner()){
                    Intent intent = new Intent((Context) MainActivity.this, OneTimeBannerDialog.class);
                    startActivity(intent);

                }
            }
        }

    }

    @SuppressLint("CheckResult")
    private void getProgressionReport() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.report_co_progression(API_KEY, sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response);
                            setData(response);

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

    private void setData(JsonObject response) {
        Gson gson = new Gson();
        ProgressionReportResponse object = gson.fromJson(response, ProgressionReportResponse.class);
        binding.appbar.tvReport.setText(String.valueOf(object.getResponse().getCoinBalance()));
    }


    public void CheckBottom(int pos) {
        navView.getMenu().getItem(pos).setChecked(true);
    }

    @SuppressLint("CheckResult")
    private void GetAllSettings() {
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAllSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SettingsModel>() {
                    @Override
                    public void onNext(SettingsModel response) {
                        //Handle logic
                        try {
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                sessionManager.save_settings_data(response.getResponse());

                                for (int i = 0; i < response.getResponse().size(); i++) {
                                    if (response.getResponse().get(i).getSettingKey().equalsIgnoreCase("translation_charge")) {
                                        TranslateCoinCharge = response.getResponse().get(i).getSettingValue();
                                    }
                                    if (response.getResponse().get(i).getSettingKey().equalsIgnoreCase("foundation_learning_reward")) {
                                        Constant.FoundationLearningCoinBonus = response.getResponse().get(i).getSettingValue();
                                    }if (response.getResponse().get(i).getSettingKey().equalsIgnoreCase("classyme_whatsapp_no")) {
                                        Constant.WHATSAPP_NO = response.getResponse().get(i).getSettingValue();
                                    }
                                }


                            } else {
                                //  binding.swipeToRefresh.setVisibility(View.GONE);
                                //  SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());

                    }

                    @Override
                    public void onComplete() {
                        //  progressDialog.dismiss();
                    }
                });


    }


    public void setDefaultFragment() {
        removeBottomNavigationLabels(0);
        Fragment fragment_home = new Home_Fragment();
        FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
        //ft_home.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        ft_home.replace(R.id.frame, fragment_home, "HOME_FRAGMENT");
        ft_home.addToBackStack(null);
        ft_home.commit();
    }


    public void LogoutUser() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this).setTitle(R.string.app_name)
                .setMessage("Are you sure, you want to logout");

        dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                if (googleApiClient != null && googleApiClient.isConnected()) {
                    // signed in. Show the "sign out" button and explanation.
                    google_logout();
                    disconnectFromFacebook();
                } else {
                    Log.e("logout_app", "local login logout");
                    // not signed in. Show the "sign in" button and explanation.
                    disconnectFromFacebook();
                    sessionManager.logout();
                }
                meraSharedPreferance.clearPreferance();


            }

        });
        final AlertDialog alert = dialog.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red_500));
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red_500));

    }

    private void disconnectFromFacebook() {
        Log.e("logout_app_fb", "fb login logout");
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

                sessionManager.logout();

            }
        }).executeAsync();
    }

    private void google_logout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e("logout_app_gmail", "google login logout");
                            sessionManager.logout();

                        } else {
                            Toast.makeText((Context) MainActivity.this, "Session not close", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Home_Fragment myFragment = (Home_Fragment) getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void removeBottomNavigationLabels(int pos) {
        // for (int i = 0; i < bottomNavigationView.getChildCount(); i++) {

        View item = navView.getChildAt(0);

        // if (item instanceof BottomNavigationMenuView) {
        BottomNavigationMenuView menu = (BottomNavigationMenuView) item;

        for (int j = 0; j < menu.getChildCount(); j++) {
            // Log.e("pos_bottom", "" + menu.getChildCount());
            if (pos == j) {
                View menuItem = menu.getChildAt(j);

                View small = menuItem.findViewById(R.id.smallLabel);
                // if (small instanceof TextView) {
                ((TextView) small).setVisibility(View.VISIBLE);//invisible
                //}
                View large = menuItem.findViewById(R.id.largeLabel);
                // if (large instanceof TextView) {
                ((TextView) large).setVisibility(View.VISIBLE);//invisible
                //  }
            } else {
                View menuItem = menu.getChildAt(j);

                View small = menuItem.findViewById(R.id.smallLabel);
                // if (small instanceof TextView) {
                ((TextView) small).setVisibility(View.VISIBLE);
                // }
                View large = menuItem.findViewById(R.id.largeLabel);
                //if (large instanceof TextView) {
                ((TextView) large).setVisibility(View.VISIBLE);
                // }
            }
        }


        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            if (pos == i) {
                final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
               // iconView.setPadding(0, 0, 0, 20);

                ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, displayMetrics);
                iconView.setLayoutParams(layoutParams);
            } else {
                final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
              //  iconView.setPadding(0, 0, 0, 10);
                ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, displayMetrics);
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, displayMetrics);
                iconView.setLayoutParams(layoutParams);
            }

        }

    }


    public void Update_header(String title) {
        //  tv_main_header.setText(title);
    }

    public void Recreate() {
        MainActivity.this.recreate();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onResume() {
       // mMainViewModel.resume();
        Speach_Record_Activity.init_tts(MainActivity.this);
        getProgressionReport();
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            if (SessionManager.getMyPref(SOUND_EFFECT).equalsIgnoreCase(String.valueOf(true))) {
                //play bg music service
                Intent musicintent = new Intent((Context) MainActivity.this, BackgroundMusicService.class);
                // musicintent.putExtra(EXTRA_SONGINDEX, 1);
                startService(musicintent);
                SessionManager.setMypref(SOUND_EFFECT, String.valueOf(true));
            }

        } else {
            Intent musicintent = new Intent((Context) MainActivity.this, BackgroundMusicService.class);
            stopService(musicintent);

            SessionManager.setMypref(SOUND_EFFECT, String.valueOf(false));
        }

        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.man)
                    .placeholder(R.drawable.man)
                    .into(binding.appbar.ivUser);
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMainViewModel.dispose();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
       // mMainViewModel.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
       // mMainViewModel.dispose();
        super.onStop();
    }

    @SuppressLint("CheckResult")
    private void getUpgradeCourseStatus() {
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.UpgradeCourseStatus(sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseUpgradeCourseStatus>() {
                    @Override
                    public void onNext(ResponseUpgradeCourseStatus response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test calendar", "" + response.getPlanUpgradeAmount());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.isStatus()) {
                                meraSharedPreferance.plan_upgrade_amountSave(response.getPlanUpgradeAmount());
                                meraSharedPreferance.isPackageUpgradedSave(response.isPlan());
                            } else {

                            }

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
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

}