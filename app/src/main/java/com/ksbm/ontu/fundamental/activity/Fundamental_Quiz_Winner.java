package com.ksbm.ontu.fundamental.activity;

import static com.ksbm.ontu.utils.Constant.Passing_percent;

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
import com.ksbm.ontu.databinding.ActivityFundamentalQuizWinnerBinding;
import com.ksbm.ontu.fundamental.model.fundamental_mcq_model.Fundamental_MCQ_Model;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Fundamental_Quiz_Model;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeModel;
import com.ksbm.ontu.fundamental.model.match_following_model.Match_Following_Model;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.FundamentalTouch_Fill;
import com.ksbm.ontu.fundamental.model.touch_the_word.TouchWordModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Fundamental_Quiz_Winner extends AppCompatActivity {
    SessionManager sessionManager;
    ActivityFundamentalQuizWinnerBinding binding;
    // Workbook_Result_Data quizResult;
    String quizResult;
    Double overall_percent;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_fundamental__quiz__winner);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fundamental__quiz__winner);

        sessionManager = new SessionManager(this);

        SpannableString content = new SpannableString(sessionManager.getWorkbook().getWorkbookName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.tvWorkbookName.setText(content);
        binding.tvTitle.setText(sessionManager.getWorkbook().getFundamental_name());

        String arr[] = sessionManager.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];
        binding.tvUserName.setText(firstWord);


        if (getIntent() != null) {
            quizResult = getIntent().getStringExtra("QuizResult");

            binding.tvGotCoins.setText(quizResult);
            binding.textView4.setText(sessionManager.getWorkbook().getReward());
            binding.textView5.setText(quizResult);

            int ontu_coin = Integer.parseInt(sessionManager.getWorkbook().getReward()) -
                    Integer.parseInt(quizResult);

            binding.tvGotCoinsOntu.setText("" + ontu_coin);

            //get customer coin percent
            double amount = Double.parseDouble(quizResult);
            overall_percent = (amount / Double.parseDouble(sessionManager.getWorkbook().getReward())) * 100;

            Log.e("overall_percent", "" + overall_percent);
            if (overall_percent.intValue() >= Passing_percent) {
                binding.tvUserWin.setVisibility(View.VISIBLE);
                binding.tvStatus.setText("Next Quiz!");
            } else {
                if (overall_percent.intValue() == Passing_percent) {
                    binding.tvOntuWin.setText("Tie");
                    binding.tvOntuWin.setVisibility(View.VISIBLE);
                    binding.tvStatus.setText("Retake Quiz!");
                } else {
                    binding.tvOntuWin.setVisibility(View.VISIBLE);
                    binding.tvStatus.setText("Retake Quiz!");
                }

            }
        }

        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.ivLoggedUser);
        }
        //animate user
        ViewAnimator();
        Utils.playMusic(R.raw.winning_sound, Fundamental_Quiz_Winner.this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvSeeVideoAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenVideoPlayer(false);
            }
        });


        binding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (overall_percent.intValue() >= Passing_percent) {
                    finish();
                } else {
                    if (Connectivity.isConnected(Fundamental_Quiz_Winner.this)) {
                        if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("drag and drop")) {
                            getQuizTask(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("checkbox")) {
                            getQuizTask(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("objective")) {
                            getQuizMCQ(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("touch and fill or fill in the blanks")) {
                            getQuizTouchFill(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("match the following")) {
                            getQuizMatchTheFollowing(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("jumble rearrange")) {
                            getQuizJumbleArrange(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else if (sessionManager.getWorkbook().getQuiz_type().equalsIgnoreCase("touch the word")) {
                            getQuizTouchWord(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
                        } else {

                        }

                    } else {
                        Toast.makeText(Fundamental_Quiz_Winner.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


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

    public void OpenVideoPlayer(boolean QuizPlay) {
        Intent intent = new Intent(Fundamental_Quiz_Winner.this, VideoPlayer.class);
        intent.putExtra("QuizPlay", QuizPlay);
        if (sessionManager.getWorkbook() != null && !sessionManager.getWorkbook().getVideolink().equalsIgnoreCase("")) {
            intent.putExtra("FilePath", sessionManager.getWorkbook().getVideolink());
            intent.putExtra("FilePathOther", true);
            startActivity(intent);
        } else if (sessionManager.getWorkbook().getBanner_file() != null && !sessionManager.getWorkbook().getBanner_file().equalsIgnoreCase("")) {
            intent.putExtra("FilePath", sessionManager.getWorkbook().getBanner_file());
            intent.putExtra("FilePathOther", false);
            startActivity(intent);
        } else {
            Toast.makeText(this, "No Video Found", Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressLint("CheckResult")
    private void getQuizTask(String workbook_id, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Winner.this, R.style.MyGravity);
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
                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, Fundamental_Quiz_CheckBox.class);
                                            intent.putExtra("QuizDetails", (Parcelable) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            startActivity(intent);
                                            finish();
                                        } else if (quiz_type.equalsIgnoreCase("drag and drop")) {

                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, Fundamental_Quiz_Drag_Drop.class);
                                            intent.putExtra("QuizDetails", (Parcelable) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }, 4000);


                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Winner.this, "Sorry", response.getMessage());

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
    private void getQuizTouchWord(String workbookId, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Winner.this, R.style.MyGravity);
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

                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, Fundamental_TouchTheWord.class);
                                            intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Winner.this, "Sorry", response.getMessage());

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
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Winner.this, R.style.MyGravity);
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

                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, Fundamental_Jumble_Arrange.class);
                                            //  intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Winner.this, "Sorry", response.getMessage());

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
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Winner.this, R.style.MyGravity);
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

                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, Fundamental_Quiz_Match_Following.class);
                                            intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Winner.this, "Sorry", response.getMessage());

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
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Winner.this, R.style.MyGravity);
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

                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, FundamentalQuizTouchFill.class);
                                            intent.putExtra("QuizDetails", response.getResponse());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Winner.this, "Sorry", response.getMessage());

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
    private void getQuizMCQ(String workbook_id, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Quiz_Winner.this, R.style.MyGravity);
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

                                            Intent intent = new Intent(Fundamental_Quiz_Winner.this, FundamentalQuizMCQ.class);
                                            intent.putParcelableArrayListExtra("QuizDetails", (ArrayList<? extends Parcelable>) response.getResponse().getQuizData());
                                            intent.putExtra("quiz_id", response.getResponse().getQuizId());
                                            intent.putExtra("quiz_name", response.getResponse().getQuizName());
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 4000);

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Quiz_Winner.this, "Sorry", response.getMessage());

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
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }
}