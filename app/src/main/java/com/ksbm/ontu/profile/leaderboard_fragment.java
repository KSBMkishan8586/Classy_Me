package com.ksbm.ontu.profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.databinding.FragmentLeaderboardBinding;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.profile.activity.AllCompetitionList;
import com.ksbm.ontu.profile.adapter.Leaderboard_Adapter;
import com.ksbm.ontu.profile.adapter.PrizesAdapter;
import com.ksbm.ontu.profile.model.LeaderBoardModel;
import com.ksbm.ontu.profile.model.MyPrize;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class leaderboard_fragment extends Fragment {
    FragmentLeaderboardBinding binding;
    SessionManager sessionManager;
    String leaderboard_type = "school";
    Context context;
    int page_no = 0;
    List<LeaderBoardModel.LeaderBoardResponse> leaderBoardResponseList = new ArrayList<>();

    List<MyPrize.MyPrizeResponse> myprizeResponseList = new ArrayList<>();

    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leaderboard, container, false);
        View view = binding.getRoot();//using data binding
        context = getActivity();
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());

        if (!sessionManager.getUser().getRoleid().equalsIgnoreCase(Constant.studend_role_id)) {
            binding.tvSchoolState.setText("State");
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        if (meraSharedPreferance.isOpenMarketGet()){
            binding.tvSchoolState.setVisibility(View.GONE);
            leaderboard_type = "country";
            binding.tvCountry.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_blue_bg));
            binding.tvSchoolState.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_sky_blue));
            getprizeList("country");
            getLeaderboardList();
        }else{
            binding.tvSchoolState.setVisibility(View.VISIBLE);
            getLeaderboardList();
            getprizeList("school");
        }

        binding.tvSchoolState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaderboard_type = "school";
                binding.tvSchoolState.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_blue_bg));
                binding.tvCountry.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_sky_blue));
                getprizeList("school");
                getLeaderboardList();
            }
        });

        binding.tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaderboard_type = "country";
                binding.tvCountry.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_blue_bg));
                binding.tvSchoolState.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_sky_blue));
                getprizeList("country");
                getLeaderboardList();

            }
        });

        binding.tvCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllCompetitionList.class);
                startActivity(intent);

            }
        });

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Connectivity.isConnected(context)) {
                    getLeaderboardList();
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
//                blankFragment.show(getActivity().getSupportFragmentManager(),blankFragment.getTag());

                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @SuppressLint("CheckResult")
    private void getLeaderboardList() {

        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetLeaderBoard(sessionManager.getUser().getUserid(), String.valueOf(page_no), leaderboard_type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LeaderBoardModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(LeaderBoardModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                leaderBoardResponseList.clear();
                                leaderBoardResponseList = response.getResponse();

                                Leaderboard_Adapter friendsAdapter = new Leaderboard_Adapter(leaderBoardResponseList, getActivity());
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", "" + e.toString());
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

    private void getprizeList(String type) {

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAllPrize(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MyPrize>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(MyPrize response) {
                        //Handle logic
                        try {
                            //progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                myprizeResponseList.clear();
                                myprizeResponseList = response.getResponse();
                                Collections.sort(myprizeResponseList, new Comparator<MyPrize.MyPrizeResponse>() {
                                    @Override
                                    public int compare(MyPrize.MyPrizeResponse o1, MyPrize.MyPrizeResponse o2) {
                                        return o1.getRank().compareTo(o2.getRank());
                                    }
                                });
                                PrizesAdapter prizeAdapter = new PrizesAdapter(myprizeResponseList, getActivity());
                                //binding.setPrizeAdapter(prizeAdapter);//set databinding adapter
                                binding.recyclerprize.setAdapter(prizeAdapter);
                                prizeAdapter.notifyDataSetChanged();

                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());
                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            Log.e("stage_crash", "" + e.toString());
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

}
