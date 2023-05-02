package com.ksbm.ontu.fundamental.adapter;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ToodleListBinding;
import com.ksbm.ontu.fundamental.fragment.Fundamental_Workbook;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Toodle_Data;

import java.util.List;

import static com.ksbm.ontu.utils.Utils.getVideoIdFromYoutubeUrl;
import static com.ksbm.ontu.utils.Utils.isValidYoutubeUrl;

public class Toodle_List_Adapter extends RecyclerView.Adapter<Toodle_List_Adapter.ViewHolder> {
    private List<Fundamental_Toodle_Data> dataModelList;
    Context context;


    public Toodle_List_Adapter(List<Fundamental_Toodle_Data> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ToodleListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.toodle_list, parent, false);
        return new ViewHolder(binding);

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fundamental_Toodle_Data dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_round);
        requestOptions.error(R.drawable.logo_round);
        requestOptions.isMemoryCacheable();

        if (dataModel.getOther_link() != null && !dataModel.getOther_link().equalsIgnoreCase("")) {

            if (isValidYoutubeUrl(dataModel.getOther_link())){
                //for you tube url
                String videoId = getVideoIdFromYoutubeUrl(dataModel.getOther_link());
                String url = "https://img.youtube.com/vi/" + videoId + "/0.jpg";
                Glide.with(context).load(url).into(holder.itemRowBinding.ivThumb);
                Toast.makeText(context, "this 00", Toast.LENGTH_SHORT).show();
            }else {

                Glide.with(context).setDefaultRequestOptions(requestOptions)
                        .load(dataModel.getOther_link())
                        .into(holder.itemRowBinding.ivThumb);
            }

        } else if (dataModel.getBannerFile() != null && !dataModel.getBannerFile().equalsIgnoreCase("")) {

//            Bitmap bitmap = getThumblineImage(dataModel.getBannerFile());
//            holder.itemRowBinding.ivThumb.setImageBitmap(bitmap);

            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(dataModel.getBannerFile())
                    .into(holder.itemRowBinding.ivThumb);
        }else{
            Glide.with(context).setDefaultRequestOptions(requestOptions)
                    .load(R.drawable.logo_round)
                    .into(holder.itemRowBinding.ivThumb);
        }

        if (dataModel.getProgress() != null && !dataModel.getProgress().equalsIgnoreCase("")) {
            holder.itemRowBinding.progressBar.setProgress(Integer.parseInt(dataModel.getProgress()));
        }


        holder.itemRowBinding.llToodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment_home = new Fundamental_Workbook();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ToodleData", dataModel);
                FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                ft_home.replace(R.id.frame, fragment_home);
                ft_home.addToBackStack(null);
                ft_home.commit();
                fragment_home.setArguments(bundle);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ToodleListBinding itemRowBinding;

        public ViewHolder(ToodleListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

    public static Bitmap getThumblineImage(String videoPath) {
        return ThumbnailUtils.createVideoThumbnail(videoPath, MINI_KIND);
    }


}
