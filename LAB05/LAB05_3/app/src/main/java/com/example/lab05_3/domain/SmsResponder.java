package com.example.lab05_3.domain;

import android.telephony.SmsManager;

import com.example.lab05_3.data.repository.SmsContactRepository;

import java.util.List;

public class SmsResponder {
    private final SmsManager smsManager;
    private final SmsContactRepository contactRepository;

    private static final String I_AM_SAFE_AND_WELL_DO_NOT_WORRY = "I am safe and well. Don't Worry!";
    private static final String TELL_MY_MOTHER_I_LOVE_HER = "Tell my mother I love her.";

    public SmsResponder(SmsContactRepository contactRepository) {
        this.smsManager = SmsManager.getDefault();
        this.contactRepository = contactRepository;
    }

    public void respond(boolean isOK, List<String> recipients) {
        String response = isOK ?
                I_AM_SAFE_AND_WELL_DO_NOT_WORRY :
                TELL_MY_MOTHER_I_LOVE_HER;

        for (String recipient : recipients) {
            smsManager.sendTextMessage(recipient, null, response, null, null);
            contactRepository.removeContact(recipient);
        }
    }
}
