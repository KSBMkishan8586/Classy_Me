package com.ksbm.ontu.fundamental.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.R;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Winner;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.fundamental.activity.VideoPlayer;
import com.ksbm.ontu.fundamental.adapter.Workbook_Adapter;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentWorkbookBinding;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Toodle_Data;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Workbook_Model;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

import static com.ksbm.ontu.utils.Utils.getVideoIdFromYoutubeUrl;
import static com.ksbm.ontu.utils.Utils.isValidYoutubeUrl;

public class Fundamental_Workbook extends Fragment {
    FragmentWorkbookBinding binding;
    SessionManager sessionManager;
    String ToodleId, ToodleFile, FundamentalId;
    Fundamental_Toodle_Data fundamental_toodle_data;

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workbook, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        if (getArguments() != null) {
            fundamental_toodle_data = (Fundamental_Toodle_Data) getArguments().getSerializable("ToodleData");
            FundamentalId = fundamental_toodle_data.getFundamentalId();
            ToodleId = fundamental_toodle_data.getCourseId();
            ToodleFile = fundamental_toodle_data.getBannerFile();

            binding.tvToodlename.setText(fundamental_toodle_data.getCourseName());
            binding.tvTitle.setText(fundamental_toodle_data.getFundamentalName());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.logo_round);
            requestOptions.error(R.drawable.logo_round);
            requestOptions.isMemoryCacheable();

            if (fundamental_toodle_data.getOther_link() != null && !fundamental_toodle_data.getOther_link().equalsIgnoreCase("")) {
                if (isValidYoutubeUrl(fundamental_toodle_data.getOther_link())){//for you tube url
                    String videoId = getVideoIdFromYoutubeUrl(fundamental_toodle_data.getOther_link());
                    String url = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
                    Glide.with(getActivity()).load(url).into(binding.ivThumb);
                }else {
                    Glide.with(getActivity()).setDefaultRequestOptions(requestOptions)
                            .load(fundamental_toodle_data.getOther_link())
                            .into(binding.ivThumb);
                }

            } else if (fundamental_toodle_data.getBannerFile() != null && !fundamental_toodle_data.getBannerFile().equalsIgnoreCase("")) {
                Glide.with(getActivity()).setDefaultRequestOptions(requestOptions)
                        .load(fundamental_toodle_data.getBannerFile())
                        .into(binding.ivThumb);
            }

            binding.ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    SideBarFragment blankFragment = new SideBarFragment();
//                    blankFragment.show(getActivity().getSupportFragmentManager(),blankFragment.getTag());

                    Intent intent = new Intent(getActivity(), DrawerActivity.class);
                    startActivity(intent);

                }
            });


            binding.ivCoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.screenShot(binding.llscreen,getActivity());
                }
            });



        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        binding.llToodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenVideoPlayer(false);

            }
        });

        if (Connectivity.isConnected(getActivity())) {
            GetWorkbookList();
        } else {
            SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        }

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeToRefresh.setRefreshing(true);
                if (Connectivity.isConnected(getActivity())) {
                    GetWorkbookList();
                } else {
                    SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
                }
            }
        });

        return view;

    }

    public void OpenVideoPlayer(boolean QuizPlay) {
        Intent intent= new Intent(getActivity(), VideoPlayer.class);
        intent.putExtra("QuizPlay", QuizPlay);
        if (fundamental_toodle_data.getOther_link()!=null && !fundamental_toodle_data.getOther_link().equalsIgnoreCase("")){
            intent.putExtra("FilePath", fundamental_toodle_data.getOther_link());
            intent.putExtra("FilePathOther", true);
            startActivity(intent);
        }else if (fundamental_toodle_data.getBannerFile()!=null && !fundamental_toodle_data.getBannerFile().equalsIgnoreCase("")){
            intent.putExtra("FilePath", fundamental_toodle_data.getBannerFile());
            intent.putExtra("FilePathOther", false);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(), "No Video Found", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("CheckResult")
    private void GetWorkbookList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        //Toast.makeText(getActivity(), sessionManager.getLanguageid()+""+","+ FundamentalId+""+","+ToodleId+""+"," +sessionManager.getUser().getUserid()+"", Toast.LENGTH_SHORT).show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        Log.d("GetFundameasfdf",ToodleId);

        apiInterface.GetFundamentalWorkbook(sessionManager.getLanguageid(), FundamentalId, ToodleId, sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Fundamental_Workbook_Model>() {
                    @Override
                    public void onNext(Fundamental_Workbook_Model response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                Workbook_Adapter friendsAdapter = new Workbook_Adapter(response.getResponse(), getActivity(), Fundamental_Workbook.this);
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();
                            } else {
                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());
                            }
                            binding.swipeToRefresh.setRefreshing(false);
                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                        binding.swipeToRefresh.setRefreshing(false);

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


    @Override
    public void onResume() {
        GetWorkbookList();
        super.onResume();
    }
}
