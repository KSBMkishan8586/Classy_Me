package com.ksbm.ontu.foundation.drawing;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.R;
import com.ksbm.ontu.foundation.drawing.util.Method;
import com.ksbm.ontu.foundation.model.Drawing_Model;

import java.util.List;


public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private Method method;
    private Context activity;
    private int columnWidth;
    //private int[] image;
    List<Drawing_Model.DrawingResponse> image;

    public ViewAdapter(Context activity, List<Drawing_Model.DrawingResponse> image, Method method) {
        this.activity = activity;
        this.image = image;
        this.method = method;
        columnWidth = method.getScreenWidth();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.view_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.imageView.setLayoutParams(new ConstraintLayout.LayoutParams(columnWidth / 2, columnWidth / 2));

        Glide.with(activity).load(image.get(position).getDrawingImages())
                .placeholder(R.drawable.placeholder_square)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> method.click(position, ""));

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_view_adapter);

        }
    }
}
