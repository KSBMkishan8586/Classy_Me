package com.ksbm.ontu.alerts_module.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.NoticeBoard_Details_Activity;
import com.ksbm.ontu.alerts_module.model.NoticeBoardModel;


import java.util.List;

public class Noticeboard_Adapter extends RecyclerView.Adapter<Noticeboard_Adapter.ViewHolder> {
    private List<NoticeBoardModel.NoticeBoardModelResponse> dataModelList;
    Context context;
    public String[] mColors = {
            "FFDCCE","FFF7B2","DCFEFF","FFCBF9","DDD5FE"
    };

    public Noticeboard_Adapter(List<NoticeBoardModel.NoticeBoardModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.noticeboard_list, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoticeBoardModel.NoticeBoardModelResponse dataModel = dataModelList.get(position);
    //    holder.bind(dataModel);
      //  holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        holder.tv_desc.setText(dataModel.getDescription());

        holder.tv_date.setText(String.valueOf(position+1));
        holder.ll_notice_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, NoticeBoard_Details_Activity.class);
                intent.putExtra("NoticeData", dataModel);
                context.startActivity(intent);
            }
        });

//        String color="#"+mColors[position];
        String color="#"+mColors[0];

        for(int c=1;c<mColors.length;c++)
        {

            holder.cardcolor.setCardBackgroundColor(Color.parseColor(color));

            if(c==mColors.length)
            {
                c=1;
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     //   public NoticeboardListBinding itemRowBinding;

       /* public ViewHolder(NoticeboardListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }*/
     //  private View view;
        TextView tv_date,tv_desc;
        CardView cardcolor;
        LinearLayout ll_notice_title;
        public ViewHolder(View itemView) {
            super(itemView);

        //    view = itemView.findViewById(R.id.noticeboard_list);
            tv_date = itemView.findViewById(R.id.tv_date);
            cardcolor = itemView.findViewById(R.id.cardcolor);
            ll_notice_title = itemView.findViewById(R.id.ll_notice_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);

        }
     /*   public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }*/
    }
}
