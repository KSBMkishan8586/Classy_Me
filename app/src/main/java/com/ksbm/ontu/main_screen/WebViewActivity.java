package com.ksbm.ontu.main_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityWebViewBinding binding;
    private String Drive_Link,ComeFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_terms_page);
        binding = DataBindingUtil.setContentView(WebViewActivity.this, R.layout.activity_web_view);

        if (getIntent()!= null){
            Drive_Link= getIntent().getStringExtra("Drive_Link");
            try {
                ComeFrom= getIntent().getStringExtra("ComeFrom");
                if(ComeFrom.equalsIgnoreCase("sit"))
                {
                    binding.tvHeader.setText(getIntent().getStringExtra("name")+"");
                }
            }
            catch (Exception e)
            {}

        }
        initView();
        startWebView(Drive_Link);

    }


    private void initView() {
        binding.ivBack.setOnClickListener(this);
        //binding.tvAddAddress.setOnClickListener(this);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {
        //Create new webview Client to show progress dialog
        //When opening a url or click on link
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
//                    progressDialog = new ProgressDialog(WebViewActivity.this);
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
                    Toast.makeText(WebViewActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                Toast.makeText(WebViewActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url) {
//                try{
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
            }

        });

        // Javascript inabled on webview
        binding.webview.getSettings().setJavaScriptEnabled(true);

        //Load url in webview
        binding.webview.loadUrl(url);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;


        }
    }

    @Override
    public void onBackPressed() {

        finish();
        //super.onBackPressed();
    }

}