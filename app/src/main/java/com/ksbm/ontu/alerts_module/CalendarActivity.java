package com.ksbm.ontu.alerts_module;

import static com.ksbm.ontu.api_client.Base_Url.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.type.DateTime;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.adapter.NotesList_Adapter;
import com.ksbm.ontu.alerts_module.adapter.UpcomingList_Adapter;
import com.ksbm.ontu.alerts_module.model.CalendarNotesDateModel;
import com.ksbm.ontu.alerts_module.model.CalendarNotesModel;
import com.ksbm.ontu.alerts_module.model.UpcomingEventModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityCalendarBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarActivity extends AppCompatActivity {
    ActivityCalendarBinding binding;
    SessionManager sessionManager;
    Context context;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_calendar);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(CalendarActivity.this), CalendarActivity.this);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_calendar);
        context= CalendarActivity.this;

        sessionManager = new SessionManager(context);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Calendar calendar = Calendar.getInstance();
        String monthname=(String)android.text.format.DateFormat.format("yyyy , MMM", new Date());
        binding.tvMonthYear.setText(monthname);
        binding.calendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
              //  Toast.makeText(CalendarActivity.this, String.format("%d-%d-%d", date.getMonth(), date.getDay(), date.getYear()), Toast.LENGTH_SHORT).show();
                String selected_date = String.format("%d-%d-%d",  date.getYear(), date.getMonth(), date.getDay());
                Intent intent = new Intent((Context) CalendarActivity.this, CalendarNotesListActivity.class);
                intent.putExtra("SelectedDate",selected_date );
                startActivity(intent);

            }
        });

//        binding.calendar.markDate(2021, 10, 10);
//        binding.calendar.markDate(2021, 10, 11);
//        binding.calendar.markDate(2021, 10, 16);
//        binding.calendar.markDate(2021, 10, 28);
        getUpcomingEvent();
    }

    private void getMonthYear() {

        binding.calendar.setOnMonthChangeListener(new OnMonthChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onMonthChange(int year, int month) {
               // Toast.makeText(CalendarActivity.this, String.format("%d-%d", year, month), Toast.LENGTH_SHORT).show();

                binding.tvMonthYear.setText(""+ year + ", " + getMonth(month));
            }
        });
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    @SuppressLint("CheckResult")
    private void getAllNotesDates() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetallNotesMarkDate(sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CalendarNotesDateModel>() {
                    @Override
                    public void onNext(CalendarNotesDateModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                             Log.e("result_my_test calendar", "" + response.getResponse());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                for(int i=0; i<response.getResponse().size(); i++){
                                    Date date = format.parse(response.getResponse().get(i).getcDate());
                                    String day  = (String) DateFormat.format("dd",   date);
                                    String monthNumber = (String) DateFormat.format("MM",   date);
                                    String year = (String) DateFormat.format("yyyy",  date);

                                    binding.calendar.markDate(Integer.parseInt(year), Integer.parseInt(monthNumber), Integer.parseInt(day));
                                }


                            } else {
                              //  SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());
                            }

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
                            progressDialog.dismiss();
                        }

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


    @SuppressLint("CheckResult")
    private void getUpcomingEvent() {
        final ProgressDialog progressDialog = new ProgressDialog(CalendarActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.upcoming_event(API_KEY, sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UpcomingEventModel>() {
                    @Override
                    public void onNext(UpcomingEventModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response);
                            if (response.getStatus() == 1) {


                                UpcomingList_Adapter friendsAdapter = new UpcomingList_Adapter(response.getResponse(), context);
                                binding.setAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();


                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());
                            }
                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

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

    @Override
    protected void onResume() {

        getMonthYear();

        if (Connectivity.isConnected(context)){
            getAllNotesDates();
        }else {
            Toast.makeText(context, "Please check Internet", Toast.LENGTH_SHORT).show();
        }
        
        super.onResume();
    }


}