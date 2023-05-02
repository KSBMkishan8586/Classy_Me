package com.ksbm.ontu.foundation.adapter;

import static com.ksbm.ontu.utils.Constant.FoundationLearningCoinBonus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.CommonUtil;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.FoundationWordQuizItemBinding;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop;
import com.ksbm.ontu.foundation.fragment.Foundation_Drag_Drop_Image;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Fill;
import com.ksbm.ontu.foundation.fragment.Foundation_Touch_Word_Text;
import com.ksbm.ontu.foundation.model.FoundationQuizModel;
import com.ksbm.ontu.utils.Utils;

import java.util.List;

public class Foundation_Drag_Drop_Adapter extends RecyclerView.Adapter<Foundation_Drag_Drop_Adapter.ViewHolder> {
    private List<FoundationQuizModel.FoundationWord> dataModelList;
    Context context;
    String Quiz_type;
    boolean isPlayed= false;

    public Foundation_Drag_Drop_Adapter(List<FoundationQuizModel.FoundationWord> dataModelList, Context ctx, String quiz_type) {
        this.dataModelList = dataModelList;
        context = ctx;
        Quiz_type = quiz_type;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FoundationWordQuizItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.foundation_word_quiz_item, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FoundationQuizModel.FoundationWord dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);

        Utils.setRandomColorWord(holder.itemRowBinding.cardItem, context);

        holder.itemRowBinding.cardItem.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Speach_Record_Activity.speakOut(context, holder.itemRowBinding.tvWord.getText().toString(), false);

                if (Quiz_type.equalsIgnoreCase("text")){
                    Foundation_Drag_Drop.DragTextItem(holder.itemRowBinding.cardItem, position);
                }else if (Quiz_type.equalsIgnoreCase("image")){
                    Foundation_Drag_Drop_Image.DragTextItem(holder.itemRowBinding.cardItem, position);
                }else if (Quiz_type.equalsIgnoreCase("text_touch_word")){
                    Foundation_Touch_Word_Text.CheckRightAnswer(holder.itemRowBinding.cardItem, position, dataModel.getWord());

                    if(!isPlayed){
                        Utils.playMusic(R.raw.coin_sound, context);
                        isPlayed= true;
                    }
                }
//                else if (Quiz_type.equalsIgnoreCase("text_touch_fill")){
//                    Foundation_Touch_Fill.CheckRightAnswer(holder.itemRowBinding.relBg, position, dataModel.getWord());
//                }

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoundationWordQuizItemBinding itemRowBinding;

        public ViewHolder(FoundationWordQuizItemBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
