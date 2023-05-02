package com.ksbm.ontu.foundation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ksbm.ontu.R;
import com.ksbm.ontu.databinding.ActivityCurrencyExchangeBinding;
import com.ksbm.ontu.foundation.model.CurrencyList;
import com.ksbm.ontu.utils.Connectivity;
import com.ksbm.ontu.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.ksbm.ontu.utils.Constant.CURRENCY_EXCHANGE_API;
import static com.ksbm.ontu.utils.Utils.loadJSONFromAsset;

public class CurrencyExchangeActivity extends AppCompatActivity {
    ActivityCurrencyExchangeBinding binding;
    ArrayList<CurrencyList> currencyList = new ArrayList<>();
    ArrayList<String> countryList = new ArrayList<>();
    Context context;
    ImageView imageView;
    String CurrencyFrom, CurrencyTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_currency_exchange);
        binding = DataBindingUtil.setContentView((Activity) this, R.layout.activity_currency_exchange);
        context = this;
        imageView = findViewById(R.id.iv_coin);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //get currency code from currency json file from assets
        loadcurrencyList();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.spnFirstCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CurrencyFrom = currencyList.get(position).getCountry_currency();
                binding.txtFirstCurrencyName.setText(currencyList.get(position).getCountry_currency());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spnSecondCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                CurrencyTo = currencyList.get(position).getCountry_currency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageView.setOnClickListener( v -> {
                    Utils.screenShot(binding.fullScreen, getWindow().getContext());
        });



        binding.btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the input is empty
                String numberToConvert = binding.etFirstCurrency.getText().toString();

                if (numberToConvert.isEmpty() || numberToConvert == "0") {
                    Snackbar.make(binding.mainLayout, "Input a value in the first text field, result will be shown in the second text field", Snackbar.LENGTH_LONG)
                            .setTextColor(ContextCompat.getColor(context, R.color.white))
                            .show();
                }
                //check if internet is available
                else if (!Connectivity.isConnected(context)) {
                    Snackbar.make(binding.mainLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG)
                            .setTextColor(ContextCompat.getColor(context, R.color.white))
                            .show();
                }

                //carry on and convert the value
                else {
                    if (CurrencyFrom != null && !CurrencyFrom.equalsIgnoreCase("") && CurrencyTo != null &&
                            !CurrencyTo.equalsIgnoreCase("")) {

//                        Thread thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try  {
                                    //Your code goes here
                                    doConversion(numberToConvert);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        thread.start();


                       //  new UpdateTask().execute();
                    } else {
                        Toast.makeText(context, "Please select currency", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void doConversion(String amount) {
//        {
//            "result": "success",
//                "documentation": "https://www.exchangerate-api.com/docs",
//                "terms_of_use": "https://www.exchangerate-api.com/terms",
//                "time_last_update_unix": 1585267200,
//                "time_last_update_utc": "Fri, 27 Mar 2020 00:00:00 +0000",
//                "time_next_update_unix": 1585270800,
//                "time_next_update_utc": "Sat, 28 Mar 2020 01:00:00 +0000",
//                "base_code": "EUR",
//                "target_code": "GBP",
//                "conversion_rate": 0.8412,
//                "conversion_result": 5.8884
//        }

//        final ProgressDialog progressDialog = new ProgressDialog(CurrencyExchangeActivity.this, R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();

        try {
            // Setting URL=  //https://v6.exchangerate-api.com/v6/YOUR-API-KEY/pair/EUR/GBP/AMOUNT
            String url_str = CURRENCY_EXCHANGE_API + CurrencyFrom + "/" + CurrencyTo + "/" + amount;

            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = null;
            request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            // Accessing object
            String req_result = jsonobj.get("result").getAsString();

            if (req_result.equalsIgnoreCase("success")) {
                String conversion_result = jsonobj.get("conversion_result").getAsString();
                binding.etSecondCurrency.setText("" + conversion_result);
                binding.txtSecondCurrencyName.setText(CurrencyTo);

            }
        }catch (Exception e){

            Log.e("exception_converter", ""+ e.toString());
        }

       // progressDialog.dismiss();

    }

    private void loadcurrencyList() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            JSONArray m_jArry = obj.getJSONArray("currencies");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                //  Log.d("Details-->", jo_inside.getString("name"));
                String country_name = jo_inside.getString("name");
                String country_currency = jo_inside.getString("cc");

                currencyList.add(new CurrencyList(country_name, country_currency));
                countryList.add(country_currency + " - " + country_name);

            }

            currencyList.add(0, new CurrencyList("", ""));
            countryList.add(0, "-Select Currency-");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spnFirstCountry.setAdapter(adapter);
            binding.spnSecondCountry.setAdapter(adapter);

            binding.spnFirstCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    CurrencyFrom = currencyList.get(position).getCountry_currency();
                    binding.txtFirstCurrencyName.setText(currencyList.get(position).getCountry_currency());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            binding.spnSecondCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    CurrencyTo = currencyList.get(position).getCountry_currency();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}