package com.ksbm.ontu.foundation.drawing;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.google.android.material.textview.MaterialTextView;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.foundation.drawing.interfaces.OnClick;
import com.ksbm.ontu.foundation.drawing.util.Constant;
import com.ksbm.ontu.foundation.drawing.util.Method;
import com.ksbm.ontu.foundation.model.Drawing_Model;
import com.ksbm.ontu.fundamental.adapter.SliderAdapter;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Model;
import com.ksbm.ontu.utils.SweetAlt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class DrawingImageFragment extends Fragment {

    //public int[] image;
    RecyclerView recyclerView;
    MaterialTextView textViewNoData;
    Method method;
    List<Drawing_Model.DrawingResponse> imageList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawing_image_fragment, container, false);

        OnClick onClick = (OnClick) (position, type) -> {
           // Constant.image = image;
            startActivity(new Intent(getActivity(), Drawing.class).putExtra("positionImg", imageList.get(position).getDrawingImages()));
        };

         method = new Method(getActivity(), onClick);

        //image = new int[]{R.drawable.image, R.drawable.image_a};
         textViewNoData = view.findViewById(R.id.textView_noData_home);
         recyclerView = view.findViewById(R.id.recyclerView_fragment);

        getAllImage();


        return view;

    }

    @SuppressLint("CheckResult")
    private void getAllImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.GetDwawingImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Drawing_Model>() {
                    @Override
                    public void onNext(Drawing_Model response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus()==1) {
                                if (response.getResponse()!=null && response.getResponse().size() > 0){
                                    imageList= response.getResponse();

                                    recyclerView.setHasFixedSize(true);
                                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                                    recyclerView.setLayoutManager(layoutManager);

                                    ViewAdapter viewAdapter = new ViewAdapter(getActivity(), imageList, method);
                                    recyclerView.setAdapter(viewAdapter);
                                    textViewNoData.setVisibility(View.GONE);
                                }else {

                                }

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
}
