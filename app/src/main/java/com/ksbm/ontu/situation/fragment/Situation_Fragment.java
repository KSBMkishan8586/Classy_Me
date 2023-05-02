package com.ksbm.ontu.situation.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentSituationBinding;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.situation.adapter.Situation_List_Adapter;
import com.ksbm.ontu.situation.model.SituationModel;
import com.ksbm.ontu.situation.model.SituationModelResponse;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Situation_Fragment extends Fragment {
    FragmentSituationBinding binding;
    SessionManager sessionManager;
    Situation_List_Adapter friendsAdapter;
    public static String bg_image;
    List<SituationModelResponse> situationModelResponseList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_situation, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        if (Constant.havefun) {
            binding.tvTitle.setText(R.string.havefun);
        } else {
            binding.tvTitle.setText(R.string.situation);
        }


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        if (Connectivity.isConnected(getActivity())) {
            GetSituationsList();
        } else {
            SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        }

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });

        binding.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.rlscreen, getActivity());
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

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<SituationModelResponse> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (SituationModelResponse item : situationModelResponseList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getSituationName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            // Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            friendsAdapter.filterList(filteredlist);
        }
    }

    @SuppressLint("CheckResult")
    private void GetSituationsList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetSituationList(sessionManager.getLanguageid(),sessionManager.getUser().getUserid(), sessionManager.getUser().getClassid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SituationModel>() {
                    @Override
                    public void onNext(SituationModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

//                                Glide.with(getActivity())
//                                        .load(response.getBg_image()).placeholder(R.drawable.whitebackground)
//                                        .into(binding.imgscreen);
                                bg_image = response.getBg_image();
                                situationModelResponseList = response.getResponse();
                                friendsAdapter = new Situation_List_Adapter(situationModelResponseList, getActivity());
                                binding.setAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

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
}
