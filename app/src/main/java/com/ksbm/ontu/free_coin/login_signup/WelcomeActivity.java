package com.ksbm.ontu.free_coin.login_signup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.ksbm.ontu.R;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Language_Settings;
import com.ksbm.ontu.databinding.ActivityWelcomeBinding;
import com.ksbm.ontu.open_market.GenderActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;
    SessionManager sessionManager;
    private GoogleSignInClient mSignInClient;
    private static final int RC_SIGN_IN = 1;
    private String social_name = "", social_id = "", social_email = "", social_img = "";
    CallbackManager callbackManager;
    MeraSharedPreferance meraSharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_welcome);
        sessionManager = new SessionManager(WelcomeActivity.this);
        meraSharedPreferance = MeraSharedPreferance.getInstance(this);

        //google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mSignInClient = GoogleSignIn.getClient((Context) this, gso);

        //*****fb login
        callbackManager = CallbackManager.Factory.create();

        binding.ivGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launches the sign in flow, the result is returned in onActivityResult
                Intent intent = mSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        binding.tvLoginManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Context) WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (!isLoggedIn && !sessionManager.isLoggedIn()) {
//             LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this, Arrays.asList("public_profile", "email"));
        }

        binding.fbButton.setPermissions(Arrays.asList("public_profile", "email"));
        binding.fbButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("onSuccess");
                        // Toast.makeText(Login_Activity.this, "success", Toast.LENGTH_SHORT).show();

                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken", accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        Log.i("Login_FB", response.toString());
                                        try {
                                            social_id = object.getString("id");
                                            try {
                                                URL profile_pic = new URL(
                                                        "http://graph.facebook.com/" + social_id + "/picture?type=large");
                                                Log.i("profile_pic", profile_pic + "");

                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }
                                            social_name = object.getString("name");
                                            String first_name = object.getString("first_name");
                                            String last_name = object.getString("last_name");

                                            if (object.has("email")) {
                                                social_email = object.getString("email");
                                            } else {
                                                social_email = "";
                                            }

                                            Log.e("fb_name", first_name + " " + last_name);
                                            Log.e("fb_email", social_email + "");

                                            //  if (social_email!=null && !social_email.isEmpty()){
                                            // if (sessionManager.getTokenId() != null && !sessionManager.getTokenId().equalsIgnoreCase("")) {
                                            if (Connectivity.isConnected(WelcomeActivity.this)) {
                                                SocialLoginApi(social_email, social_id, first_name, last_name, "facebook");
                                            } else {
                                                Toast.makeText((Context) WelcomeActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                                            }
                                            //  } else {
                                            //   Toast.makeText(WelcomeActivity.this, "Firebase token not get yet, Please restart the app.", Toast.LENGTH_SHORT).show();
                                            // }

//                                            }else {
//                                               // utilities.dialogOK(context, getString(R.string.validation_title), "Email id not found, Please try other Facebook login", getString(R.string.ok), false);
//                                                LoginManager.getInstance().logOut();
//                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        // parameters.putString("fields","id,name,email,gender, birthday");
                        parameters.putString("fields", "id, name, first_name, last_name, email,link");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                        Toast.makeText((Context) WelcomeActivity.this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Toast.makeText((Context) WelcomeActivity.this, getString(R.string.fail), Toast.LENGTH_LONG).show();
                        //  Log.e("LoginActivity", exception.toString());
                        // Log.e("LoginActivity", exception.getMessage());
                    }
                });
//        printHashKey(this);

    }

