package com.ksbm.ontu.fundamental.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FundamentalQuizFillItemBinding;
import com.ksbm.ontu.databinding.FundamentalQuizItemBinding;
import com.ksbm.ontu.fundamental.activity.FundamentalQuizTouchFill;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Match_Following;
import com.ksbm.ontu.fundamental.model.quiz_touch_fill.Touch_FillWord;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class Fundamental_Touch_Fill_Word_Adapter extends RecyclerView.Adapter<Fundamental_Touch_Fill_Word_Adapter.ViewHolder> {
    private List<Touch_FillWord> dataModelList;
    Context context;

    public Fundamental_Touch_Fill_Word_Adapter(List<Touch_FillWord> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FundamentalQuizFillItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fundamental_quiz_fill_item, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Touch_FillWord dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        Utils.setRandomColorWord(holder.itemRowBinding.cardItem1, context);

        holder.itemRowBinding.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof FundamentalQuizTouchFill) {
                    ((FundamentalQuizTouchFill) context).CheckRightAnswer(view, position, dataModel.getRightAnswer());
                    Utils.playMusic(R.raw.coin_sound, context);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FundamentalQuizFillItemBinding itemRowBinding;

        public ViewHolder(FundamentalQuizFillItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
