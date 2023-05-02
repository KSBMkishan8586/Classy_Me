package com.ksbm.ontu.open_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ksbm.ontu.R;

public class ProfeciencyActivity extends AppCompatActivity {

    LinearLayout easy, medium, advance;
    ImageView back_bt;
    String level = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profeciency);
        level = "";
        easy = (LinearLayout) findViewById(R.id.easy);
        medium = (LinearLayout) findViewById(R.id.medium);
        advance = (LinearLayout) findViewById(R.id.advance);
        back_bt = (ImageView) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAge();
            }
        });
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = "Easy";
                gotomain();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = "Medium";
                gotomain();
            }
        });
        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                level = "Advance";
                gotomain();
            }
        });

    }

    public void gotomain() {
        Intent intent = new Intent(getApplicationContext(), FillDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("AGE", getIntent().getExtras().getString("AGE"));
        bundle.putString("GENDER", getIntent().getExtras().getString("GENDER"));
        bundle.putString("PRF", getIntent().getExtras().getString("PRF"));
        bundle.putString("TYPE", getIntent().getExtras().getString("TYPE"));
        bundle.putString("LEVEL", level);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void goToAge() {
        if (getIntent().getExtras().getString("TYPE").equalsIgnoreCase("S")) {
            Intent intent = new Intent(getApplicationContext(), AgeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("GENDER", getIntent().getExtras().getString("GENDER"));
            bundle.putString("PRF", getIntent().getExtras().getString("PRF"));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), GenderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("GENDER", getIntent().getExtras().getString("GENDER"));
            bundle.putString("PRF", getIntent().getExtras().getString("PRF"));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        goToAge();
    }
}