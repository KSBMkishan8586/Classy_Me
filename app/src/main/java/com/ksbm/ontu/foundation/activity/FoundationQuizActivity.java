package com.ksbm.ontu.foundation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityFoundationQuizBinding;
import com.ksbm.ontu.foundation.fragment.Foundation_CheckBox;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop_Image;
import com.ksbm.ontu.foundation.fragment.Foundation_Match_ImageToText;
import com.ksbm.ontu.foundation.fragment.Foundation_Match_Text;
import com.ksbm.ontu.foundation.fragment.Foundation_Match_TextToImage;
import com.ksbm.ontu.foundation.fragment.Foundation_Objective_Image;
import com.ksbm.ontu.foundation.fragment.Foundation_Objective_Text;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Fill;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Word_Text;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
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

public class FoundationQuizActivity extends AppCompatActivity {
    public static ActivityFoundationQuizBinding binding;
    String foundation_id, foundation_name;
    SessionManager sessionManager;
    Context context;
    ImageView imageView;
    int question_pos = 0;
    public static int userTotalReward = 0;
    private List<FoundationQuizModel.FoundationQuizResponse> questions_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_foundation_quiz);
        imageView = (ImageView) findViewById(R.id.iv_coin);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(FoundationQuizActivity.this), FoundationQuizActivity.this);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_foundation_quiz);
        sessionManager = new SessionManager(this);
        context = this;
        userTotalReward = 0;
        System.out.println("Missh " + getIntent().getStringExtra("foundation_name"));

        if (getIntent() != null) {
            foundation_id = getIntent().getStringExtra("foundation_id");
            foundation_name = getIntent().getStringExtra("foundation_name");

            binding.tvHeader.setText(foundation_name);
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(context)) {
            getAllQuiz();
        } else {
            Toast.makeText(context, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.relativeLayout, getWindow().getContext());
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, "ques:- "+question_pos+", list:- "+questions_list.size()+",status:- "+questions_list.get(question_pos).isAttemptQuiz(), Toast.LENGTH_SHORT).show();

                if (binding.tvHeader.getText().toString().equalsIgnoreCase("Directions") && question_pos == questions_list.size() - 1) {
                    Intent intent = new Intent(context, DirectionSwipeNewActivity.class);
                    intent.putExtra("ComeFrom", "Directions");
                    //open winner page
                    Speach_Record_Activity.stop_recording();
                    int overallReward = 0;
                    for (int i = 0; i < questions_list.size(); i++) {
                        overallReward = overallReward + Integer.parseInt(questions_list.get(i).getReward());
                        // userWinReward = userWinReward + Integer.parseInt(questions_list.get(i).getUserTotalReward());
                    }
                    intent.putExtra("QuizResult", String.valueOf(userTotalReward));
                    intent.putExtra("QuizReward", String.valueOf(overallReward));
                    intent.putExtra("foundation_name", foundation_name);
                    intent.putExtra("foundation_id", foundation_id);
                    startActivity(intent);
                    finish();
                } else {
                    if (question_pos != questions_list.size() - 1) {

                        if (questions_list.get(question_pos).isAttemptQuiz()) {
                            question_pos++;
                            setQuiz();
                        } else {
                            Toast.makeText(context, "Please attempt quizes", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (questions_list.get(question_pos).isAttemptQuiz()) {
                            //open winner page
                            Speach_Record_Activity.stop_recording();
                            int overallReward = 0;
                            for (int i = 0; i < questions_list.size(); i++) {
                                overallReward = overallReward + Integer.parseInt(questions_list.get(i).getReward());
                                // userWinReward = userWinReward + Integer.parseInt(questions_list.get(i).getUserTotalReward());
                            }
                            Intent intent = new Intent(context, FoundationWinnerActivity.class);
                            intent.putExtra("QuizResult", String.valueOf(userTotalReward));
                            intent.putExtra("QuizReward", String.valueOf(overallReward));
                            intent.putExtra("foundation_name", foundation_name);
                            intent.putExtra("foundation_id", foundation_id);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "Please attempt quiz", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question_pos > 0) {
                    question_pos--;
                    setQuiz();
                    Speach_Record_Activity.stop_recording();
                }
            }
        });

    }

    @SuppressLint("CheckResult")
    private void getAllQuiz() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFoundationQuiz(sessionManager.getLanguageid(), sessionManager.getUser().getUserid(), foundation_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FoundationQuizModel>() {
                    @Override
                    public void onNext(FoundationQuizModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            //  Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                questions_list = response.getResponse();
                                setQuiz();

                            } else {
                                SweetAlt.showError(context, "Sorry", response.getMessage());
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

    private void setQuiz() {

        if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("drag and drop")) {

            if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_text")) {
                Foundation_Drag_Drop fragment_home = new Foundation_Drag_Drop();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("image_to_text")) {
                Foundation_Drag_Drop_Image fragment_home = new Foundation_Drag_Drop_Image();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            }

        } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("touch the word")) {
            if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_text")) {
                Foundation_Touch_Word_Text fragment_home = new Foundation_Touch_Word_Text();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "text_to_text");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("image_to_text")) {
                Foundation_Touch_Word_Text fragment_home = new Foundation_Touch_Word_Text();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "image_to_text");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            }

        } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("checkbox")) {
            if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_text")) {
                Foundation_CheckBox fragment_home = new Foundation_CheckBox();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("image_to_text")) {

                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//                SweetAlt.normalDialog(this, "Thanks for Complete", "This Task is end", false, false, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        finish();
//                    }
//                });
            }

        } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("touch and fill") || questions_list.get(question_pos).getQuizType().equalsIgnoreCase("fill in the blanks")) {
            if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("mix")) {
                Foundation_Touch_Fill fragment_home = new Foundation_Touch_Fill();
                Bundle bundle = new Bundle();

                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("image_to_text")) {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//                SweetAlt.normalDialog(this, "Thanks for Complete", "This Task is end", false, false, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        finish();
//                    }
//                });
            } else {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//                SweetAlt.normalDialog(this, "Thanks for Complete", "This Task is end", false, false, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        finish();
//                    }
//                });
            }

        } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("objective")) {
            if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_text")) {
                Foundation_Objective_Text fragment_home = new Foundation_Objective_Text();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "text_to_text");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("image_to_text")) {
                Foundation_Objective_Text fragment_home = new Foundation_Objective_Text();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "image_to_text");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_image")) {
                Foundation_Objective_Image fragment_home = new Foundation_Objective_Image();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "text_to_image");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//                SweetAlt.normalDialog(this, "Thanks for Complete", "This Task is end", false, false, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        finish();
