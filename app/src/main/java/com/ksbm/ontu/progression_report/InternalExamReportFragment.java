package com.ksbm.ontu.progression_report;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentInternalExamBinding;
import com.ksbm.ontu.progression_report.model.InternalExam;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InternalExamReportFragment extends Fragment {
    FragmentInternalExamBinding binding;
    SessionManager sessionManager;
    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_internal_exam, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());
        getInternalExamReport();
        binding.ivBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        if (meraSharedPreferance.isOpenMarketGet()){
            if (meraSharedPreferance.professionGet().equalsIgnoreCase("School Students")){
                if (meraSharedPreferance.ageGet().equalsIgnoreCase("Below 13")){
                    binding.internalExam.setText(getText(R.string.kids_learning));
                    binding.situationTv.setText(getText(R.string.havefun));
                    binding.tvWorksheet.setText(getText(R.string.kids_learning));
                    binding.tvSituation.setText(getText(R.string.havefun));
                    binding.timeDevotionRl.setVisibility(View.GONE);
                    binding.timeDevotionLl.setVisibility(View.GONE);

                }else if (meraSharedPreferance.ageGet().equalsIgnoreCase("Above 13")){
                    binding.internalExam.setText(getText(R.string.crash_course));
                    binding.situationTv.setText(getText(R.string.fundamental));
                    binding.tvWorksheet.setText(getText(R.string.crash_course));
                    binding.tvSituation.setText(getText(R.string.fundamental));
                    binding.timeDevotionRl.setVisibility(View.GONE);
                    binding.timeDevotionLl.setVisibility(View.GONE);

                }

            }else {

                binding.internalExam.setText(getText(R.string.crash_course));
                binding.situationTv.setText(getText(R.string.situation));
                binding.tvWorksheet.setText(getText(R.string.crash_course));
                binding.tvSituation.setText(R.string.situation);
                binding.timeDevotionRl.setVisibility(View.GONE);
                binding.timeDevotionLl.setVisibility(View.GONE);

            }
        }else{


            binding.timeDevotionRl.setVisibility(View.GONE);

//            sessionManager.getUser().
        }


        binding.internalExam.setOnClickListener(view12 -> {
            Fragment comment_f = new DetailedReportFrag();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
            Bundle args = new Bundle();
            // args.putBoolean("Pickup", false);
             args.putString("tab_name", binding.internalExam.getText().toString());
            comment_f.setArguments(args);
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, comment_f, "FreeCoinFragment").commit();
        });

        binding.situationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment comment_f = new have_fun();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                Bundle args = new Bundle();
                // args.putBoolean("Pickup", false);
                 args.putString("tab_name", binding.situationTv.getText().toString());
                comment_f.setArguments(args);
                transaction.addToBackStack(null);
                transaction.replace(R.id.frame, comment_f, "FreeCoinFragment").commit();
            }
        });




        return view;
    }

    @SuppressLint("CheckResult")
    private void getInternalExamReport() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.report_internal_exam("123456", sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
//                            Log.d("result_my_test", "" + response);

                            setData(response);

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

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

    private void setData(JsonObject response) {
        Gson gson = new Gson();
        InternalExam object = gson.fromJson(response, InternalExam.class);
        Log.d("result_my_test", "" + object);
        binding.tvWorkbookObtain.setText(object.getKidsLearning() + "%");
        binding.scoreA.setText(object.getKidsLearning() + "%");
        binding.ScoreB.setText(object.getHaveFun() + "%");
        binding.tvWorksheetObtain.setText(object.getScoreCalculater().getKidsLearning() + "%");
        binding.tvSituationObtain.setText(object.getHaveFun() + "%");
        binding.tvSituationObtainSc.setText(object.getScoreCalculater().getHaveFun() + "%");

        Log.e("=======>", "rsult"+ object.getScoreCalculater().getKidsLearning());
        Log.e("=====>", "resu;t"+ object.getScoreCalculater().getHaveFun());
        float total =Float.parseFloat(object.getScoreCalculater().getKidsLearning()) +Float.parseFloat( object.getScoreCalculater().getHaveFun());
        Float foo = Float.valueOf(String.format("%.2f", total));
        binding.tvTotalObtain.setText(foo+ "%");

        float total2 = Float.parseFloat(object.getKidsLearning()) + Float.parseFloat(object.getHaveFun());
        Float foo2 = Float.valueOf(String.format("%.2f",total2));
        binding.totalScore.setText(foo2 + " %");



//        binding.tvWorkbookObtain.setText(object.getKidsLearning()+"%");
//        binding.tvWorkbookTotal.setText(object.getKidsLearningTotal()+"%");
//        binding.tvSituationObtain.setText(object.getResponse().getHaveFunTotalObtain()+"%");
//        binding.tvSituationTotal.setText(object.getHaveFun()+"%");

//        binding.tvWorksheetWeightage.setText(object.getResponse().getScoreCalculator().getPracticeOfflineWeightage()+"%");
//        binding.tvWorksheetObtain.setText(object.getResponse().getScoreCalculator().getPracticeOfflineObtain()+"%");
//        binding.tvSituationWeightagr.setText(object.getResponse().getScoreCalculator().getPersonalityDevelopmentWeightage()+"%");
//        binding.tvSituationObtainSc.setText(object.getResponse().getScoreCalculator().getPersonalityDevelopmentObtain()+"%");
//
//        binding.tvTotalObtain.setText(object.getResponse().getScoreCalculator().getTotalObtain()+"%");

    }
}
