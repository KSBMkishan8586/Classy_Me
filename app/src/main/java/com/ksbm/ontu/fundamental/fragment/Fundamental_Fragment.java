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
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.ksbm.ontu.R;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.fundamental.adapter.SliderAdapter;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentFundamentalBinding;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Model;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Model_Data;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

import static com.ksbm.ontu.utils.Constant.Fundamental_current_item;

public class Fundamental_Fragment extends Fragment {
    FragmentFundamentalBinding binding;
    SessionManager sessionManager;
    int currentPage = 0;
    List<Fundamental_Model_Data> course_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fundamental, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        if (getArguments() != null) {
            String Title = getArguments().getString("Title");
            binding.tvTitle.setText(R.string.fundamental_work_);

        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });


        binding.ivCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.screenShot(binding.llscreen, getActivity());
            }
        });


        if (Connectivity.isConnected(getActivity())) {
            GetSliderCourse();
        } else {
            SweetAlt.showErrorMessage(getActivity(), "Sorry", "You have no internet!");
        }

        binding.ivLeftEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage > 0) {
                    currentPage--;
                    binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() - 1, true);
                } else if (currentPage == 0) {
                    binding.sliderEditor.setCurrentItem(0, true);
                }
            }
        });

        binding.ivRightEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == course_list.size() - 1) {
                    //  currentPage = 0;
                    currentPage = currentPage;
                }
                currentPage++;
                binding.sliderEditor.setCurrentItem(binding.sliderEditor.getCurrentItem() + 1, true);
            }
        });


        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                SideBarFragment blankFragment = new SideBarFragment();
//                blankFragment.show(getActivity().getSupportFragmentManager(),blankFragment.getTag());

                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);


//                Fragment fragment_home = new SideBarFragment();
//                Bundle bundle = new Bundle();
////                bundle.putString("Title", binding.tvFundamentalTitle.getText().toString());
//                FragmentTransaction ft_home = getActivity().getSupportFragmentManager().beginTransaction();
//                ft_home.replace(R.id.frame, fragment_home);
//                ft_home.addToBackStack(null);
//                ft_home.commit();
//                fragment_home.setArguments(bundle);


            }
        });

        return view;
    }

    @SuppressLint("CheckResult")
    private void GetSliderCourse() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        System.out.println("Animesh " + sessionManager.getUser().getClassid());
        String classId = sessionManager.getUser().getClassid();
        if (classId.equalsIgnoreCase("")) {
            if (sessionManager.getAgeCriteria().equalsIgnoreCase("Below 13")) {
                classId = "4";
            } else {
                classId = "4";
            }
        }
        System.out.println("Animesh " + classId + "-- " + sessionManager.getUser().getUserid() + "----" + sessionManager.getLanguageid());
        apiInterface.GetFundamentalCourse(sessionManager.getLanguageid(), classId, sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Fundamental_Model>() {
                    @Override
                    public void onNext(Fundamental_Model response) {
                        System.out.println("Mishra " + response.getMessage());
                        //Handle logic
                        try {
                            progressDialog.dismiss();

                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                course_list = response.getResponse();

                                SliderAdapter sliderAdapter = new SliderAdapter(getActivity(), course_list);
                                binding.sliderEditor.setAdapter(sliderAdapter);
//                                 binding.sliderEditor.setPageTransformer(true, new ZoomOutSlideTransformer());
//                                binding.sliderEditor.setCurrentItem(Fundamental_current_item);
                                binding.sliderEditor.addOnPageChangeListener(pageChangeListenerEditor);

                                binding.sliderEditor.setClipToPadding(false);
                                binding.sliderEditor.setPadding(100, 0, 100, 0);
//                                binding.sliderEditor.setPageMargin(-10);
                                // binding.sliderEditor.setOffscreenPageLimit(2);

                            } else {
                                SweetAlt.showErrorMessage(getActivity(), "Sorry", response.getMessage());
                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
//                        Log.e("mr_product_error", e.toString());

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

    ViewPager.OnPageChangeListener pageChangeListenerEditor = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public void onDestroy() {

        Fundamental_current_item = 0;
        super.onDestroy();
    }
}
