package com.ksbm.ontu.alerts_module;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.adapter.Noticeboard_Adapter;
import com.ksbm.ontu.alerts_module.adapter.Updateboard_Adapter;
import com.ksbm.ontu.alerts_module.model.NoticeBoardModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentUpdateAlertBinding;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class UpdateAlert_Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    FragmentUpdateAlertBinding binding;
    SessionManager sessionManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(UpdateAlert_Activity.this), UpdateAlert_Activity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_update_alert);

        context= UpdateAlert_Activity.this;
        sessionManager = new SessionManager(context);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SideBarFragment blankFragment = new SideBarFragment();
//                blankFragment.show(getSupportFragmentManager(),blankFragment.getTag());

                Intent intent = new Intent(UpdateAlert_Activity.this, DrawerActivity.class);
                startActivity(intent);
            }
        });

        getNewUpdateList();


    }

    @SuppressLint("CheckResult")
    private void getNewUpdateList() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetNewUpdateList(sessionManager.getLanguageid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NoticeBoardModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(NoticeBoardModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                Updateboard_Adapter friendsAdapter = new Updateboard_Adapter(response.getResponse(), context);
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

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

                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }


    @Override
    public void onRefresh() {
        binding.swipeToRefresh.setRefreshing(false);

        getNewUpdateList();
    }


    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}