//                    }
//                });
            }

        } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("Match the following")) {
            if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_text")) {
                Foundation_Match_Text fragment_home = new Foundation_Match_Text();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "text_to_text");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("image_to_text")) {
                Foundation_Match_ImageToText fragment_home = new Foundation_Match_ImageToText();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "image_to_text");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("text_to_image")) {
                Foundation_Match_TextToImage fragment_home = new Foundation_Match_TextToImage();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "text_to_image");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else if (questions_list.get(question_pos).getSubPatternType().equalsIgnoreCase("mix")) {
                Foundation_Match_ImageToText fragment_home = new Foundation_Match_ImageToText();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                bundle.putString("sub_pattern_type", "mix");
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.foundation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//                SweetAlt.normalDialog(this, "Thanks for Complete", "This Task is end", false, false, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        finish();
//                    }
//                });
            }

        } else {
            Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
//            SweetAlt.normalDialog(this, "Thanks for Complete", "This Task is end", false, false, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
//                @Override
//                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                    sweetAlertDialog.dismiss();
//                    finish();
//                }
//            });
        }
    }


    @Override
    public void onBackPressed() {
        String title = getResources().getString(R.string.app_name);
        String msg = "Are you sure, you want close quiz";
        SweetAlt.normalDialog(this, title, msg, true, true, "No", "Yes", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                Speach_Record_Activity.stop_recording();
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        Speach_Record_Activity.stop_recording();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Speach_Record_Activity.stop_recording();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Speach_Record_Activity.stop_recording();
        super.onDestroy();
    }
}
