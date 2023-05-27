package ca.on.conestogac.patelk.myvaccinationapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      final ConnectivityManager connManager = (ConnectivityManager)
              context.getSystemService(Context.CONNECTIVITY_SERVICE);
      final NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

      if(networkInfo != null && networkInfo.isConnected()) {
          context.startService(new Intent(context, NotificationService.class));
      }
    }
}