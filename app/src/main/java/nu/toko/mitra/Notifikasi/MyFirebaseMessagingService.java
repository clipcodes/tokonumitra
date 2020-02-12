package nu.toko.mitra.Notifikasi;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import nu.toko.mitra.MainActivity;
import nu.toko.mitra.Page.PageOrders;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.Others;
import nu.toko.mitra.Utils.Parsingan;
import nu.toko.mitra.Utils.Staticvar;
import nu.toko.mitra.Utils.UserPrefs;

import static nu.toko.mitra.Utils.Staticvar.ID_TRANSAKSI;
import static nu.toko.mitra.Utils.Staticvar.NOTIF_DATE;
import static nu.toko.mitra.Utils.Staticvar.NOTIF_TYPE;
import static nu.toko.mitra.Utils.Staticvar.STATUS_TRANSAKSI;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String title, message;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!isAppIsInBackground(getApplicationContext())) {
            Intent pushNotification = new Intent(Staticvar.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            playNotificationSound();
            Log.i(TAG, "MARKOJOS1");
        }else{
            Log.i(TAG, "MARKOJOS2");
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            Log.i(TAG, "handleDataMessage: data parent "+data);
            JSONObject dataadd = data.getJSONObject("data");
            Log.i(TAG, "handleDataMessage: data additional "+dataadd);
            String date = data.getString(NOTIF_DATE);

            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

            switch (data.getString(NOTIF_TYPE)){
                case "order":
                    int status = dataadd.getInt(STATUS_TRANSAKSI);
                    int idtrans = dataadd.getInt(ID_TRANSAKSI);
                    Log.i(TAG, "handleDataMessage: status "+status);
                    Log.i(TAG, "handleDataMessage: idtrans "+idtrans);

                    resultIntent = new Intent(getApplicationContext(), PageOrders.class);
                    resultIntent.putExtra(ID_TRANSAKSI, dataadd.getInt(ID_TRANSAKSI));
                    resultIntent.putExtra(STATUS_TRANSAKSI, dataadd.getInt(STATUS_TRANSAKSI));
                    if (status == 2){
                        title = "Info Penjualan";
                        message = "Halo "+ UserPrefs.getNama(getApplicationContext())+", pembayaran telah dikonfirmasi. Silahkan kemas barang yang dipesan dan konfirmasi pengiriman.";
                    }
                    if (status == 4){
                        title = "Info Penjualan";
                        message = "Halo "+UserPrefs.getNama(getApplicationContext())+", barang anda telah diterima oleh pembeli.";
                    }
                    break;
            }

            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            getApplicationContext(),
                            0,
                            resultIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                XNotifications x = new XNotifications(getApplicationContext());
                Notification.Builder nb = x.getAndroidChannelNotification(title, message, resultPendingIntent);
                x.getManager().notify(Staticvar.NOTIFICATION_ID, nb.build());
            } else {
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), "AX")
                        .setContentTitle(title)
                        .setContentIntent(resultPendingIntent)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ikon_notifs)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .build();

                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(Staticvar.NOTIFICATION_ID, notification);
            }

            playNotificationSound();

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getApplicationContext().getPackageName() + "/raw/notip");
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }

        return isInBackground;
    }

}