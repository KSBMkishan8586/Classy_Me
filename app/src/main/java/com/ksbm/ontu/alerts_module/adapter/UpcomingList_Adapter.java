package com.ksbm.ontu.alerts_module.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.model.CalendarNotesModel;
import com.ksbm.ontu.alerts_module.model.UpcomingEventModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.CalendarNotesListBinding;
import com.ksbm.ontu.databinding.UpcomingEventListBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UpcomingList_Adapter extends RecyclerView.Adapter<UpcomingList_Adapter.ViewHolder> {
    private List<UpcomingEventModel.UpcomingNotesResponse> dataModelList;
    Context context;
    SessionManager sessionManager;

    public UpcomingList_Adapter(List<UpcomingEventModel.UpcomingNotesResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        sessionManager= new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UpcomingEventListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.upcoming_event_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UpcomingEventModel.UpcomingNotesResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);

        // holder.itemRowBinding.setItemClickListener(this);
        String[] splitStr = dataModel.getcDate().split("-");
        String finaldate=splitStr[2];
        holder.itemRowBinding.tvDate.setText(finaldate);
    /*    if (TextUtils.isEmpty(dataModel.getTitle())){
            holder.itemRowBinding.ivDelete.setVisibility(View.VISIBLE);
        }else {
            holder.itemRowBinding.ivDelete.setVisibility(View.GONE);
        }

        holder.itemRowBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //   deleteMyNotes(dataModel.getcNoteId(), position);
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public UpcomingEventListBinding itemRowBinding;

        public ViewHolder(UpcomingEventListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
