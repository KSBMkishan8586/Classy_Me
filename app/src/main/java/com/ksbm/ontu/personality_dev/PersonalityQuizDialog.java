package com.ksbm.ontu.personality_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityPersonalityQuizDialogBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.personality_dev.model.PersonalityMCQTest;
import com.ksbm.ontu.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class PersonalityQuizDialog extends AppCompatActivity {
    ActivityPersonalityQuizDialogBinding binding;
    List<PersonalityMCQTest.PersonalityMCQResponse> quizDetails= new ArrayList<>();
    int quiz_position= 0;
    TextView tvQuestion;
    RadioButton radio1,radio2, radio4, radio3, radio5;
    RadioGroup radioGroupQuiz;
    String selecteed_ans;
    boolean radio_check= false;
    SessionManager sessionManager;
    private String Category_Id, video_id;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      //  setContentView(R.layout.activity_personality_quiz_dialog);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personality_quiz_dialog);

        sessionManager= new SessionManager(this);

        if (getIntent()!= null){
            quizDetails = getIntent().getParcelableArrayListExtra("QuizData");
            Category_Id= getIntent().getStringExtra("Category_Id");
            video_id= getIntent().getStringExtra("video_id");
        }

         tvQuestion = findViewById(R.id.tv_question);
         radio1 = findViewById(R.id.radio1);
         radio2 = findViewById(R.id.radio2);
         radio4 = findViewById(R.id.radio4);
         radio3 = findViewById(R.id.radio3);
         radio5 = findViewById(R.id.radio5);
         radioGroupQuiz = findViewById(R.id.radio_group_quiz);

        setQuiz();

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitPersonalityQuiz(quizDetails.get(quiz_position).getId(), selecteed_ans );
                if (quiz_position != quizDetails.size()-1){
                    quiz_position++;
                    setQuiz();
                    radio_check= false;
                }else {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }

            }
        });

        radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);

                if (!radio_check){
                    if (rb.isChecked()){
                        selecteed_ans  = rb.getText().toString();
                    }
                    radio_check= true;
                }


               // if (!quizDetails.get(quiz_position).isChecked_radio_button()) {
                   // quizDetails.get(quiz_position).setChecked_radio_button(true);

//                    if (checkedId == R.id.radio1) {
//                        quizDetails.get(quiz_position).setChecked_radio("option_1");
//                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio1, quiz_position, ivIQuiz);
//                    } else if (checkedId == R.id.radio2) {
//                        quizDetails.get(quiz_position).setChecked_radio("option_2");
//                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio2, quiz_position, ivIQuiz);
//                    } else if (checkedId == R.id.radio3) {
//                        quizDetails.get(quiz_position).setChecked_radio("option_3");
//                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio3, quiz_position, ivIQuiz);
//                    } else if (checkedId == R.id.radio4) {
//                        quizDetails.get(quiz_position).setChecked_radio("option_4");
//                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio4, quiz_position, ivIQuiz);
//                    } else if (checkedId == R.id.radio5) {
//                        quizDetails.get(quiz_position).setChecked_radio("option_5");
//                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio5, quiz_position, ivIQuiz);
//                    }
              //  }


            }
        });
    }


    @SuppressLint("CheckResult")
    private void SubmitPersonalityQuiz(String ques_id, String selecteed_ans) {
//        final ProgressDialog progressDialog = new ProgressDialog(PersonalityVideoPlayer.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.SubmitPersonalityQuiz(sessionManager.getUser().getUserid(),ques_id, selecteed_ans, video_id, Category_Id, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                          //  progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {


                            } else {
                                // SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                           // progressDialog.dismiss();
                        }//

                        //  binding.swipeToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                      //  progressDialog.dismiss();
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
                       // progressDialog.dismiss();
                    }
                });

    }


    @SuppressLint("SetTextI18n")
    private void setQuiz() {

        if (radio_check){
            radioGroupQuiz.clearCheck();

        }
        selecteed_ans= "";

        binding.tvQue.setText("" + (quiz_position + 1)  + " of " + quizDetails.size());
        tvQuestion.setText("" + (quiz_position + 1) + ". " + quizDetails.get(quiz_position).getQuizQuestion());

        Glide.with(getApplicationContext())
                .load(quizDetails.get(quiz_position).getQuestion_image())
                .into(binding.img);

        radio1.setText(quizDetails.get(quiz_position).getOption1());
        radio2.setText(quizDetails.get(quiz_position).getOption2());

        if (!quizDetails.get(quiz_position).getOption3().isEmpty() && quizDetails.get(quiz_position).getOption3() != null) {
            radio3.setText(quizDetails.get(quiz_position).getOption3());
            radio3.setVisibility(View.VISIBLE);
        } else {
            radio3.setVisibility(View.INVISIBLE);
        }

        if (!quizDetails.get(quiz_position).getOption4().isEmpty() && quizDetails.get(quiz_position).getOption4() != null) {
            radio4.setText(quizDetails.get(quiz_position).getOption4());
            radio4.setVisibility(View.VISIBLE);
        } else {
            radio4.setVisibility(View.INVISIBLE);
        }

        if (!quizDetails.get(quiz_position).getOption5().isEmpty() && quizDetails.get(quiz_position).getOption5() != null) {
            radio5.setText(quizDetails.get(quiz_position).getOption5());
            radio5.setVisibility(View.VISIBLE);
        } else {
            radio5.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}