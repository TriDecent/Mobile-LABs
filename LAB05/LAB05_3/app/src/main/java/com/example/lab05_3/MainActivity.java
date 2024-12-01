package com.example.lab05_3;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.lab05_3.data.repository.SmsContactRepository;
import com.example.lab05_3.domain.ChangedContactListener;
import com.example.lab05_3.domain.SmsReceiver;
import com.example.lab05_3.domain.SmsResponder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChangedContactListener {
    public static boolean isRunning;
    private final String AUTO_RESPONSE = "auto_response";
    private SmsContactRepository contactRepository;
    private SmsResponder smsResponder;
    private SwitchCompat swAutoResponse;
    private Button btnSafe, btnMayday;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    private SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        initializeFields();
        initializeBroadcastReceiver();
        handleOnClickListener();
        handleIncomingIntent();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (broadcastReceiver == null) {
            initializeBroadcastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
    }

    private void initializeFields() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        contactRepository = new SmsContactRepository();
        contactRepository.setOnContactsChangeListener(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactRepository.getContacts());
        lvMessages.setAdapter(adapter);

        smsResponder = new SmsResponder(this, contactRepository);

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        toggleButtons(autoResponse);
    }

    private void handleOnClickListener() {
        btnSafe.setOnClickListener(_ ->
                smsResponder.respond(true, contactRepository.getContacts()));

        btnMayday.setOnClickListener(_ ->
                smsResponder.respond(false, contactRepository.getContacts()));

        swAutoResponse.setOnCheckedChangeListener((_, isChecked) -> {
            toggleButtons(isChecked);
            editor.putBoolean(AUTO_RESPONSE, isChecked);
            editor.apply();
        });
    }

    private void toggleButtons(boolean isChecked) {
        int visibility = isChecked ? View.GONE : View.VISIBLE;
        btnSafe.setVisibility(visibility);
        btnMayday.setVisibility(visibility);
    }

    private void initializeBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
                if (addresses != null) {
                    for (String address : addresses) {
                        contactRepository.addContact(address);
                        if (swAutoResponse.isChecked()) {
                            smsResponder.respond(true, Collections.singletonList(address));
                        }
                    }
                }
            }
        };

        SmsReceiver smsReceiver = new SmsReceiver();
        registerReceiver(smsReceiver, new IntentFilter(SMS_RECEIVED_ACTION));
    }

    private void handleIncomingIntent() {
        Intent intent = getIntent();
        ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
        if (addresses != null) {
            for (String address : addresses) {
                contactRepository.addContact(address);
            }
        }
    }

    @Override
    public void onContactsChanged(List<String> contacts) {
        adapter.clear();
        adapter.addAll(contacts);
        adapter.notifyDataSetChanged();
    }
}