package com.ksbm.ontu.main_screen.fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ksbm.ontu.utils.Constant.SOUND_EFFECT;
import static com.ksbm.ontu.utils.Constant.WHATSAPP_NO;
import static com.ksbm.ontu.utils.Constant.upgradePackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ksbm.ontu.R;
import com.ksbm.ontu.api_client.Api_Call;
import com.ksbm.ontu.api_client.Base_Url;
import com.ksbm.ontu.api_client.RxApiClient;
import com.ksbm.ontu.custom_class.Language_Settings;
import com.ksbm.ontu.databinding.FragmentSettingsBinding;
import com.ksbm.ontu.free_coin.login_signup.PermissionActivity;
import com.ksbm.ontu.free_coin.login_signup.login_signup_model.LoginModel;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.main_screen.PaymentActivity;
import com.ksbm.ontu.service.BackgroundMusicService;
import com.ksbm.ontu.session.SessionManager;
import com.ksbm.ontu.utils.Constant;
import com.ksbm.ontu.utils.MeraSharedPreferance;
import com.ksbm.ontu.utils.Utils;

import java.net.URLEncoder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Setting_Fragment extends Fragment {
    FragmentSettingsBinding binding;
    SessionManager sessionManager;
    MeraSharedPreferance meraSharedPreferance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_home, null);
        Utils.setLanguageForApp(SessionManager.getCurrentLanguage(getActivity()), getActivity());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        View view = binding.getRoot();//using data binding
        sessionManager = new SessionManager(getActivity());
        meraSharedPreferance = MeraSharedPreferance.getInstance(getActivity());

        try {
             ((MainActivity) getActivity()).removeBottomNavigationLabels( 1);
            ((MainActivity) getActivity()).CheckBottom(1);
        } catch (Exception e) {
        }

        InitViews();


        if (meraSharedPreferance.isOpenMarketGet()){
            binding.updatePrflCv.setVisibility(View.GONE);
        }else{
            binding.updatePrflCv.setVisibility(View.VISIBLE);
        }
        binding.relLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).LogoutUser();
            }
        });

        binding.relUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PermissionActivity.class);
               // intent.addCategory(Intent.CATEGORY_HOME);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
              //  finish();
            }
        });

        binding.relSupportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (WHATSAPP_NO!=null && !WHATSAPP_NO.equalsIgnoreCase("")){
                     openWhatsApp();
                }
            }
        });

        binding.soundeffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent();
                intent.setAction("com.android.settings.TTS_SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.relLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant.EnableLanguage){
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater1.inflate(R.layout.language_dialog, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
                    RecyclerView languageView = dialogView.findViewById(R.id.recyclerView);
                    languageView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    Language_Settings.GetLanguage(languageView, getActivity(), alertDialog);
                }

            }
        });

        binding.switchSoundEffect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked){
                   // backgroundMusicService.stopMusic();
                    Intent musicintent = new Intent(getActivity(), BackgroundMusicService.class);
                    getActivity().stopService(musicintent);

                   SessionManager.setMypref(SOUND_EFFECT, String.valueOf(false));
                }else {
                    //play bg music service
                    Intent musicintent = new Intent(getActivity(), BackgroundMusicService.class);
                    // musicintent.putExtra(EXTRA_SONGINDEX, 1);
                    getActivity().startService(musicintent);

                    SessionManager.setMypref(SOUND_EFFECT, String.valueOf(true));
                }
            }
        });

