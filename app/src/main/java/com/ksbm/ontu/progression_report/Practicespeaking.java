package com.ksbm.ontu.progression_report;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentDetailedReportBinding;
import com.ksbm.ontu.main_screen.adapter.DetailedReportAdapter;
import com.ksbm.ontu.progression_report.model.DetailedReport;
import com.ksbm.ontu.progression_report.model.Re;
import com.ksbm.ontu.session.SessionManager;

import java.text.DecimalFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Practicespeaking extends Fragment {

    TextView tv_score_calculator, tv_workbook_obtain ,total_coin_earned ,total_coins,tv_total_obtain;
    SessionManager sessionManager;

    RecyclerView report_rv;

    FragmentDetailedReportBinding binding ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed_report, container, false);
        binding = FragmentDetailedReportBinding.inflate(inflater);
        report_rv = view.findViewById(R.id.report_rv);
        report_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        sessionManager = new SessionManager(getActivity());

        tv_score_calculator = view.findViewById(R.id.tv_score_calculator);
        tv_workbook_obtain = view.findViewById(R.id.tv_workbook_obtain);
        total_coin_earned = view.findViewById(R.id.total_coin_earned);
        tv_total_obtain = view.findViewById(R.id.tv_total_obtain);
        total_coins = view.findViewById(R.id.total_coins);



        tv_score_calculator.setText(getArguments().getString("tab_name")+"");
//        binding.totalCoinEarned.setText("Hllo");
        view.findViewById(R.id.iv_back).setOnClickListener(view1 -> getActivity().onBackPressed());

        getDetailedReport();

        return view;
    }


    @SuppressLint("CheckResult")
    private void getDetailedReport() {

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.getDetailedReport(sessionManager.getUser().getUserid(),"practice_speaking")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DetailedReport>() {
                    @Override
                    public void onNext(DetailedReport response) {
                        DecimalFormat dform = new DecimalFormat("#.####");
                        //Handle logic
                        try {

                            Log.d("Detailed Report", response.toString());
//                            Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus()) {

                                tv_workbook_obtain.setText(response.getPercentage()+"%");
                                report_rv.setAdapter(new DetailedReportAdapter(getActivity(),response.getRes()));

                                int totalCoin = 0 ;  int totalEarnedCoin = 0 ; float totalScore = 0f;


                                for (Re element : response.getRes()){

                                    Log.d("Totals", element.getTotalCoins() + " " + element.getEarnedCoins() + " "+ element.getEarnedCoins());
                                    totalCoin = totalCoin + Integer.parseInt(element.getTotalCoins());
                                    totalEarnedCoin = totalEarnedCoin + Integer.parseInt(element.getEarnedCoins());
                                    totalScore =  totalScore + Float.parseFloat(element.getScore());

                                }
                                total_coin_earned.setText(String.valueOf(totalEarnedCoin));
                                total_coins.setText(String.valueOf(totalCoin));
                                tv_total_obtain.setText(dform.format(totalScore/response.getRes().size())+ " %");

                                binding.totalCoinEarned.setText(Integer.toString(totalEarnedCoin));
                                Log.d("Totals", String.valueOf(totalCoin + totalEarnedCoin + totalScore));
                                Log.d("coin Totals", String.valueOf(totalEarnedCoin));


                            } else {

                                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            // progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        //  progressDialog.dismiss();
//                        Toast.makeText(requireContext(), "this content", Toast.LENGTH_SHORT).show();

                        Log.e("mr_product_error", e.toString());

                    }

                    @Override
                    public void onComplete() {
                        //  progressDialog.dismiss();
                    }
                });



    }




}