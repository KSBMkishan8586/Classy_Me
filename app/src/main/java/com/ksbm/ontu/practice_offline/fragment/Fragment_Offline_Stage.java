package com.ksbm.ontu.practice_offline.fragment;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mTextToSpeech;
import static com.ksbm.ontu.utils.Constant.LEVEL_ID;
import static com.ksbm.ontu.utils.Constant.QUIZ_COIN;
import static com.ksbm.ontu.utils.Constant.QUIZ_ID;
import static com.ksbm.ontu.utils.Constant.STAGE_ID;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FragmentOfflineStageBinding;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.practice_offline.OfflineQuizScoreDB;
import com.ksbm.ontu.practice_offline.adapter.Offline_Stage_Adapter;
import com.ksbm.ontu.practice_offline.model.OfflineStageModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Fragment_Offline_Stage extends Fragment {
    FragmentOfflineStageBinding binding;
    SessionManager sessionManager;
    OfflineQuizScoreDB offlineQuizScoreDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offline_stage, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        offlineQuizScoreDB = new OfflineQuizScoreDB(getActivity());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });


        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Connectivity.isConnected(getActivity())) {
                    GetAllStage();
                } else {
                    SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
                }
                binding.swiperefresh.setRefreshing(false);
            }
        });

        binding.llOfflineSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offlineQuizScoreDB.getScore().size() > 0) {
                    SaveDataJsonArray();
                } else {
                    Toast.makeText(getActivity(), "Already Synced", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.relMemoryMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Akash " + sessionManager.getLanguageid());
                String replace_persign = binding.tvMemoryPercent.getText().toString().replace("%", "");
                double value = Double.parseDouble(replace_persign);
                if (value == 0) {
                    System.out.println("Akash " + sessionManager.getLanguageid());
                    String msg = "";
                    if (sessionManager.getLanguageid().equalsIgnoreCase("1")) {
                        msg = "To check your Memory Retention\n" +
                                "\u2b07\n" +
                                "Complete a level with Self check ";
                        //⇩
                    } else {
                        msg = "अपनी मेमोरी रिटेंशन की जांच करने के लिए\n" +
                                "\u2b07\n" +
                                "सेल्फ चेक के साथ एक स्तर पूरा करें ";
                    }
                    Speach_Record_Activity.speakOut(getActivity(), msg, false);
                    SweetAlt.OpenFreeCoinDialog(getActivity(), msg);
                } else if (value < 60) {
                    String msg = "";
                    if (sessionManager.getLanguageid().equalsIgnoreCase("1")) {
                        msg = "Your Memory Map Progress percentage is low. We suggest you to do Practice speaking again.";
                    } else {
                        msg = "आपका मेमोरी मैप प्रगति प्रतिशत कम है। हमारा सुझाव है कि आप फिर से बोलने का अभ्यास करें।";
                    }
                    Speach_Record_Activity.speakOut(getActivity(), msg, false);
                    SweetAlt.OpenFreeCoinDialog(getActivity(), msg);
                } else {
                    String msg = "";
                    if (sessionManager.getLanguageid().equalsIgnoreCase("1")) {
                        msg = "Your Memory Map Progress is- " + binding.tvMemoryPercent.getText().toString();
                    } else {
                        msg = "आपकी स्मृति मानचित्र की प्रगति है - " + binding.tvMemoryPercent.getText().toString();
                    }
                    Speach_Record_Activity.speakOut(getActivity(), msg, false);
                    SweetAlt.OpenFreeCoinDialog(getActivity(), msg);
                }


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
//                                SweetAlt.succesDialog(getActivity(), title, msg, false, false, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        sweetAlertDialog.dismiss();
//                                       // finish();
//                                    }
//                                });
                                if (Connectivity.isConnected(getActivity())) {
                                    GetAllStage();
                                } else {
                                    SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
                                }

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

    @SuppressLint("CheckResult")
    private void GetAllStage() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        // Api_Call apiInterface = RxApiClient.getCacheEnabledRetrofit(Base_Url.BaseUrl, getActivity()).create(Api_Call.class);

        apiInterface.GetOfflineStageList(sessionManager.getUser().getClassid(), sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OfflineStageModel>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onNext(OfflineStageModel response) {
                        //Handle logic  I
                        try {
                            progressDialog.dismiss();
                            // Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                Offline_Stage_Adapter friendsAdapter = new Offline_Stage_Adapter(response.getResponse(), getActivity());
                                binding.setAdapter(friendsAdapter); //set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

                                if (response.getMemory_map_question_report() != null && !response.getMemory_map_question_report().isEmpty()) {
                                    String replace_persign = response.getMemory_map_question_report().replace("%", "");
                                    Log.e("memory_per", "" + (int) Double.parseDouble(replace_persign));
                                    binding.offlineScoreBar.setProgress((int) Double.parseDouble(replace_persign));
                                    binding.tvMemoryPercent.setText("" + (int) Double.parseDouble(replace_persign) + "%");
                                    double value = Double.parseDouble(replace_persign);
                                    if (value < 1) {
                                        String msg = "";
                                        String msgSpeaking = "";
                                        if (sessionManager.getLanguageid().equalsIgnoreCase("1")) {
                                            msgSpeaking = "To check your Memory Retention Complete a level with Self check ";
                                            msg = "To check your Memory Retention\n" +
                                                    "\u2b07\n" +
                                                    "Complete a level with Self check ";
                                        } else {
                                            msgSpeaking = "अपनी मेमोरी रिटेंशन की जांच करने के लिए सेल्फ चेक के साथ एक स्तर पूरा करें ";
                                            msg = "अपनी मेमोरी रिटेंशन की जांच करने के लिए\n" +
                                                    "\u2b07\n" +
                                                    "सेल्फ चेक के साथ एक स्तर पूरा करें ";
                                        }
                                        Speach_Record_Activity.speakOut(getActivity(), msgSpeaking, false);
                                        SweetAlt.OpenFreeCoinDialog(getActivity(), msg);
                                    } else if (value < 60) {
                                        String msg = "";
                                        if (sessionManager.getLanguageid().equalsIgnoreCase("1")) {
                                            msg = "Your Memory Map Progress percentage is low. We suggest you to do Practice speaking again.";
                                        } else {
                                            msg = "आपका मेमोरी मैप प्रगति प्रतिशत कम है। हमारा सुझाव है कि आप फिर से बोलने का अभ्यास करें।";
                                        }
                                        Speach_Record_Activity.speakOut(getActivity(), msg, false);
                                        SweetAlt.OpenFreeCoinDialog(getActivity(), msg);
                                    }

                                }

                            } else {

                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            Log.e("stage_crash", "" + e.toString());
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
                        Log.e("stage_data_error", e.toString());

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

    @Override
    public void onResume() {
        if (Connectivity.isConnected(getActivity())) {
            GetAllStage();
            SaveDataJsonArray();
        } else {
            //SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTextToSpeech!=null){
            mTextToSpeech.stop();
        }
    }
}
