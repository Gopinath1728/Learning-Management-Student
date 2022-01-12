package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.MessageModel;

import java.util.List;

public interface IMessageViewCallbackListener {
    void onMessageLoadSuccess(List<MessageModel> messageModelList);
    void onMessageLoadFailed(String messageLoadError);
}
