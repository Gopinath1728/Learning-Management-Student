package com.example.sampleschool.ui.messages;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.sampleschool.Callback.IMessageViewCallbackListener;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AttendanceModel;
import com.example.sampleschool.Model.MessageModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessageViewModel extends ViewModel implements IMessageViewCallbackListener {
    private MutableLiveData<List<MessageModel>> messageMutableLiveData;
    private MutableLiveData<String> messageErrorMessage = new MutableLiveData<>();
    private IMessageViewCallbackListener messageViewCallbackListener;

    public MessageViewModel() {
        messageViewCallbackListener=this;
    }

    public MutableLiveData<List<MessageModel>> getMessageMutableLiveData() {
        if (messageMutableLiveData == null) {
            messageMutableLiveData = new MutableLiveData<>();
            messageErrorMessage = new MutableLiveData<>();
            loadMessages();
        }
        return messageMutableLiveData;
    }

    private void loadMessages() {
        List<MessageModel> messageModels = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Students")
                .document(Common.currentStudent.getUid())
                .collection("Messages")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        messageViewCallbackListener.onMessageLoadFailed(error.getMessage());
                    } else {
                        if (value != null) {
                            messageModels.clear();
                            for (QueryDocumentSnapshot snapshot : value) {
                                MessageModel messageModel = snapshot.toObject(MessageModel.class);
                                messageModels.add(messageModel);
                            }
                            if (messageModels.size() > 0) {
                                messageViewCallbackListener.onMessageLoadSuccess(messageModels);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<String> getMessageErrorMessage() {
        return messageErrorMessage;
    }

    @Override
    public void onMessageLoadSuccess(List<MessageModel> messageModelList) {
        messageMutableLiveData.setValue(messageModelList);
    }

    @Override
    public void onMessageLoadFailed(String messageLoadError) {
messageErrorMessage.setValue(messageLoadError);
    }
}