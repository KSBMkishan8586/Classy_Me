package com.ksbm.ontu.main_screen.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.main_screen.model.OneTimeBannerModel;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterBanner extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<OneTimeBannerModel.OneTimeBannerResponse> listarray;

    public SliderAdapterBanner(Context context, List<OneTimeBannerModel.OneTimeBannerResponse> bannerdata) {
        this.context = context;
        this.listarray = bannerdata;
    }

//    public int[] sliderImage = {
//            R.drawable.livewire_png,
//            R.drawable.livewire_png,
//            R.drawable.livewire_png
//    };

    @Override
    public int getCount() {
        return listarray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.list_item_onetimebanner, container, false);

        ImageView imageView = view.findViewById(R.id.iv_icon);

        Glide.with(context)
                .load(listarray.get(position).getFilename())
                //.centerCrop()
               // .placeholder(R.drawable.background_splash)
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
