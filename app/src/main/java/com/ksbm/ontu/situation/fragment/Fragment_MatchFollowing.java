package com.ksbm.ontu.situation.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentMatchFollowingBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_MatchFollowing extends Fragment {
    FragmentMatchFollowingBinding binding;
    SessionManager sessionManager;
    SituationQuestionModel.QuestionList questionDetails;
    ArrayAdapter<String> listadapter;
    String TxtSelectQuestion= "", TxtRighAns="";
    int ques_position;
    int ans_position;
    int total_right=0;
    int total_attempt=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_following, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());


        if (getArguments()!=null){
            questionDetails= ( SituationQuestionModel.QuestionList) getArguments().getSerializable("QuestionDetails");
        }

        setQuiz();
        setOnListner();

//        binding.textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//
//                final TextView tv_id = (TextView) v.findViewById(R.id.text_row);
//                ////String txt = tv_id.getText().toString();
//
//                if (!questionDetails.getWords().get(position).isSelected() && TxtSelectQuestion.isEmpty()){
//                    // // Toast.makeText(Fundamental_Quiz_Match_Following.this, ""+txt, Toast.LENGTH_SHORT).show();
//                    tv_id.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
//                    tv_id.setEnabled(false);
//                    questionDetails.getWords().get(position).setSelected(true);
//
//                    TxtSelectQuestion= questionDetails.getWords().get(position).getQuestion();
//                    TxtRighAns= questionDetails.getWords().get(position).getAnswer();
//                }
//
//            }
//        });
//
//        binding.textList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//                final TextView tv_id = (TextView) v.findViewById(R.id.text_row);
//                String txt = tv_id.getText().toString();
//
//                if (!questionDetails.getWords().get(position).isSelectedAns() && !TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
//                    // Toast.makeText(Fundamental_Quiz_Match_Following.this, ""+txt, Toast.LENGTH_SHORT).show();
//                    total_attempt++;
//                    String TxtSelectAnswer= txt;
//                    if (TxtRighAns.equalsIgnoreCase(TxtSelectAnswer)){
//                        tv_id.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
//                        tv_id.setEnabled(false);
//                        questionDetails.getWords().get(position).setSelectedAns(true);
//                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
//                        total_right++;
//
//                    }else {
//                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
//                    }
//
//                    TxtSelectQuestion="";
//                    TxtRighAns= "";
//                   // quiz_position++;
//
//                }else {
//                    //Toast.makeText(Fundamental_Quiz_Match_Following.this, "Please select question", Toast.LENGTH_SHORT).show();
//                }
//
//                if (total_attempt ==questionDetails.getWords().size()){
//                    if (total_right==questionDetails.getWords().size()){
//                        CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), questionDetails.getSituationId(), questionDetails.getSentenceId(), questionDetails.getSituationQuestionId(),
//                                questionDetails.getReward(), "1");//1=right ans
//
//                        Toast.makeText(getActivity(), "+ "+questionDetails.getReward(), Toast.LENGTH_SHORT).show();
//                    }else {
//
//                    }
//                }
//
//            }
//        });

        return view;
    }

    private void setOnListner() {
        //left side question touch
        binding.textRow1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=0;
                    binding.textRow1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow1.setEnabled(false);

                    TxtSelectQuestion= questionDetails.getWords().get(ques_position).getQuestion();
                    TxtRighAns= questionDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(getActivity(), questionDetails.getWords().get(ques_position).getQuestion(), false);
                }
            }
        });

        binding.textRow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=1;

                    binding.textRow2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow2.setEnabled(false);

                    TxtSelectQuestion= questionDetails.getWords().get(ques_position).getQuestion();
                    TxtRighAns= questionDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(getActivity(), questionDetails.getWords().get(ques_position).getQuestion(), false);
                }
            }
        });

        binding.textRow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=2;

                    binding.textRow3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow3.setEnabled(false);

                    TxtSelectQuestion= questionDetails.getWords().get(ques_position).getQuestion();
                    TxtRighAns= questionDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(getActivity(), questionDetails.getWords().get(ques_position).getQuestion(), false);
                }
            }
        });

        binding.textRow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=3;

                    binding.textRow4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow4.setEnabled(false);

                    TxtSelectQuestion= questionDetails.getWords().get(ques_position).getQuestion();
                    TxtRighAns= questionDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(getActivity(), questionDetails.getWords().get(ques_position).getQuestion(), false);
                }
            }
        });

        binding.textRow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtSelectQuestion.isEmpty() && TxtSelectQuestion.equalsIgnoreCase("")){
                    ques_position=4;

                    binding.textRow5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                    binding.textRow5.setEnabled(false);

                    TxtSelectQuestion= questionDetails.getWords().get(ques_position).getQuestion();
                    TxtRighAns= questionDetails.getWords().get(ques_position).getAnswer();

                    Speach_Record_Activity.speakOut(getActivity(), questionDetails.getWords().get(ques_position).getQuestion(), false);
                }
            }
        });


        //click right answer in right seide view
        binding.textRowRight1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans_position=5;
                Speach_Record_Activity.speakOut(getActivity(), binding.textRowRight1.getText().toString(), false);

                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight1.getText().toString())){
                        binding.textRowRight1.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight1.setEnabled(false);
                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                        total_right = total_right + 1;


                    }else {
                        binding.textRowRight1.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight1.setEnabled(false);
                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                    }

                }

                TxtSelectQuestion="";
                TxtRighAns= "";

                getCoin();
            }
        });

        binding.textRowRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(), binding.textRowRight2.getText().toString(), false);
                total_attempt++;
                ans_position=6;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);

                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight2.getText().toString())){
                        binding.textRowRight2.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight2.setEnabled(false);
                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                        total_right = total_right + 1;
                    }else {
                        binding.textRowRight2.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight2.setEnabled(false);
                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
               // total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(), binding.textRowRight3.getText().toString(), false);
                ans_position=7;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight3.getText().toString())){
                        binding.textRowRight3.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight3.setEnabled(false);
                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                        total_right = total_right + 1;
                    }else {
                        binding.textRowRight3.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight3.setEnabled(false);
                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
               // total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(), binding.textRowRight4.getText().toString(), false);
                ans_position=8;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight4.getText().toString())){
                        binding.textRowRight4.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight4.setEnabled(false);
                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                        total_right = total_right + 1;
                    }else {
                        binding.textRowRight4.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight4.setEnabled(false);
                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
                //total_attempt++;
                getCoin();
            }
        });

        binding.textRowRight5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(getActivity(), binding.textRowRight5.getText().toString(), false);
                ans_position=9;
                if (!TxtSelectQuestion.isEmpty() && !TxtSelectQuestion.equalsIgnoreCase("")){
                    binding.arrowLayout.animateArrows(1000, ques_position, ans_position);
                    total_attempt++;
                    if (TxtRighAns.equalsIgnoreCase(binding.textRowRight5.getText().toString())){
                        binding.textRowRight5.setBackground(getResources().getDrawable(R.drawable.circle_fill_green));
                        binding.textRowRight5.setEnabled(false);
                        Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
                        total_right = total_right + 1;
                    }else {
                        binding.textRowRight5.setBackground(getResources().getDrawable(R.drawable.circle_fill_red));
                        binding.textRowRight5.setEnabled(false);
                        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                TxtSelectQuestion="";
                TxtRighAns= "";
               // total_attempt++;
                getCoin();
            }
        });

    }

    private void getCoin() {
        if (total_attempt ==questionDetails.getWords().size()){
            if (total_right==questionDetails.getWords().size()){
                CommonUtil.SubmitSituationQuiz(sessionManager.getUser().getUserid(), questionDetails.getSituationId(), questionDetails.getSentenceId(), questionDetails.getSituationQuestionId(),
                        questionDetails.getReward(), "1");//1=right ans

                binding.tvUserCoin.setText("+ " + questionDetails.getReward());
                binding.llGetCoin.setVisibility(View.VISIBLE);
                Utils.playMusic(R.raw.coin_sound, getActivity());
                Toast.makeText(getActivity(), ques_position+"", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("ResumeQuize", MODE_PRIVATE).edit();
                editor.putString("QuestionId", "match the following");
                editor.putString("situation_id", questionDetails.getSituationId()+"");
                editor.putString("situation_name", questionDetails.getSituation_heading()+"");
                editor.putString("sentence_id", questionDetails.getSentenceId()+"");
                editor.putBoolean("value", true);
                editor.apply();
               // Toast.makeText(getActivity(), "+ "+questionDetails.getReward(), Toast.LENGTH_SHORT).show();
            }else {

            }
        }
    }

    private void setQuiz() {
        binding.tvQuizName.setText(questionDetails.getSituationQuestion());

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list1 = new ArrayList<String>();

        for (int i=0; i<questionDetails.getWords().size(); i++){
            list.add(questionDetails.getWords().get(i).getQuestion());
            list1.add( questionDetails.getWords().get(i).getAnswer());
        }

//        listadapter = new ArrayAdapter<String>(getActivity(), R.layout.rowtext,R.id.text_row, list);
//        binding.textList.setAdapter(listadapter);

        Collections.shuffle(list1);

//        listadapter = new ArrayAdapter<String>(getActivity(), R.layout.rowtext, R.id.text_row, list1);
//        binding.textList1.setAdapter(listadapter);

        //set left side question
        binding.textRow1.setText(list.get(0));
        binding.textRow2.setText(list.get(1));

        if (list.size()>2 && list.get(2)!=null){
            binding.textRow3.setText(list.get(2));
        }else {
            binding.textRow3.setVisibility(View.GONE);
        }
        if (list.size()>3 && list.get(3)!=null){
            binding.textRow4.setText(list.get(3));
        }else {
            binding.textRow4.setVisibility(View.GONE);
        }
        if (list.size()>4 && list.get(4)!=null){

            binding.textRow5.setText(list.get(4));
        }else {
            binding.textRow5.setVisibility(View.GONE);
        }

        //set right side answer
        binding.textRowRight1.setText(list1.get(0));
        binding.textRowRight2.setText(list1.get(1));

        if (list1.size()>2 && list1.get(2)!=null){
            binding.textRowRight3.setText(list1.get(2));
        }else {
            binding.textRowRight3.setVisibility(View.GONE);
        }
        if (list1.size()>3 && list1.get(3)!=null){
            binding.textRowRight4.setText(list1.get(3));
        }else {
            binding.textRowRight4.setVisibility(View.GONE);
        }
        if (list1.size()>4 && list1.get(4)!=null){
            binding.textRowRight5.setText(list1.get(4));
        }else {
            binding.textRowRight5.setVisibility(View.GONE);
        }

    }

}
