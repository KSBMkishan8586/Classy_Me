package com.ksbm.ontu.practice_offline.adapter;

import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.ksbm.ontu.databinding.OfflineStageItemBinding;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.practice_offline.fragment.Fragment_Offline_Level;
import com.ksbm.ontu.practice_offline.model.OfflineStageModel;
import com.ksbm.ontu.practice_offline.activity.MemoryMapQuiz;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;

public class Offline_Stage_Adapter extends RecyclerView.Adapter<Offline_Stage_Adapter.ViewHolder> {
    private List<OfflineStageModel.OfflineStageModelResponse> dataModelList;
    Context context;
    MeraSharedPreferance meraSharedPreferance;

    public Offline_Stage_Adapter(List<OfflineStageModel.OfflineStageModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        meraSharedPreferance = MeraSharedPreferance.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfflineStageItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.offline_stage_item, parent, false);
        return new ViewHolder(binding);

    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OfflineStageModel.OfflineStageModelResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.itemRowBinding.tvEarnCoin.setText("" + dataModel.getTotalQuizEarnLevelCoin());
        holder.itemRowBinding.tvAttemptTotalQuestion.setText("" + dataModel.getTotalQuizLevelAttampt() + "/" + dataModel.getTotalQuizLevelQuestion());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bg_offline_level1);
        requestOptions.error(R.drawable.bg_offline_level1);
        requestOptions.isMemoryCacheable();

        if (dataModel.getImage() != null && !dataModel.getImage().isEmpty()) {
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(dataModel.getImage())
                    .into(holder.itemRowBinding.ivStageBg);
        }

        if (position==0){
            holder.itemRowBinding.ivLock.setVisibility(View.GONE);
        }
        if (dataModel.getStatus()!=null){
            if (dataModel.getStatus().equalsIgnoreCase("unlock")) {
                holder.itemRowBinding.ivLock.setVisibility(View.GONE);
            } else {
                holder.itemRowBinding.ivLock.setVisibility(View.VISIBLE);
            }
        }

        holder.itemRowBinding.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (position<=3){

                    if (position!=0){
                        if (dataModel.getStatus().equalsIgnoreCase("unlock")) {

                            if (dataModelList.get(position-1).getTotalQuizLevelQuestion().equals(dataModelList.get(position-1).getTotalQuizLevelAttampt())) {
                                if (!dataModelList.get(position-1).getMemoryMapQuizPlayStatus().equalsIgnoreCase("true")) {
                                    if (!dataModelList.get(position-1).getTotalQuizLevelQuestion().equals(0)) {
                                        Intent intent = new Intent(context, MemoryMapQuiz.class);
                                        intent.putExtra("stage_id", dataModelList.get(position-1).getId());
                                        context.startActivity(intent);
                                    } else {
                                        Fragment fragment_home = new Fragment_Offline_Level();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("StageId", dataModel.getId());
                                        bundle.putSerializable("StageName", dataModel.getStage());
                                        bundle.putSerializable("StageImage", dataModel.getImage());
                                        FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        ft_home.replace(R.id.frame, fragment_home);
                                        ft_home.addToBackStack(null);
                                        ft_home.commit();
                                        fragment_home.setArguments(bundle);
                                    }
                                } else {
                                    Fragment fragment_home = new Fragment_Offline_Level();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("StageId", dataModel.getId());
                                    bundle.putSerializable("StageName", dataModel.getStage());
                                    bundle.putSerializable("StageImage", dataModel.getImage());
                                    FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    ft_home.replace(R.id.frame, fragment_home);
                                    ft_home.addToBackStack(null);
                                    ft_home.commit();
                                    fragment_home.setArguments(bundle);
                                }

                            }
                            else {
                                Fragment fragment_home = new Fragment_Offline_Level();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("StageId", dataModel.getId());
                                bundle.putSerializable("StageName", dataModel.getStage());
                                bundle.putSerializable("StageImage", dataModel.getImage());
                                FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                ft_home.replace(R.id.frame, fragment_home);
                                ft_home.addToBackStack(null);
                                ft_home.commit();
                                fragment_home.setArguments(bundle);
                            }

                        }
                    }
                    else {
                        Fragment fragment_home = new Fragment_Offline_Level();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("StageId", dataModel.getId());
                        bundle.putSerializable("StageName", dataModel.getStage());
                        bundle.putSerializable("StageImage", dataModel.getImage());
                        FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                        ft_home.replace(R.id.frame, fragment_home);
                        ft_home.addToBackStack(null);
                        ft_home.commit();
                        fragment_home.setArguments(bundle);
                    }

                }else{

                    if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
                        SweetAlt.OpenPaymentDialog(context, Constant.upgrade_course, new ClickListionerss() {
                            @Override
                            public void clickYes() {
                                context.startActivity(new Intent(context, PaymentActivity.class).putExtra("price",upgradePackage));
                            }
                            @Override
                            public void clickNo() {

                            }
                        });
                    }else{
                        if (position!=0){
                            if (dataModel.getStatus().equalsIgnoreCase("unlock")) {

                                if (dataModelList.get(position-1).getTotalQuizLevelQuestion().equals(dataModelList.get(position-1).getTotalQuizLevelAttampt())) {
                                    if (!dataModelList.get(position-1).getMemoryMapQuizPlayStatus().equalsIgnoreCase("true")) {
                                        if (!dataModelList.get(position-1).getTotalQuizLevelQuestion().equals(0)) {
                                            Intent intent = new Intent(context, MemoryMapQuiz.class);
                                            intent.putExtra("stage_id", dataModelList.get(position-1).getId());
                                            context.startActivity(intent);
                                        } else {
                                            Fragment fragment_home = new Fragment_Offline_Level();
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("StageId", dataModel.getId());
                                            bundle.putSerializable("StageName", dataModel.getStage());
                                            bundle.putSerializable("StageImage", dataModel.getImage());
                                            FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                            ft_home.replace(R.id.frame, fragment_home);
                                            ft_home.addToBackStack(null);
                                            ft_home.commit();
                                            fragment_home.setArguments(bundle);
                                        }
                                    } else {
                                        Fragment fragment_home = new Fragment_Offline_Level();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("StageId", dataModel.getId());
                                        bundle.putSerializable("StageName", dataModel.getStage());
                                        bundle.putSerializable("StageImage", dataModel.getImage());
                                        FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                        ft_home.replace(R.id.frame, fragment_home);
                                        ft_home.addToBackStack(null);
                                        ft_home.commit();
                                        fragment_home.setArguments(bundle);
                                    }

                                }
                                else {
                                    Fragment fragment_home = new Fragment_Offline_Level();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("StageId", dataModel.getId());
                                    bundle.putSerializable("StageName", dataModel.getStage());
                                    bundle.putSerializable("StageImage", dataModel.getImage());
                                    FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    ft_home.replace(R.id.frame, fragment_home);
                                    ft_home.addToBackStack(null);
                                    ft_home.commit();
                                    fragment_home.setArguments(bundle);
                                }

                            }
                        }
                        else {
                            Fragment fragment_home = new Fragment_Offline_Level();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("StageId", dataModel.getId());
                            bundle.putSerializable("StageName", dataModel.getStage());
                            bundle.putSerializable("StageImage", dataModel.getImage());
                            FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            ft_home.replace(R.id.frame, fragment_home);
                            ft_home.addToBackStack(null);
                            ft_home.commit();
                            fragment_home.setArguments(bundle);
                        }
                    }
                }




            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public OfflineStageItemBinding itemRowBinding;

        public ViewHolder(OfflineStageItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
