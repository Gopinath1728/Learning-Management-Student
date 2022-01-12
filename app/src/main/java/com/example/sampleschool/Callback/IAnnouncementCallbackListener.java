package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.AnnouncementModel;

import java.util.List;

public interface IAnnouncementCallbackListener {
    void onAnnouncementLoadSuccess(List<AnnouncementModel> announcementModelList);
    void onAnnouncementLoadFailed(String announcementLoadError);
}
