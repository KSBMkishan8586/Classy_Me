package com.ksbm.ontu.foundation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityCurrencyDenominationBinding;
import com.ksbm.ontu.foundation.model.DenominationModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;

public class CurrencyDenominationActivity extends AppCompatActivity {
    ActivityCurrencyDenominationBinding binding;
    SessionManager sessionManager;
    Context context;
    List<DenominationModel.DenominationModelResponse> quizDetails= new ArrayList<>();
    DenominationAdapter alphabetAdapter;
    int quiz_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_currency_denomination);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_currency_denomination);

        context= CurrencyDenominationActivity.this;
        sessionManager = new SessionManager(this);
        binding.header.tvTitle.setText(Constant.foundation_name);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if (Connectivity.isConnected(context)) {
            GetAllDenominationList();
        } else {
            SweetAlt.showErrorMessage(context, "Sorry", "You have no internet!");
        }

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0,0,0,0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Utils.screenShot(binding.fullScreen, getWindow().getContext());
                }
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
                    // binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() - 1);

                    Speach_Record_Activity.mFileName = null;

                    if (mPlayer != null) {
                        mPlayer.release();
                        mPlayer = null;
                    }

                } else if (quiz_position == 0) {
                    binding.sliderEditor.setCurrentItem(0);
                }
                //   Log.e("current_page--", ""+quiz_position);
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alphabetAdapter != null) {
                    if (quiz_position != quizDetails.size() - 1) {
                        quiz_position++;
                        binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1);

                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }
                        Speach_Record_Activity.mFileName = null;

                    } else {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });



    }

    @SuppressLint("CheckResult")
    private void GetAllDenominationList() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetDenominationList(sessionManager.getUser().getUserid(), sessionManager.getLanguageid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DenominationModel>() {
                    @Override
                    public void onNext(DenominationModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                quizDetails = response.getResponse();

                                  alphabetAdapter = new DenominationAdapter(context, quizDetails);
                                binding.sliderEditor.setAdapter(alphabetAdapter);
                                binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
                                binding.sliderEditor.setCurrentItem(0);
                                binding.sliderEditor.addOnPageChangeListener(pageChangeListenerEditor);

                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", response.getMessage());

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

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    ViewPager.OnPageChangeListener pageChangeListenerEditor = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //currentPage=position;

            if (position == 0) {
                binding.layoutButton.tvPrevious.setVisibility(View.INVISIBLE);
            } else {
                binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
            }

//            if (position != quizDetails.size()-1){
//                binding.tvNext.setVisibility(View.VISIBLE);
//            }else {
//                binding.tvNext.setVisibility(View.INVISIBLE);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class DenominationAdapter extends PagerAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        List<DenominationModel.DenominationModelResponse> quizDetails;
        SessionManager sessionManager;
        boolean isRecording = false;

        public DenominationAdapter(Context context, List<DenominationModel.DenominationModelResponse> listarray1) {
            this.context = context;
            this.quizDetails = listarray1;
            sessionManager = new SessionManager(context);
        }


        @Override
        public int getCount() {
            return quizDetails.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @SuppressLint({"SetTextI18n", "CheckResult"})
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int quiz_position) {

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            View view = layoutInflater.inflate(R.layout.slide_denomination, container, false);

            DenominationModel.DenominationModelResponse demoni_list = quizDetails.get(quiz_position);

            TextView tv_name = view.findViewById(R.id.tv_name);
            ImageView iv_image = view.findViewById(R.id.iv_image);
            TextView tv_speak = view.findViewById(R.id.tv_speak);
            TextView tv_slow = view.findViewById(R.id.tv_slow);
            TextView tv_check = view.findViewById(R.id.tv_check);
            LinearLayout ll_get_coin = view.findViewById(R.id.ll_get_coin);

            TextView tv_symbol = view.findViewById(R.id.tv_symbol);
            TextView tv_digit = view.findViewById(R.id.tv_digit);
            TextView tv_currency_code = view.findViewById(R.id.tv_currency_code);

            ll_get_coin.setVisibility(View.GONE);
            Speach_Record_Activity.mFileName = null;

            tv_symbol.setText(demoni_list.getCurrencySymbol());
            tv_currency_code.setText(demoni_list.getCurrencyCode());
            tv_digit.setText(demoni_list.getCurrency_in_digit());
            tv_name.setText(demoni_list.getCurrency_in_text());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.common_currency_icon);
            requestOptions.error(R.drawable.common_currency_icon);
            requestOptions.isMemoryCacheable();

            if (demoni_list.getCurrencyImage()!=null && !demoni_list.getCurrencyImage().isEmpty()){
                Glide.with(context).setDefaultRequestOptions(requestOptions)
                        .load(demoni_list.getCurrencyImage())
                        .into(iv_image);
            }

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(context, demoni_list.getCurrency_in_text(), false);

                }
            });

            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(context, demoni_list.getCurrency_in_text(), false);

                }
            });


            container.addView(view);


            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}