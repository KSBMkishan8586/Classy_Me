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
import com.ksbm.ontu.databinding.ColorPalletListBinding;
import com.ksbm.ontu.databinding.CountingListBinding;
import com.ksbm.ontu.foundation.model.CountingModel;
import com.ksbm.ontu.foundation.model.PalletColorModel;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;
import java.util.Random;

public class ColorPalletAdapter extends RecyclerView.Adapter<ColorPalletAdapter.ViewHolder> {
    private List<PalletColorModel> dataModelList;
    Context context;

    public ColorPalletAdapter(List<PalletColorModel> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ColorPalletListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.color_pallet_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PalletColorModel dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.itemRowBinding.tvColorName.setBackgroundColor(dataModel.getColor_code());

        holder.itemRowBinding.tvColorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, dataModel.getColor_name(), false);

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
        public ColorPalletListBinding itemRowBinding;

        public ViewHolder(ColorPalletListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
