package com.ksbm.ontu.foundation.drawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.ksbm.ontu.R;
import com.ksbm.ontu.foundation.drawing.util.Method;

public class DrawingImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_image);

        Method method = new Method(DrawingImageActivity.this);
        method.forceRTLIfSupported();

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            //set fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main, new DrawingImageFragment(),
                    getResources().getString(R.string.home)).commitAllowingStateLoss();
            //select(0);
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
     finish();
    }

}