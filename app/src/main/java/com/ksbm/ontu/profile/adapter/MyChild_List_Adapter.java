package com.ksbm.ontu.profile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.MyChildListBinding;
import com.ksbm.ontu.databinding.PersonalityVideoListBinding;
import com.ksbm.ontu.personality_dev.model.CategoryModel;
import com.ksbm.ontu.profile.model.MyChildModel;

import java.util.List;

public class MyChild_List_Adapter extends RecyclerView.Adapter<MyChild_List_Adapter.ViewHolder> {
    private List<MyChildModel.ChildData> dataModelList;
    Context context;

    public MyChild_List_Adapter(List<MyChildModel.ChildData> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyChildListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.my_child_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyChildModel.ChildData dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.itemRowBinding.tvFullname.setText(dataModel.getcFullname());
        holder.itemRowBinding.tvUserName.setText(dataModel.getcUsername());

        if (dataModel.getcProfilePic()!=null && !dataModel.getcProfilePic().isEmpty()){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.video_show);
            requestOptions.error(R.drawable.video_show);
            requestOptions.isMemoryCacheable();

            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(dataModel.getcProfilePic())
                    .into(holder.itemRowBinding.ivUserimage);
        }

        holder.itemRowBinding.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public MyChildListBinding itemRowBinding;

        public ViewHolder(MyChildListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    // method for filtering our recyclerview items.
    public void filterList(List<MyChildModel.ChildData> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModelList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

}
