package com.ksbm.ontu.open_market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.free_coin.login_signup.WelcomeActivity;
import com.ksbm.ontu.open_market.model.ProfessionModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class GenderActivity extends AppCompatActivity {

    RecyclerView profession_recyclerView;
    CardView male_card, female_card, transgender_card;
    ArrayList<ProfessionModel> professionModels;
    int arr[] = new int[]{R.drawable.higher_studies, R.drawable.not_working, R.drawable.school_student, R.drawable.working};
    String colors[] = new String[]{"#FF80ED", "#40E0D0", "#C0D6E4", "#ffb5c2"};
    ProfessionAdapter professionAdapter;
    ImageView male_tick, female_tick, trans_tick;
    String gender = "";
    ImageView back_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        professionModels = new ArrayList<>();
        male_card = (CardView) findViewById(R.id.male_card);
        male_tick = (ImageView) findViewById(R.id.male_tick);
        female_tick = (ImageView) findViewById(R.id.female_tick);
        trans_tick = (ImageView) findViewById(R.id.trans_tick);
        female_card = (CardView) findViewById(R.id.female_card);
        transgender_card = (CardView) findViewById(R.id.transgender_card);
        profession_recyclerView = (RecyclerView) findViewById(R.id.profession_recyclerView);
        profession_recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        professionAdapter = new ProfessionAdapter();
        male_tick.setVisibility(View.INVISIBLE);
        female_tick.setVisibility(View.INVISIBLE);
        trans_tick.setVisibility(View.INVISIBLE);
        profession_recyclerView.setAdapter(professionAdapter);
        callProfession();
        male_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("M");
            }
        });
        female_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("F");
            }
        });
        transgender_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData("T");
            }
        });
        back_bt = (ImageView) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWelcome();
            }
        });
    }

    public void goToWelcome() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void setData(String m) {
        if (m.equalsIgnoreCase("M")) {
            male_tick.setVisibility(View.VISIBLE);
            trans_tick.setVisibility(View.INVISIBLE);
            female_tick.setVisibility(View.INVISIBLE);
            gender = "Male";
        } else if (m.equalsIgnoreCase("F")) {
            male_tick.setVisibility(View.INVISIBLE);
            trans_tick.setVisibility(View.INVISIBLE);
            female_tick.setVisibility(View.VISIBLE);
            gender = "Female";
        } else if (m.equalsIgnoreCase("T")) {
            male_tick.setVisibility(View.INVISIBLE);
            trans_tick.setVisibility(View.VISIBLE);
            female_tick.setVisibility(View.INVISIBLE);
            gender = "Transgender";
        } else {
            male_tick.setVisibility(View.INVISIBLE);
            trans_tick.setVisibility(View.INVISIBLE);
            female_tick.setVisibility(View.INVISIBLE);
            gender = "";
        }
    }

    private void callProfession() {
        final ProgressDialog progressDialog = new ProgressDialog(GenderActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);
        apiInterface.GetProfession("123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject response) {
                        System.out.println("Final response " + response);
                        try {
                            if (response.get("status").toString().equalsIgnoreCase("1")) {
                                JsonArray data = response.getAsJsonArray("data");
                                professionModels = new ArrayList<>();
                                for (int i = 0; i < data.size(); i++) {
                                    JsonObject object = (JsonObject) data.get(i);
                                    professionModels.add(new ProfessionModel(object.get("proffession").getAsString(), arr[i % arr.length], colors[i % colors.length]));
                                }
                                professionAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Log.e("mr_product_error", e.toString());
                        if (e instanceof HttpException) {
                            int code = ((HttpException) e).code();
                            switch (code) {
                                case 403:
                                    break;
                                case 404:
                                    //Toast.makeText(EmailSignupActivity.this, R.string.email_already_use, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    break;
                                default:
                                    // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            if (TextUtils.isEmpty(e.getMessage())) {
                                // Toast.makeText(EmailSignupActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(EmailSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });
    }


    class ProfessionAdapter extends RecyclerView.Adapter<ProfessionAdapter.ViewHolder> {

        // RecyclerView recyclerView;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.profession_row, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.cardView.setCardBackgroundColor(Color.parseColor(professionModels.get(position).getColor()));
            holder.images.setImageResource(professionModels.get(position).getImage());
            holder.profession.setText(professionModels.get(position).getProffession().trim());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gender.equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                    } else {
                        if (professionModels.get(position).getProffession().trim().equalsIgnoreCase("School Students")) {
                            Intent intent = new Intent(getApplicationContext(), AgeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("GENDER", gender);
                            bundle.putString("PRF", professionModels.get(position).getProffession().trim());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), ProfeciencyActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("AGE", "Above 13");
                            bundle.putString("TYPE", "T");
                            bundle.putString("GENDER", gender);
                            bundle.putString("PRF", professionModels.get(position).getProffession().trim());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return professionModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView images;
            TextView profession;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.cardView);
                profession = (TextView) itemView.findViewById(R.id.profession);
                images = (ImageView) itemView.findViewById(R.id.images);
            }
        }
    }

    @Override
    public void onBackPressed() {
        goToWelcome();
    }
}