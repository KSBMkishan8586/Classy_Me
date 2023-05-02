package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.CountingListBinding;
import com.ksbm.ontu.databinding.CountingStarListBinding;

public class Counting_Star_Adapter extends RecyclerView.Adapter<Counting_Star_Adapter.ViewHolder> {
   int count_star;
    Context context;

    public Counting_Star_Adapter(int count_star, Context ctx) {
        this.count_star = count_star;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CountingStarListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.counting_star_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // holder.bind(dataModel);
      //  holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.itemRowBinding.ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count_star >1){
                    Speach_Record_Activity.speakOut(context, count_star + " Stars", false);
                }else {
                    Speach_Record_Activity.speakOut(context, count_star + " Star", false);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return count_star;
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
        public CountingStarListBinding itemRowBinding;

        public ViewHolder(CountingStarListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }



}
