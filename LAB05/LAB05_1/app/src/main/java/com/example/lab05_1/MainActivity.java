package com.example.lab05_1;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;
import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("LAB05_1");
        }

        tvContent = findViewById(R.id.tv_content);

        initializeBroadcastReceiver();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (broadcastReceiver == null)
            initializeBroadcastReceiver();

        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

    private void initializeBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };

        intentFilter = new IntentFilter(SMS_RECEIVED_ACTION);
    }

    private void processReceive(Context context, Intent intent) {
        Toast.makeText(context,
                context.getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();

        var sms = new StringBuilder();

        for (SmsMessage smsMessage : getMessagesFromIntent(intent)) {
            var msgBody = smsMessage.getMessageBody();
            var msgAddress = smsMessage.getOriginatingAddress();
            sms.append("SMS from ").append(msgAddress).append(" : ").append(msgBody).append("\n");
        }

        tvContent.setText(sms.toString());
    }
}