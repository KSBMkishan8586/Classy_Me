package com.ksbm.ontu.fundamental.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FundamentalQuizItemBinding;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Drag_Drop;
import com.ksbm.ontu.fundamental.model.fundamental_quiz_model.Word_Quiz_List;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class Fundamental_Drag_Drop_Adapter extends RecyclerView.Adapter<Fundamental_Drag_Drop_Adapter.ViewHolder> {
    private List<Word_Quiz_List> dataModelList;
    Context context;

    public Fundamental_Drag_Drop_Adapter(List<Word_Quiz_List> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FundamentalQuizItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fundamental_quiz_item, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Word_Quiz_List dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        Utils.setRandomColorWord(holder.itemRowBinding.cardItem, context);

        holder.itemRowBinding.cardItem.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((Fundamental_Quiz_Drag_Drop)context).DragTextItem(view, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FundamentalQuizItemBinding itemRowBinding;

        public ViewHolder(FundamentalQuizItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