//        binding.switchSubtitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked){
//                    applySettings("yes", sessionManager.getUser().getChat_message(), sessionManager.getUser().getShow_online_status_to_friend()
//                    , sessionManager.getUser().getNotification_while_learning());
//                }else {
//                    applySettings("no", sessionManager.getUser().getChat_message(), sessionManager.getUser().getShow_online_status_to_friend()
//                            , sessionManager.getUser().getNotification_while_learning());
//                }
//            }
//        });
//        binding.switchChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked){
//                    applySettings(sessionManager.getUser().getAllow_subtitle(), "yes", sessionManager.getUser().getShow_online_status_to_friend()
//                            , sessionManager.getUser().getNotification_while_learning());
//                }else {
//                    applySettings(sessionManager.getUser().getAllow_subtitle(), "no", sessionManager.getUser().getShow_online_status_to_friend()
//                            , sessionManager.getUser().getNotification_while_learning());
//                }
//            }
//        });
//        binding.switchShowOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked){
//                    applySettings(sessionManager.getUser().getAllow_subtitle(), sessionManager.getUser().getChat_message(), "yes"
//                            , sessionManager.getUser().getNotification_while_learning());
//                }else {
//                    applySettings(sessionManager.getUser().getAllow_subtitle(), sessionManager.getUser().getChat_message(), "no"
//                            , sessionManager.getUser().getNotification_while_learning());
//                }
//            }
//        });
        binding.switchNotiAllow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    applySettings(sessionManager.getUser().getAllow_subtitle(), sessionManager.getUser().getChat_message(), sessionManager.getUser().getShow_online_status_to_friend()
                            , "yes");
                }else {
                    applySettings(sessionManager.getUser().getAllow_subtitle(), sessionManager.getUser().getChat_message(), sessionManager.getUser().getShow_online_status_to_friend()
                            , "no");
                }
            }
        });
        if (meraSharedPreferance.isOpenMarketGet()&& !meraSharedPreferance.isPackageUpgraded()){
            binding.upgradeTab.setVisibility(View.VISIBLE);
            binding.upgradeTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), PaymentActivity.class).putExtra("price",upgradePackage));
                }
            });


        }else{
            binding.upgradeTab.setVisibility(View.GONE);
        }
        binding.rlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackDialogBox();
            }
        });

        binding.relPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Constant.PRIVACY_URL;
                if (url.startsWith("https://") || url.startsWith("http://")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Invalid Url", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void feedbackDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.feedback_lyt, null);
        builder.setView(customLayout);

        ImageView cut_iv = customLayout.findViewById(R.id.cut_iv);
        EditText title = customLayout.findViewById(R.id.title);
        EditText message = customLayout.findViewById(R.id.message);
        Button submit_btn = customLayout.findViewById(R.id.submit_btn);

        AlertDialog dialog = builder.create();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Feedback Sent Successfully", Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });
        cut_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    @SuppressLint("CheckResult")
    private void applySettings(String allow_subtitle, String chat_message, String show_online_status_to_friend, String noti_learning) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = RxApiClient.getClient(Base_Url.BaseUrl).create(Api_Call.class);

        apiInterface.UpdateSettings(sessionManager.getUser().getUserid(),allow_subtitle , chat_message, show_online_status_to_friend,noti_learning, "no")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginModel>() {
                    @Override
                    public void onNext(LoginModel response) {
                        //Handle logic
                        try {
                            progressDialog.dismiss();
                            Log.e("result_my_test", "" + response.getMessage());
                            Toast.makeText(getActivity(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            if (response.getStatus() == 1) {
                                sessionManager.createSession(response.getResponse());

                            } else {
                              //  SweetAlt.showErrorMessage(MyChildActivity.this, "Sorry", response.getMessage());

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

                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                    }
                });

    }

    private void openWhatsApp() {
        String toNumber;
        if (WHATSAPP_NO.length()==10){
            toNumber = "91"+ WHATSAPP_NO;
        }else {
            toNumber =  WHATSAPP_NO;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        String message = "Hi, \n I want to some support about the app";
        try {
            String url = "https://api.whatsapp.com/send?phone=" + toNumber + "&text=" + URLEncoder.encode(message, "UTF-8");
            /// i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            //if (i.resolveActivity(packageManager) != null) {
            startActivity(i);
            // }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "it may be you dont have whats app", Toast.LENGTH_LONG).show();
        }

    }

    private void InitViews() {
        if (!Constant.EnableLanguage){
            binding.relLangView.setVisibility(View.GONE);
        }
//        if (!Constant.EnableChat){
//            binding.relChatView.setVisibility(View.GONE);
//        } if (!Constant.EnableOnlineStatusFriend){
//            binding.relOnlineViewFriend.setVisibility(View.GONE);
//        }

        if (SessionManager.getMyPref(SOUND_EFFECT).equalsIgnoreCase(String.valueOf(true))){
            binding.switchSoundEffect.setChecked(true);
        }else {
            binding.switchSoundEffect.setChecked(false);
        }
        //set setting toogle button
//        if (sessionManager.getUser().getAllow_subtitle().equalsIgnoreCase("yes")){
//            binding.switchSubtitle.setChecked(true);
//        }else {
//            binding.switchSubtitle.setChecked(false);
//        }
//        if (sessionManager.getUser().getChat_message().equalsIgnoreCase("yes")){
//            binding.switchChat.setChecked(true);
//        }else {
//            binding.switchChat.setChecked(false);
//        }
//        if (sessionManager.getUser().getShow_online_status_to_friend().equalsIgnoreCase("yes")){
//            binding.switchShowOnline.setChecked(true);
//        }else {
//            binding.switchShowOnline.setChecked(false);
//        }
        if (sessionManager.getUser().getNotification_while_learning().equalsIgnoreCase("yes")){
            binding.switchNotiAllow.setChecked(true);
        }else {
            binding.switchNotiAllow.setChecked(false);
        }
    }

}
