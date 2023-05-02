package com.ksbm.ontu;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ksbm.ontu.free_coin.login_signup.WelcomeActivity;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Splash_Activity extends AppCompatActivity {
    ImageView splash_logo_image;

    SharedPreferences sharedPreferences;
    private SessionManager session;
    String android_id;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String[] mPermission = {
            CAMERA,
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE,

    };
    String[] newPermission = {
            READ_MEDIA_AUDIO,
            READ_MEDIA_IMAGES,
            READ_MEDIA_VIDEO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new SessionManager(this);
         splash_logo_image = findViewById(R.id.splash_logo_image);///***////
        displayFirebaseRegId();
       // printHashKey(); //for fb login.,,
        Device_id();

        Glide.with(Splash_Activity.this)
                .load(R.drawable.splash_gif_ontu)
                // .error(R.drawable.ankit)
                //.placeholder(R.drawable.ankit)
                .into(splash_logo_image);


        try {

//            if (checkPermission1()) {
//                // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
//
//              OpenDashboard();
//            }
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

               if (checkPermission2()){
                   OpenDashboard();
               }else {
                   requestPermission2();
               }
            }else {

                if (checkPermission1()){
                    OpenDashboard();
                }else {
                    requestPermission1();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Log.e("exception", "" + e);//
        }


    }



    private void OpenDashboard() {
    Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLoggedIn()) {
                    Intent mainIntent = new Intent((Context) Splash_Activity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent((Context) Splash_Activity.this, WelcomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, 4000);
    }

    private void requestPermission1() {


//        if (SDK_INT >= Build.VERSION_CODES.R) {///////ll
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
//                startActivityForResult(intent, 2296);
//            } catch (Exception e) {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, 2296);
//            }
//        } else {
            //below android 11
            ActivityCompat.requestPermissions(this, mPermission, REQUEST_CODE_PERMISSION);
//        }
    }

    private void requestPermission2(){
        ActivityCompat.requestPermissions(this, newPermission, REQUEST_CODE_PERMISSION);
    }

    private boolean checkPermission1() {
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            return Environment.isExternalStorageManager();
//        } else {
           // int result4 = ContextCompat.checkSelfPermission(Splash_Activity.this, TextToSpeech);
            int result = ContextCompat.checkSelfPermission(Splash_Activity.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(Splash_Activity.this, WRITE_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(Splash_Activity.this, CAMERA);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
       // }
    }
    private boolean checkPermission2() {
        int permission = ContextCompat.checkSelfPermission(Splash_Activity.this, READ_MEDIA_VIDEO);
        int permission1 = ContextCompat.checkSelfPermission(Splash_Activity.this, READ_MEDIA_AUDIO);
        int permission2 = ContextCompat.checkSelfPermission(Splash_Activity.this, READ_MEDIA_IMAGES);
        int permission3 = ContextCompat.checkSelfPermission(Splash_Activity.this, CAMERA);
        return permission == PackageManager.PERMISSION_GRANTED && permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("HardwareIds")
    private void Device_id() {
        try {
            android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            session.saveDeviceId(android_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log.e("device_id", android_id);
    }

    private void displayFirebaseRegId() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                // send it to server
                session.saveToken(token);
                Log.e("refresh_tokentoken", token);
            }
        });
        Log.e("save_token", session.getTokenId());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //  Log.e("Req Code", "" + requestCode);
        System.out.println(grantResults[0] == PackageManager.PERMISSION_GRANTED);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED){

                if (android.os.Build.MANUFACTURER.equals("samsung"))
                {
                    dialogGtts();
                }else {
                    OpenDashboard();
                }
            }


            } else {
                Toast.makeText((Context) Splash_Activity.this, "Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                    OpenDashboard();
                } else {
                    Toast.makeText((Context) this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void dialogGtts() {

            AlertDialog.Builder builder = new AlertDialog.Builder(Splash_Activity.this);


            builder.setMessage("In Preferred engine choose\nspeech services by google");

            builder.setTitle("Change Preferred engine");

            builder.setCancelable(false);

            builder.setPositiveButton("Allow", (DialogInterface.OnClickListener) (dialog, which) -> {
                Intent intent = new Intent();
                intent.setAction("com.android.settings.TTS_SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                OpenDashboard();
            });

            builder.setNegativeButton("Already Allowed", (DialogInterface.OnClickListener) (dialog, which) -> {
                OpenDashboard();
                dialog.dismiss();
            });

            AlertDialog alertDialog = builder.create();

        final SharedPreferences settings = getSharedPreferences("pref_name", 0);
        boolean installed = settings.getBoolean("installed", false);
        if (!installed){
            alertDialog.show();
        }else {
            alertDialog.hide();
        }



    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = getPackageManager().getPackageInfo("com.ksbm.ontu",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        final SharedPreferences settings = getSharedPreferences("pref_name", 0);
        boolean installed = settings.getBoolean("installed", false);

        if (!installed) {

        } else {
            dialogGtts();
        }
    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//        Intent intent = new Intent();
//        intent.setAction("com.android.settings.TTS_SETTINGS");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//
//    }
}