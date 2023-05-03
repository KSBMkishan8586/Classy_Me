package com.ksbm.ontu.fundamental.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizMCQ;
import com.ksbm.ontu.fundamental.model.fundamental_mcq_model.Fundamental_MCQ_Data;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;

public class SliderMCQAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<Fundamental_MCQ_Data> quizDetails;
    SessionManager sessionManager;
    String quiz_id;

    public SliderMCQAdapter(Context context, List<Fundamental_MCQ_Data> listarray1, String quiz_id) {
        this.context = context;
        this.quizDetails = listarray1;
        this.quiz_id = quiz_id;
        sessionManager= new SessionManager(context);
    }


    @Override
    public int getCount() {
        return quizDetails.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int quiz_position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_mcq_quiz, container, false);

        TextView tvQuestion = view.findViewById(R.id.tv_question);
        RadioButton radio1 = view.findViewById(R.id.radio1);
        RadioButton radio2 = view.findViewById(R.id.radio2);
        RadioButton radio4 = view.findViewById(R.id.radio4);
        RadioButton radio3 = view.findViewById(R.id.radio3);
        RadioButton radio5 = view.findViewById(R.id.radio5);
        RadioGroup radioGroupQuiz = view.findViewById(R.id.radio_group_quiz);
        ImageView ivIQuiz = view.findViewById(R.id.iv_i_quiz);

        tvQuestion.setText("" + (quiz_position + 1) + ". " + quizDetails.get(quiz_position).getQuizQuestion());

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

        if (quizDetails.get(quiz_position).isChecked_radio() != null) {
            if (quizDetails.get(quiz_position).isChecked_radio().equalsIgnoreCase("option_1")) {
                radio1.setChecked(true);
                CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio1, quiz_position, ivIQuiz);
            } else if (quizDetails.get(quiz_position).isChecked_radio().equalsIgnoreCase("option_2")) {
                radio2.setChecked(true);
                CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio2, quiz_position, ivIQuiz);
            } else if (quizDetails.get(quiz_position).isChecked_radio().equalsIgnoreCase("option_3")) {
                radio3.setChecked(true);
                CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio3, quiz_position, ivIQuiz);
            } else if (quizDetails.get(quiz_position).isChecked_radio().equalsIgnoreCase("option_4")) {
                radio4.setChecked(true);
                CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio4, quiz_position, ivIQuiz);
            } else if (quizDetails.get(quiz_position).isChecked_radio().equalsIgnoreCase("option_5")) {
                radio5.setChecked(true);
                CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio5, quiz_position, ivIQuiz);
            }
        }

        if (quizDetails.get(quiz_position).isChecked_radio_button()) {
            for (int i = 0; i < radioGroupQuiz.getChildCount(); i++) {
                radioGroupQuiz.getChildAt(i).setEnabled(false);
            }
        }

        //**********
        radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);

                if (!quizDetails.get(quiz_position).isChecked_radio_button()) {
                    quizDetails.get(quiz_position).setChecked_radio_button(true);

                    if (checkedId == R.id.radio1) {
                        quizDetails.get(quiz_position).setChecked_radio("option_1");
                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio1, quiz_position, ivIQuiz);
                    } else if (checkedId == R.id.radio2) {
                        quizDetails.get(quiz_position).setChecked_radio("option_2");
                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio2, quiz_position, ivIQuiz);
                    } else if (checkedId == R.id.radio3) {
                        quizDetails.get(quiz_position).setChecked_radio("option_3");
                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio3, quiz_position, ivIQuiz);
                    } else if (checkedId == R.id.radio4) {
                        quizDetails.get(quiz_position).setChecked_radio("option_4");
                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio4, quiz_position, ivIQuiz);
                    } else if (checkedId == R.id.radio5) {
                        quizDetails.get(quiz_position).setChecked_radio("option_5");
                        CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), radio5, quiz_position, ivIQuiz);
                    }
                }


                for (int i = 0; i <radioGroupQuiz.getChildCount(); i++) {
                    radioGroupQuiz.getChildAt(i).setEnabled(false);
                }

            }
        });

        ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (quizDetails.get(quiz_position).getDeviRightAnswer() != null) {
                String msg = "Correct Answer is \n" + "'" + quizDetails.get(quiz_position).getDeviRightAnswer() + "'";
                SweetAlt.OpenFreeCoinDialog(context, msg);
//                }

            }
        });


        container.addView(view);
        return view;
    }

    private void CheckRightAns(String checked_radio, RadioButton radio, int quiz_position, ImageView ivIQuiz) {
        if (checked_radio != null) {
            if (checked_radio.equalsIgnoreCase(quizDetails.get(quiz_position).getRightAnswer())) {
                //Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                radio.setTextColor(context.getResources().getColor(R.color.green));
                ivIQuiz.setVisibility(View.GONE);
                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(quiz_position).getReward(), "1", quizDetails.get(quiz_position).getQuiz_question_id());//1=right ans
                ((FundamentalQuizMCQ)context).CoinAnimated(true, quizDetails.get(quiz_position).getReward() );

            } else {
                // Toast.makeText(context, "wrong", Toast.LENGTH_SHORT).show();
                radio.setTextColor(context.getResources().getColor(R.color.red));
                ivIQuiz.setVisibility(View.VISIBLE);

                CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, quizDetails.get(quiz_position).getReward(), "0", quizDetails.get(quiz_position).getQuiz_question_id());//0=wrong ans
                ((FundamentalQuizMCQ)context).CoinAnimated(false, quizDetails.get(quiz_position).getReward() );


            }
        }
    }

    public boolean CheckRadioStatus(int pos){
        if (quizDetails.get(pos).isChecked_radio_button()) {
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((ScrollView) object);
    }


}
