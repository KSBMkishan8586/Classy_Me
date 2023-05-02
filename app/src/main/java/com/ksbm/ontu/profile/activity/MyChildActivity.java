package com.ksbm.ontu.profile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityMyChildBinding;
import com.ksbm.ontu.personality_dev.adapter.PersonalityCategory_List_Adapter;
import com.ksbm.ontu.personality_dev.model.CategoryModel;
import com.ksbm.ontu.profile.adapter.MyChild_List_Adapter;
import com.ksbm.ontu.profile.model.MyChildModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.SituationQuizActivity;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class MyChildActivity extends AppCompatActivity {
    ActivityMyChildBinding binding;
    SessionManager sessionManager;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_my_child);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(MyChildActivity.this), MyChildActivity.this);
        // setContentView(R.layout.activity_situation_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_child);
        sessionManager = new SessionManager(this);
        context = this;

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Connectivity.isConnected(MyChildActivity.this)) {
            getMyChildList();
        } else {
            // utilities.dialogOK(getActivity(), getString(R.string.validation_title), getString(R.string.please_check_internet), getString(R.string.ok), false);
        }


    }

    @SuppressLint("CheckResult")
    private void getMyChildList() {
        final ProgressDialog progressDialog = new ProgressDialog(MyChildActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetChildList(sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MyChildModel>() {
                    @Override
                    public void onNext(MyChildModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                MyChild_List_Adapter friendsAdapter = new MyChild_List_Adapter(response.getChilds() ,MyChildActivity.this);
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(MyChildActivity.this, "Sorry", response.getMessage());

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


    @Override
    public void onBackPressed() {
        finish();
    }
}