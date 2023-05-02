package com.ksbm.ontu.practice_offline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityMemoryMapQuizBinding;
import com.ksbm.ontu.practice_offline.adapter.SliderMemoryQuizAdapter;
import com.ksbm.ontu.practice_offline.model.MemoryMapQuizModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class MemoryMapQuiz extends AppCompatActivity {
    ActivityMemoryMapQuizBinding binding;
    int quiz_position = 0;
    SessionManager sessionManager;
    String stage_id,level_id;
    SliderMemoryQuizAdapter sliderAdapter;
    List<MemoryMapQuizModel.MemoryMapQuizResponse> quizDetails = new ArrayList<>();
    boolean isSubmit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_memory_map_quiz);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  setContentView(R.layout.activity_personality_quiz_dialog);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory_map_quiz);

        sessionManager = new SessionManager(this);

        if (getIntent() != null) {
            stage_id = getIntent().getStringExtra("stage_id");
            level_id=getIntent().getStringExtra("level_id");
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        GetMemoryMapQuiz();

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0,0,0,0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        binding.tvNext.setText("Submit");
        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (sliderAdapter!=null){
                    if (sliderAdapter.CheckRadioStatus(quiz_position)){
                        if (binding.tvNext.getText().toString().equalsIgnoreCase("Next")){
                            binding.ivIQuiz.setVisibility(View.GONE);
                            binding.tvNext.setText("Submit");
                            if (quiz_position != quizDetails.size() - 1) {
                                quiz_position++;
                                binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1);

                                binding.tvQue.setText("" + (quiz_position + 1) + " of " + quizDetails.size());
                            }else {
                                finish();

                            }

                        }else {
                            sliderAdapter.CheckRightAns(quiz_position, binding.ivIQuiz);
                            binding.tvNext.setText("Next");
                        }

                    }else {
                        Toast.makeText(MemoryMapQuiz.this, "Please select option", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.e("current_page++", ""+quiz_position);
            }
        });


        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n"+ "'" +quizDetails.get(quiz_position).getRightAnswer() +"'";
                SweetAlt.OpenFreeCoinDialog(MemoryMapQuiz.this, msg);

            }
        });

    }

    @SuppressLint("CheckResult")
    private void GetMemoryMapQuiz() {
        final ProgressDialog progressDialog = new ProgressDialog(MemoryMapQuiz.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetMemoryMapQuiz(sessionManager.getLanguageid(), stage_id,level_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MemoryMapQuizModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(MemoryMapQuizModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                if (response.getResponse() != null && response.getResponse().size() > 0) {
                                    quizDetails = response.getResponse();

                                    sliderAdapter = new SliderMemoryQuizAdapter(MemoryMapQuiz.this, quizDetails, stage_id);
                                    binding.sliderEditor.setAdapter(sliderAdapter);
                                    binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
                                    binding.sliderEditor.setCurrentItem(0);

                                    binding.tvQue.setText("" + (quiz_position + 1) + " of " + quizDetails.size());
                                }

                            } else {
                                Toast.makeText(MemoryMapQuiz.this, "No memory map quiz found", Toast.LENGTH_SHORT).show();
                                finish();
                                // SweetAlt.showErrorMessage(MemoryMapQuiz.this, "Sorry", response.getMessage());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }//

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
                        // progressDialog.dismiss();
                    }
                });

    }


}