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
import com.ksbm.ontu.databinding.FragmentCocurriReportBinding;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.progression_report.model.ResponseDTO;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Co_Curricular_ReportFragment extends Fragment {
    FragmentCocurriReportBinding binding;
    SessionManager sessionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cocurri_report, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        binding.tvPersonalityDevelopmentTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment comment_f = new Practicespeaking();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                Bundle args = new Bundle();
                // args.putBoolean("Pickup", false);
                args.putString("tab_name", binding.tvPersonalityDevelopmentTab.getText().toString());
                comment_f.setArguments(args);
                transaction.addToBackStack(null);
                transaction.replace(R.id.frame, comment_f, "FreeCoinFragment").commit();
            }
        });
        binding.tvPracticeSpeakingTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment comment_f = new Practicespeaking();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
                Bundle args = new Bundle();
                // args.putBoolean("Pickup", false);
                args.putString("tab_name", binding.tvPracticeSpeakingTab.getText().toString());
                comment_f.setArguments(args);
                transaction.addToBackStack(null);
                transaction.replace(R.id.frame, comment_f, "FreeCoinFragment").commit();
            }
        });




        getCoCurricularReport();
        return view;
    }

    @SuppressLint("CheckResult")
    private void getCoCurricularReport() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.report_co_curricular_exam("123456", sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response);
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
        ResponseDTO object = gson.fromJson(response,ResponseDTO.class);
        Log.d("result_my_test", "" + object);
        binding.tvPracticeSpeakingObtain.setText(object.getPractiseSpeaking()+ " %");
        binding.tvPersonalityDevelopmentObtain.setText(object.getPersonalityDevelopment()+" %");
        binding.tvPracticeSpeakingScore.setText(object.getPractiseSpeaking() + " %");
        binding.tvPersonalityDevelopmentScore.setText(object.getPersonalityDevelopment() + " %");
        binding.tvPracticeSpeakingMarksObtain.setText(object.getScoreCalculater().getPractiseSpeaking() + " %");
        binding.tvPersonalityDevelopmentMarksObtain.setText(object.getScoreCalculater().getPersonalityDevelopment() + " %");

        Log.e("=======>", "rsult"+ object.getScoreCalculater().getPersonalityDevelopment());
        Log.e("=====>", "resu;t"+ object.getScoreCalculater().getPractiseSpeaking());
        float total =Float.parseFloat(object.getPersonalityDevelopment()) + Float.parseFloat( object.getPractiseSpeaking());
        Float foo = Float.valueOf(String.format("%.2f", total));
        double totalMarks = Double.parseDouble(object.getScoreCalculater().getPersonalityDevelopment()) + Double.parseDouble(object.getScoreCalculater().getPractiseSpeaking());
        Double setToTotal = Double.valueOf(String.format("%.2f", totalMarks));
        binding.totalScoreObtain.setText(foo + " %");
        binding.tvTotalMarksObtain.setText(setToTotal +"%");


    }
}
