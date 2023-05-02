package com.ksbm.ontu.personality_dev;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.FragmentVideoListBinding;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.personality_dev.adapter.Video_List_Adapter;
import com.ksbm.ontu.personality_dev.model.CategoryWiseModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.adapter.rxjava2.HttpException;

public class VideoListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FragmentVideoListBinding binding;
    SessionManager sessionManager;
    private String CategoryId, CategoryName;
    Video_List_Adapter friendsAdapter;

    GifImageView gifImageView;
    List<CategoryWiseModel.CategoryWiseModelResponse> categoryModelResponseList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());

        if (getArguments() != null) {
            CategoryId = getArguments().getString("CategoryId");
            CategoryName = getArguments().getString("CategoryName");

            binding.tvHeaderName.setText(CategoryName);
        }

        binding.swipeToRefresh.setColorSchemeResources(R.color.red_500);
        binding.swipeToRefresh.setOnRefreshListener(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        if (Connectivity.isConnected(getActivity())) {
            getVideoList();
        } else {
            // utilities.dialogOK(getActivity(), getString(R.string.validation_title), getString(R.string.please_check_internet), getString(R.string.ok), false);
        }

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

        return view;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<CategoryWiseModel.CategoryWiseModelResponse> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (CategoryWiseModel.CategoryWiseModelResponse item : categoryModelResponseList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
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
    private void getVideoList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        //Toast.makeText(getActivity(), CategoryId+""+","+sessionManager.getLanguageid()+"", Toast.LENGTH_SHORT).show();
        apiInterface.GetCategoryWiseList(CategoryId,sessionManager.getUser().getUserid(), sessionManager.getLanguageid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CategoryWiseModel>() {
                    @Override
                    public void onNext(CategoryWiseModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                categoryModelResponseList = response.getResponse();
                                friendsAdapter = new Video_List_Adapter(response.getResponse(), getActivity());
                                binding.setPersonalityAdapter(friendsAdapter);//set databinding adapter
                                friendsAdapter.notifyDataSetChanged();

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


    @Override
    public void onRefresh() {
        binding.swipeToRefresh.setRefreshing(false);
        getVideoList();
    }
}
