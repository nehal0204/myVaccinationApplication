package ca.on.conestogac.patelk.myvaccinationapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    private int counter;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Notification code

    @Override
    public void onCreate() {

        final Timer timer = new Timer(true);
        NotificationManager notificationManager;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notification");
        Intent ii = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Tap here to register!!");
        bigText.setBigContentTitle("Register to get your vaccine done!");

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Your Title");
        builder.setContentText("Your text");
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);

        notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ builder.setChannelId("notification"); }
        {
            String channelId = "notification";
            NotificationChannel channel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel(
                        channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_HIGH);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(channel);
            }
            builder.setChannelId(channelId);
        }

        notificationManager.notify(0, builder.build());
        super.onCreate();
 }
}