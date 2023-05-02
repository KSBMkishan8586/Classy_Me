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
import android.os.Handler;
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
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityBodyPartsBinding;
import com.ksbm.ontu.foundation.adapter.AlphabetAdapter;
import com.ksbm.ontu.foundation.model.AlphabetModelModel;
import com.ksbm.ontu.foundation.model.AnimalModel;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

import static com.ksbm.ontu.custom_class.Speach_Record_Activity.PlayRecording;
import static com.ksbm.ontu.custom_class.Speach_Record_Activity.mPlayer;
import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;
import static com.ksbm.ontu.utils.PermissionsUtils.CheckPermissions_record;
import static com.ksbm.ontu.utils.PermissionsUtils.RequestPermissions;

public class Body_Parts_Activity extends AppCompatActivity {
    ActivityBodyPartsBinding binding;
    SessionManager sessionManager;
    Context context;
    int quiz_position=0;
    List<AlphabetModelModel.AlphabetModelResponse> quizDetails;
    BodyPartsAdapter bodyPartsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_body_parts);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_body_parts);


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);

        context= Body_Parts_Activity.this;
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
        }else {
            binding.layoutButton.tvPrevious.setVisibility(View.VISIBLE);
        }

        binding.sliderEditor.beginFakeDrag(); //disable swiping
        binding.sliderEditor.setClipToPadding(true);
        binding.sliderEditor.setPadding(0,0,0,0);
        binding.sliderEditor.setPageMargin(40);
        binding.sliderEditor.setPagingEnabled(false);

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
                    // binding.sliderEditor.setCurrentItem(quiz_position--);
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() -1);

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

                if (bodyPartsAdapter!=null){
                    if (quiz_position != quizDetails.size() - 1) {
                        quiz_position++;
                        binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1);

                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }

                    }else {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                    }
                }
                // Log.e("current_page++", ""+quiz_position);
            }
        });

        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent((Context) Body_Parts_Activity.this, FreeCoinDialog.class);
                startActivity(intent);
            }
        });

        binding.header.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Utils.screenShot(binding.relativeLayout, getWindow().getContext());
                }
            }
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent((Context) Body_Parts_Activity.this, Alerts_Dialog.class);
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

        apiInterface.GetLearningList(sessionManager.getUser().getUserid(), sessionManager.getLanguageid(), Constant.foundation_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<AlphabetModelModel>() {
                    @Override
                    public void onNext(AlphabetModelModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                quizDetails = response.getResponse();

                                bodyPartsAdapter = new BodyPartsAdapter(context, quizDetails);
                                binding.sliderEditor.setAdapter(bodyPartsAdapter);
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

            if (position==0){
                binding.layoutButton.tvPrevious.setVisibility(View.INVISIBLE);
            }else {
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

    private class BodyPartsAdapter extends PagerAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        List<AlphabetModelModel.AlphabetModelResponse> quizDetails;
        SessionManager sessionManager;
        boolean isRecording = false;
        boolean isPlayed= false;

        public BodyPartsAdapter(Context context, List<AlphabetModelModel.AlphabetModelResponse> listarray1) {
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
            View view = layoutInflater.inflate(R.layout.slide_animal_quiz, container, false);

            AlphabetModelModel.AlphabetModelResponse alphabetModelResponse = quizDetails.get(quiz_position);

            TextView tv_alphabet_name = view.findViewById(R.id.tv_name);
            ImageView iv_image = view.findViewById(R.id.iv_image);
            LinearLayout tv_speak = view.findViewById(R.id.tv_speak);
            LinearLayout tv_slow = view.findViewById(R.id.tv_slow);
            LinearLayout tv_check = view.findViewById(R.id.tv_check);
            TextView tv_user_coin = view.findViewById(R.id.tv_user_coin);
            TextView tv_record_speak = view.findViewById(R.id.tv_record_speak);
            LinearLayout ll_get_coin = view.findViewById(R.id.ll_get_coin);
            LinearLayout ll_dynamic_text = view.findViewById(R.id.ll_dynamic_text);

            ll_get_coin.setVisibility(View.GONE);
            Speach_Record_Activity.mFileName = null;

            isPlayed= false;
            //****set character in seperate box***
            if (alphabetModelResponse.getAlphabet_word()!=null && !alphabetModelResponse.getAlphabet_word().equalsIgnoreCase("")){
                ll_dynamic_text.setVisibility(View.VISIBLE);
                String name= alphabetModelResponse.getAlphabet_word();

                Utils.SetSeperateCharacterInBox(name, context, ll_dynamic_text);
            }else {
                ll_dynamic_text.setVisibility(View.INVISIBLE);
            }


            tv_alphabet_name.setText(alphabetModelResponse.getName());

            RequestOptions requestOptions = new RequestOptions();
           // requestOptions.placeholder(R.drawable.body_parts_icon);
            //requestOptions.error(R.drawable.body_parts_icon);
            requestOptions.isMemoryCacheable();
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(alphabetModelResponse.getImage())
                    .into(iv_image);

            container.addView(view);

            tv_speak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (CheckPermissions_record(((AppCompatActivity) context))) {
                        if (!isRecording) {
                            isRecording = true;
                            Speach_Record_Activity.recording_start(context);
                            tv_record_speak.setText("Stop");
                            //  Toast.makeText(getActivity(), "Recording Started", Toast.LENGTH_LONG).show();

                        } else {
                            tv_record_speak.setText("Record");
                            isRecording = false;
                            Speach_Record_Activity.stop_recording();
                            // Toast.makeText(getActivity(), "Recording Stopped", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        RequestPermissions(((AppCompatActivity) context));
                    }
                }
            });

            tv_slow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getName(), true);

                }
            });

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alphabetModelResponse.getAudioFile() != null && !alphabetModelResponse.getAudioFile().isEmpty()) {
                        PlayRecording(alphabetModelResponse.getAudioFile(), context);
                    } else {
                        Speach_Record_Activity.speakOut(context, alphabetModelResponse.getLearningText(), false);
                    }
                }
            });

            tv_alphabet_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Speach_Record_Activity.speakOut(context, alphabetModelResponse.getName(), false);

                }
            });

            tv_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String file_name = Speach_Record_Activity.getRecordedFileName();
                    if (file_name != null) {
                        //PlayRecording(Speach_Record_Activity.getRecordedFileName(), context);
                        Speach_Record_Activity.speakOut(context, alphabetModelResponse.getName(), false);
                        Utils.playRecordWithdelay(context, 2);
                        tv_record_speak.setText("Record");
                        tv_user_coin.setText("+"+FoundationLearningCoinBonus);

                        if(!isPlayed){
                            CommonUtil.EarnCoin(sessionManager.getUser().getUserid(), FoundationLearningCoinBonus, "foundation_learn", quizDetails.get(quiz_position).getLearning_id());
                            Utils.EarnCoin(ll_get_coin, context, 0);
                            isPlayed= true;
                        }
                    }else {
                        Toast.makeText(context, "Please record first!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

        private void EarnCoin(LinearLayout ll_get_coin) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll_get_coin.setVisibility(View.VISIBLE);
                    Utils.playMusic(R.raw.coin_sound, context);
                }
            }, 4000);
        }

    }


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