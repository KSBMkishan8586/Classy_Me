package com.ksbm.ontu.free_coin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FreeCoinListBinding;
import com.ksbm.ontu.main_screen.model.FreeCoinModel;

import java.util.List;

public class FreeCoin_Adapter extends RecyclerView.Adapter<FreeCoin_Adapter.ViewHolder> {
    private List<FreeCoinModel.FreeCoinModelResponses> dataModelList;
    Context context;

    public FreeCoin_Adapter(List<FreeCoinModel.FreeCoinModelResponses> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FreeCoinListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.free_coin_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FreeCoinModel.FreeCoinModelResponses dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.itemRowBinding.tvSerial.setText(""+ (position+1));

        if (dataModel.getIsUsed().equalsIgnoreCase("yes")){
            holder.itemRowBinding.cardPlayer.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            holder.itemRowBinding.tvCollect.setText("Collected");
        }

        holder.itemRowBinding.cardPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;


                if (dataModel.getVideoLink()!=null && !dataModel.getVideoLink().equalsIgnoreCase("") ||
                        dataModel.getVideoFile()!=null && !dataModel.getVideoFile().equalsIgnoreCase("")){
                    Intent intent= new Intent(context, FreeCoinVideoPlayer.class);
                    if (dataModel.getVideoLink()!=null && !dataModel.getVideoLink().equalsIgnoreCase("")){
                        intent.putExtra("FilePath", dataModel.getVideoLink());
                        intent.putExtra("FilePathOther", true);
                        intent.putExtra("video_id", dataModel.getVideoId());
                        intent.putExtra("coin_amount", dataModel.getReward());
                        intent.putExtra("is_used", dataModel.getIsUsed());
                    }else {
                        intent.putExtra("FilePath", dataModel.getVideoFile());
                        intent.putExtra("FilePathOther", false);
                        intent.putExtra("video_id", dataModel.getVideoId());
                        intent.putExtra("coin_amount", dataModel.getReward());
                        intent.putExtra("is_used", dataModel.getIsUsed());
                    }
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Null Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FreeCoinListBinding itemRowBinding;

        public ViewHolder(FreeCoinListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
