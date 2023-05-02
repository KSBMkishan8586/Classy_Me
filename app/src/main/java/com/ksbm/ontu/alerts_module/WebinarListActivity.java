package com.ksbm.ontu.alerts_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.adapter.WebinarList_Adapter;
import com.ksbm.ontu.alerts_module.model.WebinarModel;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityWebinarListBinding;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class WebinarListActivity extends AppCompatActivity {
    ActivityWebinarListBinding binding;
    SessionManager sessionManager;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_webinar_list);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(WebinarListActivity.this), WebinarListActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webinar_list);
        context= WebinarListActivity.this;

        sessionManager = new SessionManager(context);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Connectivity.isConnected(context)) {
                    getWebinarList();
                } else {
                    SweetAlt.showErrorMessage(context, "Sorry", "You have no internet!");
                }
                binding.swipeToRefresh.setRefreshing(false);
            }
        });

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SideBarFragment blankFragment = new SideBarFragment();
//                blankFragment.show(getSupportFragmentManager(),blankFragment.getTag());

                Intent intent = new Intent(WebinarListActivity.this, DrawerActivity.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("CheckResult")
    private void getWebinarList() {
            final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.show();

            Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

            apiInterface.GetWebinarList(sessionManager.getUser().getUserid())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<WebinarModel>() {
                        @Override
                        public void onNext(WebinarModel response) {
                            //Handle logic
                            try {
                                progressDialog.dismiss();
                                // Log.e("result_my_test", "" + response.getMessage());
                                //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                if (response.getStatus() == 1) {
                                    WebinarList_Adapter friendsAdapter = new WebinarList_Adapter(response.getResponse(), context);
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
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {

        if(Connectivity.isConnected(context)){
            getWebinarList();
        }else {
            Toast.makeText(context, "Please Check Internet", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}