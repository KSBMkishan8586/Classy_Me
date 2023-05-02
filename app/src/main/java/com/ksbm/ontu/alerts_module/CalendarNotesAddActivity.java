package com.ksbm.ontu.alerts_module;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.adapter.NotesList_Adapter;
import com.ksbm.ontu.alerts_module.model.CalendarNotesModel;
import com.ksbm.ontu.alerts_module.CalendarNotesListActivity;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityCalendarNotesAddBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CalendarNotesAddActivity extends AppCompatActivity {
    ActivityCalendarNotesAddBinding binding;
    SessionManager sessionManager;
    Context context;
    String Selected_Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_calendar_notes_add);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(CalendarNotesAddActivity.this), CalendarNotesAddActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar_notes_add);
        context= CalendarNotesAddActivity.this;

        sessionManager = new SessionManager(context);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent()!= null){
            Selected_Date= getIntent().getStringExtra("SelectedDate");
        }

        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Et_title = binding.etTitle.getText().toString();
                String Et_description = binding.etDescription.getText().toString();

                if (!Et_title.isEmpty() && !Et_description.isEmpty()){
                    addNotes(Et_title, Et_description);
                }else {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @SuppressLint("CheckResult")
    private void addNotes(String et_title, String et_description) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.AddNotesList(sessionManager.getUser().getUserid(), Selected_Date, et_title, et_description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                             finish();

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
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }


}