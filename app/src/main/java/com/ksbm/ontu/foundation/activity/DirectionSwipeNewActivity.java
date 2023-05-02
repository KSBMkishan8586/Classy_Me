package com.ksbm.ontu.foundation.activity;

import static com.ksbm.ontu.foundation.activity.FoundationQuizActivity.userTotalReward;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.alerts_module.Alerts_Dialog;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.ActivityDirectionSwipeSliderBinding;
import com.ksbm.ontu.foundation.adapter.DirectionSliderAdapter;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop_Image;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Word_Text;
import com.ksbm.ontu.foundation.model.DirectionTask;
import com.ksbm.ontu.free_coin.FreeCoinDialog;
import com.ksbm.ontu.fundamental.model.WordResultStatus;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.SweetAlt;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DirectionSwipeNewActivity extends AppCompatActivity {
    ActivityDirectionSwipeSliderBinding binding;
    Context context;
    int quiz_position = 0;
    List<DirectionTask> directionTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_direction_swipe_slider);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_direction_swipe_slider);

        binding.header.tvTitle.setText(Constant.foundation_name);
        context = DirectionSwipeNewActivity.this;

        addTask();


        //set gif in alert image
        Glide.with(this).load(R.drawable.alert_error).into(binding.header.ivAlert);


        binding.resultViewEast.setOnDragListener(mydragListenerEast);
        binding.resultViewWest.setOnDragListener(mydragListenerWest);
        binding.resultViewNorth.setOnDragListener(mydragListenerNorth);
        binding.resultViewSouth.setOnDragListener(mydragListenerSouth);

        setTask();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().getStringExtra("ComeFrom") != null) {
            if (getIntent().getStringExtra("ComeFrom").equals("Directions")) {
                binding.ivBack.setVisibility(View.INVISIBLE);
            } else {
                binding.ivBack.setVisibility(View.VISIBLE);
            }
        } else {
            binding.ivBack.setVisibility(View.VISIBLE);
        }


        binding.header.llFreeCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FreeCoinDialog.class);
                startActivity(intent);

            }
        });

        binding.header.ivCoin.setOnClickListener(v -> {
            Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });

        binding.header.llAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Alerts_Dialog.class);
                startActivity(intent);
            }
        });

        binding.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, binding.tvQuestion.getText().toString(), false);
            }
        });

        binding.ivStar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                DragTextItem(binding.ivStar);

                return false;
            }
        });

        binding.layoutButton.tvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quiz_position > 0) {
                    quiz_position--;
                    setTask();
                } else if (quiz_position == 0) {
                    // binding.sliderEditor.setCurrentItem(0);
                    finish();
                }
            }
        });

        binding.layoutButton.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quiz_position != directionTasks.size() - 1) {
                    quiz_position++;
                    setTask();

                } else {

                    if (getIntent().getStringExtra("ComeFrom").equals("Directions")) {
                        Speach_Record_Activity.stop_recording();
                        int overallReward = 0;
                        Intent intent = new Intent(context, FoundationWinnerActivity.class);
                        intent.putExtra("QuizResult", getIntent().getStringExtra("QuizResult"));
                        intent.putExtra("QuizReward", getIntent().getStringExtra("QuizReward"));
                        intent.putExtra("foundation_name", getIntent().getStringExtra("foundation_name"));
                        intent.putExtra("foundation_id", getIntent().getStringExtra("foundation_id"));
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }

            }
        });

        binding.ivIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Please " + directionTasks.get(quiz_position).getDirection_ques();
                SweetAlt.OpenFreeCoinDialog(context, msg);
            }
        });

    }

    private void setTask() {
        binding.tvQuestion.setText(directionTasks.get(quiz_position).getDirection_ques());
        Utils.setRainbowColor(binding.tvQuestion);
        binding.ivStar.setVisibility(View.VISIBLE);
        binding.resultEast.setImageDrawable(null);
        binding.resultWest.setImageDrawable(null);
        binding.resultNorth.setImageDrawable(null);
        binding.resultSouth.setImageDrawable(null);

        binding.ivArraowDown.setVisibility(View.INVISIBLE);
        binding.ivArraowUp.setVisibility(View.INVISIBLE);
        binding.ivArraowLeft.setVisibility(View.INVISIBLE);
        binding.ivArraowRight.setVisibility(View.INVISIBLE);
        binding.ivIButton.setVisibility(View.GONE);

    }

    View.OnDragListener mydragListenerEast = new View.OnDragListener() {
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    view.setVisibility(View.INVISIBLE);
                    binding.ivArraowRight.setVisibility(View.VISIBLE);
                    binding.resultEast.setImageDrawable(getResources().getDrawable(R.drawable.swipe_ball));
                    String select_ans;
                    if (directionTasks.get(quiz_position).getDirection_type().equalsIgnoreCase("position")) {
                        select_ans = "Right";
                    } else {
                        select_ans = "East";
                    }
                    Speach_Record_Activity.speakOut(context, select_ans, false);
                    checkRightAns(select_ans);

                    break;
            }

            return true;
        }
    };

    View.OnDragListener mydragListenerWest = new View.OnDragListener() {
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    view.setVisibility(View.INVISIBLE);
                    binding.ivArraowLeft.setVisibility(View.VISIBLE);
                    binding.resultWest.setImageDrawable(getResources().getDrawable(R.drawable.swipe_ball));
                    String select_ans;
                    if (directionTasks.get(quiz_position).getDirection_type().equalsIgnoreCase("position")) {
                        select_ans = "Left";
                    } else {
                        select_ans = "West";
                    }
                    Speach_Record_Activity.speakOut(context, select_ans, false);
                    checkRightAns(select_ans);
                    break;
            }

            return true;
        }
    };

    View.OnDragListener mydragListenerNorth = new View.OnDragListener() {
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    view.setVisibility(View.INVISIBLE);
                    binding.ivArraowUp.setVisibility(View.VISIBLE);
                    binding.resultNorth.setImageDrawable(getResources().getDrawable(R.drawable.swipe_ball));
                    String select_ans;
                    if (directionTasks.get(quiz_position).getDirection_type().equalsIgnoreCase("position")) {
                        select_ans = "Top";
                    } else {
                        select_ans = "North";
                    }
                    Speach_Record_Activity.speakOut(context, select_ans, false);
                    checkRightAns(select_ans);
                    break;
            }

            return true;
        }
    };

    View.OnDragListener mydragListenerSouth = new View.OnDragListener() {
        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            int dragevent = event.getAction();
            switch (dragevent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    view.setVisibility(View.INVISIBLE);
                    binding.ivArraowDown.setVisibility(View.VISIBLE);
                    binding.resultSouth.setImageDrawable(getResources().getDrawable(R.drawable.swipe_ball));
                    String select_ans;
                    if (directionTasks.get(quiz_position).getDirection_type().equalsIgnoreCase("position")) {
                        select_ans = "Bottom";
                    } else {
                        select_ans = "South";
                    }
                    Speach_Record_Activity.speakOut(context, select_ans, false);
                    checkRightAns(select_ans);
                    break;
            }

            return true;
        }
    };

    private void checkRightAns(String select_ans) {
        if (select_ans != null && !select_ans.equalsIgnoreCase("")) {
            if (directionTasks.get(quiz_position).getDirection_ans().equalsIgnoreCase(select_ans)) {
                // Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                Utils.showToastInActivity(DirectionSwipeNewActivity.this, "Right", true);
                binding.ivIButton.setVisibility(View.GONE);
            } else {
                // Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show();
                Utils.showToastInActivity(DirectionSwipeNewActivity.this, "Wrong", false);
                binding.ivIButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void addTask() {
        directionTasks.add(new DirectionTask("Swipe Left", "Left", "Position"));
        directionTasks.add(new DirectionTask("Swipe Right ", "Right", "Position"));
        directionTasks.add(new DirectionTask("Swipe Top ", "Top", "Position"));
        directionTasks.add(new DirectionTask("Swipe Bottom ", "Bottom", "Position"));
        directionTasks.add(new DirectionTask("Swipe East Direction", "East", "Direction"));
        directionTasks.add(new DirectionTask("Swipe West Direction", "West", "Direction"));
        directionTasks.add(new DirectionTask("Swipe North Direction", "North", "Direction"));
        directionTasks.add(new DirectionTask("Swipe South Direction", "South", "Direction"));

    }

    public static void DragTextItem(View v) {
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(data, shadowBuilder, v, 0);
        } else {
            v.startDrag(data, shadowBuilder, v, 0);
        }
    }

}
