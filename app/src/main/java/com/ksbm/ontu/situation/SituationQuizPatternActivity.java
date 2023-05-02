package com.ksbm.ontu.situation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivitySituationQuizPatternBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.fragment.Fragment_Drag_Drop;
import com.ksbm.ontu.situation.fragment.Fragment_MatchFollowing;
import com.ksbm.ontu.situation.fragment.Fragment_Speak_Quiz;
import com.ksbm.ontu.situation.fragment.Fragment_Touch_Fill;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class SituationQuizPatternActivity extends AppCompatActivity {
    ActivitySituationQuizPatternBinding binding;
    SessionManager sessionManager;
    String situation_id, situation_name, sentence_id;
    Context context;
    int question_pos = 0;
    private List<SituationQuestionModel.QuestionList> questions_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(SituationQuizPatternActivity.this), SituationQuizPatternActivity.this);
        // setContentView(R.layout.activity_situation_quiz_pattern);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_situation_quiz_pattern);
        sessionManager = new SessionManager(this);
        context = this;

        try {
            SharedPreferences prefs = getSharedPreferences("ResumeQuize", MODE_PRIVATE);
            boolean value = prefs.getBoolean("value", false); //0 is the default value.
            String situation_id1 = prefs.getString("situation_id", "0"); //0 is the default value.
            String situation_name1 = prefs.getString("situation_name", ""); //0 is the default value.
            String sentence_id1 = prefs.getString("sentence_id", "0"); //0 is the default value.

            if(value)
            {
                if (getIntent() != null) {
                    situation_id = getIntent().getStringExtra("situation_id");
                    situation_name = getIntent().getStringExtra("situation_name");
                    sentence_id = sentence_id1;

                    binding.tvHeader.setText(situation_name);
                }

            }
            else
            {
                if (getIntent() != null) {
                    situation_id = getIntent().getStringExtra("situation_id");
                    situation_name = getIntent().getStringExtra("situation_name");
                    sentence_id = getIntent().getStringExtra("sentence_id");

                    binding.tvHeader.setText(situation_name);
                }
            }
        }
        catch (Exception e)
        {
            if (getIntent() != null) {
                situation_id = getIntent().getStringExtra("situation_id");
                situation_name = getIntent().getStringExtra("situation_name");
                sentence_id = getIntent().getStringExtra("sentence_id");

                binding.tvHeader.setText(situation_name);
            }
        }




        if (Connectivity.isConnected(context)) {
            getAllSentences();
        } else {
            Toast.makeText(context, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (question_pos != questions_list.size() - 1) {
                    question_pos++;
                    setQuiz();
                } else {

                    SharedPreferences.Editor editor = context.getSharedPreferences("ResumeQuize", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    Constant.NEXT_SENTENCES = true;
                    finish();
                }
            }
        });

        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question_pos > 0) {
                    question_pos--;
                    setQuiz();
                }
            }
        });

    }


    @SuppressLint("CheckResult")
    private void getAllSentences() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetSituationQuestion(sessionManager.getLanguageid(), situation_id, sentence_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SituationQuestionModel>() {
                    @Override
                    public void onNext(SituationQuestionModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            //  Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                questions_list = response.getSentence();
                                try {
                                    SharedPreferences prefs = getSharedPreferences("ResumeQuize", MODE_PRIVATE);
                                    boolean value = prefs.getBoolean("value", false); //0 is the default value.

                                    if(value)
                                    {
                                        setQuizResume();
                                    }
                                    else
                                    {
                                        setQuiz();
                                    }
                                }
                                catch (Exception e)
                                {
                                    setQuiz();
                                }


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

    public static String QuestionType="";


    private void setQuiz() {

        QuestionType = questions_list.get(question_pos).getQuizType();

        try {
            if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("drag and drop")) {

                Fragment_Drag_Drop fragment_home = new Fragment_Drag_Drop();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.situation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("jumble rearrange")) {
                Fragment_Drag_Drop fragment_home = new Fragment_Drag_Drop();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.situation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("speak only")) {
                Fragment_Speak_Quiz fragment_home = new Fragment_Speak_Quiz();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.situation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("match the following")) {
                Fragment_MatchFollowing fragment_home = new Fragment_MatchFollowing();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.situation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            } else if (questions_list.get(question_pos).getQuizType().equalsIgnoreCase("touch and fill") || questions_list.get(question_pos).getQuizType().equalsIgnoreCase("fill in the blanks")) {
                Fragment_Touch_Fill fragment_home = new Fragment_Touch_Fill();
                Bundle bundle = new Bundle();
                bundle.putSerializable("QuestionDetails", questions_list.get(question_pos));
                FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.situation_frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, e+"", Toast.LENGTH_SHORT).show();
        }
    }


    private void setQuizResume() {

        SharedPreferences prefs = getSharedPreferences("ResumeQuize", MODE_PRIVATE);
        String QuestionId = prefs.getString("QuestionId", "0");//"No name defined" is the default value.
        boolean value = prefs.getBoolean("value", false); //0 is the default value.

        int position=0;



        for (int i=0;i<questions_list.size();i++)
        {
            if (questions_list.get(i).getQuizType().equalsIgnoreCase(QuestionId))
            {
                position=i;
                QuestionType = questions_list.get(question_pos).getQuizType();
            }
        }

        if (QuestionId.equalsIgnoreCase("drag and drop") || QuestionId.equalsIgnoreCase("jumble rearrange")) {

            Fragment_Drag_Drop fragment_home = new Fragment_Drag_Drop();
            Bundle bundle = new Bundle();
            bundle.putSerializable("QuestionDetails", questions_list.get(position));
            FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
            ft_home.replace(R.id.situation_frame, fragment_home);
            ft_home.addToBackStack(null);
            ft_home.commit();
            fragment_home.setArguments(bundle);

        } else if (QuestionId.equalsIgnoreCase("speak only")) {
            Fragment_Speak_Quiz fragment_home = new Fragment_Speak_Quiz();
            Bundle bundle = new Bundle();
            bundle.putSerializable("QuestionDetails", questions_list.get(position));
            FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
            ft_home.replace(R.id.situation_frame, fragment_home);
            ft_home.addToBackStack(null);
            ft_home.commit();
            fragment_home.setArguments(bundle);

        } else if (QuestionId.equalsIgnoreCase("match the following")) {
            Fragment_MatchFollowing fragment_home = new Fragment_MatchFollowing();
            Bundle bundle = new Bundle();
            bundle.putSerializable("QuestionDetails", questions_list.get(position));
            FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
            ft_home.replace(R.id.situation_frame, fragment_home);
            ft_home.addToBackStack(null);
            ft_home.commit();
            fragment_home.setArguments(bundle);
        } else if (QuestionId.equalsIgnoreCase("touch and fill or fill in the blanks")) {
            Fragment_Touch_Fill fragment_home = new Fragment_Touch_Fill();
            Bundle bundle = new Bundle();
            bundle.putSerializable("QuestionDetails", questions_list.get(position));
            FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
            ft_home.replace(R.id.situation_frame, fragment_home);
            ft_home.addToBackStack(null);
            ft_home.commit();
            fragment_home.setArguments(bundle);
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
                finish();
            }
        });
    }
}