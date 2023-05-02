package com.ksbm.ontu.fundamental.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.databinding.FundamentalQuizCheckboxItemBinding;
import com.ksbm.ontu.databinding.FundamentalQuizItemBinding;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizTouchFill;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_CheckBox;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Word_Quiz_List;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;

public class Fundamental_CheckBox_Quiz_Adapter extends RecyclerView.Adapter<Fundamental_CheckBox_Quiz_Adapter.ViewHolder> {
    private List<Word_Quiz_List> dataModelList;
    Context context;
    String heading1; String heading2; String heading3, quiz_id;
    SessionManager sessionManager;

    public Fundamental_CheckBox_Quiz_Adapter(List<Word_Quiz_List> dataModelList, Context ctx, String heading1,
                                             String heading2, String heading3, String quiz_id) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.heading1=heading1;
        this.heading2=heading2;
        this.heading3=heading3;
        this.quiz_id=quiz_id;
        sessionManager= new SessionManager(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FundamentalQuizCheckboxItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fundamental_quiz_checkbox_item, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word_Quiz_List dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (heading3.isEmpty() && heading3.equalsIgnoreCase("")){
            holder.itemRowBinding.radio3.setVisibility(View.GONE);
        }

        holder.itemRowBinding.tvHeading1.setText(dataModel.getQuizWords());


        holder.itemRowBinding.radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb=(RadioButton)radioGroup.findViewById(checkedId);

                dataModelList.get(position).setAll_checked(true);

                if (checkedId==R.id.radio1){
                    if (dataModel.getRightAnswer().equalsIgnoreCase(heading1)){
                        //right answer
                        dataModelList.get(position).setRight_ans_by_position(true);
                    }else {
                        dataModelList.get(position).setRight_ans_by_position(false);
                        holder.itemRowBinding.ivIQuiz.setVisibility(View.VISIBLE);
                    }
                }else  if (checkedId==R.id.radio2){
                    if (dataModel.getRightAnswer().equalsIgnoreCase(heading2)){
                        //right answer
                        dataModelList.get(position).setRight_ans_by_position(true);
                    }else {
                        dataModelList.get(position).setRight_ans_by_position(false);
                        holder.itemRowBinding.ivIQuiz.setVisibility(View.VISIBLE);
                    }
                }else  if (checkedId==R.id.radio3){
                    if (dataModel.getRightAnswer().equalsIgnoreCase(heading3)){
                        //right answer
                        dataModelList.get(position).setRight_ans_by_position(true);
                    }else {
                        dataModelList.get(position).setRight_ans_by_position(false);
                        holder.itemRowBinding.ivIQuiz.setVisibility(View.VISIBLE);
                    }
                }

                for (int i = 0; i < holder.itemRowBinding.radioGroupQuiz.getChildCount(); i++) {
                    holder.itemRowBinding.radioGroupQuiz.getChildAt(i).setEnabled(false);
                }

                //submit coin
                if (dataModelList.get(position).isRight_ans_by_position()){
                    CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, dataModelList.get(position).getReward(), "1", dataModelList.get(position).getQuiz_word_id());//1=right ans

                    ((Fundamental_Quiz_CheckBox)context).CoinAnimated(true, dataModelList.get(position).getReward());
                }else {
                    CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, dataModelList.get(position).getReward(), "0", dataModelList.get(position).getQuiz_word_id());//0=wrong ans
                    ((Fundamental_Quiz_CheckBox)context).CoinAnimated(false, dataModelList.get(position).getReward() );
                }

            }
        });

        holder.itemRowBinding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Correct Answer is \n"+ "'" +dataModel.getRightAnswer() +"'";
                SweetAlt.OpenFreeCoinDialog(context, msg);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FundamentalQuizCheckboxItemBinding itemRowBinding;

        public ViewHolder(FundamentalQuizCheckboxItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public void CheckedRadio(){
        int check_count=0;
        int total_rightanswer_count=0;
        int got_total_reward=0;

        for (int i =0; i<dataModelList.size(); i++ ){
            if (dataModelList.get(i).getAll_checked()){
                check_count++;
            }
            if (dataModelList.get(i).isRight_ans_by_position()){
                total_rightanswer_count++;

                got_total_reward += Integer.parseInt(dataModelList.get(i).getReward());
            }

        }

        if (check_count != dataModelList.size()){
            Toast.makeText(context, "Please check all questions", Toast.LENGTH_SHORT).show();
        }else {
            //Log.e("total_right", ""+total_rightanswer_count);
           // Log.e("got_total_reward", ""+got_total_reward);

           ((Fundamental_Quiz_CheckBox)context).FinalQuizResult( got_total_reward);

        }
    }

}
