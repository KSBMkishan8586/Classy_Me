package com.ksbm.ontu.practice_offline.adapter;

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

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.practice_offline.model.MemoryMapQuizModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;

public class SliderMemoryQuizAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<MemoryMapQuizModel.MemoryMapQuizResponse> quizDetails;
    SessionManager sessionManager;
    String stage_id;
    String radio_select_ans;
    RadioButton select_radio;

    ImageView ivIQuiz;


    public SliderMemoryQuizAdapter(Context context, List<MemoryMapQuizModel.MemoryMapQuizResponse> listarray1, String stage_id) {
        this.context = context;
        this.quizDetails = listarray1;
        this.stage_id = stage_id;
        sessionManager = new SessionManager(context);
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
        ivIQuiz = view.findViewById(R.id.iv_i_quiz);
        ImageView img = view.findViewById(R.id.img);


        if (quizDetails.get(quiz_position).getQuestion_image().equalsIgnoreCase("") && quizDetails.get(quiz_position).getQuestion_image().equalsIgnoreCase("null")) {
            img.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(quizDetails.get(quiz_position).getQuestion_image())
                    .into(img);
        }

        tvQuestion.setText("" + (quiz_position + 1) + ". " + quizDetails.get(quiz_position).getQuizQuestion());
        Speach_Record_Activity.speakOut(context, quizDetails.get(quiz_position).getQuizQuestion(), false);
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

//        if (quizDetails.get(quiz_position).isChecked_radio_button()) {
//            for (int i = 0; i < radioGroupQuiz.getChildCount(); i++) {
//                radioGroupQuiz.getChildAt(i).setEnabled(false);
//            }
//        }

        radio_select_ans = "";
        select_radio = null;
        //**********
        radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);

                // if (!quizDetails.get(quiz_position).isChecked_radio_button()) {
                quizDetails.get(quiz_position).setChecked_radio_button(true);
                Speach_Record_Activity.speakOut(context, rb.getText().toString(), false);
                if (checkedId == R.id.radio1) {
                    radio_select_ans = rb.getText().toString();
                    select_radio = radio1;
                    // CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), rb.getText().toString(), radio1, quiz_position, ivIQuiz);
                } else if (checkedId == R.id.radio2) {
                    radio_select_ans = rb.getText().toString();
                    select_radio = radio2;
                    // CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), rb.getText().toString(), radio2, quiz_position, ivIQuiz);
                } else if (checkedId == R.id.radio3) {
                    radio_select_ans = rb.getText().toString();
                    select_radio = radio3;
                    //  CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), rb.getText().toString(), radio3, quiz_position, ivIQuiz);
                } else if (checkedId == R.id.radio4) {
                    radio_select_ans = rb.getText().toString();
                    select_radio = radio4;
                    // CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), rb.getText().toString(), radio4, quiz_position, ivIQuiz);
                } else if (checkedId == R.id.radio5) {
                    radio_select_ans = rb.getText().toString();
                    select_radio = radio5;
                    //CheckRightAns(quizDetails.get(quiz_position).isChecked_radio(), rb.getText().toString(), radio5, quiz_position, ivIQuiz);
                }
                //  }
                // CheckRightAns( quiz_position, ivIQuiz);

//                for (int i = 0; i <radioGroupQuiz.getChildCount(); i++) {
//                    radioGroupQuiz.getChildAt(i).setEnabled(false);
//                }

            }
        });


        ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n" + "'" + quizDetails.get(quiz_position).getRightAnswer() + "'";
                SweetAlt.OpenFreeCoinDialog(context, msg);

            }
        });


        container.addView(view);
        return view;
    }


    public void CheckRightAns(int quiz_position, ImageView ivIQuizs) {
        if (radio_select_ans != null && !radio_select_ans.equalsIgnoreCase("")) {
            CommonUtil.SubmitMemoryQuiz(sessionManager.getUser().getUserid(), quizDetails.get(quiz_position).getId(), radio_select_ans, sessionManager.getUser().getClassid(), stage_id);

            // ImageView ivIQuiz = view.findViewById(R.id.iv_i_quiz);
            if (radio_select_ans.equalsIgnoreCase(quizDetails.get(quiz_position).getRightAnswer())) {
                //Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                select_radio.setBackgroundResource(R.drawable.radio_green_bg);
                select_radio.setTextColor(context.getResources().getColor(R.color.white));
                ivIQuiz.setVisibility(View.GONE);
                // Toast.makeText(context, "Right Answer", Toast.LENGTH_SHORT).show();

            } else {
                select_radio.setBackgroundResource(R.drawable.radio_red_bg);
                select_radio.setTextColor(context.getResources().getColor(R.color.white));
                ivIQuiz.setVisibility(View.VISIBLE);
                // Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public boolean CheckRadioStatus(int pos) {
        if (quizDetails.get(pos).isChecked_radio_button()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((ScrollView) object);
    }


}
