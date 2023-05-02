package com.ksbm.ontu.main_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ActivityHowToTestBinding;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Utils;

public class HowToTest extends AppCompatActivity {
    ActivityHowToTestBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_how_to_test);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(HowToTest.this), HowToTest.this);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_how_to_test);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HowToTest.this, MainActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });


    }
}