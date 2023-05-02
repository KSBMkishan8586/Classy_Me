package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.CurrencyListBinding;
import com.ksbm.ontu.databinding.VideoListBinding;
import com.ksbm.ontu.foundation.model.CurrencyModel;
import com.ksbm.ontu.personality_dev.model.CategoryWiseModel;
import com.ksbm.ontu.session.SessionManager;

import java.util.List;

public class Currency_List_Adapter extends RecyclerView.Adapter<Currency_List_Adapter.ViewHolder> {
    private List<CurrencyModel.CurrencyModelResponse> dataModelList;
    Context context;
    SessionManager sessionManager;

    public Currency_List_Adapter(List<CurrencyModel.CurrencyModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        sessionManager= new SessionManager(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CurrencyListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.currency_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrencyModel.CurrencyModelResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (dataModel.getCountryFlagImage()!=null && !dataModel.getCountryFlagImage().isEmpty()){
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.ic_flag_24);
                requestOptions.error(R.drawable.ic_flag_24);
                requestOptions.isMemoryCacheable();

                Glide.with(context).setDefaultRequestOptions(requestOptions)
                        .load(dataModel.getCountryFlagImage())
                        .into(holder.itemRowBinding.ivCountryImg);

        }

        holder.itemRowBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, holder.itemRowBinding.tvTitle.getText().toString(), false);

            }
        });

        holder.itemRowBinding.tvDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, holder.itemRowBinding.tvDesc.getText().toString(), false);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CurrencyListBinding itemRowBinding;

        public ViewHolder(CurrencyListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


    // method for filtering our recyclerview items.
    public void filterList(List<CurrencyModel.CurrencyModelResponse> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModelList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


}
