package com.ksbm.ontu.situation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FundamentalQuizFillItemBinding;
import com.ksbm.ontu.databinding.SituTouchQuizFillItemBinding;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizTouchFill;
import com.ksbm.ontu.fundamental.adapter.Fundamental_Touch_Fill_Word_Adapter;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.Touch_FillWord;
import com.ksbm.ontu.situation.fragment.Fragment_Drag_Drop;
import com.ksbm.ontu.situation.fragment.Fragment_Touch_Fill;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class SituationTouchWord_Adapter extends RecyclerView.Adapter<SituationTouchWord_Adapter.ViewHolder> {
    private List<SituationQuestionModel.Word_Question> dataModelList;
    Context context;
    Fragment_Touch_Fill fragment_drag_drop;

    public SituationTouchWord_Adapter(List<SituationQuestionModel.Word_Question> dataModelList, Context ctx, Fragment_Touch_Fill fragment_drag_drop) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.fragment_drag_drop=fragment_drag_drop;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SituTouchQuizFillItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.situ_touch_quiz_fill_item, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SituationQuestionModel.Word_Question dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        Utils.setRandomColorWord(holder.itemRowBinding.cardItem, context);

        holder.itemRowBinding.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (context instanceof Fragment_Touch_Fill) {
                    ((Fragment_Touch_Fill) fragment_drag_drop).CheckRightAnswer(view, position, dataModel.getSituationWord());

               // }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SituTouchQuizFillItemBinding itemRowBinding;

        public ViewHolder(SituTouchQuizFillItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
