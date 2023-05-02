package com.ksbm.ontu.profile.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.profile.model.MyPrize;

import java.util.List;


public class PrizesAdapter extends RecyclerView.Adapter<PrizesAdapter.ViewHolder> {

    private List<MyPrize.MyPrizeResponse> dataModelList;
    Context context;


    public PrizesAdapter(List<MyPrize.MyPrizeResponse> dataModelList, Context context) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prize,parent,false);

        return new PrizesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


       holder.rank.setText(dataModelList.get(position).getRank());
        holder.price.setText(Html.fromHtml("&#8377")+""+dataModelList.get(position).getAmount());


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank,price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank=itemView.findViewById(R.id.rank);
            price=itemView.findViewById(R.id.amount);



        }
    }

}
