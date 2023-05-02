package com.ksbm.ontu.foundation.adapter;

import static com.ksbm.ontu.foundation.fragment.Foundations_List_Fragment.personality;
import static com.ksbm.ontu.utils.Constant.Body_Parts;
import static com.ksbm.ontu.utils.Constant.Colors;
import static com.ksbm.ontu.utils.Constant.Date;
import static com.ksbm.ontu.utils.Constant.Direction;
import static com.ksbm.ontu.utils.Constant.Time;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.FoundationListBinding;
import com.ksbm.ontu.foundation.activity.Foundation_Splash;
import com.ksbm.ontu.foundation.fragment.Foundation_Basics_Fragment;
import com.ksbm.ontu.foundation.model.BookMarkModel;
import com.ksbm.ontu.foundation.model.FoundationTypeModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookMarkAdapter  extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder> {

    private List<BookMarkModel> moviesList;
    public SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    private String PREFS_NAME = "auth_info";
    SessionManager sessionManager;
    Context context;
    int selected=0;
    RecyclerView rvAstroList;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView icon,delete,noselect;

        public MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.image);
            delete = view.findViewById(R.id.delete);
            noselect = view.findViewById(R.id.noselect);
        }
    }


    public BookMarkAdapter(Context context, List<BookMarkModel> moviesList, RecyclerView rvAstroList) {
        this.context=context;
        this.moviesList = moviesList;
        this.rvAstroList = rvAstroList;
    }

    @Override
    public BookMarkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark, parent, false);
        return new BookMarkAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookMarkAdapter.MyViewHolder holder, final int position) {
        final BookMarkModel movie = moviesList.get(position);
        try
        {
//            // decode base64 string
//            byte[] bytes=Base64.decode(movie.getpassword(), Base64.DEFAULT);
//            // Initialize bitmap
//            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            // set bitmap on imageView
//            holder.icon.setImageBitmap(bitmap);

            Glide.with(context).load(movie.getpassword()).into(holder.icon);
            holder.noselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.delete.isShown())
                    {
                        holder.delete.setVisibility(View.GONE);
                        BookMarkModel moviee = new BookMarkModel();
                        moviee.setisselect(false);
                        moviee.setid(movie.getid());
                        moviee.setname(movie.getname());
                        moviee.setpassword(movie.getpassword());
                        personality.set(position,moviee);
                    }
                    else
                    {
                        holder.delete.setVisibility(View.VISIBLE);
                        BookMarkModel moviee = new BookMarkModel();
                        moviee.setisselect(true);
                        moviee.setid(movie.getid());
                        moviee.setname(movie.getname());
                        moviee.setpassword(movie.getpassword());
                        personality.set(position,moviee);
                    }
//                    deleteBookMark(holder.itemView,context,movie.getid());
                }
            });

        }
        catch(Exception e)
        {
            Toast.makeText(context, e+"", Toast.LENGTH_SHORT).show();
        }


        if(personality.get(position).getisselect())
        {
            holder.delete.setVisibility(View.VISIBLE);
            BookMarkModel moviee = new BookMarkModel();
            moviee.setisselect(true);
            moviee.setid(movie.getid());
            moviee.setname(movie.getname());
            moviee.setpassword(movie.getpassword());
            personality.set(position,moviee);
//            moviesList.set(position,moviee);
        }
        else
        {
            holder.delete.setVisibility(View.GONE);
            BookMarkModel moviee = new BookMarkModel();
            moviee.setisselect(false);
            moviee.setid(movie.getid());
            moviee.setname(movie.getname());
            moviee.setpassword(movie.getpassword());
            personality.set(position,moviee);
//            moviesList.set(position,moviee);
        }



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }






}
