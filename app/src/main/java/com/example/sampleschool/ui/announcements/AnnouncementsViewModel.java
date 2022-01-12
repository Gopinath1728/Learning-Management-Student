package com.example.sampleschool.ui.announcements;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AnnouncementModel;

import java.util.List;

public class AnnouncementsViewModel extends ViewModel {
    private MutableLiveData<List<AnnouncementModel>> mutableLiveData;

    public AnnouncementsViewModel() {
    }

    public MutableLiveData<List<AnnouncementModel>> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.announcementList);
        return mutableLiveData;
    }
}
