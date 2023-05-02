package com.ksbm.ontu.foundation.adapter;

import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationBasicsListBinding;
import com.ksbm.ontu.foundation.activity.Foundation_Splash;
import com.ksbm.ontu.foundation.model.FoundationCourseModel;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class FoundationBasics_Adapter extends RecyclerView.Adapter<FoundationBasics_Adapter.ViewHolder> {
    private List<FoundationCourseModel.FoundationCourseResponse> dataModelList;
    Context context;
    SessionManager sessionManager;
    MeraSharedPreferance meraSharedPreferance;

    public FoundationBasics_Adapter(List<FoundationCourseModel.FoundationCourseResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        sessionManager = new SessionManager(context);
        meraSharedPreferance = MeraSharedPreferance.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FoundationBasicsListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.foundation_basics_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoundationCourseModel.FoundationCourseResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        //holder.itemRowBinding.setModel(dataModel);
        holder.itemRowBinding.tvTitleCourse.setText(dataModel.getFoundationName());
        Utils.setRainbowColor(holder.itemRowBinding.tvTitleCourse);

        holder.itemRowBinding.tvPrize.setText("Reward: " + dataModel.getReward());

        if (!dataModelList.get(position).getEarning().equalsIgnoreCase("0")) {
            holder.itemRowBinding.tvEarning.setText("Earnings: " + dataModelList.get(position).getEarning());
        } else {
            holder.itemRowBinding.tvEarning.setText("Earnings: 00");
        }

        Utils.setRandomBgImage(holder.itemRowBinding.relItem, holder.itemRowBinding.llBottom, context);

        Glide.with(context)
                .load(dataModelList.get(position).getIconImage())
                //.centerCrop()
                .placeholder(R.drawable.basics_foundation)
                .error(R.drawable.basics_foundation)
                .into(holder.itemRowBinding.ivFoundationImg);

        holder.itemRowBinding.relItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position<=3){
                    Constant.foundation_id = dataModelList.get(position).getFoundationId();
                    Constant.foundation_name = dataModelList.get(position).getFoundationName();
                    Speach_Record_Activity.speakOut(context, dataModelList.get(position).getFoundationName(), false);
                    Intent intent = new Intent(context, Foundation_Splash.class);
                    intent.putExtra("foundation_id", dataModelList.get(position).getFoundationId());
                    intent.putExtra("foundation_name", dataModelList.get(position).getFoundationName());
                    context.startActivity(intent);
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
                        Constant.foundation_id = dataModelList.get(position).getFoundationId();
                        Constant.foundation_name = dataModelList.get(position).getFoundationName();
                        Speach_Record_Activity.speakOut(context, dataModelList.get(position).getFoundationName(), false);
                        Intent intent = new Intent(context, Foundation_Splash.class);
                        intent.putExtra("foundation_id", dataModelList.get(position).getFoundationId());
                        intent.putExtra("foundation_name", dataModelList.get(position).getFoundationName());
                        context.startActivity(intent);
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
        public FoundationBasicsListBinding itemRowBinding;

        public ViewHolder(FoundationBasicsListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    // method for filtering our recyclerview items.
    public void filterList(List<FoundationCourseModel.FoundationCourseResponse> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModelList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


}
