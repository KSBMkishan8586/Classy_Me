package com.ksbm.ontu.firebase;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ksbm.ontu.R;
import com.ksbm.ontu.main_screen.MainActivity;
import com.ksbm.ontu.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    String title_msg = "";
    RemoteMessage remoteMessage;
    String user_id = "";
    NotificationUtils notificationUtils;


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        SessionManager session = new SessionManager(this);
        session.saveToken(s);
        Log.e("token_fcm_service", s);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily
        if (remoteMessage.getData().size() > 0) {
            //handle the data message here
            Log.e(TAG, "Data_Payload: " + remoteMessage.getData().toString());

            try {

                // JSONObject json = new JSONObject(remoteMessage.getData().toString());
                //handleDataMessage(json);
                handleDataMessage(remoteMessage);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("Notification Body: ", "" + remoteMessage.getNotification().getBody());

            //getting the title and the body
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.e("title_body: ", "" + title + " " + body);
            //then here we can use the title and body to build a notification
            assert body != null;
            handleNotification(title, body.replaceAll("\\<.*?\\>", ""));
        }


    }


    private void handleNotification(String title, String body) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //   Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NOTIFICATION", "NOTIFICATION");
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
            String format = s.format(new Date());

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Log.e("Q_foreground_noti", "fore_notiii");

                // scheduleNotification(getNotification( title , body ));

                // // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", body);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                Log.d("BroadcastReceiver::", "BroadcastReceiver");
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.showNotificationMessage(title, body, format, intent);
                notificationUtils.playNotificationSound();

            } else {
                Log.e("Q_background_noti", "pppp");
                sendNoti(title, body);
                // If the app is in background, firebase itself handles the notification
            }
        } else {
            Log.e("below_Q", "notiiii");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NOTIFICATION", "NOTIFICATION");
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder b = new NotificationCompat.Builder(this);
            String channelId = "Default";

            b.setAutoCancel(false);
            b.setDefaults(Notification.DEFAULT_ALL);
            b.setWhen(System.currentTimeMillis());
            b.setSmallIcon(R.drawable.ontu_logo);
            // b.setTicker("Hearty365");
            b.setContentTitle(title);
            b.setContentText(body);
            b.setPriority(NotificationCompat.PRIORITY_HIGH);
            b.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
            b.setContentIntent(contentIntent);
            // b.setContentInfo("Info");


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //  NotificationChannel  channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_HIGH);
                // notificationManager.createNotificationChannel(channel);

                createChannel(notificationManager);
            }

            notificationManager.notify(1, b.build());

        }

    }

    private void scheduleNotification(Notification notification) {
        Intent notificationIntent = new Intent(this, MyBroadcastReceiver.class);
        notificationIntent.putExtra(MyBroadcastReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyBroadcastReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, 1, pendingIntent);
    }

    private Notification getNotification(String title, String body) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, "default_channel");
        b.setAutoCancel(false);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setWhen(System.currentTimeMillis());
        b.setSmallIcon(R.drawable.ontu_logo);
        // b.setTicker("Hearty365");
        b.setContentTitle(title);
        b.setPriority(NotificationCompat.PRIORITY_HIGH);//for headsup
        b.setContentText(body);
        b.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
        return b.build();
    }


    private void createChannel(NotificationManager notificationManager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (defaultSoundUri != null) {
                AudioAttributes att = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                channel.setSound(defaultSoundUri, att);
            }

            NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(channel);
        }
    }

    private void sendNoti(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NOTIFICATION", "NOTIFICATION");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        String channelId = "Default";

        b.setAutoCancel(false);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setWhen(System.currentTimeMillis());
        b.setSmallIcon(R.drawable.ontu_logo);
        // b.setTicker("Hearty365");
        b.setContentTitle(title);
        b.setPriority(NotificationCompat.PRIORITY_HIGH);
        b.setContentText(body);
        b.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
        b.setContentIntent(contentIntent);
        // b.setContentInfo("Info");

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_HIGH);

            if (defaultSoundUri != null) {
                AudioAttributes att = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                channel.setSound(defaultSoundUri, att);
            }

            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, b.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void handleDataMessage(RemoteMessage remoteMessage) {
        // Log.e(TAG, "push_json: " + json.toString());
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON OBJECT", object.toString());
            String moredata = object.getString("moredata");
            String message = object.getString("message");
            System.out.println("check_msg " + message);

            try {

                JSONObject obj = new JSONObject(message);
                Log.e("My_App_json", obj.toString());
                String title = obj.getString("title");
                String body = obj.getString("body");


                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                    // app is in foreground, broadcast the push message
                    Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                    pushNotification.putExtra("message", body);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                    Log.d("BroadcastReceiver::", "BroadcastReceiver");
                    // play notification sound
                    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                    notificationUtils.showNotificationMessage(title, body, format, resultIntent);
                    notificationUtils.playNotificationSound();

                } else {
                    // app is in background, show the notification in notification tray

                    resultIntent.putExtra("message", body);

                    showNotificationMessage(getApplicationContext(), title, body, format, resultIntent);

                    // check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
                    //showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
                }


            } catch (Throwable t) {
                Log.e("My_AppEx ", "Could not parse malformed JSON: \"" + message + "\"");
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {

        Log.e("rr22666", "pppp22111");
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}
