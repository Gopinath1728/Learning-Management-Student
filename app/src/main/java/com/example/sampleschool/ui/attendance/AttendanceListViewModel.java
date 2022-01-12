package com.example.sampleschool.ui.attendance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;

import java.util.List;
import java.util.Map;

public class AttendanceListViewModel extends ViewModel {
    private MutableLiveData<Map<String, List<String>>> mutableLiveData;

    public AttendanceListViewModel() {
    }

    public MutableLiveData<Map<String, List<String>>> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.attendanceData);
        return mutableLiveData;
    }
}
