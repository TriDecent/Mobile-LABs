package com.example.lab05_3.domain;

import android.content.Context;
import android.telephony.SmsManager;

import com.example.lab05_3.R;
import com.example.lab05_3.data.repository.SmsContactRepository;

import java.util.List;

public class SmsResponder {
    private final SmsManager smsManager;
    private final Context context;
    private final SmsContactRepository contactRepository;

    public SmsResponder(Context context, SmsContactRepository contactRepository) {
        this.smsManager = SmsManager.getDefault();
        this.context = context;
        this.contactRepository = contactRepository;
    }

    public void respond(boolean isOK, List<String> recipients) {
        String response = isOK
                ? context.getString(R.string.i_am_safe_and_well_do_not_worry)
                : context.getString(R.string.tell_my_mother_i_love_her);

        for (String recipient : recipients) {
            smsManager.sendTextMessage(recipient, null, response, null, null);
            contactRepository.removeContact(recipient);
        }
    }
}
