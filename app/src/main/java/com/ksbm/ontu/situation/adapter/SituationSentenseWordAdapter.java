package com.ksbm.ontu.situation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.Speach_Record_Activity;
import com.ksbm.ontu.databinding.RowtextWordBinding;
import com.ksbm.ontu.utils.Utils;

import java.util.ArrayList;

public class SituationSentenseWordAdapter extends RecyclerView.Adapter<SituationSentenseWordAdapter.ViewHolder> {
    Context context;
    ArrayList<String> word_list = new ArrayList<String>();

    public SituationSentenseWordAdapter(ArrayList<String> list, Context ctx) {
        this.word_list = list;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowtextWordBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rowtext_word, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemRowBinding.textRow.setText(word_list.get(position));
        Utils.setRandomColorWord(holder.itemRowBinding.cardRowtextWord, context);

        holder.itemRowBinding.cardRowtextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speach_Record_Activity.speakOut(context, holder.itemRowBinding.textRow.getText().toString(), false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return word_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RowtextWordBinding itemRowBinding;

        public ViewHolder(RowtextWordBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }
}
