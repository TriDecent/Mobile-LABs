package com.example.lab05_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

public class PowerStateChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null) return;
        var message = Objects.equals(intent.getAction(), Intent.ACTION_POWER_CONNECTED) ?
                context.getString(R.string.power_connected) :
                context.getString(R.string.power_disconnected);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}