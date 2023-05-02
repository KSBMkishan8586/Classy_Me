package com.ksbm.ontu.main_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.ActivityOneTimeBannerDialogBinding;
import com.ksbm.ontu.main_screen.adapter.SliderAdapterBanner;
import com.ksbm.ontu.main_screen.model.OneTimeBannerModel;
import com.ksbm.ontu.session.SessionManager;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class OneTimeBannerDialog extends AppCompatActivity {
    SessionManager sessionManager;
    ActivityOneTimeBannerDialogBinding binding;
    private SliderAdapterBanner sliderAdapter;
    private int dotsCount;
    private ImageView[] dotes;
   // private TextView tv_skip;
    private int currentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  setContentView(R.layout.activity_personality_quiz_dialog);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_one_time_banner_dialog);

        sessionManager = new SessionManager(this);

        getAllBanner();

        binding.Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void getAllBanner() {
        final ProgressDialog progressDialog = new ProgressDialog(OneTimeBannerDialog.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAllBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<OneTimeBannerModel>() {
                    @Override
                    public void onNext(OneTimeBannerModel response) {
                        progressDialog.dismiss();
                        //Handle logic
                        try {
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                SetBanner(response.getResponse());
                               sessionManager.setOneTimeBanner(true);

                            } else {

                                //  SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());

                            }

                        } catch (Exception e) {
                            // progressDialog.dismiss();
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

    private void SetBanner(List<OneTimeBannerModel.OneTimeBannerResponse> response) {

        //dotesIndicater(0);
        sliderAdapter = new SliderAdapterBanner(OneTimeBannerDialog.this, response);
        binding.sliderPager.setAdapter(sliderAdapter);
        binding.sliderPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        binding.sliderPager.setCurrentItem(0);
        binding.sliderPager.addOnPageChangeListener(pageChangeListener);
        dotesIndicater();


//        Handler handler = new Handler();
//
//        Runnable update = new Runnable() {
//            public void run() {
//                if (currentPage == 3) {
//                    currentPage = 3;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };


//        new Timer().schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        }, 1000*1, 1000*1);
    }


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < dotsCount; i++) {
                dotes[i].setImageResource(R.drawable.circle_inactive);
            }

            dotes[position].setImageResource(R.drawable.circle_active);

//            if (position + 1 == dotsCount) {
//                // tv_skip.setText("Get Start");
//                tv_skip.setVisibility(View.VISIBLE);
//
//            } else {
//                // tv_skip.setText("Skip");
//                tv_skip.setVisibility(View.INVISIBLE);
//
//            }

            //****
            if (position==0){
                currentPage=0;
            }else if (position==1){
                currentPage=1;
            }else if (position==2){
                currentPage=2;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressLint("ClickableViewAccessibility")
    public void dotesIndicater() {
        dotsCount = sliderAdapter.getCount();
        dotes = new ImageView[dotsCount];
        binding.linearLayout.removeAllViews();
        for (int i = 0; i < dotsCount; i++) {
            dotes[i] = new ImageView(this);
            dotes[i].setImageResource(R.drawable.circle_inactive);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20,
                    20
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dotes[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    binding.sliderPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            binding.linearLayout.addView(dotes[i], params);

        }
        dotes[0].setImageResource(R.drawable.circle_active);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}