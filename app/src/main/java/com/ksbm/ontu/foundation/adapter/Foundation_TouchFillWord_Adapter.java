package com.ksbm.ontu.foundation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationTouchWordQuizItemBinding;
import com.ksbm.ontu.databinding.FoundationWordQuizItemBinding;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop_Image;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Fill;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Word_Text;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Foundation_TouchFillWord_Adapter extends RecyclerView.Adapter<Foundation_TouchFillWord_Adapter.ViewHolder> {
    private List<String> dataModelList;
    Context context;
    String Quiz_type;

    public Foundation_TouchFillWord_Adapter(ArrayList<String> dataModelList, Context ctx, String quiz_type) {
        this.dataModelList = dataModelList;
        context = ctx;
        Quiz_type = quiz_type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FoundationTouchWordQuizItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.foundation_touch_word_quiz_item, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemRowBinding.tvWord.setText(dataModelList.get(position));
        Utils.setRandomColorWord(holder.itemRowBinding.cardItem, context);

        holder.itemRowBinding.cardItem.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Speach_Record_Activity.speakOut(context, holder.itemRowBinding.tvWord.getText().toString(), false);


                if (Quiz_type.equalsIgnoreCase("text_touch_fill")){
                    Foundation_Touch_Fill.CheckRightAnswer(holder.itemRowBinding.cardItem, position, dataModelList.get(position), context);
                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoundationTouchWordQuizItemBinding itemRowBinding;

        public ViewHolder(FoundationTouchWordQuizItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }

}
