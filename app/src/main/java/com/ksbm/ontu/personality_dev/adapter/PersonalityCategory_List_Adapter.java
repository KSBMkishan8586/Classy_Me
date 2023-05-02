package com.ksbm.ontu.personality_dev.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.PersonalityVideoListBinding;
import com.ksbm.ontu.personality_dev.VideoListFragment;
import com.ksbm.ontu.personality_dev.model.CategoryModel;

import java.util.List;

public class PersonalityCategory_List_Adapter extends RecyclerView.Adapter<PersonalityCategory_List_Adapter.ViewHolder> {
    private List<CategoryModel.CategoryModelResponse> dataModelList;
    Context context;

    public PersonalityCategory_List_Adapter(List<CategoryModel.CategoryModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PersonalityVideoListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.personality_video_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryModel.CategoryModelResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (dataModel.getImage()!=null && !dataModel.getImage().isEmpty()){
            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.placeholder(R.drawable.video_show);
//            requestOptions.error(R.drawable.video_show);
            requestOptions.isMemoryCacheable();

            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(dataModel.getImage())
                    .into(holder.itemRowBinding.ivCategory);
        }

        holder.itemRowBinding.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_home = new VideoListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CategoryId", dataModel.getId());
                bundle.putString("CategoryName", dataModel.getCategory());
                FragmentTransaction ft_home = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public PersonalityVideoListBinding itemRowBinding;

        public ViewHolder(PersonalityVideoListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    // method for filtering our recyclerview items.
    public void filterList(List<CategoryModel.CategoryModelResponse> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModelList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
