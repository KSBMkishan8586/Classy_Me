package com.ksbm.ontu.fundamental.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.WorkbookListBinding;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Splash;
import com.ksbm.ontu.fundamental.fragment.Fundamental_Workbook;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Workbook_Data;
import com.ksbm.ontu.fundamental.activity.VideoPlayer;
import com.ksbm.ontu.session.SessionManager;

import java.util.List;

import static com.ksbm.ontu.utils.Constant.WorkbookPlayStatusDone;
import static com.ksbm.ontu.utils.Constant.WorkbookPlayStatusIncomplete;

public class Workbook_Adapter extends RecyclerView.Adapter<Workbook_Adapter.ViewHolder> {
    private List<Fundamental_Workbook_Data> dataModelList;
    Context context;
    Fundamental_Workbook fundamental_workbook;
    SessionManager sessionManager;

    public Workbook_Adapter(List<Fundamental_Workbook_Data> dataModelList, Context ctx, Fundamental_Workbook fundamental_workbook) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.fundamental_workbook= fundamental_workbook;
        sessionManager= new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkbookListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.workbook_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fundamental_Workbook_Data dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (dataModel.getPlay_status().equalsIgnoreCase(WorkbookPlayStatusDone)){
            holder.itemRowBinding.ivRightTick.setVisibility(View.VISIBLE);
        }else if (dataModel.getPlay_status().equalsIgnoreCase(WorkbookPlayStatusIncomplete)){
            holder.itemRowBinding.ivRightTick.setVisibility(View.GONE);
        }


        holder.itemRowBinding.llToodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.save_workbook_data(dataModel);
               // Toast.makeText(context, ""+sessionManager.getWorkbook().getWorkbookId(), Toast.LENGTH_SHORT).show();

               // fundamental_workbook.OpenVideoPlayer(true);
                if (dataModel.getOther_link()!=null && !dataModel.getOther_link().equalsIgnoreCase("") ||
                        dataModel.getBanner_file()!=null && !dataModel.getBanner_file().equalsIgnoreCase("")){

                    Intent intent= new Intent(context, VideoPlayer.class);
                    if (dataModel.getOther_link()!=null && !dataModel.getOther_link().equalsIgnoreCase("")){
                        intent.putExtra("FilePath", dataModel.getOther_link());
                        intent.putExtra("FilePathOther", true);
                    }else {
                        intent.putExtra("FilePath", dataModel.getBanner_file());
                        intent.putExtra("FilePathOther", false);
                    }
                    intent.putExtra("QuizPlay", true);
                    context.startActivity(intent);
                }else {
                    Intent intent= new Intent(context, Fundamental_Quiz_Splash.class);
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
        public WorkbookListBinding itemRowBinding;

        public ViewHolder(WorkbookListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
