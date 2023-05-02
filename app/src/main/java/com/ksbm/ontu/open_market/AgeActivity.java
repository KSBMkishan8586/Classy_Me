package com.ksbm.ontu.open_market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ksbm.ontu.R;

public class AgeActivity extends AppCompatActivity {

    CardView above_13, below_13;
    ImageView back_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        above_13 = (CardView) findViewById(R.id.above_13);
        below_13 = (CardView) findViewById(R.id.below_13);
        back_bt = (ImageView) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGender();
            }
        });
        above_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfeciencyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "S");
                bundle.putString("AGE", "Above 13");
                bundle.putString("GENDER", getIntent().getExtras().getString("GENDER"));
                bundle.putString("PRF", getIntent().getExtras().getString("PRF"));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        below_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfeciencyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "S");
                bundle.putString("AGE", "Below 13");
                bundle.putString("GENDER", getIntent().getExtras().getString("GENDER"));
                bundle.putString("PRF", getIntent().getExtras().getString("PRF"));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goToGender() {
        Intent intent = new Intent(getApplicationContext(), GenderActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToGender();
    }
}