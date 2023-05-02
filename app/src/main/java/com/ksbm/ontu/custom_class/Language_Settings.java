package com.ksbm.ontu.custom_class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.Splash_Activity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.main_screen.model.language_model.LanguageModel;
import com.ksbm.ontu.main_screen.model.language_model.LanguageModelResponse;
import com.ksbm.ontu.session.SessionManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Language_Settings {
    private static SessionManager sessionManager;

    @SuppressLint("CheckResult")
    public static void GetLanguage(RecyclerView languageView, Context context, AlertDialog alertDialog) {
        sessionManager=new SessionManager(context);

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.LanguageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LanguageModel>() {
                    @Override
                    public void onNext(LanguageModel response) {
                        //Handle logic
                        try {
                            // progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus()==1) {
                                // Toast.makeText(SignupActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                                if (response.getResponse().size() == 1) {
                                    sessionManager.setCurrentLanguage(response.getResponse().get(0).getLanguageCode(), context);
                                    sessionManager.setIsFirstTime(true, context);
                                }
                                LanguageAdapter languageAdapter = new LanguageAdapter(context, response.getResponse(), alertDialog);
                                languageView.setAdapter(languageAdapter);
                            } else {
                                //  Toast.makeText(SignupActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        // progressDialog.dismiss();
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
                        //  progressDialog.dismiss();
                    }
                });

        alertDialog.show();

    }


    public static class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ItemRowHolder> {
        private List<LanguageModelResponse> dataList;
        private Context mContext;
        AlertDialog alertDialog;

        public LanguageAdapter(Context context, List<LanguageModelResponse> dataList, AlertDialog alertDialog) {
            this.dataList = dataList;
            this.mContext = context;
            this.alertDialog = alertDialog;
        }

        @Override
        public LanguageAdapter.ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_layout, parent, false);
            return new LanguageAdapter.ItemRowHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull LanguageAdapter.ItemRowHolder holder, final int position) {

            final LanguageModelResponse language = dataList.get(position);
            final ItemRowHolder itemRowHolder = holder;
            itemRowHolder.tvLanguage.setText(language.getLanguageName());

//            if (sessionManager.getCurrentLanguage(mContext).equals(language.getLanguageCode())) {
//                itemRowHolder.radio.setImageResource(R.drawable.ic_radio_check);
//            } else {
//                itemRowHolder.radio.setImageResource(R.drawable.ic_radio_unchecked);
//            }

            itemRowHolder.radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemRowHolder.radio.setImageResource(R.drawable.ic_radio_check);
                    sessionManager.setCurrentLanguage(language.getLanguageCode(), mContext);
                    sessionManager.setLanguageid(language.getLanguageId());
                  //  Constant.D_LANG_ID=language.getLanguageCode();
                    notifyDataSetChanged();
                    alertDialog.dismiss();


                    if (!sessionManager.getIs_first_time(mContext)) {
                       // ((MainActivity)mContext).Recreate();
                        Intent mainIntent = new Intent(mContext, Splash_Activity.class);
                        mContext.startActivity(mainIntent);
                        ((MainActivity)mContext).finish();
                    }

                    Toast.makeText(mContext, R.string.language_changed_successfully, Toast.LENGTH_SHORT).show();

                    sessionManager.setIsFirstTime(false, mContext);

                }
            });

     itemRowHolder.rel_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemRowHolder.radio.setImageResource(R.drawable.ic_radio_check);
                    sessionManager.setCurrentLanguage(language.getLanguageCode(), mContext);
                    sessionManager.setLanguageid(language.getLanguageId());
                  //  Constant.D_LANG_ID=language.getLanguageCode();
                    notifyDataSetChanged();
                    alertDialog.dismiss();


                    if (!sessionManager.getIs_first_time(mContext)) {
                       // ((MainActivity)mContext).Recreate();
                        Intent mainIntent = new Intent(mContext, Splash_Activity.class);
                        mContext.startActivity(mainIntent);
                        ((MainActivity)mContext).finish();
                    }

                    Toast.makeText(mContext, R.string.language_changed_successfully, Toast.LENGTH_SHORT).show();

                    sessionManager.setIsFirstTime(false, mContext);

                }
            });

        }

        @Override
        public int getItemCount() {
            return (dataList.size());
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {
            public ImageView radio;
            public TextView tvLanguage;
            RelativeLayout rel_layout;

            public ItemRowHolder(View itemView) {
                super(itemView);
                radio = itemView.findViewById(R.id.radio);
                tvLanguage = itemView.findViewById(R.id.tvLanguage);
                rel_layout = itemView.findViewById(R.id.rel_layout);

            }

        }

    }
}




