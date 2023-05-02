package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.foundation.model.FoundationCourseModel;
import com.ksbm.ontu.utils.Constant;

import java.util.List;

public class SliderAdapter_Basics extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<FoundationCourseModel.FoundationCourseResponse> listarray;

    public SliderAdapter_Basics(Context context, List<FoundationCourseModel.FoundationCourseResponse> listarray1) {
        this.context = context;
        this.listarray = listarray1;
    }


    @Override
    public int getCount() {
        return listarray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slider_foundation_course, container, false);

        RelativeLayout rel_item = view.findViewById(R.id.rel_item);
        // TextView tv_title_course = view.findViewById(R.id.tv_title_course);
        TextView tv_prize = view.findViewById(R.id.tv_prize);
        TextView tv_earning = view.findViewById(R.id.tv_earning);
        ImageView iv_foundation_img = view.findViewById(R.id.iv_foundation_img);

        // tv_title_course.setText(listarray.get(position).getTitle());
        tv_prize.setText("Reward: " + listarray.get(position).getReward());

        if (!listarray.get(position).getEarning().equalsIgnoreCase("0")) {
            tv_earning.setText("Earnings: " + listarray.get(position).getEarning());
        } else {
            tv_earning.setText("Earnings: ---");
        }


        Glide.with(context)
                .load(listarray.get(position).getIconImage())
                //.centerCrop()
                 .placeholder(R.drawable.basics_foundation)
                .error(R.drawable.basics_foundation)
                .into(iv_foundation_img);

        rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.foundation_id = listarray.get(position).getFoundationId();
                Constant.foundation_name = listarray.get(position).getFoundationName();

//                Fragment fragment_home = new Offline_Splash_Fragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("foundation_id", listarray.get(position).getFoundationId());
//                bundle.putString("foundation_name", listarray.get(position).getFoundationName());
//                FragmentTransaction ft_home = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//                ft_home.replace(R.id.frame, fragment_home);
//                ft_home.addToBackStack(null);
//                ft_home.commit();
//                fragment_home.setArguments(bundle);

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
