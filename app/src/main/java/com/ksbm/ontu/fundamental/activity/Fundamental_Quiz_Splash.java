package com.ksbm.ontu.fundamental.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentFundamentalSplashBinding;
import com.ksbm.ontu.fundamental.model.fundamental_mcq_model.Fundamental_MCQ_Model;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Fundamental_Quiz_Model;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeModel;
import com.ksbm.ontu.fundamental.model.match_following_model.Match_Following_Model;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.FundamentalTouch_Fill;
import com.ksbm.ontu.fundamental.model.touch_the_word.TouchWordModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Fundamental_Quiz_Splash extends AppCompatActivity {
    FragmentFundamentalSplashBinding binding;
    SessionManager sessionManager;
    private String TagFundamental="TagFundamental";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_fundamental_splash);

        sessionManager = new SessionManager(this);

        //binding.tvWorkbookName.setText(sessionManager.getWorkbook().getWorkbookName());


        SpannableString content = new SpannableString(sessionManager.getWorkbook().getWorkbookName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.tvWorkbookName.setText(content);
        binding.tvTitle.setText(sessionManager.getWorkbook().getFundamental_name());

        binding.tvRewardCoins.setText(sessionManager.getWorkbook().getReward() + " Coins");

        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.ivLoggedUser);
        }
        //animate user
        ViewAnimator();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (Connectivity.isConnected(Fundamental_Quiz_Splash.this)) {
            String someTxt = sessionManager.getWorkbook().getQuiz_type();
            if (someTxt.equalsIgnoreCase("drag and drop")) {
                Log.d(TagFundamental,"1");
                getQuizTask(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("checkbox")) {
                Log.d(TagFundamental,"2");
                getQuizTask(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("objective")) {
                Log.d(TagFundamental,"3");
                getQuizMCQ(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("touch and fill or fill in the blanks")) {
                Log.d(TagFundamental,"4");
                getQuizTouchFill(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("match the following")) {
                Log.d(TagFundamental,"5");
                getQuizMatchTheFollowing(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("jumble rearrange")) {
                Log.d(TagFundamental,"6");
                getQuizJumbleArrange(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("touch the word")) {
                Log.d(TagFundamental,"7");
                getQuizTouchWord(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
            } else{
                Log.d(TagFundamental,"8 , "+sessionManager.getWorkbook().getQuiz_type());
                Toast.makeText(this, "No Condition Matched : "+sessionManager.getWorkbook().getQuiz_type(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Log.d(TagFundamental,"9");
            Toast.makeText(this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void ViewAnimator() {
        ViewAnimator
                .animate(binding.relLoginUser)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .thenAnimate(binding.relLoginUser)
                .scale(1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();

        ViewAnimator
                .animate(binding.ivOntuUser)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .thenAnimate(binding.ivOntuUser)
                .scale(1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();
    }

    @SuppressLint("CheckResult")
    private void getQuizTouchWord(String workbookId, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Splash.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFundamentalTouchWord(sessionManager.getLanguageid(), workbookId, sessionManager.getWorkbook().getFundamental_id(), sessionManager.getWorkbook().getToddle_id(),
                sessionManager.getWorkbook().getQuiz_type_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TouchWordModel>() {
                    @Override
                    public void onNext(TouchWordModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                //for 3 second screen preview
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (quiz_type.equalsIgnoreCase("touch the word")) {
                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, Fundamental_TouchTheWord.class);
                                            intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Splash.this, "Sorry", response.getMessage());

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
    private void getQuizJumbleArrange(String workbookId, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Splash.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFundamentalJumbleArrange(sessionManager.getLanguageid(), workbookId, sessionManager.getWorkbook().getFundamental_id(), sessionManager.getWorkbook().getToddle_id(),
                sessionManager.getWorkbook().getQuiz_type_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JumbleArrangeModel>() {
                    @Override
                    public void onNext(JumbleArrangeModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                //for 3 second screen preview
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (quiz_type.equalsIgnoreCase("jumble rearrange")) {

                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, Fundamental_Jumble_Arrange.class);
                                            //  intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Splash.this, "Sorry", response.getMessage());

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
    private void getQuizMatchTheFollowing(String workbookId, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Splash.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFundamentalMatchFollowing(sessionManager.getLanguageid(), workbookId, sessionManager.getWorkbook().getFundamental_id(), sessionManager.getWorkbook().getToddle_id(),
                sessionManager.getWorkbook().getQuiz_type_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Match_Following_Model>() {
                    @Override
                    public void onNext(Match_Following_Model response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                //for 3 second screen preview
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (quiz_type.equalsIgnoreCase("match the following")) {

                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, Fundamental_Quiz_Match_Following.class);
                                            intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Splash.this, "Sorry", response.getMessage());

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
    private void getQuizTouchFill(String workbookId, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Splash.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFundamentalQuizFill(sessionManager.getLanguageid(), workbookId, sessionManager.getWorkbook().getFundamental_id(), sessionManager.getWorkbook().getToddle_id(),
                sessionManager.getWorkbook().getQuiz_type_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FundamentalTouch_Fill>() {
                    @Override
                    public void onNext(FundamentalTouch_Fill response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                //for 3 second screen preview
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (quiz_type.equalsIgnoreCase("touch and fill or fill in the blanks")) {
                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, FundamentalQuizTouchFill.class);
                                            intent.putExtra("QuizDetails", response.getResponse());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Splash.this, "Sorry", response.getMessage());

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
    private void getQuizTask(String workbook_id, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Splash.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFundamentalQuiz(sessionManager.getLanguageid(), workbook_id, sessionManager.getWorkbook().getFundamental_id(), sessionManager.getWorkbook().getToddle_id(),
                sessionManager.getWorkbook().getQuiz_type_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Fundamental_Quiz_Model>() {
                    @Override
                    public void onNext(Fundamental_Quiz_Model response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                //for 3 second screen preview
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (quiz_type.equalsIgnoreCase("checkbox")) {
                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, Fundamental_Quiz_CheckBox.class);
                                            intent.putExtra("QuizDetails", (Parcelable) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            startActivity(intent);
                                            finish();
                                        } else if (quiz_type.equalsIgnoreCase("drag and drop")) {
                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, Fundamental_Quiz_Drag_Drop.class);
                                            intent.putExtra("QuizDetails", (Parcelable) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            startActivity(intent);
                                            finish();
                                        } else if (quiz_type.equalsIgnoreCase("objective")) {
                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, FundamentalQuizMCQ.class);
                                            intent.putExtra("QuizDetails", (Parcelable) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(Fundamental_Quiz_Splash.this, "Condition not matched", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Splash.this, "Sorry", response.getMessage());

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
    private void getQuizMCQ(String workbook_id, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Splash.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFundamentalQuizMCQ(sessionManager.getLanguageid(), workbook_id, sessionManager.getWorkbook().getFundamental_id(), sessionManager.getWorkbook().getToddle_id(),
                sessionManager.getWorkbook().getQuiz_type_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Fundamental_MCQ_Model>() {
                    @Override
                    public void onNext(Fundamental_MCQ_Model response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                //for 3 second screen preview
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (quiz_type.equalsIgnoreCase("objective")) {

                                            Intent intent = new Intent(Fundamental_Quiz_Splash.this, FundamentalQuizMCQ.class);
                                            intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Splash.this, "Sorry", response.getMessage());

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


}
