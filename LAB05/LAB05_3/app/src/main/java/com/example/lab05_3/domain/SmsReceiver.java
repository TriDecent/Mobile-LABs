package com.example.lab05_3.domain;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;
import static android.provider.Telephony.Sms.Intents.getMessagesFromIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.example.lab05_3.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "SMS_FORWARD_BROADCAST_RECEIVER";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "SMS_MESSAGE_ADDRESS_KEY";

    private static final String QUERY_STRING = "are you ok";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            return;
        }

        var messages = getMessagesFromIntent(intent);

        var addresses = extractAddresses(messages);
        if (addresses.isEmpty()) return;

        if (MainActivity.isRunning) {
            forwardAddresses(context, addresses);
        } else {
            startMainActivity(context, addresses);
        }
    }

    private List<String> extractAddresses(SmsMessage[] messages) {
        return Arrays.stream(messages)
                .filter(message -> message.getMessageBody().toLowerCase().contains(QUERY_STRING))
                .map(SmsMessage::getOriginatingAddress)
                .collect(Collectors.toList());
    }

    private void forwardAddresses(Context context, List<String> addresses) {
        Intent forwardIntent = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
        forwardIntent.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, new ArrayList<>(addresses));
        context.sendBroadcast(forwardIntent);
    }

    private void startMainActivity(Context context, List<String> addresses) {
        Intent startIntent = new Intent(context, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startIntent.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, new ArrayList<>(addresses));
        context.startActivity(startIntent);
    }
}