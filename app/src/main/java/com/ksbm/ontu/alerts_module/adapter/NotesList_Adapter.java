package com.ksbm.ontu.alerts_module.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.CalendarNotesListBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotesList_Adapter extends RecyclerView.Adapter<NotesList_Adapter.ViewHolder> {
    private List<CalendarNotesModel.CalendarNotesResponse> dataModelList;
    Context context;
    SessionManager sessionManager;

    public NotesList_Adapter(List<CalendarNotesModel.CalendarNotesResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        sessionManager= new SessionManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarNotesListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.calendar_notes_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CalendarNotesModel.CalendarNotesResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (dataModel.getAddedBy().equalsIgnoreCase("self")){
            holder.itemRowBinding.ivDelete.setVisibility(View.VISIBLE);
        }else {
            holder.itemRowBinding.ivDelete.setVisibility(View.GONE);
        }

        holder.itemRowBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMyNotes(dataModel.getcNoteId(), position);
            }
        });

    }


    @SuppressLint("CheckResult")
    private void deleteMyNotes(String getcNoteId, int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.DeleteMyNote(getcNoteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                dataModelList.remove(position);
                                notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
                            progressDialog.dismiss();
                        }
                        //  binding.swipeToRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());


                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CalendarNotesListBinding itemRowBinding;

        public ViewHolder(CalendarNotesListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
