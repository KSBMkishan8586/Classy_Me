package com.ksbm.ontu.fundamental.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.ActivityFundamentalJumbleArrangeBinding;
import com.ksbm.ontu.fundamental.adapter.FundamentalJumbleWord_Adapter;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeModel;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeQuiz;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeQuizWord;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Fundamental_Jumble_Arrange extends AppCompatActivity {
    ActivityFundamentalJumbleArrangeBinding binding;
    SessionManager sessionManager;
    String quiz_id, quiz_name;
    // int quiz_position=0;
    List<JumbleArrangeQuiz> quizDetails = new ArrayList<>();
    List<JumbleArrangeQuizWord> wordQuizLists = new ArrayList<>();
    TextView result;
    RelativeLayout resultView;
    int position = 0;
    int word_position = 0;
    // int item_count = 0;
    View view;
    int got_total_reward = 0;
    int got_ontu_total_reward = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fundamental_jumble_arrange);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_fundamental_jumble_arrange);

        sessionManager = new SessionManager(this);

        result = (TextView) findViewById(R.id.result);
        resultView = findViewById(R.id.resultView);

        String arr[] = sessionManager.getUser().getFullname().split(" ", 2);
        String firstWord = arr[0];

        binding.relToolbar.tvLoggedUerName.setText(firstWord);
        binding.tvWorkbookname.setText(sessionManager.getWorkbook().getWorkbookName());
        binding.relToolbar.tvCoin.setText(sessionManager.getWorkbook().getReward());
        binding.tvHeader.setText(sessionManager.getWorkbook().getFundamental_name());

        if (sessionManager.getUser().getProfilePic() != null && !sessionManager.getUser().getProfilePic().isEmpty()) {
            Glide.with(this)
                    .load(sessionManager.getUser().getProfilePic())
                    .error(R.drawable.ankit)
                    .placeholder(R.drawable.ankit)
                    .into(binding.relToolbar.ivLoggedUser);
        }

        if (getIntent() != null) {
//            quizDetails = getIntent().getParcelableArrayListExtra("QuizDetails");
            quiz_id = getIntent().getStringExtra("quiz_id");
            quiz_name = getIntent().getStringExtra("quiz_name");

            binding.tvQuizName.setText(quiz_name);
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getQuizJumbleArrange(sessionManager.getWorkbook().getWorkbookId(), sessionManager.getWorkbook().getQuiz_type());
        setOnListner();

        if (position == 0) {
            binding.tvPrevious.setVisibility(View.INVISIBLE);
        } else {
            binding.tvPrevious.setVisibility(View.VISIBLE);
        }

        binding.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position > 0) {
                    position--;
                    getTaskList();
                }
            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quizDetails.get(position).isQuizAttend()) {
                    if (position != quizDetails.size() - 1) {
                        position++;
                        getTaskList();
                    } else {
                        Intent intent = new Intent(Fundamental_Jumble_Arrange.this, Fundamental_Quiz_Winner.class);
                        intent.putExtra("QuizResult", String.valueOf(got_total_reward));
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(Fundamental_Jumble_Arrange.this, "Please check Answer first!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.tvCheckAns.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (!result.getText().toString().isEmpty()) {

                    StringBuffer sb = new StringBuffer(result.getText().toString());
                    sb.deleteCharAt(sb.length() - 1);

                    if (quizDetails.get(position).getRightAnswer().equalsIgnoreCase(sb.toString())) {
                        //Toast.makeText(Fundamental_Quiz_Start.this, "Right", Toast.LENGTH_SHORT).show();
                        result.setTextColor(getResources().getColor(R.color.green));
                        binding.ivIQuiz.setVisibility(View.GONE);
                        quizDetails.get(position).setQuizRight(true);

                        got_total_reward = got_total_reward + Integer.parseInt(quizDetails.get(position).getReward());
                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(position).getReward(), "1", quizDetails.get(position).getQuiz_question_id());//1=right ans
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvUserCoin.setText("" + got_total_reward);
                        Utils.playMusic(R.raw.coin_sound, Fundamental_Jumble_Arrange.this);
                    } else {
                        result.setTextColor(getResources().getColor(R.color.red));
                        binding.ivIQuiz.setVisibility(View.VISIBLE);
                        showCorrectDialog();
                        quizDetails.get(position).setQuizRight(false);

                        got_ontu_total_reward = got_ontu_total_reward + Integer.parseInt(quizDetails.get(position).getReward());

                        CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(position).getReward(), "0", quizDetails.get(position).getQuiz_question_id());//0= wrong answer
                        binding.relToolbar.ivOntuSideCoin.setVisibility(View.VISIBLE);
                        binding.relToolbar.ivUsersideCoinImg.setVisibility(View.INVISIBLE);
                        binding.relToolbar.tvOntuCoin.setText("" + got_ontu_total_reward);
                        Utils.playMusic(R.raw.wrong_selected, Fundamental_Jumble_Arrange.this);
                    }

                    quizDetails.get(position).setQuizAttend(true);

                    int remain_coin = Integer.parseInt(binding.relToolbar.tvCoin.getText().toString()) -
                            Integer.parseInt(quizDetails.get(position).getReward());
                    binding.relToolbar.tvCoin.setText("" + remain_coin);


                }
            }
        });

        binding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCorrectDialog();

            }
        });
    }

    private void showCorrectDialog() {
        String msg = "Correct Answer is '" + quizDetails.get(position).getRightAnswer() + "'";
        SweetAlt.OpenFreeCoinDialog(this, msg);
    }

    private void getTaskList() {
//        if (position == 0) {
//            binding.tvPrevious.setVisibility(View.INVISIBLE);
//        } else {
//            binding.tvPrevious.setVisibility(View.VISIBLE);
//        }

        if (quizDetails.get(position).isQuizAttend()) {
            if (quizDetails.get(position).isQuizRight()) {
                binding.ivIQuiz.setVisibility(View.GONE);
            } else {
                binding.ivIQuiz.setVisibility(View.VISIBLE);
            }
        } else {
            binding.ivIQuiz.setVisibility(View.GONE);
        }


        result.setText("");
        result.setTextColor(getResources().getColor(R.color.black));

        binding.tvQuizQuestion.setText(quizDetails.get(position).getQuizQuestion());
        Log.e("wordQuizLists", "" + quizDetails.get(position).getQuizWords().size());
        Log.e("quiz_details_pos", "" + position);

        if (quizDetails.get(position).getQuizWords() != null && quizDetails.get(position).getQuizWords().size() > 0) {
            wordQuizLists.clear();
            wordQuizLists = quizDetails.get(position).getQuizWords();

            FundamentalJumbleWord_Adapter friendsAdapter = new FundamentalJumbleWord_Adapter(wordQuizLists, Fundamental_Jumble_Arrange.this);
            binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
            friendsAdapter.notifyDataSetChanged();
        }

    }


    private void setOnListner() {
        resultView.setOnDragListener(mydragListener);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.ivIButton.setVisibility(View.GONE);
        binding.ivIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Please drag the words in below box and arrange correct sentence.";
                SweetAlt.OpenFreeCoinDialog(Fundamental_Jumble_Arrange.this, msg);
            }
        });
    }

    View.OnDragListener mydragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    //  item_count++;

//                    if (item_count == wordQuizLists.size()) {
//                        binding.tvSubmit.setVisibility(View.VISIBLE);
//                    }

                    result.append(wordQuizLists.get(word_position).getWords() + " ");
                    view.setVisibility(View.INVISIBLE);

                    break;
            }

            return true;
        }
    };


    public void DragTextItem(View v, int position) {
        this.word_position = position;
        this.view = v;

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
    }

    @SuppressLint("CheckResult")
    private void getQuizJumbleArrange(String workbookId, String quiz_type) {
        final ProgressDialog progressDialog = new ProgressDialog(Fundamental_Jumble_Arrange.this, R.style.MyGravity);
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

                                if (quiz_type.equalsIgnoreCase("jumble rearrange")) {
                                    quizDetails = response.getResponse().getQuizData();
                                    quiz_id = response.getResponse().getQuizId();
                                    quiz_name = response.getResponse().getQuizName();

                                    getTaskList();

                                }

                            } else {
                                SweetAlt.showErrorMessage(Fundamental_Jumble_Arrange.this, "Sorry", response.getMessage());

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