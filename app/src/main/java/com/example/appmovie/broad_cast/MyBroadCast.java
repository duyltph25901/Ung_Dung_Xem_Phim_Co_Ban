package com.example.appmovie.broad_cast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOk = ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction());
        if(isOk){
            if( isNetWorkAvailable(context) ){
                Toast.makeText(context, "Internet connected!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Internet disconnected!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager == null) return false;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network network = manager.getActiveNetwork();

            if(network==null) return false;

            NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);

            return capabilities!=null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();

            return info!=null && info.isConnected();
        }
    }
}
