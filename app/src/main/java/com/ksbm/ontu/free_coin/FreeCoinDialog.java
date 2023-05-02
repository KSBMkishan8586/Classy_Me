package com.ksbm.ontu.free_coin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.CustomAlertDialogBinding;
import com.ksbm.ontu.databinding.FragmentFreecoinBinding;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.model.FreeCoinModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class FreeCoinDialog extends AppCompatActivity {
    SessionManager sessionManager;
    FragmentFreecoinBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  setContentView(R.layout.activity_personality_quiz_dialog);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_freecoin);

        sessionManager = new SessionManager(this);

        binding.Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.rlScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.rlScreen,FreeCoinDialog.this);

            }
        });
    }

    @SuppressLint("CheckResult")
    private void getFreeCoinList() {

        final ProgressDialog progressDialog = new ProgressDialog(FreeCoinDialog.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetFreeCoinVideo(sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FreeCoinModel>() {
                    @Override
                    public void onNext(FreeCoinModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                FreeCoin_Adapter friendsAdapter = new FreeCoin_Adapter(response.getResponses(), FreeCoinDialog.this);
                                // binding.setMyAdapter(friendsAdapter);//set databinding adapter
                                binding.freecoinRecycler.setLayoutManager(new LinearLayoutManager(FreeCoinDialog.this));
                                binding.freecoinRecycler.setAdapter(friendsAdapter);
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                //  binding.swipeToRefresh.setVisibility(View.GONE);
                                //  SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

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
        finish();
        //super.onBackPressed();
    }

    @Override
    protected void onResume() {

        getFreeCoinList();
        super.onResume();
    }
}
