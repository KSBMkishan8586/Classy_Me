package com.ksbm.ontu.fundamental.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ksbm.ontu.R;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.fundamental.adapter.Toodle_List_Adapter;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentToodleBinding;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Toodle_Model;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Fundamental_Toodle_Fragment extends Fragment  {
    FragmentToodleBinding binding;
    SessionManager sessionManager;
    private String Fundamental_id, Fundamental_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_toodle, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        if (getArguments() != null) {
            Fundamental_id = getArguments().getString("Fundamental_id");
            Fundamental_title = getArguments().getString("Fundamental_title");

            binding.tvTitle.setText(Fundamental_title);
            if(Fundamental_title.length()>10)
            {
                binding.tvTitle.setTextSize(16f);
            }
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        if (Connectivity.isConnected(getActivity())) {
            GetToodleCourse();
        } else {
            SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        }


        binding.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.rlScreen,getActivity());
            }
        });

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeToRefresh.setRefreshing(true);
                if (Connectivity.isConnected(getActivity())) {
                    GetToodleCourse();
                } else {
                    SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
                }
            }
        });

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SideBarFragment blankFragment = new SideBarFragment();
//                blankFragment.show(getActivity().getSupportFragmentManager(),blankFragment.getTag());
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

    @SuppressLint("CheckResult")
    private void GetToodleCourse() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        Log.d("GetFundamentalToodle",Fundamental_id+" , "+sessionManager.getLanguageid());

        apiInterface.GetFundamentalToodle(sessionManager.getLanguageid(), Fundamental_id, sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Fundamental_Toodle_Model>() {
                    @Override
                    public void onNext(Fundamental_Toodle_Model response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                Toodle_List_Adapter friendsAdapter = new Toodle_List_Adapter(response.getResponse() ,getActivity());
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                 SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }
                            binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                        binding.swipeToRefresh.setRefreshing(false);

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


}
