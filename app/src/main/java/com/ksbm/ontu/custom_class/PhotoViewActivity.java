package com.ksbm.ontu.custom_class;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import com.ksbm.ontu.R;
import com.ksbm.ontu.utils.LruBitmapCache;

import uk.co.senab.photoview.PhotoViewAttacher;

import java.io.File;

public class PhotoViewActivity extends AppCompatActivity {

    private static final String TAG = "photo_view_activity";
    ImageView photoView, iv_back;
    PhotoViewAttacher mAttacher;
    ImageLoader imageLoader;
    String imgUrl;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        Intent i = getIntent();
        imgUrl = i.getStringExtra("imgUrl");

        photoView = findViewById(R.id.photoImageView);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //using drawable
//        if (imgUrl!=null){
//            Bitmap bitmap = BitmapFactory.decodeFile(new File(imgUrl).getPath());
//            photoView.setImageBitmap(bitmap);
//            mAttacher = new PhotoViewAttacher(photoView);
//        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        //using server url
        imageLoader = getImageLoader();
        imageLoader.get(imgUrl, new ImageLoader.ImageListener() {

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean isImmediate) {

                photoView.setImageBitmap(imageContainer.getBitmap());
                mAttacher = new PhotoViewAttacher(photoView);

                progressDialog.dismiss();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(PhotoViewActivity.this);
        }

        return mRequestQueue;
    }

}
