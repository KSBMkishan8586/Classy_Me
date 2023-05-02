package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FoundationQuizCheckboxItemBinding;
import com.ksbm.ontu.foundation.fragment.Foundation_CheckBox;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_CheckBox;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class Foundation_CheckBox_Quiz_Adapter extends RecyclerView.Adapter<Foundation_CheckBox_Quiz_Adapter.ViewHolder> {
    private List<FoundationQuizModel.FoundationWord> dataModelList;
    Context context;
    String heading1; String heading2; String heading3, quiz_id;
    SessionManager sessionManager;
    boolean isPlayed= false;
    Foundation_CheckBox foundationCheckBox;

    public Foundation_CheckBox_Quiz_Adapter(List<FoundationQuizModel.FoundationWord> dataModelList, Context ctx, String heading1,
                                            String heading2, String heading3, String quiz_id, Foundation_CheckBox foundation_checkBox) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.heading1=heading1;
        this.heading2=heading2;
        this.heading3=heading3;
        this.quiz_id=quiz_id;
        foundationCheckBox= foundation_checkBox;
        sessionManager= new SessionManager(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FoundationQuizCheckboxItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.foundation_quiz_checkbox_item, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoundationQuizModel.FoundationWord dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (heading2.isEmpty() && heading2.equalsIgnoreCase("")){
            holder.itemRowBinding.radio2.setVisibility(View.INVISIBLE);
        }else {
            holder.itemRowBinding.radio2.setVisibility(View.VISIBLE);
        }

        if (heading3.isEmpty() && heading3.equalsIgnoreCase("")){
            holder.itemRowBinding.radio3.setVisibility(View.INVISIBLE);
        }else {
            holder.itemRowBinding.radio3.setVisibility(View.VISIBLE);
        }

        holder.itemRowBinding.tvHeading1.setText(dataModel.getWord());


        holder.itemRowBinding.radioGroupQuiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb=(RadioButton)radioGroup.findViewById(checkedId);

                dataModelList.get(position).setAll_checked(true);

                if (checkedId==R.id.radio1){
                    if (dataModel.getRightAnswer().equalsIgnoreCase("heading_1")){
                        //right answer
                        dataModelList.get(position).setRight_ans_by_position(true);
                        rb.setBackground(context.getResources().getDrawable(R.drawable.radio_green_bg));
                    }else {
                        dataModelList.get(position).setRight_ans_by_position(false);
                        holder.itemRowBinding.ivIQuiz.setVisibility(View.VISIBLE);
                        rb.setBackground(context.getResources().getDrawable(R.drawable.radio_red_bg));
                    }
                }else  if (checkedId==R.id.radio2){
                    if (dataModel.getRightAnswer().equalsIgnoreCase("heading_2")){
                        //right answer
                        dataModelList.get(position).setRight_ans_by_position(true);
                        rb.setBackground(context.getResources().getDrawable(R.drawable.radio_green_bg));
                    }else {
                        dataModelList.get(position).setRight_ans_by_position(false);
                        holder.itemRowBinding.ivIQuiz.setVisibility(View.VISIBLE);
                        rb.setBackground(context.getResources().getDrawable(R.drawable.radio_red_bg));
                    }
                }else  if (checkedId==R.id.radio3){
                    if (dataModel.getRightAnswer().equalsIgnoreCase("heading_3")){
                        //right answer
                        dataModelList.get(position).setRight_ans_by_position(true);
                        rb.setBackground(context.getResources().getDrawable(R.drawable.radio_green_bg));
                    }else {
                        dataModelList.get(position).setRight_ans_by_position(false);
                        holder.itemRowBinding.ivIQuiz.setVisibility(View.VISIBLE);
                        rb.setBackground(context.getResources().getDrawable(R.drawable.radio_red_bg));
                    }
                }

                for (int i = 0; i < holder.itemRowBinding.radioGroupQuiz.getChildCount(); i++) {
                    holder.itemRowBinding.radioGroupQuiz.getChildAt(i).setEnabled(false);
                }

                //submit coin
//                if (dataModelList.get(position).isRight_ans_by_position()){
//                    CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, dataModelList.get(position).getReward(), "1", dataModelList.get(position).getQuiz_word_id());//1=right ans
//
//                    ((Fundamental_Quiz_CheckBox)context).CoinAnimated(true, dataModelList.get(position).getReward());
//                }else {
//                    CommonUtil.SubmitQuiz(sessionManager.getUser().getUserid(), quiz_id, dataModelList.get(position).getReward(), "0", dataModelList.get(position).getQuiz_word_id());//0=wrong ans
//                    ((Fundamental_Quiz_CheckBox)context).CoinAnimated(false, dataModelList.get(position).getReward() );
//                }

            }
        });

        holder.itemRowBinding.ivIQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String right_option= "";
                if (dataModel.getRightAnswer().equalsIgnoreCase("heading_1")){
                    right_option = heading1;
                }else if (dataModel.getRightAnswer().equalsIgnoreCase("heading_2")){
                    right_option = heading2;
                }else if (dataModel.getRightAnswer().equalsIgnoreCase("heading_3")){
                    right_option = heading3;
                }

                String msg = "Correct Answer is \n"+ "'" +right_option +"'";
                SweetAlt.OpenFreeCoinDialog(context, msg);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoundationQuizCheckboxItemBinding itemRowBinding;

        public ViewHolder(FoundationQuizCheckboxItemBinding itemRowBinding) {
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
       // int got_total_reward=0;

        for (int i =0; i<dataModelList.size(); i++ ){
            if (dataModelList.get(i).isAll_checked()){
                check_count++;
            }
            if (dataModelList.get(i).isRight_ans_by_position()){
                total_rightanswer_count++;
               // got_total_reward += Integer.parseInt(dataModelList.get(i).getReward());
            }
        }

        if (check_count != dataModelList.size()){
            Toast.makeText(context, "Please check all questions", Toast.LENGTH_SHORT).show();
        }else {
            foundationCheckBox.FinalQuizResult(total_rightanswer_count );

        }
    }

}
