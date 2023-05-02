//package com.ksbm.ontu.profile.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ksbm.ontu.BR;
//import com.ksbm.ontu.R;
//import com.ksbm.ontu.databinding.ItemPrizeBinding;
//import com.ksbm.ontu.databinding.LeaderboardListBinding;
//import com.ksbm.ontu.profile.model.LeaderBoardModel;
//import com.ksbm.ontu.profile.model.MyPrize;
//
//import java.util.List;
//
//public class PrizeAdapter extends RecyclerView.Adapter<PrizeAdapter.ViewHolder> {
//    private List<MyPrize.MyPrizeResponse> dataModelList;
//    Context context;
//
//    public PrizeAdapter(List<MyPrize.MyPrizeResponse> dataModelList, Context context) {
//        this.dataModelList = dataModelList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ItemPrizeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
//                R.layout.item_prize, parent, false);
//
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        MyPrize.MyPrizeResponse dataModel = dataModelList.get(position);
//        holder.bind(dataModel);
//        holder.itemRowBinding.setModel(dataModel);
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataModelList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ItemPrizeBinding itemRowBinding;
//
//        public ViewHolder(ItemPrizeBinding itemRowBinding) {
//            super(itemRowBinding.getRoot());
//            this.itemRowBinding = itemRowBinding;
//        }
//
//        public void bind(Object obj) {
//            itemRowBinding.setVariable(BR.model, obj);
//            itemRowBinding.executePendingBindings();
//        }
//    }
//}
