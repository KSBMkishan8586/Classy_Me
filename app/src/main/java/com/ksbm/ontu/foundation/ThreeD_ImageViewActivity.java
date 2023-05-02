package com.ksbm.ontu.foundation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ksbm.ontu.R;



public class ThreeD_ImageViewActivity extends AppCompatActivity {
    String imgUrl;
    ImageView iv_back;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_d__image_view);

        context = ThreeD_ImageViewActivity.this;


        Intent i = getIntent();
        imgUrl = i.getStringExtra("imgUrl");

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadPhotoSphere();
    }

    private void loadPhotoSphere() {


        try {
            //  mVRPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            Glide.with(context)
                    .asBitmap().load(imgUrl)
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                      // Toast.makeText(cxt,getResources().getString(R.string.unexpected_error_occurred_try_again),Toast.LENGTH_SHORT).show();
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                      // zoomImage.setImage(ImageSource.bitmap(bitmap));



                                      return false;
                                  }
                              }
                    ).submit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}