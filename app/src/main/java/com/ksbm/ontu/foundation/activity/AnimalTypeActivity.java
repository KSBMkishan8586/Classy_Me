package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityAnimalBinding;
import com.ksbm.ontu.databinding.ActivityAnimalTypeBinding;
import com.ksbm.ontu.foundation.adapter.AlphabetAdapter;
import com.ksbm.ontu.foundation.adapter.AnimalTypeAdapter;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.foundation.model.AnimalModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.practice_offline.adapter.Offline_Stage_Adapter;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class AnimalTypeActivity extends AppCompatActivity {
    ActivityAnimalTypeBinding binding;
    SessionManager sessionManager;
    Context context;
    String SubType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_animal_type);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_animal_type);



        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        context= AnimalTypeActivity.this;
        sessionManager = new SessionManager(this);
        binding.header.tvTitle.setText(Constant.foundation_name);

        if (getIntent()!=null){
            SubType= getIntent().getStringExtra("SubType");
        }


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.header.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Utils.screenShot(binding.fullScreen, getWindow().getContext());
                }
            }
        });




        if (Connectivity.isConnected(context)) {
            if (SubType.equalsIgnoreCase("Fruits")){
                GetFruitsTypeList();
            }else {
                GetAnimalTypeList();
            }
        } else {
            SweetAlt.showErrorMessage(context, "Sorry", "You have no internet!");
        }



    }

     @SuppressLint("CheckResult")
    private void GetFruitsTypeList() {
         final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
         progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
         progressDialog.show();

         Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

         apiInterface.GetFruitTypeList(sessionManager.getUser().getUserid(), sessionManager.getLanguageid(), Constant.foundation_id)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeWith(new DisposableObserver<AnimalModel>() {
                     @Override
                     public void onNext(AnimalModel response) {
                         //Handle logic
                         try {
                             progressDialog.dismiss();
                             Log.e("result_my_test", "" + response.getMessage());
                             //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                             if (response.getStatus() == 1) {

                                 AnimalTypeAdapter friendsAdapter = new AnimalTypeAdapter(response.getResponse(), context);
                                 binding.setAdapter(friendsAdapter); //set databinding adapter
                                 friendsAdapter.notifyDataSetChanged();

                             } else {
                                 SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());

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


    @SuppressLint("CheckResult")
    private void GetAnimalTypeList() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAnimalList(sessionManager.getUser().getUserid(), sessionManager.getLanguageid(), Constant.foundation_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AnimalModel>() {
                    @Override
                    public void onNext(AnimalModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                AnimalTypeAdapter friendsAdapter = new AnimalTypeAdapter(response.getResponse(), context);
                                binding.setAdapter(friendsAdapter); //set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());

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