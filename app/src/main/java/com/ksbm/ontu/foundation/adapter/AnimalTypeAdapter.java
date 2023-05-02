package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.AnimalTypeItemBinding;
import com.ksbm.ontu.foundation.activity.Animal_Activity;
import com.ksbm.ontu.foundation.model.AnimalModel;

import java.util.ArrayList;
import java.util.List;

public class AnimalTypeAdapter extends RecyclerView.Adapter<AnimalTypeAdapter.ViewHolder> {
    private List<AnimalModel.AnimalModelResponse> dataModelList;
    Context context;

    public AnimalTypeAdapter(List<AnimalModel.AnimalModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AnimalTypeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.animal_type_item, parent, false);

        return new ViewHolder(binding);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnimalModel.AnimalModelResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.bg_offline_level1);
//        requestOptions.error(R.drawable.bg_offline_level1);
//        requestOptions.isMemoryCacheable();
//
//        if (dataModel.getImage()!=null && !dataModel.getImage().isEmpty()){
//
//            Glide.with(context).setDefaultRequestOptions(requestOptions)
//                    .load(dataModel.getImage())
//                    .into(holder.itemRowBinding.ivStageBg);
//        }

        holder.itemRowBinding.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dataModel.getLearningActivity()!=null && dataModel.getLearningActivity().size() > 0){
                    Intent in = new Intent(context, Animal_Activity.class);
                    in.putExtra("foundationTypeName", dataModel.getFoundationType());
                    in.putParcelableArrayListExtra("AnimalList", (ArrayList<? extends Parcelable>) dataModel.getLearningActivity());
                    context.startActivity(in);
                }else {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AnimalTypeItemBinding itemRowBinding;

        public ViewHolder(AnimalTypeItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }




}
