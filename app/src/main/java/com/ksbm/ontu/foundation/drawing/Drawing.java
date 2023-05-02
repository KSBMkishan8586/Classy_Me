package com.ksbm.ontu.foundation.drawing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.drawing.Util.CanvasView;
import com.google.android.material.appbar.MaterialToolbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ksbm.ontu.R;
import com.ksbm.ontu.foundation.drawing.database.DatabaseHandler;
import com.ksbm.ontu.foundation.drawing.item.ColorList;
import com.ksbm.ontu.foundation.drawing.util.Constant;
import com.ksbm.ontu.foundation.drawing.util.Events;
import com.ksbm.ontu.foundation.drawing.util.GlobalBus;
import com.ksbm.ontu.foundation.drawing.util.Method;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ksbm.ontu.utils.Constant.drawing;

public class Drawing extends AppCompatActivity {

    private Method method;
    //private int position;
    private Dialog dialog;
    private DatabaseHandler db;
    private MaterialToolbar toolbar;
    private SeekBar seekBar;
    private CanvasView canvasView;
    private RecyclerView recyclerView;
    private ColorAdapter colorAdapter;
    private List<ColorList> colorLists;
    private ProgressDialog progressDialog;
    private ConstraintLayout constraintLayout;
    private boolean isShare = false;
    String positionImg;
    Bitmap bitmap;
    //URL url_value = null;
    // Bitmap mIcon1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        method = new Method(Drawing.this);
        method.forceRTLIfSupported();

        GlobalBus.getBus().register(this);

        db = new DatabaseHandler(Drawing.this);
        colorLists = new ArrayList<>();

        progressDialog = new ProgressDialog(Drawing.this);

        toolbar = findViewById(R.id.toolbar_drawing);
        toolbar.setTitle(getResources().getString(R.string.drawing));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog = new Dialog(Drawing.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (method.isRtl()) {
            dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
        seekBar = dialog.findViewById(R.id.seekBarVolume_dialog);
        seekBar.setProgress(20);

        canvasView = findViewById(R.id.canvas_drawing);
        canvasView.paintStork(20);
        canvasView.paintColor(Constant.color_chose);
        if (db.isColorCode(Constant.color_chose)) {
            db.addColor(Constant.color_chose);
        }
        colorLists = db.getColorDetail();

        positionImg = getIntent().getStringExtra("positionImg");

        //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getBitmapFromURL(positionImg));

        constraintLayout = findViewById(R.id.constraintLayout_imageView_drawing);
        ImageView imageView = findViewById(R.id.imageView_drawing);
        ImageView imageViewEraser = findViewById(R.id.imageView_eraser_drawing);
        ImageView imageViewPaintPalette = findViewById(R.id.imageView_paintPalette_drawing);
        ImageView imageViewPaintBrush = findViewById(R.id.imageView_paintBrush_drawing);

        LinearLayout ll_color = findViewById(R.id.ll_color);
        LinearLayout ll_eraser = findViewById(R.id.ll_eraser);
        LinearLayout ll_brush = findViewById(R.id.ll_brush);
        // imageView.setImageBitmap(bitmap);

        new GetImageFromUrl(imageView).execute(positionImg);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(positionImg);
//                    Bitmap myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//
//                    Log.e("keshav", "Bitmap " + myBitmap);
//                    imageView.setImageBitmap( myBitmap);
//
//                } catch (IOException e) {
//                    Log.e("keshav", "Exception " + e.getMessage());
//                }
//            }
//        });

        recyclerView = findViewById(R.id.recyclerView_drawing);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Drawing.this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        colorAdapter = new ColorAdapter(Drawing.this, colorLists, canvasView);
        recyclerView.setAdapter(colorAdapter);

        imageViewEraser.setOnClickListener(v -> {
            ll_brush.setBackgroundResource(R.drawable.rectangle_bg_white);
            ll_color.setBackgroundResource(R.drawable.rectangle_bg_white);
            ll_eraser.setBackgroundResource(R.drawable.rect_golden);

            canvasView.paintColor(Color.parseColor("#FFFFFF"));

        });

        imageViewPaintPalette.setOnClickListener(v -> {

            ll_brush.setBackgroundResource(R.drawable.rectangle_bg_white);
            ll_color.setBackgroundResource(R.drawable.rect_golden);
            ll_eraser.setBackgroundResource(R.drawable.rectangle_bg_white);

            startActivity(new Intent(Drawing.this, ColorChose.class));
        });

        imageViewPaintBrush.setOnClickListener(v -> {
            ll_brush.setBackgroundResource(R.drawable.rect_golden);
            ll_color.setBackgroundResource(R.drawable.rectangle_bg_white);
            ll_eraser.setBackgroundResource(R.drawable.rectangle_bg_white);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    canvasView.paintStork(progress / 2);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            dialog.show();
        });

    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        ProgressDialog progressDialog;
        ImageView imageView;

        public GetImageFromUrl(ImageView img) {
            this.imageView = img;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Drawing.this, R.style.MyGravity);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);

            progressDialog.dismiss();
        }
    }


    @Subscribe
    public void getData(Events.ColorNotify colorNotify) {
        colorLists.clear();
        colorLists = db.getColorDetail();
        colorAdapter = new ColorAdapter(Drawing.this, colorLists, canvasView);
        recyclerView.setAdapter(colorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawing_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_share_main:
                isShare = true;
                new SaveShare().execute();
                Toast.makeText(Drawing.this, getResources().getString(R.string.share), Toast.LENGTH_SHORT).show();
                break;

            case R.id.ic_restart:
                canvasView.clear(getResources().getColor(R.color.canvasBg_drawing));
                break;

            case R.id.screen_short:
                Dexter.withContext(this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                new SaveShare().execute();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                // check for permanent denial of permission
                                method.alertBox(getResources().getString(R.string.cannot_use_save_share));
                                if (response.isPermanentlyDenied()) {
                                    // navigate user to app settings
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
                break;

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public class SaveShare extends AsyncTask<String, String, String> {

        String filePath;
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.show();

            // create bitmap screen capture
            constraintLayout.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(constraintLayout.getDrawingCache());
            constraintLayout.setDrawingCacheEnabled(false);

        }

        @Override
        protected String doInBackground(String... strings) {

            String mPath;
            if (isShare) {
                mPath = getExternalCacheDir().getAbsolutePath();
            } else {
                // mPath = Environment.getExternalStorageDirectory() + drawing;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + drawing;
                } else {
                    mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + drawing;
                }
            }
            File imageFile = new File(mPath);

            if (!imageFile.exists()) {
                imageFile.mkdirs();
            }

            Random generator = new Random();
            filePath = imageFile.toString() + "/" + "Image-" + generator.nextInt(1000) + ".jpg";

            try {

                FileOutputStream outputStream = new FileOutputStream(filePath);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (isShare) {
                isShare = false;
                method.share(filePath);
            } else {
                method.alertBox(getResources().getString(R.string.save_drawing));
            }

            super.onPostExecute(s);
        }

    }

    @Override
    protected void onResume() {
        if (canvasView != null) {
            canvasView.paintColor(Constant.color_chose);
        }
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        GlobalBus.getBus().unregister(this);
        super.onDestroy();
    }

}


