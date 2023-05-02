package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.foundation.model.DirectionTask;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;

import java.util.List;


public class DirectionSliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<DirectionTask> quizDetails;
    SessionManager sessionManager;
    String select_ans= "";

    public DirectionSliderAdapter(Context context, List<DirectionTask> listarray1) {
        this.context = context;
        this.quizDetails = listarray1;
        sessionManager = new SessionManager(context);
    }

    @Override
    public int getCount() {
        return quizDetails.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int quiz_position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.direction_slider, container, false);
        container.addView(view);

        DirectionTask directionTask = quizDetails.get(quiz_position);

        TextView tv_question = view.findViewById(R.id.tv_question);
        RelativeLayout rel_img_left = view.findViewById(R.id.rel_img_left);
        RelativeLayout rel_img_right = view.findViewById(R.id.rel_img_right);
        RelativeLayout rel_img_top = view.findViewById(R.id.rel_img_top);
        RelativeLayout rel_img_bottom = view.findViewById(R.id.rel_img_bottom);
        //  ll_get_coin.setVisibility(View.GONE);
        // Speach_Record_Activity.mFileName = null;
        tv_question.setText(directionTask.getDirection_ques());
        Utils.setRainbowColor(tv_question);

        rel_img_bottom.setEnabled(true);
        rel_img_right.setEnabled(true);
        rel_img_top.setEnabled(true);
        rel_img_left.setEnabled(true);

        tv_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, tv_question.getText().toString(), false);
            }
        });

        rel_img_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directionTask.getDirection_type().equalsIgnoreCase("position")){
                     select_ans= "Bottom";
                }else {
                     select_ans= "South";
                }
                Speach_Record_Activity.speakOut(context, select_ans, false);

                checkRightAns(select_ans, rel_img_bottom, quiz_position);
                rel_img_right.setEnabled(false);
                rel_img_top.setEnabled(false);
                rel_img_left.setEnabled(false);
                rel_img_bottom.setEnabled(false);
            }
        });

        rel_img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directionTask.getDirection_type().equalsIgnoreCase("position")){
                    select_ans= "Left";
                }else {
                    select_ans= "West";
                }
                Speach_Record_Activity.speakOut(context, select_ans, false);
                checkRightAns(select_ans, rel_img_left, quiz_position);
                rel_img_right.setEnabled(false);
                rel_img_top.setEnabled(false);
                rel_img_left.setEnabled(false);
                rel_img_bottom.setEnabled(false);

            }
        });

        rel_img_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directionTask.getDirection_type().equalsIgnoreCase("position")){
                    select_ans= "Top";
                }else {
                    select_ans= "North";
                }
                Speach_Record_Activity.speakOut(context, select_ans, false);
                checkRightAns(select_ans, rel_img_top, quiz_position);
                rel_img_right.setEnabled(false);
                rel_img_top.setEnabled(false);
                rel_img_left.setEnabled(false);
                rel_img_bottom.setEnabled(false);
            }
        });

        rel_img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directionTask.getDirection_type().equalsIgnoreCase("position")){
                    select_ans= "Right";
                }else {
                    select_ans= "East";
                }
                Speach_Record_Activity.speakOut(context, select_ans, false);
                checkRightAns(select_ans, rel_img_right, quiz_position);
                rel_img_right.setEnabled(false);
                rel_img_top.setEnabled(false);
                rel_img_left.setEnabled(false);
                rel_img_bottom.setEnabled(false);
            }
        });


        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkRightAns(String select_ans, RelativeLayout rel_layout, int quiz_position) {
        if (select_ans!=null && !select_ans.equalsIgnoreCase("")){
            if (quizDetails.get(quiz_position).getDirection_ans().equalsIgnoreCase(select_ans)){
                Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                rel_layout.setBackground(context.getResources().getDrawable(R.drawable.circle_fill_green));
               // binding.relRow1.setEnabled(false);

            }else {
                Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                rel_layout.setBackground(context.getResources().getDrawable(R.drawable.circle_fill_red));
            }
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
