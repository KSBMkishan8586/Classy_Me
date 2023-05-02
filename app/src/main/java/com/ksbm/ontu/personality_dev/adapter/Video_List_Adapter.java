package com.ksbm.ontu.personality_dev.adapter;

import static com.ksbm.ontu.utils.Utils.getVideoIdFromYoutubeUrl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.databinding.VideoListBinding;
import com.ksbm.ontu.main_screen.model.SuccesModel;
import com.ksbm.ontu.personality_dev.PersonalityVideoPlayer;
import com.ksbm.ontu.personality_dev.model.CategoryWiseModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.SweetAlt;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class Video_List_Adapter extends RecyclerView.Adapter<Video_List_Adapter.ViewHolder> {
    private List<CategoryWiseModel.CategoryWiseModelResponse> dataModelList;
    Context context;
    SessionManager sessionManager;

    public Video_List_Adapter(List<CategoryWiseModel.CategoryWiseModelResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
        sessionManager = new SessionManager(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.video_list, parent, false);

        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryWiseModel.CategoryWiseModelResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        if (dataModel.getVideoUrlPath() != null && !dataModel.getVideoUrlPath().isEmpty()) {

            if (dataModel.getVideoType().equalsIgnoreCase("youtube")) {
                String videoId = getVideoIdFromYoutubeUrl(dataModel.getVideoUrlPath());
                String url = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
                Glide.with(context).load(url).centerCrop().into(holder.itemRowBinding.ivVideoThumb);
            } else {
                RequestOptions requestOptions = new RequestOptions();
//                requestOptions.placeholder(R.drawable.video_show);
//                requestOptions.error(R.drawable.video_show);
                requestOptions.isMemoryCacheable();

                Glide.with(context).setDefaultRequestOptions(requestOptions)
                        .load(dataModel.getVideoUrlPath()).centerCrop()
                        .into(holder.itemRowBinding.ivVideoThumb);
            }

        }

        holder.itemRowBinding.llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = context.getResources().getString(R.string.app_name);
                String msg = "Video charge Coin - " + dataModel.getCoin_charge() + "\n" + "Earning Coin - " + dataModel.getCoinBonus();
                SweetAlt.normalDialog(context, title, msg, true, true, "Cancel", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        deductCoin(sessionManager.getUser().getUserid(), dataModel.getId(), dataModel.getCategoryId(), dataModel);
                    }
                });

            }
        });

    }


    @SuppressLint("CheckResult")
    private void deductCoin(String userid, String videoid, String categoryId, CategoryWiseModel.CategoryWiseModelResponse dataModel) {

        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.PersonalityVideoCharge(userid, videoid, categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SuccesModel>() {
                    @Override
                    public void onNext(SuccesModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {

                                if (dataModel.getVideoUrlPath() != null && !dataModel.getVideoUrlPath().equalsIgnoreCase("")) {
                                    Intent intent = new Intent(context, PersonalityVideoPlayer.class);
                                    intent.putExtra("FilePath", dataModel.getVideoUrlPath());
                                    intent.putExtra("Video_type", dataModel.getVideoType());
                                    intent.putExtra("Player_type", dataModel.getPlayer_type());
                                    intent.putExtra("Drive_Link", dataModel.getDriverLink());
                                    intent.putExtra("Video_Id", dataModel.getId());
                                    intent.putExtra("Coin", dataModel.getCoinBonus());
                                    intent.putExtra("Category_Id", dataModel.getCategoryId());
                                    intent.putExtra("ComeFrom", "PD");
                                    intent.putExtra("first_open", dataModel.getfirstopen());
                                    intent.putExtra("Duration", dataModel.getDuration());
                                    context.startActivity(intent);
                                }
                                Toast.makeText(context, "-"+dataModel.getCoin_charge()+" Coins deducted", Toast.LENGTH_SHORT).show();

                            } else {
                                SweetAlt.showErrorMessage(context, "" + response.getMessage(), response.getMessage());

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
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public VideoListBinding itemRowBinding;

        public ViewHolder(VideoListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


    // method for filtering our recyclerview items.
    public void filterList(List<CategoryWiseModel.CategoryWiseModelResponse> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        dataModelList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
