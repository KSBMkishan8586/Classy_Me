package com.ksbm.ontu.main_screen.fragment;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.main_screen.adapter.Shop_List_Adapter;
import com.ksbm.ontu.databinding.FragmentShopBinding;
import com.ksbm.ontu.main_screen.model.MyShopCoin;
import com.ksbm.ontu.main_screen.model.PersonalityModel;
import com.ksbm.ontu.profile.adapter.PrizesAdapter;
import com.ksbm.ontu.profile.model.MyPrize;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ShopFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    FragmentShopBinding binding;
    SessionManager sessionManager;
    List<MyShopCoin.MyShopResponse> myshopResponseList= new ArrayList<>();
    MeraSharedPreferance meraSharedPreferance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false);
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        binding.swipeToRefresh.setOnRefreshListener(this);

        try {
             ((MainActivity) getActivity()).removeBottomNavigationLabels( 3);
            ((MainActivity) getActivity()).CheckBottom(3);
        } catch (Exception e) {
        }

        getShopList();


        binding.relToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.llscreen,getActivity());
            }
        });

        if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
            binding.upgradeTab.setVisibility(View.VISIBLE);
            binding.upgradeTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), PaymentActivity.class).putExtra("price",upgradePackage));
                }
            });


        }else{
            binding.upgradeTab.setVisibility(View.GONE);
        }


        return view;
    }




    private void getShopList() {

//        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAllShop("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MyShopCoin>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(MyShopCoin response) {
                        //Handle logic
                        try {
                            //progressDialog.dismiss();
                             Log.e("result_my_test", "" + response);
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                myshopResponseList.clear();
                                myshopResponseList= response.getResponse();

                                Shop_List_Adapter prizeAdapter = new Shop_List_Adapter(myshopResponseList, getActivity());
                                binding.setShopAdapter(prizeAdapter);//set databinding adapter
//                                binding.recyclerprize.setAdapter(prizeAdapter);
                                prizeAdapter.notifyDataSetChanged();
                                binding.swipeToRefresh.setRefreshing(false);

                            } else {
                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", ""+e.toString());
                            //progressDialog.dismiss();
                        }
                        //  binding.swipeToRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //progressDialog.dismiss();
                        Log.e("mr_product_error", e.toString());


                    }

                    @Override
                    public void onComplete() {
                        //progressDialog.dismiss();
                    }
                });

    }


    @Override
    public void onRefresh() {
        binding.swipeToRefresh.setRefreshing(false);

        getShopList();
    }

}
