package com.ksbm.ontu.main_screen;

import static com.ksbm.ontu.utils.Constant.upgradePackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.main_screen.model.ResponseUpgradeCourse;
import com.ksbm.ontu.personality_dev.adapter.PersonalityCategory_List_Adapter;
import com.ksbm.ontu.personality_dev.model.CategoryModel;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.SweetAlt;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    float TotalAmount;
    SessionManager sessionManager;
    LinearLayout succes;
    MeraSharedPreferance meraSharedPreferance;
    TextView orderid;
    Button manage,continue_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sessionManager = new SessionManager(this);
        manage = findViewById(R.id.manage);
        continue_buy = findViewById(R.id.continue_buy);
        orderid = findViewById(R.id.orderid);
        meraSharedPreferance = MeraSharedPreferance.getInstance(this);
        if (getIntent().getExtras().getString("price").equalsIgnoreCase(upgradePackage)){
            TotalAmount= Float.parseFloat(meraSharedPreferance.plan_upgrade_amountGet());
        }else{
            TotalAmount= Float.parseFloat(getIntent().getExtras().getString("price"));
        }
        succes=findViewById(R.id.success);
        startPayment();

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        continue_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void startPayment() {
        final Activity activity = this;
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", getString(R.string.app_name));
            options.put("description", "Total payable amount");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Double.parseDouble(TotalAmount+"")*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email",sessionManager.getUser().getEmail());
            preFill.put("contact", sessionManager.getUser().getMobile());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        if (getIntent().getExtras().getString("price").equalsIgnoreCase(upgradePackage)){
            updateStatus(s);
        }else{
            Toast.makeText(this, "payment success for somthing else", Toast.LENGTH_SHORT).show();
//            TotalAmount= Float.parseFloat(getIntent().getExtras().getString("price"));
        }

    }

    private void updateStatus(String transactionId) {
        final ProgressDialog progressDialog = new ProgressDialog(PaymentActivity.this, R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.SetUpgradeCourse(TotalAmount+"",sessionManager.getUser().getUserid(), transactionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseUpgradeCourse>() {
                    @Override
                    public void onNext(ResponseUpgradeCourse response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMsg());
                            //Toast.makeText(EmailSignupActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.isStatus()) {
                                meraSharedPreferance.isPackageUpgradedSave(true);
                                succes.setVisibility(View.VISIBLE);
                                orderid.setText(transactionId);
                            } else {
                                SweetAlt.showErrorMessage(PaymentActivity.this, "Sorry", response.getMsg());

                            }
                            //  binding.swipeToRefresh.setRefreshing(false);

                        } catch (Exception e) {
                            progressDialog.dismiss();
                        }

                        //  binding.swipeToRefresh.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //Handle error
                        progressDialog.dismiss();
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

    @Override
    public void onPaymentError(int i, String s) {

        finish();
    }
}