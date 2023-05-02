package com.ksbm.ontu.fundamental.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.ksbm.ontu.R;
import com.ksbm.ontu.fundamental.fragment.Fundamental_Toodle_Fragment;
import com.ksbm.ontu.fundamental.model.fundamental_model.Fundamental_Model_Data;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.utils.ClickListionerss;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.skydoves.elasticviews.ElasticLayout;

import java.util.List;

import static com.ksbm.ontu.utils.Constant.Fundamental_current_item;
import static com.ksbm.ontu.utils.Constant.upgradePackage;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<Fundamental_Model_Data> listarray;
    int positions=0;
    MeraSharedPreferance meraSharedPreferance;

    public SliderAdapter(Context context, List<Fundamental_Model_Data> listarray1) {
        this.context = context;
        this.listarray = listarray1;
        meraSharedPreferance = MeraSharedPreferance.getInstance(context);
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
        View view = layoutInflater.inflate(R.layout.slider_fundamental_course, container, false);

        ElasticLayout rel_item = view.findViewById(R.id.rel_item);
        RelativeLayout rlbackground = view.findViewById(R.id.rlbackground);
        TextView tv_title_course = view.findViewById(R.id.tv_title_course);
        TextView tv_prize = view.findViewById(R.id.tv_prize);
        TextView tv_earning = view.findViewById(R.id.tv_earning);

        tv_title_course.setText(listarray.get(position).getFundamentalName());

        if(listarray.get(position).getFundamentalName().length()>10)
        {
            tv_title_course.setTextSize(16f);
        }

        tv_prize.setText(context.getResources().getString(R.string.reward) + ": "+  listarray.get(position).getReward());

        if (listarray.get(position).getEarning() !=null && !listarray.get(position).getEarning().equalsIgnoreCase("") && !listarray.get(position).getEarning().equalsIgnoreCase("null")){
            tv_earning.setText(context.getResources().getString(R.string.earnings) + ": "+listarray.get(position).getEarning());
        }else {
            tv_earning.setText(context.getResources().getString(R.string.earnings) + ": ---");
        }


        if(positions==0)
        {
            positions++;
            rlbackground.setBackgroundResource(R.drawable.carda);
        } else if(positions==1)
        {
            positions++;
            rlbackground.setBackgroundResource(R.drawable.cardb);
        } else if(positions==2)
        {
            positions++;
            rlbackground.setBackgroundResource(R.drawable.cardc);
        } else if(positions==3)
        {
            positions=0;
            rlbackground.setBackgroundResource(R.drawable.cardd);
        }

//        Glide
//                .with(context)
//                .load(listarray.get(position).getImageLink())
//                //.centerCrop()
//                // .placeholder(R.drawable.loading_spinner)
//                .into(imageView);

        rel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position<=3){
                    Fundamental_current_item = position;
                    Fragment fragment_home = new Fundamental_Toodle_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Fundamental_id", listarray.get(position).getFundamentalId());
                    bundle.putString("Fundamental_title", listarray.get(position).getFundamentalName());
                    FragmentTransaction ft_home = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.frame, fragment_home);
                    ft_home.addToBackStack(null);
                    ft_home.commit();
                    fragment_home.setArguments(bundle);
                }else{
                    if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
                        SweetAlt.OpenPaymentDialog(context, Constant.upgrade_course, new ClickListionerss() {
                            @Override
                            public void clickYes() {
                                context.startActivity(new Intent(context, PaymentActivity.class).putExtra("price",upgradePackage));
                            }
                            @Override
                            public void clickNo() {

                            }
                        });
                    }else{
                        Fundamental_current_item = position;
                        Fragment fragment_home = new Fundamental_Toodle_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("Fundamental_id", listarray.get(position).getFundamentalId());
                        bundle.putString("Fundamental_title", listarray.get(position).getFundamentalName());
                        FragmentTransaction ft_home = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                        ft_home.replace(R.id.frame, fragment_home);
                        ft_home.addToBackStack(null);
                        ft_home.commit();
                        fragment_home.setArguments(bundle);
                    }


                }




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
