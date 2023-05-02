package com.ksbm.ontu.profile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityAllCompetitionListBinding;
import com.ksbm.ontu.profile.adapter.Competition_List_Adapter;
import com.ksbm.ontu.profile.adapter.MyChild_List_Adapter;
import com.ksbm.ontu.profile.model.CompetitionListModel;
import com.ksbm.ontu.profile.model.MyChildModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AllCompetitionList extends AppCompatActivity {
    ActivityAllCompetitionListBinding binding;
    SessionManager sessionManager;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_all_competition_list);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(AllCompetitionList.this), AllCompetitionList.this);
        // setContentView(R.layout.activity_situation_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_competition_list);
        sessionManager = new SessionManager(this);
        context = this;

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(AllCompetitionList.this)) {
            getCompetitionList();
        } else {
            // utilities.dialogOK(getActivity(), getString(R.string.validation_title), getString(R.string.please_check_internet), getString(R.string.ok), false);
        }

    }

    @SuppressLint("CheckResult")
    private void getCompetitionList() {
        final ProgressDialog progressDialog = new ProgressDialog(AllCompetitionList.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetCompetitionList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CompetitionListModel>() {
                    @Override
                    public void onNext(CompetitionListModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                Competition_List_Adapter friendsAdapter = new Competition_List_Adapter(response.getResponse() ,AllCompetitionList.this);
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(AllCompetitionList.this, "Sorry", response.getMessage());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
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
}