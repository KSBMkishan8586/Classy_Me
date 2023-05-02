package com.ksbm.ontu.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ksbm.ontu.BR;
import com.ksbm.ontu.R;
import com.ksbm.ontu.custom_class.PhotoViewActivity;
import com.ksbm.ontu.databinding.CertificateListBinding;
import com.ksbm.ontu.profile.model.MyCertificate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import static com.ksbm.ontu.utils.Constant.certificate_folder;

public class Certificate_Adapter extends RecyclerView.Adapter<Certificate_Adapter.ViewHolder> {
    private List<MyCertificate.MyCertificateResponse> dataModelList;
    Context context;
    String FileName;

    public Certificate_Adapter(List<MyCertificate.MyCertificateResponse> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CertificateListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.certificate_list, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyCertificate.MyCertificateResponse dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setModel(dataModel);
        // holder.itemRowBinding.setItemClickListener(this);
        Glide.with(context)
                .load(dataModel.getUrl())
                .error(R.drawable.certificate_image)
                .placeholder(R.drawable.certificate_image)
                .into(holder.itemRowBinding.ivCertificate);

        holder.itemRowBinding.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataModel.getUrl()!=null && !dataModel.getUrl().isEmpty()){
                    FileName= "Classyme"+System.currentTimeMillis();
                     //FileName =  dataModel.getFilename();
                    Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                    new DownloadImage().execute(dataModel.getUrl());
                }
            }
        });

        holder.itemRowBinding.ivCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, PhotoViewActivity.class);
                // intent.putExtra("imgUrl", dataModel.getImage());
                intent.putExtra("imgUrl", dataModel.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CertificateListBinding itemRowBinding;

        public ViewHolder(CertificateListBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Object obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();
        }
    }


    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";
        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d(TAG, "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            saveImage(result, FileName);
        }
    }

    private void saveImage(Bitmap data, String fileName) {

        File file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            file =   new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + certificate_folder);
        } else {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + certificate_folder);
        }

        if (!file.exists()) {
            file.mkdirs();
        }

       // Log.e("file_path",  ""+ file);

        File saveImage = new File(file,fileName);
        try {
            OutputStream outputStream = new FileOutputStream(saveImage);
            data.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
