package com.ksbm.ontu.main_screen.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ksbm.ontu.BuildConfig;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.SideBarFragmentBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.practice_offline.OfflineQuizScoreDB;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

import static com.ksbm.ontu.utils.Constant.LEVEL_ID;
import static com.ksbm.ontu.utils.Constant.QUIZ_COIN;
import static com.ksbm.ontu.utils.Constant.QUIZ_ID;
import static com.ksbm.ontu.utils.Constant.STAGE_ID;

public class SideBarFragment extends BottomSheetDialogFragment {
    SideBarFragmentBinding binding;
    SessionManager sessionManager;
    OfflineQuizScoreDB offlineQuizScoreDB;

    public SideBarFragment() {
    }

    public static SideBarFragment newInstance() {
        return new SideBarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        // getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(inflater, R.layout.side_bar_fragment, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        offlineQuizScoreDB = new OfflineQuizScoreDB(getActivity());
//        binding.sidebarScreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//              //  getActivity().onBackPressed();
//
//            }
//        });

        binding.llSyncCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offlineQuizScoreDB.getScore().size() > 0) {
                    SaveDataJsonArray();
                } else {
                    Toast.makeText(getActivity(), "Already Synced", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.llInviteFrnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String shareMessage = "\nLet me recommend you this application\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        binding.llRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateApp();
            }
        });


        return view;
    }

    private void SaveDataJsonArray() {
        // retrive data from cart database
        ArrayList<HashMap<String, String>> items = offlineQuizScoreDB.getScore();
        if (items.size() > 0) {
            JSONArray passArray = new JSONArray();
            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> map = items.get(i);

                JSONObject jObjP = new JSONObject();

                try {
                    jObjP.put("stage_id", map.get(STAGE_ID));
                    jObjP.put("level_id", map.get(LEVEL_ID));
                    jObjP.put("coin_per_question", map.get(QUIZ_COIN));
                    jObjP.put("quiz_id", map.get(QUIZ_ID));
                    jObjP.put("classid", sessionManager.getUser().getClassid());

                    passArray.put(jObjP);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (Connectivity.isConnected(getActivity())) {
                Log.e("ndata:", "" + passArray.toString());
                SyncScoreData(passArray, sessionManager.getUser().getUserid());
            } else {
                Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
            }

        } else {
            // Toast.makeText(getActivity(), "Already Synced", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("CheckResult")
    private void SyncScoreData(JSONArray passArray, String userid) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.UploadScoreData(userid, passArray.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                offlineQuizScoreDB.clearCart();

                                Log.e("offlince_db_size", "" + offlineQuizScoreDB.getItemCount());

                                String title = getResources().getString(R.string.app_name);
                                String msg = response.getMessage();
                                SweetAlt.succesDialog(getActivity(), title, msg, false, false, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        // finish();
                                    }
                                });

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

    /*
     * Start with rating the app
     * Determine if the Play Store is installed on the device
     *
     * */
    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, BuildConfig.APPLICATION_ID)));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}
