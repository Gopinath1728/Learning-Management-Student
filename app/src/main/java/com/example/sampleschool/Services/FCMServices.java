package com.example.sampleschool.Services;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class FCMServices extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String, String> dataRecv = remoteMessage.getData();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isMessage", true);
        Common.showNotification(this, new Random().nextInt(),
                dataRecv.get("title"),
                dataRecv.get("content"),
                intent);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Common.updateToken(this,s);
    }
}
