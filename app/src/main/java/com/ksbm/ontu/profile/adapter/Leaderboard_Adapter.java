package com.ksbm.ontu.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.LeaderboardListBinding;
import com.ksbm.ontu.profile.model.LeaderBoardModel;
import com.ksbm.ontu.session.SessionManager;

import java.util.List;

public class Leaderboard_Adapter extends RecyclerView.Adapter<Leaderboard_Adapter.ViewHolder> {
    private List<LeaderBoardModel.LeaderBoardResponse> dataModelList;
    Context context;
    SessionManager sessionManager;

    public Leaderboard_Adapter(List<LeaderBoardModel.LeaderBoardResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LeaderboardListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.leaderboard_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeaderBoardModel.LeaderBoardResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        //holder.itemRowBinding.setModel(dataModel);
        holder.itemRowBinding.tvUsername.setText(dataModel.getFullname());
        holder.itemRowBinding.tvCoin.setText(dataModel.getTotalEarning());
        if (position < 3) {
            holder.itemRowBinding.ivBudge.setVisibility(View.VISIBLE);
            holder.itemRowBinding.ranks.setText(dataModel.getRank());
        } else {
            holder.itemRowBinding.ivBudge.setVisibility(View.INVISIBLE);
        }
        if (dataModel.getUserid().equalsIgnoreCase(sessionManager.getUser().getUserid())) {
            holder.itemRowBinding.cactusCardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_blue1));
        } else {
            holder.itemRowBinding.cactusCardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_blue2));

        }
        Glide.with(context).load(dataModel.getUserPic()).placeholder(R.drawable.man).into(holder.itemRowBinding.userPic);
        Glide.with(context).load(dataModel.getSchoolPic()).placeholder(R.drawable.school_icon).into(holder.itemRowBinding.schoolPic);

        // holder.itemRowBinding.setItemClickListener(this);


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LeaderboardListBinding itemRowBinding;

        public ViewHolder(LeaderboardListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
