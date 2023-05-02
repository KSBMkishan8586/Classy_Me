package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationListBinding;
import com.ksbm.ontu.foundation.fragment.Foundation_Basics_Fragment;
import com.ksbm.ontu.foundation.activity.Foundation_Splash;
import com.ksbm.ontu.foundation.model.FoundationTypeModel;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.wooplr.spotlight.SpotlightView;

import java.util.List;

import static com.ksbm.ontu.utils.Constant.Body_Parts;
import static com.ksbm.ontu.utils.Constant.Colors;
import static com.ksbm.ontu.utils.Constant.Date;
import static com.ksbm.ontu.utils.Constant.Direction;
import static com.ksbm.ontu.utils.Constant.Time;
import static com.ksbm.ontu.utils.Constant.upgradePackage;

public class FoundationList_Adapter extends RecyclerView.Adapter<FoundationList_Adapter.ViewHolder> {
    private List<FoundationTypeModel> dataModelList;
    Context context;
    Activity activity;
    SessionManager sessionManager;

    public FoundationList_Adapter(List<FoundationTypeModel> dataModelList, Context ctx, Activity activity) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.activity = activity;
        sessionManager = new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FoundationListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.foundation_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoundationTypeModel dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);
        Glide.with(context).load(dataModel.getDrawable()).into(holder.itemRowBinding.ivImage);

        if (position == 0) {
            holder.itemRowBinding.relBg.setBackgroundResource(R.drawable.basic_button);
        } else if (position == 1) {
            holder.itemRowBinding.relBg.setBackgroundResource(R.drawable.direction_button);
        } else if (position == 2) {
            holder.itemRowBinding.relBg.setBackgroundResource(R.drawable.basic_button);
        } else if (position == 3) {
            holder.itemRowBinding.relBg.setBackgroundResource(R.drawable.body_parts_button);
        } else if (position == 4) {
            holder.itemRowBinding.relBg.setBackgroundResource(R.drawable.date_time_button);
        } else if (position == 5) {
            holder.itemRowBinding.relBg.setBackgroundResource(R.drawable.direction_button);
        }
        holder.itemRowBinding.relFoundation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, holder.itemRowBinding.tvFoundationTitle.getText().toString(), false);
                if (position == 0) {
                    Fragment fragment_home = new Foundation_Basics_Fragment();
                    Bundle bundle = new Bundle();
                    // bundle.putString("Title", binding.tvFoundationTitle.getText().toString());
                    FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.frame, fragment_home);
                    ft_home.addToBackStack(null);
                    ft_home.commit();
                    fragment_home.setArguments(bundle);

                } else if (position == 1) {
                    Constant.foundation_id = Direction;
                    Constant.foundation_name = "Directions";

                    Intent intent = new Intent(context, Foundation_Splash.class);
                    intent.putExtra("foundation_id", Constant.foundation_id);
                    intent.putExtra("foundation_name", Constant.foundation_name);
                    context.startActivity(intent);

                } else if (position == 2) {
                    Constant.foundation_id = Date;
                    Constant.foundation_name = "Date";

                    Intent intent = new Intent(context, Foundation_Splash.class);
                    intent.putExtra("foundation_id", Constant.foundation_id);
                    intent.putExtra("foundation_name", Constant.foundation_name);
                    context.startActivity(intent);
                } else if (position == 3) {
                    Constant.foundation_id = Time;
                    Constant.foundation_name = "Time";
                    Intent intent = new Intent(context, Foundation_Splash.class);
                    intent.putExtra("foundation_id", Constant.foundation_id);
                    intent.putExtra("foundation_name", Constant.foundation_name);
                    context.startActivity(intent);
                } else if (position == 4) {
                    Constant.foundation_id = Colors;
                    Constant.foundation_name = "Colors";
                    Intent intent = new Intent(context, Foundation_Splash.class);
                    intent.putExtra("foundation_id", Constant.foundation_id);
                    intent.putExtra("foundation_name", Constant.foundation_name);
                    context.startActivity(intent);
                } else if (position == 5) {
                    Constant.foundation_id = Constant.Body_Partsoriginal;
                    Constant.foundation_name = "Body Parts";
                    Intent intent = new Intent(context, Foundation_Splash.class);
                    intent.putExtra("foundation_id", Constant.foundation_id);
                    intent.putExtra("foundation_name", Constant.foundation_name);
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoundationListBinding itemRowBinding;

        public ViewHolder(FoundationListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
