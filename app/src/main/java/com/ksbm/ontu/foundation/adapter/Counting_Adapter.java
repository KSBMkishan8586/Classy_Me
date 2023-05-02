package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.CountingListBinding;
import com.ksbm.ontu.foundation.model.CountingModel;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.List;
import java.util.Random;


public class Counting_Adapter extends RecyclerView.Adapter<Counting_Adapter.ViewHolder> {
    private List<CountingModel> dataModelList;
    Context context;

    public Counting_Adapter(List<CountingModel> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CountingListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.counting_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CountingModel dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        Utils.setRandomColor(holder.itemRowBinding.tvCount, context);

        holder.itemRowBinding.tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = holder.itemRowBinding.tvCount.getText().toString();
                SweetAlt.CountingTooltipDialog(context, msg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CountingListBinding itemRowBinding;

        public ViewHolder(CountingListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
