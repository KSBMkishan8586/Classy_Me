package com.ksbm.ontu.progression_report;

import static com.ksbm.ontu.api_client.Base_Url.API_KEY;
import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mTextToSpeech;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentProgressionReportBinding;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.progression_report.model.ProgressionReportResponse;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProgressionReportFragment extends Fragment {
    FragmentProgressionReportBinding binding;
    SessionManager sessionManager;
    MeraSharedPreferance meraSharedPreferance;
    ProgressBar ProgressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progression_report, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());
        getProgressionReport();
        if (meraSharedPreferance.isOpenMarketGet()){
            binding.schoolRankLl.setVisibility(View.GONE);
        }else{
            binding.schoolRankLl.setVisibility(View.VISIBLE);
        }
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setDefaultFragment();
            }
        });

        binding.infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFreeCoinDialog(requireContext() , "Some msg");
            }
        });

        binding.relInternalExam.setOnClickListener(view1 -> {
            Fragment comment_f = new InternalExamReportFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
            Bundle args = new Bundle();
            // args.putBoolean("Pickup", false);
            // args.putString("user_id", item.fb_id);
            comment_f.setArguments(args);
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, comment_f, "FreeCoinFragment").commit();
        });


        binding.relCocurricular.setOnClickListener(view12 -> {
            Fragment comment_f = new Co_Curricular_ReportFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
            Bundle args = new Bundle();
            // args.putBoolean("Pickup", false);
            // args.putString("user_id", item.fb_id);
            comment_f.setArguments(args);
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, comment_f, "FreeCoinFragment").commit();
        });

        return view;
    }

    @SuppressLint("CheckResult")
    private void getProgressionReport() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.report_co_progression(API_KEY, sessionManager.getUser().getUserid())
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
        ProgressionReportResponse object = gson.fromJson(response, ProgressionReportResponse.class);
        binding.tvFullName.setText(object.getResponse().getFullname());
        binding.tvCoinBalance.setText(object.getResponse().getCoinBalance());
        binding.tvSchoolRank.setText(object.getResponse().getSchoolrank());
        binding.tvNationalRank.setText(object.getResponse().getSchoolrank());
        binding.tvInternalExam.setText(String.valueOf(object.getResponse().getInternalExam())+"%");
        binding.tvCoCurricular.setText(String.valueOf(object.getResponse().getCoCurrilier())+"%");

        int progressDoubl =  object.getResponse().getInternalExam()+object.getResponse().getCoCurrilier();
        int resultInt = progressDoubl/2;
        binding.progressBar.setProgress(resultInt);
    }



    public static void OpenFreeCoinDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_ans_dialog);
        //FragmentFreecoinBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.fragment_freecoin, null, false);
        // setContentView(binding.getRoot());

        ImageView Goback = (ImageView) dialog.findViewById(R.id.Goback);
        TextView tv_msg = dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Internal exam \n + \n Co-Curricular Activity \n /2");

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextToSpeech!=null){
                    mTextToSpeech.stop();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