//    public static void printHashKey(Context pContext) {
//        try {
//            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("printHashKey", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("printHashKey", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("printHashKey", "printHashKey()", e);
//        }
//    }


    public void onClick(View v) {
        if (v == binding.ivFbLogin) {
            binding.fbButton.performClick();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // handleSignInResult(result);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            // Log.e("NewSignIn", " " + task.getResult());
            if (task.isSuccessful()) {
                // Sign in succeeded, proceed with account
                GoogleSignInAccount acct = task.getResult();
                assert acct != null;
                social_name = acct.getDisplayName();
                social_email = acct.getEmail();

                String lastname = acct.getFamilyName();
                String firstname = acct.getGivenName();

                if (acct.getPhotoUrl() != null) {
                    social_img = acct.getPhotoUrl().toString();
                } else {
                    social_img = "";
                }
                Log.e("social_img ", " " + social_img);
                social_id = acct.getId();

                acct.getId();
                Log.e("GoogleResult", social_id + "------" + social_name + "------" + social_email);
                Log.e("GoogleResult1", firstname + "-" + lastname);

                if (Connectivity.isConnected(WelcomeActivity.this)) {
                    // Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    gotoHome(firstname, lastname);
                } else {
                    Toast.makeText((Context) this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }

            } else {
                Log.d("sdadcancel",""+task);
                Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
                // Sign in failed, handle failure and update UI
                // ...
            }
        }

    }

    private void gotoHome(String firstname, String lastname) {
        if (!social_email.isEmpty() && !social_id.isEmpty()) {

            if (!Patterns.EMAIL_ADDRESS.matcher(social_email).matches()) {
                Toast.makeText((Context) WelcomeActivity.this, "Email not valid", Toast.LENGTH_SHORT).show();
            } else {
                // if (sessionManager.getTokenId() != null && !sessionManager.getTokenId().equalsIgnoreCase("")) {
                if (Connectivity.isConnected(WelcomeActivity.this)) {
                    SocialLoginApi(social_email, social_id, firstname, lastname, "google");

                } else {
                    Toast.makeText((Context) WelcomeActivity.this, "Please check internet", Toast.LENGTH_SHORT).show();
                }
//                } else {
//                    Toast.makeText(WelcomeActivity.this, "Firebase token not get yet, Please restart the app.", Toast.LENGTH_SHORT).show();
//                }

            }
        } else {
            Toast.makeText((Context) WelcomeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("CheckResult")
    private void SocialLoginApi(String social_email, String social_id, String firstname, String lastname, String social_type) {
        final ProgressDialog progressDialog = new ProgressDialog((Context) WelcomeActivity.this, R.style.MyGravity);
        progressDialog.setMessage("Processing...");
        //  progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.LoginSocialUser(firstname + " " + lastname, social_email, social_id, social_type, sessionManager.getTokenId(), sessionManager.getDeviceId(), "M")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(LoginModel response) {
                        System.out.println(response.getResponse().toString());
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getStatus());
                            Toast.makeText((Context) WelcomeActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                // Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(response.getResponse());
                                sessionManager.setUser(response.getResponse());
                                if (response.getResponse().getProfessionName().equalsIgnoreCase("")) {
                                    Intent intent = new Intent((Context) WelcomeActivity.this, GenderActivity.class);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent((Context) WelcomeActivity.this, MainActivity.class);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                /*Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();*/
                            }else if (response.getStatus() == 2){
                                // Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(response.getResponse());
                                sessionManager.setUser(response.getResponse());
                                meraSharedPreferance.isOpenMarketSave(true);
                                meraSharedPreferance.professionSave(response.getOpen_market().getProfession());
                                meraSharedPreferance.ageSave(response.getOpen_market().getAge());
                                meraSharedPreferance.levelSave(response.getOpen_market().getEnglish_proficiency());

                                Intent intent = new Intent((Context) WelcomeActivity.this, MainActivity.class);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                SweetAlt.showErrorMessage(WelcomeActivity.this, "Sorry", response.getMessage());
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
                        // Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //  Toast.makeText(Login_Activity.this, R.string.invalid_login, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(Login_Activity.this, R.string.cb_snooze_network_error, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(Login_Activity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText((Context) WelcomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        e.printStackTrace();

                    }


                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constant.EnableLanguage) {
            if (sessionManager.getIs_first_time(WelcomeActivity.this)) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder((Context) WelcomeActivity.this);
                LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater1.inflate(R.layout.language_dialog, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                RecyclerView languageView = dialogView.findViewById(R.id.recyclerView);
                languageView.setLayoutManager(new LinearLayoutManager((Context) WelcomeActivity.this));
                AlertDialog alertDialog = dialog.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                Language_Settings.GetLanguage(languageView, WelcomeActivity.this, alertDialog);

            }
        }

    }
}