package com.ksbm.ontu.foundation.fragment;

import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentFoundationBasicBinding;
import com.ksbm.ontu.foundation.adapter.FoundationBasics_Adapter;
import com.ksbm.ontu.foundation.adapter.FoundationList_Adapter;
import com.ksbm.ontu.foundation.adapter.SliderAdapter_Basics;
import com.ksbm.ontu.foundation.model.FoundationCourseModel;
import com.ksbm.ontu.main_screen.DrawerActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.databinding.FragmentFundamentalBinding;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.main_screen.fragment.SideBarFragment;
import com.ksbm.ontu.personality_dev.model.CategoryModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Foundation_Basics_Fragment extends Fragment {
    FragmentFoundationBasicBinding binding;
    SessionManager sessionManager;
    List<FoundationCourseModel.FoundationCourseResponse>course_list=new ArrayList<>();
    FoundationBasics_Adapter friendsAdapter;
    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_foundation_basic, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());

       // binding.tvTitle.setText(R.string.foundation_work);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SideBarFragment blankFragment = new SideBarFragment();
//                blankFragment.show(getActivity().getSupportFragmentManager(),blankFragment.getTag());
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
            }
        });


        if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
            SweetAlt.OpenPaymentDialog(getActivity(), Constant.upgrade_course, new ClickListionerss() {
                @Override
                public void clickYes() {
                    startActivity(new Intent((Activity)getActivity(), PaymentActivity.class).putExtra("price", upgradePackage));
                }
                @Override
                public void clickNo() {
                    getActivity().onBackPressed();
                }
            });
        }

        return view;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<FoundationCourseModel.FoundationCourseResponse> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (FoundationCourseModel.FoundationCourseResponse item : course_list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getFoundationName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            // Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            friendsAdapter.filterList(filteredlist);
        }
    }

    @SuppressLint("CheckResult")
    private void GetSliderCourse() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

//        Toast.makeText(getActivity(), sessionManager.getLanguageid()+""+","+sessionManager.getUser().getUserid()+"", Toast.LENGTH_SHORT).show();

        apiInterface.GetFoundationList(sessionManager.getLanguageid(), sessionManager.getUser().getUserid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FoundationCourseModel>() {
                    @Override
                    public void onNext(FoundationCourseModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                course_list= response.getResponse();

                                 friendsAdapter = new FoundationBasics_Adapter(course_list, getActivity());
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

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


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public void onResume() {
        GetSliderCourse();
        super.onResume();
    }
}
