package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityAlphabetBinding;
import com.ksbm.ontu.foundation.adapter.AlphabetAdapter;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.translation.MainViewModel;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.GoogleCloudTTSFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;


public class Alphabet_Activity extends AppCompatActivity {
    ActivityAlphabetBinding binding;
    SessionManager sessionManager;
    Context context;
    int quiz_position = 0;
    AlphabetAdapter alphabetAdapter;
    List<AlphabetModelModel.AlphabetModelResponse> quizDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_alphabet);
        binding = DataBindingUtil.setContentView((Activity)this, R.layout.activity_alphabet);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        if (Constant.previous_btn) {
            binding.layoutButton.tvPrevious.setVisibility(View.GONE);
        }

        context = Alphabet_Activity.this;
        sessionManager = new SessionManager(this);

        if (Connectivity.isConnected(context)) {
            GetAllAlphabets();
        } else {
            SweetAlt.showErrorMessage(context, "Sorry", "You have no internet!");
        }

        binding.header.tvTitle.setText(Constant.foundation_name);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (quiz_position == 0) {
            binding.layoutButton.tvPrevious.setVisibility(View.INVISIBLE);
        } else {
            binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
        }

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0, 0, 0, 0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
                    // binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() - 1);
                    if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                        Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getAlphabet_word() + "", false);
                    } else {
                        if (quizDetails.get(binding.sliderEditor.getCurrentItem()).getCapital_alphabet_letter().isEmpty()) {
                            Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getAlphabet_word() + "", false);
                        } else {
                            Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getCapital_alphabet_letter() + "", false);
                        }
                    }


                    if (mPlayer != null) {
                        mPlayer.release();
                        mPlayer = null;
                    }

                } else if (quiz_position == 0) {
                    binding.sliderEditor.setCurrentItem(0);
                    if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                        Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getAlphabet_word() + "", false);
                    } else {
                        if (quizDetails.get(0).getCapital_alphabet_letter().isEmpty()) {
                            Speach_Record_Activity.speakOut(context, quizDetails.get(0).getLearningText() + "", false);
                        } else {
                            Speach_Record_Activity.speakOut(context, quizDetails.get(0).getAlphabet_word() + "", false);
                        }
                    }
                }
                //   Log.e("current_page--", ""+quiz_position);
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




        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alphabetAdapter != null) {
                    binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
                    if (quiz_position != quizDetails.size() - 1) {
                        quiz_position++;
                        binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1);
                        if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                            Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getAlphabet_word() + "", false);
                        } else {
                            if (quizDetails.get(binding.sliderEditor.getCurrentItem()).getCapital_alphabet_letter().isEmpty()) {
                                Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getAlphabet_word() + "", false);
                            } else {
                                Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getCapital_alphabet_letter() + "", false);
                            }
                        }

                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }

                    } else {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Context) Alphabet_Activity.this, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Context) Alphabet_Activity.this, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("CheckResult")
    private void GetAllAlphabets() {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetAlphabetList(sessionManager.getUser().getUserid(), sessionManager.getLanguageid(), Constant.foundation_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AlphabetModelModel>() {
                    @Override
                    public void onNext(AlphabetModelModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();

                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                quizDetails = response.getResponse();

                                alphabetAdapter = new AlphabetAdapter(context, quizDetails);
                                binding.sliderEditor.setAdapter(alphabetAdapter);
                                // binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
                                binding.sliderEditor.setCurrentItem(0);
                                binding.sliderEditor.addOnPageChangeListener(pageChangeListenerEditor);
                                if (Constant.foundation_id.equalsIgnoreCase(Constant.Body_Partsoriginal)) {
                                    Speach_Record_Activity.speakOut(context, quizDetails.get(binding.sliderEditor.getCurrentItem()).getAlphabet_word() + "", false);
                                } else {
                                    if (quizDetails.get(0).getCapital_alphabet_letter().isEmpty()) {
                                        Speach_Record_Activity.speakOut(context, quizDetails.get(0).getLearningText() + "", false);
                                    } else {
                                        Speach_Record_Activity.speakOut(context, quizDetails.get(0).getAlphabet_word() + "", false);
                                    }
                                }
                            } else {
                                SweetAlt.showErrorMessage(context, "Sorry", "No " + Constant.foundation_name + " learning question found.");
                            }

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


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    //  WHEN ACTIVITY IS PAUSED
    @Override
    public void onPause() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        super.onPause();
    }

    //  WHEN ACTIVITY IS DESTROYED
    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        // process_tts.dismiss();
        super.onDestroy();
    }
}