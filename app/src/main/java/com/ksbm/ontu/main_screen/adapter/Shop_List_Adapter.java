package com.ksbm.ontu.main_screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ShopListBinding;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.main_screen.model.MyShopCoin;
import com.ksbm.ontu.main_screen.model.PersonalityModel;

import java.text.DecimalFormat;
import java.util.List;

public class Shop_List_Adapter  extends RecyclerView.Adapter<Shop_List_Adapter.ViewHolder> {
    private List<MyShopCoin.MyShopResponse> dataModelList;
    Context context;

    public Shop_List_Adapter(List<MyShopCoin.MyShopResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShopListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.shop_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyShopCoin.MyShopResponse dataModel = dataModelList.get(position);
        int coin = Integer.parseInt(dataModel.getCoins());
        int extra_coin = Integer.parseInt(dataModel.getExtra_coins());
        double eq1 = extra_coin*100;
        double eq2 = eq1/coin;
        String eq3 = new DecimalFormat("##.#").format(eq2);
        Log.d("fadsfds1",coin+"");
        Log.d("fadsfds2",extra_coin+"");
        Log.d("fadsfds3",eq3+"");
        dataModel.setExtra_percent(eq3+"%");

        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);




         holder.itemRowBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context, PaymentActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 intent.putExtra("price",dataModelList.get(position).getPrice());
                 context.startActivity(intent);
             }
         });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ShopListBinding itemRowBinding;

        public ViewHolder(ShopListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
