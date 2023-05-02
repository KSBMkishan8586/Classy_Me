package com.ksbm.ontu.alerts_module;

import static com.ksbm.ontu.BR.adapter;

import static java.security.AccessController.getContext;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.adapter.NotesList_Adapter;
import com.ksbm.ontu.alerts_module.model.CalendarNotesDateModel;
import com.ksbm.ontu.alerts_module.model.CalendarNotesModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityCalendarNotesListBinding;
import com.ksbm.ontu.foundation.adapter.BookMarkAdapter;
import com.ksbm.ontu.foundation.model.BookMarkModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CalendarNotesListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ActivityCalendarNotesListBinding binding;
    SessionManager sessionManager;
    Context context;
//    NotesList_Adapter notesList_adapter;
    String Selected_Date;

    public static List<CalendarNotesModel> personality=new ArrayList<CalendarNotesModel>();
    List<CalendarNotesModel> personalityModels=new ArrayList<CalendarNotesModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_calendar_notes_list);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(CalendarNotesListActivity.this), CalendarNotesListActivity.this);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_calendar_notes_list);
        context = CalendarNotesListActivity.this;

        sessionManager = new SessionManager(context);

//        try {
//            GridLayoutManager manager=new GridLayoutManager((Context) this,2);
//            binding.recyclelist.setLayoutManager(manager);
//            binding.recyclelist.setNestedScrollingEnabled(false);
//            notesList_adapter = new NotesList_Adapter(getContext(),personalityModels,binding.recyclelist);
//            binding.recyclelist.setAdapter(notesList_adapter);
//        }
//        catch (Exception e){
//
//        }



        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent() != null) {
            Selected_Date = getIntent().getStringExtra("SelectedDate");
        }

        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CalendarNotesAddActivity.class);
                intent.putExtra("SelectedDate", Selected_Date);
                startActivity(intent);
            }
        });




    }



    @SuppressLint("CheckResult")
    private void getAllNotes() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetallNotesList(sessionManager.getUser().getUserid(), Selected_Date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CalendarNotesModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(CalendarNotesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                NotesList_Adapter friendsAdapter = new NotesList_Adapter(response.getResponse(), context);
                                binding.setAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", "" + e.toString());
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
    public void onRefresh() {
        if (Connectivity.isConnected(context)) {
            getAllNotes();
        } else {
            Toast.makeText(context, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        binding.swipeToRefresh.setRefreshing(false);
    }


    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }


    @Override
    protected void onResume() {

        if (Connectivity.isConnected(context)) {
            getAllNotes();
        } else {
            Toast.makeText(context, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

        super.onResume();
    }
}