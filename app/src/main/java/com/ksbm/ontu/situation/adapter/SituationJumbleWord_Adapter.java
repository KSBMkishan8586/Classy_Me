package com.ksbm.ontu.situation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;

import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.SituationJumbleWordBinding;
import com.ksbm.ontu.foundation.activity.BodyPartDrop;
import com.ksbm.ontu.situation.SituationQuizPatternActivity;
import com.ksbm.ontu.situation.fragment.Fragment_Drag_Drop;
import com.ksbm.ontu.situation.model.SituationQuestionModel;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class SituationJumbleWord_Adapter extends RecyclerView.Adapter<SituationJumbleWord_Adapter.ViewHolder> {
    private List<SituationQuestionModel.Word_Question> dataModelList;
    Context context;
    Fragment fragment_drag_drop;

    public SituationJumbleWord_Adapter(List<SituationQuestionModel.Word_Question> dataModelList, Context ctx,
                                       Fragment fragment_drag_drop) {
        this.dataModelList = dataModelList;
        context = ctx;
        this.fragment_drag_drop = fragment_drag_drop;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SituationJumbleWordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.situation_jumble_word, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SituationQuestionModel.Word_Question dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        Utils.setRandomColorWord(holder.itemRowBinding.cardItem, context);


        if (SituationQuizPatternActivity.QuestionType.equalsIgnoreCase("jumble rearrange"))
        {
            holder.itemRowBinding.cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Fragment_Drag_Drop)fragment_drag_drop).DragTextItem(v, position);
                    holder.itemRowBinding.cardItem.setVisibility(View.INVISIBLE);
                    //Fragment_Drag_Drop.binding.result.setText(dataModel.getSituationWord());
                    Fragment_Drag_Drop.binding.result.append(dataModel.getSituationWord() + " ");
                }
            });
        }
        else {
            holder.itemRowBinding.cardItem.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ((Fragment_Drag_Drop)fragment_drag_drop).DragTextItem(view, position);
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_MOVE: {
                            Fragment_Drag_Drop.binding.tvCheckAns.setBackgroundResource(R.drawable.rectangle_bg_green);
                            return true;
                        }
                        case MotionEvent.ACTION_UP: {

                            return true;

                        }
                        case MotionEvent.ACTION_DOWN: {

                            return true;
                        }
                    }
                    return false;
                }
            });
        }





    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SituationJumbleWordBinding itemRowBinding;

        public ViewHolder(SituationJumbleWordBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
