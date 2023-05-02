package com.ksbm.ontu.fundamental.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FundamentalJumbleWordBinding;
import com.ksbm.ontu.fundamental.activity.Fundamental_Jumble_Arrange;
import com.ksbm.ontu.fundamental.activity.Fundamental_Quiz_Drag_Drop;
import com.ksbm.ontu.fundamental.model.jumble_arrange_model.JumbleArrangeQuizWord;
import com.ksbm.ontu.situation.fragment.Fragment_Drag_Drop;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class FundamentalJumbleWord_Adapter extends RecyclerView.Adapter<FundamentalJumbleWord_Adapter.ViewHolder> {
    private List<JumbleArrangeQuizWord> dataModelList;
    Context context;

    public FundamentalJumbleWord_Adapter(List<JumbleArrangeQuizWord> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FundamentalJumbleWordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fundamental_jumble_word, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JumbleArrangeQuizWord dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);


//        holder.itemRowBinding.cardItem.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                if (context instanceof Fundamental_Jumble_Arrange) {
//                    ((Fundamental_Jumble_Arrange)context).DragTextItem(view, position);
//                }
//                return true;
//            }
//        });

        Utils.setRandomColorWord(holder.itemRowBinding.cardItem, context);

        holder.itemRowBinding.cardItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((Fundamental_Jumble_Arrange)context).DragTextItem(view, position);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FundamentalJumbleWordBinding itemRowBinding;

        public ViewHolder(FundamentalJumbleWordBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


}
