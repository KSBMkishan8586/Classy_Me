package com.ksbm.ontu.progression_report;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.progression_report.model.Re;

import java.util.List;

public class haveFunAdapter extends RecyclerView.Adapter<haveFunAdapter.ViewHolder> {
    Activity activity;
    List<Re> model;

    public haveFunAdapter(Activity activity, List<Re> model) {
        this.activity = activity;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_report_lyt, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_name.setText(model.get(position).getParticular());
        holder.tv_ttl_coins.setText(model.get(position).getTotalCoins());
        holder.tv_earn_coins.setText(model.get(position).getEarnedCoins());
        holder.tv_score.setText(model.get(position).getScore()+ " %");


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,tv_ttl_coins,tv_earn_coins,tv_score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_earn_coins = itemView.findViewById(R.id.tv_earn_coins);
            tv_score = itemView.findViewById(R.id.tv_score);
            tv_ttl_coins = itemView.findViewById(R.id.tv_ttl_coins);

        }
    }
}
